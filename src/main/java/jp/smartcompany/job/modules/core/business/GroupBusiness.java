package jp.smartcompany.job.modules.core.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.job.common.Constant;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginUserGroupBO;
import jp.smartcompany.job.modules.core.pojo.bo.UserGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.pojo.handler.UserGroupEntityListHandler;
import jp.smartcompany.job.modules.core.service.IMastGroupService;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.util.ShiroUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.GROUP)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupBusiness {

    private final IMastSystemService iMastSystemService;
    private final IMastGroupService iMastGroupService;
    private final HttpSession httpSession;

    private static final String GROUP_CHECK_MODE_SINGLE = "single";
    private static final String GROUP_CHECK_MODE_MULTIPLE = "multiple";
    private final DataSource dataSource;

    /**
     * 获取当前登录用户拥有的角色
     */
    public void getGroupList(String language, List<MastSystemDO> systemList) {
        String groupCheckMode = GROUP_CHECK_MODE_MULTIPLE;
        boolean groupCheckFlag = true;
        iMastSystemService.getByLang(language);
        if (groupCheckFlag) {
            setPretreatGroup(language);
        } else {
            setGroupInfo(groupCheckMode,language,systemList);
        }
    }

    private void setGroupInfo(String checkMode,String language,List<MastSystemDO> systemList){
        String userId = ShiroUtil.getUserId();
        systemList.forEach(system -> {
            String systemCode = system.getMsCsystemidPk();
            List<LoginUserGroupBO> mastGroupList = iMastGroupService.getUserGroupByLanguage(language,systemCode);
            if (CollUtil.isNotEmpty(mastGroupList)) {
                List<LoginGroupBO> listLoginGroup =CollUtil.newArrayList();

                for (LoginUserGroupBO loginUserGroupBO : mastGroupList) {
                    int nQueryCount = getAssembleSql(loginUserGroupBO, userId);
                    if (1 < nQueryCount) {
                        LoginGroupBO loginGroup = setLoginGroupBO(loginUserGroupBO);
                        listLoginGroup.add(loginGroup);
                        if (StrUtil.equalsIgnoreCase(checkMode, GROUP_CHECK_MODE_SINGLE)) {
                            break;
                        }
                    }
                }

                if (StrUtil.equalsIgnoreCase(checkMode,GROUP_CHECK_MODE_SINGLE) && CollUtil.isEmpty(listLoginGroup)) {
                    LoginUserGroupBO lastGroup = CollUtil.getLast(mastGroupList);
                    LoginGroupBO loginGroup = setLoginGroupBO(lastGroup);
                    listLoginGroup.add(loginGroup);
                }
                if (CollUtil.isNotEmpty(listLoginGroup)) {
                    PsSession session = (PsSession) httpSession.getAttribute(Constant.LOGIN_INFO);
                    session.setLoginGroups(MapUtil.<String,List<LoginGroupBO>>builder().put(systemCode,listLoginGroup).build());
                }
            }
        });
    }

    private void setPretreatGroup(String language) {
        String userId = ShiroUtil.getUserId();
        List<LoginGroupBO> lGroup = CollUtil.newArrayList();
        String systemCode = "";
        Map<String, List<LoginGroupBO>> hGroupMap = MapUtil.newHashMap(true);
        List<LoginUserGroupBO> mastGroupList = iMastGroupService.getPretreatGroupByLanguageUserId(language, userId);
        for (LoginUserGroupBO group : mastGroupList) {
            systemCode = group.getSystemId();
            if (hGroupMap.containsKey(systemCode)) {
                lGroup = hGroupMap.get(systemCode);
            }
            lGroup.add(setLoginGroupBO(group));
            hGroupMap.put(systemCode, lGroup);
        }
        if (CollUtil.isNotEmpty(lGroup)) {
            PsSession session = (PsSession) httpSession.getAttribute(Constant.LOGIN_INFO);
            session.setLoginGroups(hGroupMap);
        }
    }

    private LoginGroupBO setLoginGroupBO(LoginUserGroupBO loginUserGroupBO) {
        LoginGroupBO loginGroup = new LoginGroupBO();
        loginGroup.setSystemCode(loginUserGroupBO.getSystemId())
                .setSystemName(loginUserGroupBO.getSystemName())
                .setGroupCode(loginUserGroupBO.getGroupId())
                .setGroupName(loginUserGroupBO.getGroupName());
        return loginGroup;
    }

    private int getAssembleSql(LoginUserGroupBO groupBO, String userId) {
        String strQuery = groupBO.getPQuery() + " AND HD_CUSERID = "
                + " '" + userId + "'";
        int nQueryCount = 0;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            List<UserGroupBO> entityList = SqlExecutor.query(connection,strQuery, new UserGroupEntityListHandler());
            return entityList.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nQueryCount;
    }

}
