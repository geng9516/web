package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastManager4salaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 給与用管理職判定マスタ                   人給発令データから連携した管理職ﾌﾗｸﾞ・指定職ﾌﾗｸﾞを元 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgMastManager4salaryMapper extends BaseMapper<TmgMastManager4salaryDO> {

        }
