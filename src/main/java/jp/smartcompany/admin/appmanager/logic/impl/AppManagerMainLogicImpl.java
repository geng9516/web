package jp.smartcompany.admin.appmanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.admin.appmanager.dto.MastAppDTO;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import jp.smartcompany.admin.appmanager.handler.MastTemplateHandler;
import jp.smartcompany.admin.appmanager.logic.AppManagerMainLogic;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastApptreeDO;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
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

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AppManagerMainLogicImpl implements AppManagerMainLogic {

    private final IMastApptreeService appTreeService;
    private final DataSource dataSource;
    private Connection connection;
    private final TimedCache<String,Object> timedCache;

    @Override
    public List<MastAppTreeDTO> getAppList() {
       return appTreeService.selectMastAppList();
    }

    @Override
    public List<MastTemplateDTO> getTemplateList() {
        try {
            connection = dataSource.getConnection();
            return SqlExecutor.query(connection, "select MAT_ID, MAT_CTEMPLATEID, MAT_CNAME from mast_apptemplate",new MastTemplateHandler());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        } finally {
            DbUtil.close(connection);
        }
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public String updateMenuList(List<MastAppDTO> paramList) {
        HttpSession session = ContextUtil.getSession();
        appTreeService.removeAll();
        Date date = DateUtil.date();
        String username = SecurityUtil.getUsername();

        List<MastApptreeDO> updateList = CollUtil.newArrayList();

        for (int i = 0; i < paramList.size(); i++) {
            MastAppDTO dto = paramList.get(i);
            MastApptreeDO treeDO = new MastApptreeDO();
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
            treeDO.setVersionno(1L);
            treeDO.setMtrNseq((long)i);
            treeDO.setMtrCmodifieruserid(username);
            updateList.add(treeDO);
        }

        appTreeService.saveBatch(updateList);
        timedCache.remove(Constant.getSessionMenuId(session.getId()));
        ContextUtil.getSession().removeAttribute(Constant.IS_APPROVER);
        return "変更しました";
    }
    /**
     *  层级： application -> site -> application -> 页面 -> 按钮
     *                       dialog application -> 页面 -> 按钮
     *
     *                          编辑自身信息
     *   根节点（application）   添加dialog application
     *                          添加site
     */


}
