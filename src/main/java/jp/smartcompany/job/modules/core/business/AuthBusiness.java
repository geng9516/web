package jp.smartcompany.job.modules.core.business;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.pojo.bo.MenuBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限验证Logic层
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthBusiness {

    private final IMastPasswordService iMastPasswordService;
    private final IMastAccountService iMastAccountService;
    private final LoginAuditService loginAuditService;
    private final IMastGroupapppermissionService iMastGroupapppermissionService;
    private final LRUCache<Object,Object> lruCache;
    private final ScCacheUtil scCacheUtil;
    private final DataSource dataSource;

    public static final String LOGIN_PERMISSIONS = "loginAppPermissions";

    public boolean checkPassword(MastAccountDO account, String password) throws AuthenticationException {
        //パスワード有効日数取得
        String sPasswordValid = scCacheUtil.getSystemProperty("PasswordValidPeriod");
        //パスワードﾞ有効日数が設定されていない場合
        if (sPasswordValid == null) {
            throw new AuthenticationException("PasswordValidPeriod");
        }
        //パスワード入力最大許容回数取得
        String sLoginRetry = scCacheUtil.getSystemProperty("LoginRetry");
        //パスワード入力最大許容回数が設定されていない場合
        if (sLoginRetry == null) {
            throw new AuthenticationException("LoginRetry");
        }
        List<MastPasswordDO> passwordHistories = iMastPasswordService.getUpdateDateByUsernamePassword(account.getMaCuserid(),password);
        if (CollUtil.isEmpty(passwordHistories)) {
            checkPassWordPermission(account, sLoginRetry);
        } else {
            checkPassWordPeriod(passwordHistories, sPasswordValid);
        }
        // 通常ログイン時のみ、認証ＯＫでパスワード間違い回数1以上の場合、0クリア
        // アカウントマスタを更新
        if (account.getMaNretrycounter() > 0) {
            account.setMaNretrycounter(0); // パスワード間違い回数0クリア
            account.setMaCmodifieruserid(account.getMaCuserid()); // 最終更新者
            iMastAccountService.updateById(account);
        }
        return true;
    }

    public void login(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        UsernamePasswordToken token = new UsernamePasswordToken(username, loginDTO.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
    }

    public void logout() {
        String username = ShiroUtil.getUsername();
        saveLoginInfo(false, username);
        ShiroUtil.getSubject().logout();
    }

    public void saveLoginInfo(boolean status,String username) {
        LoginAuditDO loginAuditDO = new LoginAuditDO();
        HttpServletRequest request = ContextUtil.getHttpRequest();
        Date now = DateUtil.date();
        loginAuditDO
                .setOperation(status? Constant.LOG_LOGIN:Constant.LOG_LOGOUT)
                .setStatus(status)
                .setUsername(username);
        loginAuditDO.setCreateTime(now);
        loginAuditDO.setUpdateTime(now);
        if (request != null){
            loginAuditDO.setUserAgent(request.getHeader(Constant.KEY_USER_AGENT));
            loginAuditDO.setIp(IpUtil.getRemoteAddr(request));
        }
        loginAuditService.save(loginAuditDO);
    }

    public List<MenuGroupBO> getUserPerms(String systemId,String language,List<String> groupIds) throws SQLException {

        List<Object> commonParams = CollUtil.newArrayList();
        String sql = "SELECT DISTINCT MGP_CGROUPID,NVL(MGP_COBJECTID, MTR_COBJECTID) MGP_COBJECTID,NVL(MGP_CSITE, MTR_CSITEID) MGP_CSITE,NVL(MGP_CAPP, MTR_CAPPID) MGP_CAPP,NVL(MGP_CSUBAPP, MTR_CSUBAPPID) MGP_CSUBAPP," +
                "NVL(MGP_CBUTTON, MTR_CBUTTONID) MGP_CBUTTON,NVL(MGP_CSCREEN, MTR_CSCREENID) MGP_CSCREEN,DECODE(MGP_CPERMISSION, '1', DECODE(MGP_CREJECT, '1', '2', '1'), '0') PERMISSION," +
                "PSMASTER.FUNC_GET_OBJ_NAME (MTR_CSITEID,MTR_CAPPID,MTR_CSUBAPPID,MTR_CSCREENID,MTR_CBUTTONID,'"+language+"') OBJECTNAME, MTR_CTYPE TYPE,MG_NWEIGHTAGE,MTR_NSEQ,MTR_CURL2,MTR_ICON,MTR_ID " +
                "FROM " +
                "(" +
                    "MAST_APPTREE " +
                    "LEFT OUTER JOIN MAST_GROUPAPPPERMISSION ON MTR_COBJECTID = MGP_COBJECTID " +
                    "AND MTR_CSYSTEMID = MGP_CSYSTEMID "+
                    "AND MGP_CGROUPID IN (";
                        String groupStr = "";
                        if (CollUtil.isNotEmpty(groupIds)) {
                            for (String groupId : groupIds) {
                                commonParams.add(groupId);
                                groupStr+="?,";
                            }
                        }
                        sql+=groupStr.substring(0,groupStr.length()-1);
                    sql+=") ";
                    sql+="AND MGP_DSTARTDATE <= ? " +
                    "AND MGP_DENDDATE >= ?" +
                    "LEFT JOIN MAST_GROUP ON MG_CGROUPID_PK = MGP_CGROUPID " +
                    "AND MG_CSYSTEMID_CK_FK = MGP_CSYSTEMID " +
                    "AND MG_DSTARTDATE <= ?" +
                    "AND MG_DENDDATE <= ?" +
                ") " +
                "WHERE " +
                "MTR_CSYSTEMID = ? " +
                "AND MTR_CTYPE <> '0' " +
                "AND MTR_CSITEID = ? "+
                "ORDER BY MTR_NSEQ,MG_NWEIGHTAGE,MGP_CGROUPID";
        Connection conn = null;
        String date = SysUtil.transDateToString(DateUtil.date());
        commonParams.add(date);
        commonParams.add(date);
        commonParams.add(date);
        commonParams.add(date);
        commonParams.add(systemId);
        List<GroupAppManagerPermissionDTO> tmgPermList = CollUtil.newArrayList();
        List<GroupAppManagerPermissionDTO> tmgAdminList = CollUtil.newArrayList();
        List<GroupAppManagerPermissionDTO> tmgInpList =CollUtil.newArrayList();
        List<GroupAppManagerPermissionDTO> adminList = CollUtil.newArrayList();
        try {
            conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            conn.setReadOnly(true);
            List<Object> tmgPermParams = CollUtil.newArrayList();
            tmgPermParams.addAll(commonParams);
            tmgPermParams.add(TmgUtil.Cs_SITE_ID_TMG_PERM);
            Object[] permParams = new String[tmgPermParams.size()];
            for (int i = 0; i < tmgPermParams.size(); i++) {
                permParams[i] = tmgPermParams.get(i);
            }
            List<Entity> tmgPermEntityList = SqlExecutor.query(preparedStatement,new EntityListHandler(),permParams);
            convertDbData(tmgPermList, tmgPermEntityList);

            List<Object> tmgAdminParams = CollUtil.newArrayList();
            tmgAdminParams.addAll(commonParams);
            tmgAdminParams.add( TmgUtil.Cs_SITE_ID_TMG_ADMIN);
            Object[] adminParams = new String[tmgAdminParams.size()];
            for (int i = 0; i < tmgAdminParams.size(); i++) {
                adminParams[i] = tmgAdminParams.get(i);
            }
            List<Entity> tmgAdminEntityList=  SqlExecutor.query(preparedStatement,new EntityListHandler(),adminParams);
            convertDbData(tmgAdminList, tmgAdminEntityList);


            List<Object> tmgInpParams = CollUtil.newArrayList();
            tmgInpParams.addAll(commonParams);
            tmgInpParams.add( TmgUtil.Cs_SITE_ID_TMG_INP);
            Object[] inpParams = new String[tmgInpParams.size()];
            for (int i = 0; i < tmgInpParams.size(); i++) {
                inpParams[i] = tmgInpParams.get(i);
            }
            List<Entity> tmgInpEntityList=  SqlExecutor.query(preparedStatement,new EntityListHandler(),inpParams);
            convertDbData(tmgInpList,tmgInpEntityList);

            List<Object> administratorParams = CollUtil.newArrayList();
            administratorParams.addAll(commonParams);
            administratorParams.add("Admin");
            Object[] admParams = new String[administratorParams.size()];
            for (int i = 0; i < administratorParams.size(); i++) {
                admParams[i] = administratorParams.get(i);
            }
            List<Entity> adminEntityList=  SqlExecutor.query(preparedStatement,new EntityListHandler(),admParams);
            convertDbData(adminList,adminEntityList);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("获取用户菜单信息失败");
        } finally {
            conn.setReadOnly(false);
            if (conn!=null) {
                conn.close();
            }
        }

//        List<GroupAppManagerPermissionDTO> tmgPermList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, TmgUtil.Cs_SITE_ID_TMG_PERM,null,language);
//        List<GroupAppManagerPermissionDTO> tmgAdminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(), groupIds, TmgUtil.Cs_SITE_ID_TMG_ADMIN,null,language);
//        List<GroupAppManagerPermissionDTO> tmgInpList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, TmgUtil.Cs_SITE_ID_TMG_INP,null,language);
//        List<GroupAppManagerPermissionDTO> adminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds,"Admin",null,language);

        // 加载topMenu Start
        List<GroupAppManagerPermissionDTO> topMenus = CollUtil.newArrayList();

        if (CollUtil.isNotEmpty(tmgPermList)) {
            List<GroupAppManagerPermissionDTO> permPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgPermList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                    permPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : permPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(tmgAdminList)) {
            List<GroupAppManagerPermissionDTO> tmgAdminPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgAdminList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                    tmgAdminPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgAdminPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(adminList)) {
            List<GroupAppManagerPermissionDTO> adminPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : adminList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),"Admin")) {
                    adminPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : adminPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }

        if (CollUtil.isNotEmpty(tmgInpList)) {
            List<GroupAppManagerPermissionDTO> tmgInpPermissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgInpList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getType(),"1") && StrUtil.equals(groupAppManagerPermissionDTO.getMgpCobjectid(),TmgUtil.Cs_SITE_ID_TMG_INP)) {
                    tmgInpPermissionList.add(groupAppManagerPermissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : tmgInpPermissionList) {
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"2")) {
                    permissionItem = null;
                    break;
                }
                if (StrUtil.equals(groupAppManagerPermissionDTO.getPermission(),"1") && permissionItem==null){
                    permissionItem = groupAppManagerPermissionDTO;
                }
            }
            if (permissionItem!=null) {
                topMenus.add(permissionItem);
            }
        }
        // 加载topMenu End
        // 加载前端显示菜单
        List<MenuGroupBO> menuGroupList = CollUtil.newArrayList();
        for (GroupAppManagerPermissionDTO topMenu : topMenus) {
            // 加载主菜单
            MenuGroupBO menuGroupBO = new MenuGroupBO();
            MenuBO topMenuDO = new MenuBO();
            topMenuDO.setPageId(topMenu.getMgpCsite());
            topMenuDO.setPerms(topMenu.getMgpCobjectid());
            topMenuDO.setOrderNum(topMenu.getMtrNseq());
            topMenuDO.setUrl(topMenu.getMtrCurl2());
            topMenuDO.setJaName(topMenu.getObjectName());
            topMenuDO.setIcon(topMenu.getMtrIcon());
            topMenuDO.setType(topMenu.getType());
            topMenuDO.setCompanyId("01");
            topMenuDO.setCustomerId("01");
            menuGroupBO.setMenu(topMenuDO);
            // 加载二级导航
            List<GroupAppManagerPermissionDTO> appList = CollUtil.newArrayList();
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_INP)) {
                appList = tmgInpList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_ADMIN)) {
                appList = tmgAdminList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), TmgUtil.Cs_SITE_ID_TMG_PERM)) {
                appList = tmgPermList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(), "Admin")) {
                appList = adminList;
            }
            List<MenuBO> secondMenuList = CollUtil.newArrayList();
            List<GroupAppManagerPermissionDTO> secondAppList = appList.stream().filter(item -> StrUtil.equals(item.getType(), "3")).collect(Collectors.toList());

            List<List<GroupAppManagerPermissionDTO>> secondGroupAppList = CollUtil.groupByField(secondAppList,"mgpCobjectid");
            for (List<GroupAppManagerPermissionDTO> groupPerms : secondGroupAppList) {
                for (GroupAppManagerPermissionDTO groupPerm : groupPerms) {
                    String permission = groupPerm.getPermission();
                    if (StrUtil.equals(permission,"2")) {
                        break;
                    }
                    if (StrUtil.equals(permission,"0")) {
                        continue;
                    }
                    if (StrUtil.equals(permission,"1")) {
                        MenuBO secondMenuDO = new MenuBO();
                        secondMenuDO.setPageId(groupPerm.getMgpCapp());
                        secondMenuDO.setPerms(groupPerm.getMgpCobjectid());
                        secondMenuDO.setOrderNum(groupPerm.getMtrNseq());
                        secondMenuDO.setUrl(groupPerm.getMtrCurl2());
                        secondMenuDO.setJaName(groupPerm.getObjectName());
                        secondMenuDO.setIcon(groupPerm.getMtrIcon());
                        secondMenuDO.setType(groupPerm.getType());
                        secondMenuDO.setCompanyId("01");
                        secondMenuDO.setCustomerId("01");
                        secondMenuDO.setMenuId(groupPerm.getMtrId());
                        CollUtil.addAllIfNotContains(secondMenuList,CollUtil.newArrayList(secondMenuDO));
                    }
                }
            }

            menuGroupBO.setSecondMenuList(secondMenuList);

            menuGroupList.add(menuGroupBO);
        }
        return menuGroupList;
    }

    private void convertDbData(List<GroupAppManagerPermissionDTO> tmgPermList, List<Entity> tmgPermEntityList) {
        tmgPermEntityList.forEach(entity -> {
                GroupAppManagerPermissionDTO dto = new GroupAppManagerPermissionDTO();
                dto.setMtrId(((BigDecimal)entity.get("MTR_ID")).longValue());
                dto.setPermission((String)entity.get("PERMISSION"));
                dto.setMtrIcon((String)entity.get("MTR_ICON"));
                dto.setMtrCurl2((String)entity.get("MTR_CURL2"));
                dto.setMtrNseq(((BigDecimal)entity.get("MTR_NSEQ")).longValue());
                if (entity.get("MG_NWEIGHTAGE")!=null) {
                    dto.setMgNweightage(((BigDecimal) entity.get("MG_NWEIGHTAGE")).longValue());
                }
                dto.setType((String)entity.get("TYPE"));
                dto.setObjectName((String)entity.get("OBJECTNAME"));
                dto.setMgpCscreen((String)entity.get("MGP_CSCREEN"));
                dto.setMgpCbutton((String)entity.get("MGP_CBUTTON"));
                dto.setMgpCsubapp((String)entity.get("MGP_CSUBAPP"));
                dto.setMgpCapp((String)entity.get("MGP_CAPP"));
                dto.setMgpCsite((String)entity.get("MGP_CSITE"));
                dto.setMgpCobjectid((String)entity.get("MGP_COBJECTID"));
                dto.setMgpCgroupid((String)entity.get("MGP_CGROUPID"));
                tmgPermList.add(dto);
        });
    }

    public Set<String> getAllUserPerms(String systemId, String language, List<String> groupIds) {
        Set<String> perms =(Set<String>)lruCache.get(LOGIN_PERMISSIONS);
        if (CollUtil.isEmpty(perms)) {
            List<GroupAppManagerPermissionDTO> permissionList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, null,null,language);
            List<List<GroupAppManagerPermissionDTO>> secondGroupAppList = CollUtil.groupByField(permissionList,"mgpCobjectid");
            for (List<GroupAppManagerPermissionDTO> groupPerms : secondGroupAppList) {
                for (GroupAppManagerPermissionDTO groupPerm : groupPerms) {
                    String permission = groupPerm.getPermission();
                    if (StrUtil.equals(permission,"2")) {
                        break;
                    }
                    if (StrUtil.equals(permission,"0")) {
                        continue;
                    }
                    if (StrUtil.equals(permission,"1")) {
                        perms.add(permission);
                    }
                }
            }
            lruCache.put(LOGIN_PERMISSIONS,perms);
        }
        return perms;
    }

    /**
     * パスワード許容回数チェック
     * @param poAccountEntity パスワードマスタ検索結果
     * @param psLoginRetry パスワード許容回数
     */
    private void checkPassWordPermission(MastAccountDO poAccountEntity, String psLoginRetry) throws UnauthenticatedException {
        //パスワード間違い回数取得
        int iRetryCount = poAccountEntity.getMaNretrycounter();
        int retryCount = iRetryCount+1;
        Date loginTime = DateUtil.date();
        if (Integer.parseInt(psLoginRetry) < retryCount) {
            poAccountEntity.setMaNretrycounter(retryCount);
            poAccountEntity.setMaNpasswordlock(1);
            poAccountEntity.setMaDmodifieddate(loginTime);
            //アカウントマスタ更新処理
            iMastAccountService.updateById(poAccountEntity);
            //認証エラー（ロックアウト）
            throw new LockedAccountException(CoreError.USER_LOCK.msg());
        } else {
            poAccountEntity.setMaNretrycounter(iRetryCount);
            poAccountEntity.setMaNpasswordlock(0);
            poAccountEntity.setMaDmodifieddate(loginTime);
            iMastAccountService.updateById(poAccountEntity);
            //認証エラー（パスワード間違い）
            throw new IncorrectCredentialsException(ErrorMessage.PASSWORD_INVALID.msg());
        }
    }

    /**
     * パスワード期間チェック
     * @param poPasswordData パスワードマスタ検索結果
     * @param psPasswordValid パスワード有効日数
     */
    private void checkPassWordPeriod(List<MastPasswordDO> poPasswordData, String psPasswordValid) {
        poPasswordData.forEach(oPasswordEntity -> {
            //パスワード設定日取得
            Date now = DateUtil.date();
//            Date oSetDay = oPasswordEntity.getMapDpwddate();
//            Date expireDay = DateUtil.offsetDay(oSetDay,Integer.parseInt(psPasswordValid));
            //パスワード設定日取得
            Date oSetDay = oPasswordEntity.getMapDpwddate();
            oSetDay.setDate(oSetDay.getDate()
                    + Integer.parseInt(psPasswordValid));
            // 当前时间大于密码设定日时密码过期
            if (oSetDay.before(now)) {
                //認証エラー（パスワード期間切れ）
                throw new ExpiredCredentialsException("パスワード期間切れ");
            }
        });
    }

}
