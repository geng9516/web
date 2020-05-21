package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMonthlyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MonthlyLinkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]月別情報                      2007/2/23 年休調整日数のカラムを追加。年休(時間) Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMonthlyMapper extends BaseMapper<TmgMonthlyDO> {

    /**
     * 該当の職員・年月の超過勤務時間数の合計を取得する
     *
     * @param map Map
     * @return 合計
     */
    List<String> buildSQLForSelectMonthlyOverTime(Map<String, Object> map);


    /**
     * 表示月遷移リスト情報を取得する
     *
     * @param map Map
     * @return 合計
     */
    List<DispMonthlyVO> buildSQLForSelectDispMonthlyList(Map<String, Object> map);

    /**
     * 前月・翌月の月別情報を取得する
     *
     * @param map Map
     * @return MonthlyLinkVO
     */
    MonthlyLinkVO buildSQLForSelectMonthlyLink(Map<String, Object> map);

    /**
     * カレンダーを取得する
     * @param map Map
     * @return Map
     */
    HashMap buildSQLForSelectCalendar(Map<String, Object> map);


    /**
     * 月別情報を取得する
     * @param map Map
     * @return Map
     */
    HashMap buildSQLForSelectMonthly(Map<String, Object> map);

}
