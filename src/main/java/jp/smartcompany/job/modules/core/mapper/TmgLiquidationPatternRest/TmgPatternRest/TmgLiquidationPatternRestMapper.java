package jp.smartcompany.job.modules.core.mapper.TmgLiquidationPatternRest.TmgPatternRest;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPatternRestDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternRestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 勤務パターン休憩情報 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgLiquidationPatternRestMapper extends BaseMapper<TmgLiquidationPatternRestDO> {

}
