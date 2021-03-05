package jp.smartcompany.job.modules.personalinformation.conditionsearch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity.HistSearchOrderDO;

import java.util.List;

public interface IHistSearchOrderService extends IService<HistSearchOrderDO> {

    List<HistSearchOrderDO> selectBySettingId(Long settingId);

}
