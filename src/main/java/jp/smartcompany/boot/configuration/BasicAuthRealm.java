package jp.smartcompany.boot.configuration;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.enums.ErrorMessage;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasicAuthRealm extends AuthorizingRealm {

    private final IMastAccountService iMastAccountService;
    private final AuthBusiness authBusiness;

    /**
     * 身份验证（登录验证）
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String username = (String) authToken.getPrincipal();
        Object credentials = authToken.getCredentials();
        if (credentials==null){
            return null;
        }
        MastAccountDO mastAccountDO = iMastAccountService.getByUsername(username);
        if (mastAccountDO == null) {
            throw new UnknownAccountException(ErrorMessage.USER_NOT_EXIST.msg());
        }
        if (mastAccountDO.getMaNpasswordlock() == 1) {
            throw new LockedAccountException(CoreError.USER_LOCK.msg());
        }
        String password = new String((char[])credentials);

        String digestPassword = new Md5Hash(password).toHex();
        boolean isValid = authBusiness.checkPassword(mastAccountDO,digestPassword);
        if (isValid) {
          LoginAccountBO loginAccountBO = iMastAccountService.getAccountInfo(username);
          return new SimpleAuthenticationInfo(loginAccountBO,digestPassword,getName());
        }
        return null;
    }

    /**
     * 授权认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
        LoginAccountBO loginAccountBO =(LoginAccountBO) principals.getPrimaryPrincipal();
        if (loginAccountBO!=null){
            PsSession psSession = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
            Map<String, List<LoginGroupBO>> groups = psSession.getLoginGroups();
            List<LoginGroupBO> groupList = CollUtil.newArrayList();
            groups.forEach((key,value)-> {
                if (StrUtil.equals(psSession.getLoginCompany(),key)) {
                    CollUtil.addAllIfNotContains(groupList,value);
                }
            });
            List<String> groupCodes =groupList.stream().map(LoginGroupBO::getGroupCode).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(groupList)) {
                authInfo.setRoles(CollUtil.newHashSet(groupCodes));
            }
            Set<String> perms = authBusiness.getAllUserPerms(psSession.getLoginCompany(),psSession.getLanguage(),groupCodes);
            authInfo.setStringPermissions(perms);
            return authInfo;
        } else {
          throw new UnauthenticatedException();
        }
    }

}
