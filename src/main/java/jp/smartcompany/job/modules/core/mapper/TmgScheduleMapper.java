package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * @author 陳毅力
 * @description 予定作成
 * @date 2020/05/25
 **/
@Mapper
public interface TmgScheduleMapper extends BaseMapper<Object> {

    /**
     * 年次休暇残
     *
     * @param params
     * @return
     */
    NpaidRestDTO selectTmgMonthly(HashMap<String, Object> params);

    /**
     * [勤怠]日別情報より予定データを取得します
     *
     * @param params
     * @return
     */
    ScheduleDataDTO selectSchedule(HashMap<String, Object> params);

    /**
     * 4週間単位の変形労働制職員対応
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(HashMap<String, Object> params);

    /**
     * 翌月リンクを取得
     *
     * @param params
     * @return
     */
    String selectLinkOfNextMonth(HashMap<String, Object> params);

    /**
     * 前月リンクを取得
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectLinkOfPreMonth(HashMap<String, Object> params);

    /**
     * 対象ユーザー情報
     *
     * @param params
     * @return
     */
    TargetUserDetailDTO selectTargetUserDetail(HashMap<String, Object> params);

    /**
     * 平日時の勤務時間
     *
     * @param params
     * @return
     */
    String selectWorkingHours(HashMap<String, Object> params);

    /**
     * 今月の月中有給付与の情報
     *
     * @param params
     * @return
     */
    HalfwayPaidHolidayDTO selectHalfwayPaidHoliday(HashMap<String, Object> params);

    /**
     * 週勤務パターン
     *
     * @param params
     * @return
     */
    TmgWeekPatternDTO selectTmgWeekPattern(HashMap<String, Object> params);

    /**
     * 基準日時点で４週間の変形労働制対象者か
     *
     * @param params
     * @return
     */
    int selectVariationalWorkInfo(HashMap<String, Object> params);

    /**
     * 基準日の月に４週間の変形労働制の期間が存在するか
     *
     * @param params
     * @return
     */
    int selectVariationalWorkDays(HashMap<String, Object> params);

    /**
     * 基本労働制対象者の表示開始?終了日の取得する
     *
     * @param params
     * @return
     */
    HashMap<String, Object> selectDsipDate(HashMap<String, Object> params);



}
