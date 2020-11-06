package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidationDailyMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.core.service.ITmgliquidationDailyService;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmgLiquidationDailyServiceImpl extends ServiceImpl<TmgLiquidationDailyMapper, TmgLiquidationDailyDO> implements ITmgliquidationDailyService {
    @Override
    public List<String> getMonthList(String empId, String startDate, String endDate){
        return baseMapper.getMonthList( empId, startDate, endDate);
    }

    @Override
    public List<LiquidationDailyDto> getMonthInfo(String empId, String yyyymm){
        return baseMapper.getMonthInfo( empId,yyyymm);
    }
}
