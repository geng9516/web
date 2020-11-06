package jp.smartcompany.job.modules.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TmgLiquidationDailyMapper extends BaseMapper<TmgLiquidationDailyDO> {
    List<String> getMonthList(@Param("empId") String empId,
                              @Param("startDate")String startDate,
                              @Param("endDate")String endDate);

    List<LiquidationDailyDto> getMonthInfo(@Param("empId")String empId,
                                           @Param("yyyymm")String yyyymm);
}
