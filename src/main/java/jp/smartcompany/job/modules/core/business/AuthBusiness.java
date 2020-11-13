package jp.smartcompany.job.modules.core.business;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jp.smartcompany.admin.groupappmanager.dto.GroupAppManagerPermissionDTO;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.IpUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.job.modules.core.CoreBean;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuBO;
import jp.smartcompany.job.modules.core.pojo.bo.MenuGroupBO;
import jp.smartcompany.job.modules.core.pojo.dto.ChangePasswordDTO;
import jp.smartcompany.job.modules.core.pojo.entity.LoginAuditDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastGroupapppermissionService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.LoginAuditService;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限验证Logic层
 * @author Xiao Wenpeng
 */
@Service(CoreBean.Business.AUTH)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthBusiness {

    private final IMastPasswordService iMastPasswordService;
    private final IMastAccountService iMastAccountService;
    private final LoginAuditService loginAuditService;
    private final IMastGroupapppermissionService iMastGroupapppermissionService;
    private final ScCacheUtil scCacheUtil;
    private final LRUCache<Object,Object> lruCache;
    private final TimedCache<String,Object> timedCache;

    public static final String LOGIN_PERMISSIONS = "loginAppPermissions";

    /**
     * 登录密码
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void changePassword(ChangePasswordDTO dto) {
        // 当日：パスワード変更を行っているか。（最新のパスワード 1件取得）
        List<MastPasswordDO> lMastPasswordList = iMastPasswordService.selectSinglePassword(dto.getUsername(), "1");
        MastPasswordDO passwordEntity =  lMastPasswordList.get(0);
        if (!StrUtil.equals(dto.getNewPassword(),dto.getRepeatPassword())) {
            throw new GlobalException(400,"パスワードの確認はまちがいます");
        }
        // 如果旧密码不正确则不允许修改
        if (!StrUtil.equals(passwordEntity.getMapCpassword(), DigestUtil.md5Hex(dto.getOldPassword()))) {
           throw new GlobalException(400,"古いパスワードは間違います");
        }
        if (StrUtil.equals(dto.getOldPassword(), dto.getNewPassword())) {
            throw new GlobalException(400,"古いパスワードと新しいパスワードは同じです");
        }
        if (CollUtil.isNotEmpty(lMastPasswordList)) {
            Date dDate = DateUtil.date();
            if (DateUtil.isSameDay(dDate,passwordEntity.getMapDpwddate())) {
                // 当日：複数回パスワード変更対応用パスワードマスタ 更新
                MastPasswordDO oEntity = setData(dto.getUsername(), DigestUtil.md5Hex(dto.getNewPassword()));
                oEntity.setMapId(passwordEntity.getMapId());
                iMastPasswordService.updateById(oEntity);
            } else {
                // パスワードマスタ 更新（履歴No +1）
                iMastPasswordService.updateHistory(dto.getUsername());
                // パスワードマスタ 新規登録
                iMastPasswordService.save(setData(dto.getUsername(), DigestUtil.md5Hex(dto.getNewPassword())));
            }
        } else {
            // パスワードマスタ 更新（履歴No +1）
            iMastPasswordService.updateHistory(dto.getUsername());
            // パスワードマスタ 新規登録
            iMastPasswordService.save(setData(dto.getUsername(), DigestUtil.md5Hex(dto.getNewPassword())));
        }
        timedCache.remove(SecurityUtil.getUsername()+"passwordExpired");
    }

    public void logout(HttpServletRequest req) {
        lruCache.clear();
        timedCache.clear();
        HttpSession session = req.getSession(false);
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (psSession!=null) {
            saveLoginInfo(false, psSession.getLoginUser());
        }
    }

    // 打卡时验证用户是否登录
    public LoginAccountBO basicStamping(String username,String password) {
        MastAccountDO mastAccountDO = iMastAccountService.getByUsername(username);
        //パラメータアカウントがない場合
        if (mastAccountDO == null) {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_EXIST.msg());
        }
        if (mastAccountDO.getMaNpasswordlock() == 1) {
            throw new LockedException(CoreError.USER_LOCK.msg());
        }
        String digestPassword = DigestUtil.md5Hex(password);
        boolean isValid = checkPassword(mastAccountDO,digestPassword);
        if (isValid) {
            return iMastAccountService.getAccountInfo(username);
        }
        return null;
    }

    public boolean checkPassword(MastAccountDO account, String password) {
        //パスワード有効日数取得
        String sPasswordValid = scCacheUtil.getSystemProperty("PasswordValidPeriod");
        //パスワードﾞ有効日数が設定されていない場合
        if (sPasswordValid == null) {
            throw new BadCredentialsException("PasswordValidPeriod");
        }
        //パスワード入力最大許容回数取得
        String sLoginRetry = scCacheUtil.getSystemProperty("LoginRetry");
        //パスワード入力最大許容回数が設定されていない場合
        if (sLoginRetry == null) {
            throw new BadCredentialsException("LoginRetry");
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

    private static final String ADMIN = "Admin";
    private static final String TMG_SETTINGS = "TMG_SETTINGS";

    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<MenuGroupBO> getUserPerms(String systemId,String language,List<String> groupIds,boolean isApprover) {

        List<GroupAppManagerPermissionDTO> tmgPermList = CollUtil.newArrayList();
        List<GroupAppManagerPermissionDTO> tmgAdminList;
        List<GroupAppManagerPermissionDTO> tmgInpList;
        List<GroupAppManagerPermissionDTO> adminList;
        List<GroupAppManagerPermissionDTO> tmgSettingsList;

        if (isApprover) {
            tmgPermList = iMastGroupapppermissionService.selectPermissionList(systemId, DateUtil.date(), groupIds, TmgUtil.Cs_SITE_ID_TMG_PERM, null, language);
        }
        tmgAdminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(), groupIds, TmgUtil.Cs_SITE_ID_TMG_ADMIN,null,language);
        tmgInpList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds, TmgUtil.Cs_SITE_ID_TMG_INP,null,language);
        adminList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds,ADMIN,null,language);
        tmgSettingsList = iMastGroupapppermissionService.selectPermissionList(systemId,DateUtil.date(),groupIds,TMG_SETTINGS ,null,language);

        // 加载topMenu Start
        List<GroupAppManagerPermissionDTO> topMenus = CollUtil.newArrayList();
        conventMenuList(TmgUtil.Cs_SITE_ID_TMG_ADMIN, topMenus, tmgPermList);
        conventMenuList(TmgUtil.Cs_SITE_ID_TMG_INP,topMenus,tmgInpList);
        conventMenuList(ADMIN,topMenus,adminList);
        conventMenuList(TMG_SETTINGS,topMenus,tmgSettingsList);
        // 加载topMenu End

        // 加载前端显示菜单
        List<MenuGroupBO> menuGroupList = CollUtil.newArrayList();

        for ( int i = 0; i < topMenus.size(); i++) {
            int moduleIndex=  i+1;
            GroupAppManagerPermissionDTO topMenu = topMenus.get(i);
            // 加载主菜单
            MenuGroupBO menuGroupBO = new MenuGroupBO();

            menuGroupBO.setOrderNum(topMenu.getMtrNseq());
            menuGroupBO.setPageId(topMenu.getMgpCsite());
            menuGroupBO.setPerms(topMenu.getMgpCobjectid());
            menuGroupBO.setOrderNum(topMenu.getMtrNseq());
            menuGroupBO.setUrl(topMenu.getMtrCurl());
            menuGroupBO.setJaName(topMenu.getObjectName());
            menuGroupBO.setIcon(topMenu.getMtrCimageurl());
            menuGroupBO.setType(topMenu.getType());
            menuGroupBO.setCompanyId("01");
            menuGroupBO.setCustomerId("01");

            menuGroupBO.setModuleIndex(moduleIndex);
            menuGroupBO.setSiteId(topMenu.getMgpCsite());

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
            if (StrUtil.equals(topMenu.getMgpCsite(), ADMIN)) {
                appList = adminList;
            }
            if (StrUtil.equals(topMenu.getMgpCsite(),TMG_SETTINGS)) {
                appList = tmgSettingsList;
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
                        secondMenuDO.setUrl(groupPerm.getMtrCurl());
                        secondMenuDO.setJaName(groupPerm.getObjectName());
                        secondMenuDO.setIcon(groupPerm.getMtrCimageurl());
                        secondMenuDO.setType(groupPerm.getType());
                        secondMenuDO.setCompanyId("01");
                        secondMenuDO.setCustomerId("01");

                        secondMenuDO.setSiteId(groupPerm.getMgpCsite());

                        CollUtil.addAllIfNotContains(secondMenuList,CollUtil.newArrayList(secondMenuDO));
                    }
                }
            }

            for (int i1 = 0; i1 < secondMenuList.size(); i1++) {
                MenuBO secondMenuDO = secondMenuList.get(i1);
                int menuIndex = i1+1;
                secondMenuDO.setMenuIndex(menuIndex);
            }

            menuGroupBO.setSecondMenuList(secondMenuList);

            menuGroupList.add(menuGroupBO);
        }
        return CollUtil.sort(menuGroupList,Comparator.comparingLong(MenuGroupBO::getOrderNum));
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

    private void conventMenuList(String siteId, List<GroupAppManagerPermissionDTO> topMenus, List<GroupAppManagerPermissionDTO> list) {
            List<GroupAppManagerPermissionDTO> permissionList = CollUtil.newArrayList();
            for (GroupAppManagerPermissionDTO permissionDTO : list) {
                if (StrUtil.equals(permissionDTO.getType(),"1") && StrUtil.equals(permissionDTO.getMgpCobjectid(),siteId)) {
                    permissionList.add(permissionDTO);
                }
            }
            GroupAppManagerPermissionDTO permissionItem = null;
            for (GroupAppManagerPermissionDTO groupAppManagerPermissionDTO : permissionList) {
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

    /**
     * 登録データをEntityへ設定する.
     * @param sUserid ユーザID
     * @param sPassword パスワード
     * @return パスワード変更処理用Entity
     */
    private MastPasswordDO setData(final String sUserid, final String sPassword) {
        Date dDate = DateUtil.date();
        MastPasswordDO passwdEntity = new MastPasswordDO();
        passwdEntity.setMapCuserid(sUserid);                         // ユーザID
        passwdEntity.setMapNhistory(0L);              // 履歴No
        passwdEntity.setMapCpassword(sPassword);                     // パスワード
        passwdEntity.setMapDpwddate(dDate); // パスワード設定日
        passwdEntity.setMapCmodifieruserid(sUserid);                       // 更新者
        passwdEntity.setMapDmodifieddate(dDate);  // 更新日
        return passwdEntity;
    }

    /**
     * パスワード許容回数チェック
     * @param poAccountEntity パスワードマスタ検索結果
     * @param psLoginRetry パスワード許容回数
     */
    private void checkPassWordPermission(MastAccountDO poAccountEntity, String psLoginRetry) {
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
            throw new LockedException(CoreError.USER_LOCK.msg());
        } else {
            poAccountEntity.setMaNretrycounter(iRetryCount);
            poAccountEntity.setMaNpasswordlock(0);
            poAccountEntity.setMaDmodifieddate(loginTime);
            iMastAccountService.updateById(poAccountEntity);
            //認証エラー（パスワード間違い）
            throw new BadCredentialsException(ErrorMessage.PASSWORD_INVALID.msg());
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
                throw new CredentialsExpiredException("このパスワードは有効期限を過ぎました、新しいパスワードを登録してください");
            }
        });
    }

}
