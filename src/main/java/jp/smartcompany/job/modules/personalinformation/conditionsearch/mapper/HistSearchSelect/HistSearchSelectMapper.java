package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSelect;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSelectDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistSearchSelectMapper extends BaseMapper<HistSearchSelectDO> {

    List<HistSearchSelectDO> selectBySettingId(Long settingId);

}
