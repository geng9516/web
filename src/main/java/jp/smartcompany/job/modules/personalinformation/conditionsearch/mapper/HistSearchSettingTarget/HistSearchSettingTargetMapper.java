package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSettingTarget;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingTargetDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistSearchSettingTargetMapper extends BaseMapper<HistSearchSettingTargetDO> {

    List<HistSearchSettingTargetDO> selectBySettingId(Long settingId);

}
