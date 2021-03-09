package jp.smartcompany.job.modules.tmg_setting.notificationsetting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.smartcompany.job.modules.tmg_setting.notificationsetting.mapper.NotificationSettingMapper;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo.entity.TmgNtfCheckDo;
import jp.smartcompany.job.modules.tmg_setting.notificationsetting.service.INotificationSettingService;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class NotificationSettingImpl extends ServiceImpl<NotificationSettingMapper, TmgNtfCheckDo> implements INotificationSettingService {

    @Override
    public List<TmgNtfCheckDo> getNewCheckList(String group,String typeGroup,String sysdate,String timeType){
        return baseMapper.getNewCheckList( group, typeGroup,sysdate,timeType);
    }

    @Override
    public List<TmgNtfCheckDo> getCheckFunc(String ntfType, String sysdate){
        return baseMapper.getCheckFunc( ntfType, sysdate);

    }
}
