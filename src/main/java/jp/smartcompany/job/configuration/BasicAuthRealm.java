package jp.smartcompany.job.configuration;

import jp.smartcompany.job.enums.ErrorMessage;
import jp.smartcompany.job.modules.core.CoreError;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import jp.smartcompany.job.modules.core.service.IMastAccountService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
          return new SimpleAuthenticationInfo(mastAccountDO,digestPassword,getName());
        }
        return null;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

}
