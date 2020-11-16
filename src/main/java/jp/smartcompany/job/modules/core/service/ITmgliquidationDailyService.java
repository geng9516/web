package jp.smartcompany.job.modules.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.MonthSumTimeDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;

import java.util.List;

public interface ITmgliquidationDailyService extends IService<TmgLiquidationDailyDO> {
    List<String> getMonthList(String empId, String startDate, String endDate);

    List<LiquidationDailyDto> getMonthInfo(String empId,String yyyymm);

    void execTLDDInsert(String empId, String startDate, String endDate, String userCode, String custID, String compCode);

    List<MonthSumTimeDto> getMonthSumTime(String empId, String startDate, String endDate,String custID, String compCode);

    int checkLiquidationDaily(String custID, String compCode, String empid, String yyyymmdd);

    List<LiquidationDailyInfoVo> selectDailyInfo(String custID, String compCode, String empId, String yyyymm);

    void execTDAInsert(String empId, String startDate, String endDate, String userCode, String custID, String compCode);
}
