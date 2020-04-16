package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastBalanceitem4salaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 差引優先項目マスタ                     給与データ出力時に超勤項目から時間を差引く際の、差し引く対象 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastBalanceitem4salaryMapper extends BaseMapper<TmgMastBalanceitem4salaryDO> {

        }
