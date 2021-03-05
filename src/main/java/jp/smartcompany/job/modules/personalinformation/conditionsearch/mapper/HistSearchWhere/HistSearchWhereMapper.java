package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchWhere;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchWhereDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistSearchWhereMapper extends BaseMapper<HistSearchWhereDO> {

    List<HistSearchWhereDO> selectBySettingId(Long settingId);
}
