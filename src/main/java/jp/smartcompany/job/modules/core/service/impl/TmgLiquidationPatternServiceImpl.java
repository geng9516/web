package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidationPattern.TmgPattern.TmgLiquidationPatternMapper;
import jp.smartcompany.job.modules.core.mapper.TmgPattern.TmgPatternMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPatternDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgPatternDO;
import jp.smartcompany.job.modules.core.service.ITmgLiquidationPatternService;
import jp.smartcompany.job.modules.core.service.ITmgPatternService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDetailRow;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgLiquidationPatternServiceImpl extends ServiceImpl<TmgLiquidationPatternMapper, TmgLiquidationPatternDO> implements ITmgLiquidationPatternService {

}
