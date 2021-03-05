package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSelect.HistSearchSelectMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSelectDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchSelectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistSearchSelectServiceImpl extends ServiceImpl<HistSearchSelectMapper, HistSearchSelectDO>
implements IHistSearchSelectService {
  @Override
  public List<HistSearchSelectDO> selectBySettingId(Long settingId) {
      return baseMapper.selectBySettingId(settingId);
  }

}
