package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchDefinitionsDO;

import java.util.List;

public interface IHistSearchDefinitionsService extends IService<HistSearchDefinitionsDO> {
    List<HistSearchDefinitionsDO> selectBySettingId(Long settingId);
}
