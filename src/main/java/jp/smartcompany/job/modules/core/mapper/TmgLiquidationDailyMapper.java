package jp.smartcompany.job.modules.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.MonthSumTimeDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;
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

    void execTLDDInsert(@Param("empId")String empId,
                       @Param("startDate")String startDate,
                       @Param("endDate")String endDate,
                       @Param("userCode")String userCode,
                       @Param("custID")String custID,
                       @Param("compCode")String compCode);

    List<MonthSumTimeDto> getMonthSumTime(@Param("empId")String empId,
                                          @Param("startDate")String startDate,
                                          @Param("endDate")String endDate,
                                          @Param("custID")String custID,
                                          @Param("compCode")String compCode);

    int checkLiquidationDaily(@Param("custID")String custID,
                              @Param("compCode")String compCode,
                              @Param("empId")String empId,
                              @Param("yyyymmdd")String yyyymmdd);

    List<LiquidationDailyInfoVo> selectDailyInfo(@Param("custID")String custID,
                                                 @Param("compCode")String compCode,
                                                 @Param("empId")String empId,
                                                 @Param("yyyymm")String yyyymm);

    void execTDAInsert(@Param("empId")String empId,
                       @Param("startDate")String startDate,
                       @Param("endDate")String endDate,
                       @Param("userCode")String userCode,
                       @Param("custID")String custID,
                       @Param("compCode")String compCode);
}
