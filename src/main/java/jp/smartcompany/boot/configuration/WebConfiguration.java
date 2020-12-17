package jp.smartcompany.boot.configuration;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.boot.interceptor.AuditInterceptor;
import jp.smartcompany.boot.interceptor.LoginInfoInterceptor;
import jp.smartcompany.boot.interceptor.SmartParameterInterceptor;
import jp.smartcompany.boot.util.ScCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Configuration
@RequiredArgsConstructor
public class WebConfiguration extends WebMvcConfigurationSupport {

    private final AuditInterceptor auditInterceptor;
    private final LoginInfoInterceptor loginInfoInterceptor;
    private final SmartParameterInterceptor smartParameterInterceptor;
    private final ScCacheUtil cacheUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).excludePathPatterns("/login","/logout","/expirePassword","/favicon.ico","/sys/log/**","/static/**","/error");
        registry.addInterceptor(smartParameterInterceptor).excludePathPatterns("/static/**");

        String[] loginInterceptorUrlPattern = {
                "/",
                "/index",
                "/sys/**",
                "/tmg_admin/**",
                "/tmg_inp/**",
                "/admin/**",
                "/expirePassword",
                "/changeExpirePassword",
                "/changePassword",
                "/tmg_perm/**",
                "/tmg_settings/**"
        };
        registry.addInterceptor(loginInfoInterceptor).addPathPatterns(loginInterceptorUrlPattern);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/favicon.ico");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/favicon.ico");
        List<String> uploadFilePaths = CollUtil.newArrayList(
                "file:"+cacheUtil.getSystemProperty("TMG_NOTICE_BOARD_UPLOAD_PATH").replaceAll("\\\\","/")
        );
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(
                        uploadFilePaths.toArray(new String[0])
                );
        super.addResourceHandlers(registry);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

}