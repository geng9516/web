package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerChangeDateDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupapppermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション権限マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface IMastGroupapppermissionService extends IService<MastGroupapppermissionDO> {

        List<GroupAppManagerPermissionDTO> selectPermissionList(
                String systemId, Date date, List<String> groupIds, String siteId, String appId, String language
        );

        GroupAppManagerChangeDateDTO selectDate(String systemId, Date pdDate, String groupId);

}
