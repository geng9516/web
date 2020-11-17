package jp.smartcompany.job.modules.core.mapper.TmgLiquidationPeriod;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPeriodDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TmgLiquidationPeriodMapper extends BaseMapper<TmgLiquidationPeriodDO> {
    List<LiquidationPeriodListDto> getLiquidationDispFromType(@Param("custId") String custId,
                                                              @Param("compId") String compId,
                                                              @Param("type") String type,
                                                              @Param("searchText") String searchText);


    List<LiquidationPeriodListDto> getLiquidationDispFromWorkType(@Param("custId") String custId,
                                                                  @Param("compId") String compId,
                                                                  @Param("workType") String workType);

    @Select("select TMG_LIQUIDATION_PERIOD_SEQ.nextval from dual")
    String getSeq();
}
