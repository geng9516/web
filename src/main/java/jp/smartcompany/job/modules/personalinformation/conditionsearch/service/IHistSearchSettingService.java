package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.vo.CommonConditionVO;

import java.util.List;

public interface IHistSearchSettingService extends IService<HistSearchSettingDO> {

    int selectSameSettingName(String settingName);

    long selectSeq();

    List<CommonConditionVO> selectList(String s, String loginUserId, List<String> companyList, List<String> groupIds);

    HistSearchSettingDO selectSettingInfo(Long settingId);

}
