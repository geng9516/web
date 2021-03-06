package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSetting.HistSearchSettingMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchSettingService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public HistSearchSettingDO selectSettingInfo(Long settingId) {
        return baseMapper.selectSettingInfo(settingId);
    }

    @Override
    public List<CommonConditionVO> selectList(String custId, String loginUserId, List<String> companyList, List<String> groupIds) {
        return baseMapper.selectCommonList(custId,loginUserId,companyList,groupIds);
    }

}
