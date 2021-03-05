package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSettingTargetDO;

import java.util.List;

public interface IHistSearchSettingTargetService extends IService<HistSearchSettingTargetDO> {
    List<HistSearchSettingTargetDO> selectBySettingId(Long settingId);
}
