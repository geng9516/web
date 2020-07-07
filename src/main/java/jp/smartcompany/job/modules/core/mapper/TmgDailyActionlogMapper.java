package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyActionlogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DailyLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [勤怠]日別情報操作ログ Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgDailyActionlogMapper extends BaseMapper<TmgDailyActionlogDO> {

    List<DailyLogVO> buildSQLForSelectTmgSelectDailyActionLog(@Param("custID") String custID,
                                                              @Param("compCode") String compCode,
                                                              @Param("targetUser") String targetUser,
                                                              @Param("day") String day,
                                                              @Param("language") String language);
}
