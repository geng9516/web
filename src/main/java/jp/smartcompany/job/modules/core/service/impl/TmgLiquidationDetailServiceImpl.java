package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidationDetailMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationDetailDO;
import jp.smartcompany.job.modules.core.service.ITmgliquidationDetailService;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmgLiquidationDetailServiceImpl extends ServiceImpl<TmgLiquidationDetailMapper, TmgLiquidationDetailDO> implements ITmgliquidationDetailService {
    @Override
    public List<EditDispVo> getLiquidationDetail(String custID, String compCode, String tlpId, String startDate, String endDate){
        return getBaseMapper().getLiquidationDetail( custID,  compCode,  tlpId,  startDate,  endDate);
    }

    @Override
    public List<EditDispVo> getLiquidationDetailNew(String startDate, String months){
        return getBaseMapper().getLiquidationDetailNew(startDate,  months);
    }
}
