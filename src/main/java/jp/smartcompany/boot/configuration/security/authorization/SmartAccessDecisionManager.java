package jp.smartcompany.boot.configuration.security.authorization;

import cn.hutool.core.util.ArrayUtil;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartAccessDecisionManager implements AccessDecisionManager {

    private final SecurityProperties securityProperties;

    /**
     * @param authentication: 当前登录用户的角色信息
     * @param object: 请求url信息
     * @param needRoles: `SmartFilterInvocationSecurityMetadataSource`中的getAttributes方法传来的，表示当前请求需要的角色（可能有多个）
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> needRoles) throws AccessDeniedException, AuthenticationException {
        // 遍历角色
        for (ConfigAttribute ca : needRoles) {
            // ① 当前url请求需要的权限,经过认证的用户才会进入步骤2进行授权判断
            String needRole = ca.getAttribute();
            String requestUrl = ((FilterInvocation) object).getRequestUrl();
            // 已经登录的用户
            String[] urlList = securityProperties.getOnlyAuthenticationList();
            if (authentication instanceof UsernamePasswordAuthenticationToken && authentication.isAuthenticated()) {
                // 如果是只需要认证而不需要授权的url则通过
                if (ArrayUtil.contains(securityProperties.getOnlyAuthenticationList(),requestUrl)) {
                   return;
                }
                PathMatcher matcher = new AntPathMatcher();
                if (requestUrl.contains("?")) {
                    requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("?"));
                }
                for (String url : urlList) {
                    if (matcher.match(url, requestUrl)) {
                        return;
                    }
                }
            // 如果是匿名用户则抛出异常
            } else {
                throw new BadCredentialsException("forbiddenAnonymousUser");
            }
            return;
            // ② 当前用户所具有的角色
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                // 只要包含其中一个角色即可访问
//                if (authority.getAuthority().equals(needRole)) {
//                    return;
//                }
//            }
        }
        // 当前访问用户一个权限都没有时
        throw new AccessDeniedException("システム処理中にエラーが発生しました。システム管理者にお問い合わせください。");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
