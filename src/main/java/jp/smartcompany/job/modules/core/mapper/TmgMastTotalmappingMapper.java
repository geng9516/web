package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastTotalmappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 集計項目マッピングマスタ                  日次集計処理において、tmg_dailyおよびtmg_dai Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastTotalmappingMapper extends BaseMapper<TmgMastTotalmappingDO> {

        }
