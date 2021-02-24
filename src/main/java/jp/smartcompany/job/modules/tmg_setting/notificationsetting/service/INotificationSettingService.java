package jp.smartcompany.job.modules.tmg_setting.notificationsetting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity.TmgNtfCheckDo;

import java.util.List;

public interface INotificationSettingService  extends IService<TmgNtfCheckDo> {

    List<TmgNtfCheckDo> getNewCheckList(String ntfType,String sysdate,String timeType);

}
