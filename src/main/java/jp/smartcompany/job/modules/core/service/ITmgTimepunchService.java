package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * [勤怠]打刻
 */
public interface ITmgTimepunchService extends IService<TmgTimepunchDO> {

    /**
     * 打刻時に打刻データ(未反映)情報に登録する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param modifierprogramid
     * @param ctpTypeid
     */
    void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid, String ctpTypeid);

    /**
     * TMG_TRIGGERへINSERTする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param modifierprogramid
     */
    void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid);

    /**
     * TMG_TRIGGERへDELETEする
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param modifierprogramid
     */
    void deleteTmgTrgger(String custId, String compCode, String employeeId, String modifierprogramid);

    /**
     * 打刻画面表示判断
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param targetDate
     * @return
     */
    String selectIsTimePunchTarget(String custId, String compCode, String employeeId, String targetDate);


    /**
     * 本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻を取得します
     *
     * @param custId
     * @param compCode
     * @return
     */
    BaseTimesDTO selectBaseTimes(String custId, String compCode);

    /**
     * 打刻更新先となる日と今日の日付を取得するクエリ
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    String selectBaseTimesWithPattern(String custId, String compCode, String employeeId);

    /**
     * 出勤日数と時間数を取得
     *
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    List<DutyDaysAndHoursDTO> selectDutyDaysAndHoursSQL(String custId, String compCode, String language);

    /**
     * 出勤日数と時間数を取得
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param targetDate
     * @param dutyDaysAndHoursDTOList
     * @return
     */
    HashMap<String, Object> selectDutyDaysAndHours(String custId, String compCode, String employeeId, String targetDate, List<DutyDaysAndHoursDTO> dutyDaysAndHoursDTOList);

    /**
     * 超過勤務時間
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param startDate
     * @param endDate
     * @return
     */
    String selectOverTime(String custId, String compCode, String employeeId, String startDate, String endDate);

    /**
     * 予定データ取得
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @param targetDate
     * @return
     */
    ScheduleInfoDTO selectScheduleInfo(String custId, String compCode, String employeeId, String targetDate);

    /**
     * エラーメッセージを取得する
     *
     * @param employeeId
     * @param targetDate
     * @param timepunch
     * @param custId
     * @param compCode
     * @return
     */
    String selectErrMsg(String employeeId, String targetDate, String timepunch, String custId, String compCode);

    /**
     * 打刻と予定データを取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    ClockInfoVO selectClockInfo(String custId, String compCode, String employeeId);

    /**
     * 職員の勤務パターンを取得する
     *
     * @param custId
     * @param compCode
     * @param employeeId
     * @return
     */
    int selectEmpPattern(String custId, String compCode, String employeeId);


    /**
     * 務パターンの変更時間を取得する
     *
     * @param custId
     * @param compCode
     * @param sectionId
     * @param patternId
     * @return
     */
    int selectPatternChangeTime(String custId, String compCode, String sectionId, String patternId);

    /**
     * プロシージャ：TMG_P_CTL_TIMEPUNCH_ALLを呼び出す
     *
     * @param modUserId
     * @param modProgramId
     * @param customerId
     * @param companyId
     * @return
     */
    void execTDAInsert(String modUserId, String modProgramId, String customerId, String companyId);
}
