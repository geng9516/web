package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 段階導入シュミレーション登録 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgStatusWorktypeSimMapper extends BaseMapper<TmgStatusWorktypeSimDO> {

    StatusWorkTypeSimDto buildSQLForSelectTmgStatusWorkTypeSim(@Param("custID")String custID,
                                                               @Param("compCode")String compCode,
                                                               @Param("language")String language);
}
