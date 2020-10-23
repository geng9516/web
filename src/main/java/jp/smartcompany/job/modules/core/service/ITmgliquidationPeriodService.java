package jp.smartcompany.job.modules.core.service;


import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPeriodDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;

import java.util.List;


public interface ITmgliquidationPeriodService  extends IService<TmgLiquidationPeriodDO> {
    List<LiquidationPeriodListDto> getLiquidationDispFromType(String custId,String compId,String type, String searchText);

    List<LiquidationPeriodListDto> getLiquidationDispFromWorkType(String custId,String compId,String workType);

    String getSeq();
}
