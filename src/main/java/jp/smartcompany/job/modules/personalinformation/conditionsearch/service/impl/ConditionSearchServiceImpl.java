package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.pojo.entity.MastDatadictionaryDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.ConditionSearchMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.TableOptionDTO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IConditionSearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionSearchServiceImpl extends ServiceImpl<ConditionSearchMapper, MastDatadictionaryDO>
implements IConditionSearchService {

    @Override
    public List<TableOptionDTO> selectTable() {
        return baseMapper.selectTable();
    }

}
