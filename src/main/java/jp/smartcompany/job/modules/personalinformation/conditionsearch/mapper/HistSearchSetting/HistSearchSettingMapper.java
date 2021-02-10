package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSetting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistSearchSettingMapper extends BaseMapper<HistSearchSettingDO> {

    int selectSameSettingName(String settingName);

    long selectSeq();

    String selectSettingOwner(Long settingId);

}
