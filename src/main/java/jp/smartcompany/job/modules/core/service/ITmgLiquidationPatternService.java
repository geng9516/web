package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPatternDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.SelectPatternDto;

import java.util.List;

/**
 * <p>
 * [勤怠]勤務パターン                    制約：月中に歴が切れないこと、デフォルトフラグがonの行は同 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgLiquidationPatternService extends IService<TmgLiquidationPatternDO> {

    List<SelectPatternDto> selectLiquidationPatternInfo(String empId, String yyyymm, String custID, String compCode);

    String selectSeq();
}
