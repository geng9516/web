package jp.smartcompany.job.modules.core.business;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.*;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.pojo.bo.MenuBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    public static final String LOGIN_PERMISSIONS = "loginAppPermissions";

    public boolean checkPassword(MastAccountDO account, String password) throws AuthenticationException {
        Date passwordSetDate = iMastPasswordService.getUpdateDateByUsernamePassword(account.getMaCuserid(),password);
        // 密码错误
        if (passwordSetDate == null) {
// todo: 判断尝试重新登录次数
//            int retryCount = account.getMaNretrycounter();
//            if () {
//                account.setMaNpasswordlock(1).setMaDmodifieddate(DateUtil.date());
//                iMastAccountService.updateById(account);
//            }
//            account.setMaNretrycounter(retryCount + 1)
//                    .setMaNpasswordlock(0)
//                    .setMaDmodifieddate(DateUtil.date());
//            iMastAccountService.updateById(account);
            throw new IncorrectCredentialsException(ErrorMessage.PASSWORD_INVALID.msg());
        // 判断密码是否已经过了使用期限
        } else {
            if (SysDateUtil.isLess(passwordSetDate, DateUtil.date())) {
               // 抛出密码过期异常
            }
        }
        if (account.getMaNretrycounter() > 0) {
            account.setMaNretrycounter(0).setMaCmodifieruserid(account.getMaCuserid());
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

    public List<MenuGroupBO> getUserPerms(String systemId,String language,List<String> groupIds) {
        List<GroupAppManagerPermissionDTO> tmgPermList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, TmgUtil.Cs_SITE_ID_TMG_PERM,null,language);
        List<GroupAppManagerPermissionDTO> tmgAdminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(), groupIds, TmgUtil.Cs_SITE_ID_TMG_ADMIN,null,language);
        List<GroupAppManagerPermissionDTO> tmgInpList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, TmgUtil.Cs_SITE_ID_TMG_INP,null,language);
        List<GroupAppManagerPermissionDTO> adminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds,"Admin",null,language);

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
}
