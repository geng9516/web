package jp.smartcompany.job.modules.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDetailDO;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TmgLiquidationDetailMapper extends BaseMapper<TmgLiquidationDetailDO> {
    List<EditDispVo> getLiquidationDetail(@Param("custID") String custID,
                                          @Param("compCode") String compCode,
                                          @Param("tlpId") String tlpId,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);
}
