package jp.smartcompany.job.modules.tmg_setting.notificationsetting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.MastGenericDetail.MastGenericDetailMapper;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.mapper.NotificationSettingMapper;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity.TmgNtfCheckDo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.service.INotificationSettingService;

import java.util.List;

public class NotificationSettingImpl extends ServiceImpl<NotificationSettingMapper, TmgNtfCheckDo> implements INotificationSettingService {


    @Override
    public List<TmgNtfCheckDo> getNewCheckList(String ntfType,String sysdate,String timeType){
        return baseMapper.getNewCheckList(ntfType,sysdate,timeType);
    }
}
