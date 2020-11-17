package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidation.TmgLiquidationDailyCheckMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyCheckDO;
import jp.smartcompany.job.modules.core.service.ITmgliquidationDailyCheckService;
import org.springframework.stereotype.Service;

@Service
public class TmgLiquidationDailyCheckServiceImpl extends ServiceImpl<TmgLiquidationDailyCheckMapper, TmgLiquidationDailyCheckDO> implements ITmgliquidationDailyCheckService {
}
