package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingDO;

public interface IHistSearchSettingService extends IService<HistSearchSettingDO> {

    int selectSameSettingName(String settingName);

    long selectSeq();

    String selectSettingOwner(Long settingId);

}
