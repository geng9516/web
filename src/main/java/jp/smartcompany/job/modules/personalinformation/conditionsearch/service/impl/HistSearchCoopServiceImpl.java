package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchCoop.HistSearchCoopMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchCoopService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistSearchCoopServiceImpl extends ServiceImpl<HistSearchCoopMapper, HistSearchCoopDO> implements IHistSearchCoopService
{

    @Override
    public Boolean selectSameDataName(String hscCdataname) {
      return baseMapper.selectSameDataName(hscCdataname) > 0;
    }

    @Override
    public Long selectSeq() {
      return baseMapper.selectSeq();
    }

    @Override
    public List<HistSearchCoopDO> selectCoopList(String userId) {
        return baseMapper.selectCoopList(userId);
    }

    @Override
    public void deleteCoop(Long dataId) {
       baseMapper.deleteCoop(dataId);
    }

}
