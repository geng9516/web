package jp.smartcompany.job.modules.core.mapper.TmgLiquidationDaily;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDailyDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationDailyDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.MonthSumTimeDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDailyInfoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TmgLiquidationDailyMapper extends BaseMapper<TmgLiquidationDailyDO> {
    List<String> getMonthList(@Param("empId") String empId,
                              @Param("startDate")String startDate,
                              @Param("endDate")String endDate);

    List<LiquidationDailyDto> getMonthInfo(@Param("empId")String empId,
                                           @Param("yyyymm")String yyyymm,
                                           @Param("startDate")String startDate,
                                           @Param("endDate")String endDate);

    @Insert("CALL TMG_P_LIQUIDATION_DAILY_INSERT (#{empId},#{startDate},#{endDate},#{userCode},#{custID},#{compCode})")
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

    @Select("SELECT TMG_F_CHECK_LIQUIDATION_DAILY( #{empId},TRUNC(TO_DATE(#{yyyymmdd}),'mm'),#{custID},#{compCode}) FROM DUAL")
    int checkLiquidationDaily(@Param("custID")String custID,
                              @Param("compCode")String compCode,
                              @Param("empId")String empId,
                              @Param("yyyymmdd")String yyyymmdd);

    List<LiquidationDailyInfoVo> selectDailyInfo(@Param("custID")String custID,
                                                 @Param("compCode")String compCode,
                                                 @Param("empId")String empId,
                                                 @Param("yyyymm")String yyyymm);

    @Insert(" CALL TMG_P_SHEET_INSERT_LIQUIDATION (#{empId},#{startDate},#{endDate},#{userCode},#{custID},#{compCode})")
    void execTDAInsert(@Param("empId")String empId,
                       @Param("startDate")String startDate,
                       @Param("endDate")String endDate,
                       @Param("userCode")String userCode,
                       @Param("custID")String custID,
                       @Param("compCode")String compCode);
}
