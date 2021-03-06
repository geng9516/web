package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSettingTarget.HistSearchSettingTargetMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingTargetDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchSettingTargetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistSearchSettingTargetServiceImpl extends ServiceImpl<HistSearchSettingTargetMapper, HistSearchSettingTargetDO>
implements IHistSearchSettingTargetService {

    @Override
    public List<HistSearchSettingTargetDO> selectBySettingId(Long settingId) {
       return baseMapper.selectBySettingId(settingId);
    }

}
