package jp.smartcompany.boot.configuration;

import cn.hutool.core.util.ArrayUtil;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.interceptor.AuditInterceptor;
import jp.smartcompany.boot.interceptor.LoginInfoInterceptor;
import jp.smartcompany.boot.interceptor.SmartParameterInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Xiao Wenpeng
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfiguration extends WebMvcConfigurationSupport {

    private final AuditInterceptor auditInterceptor;
    private final LoginInfoInterceptor loginInfoInterceptor;
    private final SecurityProperties securityProperties;
    private final SmartParameterInterceptor smartParameterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).excludePathPatterns("/login","/logout","/expirePassword","/favicon.ico","/sys/log/**","/static/**","/error");
        registry.addInterceptor(smartParameterInterceptor).excludePathPatterns("/static/**");
        String[] configAuthenticationList = securityProperties.getOnlyAuthenticationList();
        String[] onlyAuthenticationList = new String[configAuthenticationList.length+1];
        System.arraycopy(configAuthenticationList, 0, onlyAuthenticationList, 0, configAuthenticationList.length);
        // 单独添加承认site的匹配路径
        onlyAuthenticationList[onlyAuthenticationList.length-1] = "/tmg_perm/**";
        registry.addInterceptor(loginInfoInterceptor).addPathPatterns(onlyAuthenticationList);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/favicon.ico");
        super.addResourceHandlers(registry);
    }



}