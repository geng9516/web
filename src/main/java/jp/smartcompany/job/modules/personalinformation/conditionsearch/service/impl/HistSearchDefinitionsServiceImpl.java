package jp.smartcompany.job.modules.personalinformation.conditionsearch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchDefinitions.HistSearchDefinitionsMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchDefinitionsDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.service.IHistSearchDefinitionsService;
import org.springframework.stereotype.Service;

@Service
public class HistSearchDefinitionsServiceImpl extends ServiceImpl<HistSearchDefinitionsMapper, HistSearchDefinitionsDO>
implements IHistSearchDefinitionsService {

}
