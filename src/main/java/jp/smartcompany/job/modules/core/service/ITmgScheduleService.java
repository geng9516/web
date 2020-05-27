package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;

import java.util.HashMap;

/**
 * @author 陳毅力
 * @description 予定作成
 * @date 2020/05/25
 **/
public interface ITmgScheduleService extends IService<Object> {

    /**
     * 年次休暇残
     *
     * @param employeeId
     * @param workYear
     * @param compCode
     * @param custId
     * @return
     */
    NpaidRestDTO selectTmgMonthly(String employeeId, String workYear, String compCode, String custId);

    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param ctpye_plan
     * @param startDispDate
     * @param endDispDate
     * @param dStart
     * @param dEnd
     * @param manageflg
     * @param employeeId
     * @param compCode
     * @param custId
     * @param language
     * @return
     */
    ScheduleDataDTO selectSchedule(String ctpye_plan, String startDispDate, String endDispDate, String dStart, String dEnd, String manageflg, String employeeId, String compCode, String custId, String language);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param baseDate
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(String baseDate, String custId, String compCode, String employeeId);

    /**
     * 翌月リンクを取得
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    String selectLinkOfNextMonth(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 前月リンクを取得
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    HashMap<String, Object> selectLinkOfPreMonth(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 対象ユーザー情報
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @param startDispDate
     * @param language
     * @return
     */
    TargetUserDetailDTO selectTargetUserDetail(String employeeId, String baseDate, String custId, String compCode, String startDispDate, String language);

    /**
     * 平日時の勤務時間
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    String selectWorkingHours(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 今月の月中有給付与の情報
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    HalfwayPaidHolidayDTO selectHalfwayPaidHoliday(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 週勤務パターン
     *
     * @param employeeId
     * @param baseDate
     * @param custId
     * @param compCode
     * @return
     */
    TmgWeekPatternDTO selectTmgWeekPattern(String employeeId, String baseDate, String custId, String compCode);

    /**
     * 基準日時点で４週間の変形労働制対象者か
     *
     * @param employeeId
     * @param targetDate
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    int selectVariationalWorkInfo(String employeeId, String targetDate, String custId, String compCode, String language);

    /**
     * 基準日の月に４週間の変形労働制の期間が存在するか
     *
     * @param employeeId
     * @param targetDate
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    int selectVariationalWorkDays(String employeeId, String targetDate, String custId, String compCode, String language);

}
