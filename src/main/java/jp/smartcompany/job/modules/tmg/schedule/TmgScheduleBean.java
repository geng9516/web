package jp.smartcompany.job.modules.tmg.schedule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.NpaidRestDTO;
import jp.smartcompany.job.modules.tmg.schedule.dto.ScheduleDataDTO;
import jp.smartcompany.job.modules.tmg.schedule.dto.TargetUserDetailDTO;
import jp.smartcompany.job.modules.tmg.schedule.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 陳毅力
 * @description 予定作成
 * @objectSource ps.c01.tmg.TmgSchedule.TmgScheduleBean
 * @date 2020/05/25
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgScheduleBean {

    private final Logger logger = LoggerFactory.getLogger(TmgScheduleBean.class);
    private final PsDBBean psDBBean;
    private final ITmgScheduleService iTmgScheduleService;
    /**
     * 汎用参照オブジェクト
     */
    private TmgReferList referList = null;

    /**
     * 日付形式1
     */
    protected final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    /**
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(予定(休憩))
     */
    private final String NOTWORKINGID_PLAN_REST = "TMG_ITEMS|PlanRest";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>管理対象外</b>"を表すマスターコードです
     */
    public final String Cs_MGD_MANAGEFLG_0 = "TMG_MANAGEFLG|0";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>平日</b>"を表すマスターコードです
     */
    public final String Cs_MGD_HOLFLG_0 = "TMG_HOLFLG|0";

    /**
     * 日付形式2
     */
    private static final String Cs_FORMAT_SLASH = "/";

    /**
     * 日付形式1
     */
    public static final String Cs_FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    /**
     * システム年月日の前月の月初(1日)
     */
    private String _preFirstDayOfSysDate = null;

    /**
     * 今月
     */
    private static final int PARAM_THIS_MONTH = -1;

    /**
     * システム年月日(今月)
     */
    private String _thisMonth = "";


    /**
     * 1ヶ月前
     */
    private static final int PARAM_PREV_MONTH = -2;

    /**
     * データ表示開始日が4週間区切りの日かをチェックするフラグ
     */
    protected boolean _isDispVariationalWorkType = false;

    private String _endDateOf4Weeks = null;
    /**
     * 表示開始日
     */
    private String _startDispDate = null;

    /**
     * 終了日
     */
    private String _endDispDate = null;

    /**
     * 基準日(開始日)
     */
    protected String _baseDate = null;

    /**
     * 終了日
     */
    protected String _endDate = null;


    /**
     * 検索対象者が対象期間中に４週間変形労働制の場合はtrueになります
     */
    private boolean _isVariationalWorkType = false;

    /**
     * ログインユーザー顧客コード
     */
    private String _loginCustCode = null;

    /**
     * ログインユーザー法人コード
     */
    private String _targetCompCode = null;


    private String _targetCustCode = null;


    /**
     * 検索対象ユーザー所属コード
     */
    private String _targetSecCode = "";

    /**
     * 検索対象ユーザーコード
     */
    protected String _targetUserCode = "";

    /**
     * 検索対象ユーザー所属グループコード
     */
    private String _targetGroupCode = "";

    /**
     * ログインユーザーコード
     */
    private String _loginUserCode = "";

    /**
     * ログイン画面で指定した言語コード
     */
    private String _loginLanguageCode = null;

    /**
     * 起算日を格納する変数
     */
    private String detailPeriod = "";


    /**
     * 対象者が4週間の変形労働制対象者か検索しフラグ値を設定します
     *
     * @param baseDate
     */
    private void setVariationalWorkInfo(String baseDate) {

        int tmp1 = iTmgScheduleService.selectVariationalWorkInfo(_targetUserCode, baseDate, _targetCustCode, _targetCompCode, _loginLanguageCode);
        int tmp2 = iTmgScheduleService.selectVariationalWorkDays(_targetUserCode, baseDate, _targetCustCode, _targetCompCode, _loginLanguageCode);

        if (tmp1 > 0) {
            _isVariationalWorkType = true;
        }
        if (tmp2 > 0) {
            _isVariationalWorkType = true;
        }
    }

    /**
     * 対象ユーザー情報
     *
     * @param employeeId
     * @param year
     * @param month
     * @return
     */
    public TargetUserDetailDTO selectTargetUserDetail(String employeeId, String year, String month) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        TargetUserDetailDTO targetUserDetailDTO = iTmgScheduleService.selectTargetUserDetail(employeeId, _baseDate, _targetCustCode, _targetCompCode, _startDispDate, _loginLanguageCode);
        return targetUserDetailDTO;

    }

    /**
     * 画面から入力された実行条件を判定し設定します。
     *
     * @param year
     * @param month
     * @param employeeId
     * @param modelMap
     */
    public void setExecuteParameters(String year, String month, String employeeId, ModelMap modelMap) {
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        // 現在日付の1ヶ月前の初日
        _preFirstDayOfSysDate = this.getFirstDayOfMonth(getSysdate(), PARAM_PREV_MONTH);
        // 現在日付の月初日を取得
        _thisMonth = this.getFirstDayOfMonth(getSysdate(), PARAM_THIS_MONTH);
        // 2020/04/01
        _baseDate = year + "/" + month + "/" + "01";
        this.setReferList(_baseDate, modelMap);
        this.setBasicUserInfo();
        _targetUserCode = employeeId;

        // 社員が選択されている場合は基本労働制か変形労働制か判定する処理を実行する。
        if (isSelectedTargetUser()) {
            setVariationalWorkInfo(_baseDate);
        }
        // 4週間単位の変形労働制職員か？
        if (_isVariationalWorkType) {
            // 起算日を取得します
            detailPeriod = iTmgScheduleService.selectDetailPeriod(employeeId, _targetCompCode, _targetCustCode);
        }

        // ユーザー基本情報も再設定する。
        if (isSelectedTargetUser() && (!_isVariationalWorkType)) {
            // 当月の初日を取得する
            _baseDate = this.getFirstDayOfMonth(_baseDate, PARAM_THIS_MONTH);
            //setTmgReferListOfBeforeProcess();
            setBasicUserInfo();
        } else if (isSelectedTargetUser() && _isVariationalWorkType
                && _endDateOf4Weeks.equals("")) {
            initBaseDateOf4Weeks();
        }
        // 4週間単位の変形労働職員の場合、表示基準日が4週間区切りの日付かどうかを設定する。
        if (_isVariationalWorkType) {
            setDispVariationalWork();
        }

        //終了日を取得する
        _endDate = getDateEndOfSchedule(_isVariationalWorkType, _baseDate);

        // 表示開始?終了日セット
        setDispDate();


    }


    /**
     * 基準日が表示基準日が4週間区切りの日付と一致しているかを設定します。
     */
    private void setDispVariationalWork() {
        String tmp = iTmgScheduleService.selectIsStart4weeks("TO_DATE(" + _baseDate + ", 'yyyy/MM/dd')", detailPeriod);
        if (null != tmp && !"".equals(tmp)) {
            if (Integer.parseInt(tmp) > 0) {
                _isDispVariationalWorkType = true;
            } else {
                _isDispVariationalWorkType = false;
            }
        }
    }

    /**
     * 基準日を4週間区切りの日付に丸めます 4週間単位の変形労働制職員の場合、基準日は必ず平成16年4月4日を起算日とする4週間区切りの日付になります
     *
     * @author shishido since 2007/12/18
     */
    private void initBaseDateOf4Weeks() {

    }


    /**
     * 引数で指定された値分だけ基準日の月を移動します。
     *
     * @param date    基準日 (「yyyy/mm/dd」形式の文字列)
     * @param mvValue 移動したい値()
     * @return String 移動した年月日 - ※日は月初(1日)になります。
     */
    private String getFirstDayOfMonth(String date, int mvValue) {
        Date objDate = null;

        try {
            String ymd[] = StringUtils.split(date, Cs_FORMAT_SLASH);
            Calendar calendar = Calendar.getInstance();

            // カレンダーに年月日を設定
            calendar.set(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]) + mvValue, 1);
            objDate = calendar.getTime();

        } catch (Exception e) {
            e.printStackTrace(); // 念の為
        }
        return new SimpleDateFormat(Cs_FORMAT_DATE_TYPE1).format(objDate);
    }

    /**
     * システム年月日を返却します。
     */
    private String getSysdate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }

    /**
     * ユーザーの基本情報を設定します。
     */
    private void setBasicUserInfo() {
        // 顧客コード
        if (null != psDBBean.getTargetCust() && !"".equals(psDBBean.getTargetCust())) {
            _targetCustCode = psDBBean.getTargetCust();
        }
        // 法人コード
        if (null != psDBBean.getTargetComp() && !"".equals(psDBBean.getTargetComp())) {
            _targetCompCode = psDBBean.getTargetComp();
        }
        // 言語コード
        if (null != psDBBean.getLanguage() && !"".equals(psDBBean.getLanguage())) {
            _loginLanguageCode = psDBBean.getLanguage();
        }

        // 2019/12/02 NSC.OU 入力サイトで、予定作成画面を表示する対応
        if (TmgUtil.Cs_SITE_ID_TMG_INP.equals(psDBBean.getSiteId())) {
            // 対象ユーザー
            if (null != psDBBean.getTargetUser() && !"".equals(psDBBean.getTargetUser())) {
                _targetUserCode = psDBBean.getTargetUser();
            }
        } else {
            if (referList == null) {
                return;
            } else {
                // 対象ユーザー
                if (null != referList.getTargetEmployee() && !"".equals(referList.getTargetEmployee())) {
                    _targetUserCode = referList.getTargetEmployee();
                }
                // 組織コード
                _targetSecCode = referList.getTargetSec();
                // 対象ユーザー所属グループ
                _targetGroupCode = referList.getTargetGroup();
            }
        }

    }

    /**
     * 検索対象者が選択されているか判定し返します。
     *
     * @return 選択されているtrue, 選択されていないfalse
     */
    public boolean isSelectedTargetUser() {

        if (_targetUserCode != null && _targetUserCode.length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 汎用リンクコンポーネントを生成します。
     */
    protected void setReferList(String pBaseDate, ModelMap modelMap) {
        try {
            referList = new TmgReferList(psDBBean, "TmgSchedule", pBaseDate,
                    TmgReferList.TREEVIEW_TYPE_EMP, true, true, false, false,
                    true);
            referList.putReferList(modelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* *//**
     * 汎用参照リンクオブジェクトを生成します。 _preMonthDate = システム年月日から1ヶ月前の月初のデータ_baseDate = 基準日
     * この条件でなくてはならない。_preMonthDate <= _baseDate
     * _baseDateが_preMonthDateより未来の場合は正の数値が返却される。
     *//*
    private void setTmgReferListOfBeforeProcess() {
        if (toDateFormat(_baseDate).after(toDateFormat(_preFirstDayOfSysDate))) {
            setReferList(_preFirstDayOfSysDate);
        } else {
            setReferList(_baseDate);
        }
    }
*/
    /*
     */
/**
 * 日付形式「yyyy/mm/dd」のString型文字列をDate型にキャストします。
 *
 * @param strDate
 * @return
 *//*

    public Date toDateFormat(String strDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat(FORMAT_DATE_TYPE1).parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
*/

    /**
     * 対象社員の勤務形態に対応する終了日を返します。
     *
     * @param isVariationalWorkType
     * @param baseDate
     * @return 変形労働制対象者の場合は４週間後の年月日を返します。 そうでなければ基準日の年月末日を返します。
     */
    private String getDateEndOfSchedule(boolean isVariationalWorkType, String baseDate) {

        if (isVariationalWorkType) {
            return _endDateOf4Weeks;
        } else {
            return getActualMaximumOfTypeStringOfDate(baseDate);
        }
    }

    /**
     * 「yyyy/MM/dd」形式の日付文字列に対して指定された{@link java.util.Calendar}型の
     * フィールド{Calendar.DAY_OF_MONTH}の最大値を取得し、 「yyyy/MM/dd」形式の日付文字列に再フォーマットして返します。
     *
     * @param date 「yyyy/MM/dd」形式の日付文字列
     * @return {Calendar.DAY_OF_MONTH}の最大値を取得し再フォーマット後の「yyyy/MM/dd」形式の日付文字列
     */
    private String getActualMaximumOfTypeStringOfDate(String date) {

        GregorianCalendar cal = (GregorianCalendar) getCalendarOfTypeStringOfDate(date);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DATE, lastDay);
        return new SimpleDateFormat(FORMAT_DATE_TYPE1).format(cal.getTime());
    }

    /**
     * 「yyyy/MM/dd」形式の日付文字列を{@link java.util.Calendar}型に設定して返します。
     *
     * @param date 「yyyy/MM/dd」形式の日付文字列
     * @return {@link java.util.Calendar}オブジェクトに変換された「yyyy/MM/dd」形式の日付文字列
     * @see TmgScheduleBean#divideDate(String)
     */
    private Calendar getCalendarOfTypeStringOfDate(String date) {

        int[] dates = new int[3];
        GregorianCalendar cal = (GregorianCalendar) Calendar.getInstance();
        dates = divideDate(date);
        // 指定された日付でカレンダーオブジェクトを生成
        cal.set(dates[0], (dates[1] - 1), dates[2]);
        return cal;
    }

    /**
     * yyyy/MM/dd形式の文字列を「yyyy」,「MM」,「dd」分割し数値型配列に格納します。
     */
    private int[] divideDate(String date) throws NumberFormatException {

        int[] dates = new int[3];
        try {
            StringTokenizer st = new StringTokenizer(date, "/");
            for (int i = 0; st.hasMoreTokens(); i++) {
                dates[i] = Integer.parseInt(st.nextToken());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * * 基本労働制対象者の表示開始?終了日を設定 月初の週が日曜日以外から始まる場合、前月末の日曜から
     * * 月末の週が土曜日以外で終わる場合、翌月頭の土曜日までを求める
     */
    private void setDispDate() {

        // 表示開始日
        _startDispDate = _baseDate;
        // 表示終了日
        _endDispDate = _endDate;

        if (_isVariationalWorkType || _baseDate == null) {
            // 基準日が初期化されていない場合は何もしない
            return;
        }

        // 週単位でチェックする人は表示開始終了日を取得
        HashMap<String, Object> results0 = this.selectDsipDate(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
        if (null != results0) {
            // 前月シート存在確認
            HashMap<String, Object> results1 = this.selectLinkOfPreMonth(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
            if (null != results1) {
                _startDispDate = results1.get("premonth").toString();
            }
            // 翌月シート存在確認
            HashMap<String, Object> results2 = this.selectLinkOfNextMonthNextSaturday(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
            if (null != results2) {
                _endDispDate = results2.get("premonth_lastday").toString();
            }
        }

    }

    /**
     * 月末の次の土曜日まで表示する
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    private HashMap<String, Object> selectLinkOfNextMonthNextSaturday(String employeeId, String baseDate, String custId, String compCode) {

        HashMap<String, Object> results = iTmgScheduleService.selectLinkOfNextMonthNextSaturday(employeeId, baseDate, compCode, custId);

        return results;

    }

    /**
     * 基本労働制対象者の表示開始?終了日の取得する
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    private HashMap<String, Object> selectDsipDate(String employeeId, String baseDate, String custId, String compCode) {
        String lastday = baseDate;
        HashMap<String, Object> results = iTmgScheduleService.selectDsipDate(employeeId, baseDate, lastday, custId, compCode);
        return results;
    }

    /**
     * 前月リンクを取得
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    private HashMap<String, Object> selectLinkOfPreMonth(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> results = iTmgScheduleService.selectLinkOfPreMonth(employeeId, baseDate, custId, compCode);
        return results;
    }

    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param year
     * @param month
     * @param employeeId
     * @return
     */
    public List<ScheduleDataDTO> selectSchedule(String year, String month, String employeeId) {
        List<ScheduleDataDTO> scheduleDataDTOS = iTmgScheduleService.selectSchedule(NOTWORKINGID_PLAN_REST, _startDispDate, _endDispDate, _baseDate, _endDate, _isVariationalWorkType, Cs_MGD_MANAGEFLG_0, employeeId, _targetCompCode, _targetCustCode, _loginLanguageCode);
        return scheduleDataDTOS;
    }

    /**
     * 公休日数	基準日数	基準時間	年次休暇残 info
     *
     * @param year
     * @param month
     * @param employeeId
     * @return
     */
    public PaidHolidayVO selectPaidHolidayInfo(String year, String month, String employeeId) {
        PaidHolidayVO paidHolidayVO = new PaidHolidayVO();
        List<ScheduleDataDTO> scheduleDataDTOS = iTmgScheduleService.selectSchedule(NOTWORKINGID_PLAN_REST, _startDispDate, _endDispDate, _baseDate, _endDate, _isVariationalWorkType, Cs_MGD_MANAGEFLG_0, employeeId, _targetCompCode, _targetCustCode, _loginLanguageCode);
        NpaidRestDTO npaidRestDTO = iTmgScheduleService.selectTmgMonthly(employeeId, _baseDate, _targetCompCode, _targetCustCode);
        /** 全社カレンダー.TCA_CHOLFLG値格納リスト */
        ArrayList _TCA_CHOLFlgList = new ArrayList();
        int weekday = 0;
        int holiday = 0;
        for (int i = 0; i < scheduleDataDTOS.size(); i++) {

            String sHolFlg = scheduleDataDTOS.get(i).getHolflgCalendar();
            _TCA_CHOLFlgList.add(sHolFlg);
            // 休日、平日を集計
            if (Cs_MGD_HOLFLG_0.equals(sHolFlg)) {
                weekday++;
            } else {
                holiday++;
            }
        }
        paidHolidayVO.setDateOfRecordDays(String.valueOf(weekday));
        paidHolidayVO.setNationalHolidayDays(String.valueOf(holiday));

        //年次休暇残
        String npaidRestDaysHour = npaidRestDTO.getTmo_npaid_rest_days() == null ? "0" : npaidRestDTO.getTmo_npaid_rest_days() + "日";
        int hour = 0;
        int min = 0;
        if (null != npaidRestDTO) {
            String hoursMins = npaidRestDTO.getTmo_npaid_rest_hours();
            if (null != hoursMins && !"".equals(hoursMins)) {
                int hoursMins_int = Integer.parseInt(hoursMins);

                if (hoursMins_int % 60 == 0) {
                    hour = hoursMins_int / 60;
                } else {
                    hour = (hoursMins_int - hoursMins_int % 60) / 60;
                    min = hoursMins_int % 60;
                }
            }
            npaidRestDaysHour += hour + "時間";
            npaidRestDaysHour += min + "分";
        }

        paidHolidayVO.setNpaidRestDaysHour(npaidRestDaysHour);

        //基準時間
        String workingHours = iTmgScheduleService.selectWorkingHours(employeeId, _baseDate, _targetCustCode, _targetCompCode);
        String dateOfRecord = this.calculateWorkingHourOfMonth(workingHours == null ? "0" : workingHours, paidHolidayVO.getDateOfRecordDays());
        paidHolidayVO.setDateOfRecord(dateOfRecord);

        return paidHolidayVO;
    }

    /**
     * 平日時の勤務時間と基準日数を乗算し、基準時間を算出します。
     *
     * @param MIOfWeekDay 平日の勤務時間(分)
     * @param baseDateCnt 基準日の日数
     */
    private String calculateWorkingHourOfMonth(String MIOfWeekDay, String baseDateCnt) {

        // 平日の勤務時間(分)
        double minites = 0.0;
        // 基準日の日数
        double bDateCnt = 0.0;

        // 勤務時間数に端数が存在する場合に不正な値となるため修正
        // 1ヶ月の勤務時間(時間)
        double WorkingHourOfMonth = 0.0;
        minites = Double.parseDouble(StringUtils.defaultIfEmpty(MIOfWeekDay, "0"));
        bDateCnt = Double.parseDouble(StringUtils.defaultIfEmpty(baseDateCnt, "0"));
        // 1ヶ月の勤務時間を算出
        WorkingHourOfMonth = (minites / 60) * bDateCnt;
        return String.valueOf(WorkingHourOfMonth);

    }

}