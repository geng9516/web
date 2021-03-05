package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchSelectDO;

import java.util.List;

public interface IHistSearchSelectService extends IService<HistSearchSelectDO> {


    List<HistSearchSelectDO> selectBySettingId(Long settingId);

}
