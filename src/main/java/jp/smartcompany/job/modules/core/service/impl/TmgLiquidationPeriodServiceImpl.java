package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgLiquidation.TmgLiquidationPeriodMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgLiquidationPeriodDO;
import jp.smartcompany.job.modules.core.service.ITmgliquidationPeriodService;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationPeriodListDto;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class TmgLiquidationPeriodServiceImpl extends ServiceImpl<TmgLiquidationPeriodMapper, TmgLiquidationPeriodDO> implements ITmgliquidationPeriodService {

    @Override
    public List<LiquidationPeriodListDto> getLiquidationDispFromType(String custId,String compId,String type, String searchText){
        return baseMapper.getLiquidationDispFromType( custId, compId, type,  searchText);
    }

    @Override
    public List<LiquidationPeriodListDto> getLiquidationDispFromWorkType(String custId,String compId,String workType){
        return baseMapper.getLiquidationDispFromWorkType( custId, compId,workType);
    }

    @Override
    public  String getSeq(){
        return baseMapper.getSeq();
    }
}
