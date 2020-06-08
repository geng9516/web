package jp.smartcompany.job.modules.tmg.schedule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import jp.smartcompany.job.modules.tmg.schedule.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleInfoVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
import java.text.ParseException;
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
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(申請(休憩))
     */
    public static final String NOTWORKINGID_NOTICE_REST = "TMG_ITEMS|NoticeRest";

    /**
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(実績(休憩))
     */
    public static final String NOTWORKINGID_RESULT_REST = "TMG_ITEMS|ResultRest";

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
     * 休憩開始時間 key
     */
    private final String _restOpen = "restOpen";

    /**
     * 休憩終了時間 key
     */
    private final String _restClose = "restClose";

    /**
     * 更新プログラムID
     */
    public final String TMG_SCHEDULE_CMODIFIERPROGRAMID = "TmgSchedule_ACT_EditMonthly_USchedule";

    /**
     * プログラムID
     */
    public final String TMG_SCHEDULE_CPROGRAMID = "TmgSchedule_ACT_EditMonthly_USchedule";

    /**
     * アクションを表すコードです。参照画面.読み出し処理
     */
    public final String ACT_DISPMONTHLY_RSCHEDULE = "ACT_DispMonthly_RSchedule";

    /**
     * アクションを表すコードです。編集画面.読み出し処理
     */
    public final String ACT_EDITMONTHLY_RSCHEDULE = "ACT_EditMonthly_RSchedule";

    /**
     * アクションを表すコードです。編集画面.更新処理
     */
    public final String ACT_EDITMONTHLY_USCHEDULE = "ACT_EditMonthly_USchedule";

    /**
     * データ開始日
     */
    public final String Cs_MINDATE = "1900/01/01";

    /**
     * データ開始日
     */
    public final String Cs_MAXDATE = "2222/12/31";

    /**
     * 実績クリアの可否
     */
    public final boolean bClearResult = false;

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

        if (null != psDBBean) {

            //login user
            _loginUserCode = psDBBean.getUserCode();

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
        } else {
            logger.error("psDBBean対象が空です");
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
    public ScheduleInfoVO selectPaidHolidayInfo(String year, String month, String employeeId) {
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
        if (weekday == 0) {
            paidHolidayVO.setDateOfRecordDays("0日");
        } else {
            paidHolidayVO.setDateOfRecordDays(String.valueOf(weekday));
        }
        if (holiday == 0) {
            paidHolidayVO.setNationalHolidayDays("0日");
        } else {
            paidHolidayVO.setNationalHolidayDays(String.valueOf(holiday));
        }

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

        ScheduleInfoVO scheduleInfoVO = new ScheduleInfoVO();
        scheduleInfoVO.setPaidHolidayVO(paidHolidayVO);
        scheduleInfoVO.setScheduleDataDTOList(scheduleDataDTOS);

        return scheduleInfoVO;
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
        return this.getWorkingHourOfMonthDisp(String.valueOf(WorkingHourOfMonth));
    }

    /**
     * フォーマット時間　　　時間--＞ｘ時間ｘ分
     *
     * @param workingHourOfMonth
     * @return
     */
    private String getWorkingHourOfMonthDisp(String workingHourOfMonth) {
        if (null != workingHourOfMonth && !"".equals(workingHourOfMonth)) {
            Double dWorkingHourOfMonthDisp = new Double(Double.parseDouble(workingHourOfMonth) * 60);
            int iWorkingHourOfMonthDisp = dWorkingHourOfMonthDisp.intValue();
            return this.formatMinuteToDispTime(iWorkingHourOfMonthDisp);
        }
        return null;
    }

    /**
     * 分数を表示形式時刻(***時間**分)に変換します
     *
     * @param minute 分数
     * @return String 表示形式時刻
     */
    private String formatMinuteToDispTime(int minute) {
        // 時間数と分数を求める
        int hour = (minute - (minute % 60)) / 60;
        int min = minute % 60;
        String sDispTime = new String("00" + min);
        return hour + "時間" + (sDispTime.substring(sDispTime.length() - 2)) + "分";
    }

    /**
     * 翌月リンクを取得
     *
     * @param employeeId
     * @return
     */
    public HashMap<String, Object> selectLinkOfNextMonth(String employeeId) {
        String nextMonth = iTmgScheduleService.selectLinkOfNextMonth(employeeId, _baseDate, _targetCustCode, _targetCompCode);
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("nextMonth", nextMonth);
        return result;
    }

    /**
     * 前月リンクを取得
     *
     * @param employeeId
     * @return
     */
    public HashMap<String, Object> selectLinkOfPreMonth(String employeeId) {

        return iTmgScheduleService.selectLinkOfPreMonth(employeeId, _baseDate, _targetCustCode, _targetCompCode);
    }

    /**
     * ディフォルト時間
     *
     * @return
     */
    public HashMap<String, Object> getDefaultDate() {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("defaultYear", DateUtil.format(DateUtil.nextMonth(), "yyyy"));
        result.put("defaultMonth", DateUtil.format(DateUtil.nextMonth(), "MM"));
        return result;
    }

    /**
     * [区分]汎用マスタより区分コンボボックスの選択値を取得します
     *
     * @return
     */
    private List<HashMap<String, Object>> selectGenericDetail() {
        return iTmgScheduleService.selectGenericDetail(_loginLanguageCode, _baseDate, _targetCompCode, _targetCustCode);
    }

    /**
     * [出張]汎用マスタより出張区分コンボボックスの選択値を取得します
     *
     * @return
     */
    private List<HashMap<String, Object>> selectBusinessTrip() {
        return iTmgScheduleService.selectBusinessTrip(_loginLanguageCode, _baseDate, _targetCompCode, _targetCustCode);
    }

    /**
     * 勤務パターンテーブルより勤務パターンコンボボックスの選択値を取得します.(一括指定用)
     *
     * @param sectionid
     * @param groupid
     * @return
     */
    private List<HashMap<String, Object>> selectWorkPatternIkkatu(String sectionid, String groupid) {
        return iTmgScheduleService.selectWorkPatternIkkatu(_targetCompCode, _targetCustCode, sectionid, groupid, _baseDate);
    }

    /**
     * [区分][出張][勤務パターン]
     *
     * @param sectionid
     * @param groupid
     * @return
     */
    public HashMap<String, Object> selectIkkaInfo(String sectionid, String groupid) {
        //[区分]
        List<HashMap<String, Object>> kubunnList = this.selectGenericDetail();
        //[出張]
        List<HashMap<String, Object>> syuccyouList = this.selectBusinessTrip();
        // 勤務パターン
        List<HashMap<String, Object>> workPatternList = this.selectWorkPatternIkkatu(sectionid, groupid);
        HashMap<String, Object> results = new HashMap<String, Object>();
        results.put("kubunnList", kubunnList);
        results.put("syuccyouList", syuccyouList);
        results.put("workPatternList", workPatternList);
        return results;
    }

    /**
     * データをロードのテストファンクション
     *
     * @return
     */
    private MonthlyUScheduleEditParaDTO loadMonthlyUScheduleEditParaDTOData() {

        MonthlyUScheduleEditParaDTO monthlyUScheduleEditParaDTO = new MonthlyUScheduleEditParaDTO();
        monthlyUScheduleEditParaDTO.setLoginUserId("46402406");
        monthlyUScheduleEditParaDTO.setBaseDate("2020/04/01");
        monthlyUScheduleEditParaDTO.setDispStartDate("2020/07/01");
        monthlyUScheduleEditParaDTO.setEndDispDate("2020/04/30");
        monthlyUScheduleEditParaDTO.setTargetUserId("29042924");

        MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO = new MonthlyScheduleEmpInfoDTO();
        monthlyScheduleEmpInfoDTO.setBussinessTripid("TMG_BUSINESS_TRIP|00");
        monthlyScheduleEmpInfoDTO.setComment("77777111");
        monthlyScheduleEmpInfoDTO.setDyyyymmdd("2020/04/02");
        monthlyScheduleEmpInfoDTO.setNopen("9:00");
        monthlyScheduleEmpInfoDTO.setNclose("17:30");
        monthlyScheduleEmpInfoDTO.setWorkId("TMG_WORK|000");

        List<HashMap<String, String>> restList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> rest1 = new HashMap<String, String>();
        rest1.put(_restOpen, "12:00");
        rest1.put(_restClose, "12:46");
        restList.add(rest1);

        HashMap<String, String> rest2 = new HashMap<String, String>();
        rest2.put(_restOpen, "12:46");
        rest2.put(_restClose, "13:00");
        restList.add(rest2);
        monthlyScheduleEmpInfoDTO.setRestList(restList);
        //////////////////////////////////////////////////////

        MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO1 = new MonthlyScheduleEmpInfoDTO();
        monthlyScheduleEmpInfoDTO1.setBussinessTripid("TMG_BUSINESS_TRIP|00");
        monthlyScheduleEmpInfoDTO1.setComment("8888111");
        monthlyScheduleEmpInfoDTO1.setDyyyymmdd("2020/04/03");
        monthlyScheduleEmpInfoDTO1.setNopen("9:00");
        monthlyScheduleEmpInfoDTO1.setNclose("17:30");
        monthlyScheduleEmpInfoDTO1.setWorkId("TMG_WORK|000");

        List<HashMap<String, String>> restList1 = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> rest11 = new HashMap<String, String>();
        rest11.put(_restOpen, "12:00");
        rest11.put(_restClose, "13:00");
        restList1.add(rest11);
       /* HashMap<String, String> rest21 = new HashMap<String, String>();
        rest21.put(_restOpen, "12:45");
        rest21.put(_restClose, "12:51");
        restList1.add(rest21);*/
        monthlyScheduleEmpInfoDTO1.setRestList(restList1);

        List<MonthlyScheduleEmpInfoDTO> monthlyScheduleEmpInfoDTOS = new ArrayList<MonthlyScheduleEmpInfoDTO>();
        monthlyScheduleEmpInfoDTOS.add(monthlyScheduleEmpInfoDTO);
        monthlyScheduleEmpInfoDTOS.add(monthlyScheduleEmpInfoDTO1);
        monthlyUScheduleEditParaDTO.setMonthlyScheduleEmpInfoDTOS(monthlyScheduleEmpInfoDTOS);
        JSONObject jsonObject = new JSONObject(monthlyUScheduleEditParaDTO);
        System.out.println("予定作成jsonフォーマット:\n" + jsonObject.toString());
        return monthlyUScheduleEditParaDTO;

    }


    /**
     * 予定作成更新処理を行います。
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeEditMonthlyUSchedule() {

        //画面から
        MonthlyUScheduleEditParaDTO monthlyUScheduleEditParaDTO = this.loadMonthlyUScheduleEditParaDTOData();

        if (null == monthlyUScheduleEditParaDTO) {
            logger.warn("更新データが空です");
            return;
        }

        List<MonthlyScheduleEmpInfoDTO> monthlyScheduleEmpInfoDTOS = monthlyUScheduleEditParaDTO.getMonthlyScheduleEmpInfoDTOS();
        if (null == monthlyScheduleEmpInfoDTOS) {
            logger.warn("予定データが空です");
            return;
        }

        // チェックテーブル削除
        iTmgScheduleService.deleteDailyCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), _targetCompCode, _targetCustCode);
        iTmgScheduleService.deleteDailyDetailCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), _targetCompCode, _targetCustCode);

        for (int i = 0; i < monthlyScheduleEmpInfoDTOS.size(); i++) {
            MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO = monthlyScheduleEmpInfoDTOS.get(i);
            //日別情報を更新する(ロット処理)
            List<HashMap<String, String>> restTime = monthlyScheduleEmpInfoDTO.getRestList();
            String nRestOpen = "";
            String nRestClose = "";
            //対象日
            String sTargetDate = monthlyScheduleEmpInfoDTO.getDyyyymmdd();
            boolean bNoWorking = isNoWorkingId(monthlyScheduleEmpInfoDTO.getWorkId());

            //[勤怠]日別情報を更新する
            iTmgScheduleService.insertTmgDailyCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, bClearResult, Cs_MGD_HOLFLG_0, monthlyScheduleEmpInfoDTO.getWorkId(),
                    monthlyScheduleEmpInfoDTO.getNopen() == "" ? "NULL" : monthlyScheduleEmpInfoDTO.getNopen(), monthlyScheduleEmpInfoDTO.getNclose() == "" ? "NULL" : monthlyScheduleEmpInfoDTO.getNclose(), bNoWorking, monthlyScheduleEmpInfoDTO.getBussinessTripid(), monthlyScheduleEmpInfoDTO.getComment(), monthlyUScheduleEditParaDTO.getTargetUserId(), monthlyScheduleEmpInfoDTO.getDyyyymmdd(), _targetCompCode, _targetCustCode, _loginLanguageCode);

            // 複数休憩分ループする
            for (int j = 0; j < restTime.size(); j++) {
                HashMap<String, String> restTimeHashMap = restTime.get(j);
                //休憩開始時間
                nRestOpen = restTimeHashMap.get(_restOpen);
                //休憩終了時間
                nRestClose = restTimeHashMap.get(_restClose);
                if (!ObjectUtil.isEmpty(nRestOpen) && !ObjectUtil.isEmpty(nRestOpen) && !ObjectUtil.isEmpty(nRestClose) && !ObjectUtil.isEmpty(nRestClose)) {
                    // TMG_DAILY_DETAIL 休憩時間レコード登録：予定
                    iTmgScheduleService.insertTmgDailyDetailCheckRest(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getTargetUserId(),
                            Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_PLAN_REST, nRestOpen, nRestClose,
                            NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_PLAN_REST.equals(NOTWORKINGID_RESULT_REST));
                    // 就業区分が就業禁止(有給)、就業禁止(無給)以外の場合に申請と実績の休憩レコードを作成する。
                    if (!isNoWorkingId(monthlyScheduleEmpInfoDTO.getWorkId())) {
                        // TMG_DAILY_DETAIL 休憩時間レコード登録：申請
                        iTmgScheduleService.insertTmgDailyDetailCheckRest(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getTargetUserId(),
                                Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_NOTICE_REST, nRestOpen, nRestClose,
                                NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_NOTICE_REST.equals(NOTWORKINGID_RESULT_REST));

                        // TMG_DAILY_DETAIL 休憩時間レコード登録：実績
                        iTmgScheduleService.insertTmgDailyDetailCheckRest(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getTargetUserId(),
                                Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_RESULT_REST, nRestOpen, nRestClose,
                                NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_RESULT_REST.equals(NOTWORKINGID_RESULT_REST));
                    }
                }

            }
            iTmgScheduleService.insertTmgTrigger(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getLoginUserId(),
                    Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, ACT_EDITMONTHLY_USCHEDULE);
            iTmgScheduleService.deleteTmgTrigger(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID);
            iTmgScheduleService.deleteDailyCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), _targetCompCode, _targetCustCode);
            iTmgScheduleService.deleteDetailCheck(_targetCustCode, _targetCompCode, monthlyUScheduleEditParaDTO.getLoginUserId());
        }
    }

    /**
     * 指定された就業区分が就業禁止(有給)または就業禁止(無給)かどうかを判断します。
     *
     * @param sWorkingId
     * @return true : 就業禁止(有給)、就業禁止(無給)の場合　false：就業禁止(有給)、就業禁止(無給)以外の場合
     */
    private boolean isNoWorkingId(String sWorkingId) {

        if (TmgUtil.Cs_MGD_WORK_200.equals(sWorkingId)
                || TmgUtil.Cs_MGD_WORK_412.equals(sWorkingId)) {

            // 就業禁止(有給)、就業禁止(無給)の場合
            return true;
        } else {

            // 就業禁止(有給)、就業禁止(無給)以外の場合
            return false;
        }
    }

    /**
     * 開始日 ? 終了日 の日数を返します。
     *
     * @param start
     * @param end
     * @return 終了日と開始日の差分日数を返す。
     */
    private int getDifferenceOfDays(String start, String end) {

        long nStart = 0;
        long nEnd = 0;
        int nDifference = 0;

        // データ表示開始日をミリ秒単位で取得
        nStart = new Long(getCalendarOfTypeStringOfDate(start).getTimeInMillis()).longValue();

        // データ表示終了日をミリ秒単位で取得
        nEnd = new Long(getCalendarOfTypeStringOfDate(end).getTimeInMillis()).longValue();

        // 終了日と開始日の差分を算出
        // nDifference = new Long((nEnd - nStart) / (86400000)).intValue();
        nDifference = new Long((nEnd - nStart) / (1000 * 60 * 60 * 24))
                .intValue();

        if (nDifference < 0) {
            nDifference = 0;
        }

        return nDifference;
    }

    /**
     * 週次勤務パターン
     *
     * @return
     */
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(String year, String month) {

        if (null == year || "".equals(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (null == month || "".equals(month)) {
            month = DateUtil.thisMonth() + "";
        }
        if (month.length() == 1) {
            month += "0";
        }

        // 表示月   2020/04/01
        String baseDay = year + "/" + month + "/01";
        if (null == _baseDate || "".equals(_baseDate)) {
            _baseDate = baseDay;
        }

        // 本日
        String sysDate = DateUtil.now();

        // 現在日付の翌月(初期表示時の年月)を表示している場合のみ、
        // 適用開始日が未来のパターンが全て表示される。
        boolean isAfter = this.compareDate(sysDate, baseDay);

        List<TmgWeekPatternDTO> tmgWeekPatternDTOS = iTmgScheduleService.selectTmgWeekPattern(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode, isAfter);
        return tmgWeekPatternDTOS;
    }

    /**
     * 時間比較
     *
     * @param dateStrA
     * @param dateStrB
     * @return dateStrA>dateStrBの場合、TRUE戻る
     */
    private boolean compareDate(String dateStrA, String dateStrB) {
        try {
            Date dateA = DateFormat.getDateInstance().parse(dateStrA);
            Date dateB = DateFormat.getDateInstance().parse(dateStrB);
            if (dateA.after(dateB)) {
                return true;
            }
            return false;
        } catch (ParseException e) {
            logger.error("時間比較エラー", e);
        }
        return false;
    }

    /**
     * 週勤務パターンを取得する
     *
     * @param employeeId
     * @param twp_nid
     * @return
     */
    public TmgWeekPatternVO selectCsvReference(String employeeId, int twp_nid) {

        if (ObjectUtil.isNotNull(twp_nid)) {
            return iTmgScheduleService.selectCsvReference(_targetCustCode, _targetCompCode, _loginLanguageCode, employeeId, twp_nid);
        } else {
            logger.error("データidが空です");
        }
        return null;
    }

    /**
     * 週勤務パターンを取得する
     *
     * @param employeeId
     * @return
     */
    public List<TmgWeekPatternVO> selectCsvReference(String employeeId) {

        return iTmgScheduleService.selectCsvReferenceList(_targetCustCode, _targetCompCode, _loginLanguageCode, employeeId);
    }


    /**
     * テストデータ
     *
     * @return
     */
    private List<TmgWeekPatternCheckDTO> loadTmgWeekPatternCheckDTOData() {
        List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList = new ArrayList<TmgWeekPatternCheckDTO>();
        TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO = new TmgWeekPatternCheckDTO();
        tmgWeekPatternCheckDTO.setCompCode("01");
        tmgWeekPatternCheckDTO.setCustId("01");
        tmgWeekPatternCheckDTO.setEmployeeId("29042924");
        tmgWeekPatternCheckDTO.setTwp_cmodifieruserid(_loginUserCode);
        tmgWeekPatternCheckDTO.setPeriod("2020/08/01-2020/08/31");
        tmgWeekPatternCheckDTO.setTwp_dstartdate("2020/08/01");
        tmgWeekPatternCheckDTO.setTwp_denddate("2020/08/31");
        tmgWeekPatternCheckDTO.setCheckFlag(false);
        tmgWeekPatternCheckDTO.setTwp_copen1("");
        tmgWeekPatternCheckDTO.setTwp_cclose1("");
        tmgWeekPatternCheckDTO.setTwp_crestopen1("");
        tmgWeekPatternCheckDTO.setTwp_crestclose1("");
        tmgWeekPatternCheckDTO.setTwp_cholflg1("1");
        tmgWeekPatternCheckDTO.setTwp_copen2("08:30");
        tmgWeekPatternCheckDTO.setTwp_cclose2("07:30");
        tmgWeekPatternCheckDTO.setTwp_crestopen2("12:00");
        tmgWeekPatternCheckDTO.setTwp_crestclose2("13:00");
        tmgWeekPatternCheckDTO.setTwp_cholflg2("0");
        tmgWeekPatternCheckDTO.setTwp_copen3("08:30");
        tmgWeekPatternCheckDTO.setTwp_cclose3("07:30");
        tmgWeekPatternCheckDTO.setTwp_crestopen3("12:00");
        tmgWeekPatternCheckDTO.setTwp_crestclose3("13:00");
        tmgWeekPatternCheckDTO.setTwp_cholflg3("0");
        tmgWeekPatternCheckDTO.setTwp_copen4("08:30");
        tmgWeekPatternCheckDTO.setTwp_cclose4("07:30");
        tmgWeekPatternCheckDTO.setTwp_crestopen4("12:00");
        tmgWeekPatternCheckDTO.setTwp_crestclose4("13:00");
        tmgWeekPatternCheckDTO.setTwp_cholflg4("0");
        tmgWeekPatternCheckDTO.setTwp_copen5("08:30");
        tmgWeekPatternCheckDTO.setTwp_cclose5("07:30");
        tmgWeekPatternCheckDTO.setTwp_crestopen5("12:00");
        tmgWeekPatternCheckDTO.setTwp_crestclose5("13:00");
        tmgWeekPatternCheckDTO.setTwp_cholflg5("0");
        tmgWeekPatternCheckDTO.setTwp_copen6("08:30");
        tmgWeekPatternCheckDTO.setTwp_cclose6("07:30");
        tmgWeekPatternCheckDTO.setTwp_crestopen6("12:00");
        tmgWeekPatternCheckDTO.setTwp_crestclose6("13:00");
        tmgWeekPatternCheckDTO.setTwp_cholflg6("0");
        tmgWeekPatternCheckDTO.setTwp_copen7("");
        tmgWeekPatternCheckDTO.setTwp_cclose7("");
        tmgWeekPatternCheckDTO.setTwp_crestopen7("");
        tmgWeekPatternCheckDTO.setTwp_crestclose7("");
        tmgWeekPatternCheckDTO.setTwp_cholflg7("3");

        TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO1 = new TmgWeekPatternCheckDTO();
        tmgWeekPatternCheckDTO1.setCompCode("01");
        tmgWeekPatternCheckDTO1.setCustId("01");
        tmgWeekPatternCheckDTO1.setEmployeeId("29042924");
        tmgWeekPatternCheckDTO1.setTwp_cmodifieruserid(_loginUserCode);
        tmgWeekPatternCheckDTO1.setPeriod("2020/07/01-2020/07/31");
        tmgWeekPatternCheckDTO1.setTwp_dstartdate("2020/07/01");
        tmgWeekPatternCheckDTO1.setTwp_denddate("2020/07/31");
        tmgWeekPatternCheckDTO1.setCheckFlag(true);
        tmgWeekPatternCheckDTO1.setTwp_copen1("");
        tmgWeekPatternCheckDTO1.setTwp_cclose1("");
        tmgWeekPatternCheckDTO1.setTwp_crestopen1("");
        tmgWeekPatternCheckDTO1.setTwp_crestclose1("");
        tmgWeekPatternCheckDTO1.setTwp_cholflg1("1");
        tmgWeekPatternCheckDTO1.setTwp_copen2("08:30");
        tmgWeekPatternCheckDTO1.setTwp_cclose2("07:30");
        tmgWeekPatternCheckDTO1.setTwp_crestopen2("12:00");
        tmgWeekPatternCheckDTO1.setTwp_crestclose2("13:00");
        tmgWeekPatternCheckDTO1.setTwp_cholflg2("0");
        tmgWeekPatternCheckDTO1.setTwp_copen3("08:30");
        tmgWeekPatternCheckDTO1.setTwp_cclose3("07:30");
        tmgWeekPatternCheckDTO1.setTwp_crestopen3("12:00");
        tmgWeekPatternCheckDTO1.setTwp_crestclose3("13:00");
        tmgWeekPatternCheckDTO1.setTwp_cholflg3("0");
        tmgWeekPatternCheckDTO1.setTwp_copen4("08:30");
        tmgWeekPatternCheckDTO1.setTwp_cclose4("07:30");
        tmgWeekPatternCheckDTO1.setTwp_crestopen4("12:00");
        tmgWeekPatternCheckDTO1.setTwp_crestclose4("13:00");
        tmgWeekPatternCheckDTO1.setTwp_cholflg4("0");
        tmgWeekPatternCheckDTO1.setTwp_copen5("08:30");
        tmgWeekPatternCheckDTO1.setTwp_cclose5("07:30");
        tmgWeekPatternCheckDTO1.setTwp_crestopen5("12:00");
        tmgWeekPatternCheckDTO1.setTwp_crestclose5("13:00");
        tmgWeekPatternCheckDTO1.setTwp_cholflg5("0");
        tmgWeekPatternCheckDTO1.setTwp_copen6("08:30");
        tmgWeekPatternCheckDTO1.setTwp_cclose6("07:30");
        tmgWeekPatternCheckDTO1.setTwp_crestopen6("12:00");
        tmgWeekPatternCheckDTO1.setTwp_crestclose6("13:00");
        tmgWeekPatternCheckDTO1.setTwp_cholflg6("0");
        tmgWeekPatternCheckDTO1.setTwp_copen7("");
        tmgWeekPatternCheckDTO1.setTwp_cclose7("");
        tmgWeekPatternCheckDTO1.setTwp_crestopen7("");
        tmgWeekPatternCheckDTO1.setTwp_crestclose7("");
        tmgWeekPatternCheckDTO1.setTwp_cholflg7("3");

        tmgWeekPatternCheckDTOList.add(tmgWeekPatternCheckDTO);
        tmgWeekPatternCheckDTOList.add(tmgWeekPatternCheckDTO1);
        return tmgWeekPatternCheckDTOList;
    }


    /**
     * 週勤務パターン登録画面　登録処理
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeMakeWeekPattern_UWPtn() {

        //画面から(暫くは　フェイクデータ)
        List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList = this.loadTmgWeekPatternCheckDTOData();

        String _errCode = "0";
        /**
         * eg:ACT_MakeWeekPattern_UWPtn
         */
        String actionParam = psDBBean.getRequestHash().get("txtACTION") == null ? "" : psDBBean.getRequestHash().get("txtACTION").toString();
        //初期化modifiedProgramId
        if (null == actionParam || "".equals(actionParam)) {
            actionParam = "ACT_MakeWeekPattern_UWPtn";
        }
        /**
         * eg: TmgSchedule_ACT_MakeWeekPattern_UWPtn
         */
        String modifierprogramid = "TmgSchedule_" + actionParam;

        iTmgScheduleService.deleteTmgTrigger(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteErrMsg(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteWeekPatternCheck(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);

        //週勤務パターンデータを登録
        for (int i = 0; i < tmgWeekPatternCheckDTOList.size(); i++) {
            TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO = tmgWeekPatternCheckDTOList.get(i);
            tmgWeekPatternCheckDTO.setTwp_cmodifierprogramid(modifierprogramid);
            if (tmgWeekPatternCheckDTO.isCheckFlag()) {
                //注意：　更新フラグ  TRUEの場合は登録    FALSEの場合は削除(不要)
                iTmgScheduleService.insertTmgWeekPatternCheck(tmgWeekPatternCheckDTO);
            }
        }

        //エラーメッセージに追加する
        iTmgScheduleService.insertErrMsg(_targetCustCode, _targetCompCode, _loginLanguageCode, _targetUserCode, _loginUserCode, modifierprogramid, Cs_MINDATE, Cs_MAXDATE);
        //エラーメッセージ取得
        String errCode = iTmgScheduleService.selectErrMsg(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        if (!_errCode.equals(errCode)) {
            logger.error("週勤務パターン登録の際は、エラーが発生しました!");
            return;
        }
        iTmgScheduleService.insertTrigger(_targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, modifierprogramid, actionParam);
        iTmgScheduleService.deleteTmgTrigger(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteErrMsg(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteWeekPatternCheck(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);

    }


}