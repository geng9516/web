package jp.smartcompany.job.modules.tmg.schedule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.TargetUserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;


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
     * 日付形式1
     */
    protected static final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";
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
    protected String _loginLanguageCode = null;

    /**
     * 対象者が4週間の変形労働制対象者か検索しフラグ値を設定します
     *
     * @param baseDate
     * @param employeeId
     */
    private void setVariationalWorkInfo(String baseDate, String employeeId) {
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        String language = psDBBean.getLanguage();

        int tmp1 = iTmgScheduleService.selectVariationalWorkInfo(employeeId, baseDate, custId, compCode, language);
        int tmp2 = iTmgScheduleService.selectVariationalWorkDays(employeeId, baseDate, custId, compCode, language);

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

        //初期化
        this.setExecuteParameters(employeeId, year, month);

        TargetUserDetailDTO targetUserDetailDTO = iTmgScheduleService.selectTargetUserDetail(employeeId, _baseDate, _targetCustCode, _targetCompCode, _startDispDate, _loginLanguageCode);

        return targetUserDetailDTO;

    }

    /**
     * 画面から入力された実行条件を判定し設定します。
     *
     * @param year
     * @param month
     * @param employeeId
     */
    private void setExecuteParameters(String year, String month, String employeeId) {
        if (ObjectUtil.isNull(year) || ObjectUtil.isEmpty(year)) {
            year = DateUtil.thisYear() + "";
        }
        if (ObjectUtil.isNull(month) || ObjectUtil.isEmpty(month)) {
            month = DateUtil.thisMonth() + "";
        }
        _baseDate = year + "/" + month + "/" + "01";
        this.setVariationalWorkInfo(_baseDate, employeeId);
        _endDate = getDateEndOfSchedule(_isVariationalWorkType, _baseDate);

        _loginCustCode = psDBBean.getCustID();
        _targetCompCode = psDBBean.getCompCode();
        _loginLanguageCode = psDBBean.getLanguage();
        _targetCompCode = psDBBean.getTargetComp();
        _targetCustCode = psDBBean.getTargetCust();

    }

    /**
     * 対象社員の勤務形態に対応する終了日を返します。
     *
     * @param isVariationalWorkType
     * @param baseDate
     * @return 変形労働制対象者の場合は４週間後の年月日を返します。 そうでなければ基準日の年月末日を返します。
     */
    private String getDateEndOfSchedule(boolean isVariationalWorkType,
                                        String baseDate) {

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
    private void setDispDate(String employeeId, String baseDate) {

        // 表示開始日
        _startDispDate = _baseDate;
        // 表示終了日
        _endDispDate = _endDate;

        if (_isVariationalWorkType || _baseDate == null) {
            // 基準日が初期化されていない場合は何もしない
            return;
        }

        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();

        HashMap<String, Object> results0 = this.selectDsipDate(employeeId, baseDate, custId, compCode);
        if (results0.size() > 0) {
            HashMap<String, Object> results1 = this.selectLinkOfPreMonth(employeeId, baseDate, custId, compCode);
            if (results1.size() > 0) {
                _startDispDate = results1.get("premonth").toString();
            }
            HashMap<String, Object> results2 = this.selectLinkOfNextMonthNextSaturday(employeeId, baseDate, custId, compCode);
            if (results2.size() > 0) {
                _endDispDate = results1.get("premonth_lastday").toString();
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


}
