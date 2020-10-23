package jp.smartcompany.boot.configuration;

import jp.smartcompany.boot.interceptor.AuditInterceptor;
import jp.smartcompany.boot.interceptor.LoginInfoInterceptor;
import jp.smartcompany.boot.interceptor.SmartParameterInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Xiao Wenpeng
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WebConfiguration extends WebMvcConfigurationSupport {

    private final AuditInterceptor auditInterceptor;
    private final LoginInfoInterceptor loginInfoInterceptor;
    private final SmartParameterInterceptor smartParameterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).excludePathPatterns("/login","/logout","/expirePassword","/favicon.ico","/sys/log/**","/static/**","/error");
        registry.addInterceptor(smartParameterInterceptor).excludePathPatterns("/static/**");

        String[] loginInterceptorUrlPattern = {"/",
                "/index", "/sys/**",
                "/tmg_admin/**",
                "/tmg_inp/**",
                "/admin/**",
                "/expirePassword",
                "/changeExpirePassword",
                "/changePassword",
                "/tmg_perm/**"};
        registry.addInterceptor(loginInfoInterceptor).addPathPatterns(loginInterceptorUrlPattern);
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