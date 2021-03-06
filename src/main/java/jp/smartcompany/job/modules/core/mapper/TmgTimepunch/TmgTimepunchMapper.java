package jp.smartcompany.job.modules.core.mapper.TmgTimepunch;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyAndRelaxDateDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * [勤怠]打刻データMapper 接口
 */
@Mapper
public interface TmgTimepunchMapper extends BaseMapper<TmgTimepunchDO> {

    /**
     * 打刻画面表示判断
     *
     * @param params
     * @return
     */
    String selectIsTimePunchTarget(HashMap<String, Object> params);

    /**
     * 本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻を取得します
     *
     * @param params
     * @return
     */
    BaseTimesDTO selectBaseTimes(HashMap<String, Object> params);

    /**
     * 打刻更新先となる日と今日の日付を取得するクエリ
     *
     * @param params
     * @return
     */
    String selectBaseTimesWithPattern(HashMap<String, Object> params);

    /**
     * 出勤日数と時間数の取得SQL
     *
     * @param params
     * @return
     */
    List<DutyDaysAndHoursDTO> selectDutyDaysAndHoursSQL(HashMap<String, Object> params);

    /**
     * 出勤日数と時間数を取得
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectDutyDaysAndHours(HashMap<String, Object> params);

    /**
     * 超過勤務時間
     *
     * @param params
     * @return
     */
    String selectOverTime(HashMap<String, Object> params);

    /**
     * 予定データ取得
     *
     * @return
     */
    ScheduleInfoDTO selectScheduleInfo(HashMap<String, Object> params);

    /**
     * エラーメッセージを取得する
     *
     * @param params
     * @return
     */
    String selectErrMsg(HashMap<String, Object> params);

    /**
     * 打刻と予定データを取得する
     *
     * @param params
     * @return
     */
    ClockInfoVO selectClockInfo(HashMap<String, Object> params);

    /**
     * 職員の勤務パターンを取得する
     *
     * @param params
     * @return
     */
    int selectEmpPattern(HashMap<String, Object> params);

    /**
     * 務パターンの変更時間を取得する
     *
     * @param params
     * @return
     */
    int selectPatternChangeTime(HashMap<String, Object> params);

    /**
     * プロシージャ：TMG_P_CTL_TIMEPUNCH_ALLを呼び出す
     *
     * @param modUserId
     * @param modProgramId
     * @param customerId
     * @param companyId
     * @return
     */
    @Insert(" CALL TMG_P_CTL_TIMEPUNCH_ALL (#{modUserId},#{modProgramId},#{customerId},#{companyId})")
    void execTDAInsert(@Param("modUserId")String modUserId,
                       @Param("modProgramId")String modProgramId,
                       @Param("customerId")String customerId,
                       @Param("companyId")String companyId);

    /**
     * 個人勤務及び休暇情報
     *
     * @param params
     * @return
     */
    List<DutyAndRelaxDateDTO> selectDisplayInfo(HashMap<String, Object> params);
}
