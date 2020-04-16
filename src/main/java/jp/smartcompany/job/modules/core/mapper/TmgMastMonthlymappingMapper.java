package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastMonthlymappingDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 月次テーブルマッピングマスタ                月次集計処理において、tmg_monthly_totaliz Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastMonthlymappingMapper extends BaseMapper<TmgMastMonthlymappingDO> {

        }
