package jp.smartcompany.job.modules.tmg.schedule;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.framework.util.PsBuildTargetSql;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import jp.smartcompany.job.modules.tmg.schedule.vo.PaidHolidayVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.ScheduleInfoVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.WeekWorkType;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ITmgScheduleService iTmgScheduleService;

    private PsDBBean psDBBean;

    /**
     * 汎用参照オブジェクト
     */
    private TmgReferList referList = null;

    /**
     * 日付形式1
     */
    private final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    /**
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(予定(休憩))
     */
    private final String NOTWORKINGID_PLAN_REST = "TMG_ITEMS|PlanRest";

    /**
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(申請(休憩))
     */
    private final String NOTWORKINGID_NOTICE_REST = "TMG_ITEMS|NoticeRest";

    /**
     * 複数休憩JSON取得時(TMG_F_GET_MDAILYファンクション使用時)のCTPYE PLAN(実績(休憩))
     */
    private final String NOTWORKINGID_RESULT_REST = "TMG_ITEMS|ResultRest";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>管理対象外</b>"を表すマスターコードです
     */
    private final String Cs_MGD_MANAGEFLG_0 = "TMG_MANAGEFLG|0";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>平日</b>"を表すマスターコードです
     */
    private final String Cs_MGD_HOLFLG_0 = "TMG_HOLFLG|0";

    /**
     * 日付形式2
     */
    private static final String Cs_FORMAT_SLASH = "/";

    /**
     * 日付形式1
     */
    private static final String Cs_FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    /**
     * 日付形式2
     */
    private static final String Cs_FORMAT_DATE_TYPE2 = "yy-MM-dd";

    /**
     * 日付形式３
     */
    private static final String Cs_FORMAT_DATE_TYPE3 = "yyyy年M月dd日";

    /**
     * 今月
     */
    private static final int PARAM_THIS_MONTH = -1;

    /**
     * システム年月日(今月)
     */
    private String _thisMonthFirstDay = "";
    /**
     * 現在日付の月末日
     */
    private String _thisMonthLastDay = "";

    /**
     * 1ヶ月前
     */
    private static final int PARAM_PREV_MONTH = -2;

    /**
     * データ表示開始日が4週間区切りの日かをチェックするフラグ
     */
    private boolean _isDispVariationalWorkType = false;

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
    private String _baseDate = null;

    /**
     * 終了日
     */
    private String _endDate = null;


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
    private String _targetUserCode = "";

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
    private final String TMG_SCHEDULE_CMODIFIERPROGRAMID = "TmgSchedule_ACT_EditMonthly_USchedule";

    /**
     * プログラムID
     */
    private final String TMG_SCHEDULE_CPROGRAMID = "TmgSchedule_ACT_EditMonthly_USchedule";

    /**
     * アクションを表すコードです。参照画面.読み出し処理
     */
    private final String ACT_DISPMONTHLY_RSCHEDULE = "ACT_DispMonthly_RSchedule";

    /**
     * アクションを表すコードです。編集画面.読み出し処理
     */
    private final String ACT_EDITMONTHLY_RSCHEDULE = "ACT_EditMonthly_RSchedule";

    /**
     * アクションを表すコードです。編集画面.更新処理
     */
    public final String ACT_EDITMONTHLY_USCHEDULE = "ACT_EditMonthly_USchedule";

    /**
     * プログラムID
     */
    private final String TMG_SCHEDULE_CHECK_CPROGRAMID = "TmgScheduleCheck";


    /**
     * データ開始日
     */
    private final String Cs_MINDATE = "1900/01/01";

    /**
     * データ開始日
     */
    private final String Cs_MAXDATE = "2222/12/31";

    /**
     * 実績クリアの可否
     */
    private boolean bClearResult = false;

    /**
     * 4週間後
     */
    private final int PARAM_4WEEK_AFTER = 27;

    /**
     * 前月開始時間
     */
    private String preStart = "";

    /**
     * 　前月終了時間
     */
    private String preEnd = "";

    /**
     * 　翌月開始時間
     */
    private String nextStart = "";

    /**
     * 　翌月終了時間
     */
    private String nextEnd = "";

    /**
     *
     */
    private final String CS_ON = "TMG_ONOFF|1";

    /**
     *
     */
    private final String CS_OFF = "TMG_ONOFF|0";

    /**
     * マスタコード(予定確認)
     */
    private final String Cs_MGD_ITEMS_SCHEDULECHECK = "TMG_ITEMS|ScheduleCheck";

    private final String dutyKey = "TMG_ITEMS|PlanDuty";

    private final String restKey = "TMG_ITEMS|PlanRest";

    private final String planTypeKey = "CID";

    private final String planJSONKey = "JSON";
    private String _preFirstDayOfSysDate = "";

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
     * @return
     */
    public TargetUserDetailDTO selectTargetUserDetail() {

        String employeeId = psDBBean.getUserCode();
        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }
        TargetUserDetailDTO targetUserDetailDTO = iTmgScheduleService.selectTargetUserDetail(employeeId, _baseDate, psDBBean.getCustID(), psDBBean.getCompCode(), _startDispDate, psDBBean.getLanguage());
        return targetUserDetailDTO;

    }

    /**
     * PsDBBean初期化
     *
     * @param psDBBean
     */
    public void setExecuteParameters(PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
    }

    /**
     * 画面から入力された実行条件を判定し設定します。
     *
     * @param txtBaseDate 2020/03/15
     * @param txtEndDate  2020/04/11
     */


    public void setExecuteParameters(String txtBaseDate, String txtEndDate, PsDBBean psDBBean) {
        this.psDBBean = psDBBean;
        //変数初期化
        _baseDate = "";
        _endDateOf4Weeks = "";
        preStart = "";
        preEnd = "";
        nextStart = "";
        nextEnd = "";
        _thisMonthFirstDay = "";
        _thisMonthLastDay = "";
        _isVariationalWorkType = false;
        detailPeriod = "";
        _loginCustCode = "";
        _loginUserCode = "";
        _targetUserCode = "";
        _targetCompCode = "";
        _targetCustCode = "";
        _preFirstDayOfSysDate = "";

        logger.info("****");
        //先ずは、目標ユーザー、いないあれば、ログインユーザーを取得する
        if (null != psDBBean.getEmployeeCode()) {
            _targetUserCode = psDBBean.getEmployeeCode();
        } else {
            _targetUserCode = psDBBean.getTargetUser();
        }

        //_targetUserCode = psDBBean.getTargetUser() == null ?  referList.getTargetEmployee() : psDBBean.getTargetUser();
        //_targetUserCode = "C1000015";
        _loginCustCode = psDBBean.getUserCode();
        //WEBから基準時間を渡せれば
        if (null != txtBaseDate && !"".equals(txtBaseDate)) {
            _baseDate = txtBaseDate;
        }
        if (null != txtEndDate && !"".equals(txtEndDate)) {
            _endDateOf4Weeks = txtEndDate;
        } else {
            _endDateOf4Weeks = "";
        }
        this.setExecuteParameters();
    }

    /**
     * 汎用参照リンクオブジェクトを生成します。 _preMonthDate = システム年月日から1ヶ月前の月初のデータ_baseDate = 基準日
     * この条件でなくてはならない。_preMonthDate <= _baseDate
     * _baseDateが_preMonthDateより未来の場合は正の数値が返却される。
     */
    private void setTmgReferListOfBeforeProcess() {

        if (toDateFormat(_baseDate).after(toDateFormat(_preFirstDayOfSysDate))) {
            setReferList(_preFirstDayOfSysDate);
        } else {
            setReferList(_baseDate);
        }
    }

    /**
     * 日付形式「yyyy/mm/dd」のString型文字列をDate型にキャストします。
     *
     * @param strDate
     * @return
     */
    public Date toDateFormat(String strDate) {
        Date date = null;
        try {
            date = new SimpleDateFormat(FORMAT_DATE_TYPE1).parse(strDate);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return date;
    }

    /**
     * 画面から入力された実行条件を判定し設定します。
     */
    public void setExecuteParameters() {

        // 現在日付の1ヶ月前の初日
        _preFirstDayOfSysDate = this.getFirstDayOfMonth(getSysdate(), PARAM_PREV_MONTH);
        // 現在日付の月初日を取得
        _thisMonthFirstDay = this.getFirstDayOfMonth(getSysdate(), PARAM_THIS_MONTH);
        // 現在日付の月末日を取得
        _thisMonthLastDay = DateUtil.format(DateUtil.endOfMonth(CalendarUtil.calendar()).getTime(), "yyyy/MM/dd");
        // システム年月日の翌月１日時点
        if (_baseDate == null || _baseDate.length() == 0) {
            _baseDate = getNextFirstDayOfSysDate();
        }
        setTmgReferListOfBeforeProcess();

        this.setBasicUserInfo();


        // 社員が選択されている場合は基本労働制か変形労働制か判定する処理を実行する。
        if (isSelectedTargetUser()) {
            setVariationalWorkInfo(_baseDate);
        }
        // 4週間単位の変形労働制職員か？
        if (_isVariationalWorkType) {
            // 起算日を取得します
            detailPeriod = iTmgScheduleService.selectDetailPeriod(_targetUserCode, _targetCompCode, _targetCustCode);
            if (null != detailPeriod && !"".equals(detailPeriod)) {
                detailPeriod = DateUtil.format(DateUtil.parse(detailPeriod, Cs_FORMAT_DATE_TYPE2), Cs_FORMAT_DATE_TYPE1);
            }
            //検索対象年月日の開始日reset
            //変形労働制社員の初めて検索すれば、有効開始時間を取得
            if (_baseDate.equals(getNextFirstDayOfSysDate())) {
                _baseDate = iTmgScheduleService.selectBaseDateFor4Week(_targetCustCode, _targetCompCode, _targetUserCode, _thisMonthFirstDay, _thisMonthLastDay, PARAM_4WEEK_AFTER);
            }

        } else {
            // 新たなインタフェース対応
            preStart = this.selectLinkOfNextMonth(_baseDate, _targetUserCode);
            if (null != preStart && !"".equals(preStart)) {
                preEnd = DateUtil.format(DateUtil.endOfMonth(DateUtil.parse(preStart, "yyyy/MM/dd")), "yyyy/MM/dd");
            }
            HashMap<String, Object> nextResult = this.selectLinkOfPreMonth(_baseDate, _targetUserCode);
            if (null != nextResult) {
                nextStart = nextResult.get("PREMONTH").toString();
                nextEnd = nextResult.get("PREMONTHLASTDAY").toString();
            }
        }

        // ユーザー基本情報も再設定する。
        if (isSelectedTargetUser() && (!_isVariationalWorkType)) {
            // 当月の初日を取得する
            _baseDate = this.getFirstDayOfMonth(_baseDate, PARAM_THIS_MONTH);
            //setTmgReferListOfBeforeProcess();
            setBasicUserInfo();
        } else if (isSelectedTargetUser() && _isVariationalWorkType && _endDateOf4Weeks.equals("")) {
            initBaseDateOf4Weeks();
        }
        // 4週間単位の変形労働職員の場合、表示基準日が4週間区切りの日付かどうかを設定する。
        if (_isVariationalWorkType) {
            setDispVariationalWork();

            //baseDate初期化すると、4週間単位の変形労働職員の場合 前月と翌月の開始時間と終了時間を取得する
            HashMap<String, Object> preMonthResult = iTmgScheduleService.selectBaseDateOf4WeeksBeforeDay(_baseDate, detailPeriod, _targetCustCode, _targetCompCode, _targetUserCode);
            if (null != preMonthResult) {
                // 検索結果が存在するなら、基準日の値を更新する
                if (null != preMonthResult.get("PRE_START")) {
                    preStart = preMonthResult.get("PRE_START").toString();
                }
                if (null != preMonthResult.get("PRE_END")) {
                    preEnd = preMonthResult.get("PRE_END").toString();
                }
            }
            HashMap<String, Object> nextMonthResult = iTmgScheduleService.SelectBaseDateOf4WeeksAfterDay(_baseDate, detailPeriod, _targetCustCode, _targetCompCode, _targetUserCode);
            if (null != nextMonthResult) {
                // 検索結果が存在するなら、基準日の値を更新する
                if (null != nextMonthResult.get("NEXT_START")) {
                    nextStart = nextMonthResult.get("NEXT_START").toString();
                }
                if (null != nextMonthResult.get("NEXT_END")) {
                    nextEnd = nextMonthResult.get("NEXT_END").toString();
                }
            }
        }

        //終了日を取得する
        _endDate = getDateEndOfSchedule(_isVariationalWorkType, _baseDate);

        // 表示開始?終了日セット
        setDispDate();

    }

    /**
     * システム年月日の翌月１日時点を取得します。
     * <p>
     * システム年月日の1ヶ月後1日を参照します。<br>
     * [例] "2007/03/23" --> "2007/04/01"
     * </p>
     *
     * @return システム年月日の翌月１日時点
     */
    private String getNextFirstDayOfSysDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TYPE1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 基準日が表示基準日が4週間区切りの日付と一致しているかを設定します。
     */
    private void setDispVariationalWork() {
        String tmp = iTmgScheduleService.selectIsStart4weeks(_baseDate, detailPeriod);
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
        HashMap<String, Object> results1 = iTmgScheduleService.SelectBaseDateOf4WeeksAfterDay(_baseDate, detailPeriod, _targetCustCode, _targetCompCode, _targetUserCode);
        if (null == results1) {
            HashMap<String, Object> results2 = iTmgScheduleService.selectBaseDateOf4WeeksBeforeDay(_baseDate, detailPeriod, _targetCustCode, _targetCompCode, _targetUserCode);
            if (null == results2) {
                // 検索結果が無い場合は何もしない(基準日の丸め処理を行わない)
                // 2007/12/27 J.Okamoto #309 検索結果がなかった場合、終了日には基準日の月末を設定します。
                _endDateOf4Weeks = getActualMaximumOfTypeStringOfDate(_baseDate);
            } else {
                // 検索結果が存在するなら、基準日の値を更新する
                if (null != results2.get("PRE_START")) {
                    _baseDate = results2.get("PRE_START").toString();
                }
                if (null != results2.get("PRE_END")) {
                    _endDateOf4Weeks = results2.get("PRE_END").toString();
                }
            }
        } else {
            // 基準日以降で、且つ組織ツリーの基準日が運用日の場合または組織ツリーの基準日が最も近い4週間区切りの日付の開始日の場合、取得できていれば何もしない
            // 　①組織ツリーの基準日が運用日の場合：表示対象が運用日の次の期間を表示する為。
            // 　②組織ツリーの基準日が最も近い4週間区切りの日付の開始日の場合：①だけだと、組織ツリーの基準日が最も近い4週間区切りの日付の開始日の場合に前期間を表示してしまうので。
            if (null != results1.get("NEXT_END")) {
                _endDateOf4Weeks = results1.get("NEXT_END").toString();
            }
        }
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
            //target user
            if (null == _targetUserCode || "".equals(_targetUserCode)) {
                if (null != psDBBean.getTargetUser() && !"".equals(psDBBean.getTargetUser())) {
                    _targetUserCode = psDBBean.getTargetUser();
                }
            }

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
                if (null != psDBBean.getTargetUser() && !"".equals(psDBBean.getTargetUser()) && null == _targetUserCode) {
                    _targetUserCode = psDBBean.getTargetUser();
                }
            } else {
                if (referList == null) {
                    return;
                } else {
                    // 対象ユーザー
                    if (null != referList.getTargetEmployee() && !"".equals(referList.getTargetEmployee()) && null == _targetUserCode) {
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
    private boolean isSelectedTargetUser() {

        if (_targetUserCode != null && _targetUserCode.length() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 汎用リンクコンポーネントを生成します。
     */
    private void setReferList(String pBaseDate) {
        try {
            referList = new TmgReferList(psDBBean, "TmgSchedule", pBaseDate,
                    TmgReferList.TREEVIEW_TYPE_EMP, true, true, false, false,
                    true);
        } catch (Exception e) {
            logger.error("汎用リンクコンポーネントを生成することは失敗しました", e);
        }
    }


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
        System.out.println("764-->" + _startDispDate);
        // 表示終了日
        _endDispDate = _endDate;

        if (_isVariationalWorkType || _baseDate == null) {
            // 基準日が初期化されていない場合は何もしない
            return;
        }

        // 週単位でチェックする人は表示開始終了日を取得
        HashMap<String, Object> results0 = this.selectDsipDate(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
        if (null != results0) {
            System.out.println("results0  size:" + results0.size());
        } else {
            System.out.println("results0 is empty");
        }
        if (null != results0) {
            // 前月シート存在確認
            // HashMap<String, Object> results1 = this.selectLinkOfPreMonth(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
            String preMonth = this.selectLinkOfNextMonth(_baseDate, _targetUserCode);
            if (null != preMonth) {
                _startDispDate = results0.get("START_DATE").toString();
                System.out.println("781-->" + _startDispDate);
            }
            // 翌月シート存在確認
            HashMap<String, Object> results2 = this.selectLinkOfNextMonthNextSaturday(_targetUserCode, _baseDate, _targetCustCode, _targetCompCode);
            if (null != results2) {
                _endDispDate = results0.get("END_DATE").toString();
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
        List<ScheduleDataDTO> scheduleDataDTOS = iTmgScheduleService.selectSchedule(NOTWORKINGID_PLAN_REST, _baseDate, _endDate, _isVariationalWorkType, Cs_MGD_MANAGEFLG_0, employeeId, _targetCompCode, _targetCustCode, _loginLanguageCode);
        return scheduleDataDTOS;
    }


    /**
     * 公休日数	基準日数	基準時間	年次休暇残 info
     *
     * @param startDispDate
     * @param endDispDate
     * @return
     */
    public ScheduleInfoVO selectPaidHolidayInfo(String startDispDate, String endDispDate) {

     /*   if (null != startDispDate && !"".equals(startDispDate)) {
            _startDispDate = startDispDate;
        }
        if (null != endDispDate && !"".equals(endDispDate)) {
            _endDispDate = endDispDate;
        }*/

        String employeeId = _targetUserCode;

        ScheduleInfoVO scheduleInfoVO = new ScheduleInfoVO();
        PaidHolidayVO paidHolidayVO = new PaidHolidayVO();
        System.out.println("*******" + _startDispDate + "~" + _endDispDate);
        //eg: 2020年3月15日～2020年4月11日
        String period = "";
        if (null != _startDispDate && !"".equals(_startDispDate)) {
            period = DateUtil.format(DateUtil.parse(_startDispDate), "yyyy年MM月dd日").toString();
            if (null != _endDispDate && !"".equals(_endDispDate)) {
                period += "～" + DateUtil.format(DateUtil.parse(_endDispDate), "yyyy年MM月dd日").toString();
            }
        }
        scheduleInfoVO.setPreEnd(preEnd);
        scheduleInfoVO.setPreStart(preStart);
        scheduleInfoVO.setNextStart(nextStart);
        scheduleInfoVO.setNextEnd(nextEnd);
        scheduleInfoVO.setPeriod(period);
        List<ScheduleDataDTO> scheduleDataDTOS = iTmgScheduleService.selectSchedule(NOTWORKINGID_PLAN_REST, _startDispDate, _endDispDate, _isVariationalWorkType, Cs_MGD_MANAGEFLG_0, employeeId, _targetCompCode, _targetCustCode, _loginLanguageCode);
        // Arrayにデータフォーマッを変える
        for (int i = 0; i < scheduleDataDTOS.size(); i++) {
            ScheduleDataDTO scheduleDataDTO = scheduleDataDTOS.get(i);
            if (null != scheduleDataDTO.getTimerange() && !"".equals(scheduleDataDTO.getTimerange())) {
                scheduleDataDTO.setTimerange_arr(JSONUtil.parseArray(scheduleDataDTO.getTimerange()).toArray());
            }
            if (null != scheduleDataDTO.getJson() && !"".equals(scheduleDataDTO.getJson())) {
                scheduleDataDTO.setJson_arr(JSONUtil.parseArray(scheduleDataDTO.getJson()).toArray());
            }
        }
        NpaidRestDTO npaidRestDTO = iTmgScheduleService.selectTmgMonthly(employeeId, _startDispDate, _targetCompCode, _targetCustCode);

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
            paidHolidayVO.setDateOfRecordDays("0");
        } else {
            paidHolidayVO.setDateOfRecordDays(String.valueOf(weekday));
        }
        if (holiday == 0) {
            paidHolidayVO.setNationalHolidayDays("0");
        } else {
            paidHolidayVO.setNationalHolidayDays(String.valueOf(holiday));
        }
        String npaidRestDaysHour = "0.0日 0時間 0分";
        if (null != npaidRestDTO) {
            //年次休暇残
            npaidRestDaysHour = npaidRestDTO.getTmo_npaid_rest_days() == null ? "0.0" : Float.valueOf(npaidRestDTO.getTmo_npaid_rest_days()) + "日 ";
            int hour = 0;
            int min = 0;
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
            npaidRestDaysHour += hour + "時間 ";
            npaidRestDaysHour += min + "分";
        }

        paidHolidayVO.setNpaidRestDaysHour(npaidRestDaysHour);

        //基準時間
        String workingHours = iTmgScheduleService.selectWorkingHours(employeeId, _baseDate, _targetCustCode, _targetCompCode);
        String dateOfRecord = this.calculateWorkingHourOfMonth(workingHours == null ? "0" : workingHours, paidHolidayVO.getDateOfRecordDays());
        paidHolidayVO.setDateOfRecord(dateOfRecord);


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

        // データ処理
        if ("0日".equals(baseDateCnt)) {
            baseDateCnt = "0";
        }
        if ("0日".equals(MIOfWeekDay)) {
            baseDateCnt = "0";
        }

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
     * @param baseDate
     * @param employeeId
     * @return
     */
    public String selectLinkOfNextMonth(String baseDate, String employeeId) {
        if (null == baseDate || "".equals(baseDate)) {
            baseDate = _baseDate;
        }
        String nextMonth = iTmgScheduleService.selectLinkOfNextMonth(employeeId, baseDate, _targetCustCode, _targetCompCode);
        return nextMonth;
    }

    /**
     * 前月リンクを取得
     *
     * @param baseDate
     * @param employeeId
     * @return
     */
    public HashMap<String, Object> selectLinkOfPreMonth(String baseDate, String employeeId) {
        if (null == baseDate || "".equals(baseDate)) {
            baseDate = _baseDate;
        }
        return iTmgScheduleService.selectLinkOfPreMonth(employeeId, baseDate, _targetCustCode, _targetCompCode);
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
    private List<HashMap<String, Object>> selectGenericDetail(String custId, String compId, String language, String baseDate) {
        return iTmgScheduleService.selectGenericDetail(language, baseDate, compId, custId);
    }

    /**
     * 勤務パターンテーブルより勤務パターンコンボボックスの選択値を取得します.(一括指定用)
     *
     * @param sectionid
     * @param groupid
     * @return
     */
    private List<HashMap<String, Object>> selectWorkPatternIkkatu(String sectionid, String groupid, String baseDate, String custId, String compId) {
        return iTmgScheduleService.selectWorkPatternIkkatu(compId, custId, sectionid, groupid, baseDate);
    }

    /**
     * [出張]汎用マスタより出張区分コンボボックスの選択値を取得します
     *
     * @return
     */
    private List<HashMap<String, Object>> selectBusinessTrip(String custId, String compId, String language, String baseDate) {
        return iTmgScheduleService.selectBusinessTrip(language, baseDate, compId, custId);
    }

    /**
     * [区分][出張][勤務パターン]
     *
     * @param baseDate
     * @return
     */
    public HashMap<String, Object> selectIkkaInfo(String baseDate, PsDBBean psDBBean) {
        if (null != psDBBean) {
            String groupid = psDBBean.getGroupID();
            String sectionid = psDBBean.getRequestHash().get("sectionid") == null ? "" : psDBBean.getRequestHash().get("sectionid").toString();
            String custId = psDBBean.getCustID();
            String compId = psDBBean.getCompCode();
            String language = psDBBean.getLanguage();
            return this.selectIkkaInfo(sectionid, groupid, baseDate, custId, compId, language);
        } else {
            logger.error("psDBBean 対象が空です");
            return null;
        }
    }

    /**
     * [区分][出張][勤務パターン]
     *
     * @return
     */
    public HashMap<String, Object> selectIkkaInfo(String sectionid, String groupid, String baseDate, String custId, String compId, String language) {
        if ("".equals(baseDate) || null == baseDate) {
            baseDate = getSysdate();
            logger.warn("baseDateが空です");
        }
        if ("".equals(custId) || null == custId) {
            logger.warn("custIdが空です");
            return null;
        }
        if ("".equals(compId) || null == compId) {
            logger.warn("compIdが空です");
            return null;
        }
        if ("".equals(language) || null == language) {
            logger.warn("languageが空です");
            return null;
        }
        if ("".equals(sectionid) || null == sectionid) {
            logger.warn("sectionidが空です");
            return null;
        }
        if ("".equals(groupid) || null == groupid) {
            logger.warn("groupIdが空です");
            groupid = "null";
        }

        //[区分]
        List<HashMap<String, Object>> kubunnList = this.selectGenericDetail(custId, compId, language, baseDate);
        //[出張]
        List<HashMap<String, Object>> syuccyouList = this.selectBusinessTrip(custId, compId, language, baseDate);
        // 勤務パターン
        List<HashMap<String, Object>> workPatternList = this.selectWorkPatternIkkatu(sectionid, groupid, baseDate, custId, compId);
        // データフォマードを変更する
        for (int i = 0; i < workPatternList.size(); i++) {
            HashMap<String, Object> hashMap = workPatternList.get(i);
            List<JSONObject> dutyArray = new ArrayList<JSONObject>();
            List<JSONObject> restArray = new ArrayList<JSONObject>();
            Map plan = new HashMap();
            if (null != hashMap.get(planJSONKey) && !"".equals(hashMap.get(planJSONKey))) {
                String json = hashMap.get(planJSONKey).toString();
                JSONArray jsonArray = JSONUtil.parseArray(json);
                for (int j = 0; j < jsonArray.size(); j++) {
                    JSONObject o = (JSONObject) jsonArray.get(j);
                    if (o.get(planTypeKey).equals(dutyKey)) {
                        dutyArray.add(o);
                    }
                    if (o.get(planTypeKey).equals(restKey)) {
                        restArray.add(o);
                    }
                }
            }
            plan.put("dutyArray", dutyArray);
            plan.put("restArray", restArray);
            hashMap.put(planJSONKey, plan);
        }
        HashMap<String, Object> results = new HashMap<String, Object>();
        results.put("kubunnList", kubunnList);
        results.put("syuccyouList", syuccyouList);
        results.put("workPatternList", workPatternList);
        return results;
    }


    /**
     * 実績クリアの可否を取得します
     */
    private void isClearResult() {
        String sPropnameResult = psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_RESULT);
        if (sPropnameResult != null && !sPropnameResult.equals("") && sPropnameResult.equals("yes")) {
            this.bClearResult = true;
        }
    }

    /**
     * 予定作成更新処理を行います。
     */
    public void executeEditMonthlyUSchedule(String content, PsDBBean psDBBean) {

        if (JSONUtil.isJsonObj(content)) {
            MonthlyUScheduleEditParaDTO monthlyUScheduleEditParaDTO = JSONUtil.parseObj(content).toBean(MonthlyUScheduleEditParaDTO.class);
            if (null != monthlyUScheduleEditParaDTO) {
                List<MonthlyScheduleEmpInfoDTO> monthlyScheduleEmpInfoDTOS = monthlyUScheduleEditParaDTO.getMonthlyScheduleEmpInfoDTOS();
                for (int i = 0; i < monthlyScheduleEmpInfoDTOS.size(); i++) {
                    MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO = monthlyScheduleEmpInfoDTOS.get(i);
                    monthlyScheduleEmpInfoDTO.setNopen(monthlyScheduleEmpInfoDTO.getNopen() == null ? "" : monthlyScheduleEmpInfoDTO.getNopen());
                    monthlyScheduleEmpInfoDTO.setNclose(monthlyScheduleEmpInfoDTO.getNclose() == null ? "" : monthlyScheduleEmpInfoDTO.getNclose());
                    monthlyScheduleEmpInfoDTO.setComment(monthlyScheduleEmpInfoDTO.getComment() == null ? "" : monthlyScheduleEmpInfoDTO.getComment());
                    monthlyScheduleEmpInfoDTO.setBussinessTripid(monthlyScheduleEmpInfoDTO.getBussinessTripid() == null ? "" : monthlyScheduleEmpInfoDTO.getBussinessTripid());
                    monthlyScheduleEmpInfoDTO.setWorkId(monthlyScheduleEmpInfoDTO.getWorkId() == null ? "" : monthlyScheduleEmpInfoDTO.getWorkId());
                }
                monthlyUScheduleEditParaDTO.setMonthlyScheduleEmpInfoDTOS(monthlyScheduleEmpInfoDTOS);
                this.isClearResult();
                this.executeEditMonthlyUSchedule(monthlyUScheduleEditParaDTO, psDBBean);
                this.triggerOper(monthlyUScheduleEditParaDTO, psDBBean);
            } else {
                logger.error("JSON対象がオブジェクトに変更することが失敗しました");
            }
        } else {
            logger.error("更新内容は標準的なjsonではない");
        }
    }


    /**
     * 予定作成更新処理を行います。
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void executeEditMonthlyUSchedule(MonthlyUScheduleEditParaDTO monthlyUScheduleEditParaDTO, PsDBBean psDBBean) {

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
        iTmgScheduleService.deleteDailyCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), psDBBean.getCompCode(), psDBBean.getCustID(), null);
        iTmgScheduleService.deleteDailyDetailCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), psDBBean.getCompCode(), psDBBean.getCustID(), null);

        for (int i = 0; i < monthlyScheduleEmpInfoDTOS.size(); i++) {
            MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO = monthlyScheduleEmpInfoDTOS.get(i);
            //日別情報を更新する(ロット処理)
            List<HashMap<String, String>> restTime = monthlyScheduleEmpInfoDTO.getRestList();
            String nRestOpen = "";
            String nRestClose = "";
            //対象日
            String sTargetDate = monthlyScheduleEmpInfoDTO.getDyyyymmdd();
            boolean bNoWorking = isNoWorkingId(monthlyScheduleEmpInfoDTO.getWorkId());
            //logger.info("nopen:" + monthlyScheduleEmpInfoDTO.getNopen() + " ---- nclose:" + monthlyScheduleEmpInfoDTO.getNclose());
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
                    logger.info("nRestOpen:" + nRestOpen + " ---- nRestClose:" + nRestClose);
                    iTmgScheduleService.insertTmgDailyDetailCheckRest(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getTargetUserId(),
                            Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_PLAN_REST, nRestOpen, nRestClose,
                            NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_PLAN_REST.equals(NOTWORKINGID_RESULT_REST));
                    // 就業区分が就業禁止(有給)、就業禁止(無給)以外の場合に申請と実績の休憩レコードを作成する。
                    if (!isNoWorkingId(monthlyScheduleEmpInfoDTO.getWorkId())) {
                        // TMG_DAILY_DETAIL 休憩時間レコード登録：申請
                        iTmgScheduleService.insertTmgDailyDetailCheckRest(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getTargetUserId(),
                                Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_NOTICE_REST, nRestOpen, nRestClose,
                                NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_NOTICE_REST.equals(NOTWORKINGID_RESULT_REST));

                        // TMG_DAILY_DETAIL 休憩時間レコード登録：実績
                        iTmgScheduleService.insertTmgDailyDetailCheckRest(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getTargetUserId(),
                                Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, sTargetDate, NOTWORKINGID_RESULT_REST, nRestOpen, nRestClose,
                                NOTWORKINGID_PLAN_REST, NOTWORKINGID_NOTICE_REST, NOTWORKINGID_RESULT_REST, bClearResult, NOTWORKINGID_RESULT_REST.equals(NOTWORKINGID_RESULT_REST));
                    }
                }
            }

        }

    }

    /**
     * 前のタスクが終わったら、このタスクを実行するしかない
     *
     * @param monthlyUScheduleEditParaDTO
     */
    @Synchronized
    @Transactional(rollbackFor = GlobalException.class)
    public void triggerOper(MonthlyUScheduleEditParaDTO monthlyUScheduleEditParaDTO, PsDBBean psDBBean) {
        if (null == monthlyUScheduleEditParaDTO) {
            logger.warn("更新データが空です");
            return;
        }
        List<MonthlyScheduleEmpInfoDTO> monthlyScheduleEmpInfoDTOS = monthlyUScheduleEditParaDTO.getMonthlyScheduleEmpInfoDTOS();
        if (null == monthlyScheduleEmpInfoDTOS) {
            logger.warn("予定データが空です");
            return;
        }
        for (int i = 0; i < monthlyScheduleEmpInfoDTOS.size(); i++) {
            MonthlyScheduleEmpInfoDTO monthlyScheduleEmpInfoDTO = monthlyScheduleEmpInfoDTOS.get(i);
            logger.info("--->targetDate:" + monthlyScheduleEmpInfoDTO.getDyyyymmdd());
            iTmgScheduleService.insertTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getTargetUserId(),
                    Cs_MINDATE, Cs_MAXDATE, monthlyUScheduleEditParaDTO.getLoginUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID, monthlyScheduleEmpInfoDTO.getDyyyymmdd(), ACT_EDITMONTHLY_USCHEDULE);
            iTmgScheduleService.deleteTmgTrigger(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getTargetUserId(), TMG_SCHEDULE_CMODIFIERPROGRAMID);
            iTmgScheduleService.deleteDailyCheck(monthlyUScheduleEditParaDTO.getLoginUserId(), psDBBean.getCompCode(), psDBBean.getCustID(), monthlyScheduleEmpInfoDTO.getDyyyymmdd());
            iTmgScheduleService.deleteDetailCheck(psDBBean.getCustID(), psDBBean.getCompCode(), monthlyUScheduleEditParaDTO.getLoginUserId(), monthlyScheduleEmpInfoDTO.getDyyyymmdd());
        }

    }


    /**
     * 指定された就業区分が就業禁止(有給)または就業禁止(無給)かどうかを判断します。
     *
     * @param sWorkingId
     * @return true : 就業禁止(有給)、就業禁止(無給)の場合　false：就業禁止(有給)、就業禁止(無給)以外の場合
     */
    private boolean isNoWorkingId(String sWorkingId) {

        if (TmgUtil.Cs_MGD_WORK_200.equals(sWorkingId) || TmgUtil.Cs_MGD_WORK_412.equals(sWorkingId)) {
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
        nDifference = new Long((nEnd - nStart) / (1000 * 60 * 60 * 24)).intValue();

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
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(String baseDate, PsDBBean psDBBean) {
        //フランドから要らない
        baseDate = "";
        if (null == baseDate || "".equals(baseDate)) {
            baseDate = DateUtil.nextMonth().toString("yyyy/MM") + "/01";
            logger.warn("週次勤務パターン-->baseDate パラメータが空です," + baseDate + "にリセットされた");
            //  return null;
        }

        // 本日
        String sysDate = DateUtil.now();

        // 現在日付の翌月(初期表示時の年月)を表示している場合のみ、
        // 適用開始日が未来のパターンが全て表示される。
        boolean isAfter = this.compareDate(sysDate, baseDate);

        List<TmgWeekPatternDTO> tmgWeekPatternDTOS = iTmgScheduleService.selectTmgWeekPattern(psDBBean.getEmployeeCode(), baseDate, psDBBean.getCustID(), psDBBean.getCompCode(), isAfter);
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
        if (null != dateStrA && !"".equals(dateStrA) && null != dateStrB && !"".equals(dateStrB)) {
            if (DateUtil.parse(dateStrA).after(DateUtil.parse(dateStrB))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 週勤務パターンを取得する
     *
     * @param twp_nid
     * @return
     */
    public TmgWeekPatternVO selectCsvReference(int twp_nid, PsDBBean psDBBean) {

        if (ObjectUtil.isNotNull(twp_nid)) {
            String employeeId = psDBBean.getEmployeeCode();
            if (null == employeeId || "".equals(employeeId)) {
                logger.error("目標ユーザーが空です");
                return null;
            }
            return iTmgScheduleService.selectCsvReference(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage(), employeeId, twp_nid);
        } else {
            logger.error("データidが空です");
        }
        return null;
    }

    /**
     * 週勤務パターンを取得する
     *
     * @return
     */
    public List<TmgWeekPatternVO> selectCsvReferenceList(int twp_nid, PsDBBean psDBBean) {
        String employeeId = psDBBean.getEmployeeCode();
        if (null == employeeId || "".equals(employeeId)) {
            logger.error("目標ユーザーが空です");
            return null;
        }
        return iTmgScheduleService.selectCsvReferenceList(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage(), employeeId, twp_nid);
    }

    private String workTypeConvert(String divisionId) {
        if (null != divisionId) {
            if ("TMG_WORK|000".equals(divisionId)) {
                return "0";
            } else if ("TMG_WORK|500".equals(divisionId)) {
                return "1";
            } else if ("TMG_WORK|570".equals(divisionId)) {
                return "3";
            } else if ("TMG_WORK|480".equals(divisionId)) {
                return "0";
            } else {
                return "";
            }
        } else {
            logger.warn("勤務区分が空です");
        }
        return "";
    }

    /**
     * @param workTypeName --> 出勤　　法定休　　所定休　　勤務を要しない日
     * @return
     */
    private String workTypeConvert2(String workTypeName) {
        String divisionId = "0";
        if (null != workTypeName) {
            if ("出勤".equals(workTypeName)) {
                divisionId = "TMG_WORK|000";
            } else if ("法定休".equals(workTypeName)) {
                divisionId = "TMG_WORK|500";
            } else if ("所定休".equals(workTypeName)) {
                divisionId = "TMG_WORK|570";
            } else if ("勤務を要しない日".equals(workTypeName)) {
                divisionId = "TMG_WORK|480";
            } else {
                return "";
            }
        } else {
            logger.warn("勤務区分が空です");
        }
        return this.workTypeConvert(divisionId);
    }

    /**
     * 　週勤務パターン　汎用　実行
     *
     * @param tmgWeekPatternCheckDTOList
     * @param _targetCustCode
     * @param _targetCompCode
     * @param _targetUserCode
     * @param _loginUserCode
     * @param _loginLanguageCode
     * @return
     */
    private GlobalResponse executeMakeWeekPattern(List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList, String _targetCustCode, String _targetCompCode, String _targetUserCode, String _loginUserCode, String _loginLanguageCode, String twp_nid) {
        // if (null != tmgWeekPatternCheckDTOList) {
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
            TmgWeekPatternCheckDTO twpcDto = tmgWeekPatternCheckDTOList.get(i);
            twpcDto.setTwp_cmodifierprogramid(modifierprogramid);
            if (twpcDto.isCheckFlag()) {
                //注意：　更新フラグ  TRUEの場合は登録    FALSEの場合は削除(不要)
                iTmgScheduleService.insertTmgWeekPatternCheck(twpcDto);
            }
        }

        //更新又は削除の場合、既存レコードは、もう含めていますので、インサート不要
        //つまり、登録時に、既存データがインサートするとことが必要です
        if (null == twp_nid || "".equals(twp_nid)) {
            //既存レコードをインサート
            iTmgScheduleService.buildSQLForSelectInsertTmgWeekPatternCheck(_targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, modifierprogramid, null, null, null);
        }

        //エラーメッセージに追加する
        iTmgScheduleService.insertErrMsg(_targetCustCode, _targetCompCode, _loginLanguageCode, _targetUserCode, _loginUserCode, modifierprogramid, Cs_MINDATE, Cs_MAXDATE);
        //エラーメッセージ取得
        String _errCode = "0";
        String errCode = iTmgScheduleService.selectErrMsg(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        if (!_errCode.equals(errCode)) {
            logger.error("週勤務パターン登録の際は、エラーが発生しました!");
            JSONObject jsonObject_err = JSONUtil.parseObj(errCode);
            Iterator it = jsonObject_err.values().iterator();
            String errMsg = "";
            while (it.hasNext()) {
                JSONArray value = (JSONArray) it.next();
                jsonObject_err = (JSONObject) value.get(0);
                errMsg = jsonObject_err.get("ERRMSG") == null ? "" : jsonObject_err.get("ERRMSG").toString();
            }
            return GlobalResponse.error(errMsg);
        }
        iTmgScheduleService.insertTrigger(_targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, modifierprogramid, actionParam);
        iTmgScheduleService.deleteTmgTrigger(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteErrMsg(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        iTmgScheduleService.deleteWeekPatternCheck(_targetCustCode, _targetCompCode, _loginUserCode, modifierprogramid);
        return GlobalResponse.ok();
        /*} else {
            logger.error("週勤務パターンリストが空です");
            return GlobalResponse.error("週勤務パターンリストが空です");
        }*/
    }


    /**
     * 週勤務パターンの適用時間を更新する
     *
     * @param twp_dstartdate
     * @param twp_denddate
     * @param twp_nid
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse executeMakeWeekPattern_UWPtn(String twp_dstartdate, String twp_denddate, String twp_nid, PsDBBean psDBBean) {
        if (null == twp_dstartdate || null == twp_denddate || null == twp_nid || "".equals(twp_dstartdate) || "".equals(twp_denddate) || "".equals(twp_nid)) {
            logger.error("パラメータが不正です");
            return GlobalResponse.error("パラメータが不正です");
        }
        String _targetCustCode = psDBBean.getCustID();
        String _targetCompCode = psDBBean.getCompCode();
        String _targetUserCode = psDBBean.getEmployeeCode();
        String _loginUserCode = psDBBean.getUserCode();
        String _loginLanguageCode = psDBBean.getLanguage();
        //先ずは、週勤務パターンを取得する
        List<TmgWeekPatternVO> tmgWeekPatternVOList = iTmgScheduleService.selectCsvReferenceList(_targetCustCode, _targetCompCode, _loginLanguageCode, _targetUserCode, 0);
        if (null != tmgWeekPatternVOList) {
            List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList = new ArrayList<TmgWeekPatternCheckDTO>();
            TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO = null;
            for (int i = 0; i < tmgWeekPatternVOList.size(); i++) {
                TmgWeekPatternVO tmgWeekPatternVO = tmgWeekPatternVOList.get(i);
                tmgWeekPatternCheckDTO = new TmgWeekPatternCheckDTO();
                if (tmgWeekPatternVO.getTwp_nid().equals(twp_nid)) {
                    //update
                    tmgWeekPatternCheckDTO.setTwp_dstartdate(twp_dstartdate);
                    tmgWeekPatternCheckDTO.setTwp_denddate(twp_denddate);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_dstartdate(tmgWeekPatternVO.getTwp_dstartdate());
                    tmgWeekPatternCheckDTO.setTwp_denddate(tmgWeekPatternVO.getTwp_denddate());
                }
                tmgWeekPatternCheckDTO.setTwp_cmodifieruserid(_loginUserCode);
                tmgWeekPatternCheckDTO.setPeriod(tmgWeekPatternCheckDTO.getTwp_dstartdate() + "-" + tmgWeekPatternCheckDTO.getTwp_denddate());
                tmgWeekPatternCheckDTO.setCheckFlag(true);
                tmgWeekPatternCheckDTO.setEmployeeId(_targetUserCode);
                tmgWeekPatternCheckDTO.setCompCode(_targetCompCode);
                tmgWeekPatternCheckDTO.setCustId(_targetCustCode);
                tmgWeekPatternCheckDTO.setTwp_copen1(tmgWeekPatternVO.getTwp_copen1() == null ? "" : tmgWeekPatternVO.getTwp_copen1());
                tmgWeekPatternCheckDTO.setTwp_cclose1(tmgWeekPatternVO.getTwp_cclose1() == null ? "" : tmgWeekPatternVO.getTwp_cclose1());
                if (null != tmgWeekPatternVO.getRest1() && !"".equals(tmgWeekPatternVO.getRest1().trim())) {
                    String rest = tmgWeekPatternVO.getRest1().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen1(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose1(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen1("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose1("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg1(this.workTypeConvert2(tmgWeekPatternVO.getWorkname1()));

                tmgWeekPatternCheckDTO.setTwp_copen2(tmgWeekPatternVO.getTwp_copen2() == null ? "" : tmgWeekPatternVO.getTwp_copen2());
                tmgWeekPatternCheckDTO.setTwp_cclose2(tmgWeekPatternVO.getTwp_cclose2() == null ? "" : tmgWeekPatternVO.getTwp_cclose2());
                if (null != tmgWeekPatternVO.getRest2() && !"".equals(tmgWeekPatternVO.getRest2().trim())) {
                    String rest = tmgWeekPatternVO.getRest2().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen2(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose2(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen2("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose2("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg2(this.workTypeConvert2(tmgWeekPatternVO.getWorkname2()));

                tmgWeekPatternCheckDTO.setTwp_copen3(tmgWeekPatternVO.getTwp_copen3() == null ? "" : tmgWeekPatternVO.getTwp_copen3());
                tmgWeekPatternCheckDTO.setTwp_cclose3(tmgWeekPatternVO.getTwp_cclose3() == null ? "" : tmgWeekPatternVO.getTwp_cclose3());
                if (null != tmgWeekPatternVO.getRest3() && !"".equals(tmgWeekPatternVO.getRest3().trim())) {
                    String rest = tmgWeekPatternVO.getRest3().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen3(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose3(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen3("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose3("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg3(this.workTypeConvert2(tmgWeekPatternVO.getWorkname3()));

                tmgWeekPatternCheckDTO.setTwp_copen4(tmgWeekPatternVO.getTwp_copen4() == null ? "" : tmgWeekPatternVO.getTwp_copen4());
                tmgWeekPatternCheckDTO.setTwp_cclose4(tmgWeekPatternVO.getTwp_cclose4() == null ? "" : tmgWeekPatternVO.getTwp_cclose4());
                if (null != tmgWeekPatternVO.getRest4() && !"".equals(tmgWeekPatternVO.getRest4().trim())) {
                    String rest = tmgWeekPatternVO.getRest4().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen4(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose4(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen4("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose4("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg4(this.workTypeConvert2(tmgWeekPatternVO.getWorkname4()));

                tmgWeekPatternCheckDTO.setTwp_copen5(tmgWeekPatternVO.getTwp_copen5() == null ? "" : tmgWeekPatternVO.getTwp_copen5());
                tmgWeekPatternCheckDTO.setTwp_cclose5(tmgWeekPatternVO.getTwp_cclose5() == null ? "" : tmgWeekPatternVO.getTwp_cclose5());
                if (null != tmgWeekPatternVO.getRest5() && !"".equals(tmgWeekPatternVO.getRest5().trim())) {
                    String rest = tmgWeekPatternVO.getRest5().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen5(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose5(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen5("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose5("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg5(this.workTypeConvert2(tmgWeekPatternVO.getWorkname5()));

                tmgWeekPatternCheckDTO.setTwp_copen6(tmgWeekPatternVO.getTwp_copen6() == null ? "" : tmgWeekPatternVO.getTwp_copen6());
                tmgWeekPatternCheckDTO.setTwp_cclose6(tmgWeekPatternVO.getTwp_cclose6() == null ? "" : tmgWeekPatternVO.getTwp_cclose6());
                if (null != tmgWeekPatternVO.getRest6() && !"".equals(tmgWeekPatternVO.getRest6().trim())) {
                    String rest = tmgWeekPatternVO.getRest6().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen6(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose6(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen6("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose6("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg6(this.workTypeConvert2(tmgWeekPatternVO.getWorkname6()));

                tmgWeekPatternCheckDTO.setTwp_copen7(tmgWeekPatternVO.getTwp_copen7() == null ? "" : tmgWeekPatternVO.getTwp_copen7());
                tmgWeekPatternCheckDTO.setTwp_cclose7(tmgWeekPatternVO.getTwp_cclose7() == null ? "" : tmgWeekPatternVO.getTwp_cclose7());
                if (null != tmgWeekPatternVO.getRest7() && !"".equals(tmgWeekPatternVO.getRest7().trim())) {
                    String rest = tmgWeekPatternVO.getRest7().replaceAll(" ", "");
                    tmgWeekPatternCheckDTO.setTwp_crestopen7(rest.split("-")[0]);
                    tmgWeekPatternCheckDTO.setTwp_crestclose7(rest.split("-")[1]);
                } else {
                    tmgWeekPatternCheckDTO.setTwp_crestopen7("");
                    tmgWeekPatternCheckDTO.setTwp_crestclose7("");
                }
                tmgWeekPatternCheckDTO.setTwp_cholflg7(this.workTypeConvert2(tmgWeekPatternVO.getWorkname7()));

                tmgWeekPatternCheckDTOList.add(tmgWeekPatternCheckDTO);
            }
            //トリガ　実行
            return this.executeMakeWeekPattern(tmgWeekPatternCheckDTOList, _targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, _loginLanguageCode, twp_nid);
           /* } else {
                logger.error("週勤務パターンを削除することが失敗しました");
                return GlobalResponse.error("週勤務パターンを削除することが失敗しました");
            }*/
        } else {
            logger.error("週勤務パターンを取得することが失敗しました");
            return GlobalResponse.error("週勤務パターンを取得することが失敗しました");
        }

    }


    /**
     * 週勤務パターン登録画面　登録処理
     *
     * @param content
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse executeMakeWeekPattern_UWPtn(String content, PsDBBean psDBBean) {
        boolean flag = true;
        GlobalResponse globalResponse = GlobalResponse.ok();
        List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList = new ArrayList<TmgWeekPatternCheckDTO>();
        String _targetCustCode = psDBBean.getCustID();
        String _targetCompCode = psDBBean.getCompCode();
        String _targetUserCode = psDBBean.getEmployeeCode();
        String _loginUserCode = psDBBean.getUserCode();
        String _loginLanguageCode = psDBBean.getLanguage();
        if (JSONUtil.isJsonObj(content)) {
            JSONObject jsonObject = JSONUtil.parseObj(content);
            if (null != JSONUtil.parseObj(content)) {
                WeekPatternInputDTO weekPatternInputDTO = jsonObject.toBean(WeekPatternInputDTO.class);
                List<WeekPatternFormDTO> weekPatternFormDTOList = null;
                if (null != weekPatternInputDTO) {
                    TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO = new TmgWeekPatternCheckDTO();
                    tmgWeekPatternCheckDTO.setCustId(_targetCustCode);
                    tmgWeekPatternCheckDTO.setCompCode(_targetCompCode);
                    tmgWeekPatternCheckDTO.setEmployeeId(_targetUserCode);
                    tmgWeekPatternCheckDTO.setTwp_cmodifieruserid(_loginUserCode);
                    tmgWeekPatternCheckDTO.setTwp_dstartdate(weekPatternInputDTO.getApplyStart());
                    tmgWeekPatternCheckDTO.setTwp_denddate(weekPatternInputDTO.getApplyEnd());
                    tmgWeekPatternCheckDTO.setCheckFlag(true);
                    tmgWeekPatternCheckDTO.setPeriod(weekPatternInputDTO.getApplyStart() + "-" + weekPatternInputDTO.getApplyEnd());
                    if (null != jsonObject.get("applyList")) {
                        weekPatternFormDTOList = JSONUtil.parseArray(jsonObject.get("applyList")).toList(WeekPatternFormDTO.class);
                        for (int i = 0; i < weekPatternFormDTOList.size(); i++) {
                            WeekPatternFormDTO weekPatternFormDTO = weekPatternFormDTOList.get(i);
                            if ("0".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen1(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose1(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen1(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose1(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg1(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("1".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen2(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose2(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen2(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose2(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg2(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("2".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen3(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose3(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen3(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose3(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg3(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("3".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen4(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose4(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen4(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose4(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg4(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("4".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen5(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose5(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen5(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose5(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg5(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("5".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen6(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose6(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen6(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose6(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg6(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else if ("6".equals(weekPatternFormDTO.getWorkFlag())) {
                                tmgWeekPatternCheckDTO.setTwp_copen7(weekPatternFormDTO.getStartTime() == null ? "" : weekPatternFormDTO.getStartTime());
                                tmgWeekPatternCheckDTO.setTwp_cclose7(weekPatternFormDTO.getEndTime() == null ? "" : weekPatternFormDTO.getEndTime());
                                tmgWeekPatternCheckDTO.setTwp_crestopen7(weekPatternFormDTO.getRestStartTime() == null ? "" : weekPatternFormDTO.getRestStartTime());
                                tmgWeekPatternCheckDTO.setTwp_crestclose7(weekPatternFormDTO.getRestEndTime() == null ? "" : weekPatternFormDTO.getRestEndTime());
                                tmgWeekPatternCheckDTO.setTwp_cholflg7(workTypeConvert(weekPatternFormDTO.getWorkDivisionId()));
                            } else {
                                logger.warn("データが認識できません");
                            }
                        }
                        tmgWeekPatternCheckDTOList.add(tmgWeekPatternCheckDTO);

                        //トリガ　実行
                        return this.executeMakeWeekPattern(tmgWeekPatternCheckDTOList, _targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, _loginLanguageCode, null);
                    } else {
                        flag = false;
                        globalResponse = GlobalResponse.error("週勤務パターンリストを取得失敗しました");
                        logger.error("週勤務パターンリストを取得失敗しました");
                        return globalResponse;
                    }

                } else {
                    flag = false;
                    globalResponse = GlobalResponse.error("JSON対象がオブジェクトに変更することが失敗しました");
                    logger.error("JSON対象がオブジェクトに変更することが失敗しました");
                    return globalResponse;
                }


            }
        } else {
            flag = false;
            globalResponse = GlobalResponse.error("JSON対象ではありません");
            logger.error("JSON対象ではありません");
        }
        return globalResponse;
    }


    /**
     * TmgMonthlyの更新日取得(予定確認画面)
     *
     * @return
     */
    public String selectMonthlyModifiedDate() {

        String compCode = psDBBean.getCompCode();
        if (null == _loginCustCode || "".equals(_loginCustCode)) {
            logger.error("顧客コードが空です");
            return null;
        }
        if (null == compCode || "".equals(compCode)) {
            logger.error("会社コードが空です");
            return null;
        }
        if (null == _loginUserCode || "".equals(_loginUserCode)) {
            logger.error("ログインユーザーが空です");
            return null;
        }
        if (null == _baseDate || "".equals(_baseDate)) {
            logger.error("基準時間が空です");
            return null;
        }

        String modifiedDate = iTmgScheduleService.selectMonthlyModifiedDate(_loginCustCode, compCode, _loginUserCode, _baseDate);
        if (null != modifiedDate && !"".equals(modifiedDate)) {
            modifiedDate = DateUtil.format(DateUtil.parse(modifiedDate, Cs_FORMAT_DATE_TYPE1), Cs_FORMAT_DATE_TYPE3);
        } else {
            logger.warn("予定データの更新日が未取得です");
        }

        return modifiedDate;
    }

    /**
     * 予定確認フラグのレコードを挿入します。
     *
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean updateSchedulePermStatus() {
        String compCode = psDBBean.getCompCode();
        if (null == _loginCustCode || "".equals(_loginCustCode)) {
            logger.error("顧客コードが空です");
            return false;
        }
        if (null == compCode || "".equals(compCode)) {
            logger.error("会社コードが空です");
            return false;
        }
        if (null == _loginUserCode || "".equals(_loginUserCode)) {
            logger.error("ログインユーザーが空です");
            return false;
        }
        if (null == _baseDate || "".equals(_baseDate)) {
            logger.error("基準時間が空です");
            return false;
        }

        /**
         * 確認ボタンのフラグレコード更新処理（レコードは夜間処理で作成されます）
         */
        iTmgScheduleService.updateSchedulePermStatus(_loginCustCode, compCode, _loginUserCode, _baseDate, TMG_SCHEDULE_CHECK_CPROGRAMID, CS_ON, Cs_MGD_ITEMS_SCHEDULECHECK);

        return true;
    }

    /**
     * http://localhost:6879/sys/schedule/selectWeekPtn
     * 週勤務パターン画面に勤務区分リスト
     *
     * @return
     */
    public List<WeekWorkType> selectWeekPtn(PsDBBean psDBBean) {
        if (null == psDBBean) {
            logger.error("週勤務パターン画面に勤務区分リスト -->PsDBBean が空です");
            return null;
        }
        return iTmgScheduleService.selectWeekPtn(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage());

    }


    /**
     * 週勤務パターン　を削除する
     */
    public boolean deleteWeekPtn_bak(String twp_nid, PsDBBean psDBBean) {
        if (null == psDBBean) {
            logger.error("週勤務パターン　を削除する -->PsDBBean が空です");
            return false;
        }
        if (null != twp_nid && !"".equals(twp_nid)) {
            iTmgScheduleService.deleteWeekPtn(psDBBean.getCustID(), psDBBean.getCompCode(), twp_nid);
            return true;
        } else {
            logger.error("週勤務パターンコードが空です");
            return false;
        }
    }


    /**
     * 週勤務パターン　を削除する
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean deleteWeekPtn(String twp_nid, PsDBBean psDBBean) {
        if (null == psDBBean) {
            logger.error("週勤務パターン　を削除する -->PsDBBean が空です");
            return false;
        }
        if (null != twp_nid && !"".equals(twp_nid)) {
            String _targetCustCode = psDBBean.getCustID();
            String _targetCompCode = psDBBean.getCompCode();
            String _targetUserCode = psDBBean.getEmployeeCode();
            String _loginUserCode = psDBBean.getUserCode();
            String _loginLanguageCode = psDBBean.getLanguage();
            //先ずは、週勤務パターンを取得する
            List<TmgWeekPatternVO> tmgWeekPatternVOList = iTmgScheduleService.selectCsvReferenceList(_targetCustCode, _targetCompCode, _loginLanguageCode, _targetUserCode, Integer.parseInt(twp_nid));
            if (null != tmgWeekPatternVOList) {
                List<TmgWeekPatternCheckDTO> tmgWeekPatternCheckDTOList = new ArrayList<TmgWeekPatternCheckDTO>();
                TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO = null;
                for (int i = 0; i < tmgWeekPatternVOList.size(); i++) {
                    TmgWeekPatternVO tmgWeekPatternVO = tmgWeekPatternVOList.get(i);
                    tmgWeekPatternCheckDTO = new TmgWeekPatternCheckDTO();
                    tmgWeekPatternCheckDTO.setTwp_dstartdate(tmgWeekPatternVO.getTwp_dstartdate());
                    tmgWeekPatternCheckDTO.setTwp_denddate(tmgWeekPatternVO.getTwp_denddate());
                    tmgWeekPatternCheckDTO.setPeriod(tmgWeekPatternVO.getTwp_dstartdate() + "-" + tmgWeekPatternVO.getTwp_denddate());
                    tmgWeekPatternCheckDTO.setTwp_cmodifieruserid(_loginUserCode);
                    tmgWeekPatternCheckDTO.setCheckFlag(true);
                    tmgWeekPatternCheckDTO.setEmployeeId(_targetUserCode);
                    tmgWeekPatternCheckDTO.setCompCode(_targetCompCode);
                    tmgWeekPatternCheckDTO.setCustId(_targetCustCode);
                    tmgWeekPatternCheckDTO.setTwp_copen1(tmgWeekPatternVO.getTwp_copen1() == null ? "" : tmgWeekPatternVO.getTwp_copen1());
                    tmgWeekPatternCheckDTO.setTwp_cclose1(tmgWeekPatternVO.getTwp_cclose1() == null ? "" : tmgWeekPatternVO.getTwp_cclose1());
                    if (null != tmgWeekPatternVO.getRest1() && !"".equals(tmgWeekPatternVO.getRest1().trim())) {
                        String rest = tmgWeekPatternVO.getRest1().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen1(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose1(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen1("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose1("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg1(this.workTypeConvert2(tmgWeekPatternVO.getWorkname1()));

                    tmgWeekPatternCheckDTO.setTwp_copen2(tmgWeekPatternVO.getTwp_copen2() == null ? "" : tmgWeekPatternVO.getTwp_copen2());
                    tmgWeekPatternCheckDTO.setTwp_cclose2(tmgWeekPatternVO.getTwp_cclose2() == null ? "" : tmgWeekPatternVO.getTwp_cclose2());
                    if (null != tmgWeekPatternVO.getRest2() && !"".equals(tmgWeekPatternVO.getRest2().trim())) {
                        String rest = tmgWeekPatternVO.getRest2().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen2(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose2(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen2("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose2("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg2(this.workTypeConvert2(tmgWeekPatternVO.getWorkname2()));

                    tmgWeekPatternCheckDTO.setTwp_copen3(tmgWeekPatternVO.getTwp_copen3() == null ? "" : tmgWeekPatternVO.getTwp_copen3());
                    tmgWeekPatternCheckDTO.setTwp_cclose3(tmgWeekPatternVO.getTwp_cclose3() == null ? "" : tmgWeekPatternVO.getTwp_cclose3());
                    if (null != tmgWeekPatternVO.getRest3() && !"".equals(tmgWeekPatternVO.getRest3().trim())) {
                        String rest = tmgWeekPatternVO.getRest3().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen3(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose3(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen3("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose3("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg3(this.workTypeConvert2(tmgWeekPatternVO.getWorkname3()));

                    tmgWeekPatternCheckDTO.setTwp_copen4(tmgWeekPatternVO.getTwp_copen4() == null ? "" : tmgWeekPatternVO.getTwp_copen4());
                    tmgWeekPatternCheckDTO.setTwp_cclose4(tmgWeekPatternVO.getTwp_cclose4() == null ? "" : tmgWeekPatternVO.getTwp_cclose4());
                    if (null != tmgWeekPatternVO.getRest4() && !"".equals(tmgWeekPatternVO.getRest4().trim())) {
                        String rest = tmgWeekPatternVO.getRest4().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen4(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose4(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen4("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose4("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg4(this.workTypeConvert2(tmgWeekPatternVO.getWorkname4()));

                    tmgWeekPatternCheckDTO.setTwp_copen5(tmgWeekPatternVO.getTwp_copen5() == null ? "" : tmgWeekPatternVO.getTwp_copen5());
                    tmgWeekPatternCheckDTO.setTwp_cclose5(tmgWeekPatternVO.getTwp_cclose5() == null ? "" : tmgWeekPatternVO.getTwp_cclose5());
                    if (null != tmgWeekPatternVO.getRest5() && !"".equals(tmgWeekPatternVO.getRest5().trim())) {
                        String rest = tmgWeekPatternVO.getRest5().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen5(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose5(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen5("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose5("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg5(this.workTypeConvert2(tmgWeekPatternVO.getWorkname5()));

                    tmgWeekPatternCheckDTO.setTwp_copen6(tmgWeekPatternVO.getTwp_copen6() == null ? "" : tmgWeekPatternVO.getTwp_copen6());
                    tmgWeekPatternCheckDTO.setTwp_cclose6(tmgWeekPatternVO.getTwp_cclose6() == null ? "" : tmgWeekPatternVO.getTwp_cclose6());
                    if (null != tmgWeekPatternVO.getRest6() && !"".equals(tmgWeekPatternVO.getRest6().trim())) {
                        String rest = tmgWeekPatternVO.getRest6().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen6(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose6(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen6("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose6("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg6(this.workTypeConvert2(tmgWeekPatternVO.getWorkname6()));

                    tmgWeekPatternCheckDTO.setTwp_copen7(tmgWeekPatternVO.getTwp_copen7() == null ? "" : tmgWeekPatternVO.getTwp_copen7());
                    tmgWeekPatternCheckDTO.setTwp_cclose7(tmgWeekPatternVO.getTwp_cclose7() == null ? "" : tmgWeekPatternVO.getTwp_cclose7());
                    if (null != tmgWeekPatternVO.getRest7() && !"".equals(tmgWeekPatternVO.getRest7().trim())) {
                        String rest = tmgWeekPatternVO.getRest7().replaceAll(" ", "");
                        tmgWeekPatternCheckDTO.setTwp_crestopen7(rest.split("-")[0]);
                        tmgWeekPatternCheckDTO.setTwp_crestclose7(rest.split("-")[1]);
                    } else {
                        tmgWeekPatternCheckDTO.setTwp_crestopen7("");
                        tmgWeekPatternCheckDTO.setTwp_crestclose7("");
                    }
                    tmgWeekPatternCheckDTO.setTwp_cholflg7(this.workTypeConvert2(tmgWeekPatternVO.getWorkname7()));
                    tmgWeekPatternCheckDTOList.add(tmgWeekPatternCheckDTO);
                }
                //トリガ　実行
                this.executeMakeWeekPattern(tmgWeekPatternCheckDTOList, _targetCustCode, _targetCompCode, _targetUserCode, _loginUserCode, _loginLanguageCode, twp_nid);

            } else {
                logger.error("週勤務パターンを取得することが失敗しました");
                //return GlobalResponse.error("週勤務パターンを取得することが失敗しました");
                return false;
            }
            return true;
        } else {
            logger.error("週勤務パターンコードが空です");
            return false;
        }
    }


}