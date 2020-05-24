package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyDetailVO;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.MgdAttributeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]日別情報詳細                    2007/02/23 予定実績を再統合。また、申請番号のカラ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyDetailMapper extends BaseMapper<TmgDailyDetailDO> {

    /**
     * 日別詳細情報を取得する
     */
    List<DailyDetailVO> buildSQLForSelectDetail(Map<String, Object> map);

}
