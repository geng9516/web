package jp.smartcompany.boot.configuration.security.authentication;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.job.modules.core.business.GroupBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastPasswordDO;
import jp.smartcompany.job.modules.core.pojo.entity.MastSystemDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.service.IMastPasswordService;
import jp.smartcompany.job.modules.core.service.IMastSystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("smartUserDetails")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartUserDetailsServiceImpl implements UserDetailsService {

    private final IMastAccountService accountService;
    private final ScCacheUtil scCacheUtil;
    private final IMastPasswordService passwordService;
    private final LRUCache<Object,Object> lruCache;
    private final TimedCache<String,Object> timedCache;
    private final GroupBusiness groupBusiness;
    private final IMastSystemService systemService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MastAccountDO account = accountService.getByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_EXIST.msg());
        }
        boolean isLocked =account.getMaNpasswordlock() == 1;
        boolean passwordExpired = false;
        //パスワード有効日数取得
        String sPasswordValid = scCacheUtil.getSystemProperty("PasswordValidPeriod");
        //パスワードﾞ有効日数が設定されていない場合
        if (sPasswordValid == null) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstant.PASSWORD_INVALID_DAYS_ERROR);
        }
        //パスワード入力最大許容回数取得
        String sLoginRetry = scCacheUtil.getSystemProperty("LoginRetry");
        //パスワード入力最大許容回数が設定されていない場合
        if (sLoginRetry == null) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstant.LOGIN_TRY_COUNT_ERROR);
        }
        String encodePassword = (String)lruCache.get(username+"password");

        List<MastPasswordDO> passwordHistories = passwordService.getUpdateDateByUsernamePassword(account.getMaCuserid(),encodePassword);
        if (CollUtil.isEmpty(passwordHistories)) {
            int iRetryCount = account.getMaNretrycounter();
            int retryCount = iRetryCount+1;
            Date loginTime = DateUtil.date();
            if (Integer.parseInt(sLoginRetry) < retryCount) {
                account.setMaNretrycounter(retryCount);
                account.setMaNpasswordlock(1);
                account.setMaDmodifieddate(loginTime);
                //アカウントマスタ更新処理
                accountService.updateById(account);
                //認証エラー（ロックアウト）
                isLocked = true;
            } else {
                account.setMaNretrycounter(iRetryCount);
                account.setMaNpasswordlock(0);
                account.setMaDmodifieddate(loginTime);
                accountService.updateById(account);
                //認証エラー（パスワード間違い）
                throw new BadCredentialsException(SecurityConstant.PASSWORD_ERROR);
            }
        } else {
            for (MastPasswordDO oPasswordEntity : passwordHistories) {
                //パスワード設定日取得
                Date now = DateUtil.date();
                //パスワード設定日取得
                Date oSetDay = oPasswordEntity.getMapDpwddate();
                oSetDay.setDate(oSetDay.getDate()
                        + Integer.parseInt(sPasswordValid));
                // 当前时间大于密码设定日时密码过期
                if (oSetDay.before(now)) {
                    //認証エラー（パスワード期間切れ）
                    passwordExpired = true;
                    break;
                }
            }
        }
        // 密码是否过期标识位，
        timedCache.put(username+"passwordExpired",passwordExpired);
        // 通常ログイン時のみ、認証ＯＫでパスワード間違い回数1以上の場合、0クリア
        // アカウントマスタを更新
        if (account.getMaNretrycounter() > 0) {
            account.setMaNretrycounter(0); // パスワード間違い回数0クリア
            account.setMaCmodifieruserid(account.getMaCuserid()); // 最終更新者
            accountService.updateById(account);
        }
        LoginAccountBO loginAccountBO = accountService.getAccountInfo(username);
        if (loginAccountBO == null) {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_EXIST.msg());
        }
        loginAccountBO.setMaCuserid(username);

        // 获取用户组
        List<MastSystemDO> systemList = (List<MastSystemDO>)lruCache.get(Constant.SYSTEM_LIST);
        if (systemList==null) {
            systemList =  systemService.getByLang("ja");
            lruCache.put(Constant.SYSTEM_LIST,systemList);
        }
        Map<String, List<LoginGroupBO>> loginGroups = groupBusiness.getGroupInfos(username,"multiple","ja",systemList);
        return new SmartUserDetails(loginAccountBO,loginGroups,encodePassword,isLocked,true);
    }

}
