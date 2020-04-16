package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastDailymappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 日次テーブルマッピングマスタ                日次集計処理において、tmg_daily_totalizat Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastDailymappingMapper extends BaseMapper<TmgMastDailymappingDO> {

        }
