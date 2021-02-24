package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchCoopDetail.HistSearchCoopDetailMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDetailDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchCoopDetailService;
import org.springframework.stereotype.Service;

@Service
public class HistSearchCoopDetailServiceImpl extends ServiceImpl<HistSearchCoopDetailMapper, HistSearchCoopDetailDO>
implements IHistSearchCoopDetailService {

    @Override
    public void saveQuery(String query) {
        baseMapper.saveQuery(query);
    }

    @Override
    public void deleteDetail(Long oDataId) {
        baseMapper.deleteDetail(oDataId);
    }
}
