package jp.smartcompany.admin.appmanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.admin.appmanager.dto.AppSortDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import jp.smartcompany.admin.appmanager.handler.MastTemplateHandler;
import jp.smartcompany.admin.appmanager.logic.AppManagerMainLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import jp.smartcompany.job.modules.core.service.impl.MastApptreeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AppManagerMainLogicImpl implements AppManagerMainLogic {

    private final IMastApptreeService appTreeService;
    private final DataSource dataSource;
    private final TimedCache<String,Object> timedCache;

    @Override
    public List<MastAppTreeDTO> getAppList() {
       return appTreeService.selectMastAppList();
    }

    @Override
    public List<MastTemplateDTO> getTemplateList() {
        try (Connection connection = dataSource.getConnection()) {
            return SqlExecutor.query(connection, "select MAT_ID, MAT_CTEMPLATEID, MAT_CNAME from mast_apptemplate",new MastTemplateHandler());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public String updateMenuList(MastAppDTO dto) {
        HttpSession session = ContextUtil.getSession();
        Date date = DateUtil.date();
        String username = SecurityUtil.getUsername();

        MastApptreeDO treeDO = new MastApptreeDO();
        treeDO.setMtrId(dto.getId());
        treeDO.setMtrCurl(dto.getUrl());
        treeDO.setMtrCtype(dto.getType());
        treeDO.setMtrCtemplateid(dto.getTemplateId());
        treeDO.setMtrCappautoload(dto.getAutoUpload());
        treeDO.setMtrCappid(dto.getAppId());
        treeDO.setMtrCbuttonid(dto.getButtonId());
        treeDO.setMtrCcriterialdatetype(dto.getBaseDateType());
        treeDO.setMtrCdatapermissiontype(dto.getPermissionType());
        treeDO.setMtrCdefaulttargetuser(dto.getDefaultTargetUser());
        treeDO.setMtrCdomainid(dto.getDomainId());
        treeDO.setMtrCimageurl(dto.getIcon());
        treeDO.setMtrCobjectid(dto.getObjectId());
        treeDO.setMtrCobjname(dto.getName());
        treeDO.setMtrCobjnameja(dto.getName());
        treeDO.setMtrConlinehelpurl(dto.getHelpDocUrl());
        treeDO.setMtrCsitecaption(dto.getRemark());
        treeDO.setMtrCsitecaptionja(dto.getRemark());
        treeDO.setMtrCsiteid(dto.getSiteId());
        treeDO.setMtrCscreenid(dto.getScreenId());
        treeDO.setMtrCsubappid(dto.getSubAppId());
        treeDO.setMtrCsystemid("01");

        treeDO.setMtrDmodifieddate(date);
        treeDO.setMtrNseq(dto.getSort());
        treeDO.setMtrCmodifieruserid(username);
        // 根节点无需变更层级
        if (StrUtil.equals(dto.getObjectId(),"TopPage") && StrUtil.equals(dto.getParentId(),"0")){
            appTreeService.updateById(treeDO);
        } else {
            MastApptreeDO parentTree = appTreeService.getByParentId(dto.getParentId());
            treeDO.setAppLevel(MastApptreeServiceImpl.calculateLevel(parentTree.getAppLevel(), dto.getParentId()));
            treeDO.setParentId(dto.getParentId());
            if (dto.getId() == null) {
                appTreeService.save(treeDO);
            }else {
                appTreeService.updateById(treeDO);
            }
        }
        timedCache.remove(Constant.getSessionMenuId(session.getId()));
        timedCache.remove(Constant.KEY_HOME_URL);
        ContextUtil.getSession().removeAttribute(Constant.IS_APPROVER);
        return "変更しました";
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void updateMenuSort(List<AppSortDTO> list) {
       List<MastApptreeDO> doList = CollUtil.newArrayList();
       list.forEach(sortItem -> {
           MastApptreeDO mastApptreeDO = new MastApptreeDO();
           mastApptreeDO.setMtrId(sortItem.getId());
           mastApptreeDO.setMtrNseq(sortItem.getSort());
           doList.add(mastApptreeDO);
       });
       appTreeService.updateBatchById(doList);
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public void delete(Long id) {
       MastApptreeDO appTreeDO = appTreeService.getById(id);
       if (appTreeDO == null) {
          throw new GlobalException("メニューは存在しません");
       }
       if (StrUtil.equals(MastApptreeServiceImpl.ROOT,appTreeDO.getParentId())) {
           throw new GlobalException("ルート部門の削除ができません");
       }
       List<MastApptreeDO> children = appTreeService.list(
            SysUtil.<MastApptreeDO>query().likeRight("app_level",MastApptreeServiceImpl.calculateLevel(appTreeDO.getAppLevel(),appTreeDO.getMtrCobjectid()))
       );
       List<Long> removeIds = CollUtil.newArrayList(id);
       if (CollUtil.isNotEmpty(children)){
           List<Long> childrenIds = children.stream()
                   .map(MastApptreeDO::getMtrId)
                   .collect(Collectors.toList());
           CollUtil.addAllIfNotContains(removeIds,childrenIds);
       }
       appTreeService.removeByIds(removeIds);
    }

}
