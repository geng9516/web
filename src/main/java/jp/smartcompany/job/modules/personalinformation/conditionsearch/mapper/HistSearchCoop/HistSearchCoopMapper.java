package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchCoop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HistSearchCoopMapper extends BaseMapper<HistSearchCoopDO> {

    int selectSameDataName(String hscCdataname);

    Long selectSeq();

}
