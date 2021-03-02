package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchCoop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchCoopDO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface HistSearchCoopMapper extends BaseMapper<HistSearchCoopDO> {

    int selectSameDataName(String hscCdataname);

    Long selectSeq();

    List<HistSearchCoopDO> selectCoopList(String userId);

    int deleteCoop(Long dataId);

}
