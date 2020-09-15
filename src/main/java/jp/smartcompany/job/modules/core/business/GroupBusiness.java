package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.DBMastGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.PQueryUserGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.handler.UserGroupEntityListHandler;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.boot.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 用户组Logic层
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.GROUP)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class GroupBusiness {

    private final IMastGroupService iMastGroupService;

    private static final String GROUP_CHECK_MODE_SINGLE = "single";
    private static final String GROUP_CHECK_MODE_MULTIPLE = "multiple";
    private final DataSource dataSource;

    /**
     * 获取当前登录用户拥有的角色
     */
    public void getGroupList(String language, List<MastSystemDO> systemList,HttpSession httpSession) {
        String groupCheckMode = GROUP_CHECK_MODE_MULTIPLE;
        boolean groupCheckFlag = false;
        if (groupCheckFlag) {
            setPretreatGroup(language,httpSession);
        } else {
            setGroupInfo(groupCheckMode,language,systemList,httpSession);
        }
    }

    public void setGroupInfo(String checkMode,String language,List<MastSystemDO> systemList,HttpSession httpSession){
        PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
        if (MapUtil.isNotEmpty(session.getLoginGroups())){
            return;
        }
        String userId = SecurityUtil.getUserId();
        systemList.forEach(system -> {
            String systemCode = system.getMsCsystemidPk();
            List<DBMastGroupBO> mastGroupList = iMastGroupService.getUserGroupByLanguage(language,systemCode);
            if (CollUtil.isNotEmpty(mastGroupList)) {
                List<LoginGroupBO> listLoginGroup =CollUtil.newArrayList();

                for (DBMastGroupBO DBMastGroupBO : mastGroupList) {
                    int nQueryCount = 0;
                    try {
                        nQueryCount = getAssembleSql(DBMastGroupBO, userId);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if (nQueryCount>0) {
                        LoginGroupBO loginGroup = setLoginGroupBO(DBMastGroupBO);
                        listLoginGroup.add(loginGroup);
                        if (StrUtil.equalsIgnoreCase(checkMode, GROUP_CHECK_MODE_SINGLE)) {
                            break;
                        }
                    }
                }

                if (StrUtil.equalsIgnoreCase(checkMode,GROUP_CHECK_MODE_SINGLE) && CollUtil.isEmpty(listLoginGroup)) {
                    DBMastGroupBO lastGroup = CollUtil.getLast(mastGroupList);
                    LoginGroupBO loginGroup = setLoginGroupBO(lastGroup);
                    listLoginGroup.add(loginGroup);
                }

                log.info("【groupBusiness：{}】",listLoginGroup);
                if (CollUtil.isNotEmpty(listLoginGroup)) {
                    session.setLoginGroups(MapUtil.<String,List<LoginGroupBO>>builder().put(systemCode,listLoginGroup).build());
                    System.out.println("+++");
                }
            }
        });
    }

    public Map<String, List<LoginGroupBO>> getGroupInfos(String userId,String checkMode,String language,List<MastSystemDO> systemList){
        Map<String,List<LoginGroupBO>> map = MapUtil.newHashMap();
        systemList.forEach(system -> {
            String systemCode = system.getMsCsystemidPk();
            List<DBMastGroupBO> mastGroupList = iMastGroupService.getUserGroupByLanguage(language,systemCode);
            if (CollUtil.isNotEmpty(mastGroupList)) {
                List<LoginGroupBO> listLoginGroup =CollUtil.newArrayList();

                for (DBMastGroupBO DBMastGroupBO : mastGroupList) {
                    int nQueryCount = 0;
                    try {
                        nQueryCount = getAssembleSql(DBMastGroupBO, userId);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if (nQueryCount>0) {
                        LoginGroupBO loginGroup = setLoginGroupBO(DBMastGroupBO);
                        listLoginGroup.add(loginGroup);
                        if (StrUtil.equalsIgnoreCase(checkMode, GROUP_CHECK_MODE_SINGLE)) {
                            break;
                        }
                    }
                }

                if (StrUtil.equalsIgnoreCase(checkMode,GROUP_CHECK_MODE_SINGLE) && CollUtil.isEmpty(listLoginGroup)) {
                    DBMastGroupBO lastGroup = CollUtil.getLast(mastGroupList);
                    LoginGroupBO loginGroup = setLoginGroupBO(lastGroup);
                    listLoginGroup.add(loginGroup);
                }

                log.info("【groupBusiness：{}】",listLoginGroup);
                if (CollUtil.isNotEmpty(listLoginGroup)) {
                    map.put(systemCode,listLoginGroup);
                }
            }
        });
        return map;
    }

    private void setPretreatGroup(String language,HttpSession httpSession) {
        String userId = SecurityUtil.getUserId();
        List<LoginGroupBO> lGroup = CollUtil.newArrayList();
        String systemCode = "";
        Map<String, List<LoginGroupBO>> hGroupMap = MapUtil.newHashMap(true);
        List<DBMastGroupBO> mastGroupList = iMastGroupService.getPretreatGroupByLanguageUserId(language, userId);
        for (DBMastGroupBO group : mastGroupList) {
            systemCode = group.getSystemId();
            if (hGroupMap.containsKey(systemCode)) {
                lGroup = hGroupMap.get(systemCode);
            }
            lGroup.add(setLoginGroupBO(group));
            hGroupMap.put(systemCode, lGroup);
        }
        if (CollUtil.isNotEmpty(lGroup)) {
            PsSession session = (PsSession) httpSession.getAttribute(Constant.PS_SESSION);
            session.setLoginGroups(hGroupMap);
        }
    }

    private LoginGroupBO setLoginGroupBO(DBMastGroupBO dBMastGroupBO) {
        LoginGroupBO loginGroup = new LoginGroupBO();
        loginGroup.setSystemCode(dBMastGroupBO.getSystemId())
                .setSystemName(dBMastGroupBO.getSystemName())
                .setGroupCode(dBMastGroupBO.getGroupId())
                .setGroupName(dBMastGroupBO.getGroupName());
        return loginGroup;
    }

    public int getAssembleSql(DBMastGroupBO groupBO, String userId) throws SQLException {
        String strQuery = groupBO.getPQuery() + " AND HD_CUSERID = "
                + " '" + userId + "'";
        int nQueryCount = 0;
        log.info("运行的sql语句：{}",strQuery);
        try (Connection connection = dataSource.getConnection()) {
            List<PQueryUserGroupBO> entityList = SqlExecutor.query(connection, strQuery, new UserGroupEntityListHandler());
            return entityList.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nQueryCount;
    }

}
