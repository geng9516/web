package jp.smartcompany.admin.appmanager.logic.impl;

import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.admin.appmanager.dto.MastAppTreeDTO;
import jp.smartcompany.admin.appmanager.dto.MastTemplateDTO;
import jp.smartcompany.admin.appmanager.handler.MastTemplateHandler;
import jp.smartcompany.admin.appmanager.logic.AppManagerMainLogic;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.service.IMastApptreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AppManagerMainLogicImpl implements AppManagerMainLogic {

    private final IMastApptreeService appTreeService;
    private final DataSource dataSource;

    @Override
    public List<MastAppTreeDTO> getAppTree() {
       return appTreeService.selectMastAppTree();
    }

    @Override
    public List<MastTemplateDTO> getTemplateList() {
        try {
            List<MastTemplateDTO> templateList = SqlExecutor.query(dataSource.getConnection(), "select MAT_ID, MAT_CTEMPLATEID, MAT_CNAME from mast_apptemplate",new MastTemplateHandler());
            return templateList;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

}
