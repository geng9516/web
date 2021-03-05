package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchOrder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchOrderDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistSearchOrderMapper extends BaseMapper<HistSearchOrderDO> {

    List<HistSearchOrderDO> selectBySettingId(Long settingId);

}
