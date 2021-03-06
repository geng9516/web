package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchWhere.HistSearchWhereMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchWhereDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchWhereService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistSearchWhereServiceImpl extends ServiceImpl<HistSearchWhereMapper, HistSearchWhereDO>
implements IHistSearchWhereService {

    @Override
    public List<HistSearchWhereDO> selectBySettingId(Long settingId) {
      return baseMapper.selectBySettingId(settingId);
    }

}
