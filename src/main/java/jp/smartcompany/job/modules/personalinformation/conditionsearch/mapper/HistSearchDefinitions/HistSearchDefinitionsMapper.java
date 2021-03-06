package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchDefinitions;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchDefinitionsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistSearchDefinitionsMapper extends BaseMapper<HistSearchDefinitionsDO> {
    List<HistSearchDefinitionsDO> selectBySettingId(Long settingId);
}
