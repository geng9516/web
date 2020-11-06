package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;

import java.util.List;

public interface ITmgliquidationDailyService extends IService<TmgLiquidationDailyDO> {
    List<String> getMonthList(String empId, String startDate, String endDate);

    List<LiquidationDailyDto> getMonthInfo(String empId,String yyyymm);
}
