package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyEditVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]日別情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyMapper extends BaseMapper<TmgDailyDO> {

    /**
     * 対象社員について対象月の未承認日数を集計する
     *
     * @param map
     * @return 未承認日数
     */
    String buildSQLForSelectCountNotApprovalDay(Map<String, Object> map);


    /**
     *日別情報を取得する
     *
     * @param map
     * @return
     */
    List<HashMap> buildSQLForSelectDaily(Map<String, Object> map);


    /**
     * 日別情報(編集用)を取得する
     *
     * @param map
     * @return
     */
    DailyEditVO buildSQLForSelectDailyEdit(Map<String, Object> map);

}
