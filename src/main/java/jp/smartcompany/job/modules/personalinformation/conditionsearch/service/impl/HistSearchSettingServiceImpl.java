package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSetting.HistSearchSettingMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchSettingService;
import org.springframework.stereotype.Service;

@Service
public class HistSearchSettingServiceImpl extends ServiceImpl<HistSearchSettingMapper, HistSearchSettingDO>
implements IHistSearchSettingService {

    @Override
    public int selectSameSettingName(String settingName) {
       return baseMapper.selectSameSettingName(settingName);
    }

    @Override
    public long selectSeq() {
        return baseMapper.selectSeq();
    }

    @Override
    public String selectSettingOwner(Long settingId) {
        return baseMapper.selectSettingOwner(settingId);
    }

}
