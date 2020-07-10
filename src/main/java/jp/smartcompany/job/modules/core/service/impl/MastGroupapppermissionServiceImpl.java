package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerChangeDateDTO;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.util.PageUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGroupapppermissionDO;
import jp.smartcompany.job.modules.core.mapper.MastGroupapppermissionMapper;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * グループ別アプリケーション権限マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class MastGroupapppermissionServiceImpl extends ServiceImpl<MastGroupapppermissionMapper, MastGroupapppermissionDO> implements IMastGroupapppermissionService {

        @Override
        public List<GroupAppManagerPermissionDTO> selectPermissionList(
                String systemId,Date date,List<String> groupIds,String siteId,String appId, String language
        ) {
                String strDate = SysUtil.transDateToString(date);
                return baseMapper.selectPermissionList(systemId,strDate,groupIds,siteId,appId,language);
        }

        @Override
        public GroupAppManagerChangeDateDTO selectDate(String systemId, Date pdDate, String groupId) {
                String strDate = SysUtil.transDateToString(pdDate);
                return baseMapper.selectDate(systemId,strDate,groupId);
        }

        @Override
        public Page<MastGroupapppermissionDO> pagePermissionList(IPage<MastGroupapppermissionDO> page) {
               return baseMapper.selectPermissionListPage(page);
        }

//        @Override
//        public int deleteAfter(String systemId,String date,String groupId, String objectId) {
//             return baseMapper.deleteAfter(systemId,date,groupId,objectId);
//        }
//
//        @Override
//        public List<MastGroupapppermissionDO> selectValidPermissions(
//                String systemId, String date, String groupId, String objectId) {
//                return baseMapper.selectValidPermissions(systemId,date,groupId,objectId);
//        }
//
//        @Override
//        public int deleteOtherSysObj(String systemId,String objectId) {
//                return baseMapper.deleteOtherSysObj(systemId,objectId);
//        }

}
