package jp.smartcompany.admin.appmanager.logic.impl;

import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.db.sql.SqlExecutor;
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

import javax.annotation.PostConstruct;
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

    @PostConstruct
    public void init() throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public List<MastAppTreeDTO> getAppTree() {
       return appTreeService.selectMastAppTree();
    }

    @Override
    public List<MastTemplateDTO> getTemplateList() {
        try {
            return SqlExecutor.query(connection, "select MAT_ID, MAT_CTEMPLATEID, MAT_CNAME from mast_apptemplate",new MastTemplateHandler());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public String updateMenuList(List<MastAppTreeDTO> paramList) {
        appTreeService.removeAll();
        Date date = DateUtil.date();
        String username = SecurityUtil.getUsername();

        List<MastApptreeDO> updateList = CollUtil.newArrayList();

        for (int i = 0; i < paramList.size(); i++) {
            MastAppTreeDTO dto = paramList.get(i);
            MastApptreeDO treeDO = new MastApptreeDO();
            BeanUtil.copyProperties(dto,treeDO);
            treeDO.setMtrDmodifieddate(date);
            treeDO.setVersionno(1L);
            treeDO.setMtrNseq((long)i);
            treeDO.setMtrCmodifieruserid(username);
            updateList.add(treeDO);
        }

        appTreeService.saveBatch(updateList);
        timedCache.remove(Constant.TOP_NAVS);
        ContextUtil.getSession().removeAttribute(Constant.IS_APPROVER);
        return "変更成功";
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
