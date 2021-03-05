package jp.smartcompany.job.modules.personalinformation.conditionsearch.mapper.HistSearchSetting;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HistSearchSettingMapper extends BaseMapper<HistSearchSettingDO> {

    int selectSameSettingName(String settingName);

    long selectSeq();

    HistSearchSettingDO selectSettingInfo(Long settingId);

    List<CommonConditionVO> selectCommonList(@Param("custId") String custId,
                                             @Param("userId") String loginUserId,
                                             @Param("companyList") List<String> companyList,
                                             @Param("groupIds") List<String> groupIds);

}
