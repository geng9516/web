package jp.smartcompany.boot.configuration.security.authorization;

import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SecurityProperties securityProperties;

    /***
     * 返回该url所需要的用户权限信息
     * @param object: 储存请求url信息
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取当前请求url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 放行不需要授权的url
        for (String urlPattern : securityProperties.getWhiteList()) {
            PathMatcher matcher = new AntPathMatcher();
            if (requestUrl.contains("?")) {
                requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("?"));
            }
            if (matcher.match(urlPattern, requestUrl)) {
                return null;
            }
        }
        // 如果数据中没有找到相应url资源则为非法访问，要求用户登录再进行操作
        return SecurityConfig.createList(SecurityConstant.ANONYMOUS_USER);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
