package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchCoopDetail;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDetailDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistSearchCoopDetailMapper extends BaseMapper<HistSearchCoopDetailDO> {

    void saveQuery(String query);

    void deleteDetail(Long oDataId);

}
