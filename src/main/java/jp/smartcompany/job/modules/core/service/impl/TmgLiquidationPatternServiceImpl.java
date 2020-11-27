package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidationPattern.TmgLiquidationPatternMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPatternDO;
import jp.smartcompany.job.modules.core.service.ITmgLiquidationPatternService;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.SelectPatternDto;
import org.springframework.stereotype.Repository;

import java.util.List;

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

        @Override
       public List<SelectPatternDto> selectLiquidationPatternInfo(String empId, String yyyymm, String custID, String compCode){
                return getBaseMapper().selectLiquidationPatternInfo( empId, yyyymm, custID, compCode);
        }

        @Override
        public String selectSeq(){
            return getBaseMapper().selectSeq();
        }
}
