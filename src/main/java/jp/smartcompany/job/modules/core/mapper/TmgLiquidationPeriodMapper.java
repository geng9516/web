package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPeriodDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
}
