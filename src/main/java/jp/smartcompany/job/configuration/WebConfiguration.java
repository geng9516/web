package jp.smartcompany.job.configuration;

import jp.smartcompany.job.interceptor.AuditInterceptor;
import jp.smartcompany.job.interceptor.SysSessionInterceptor;
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
    private final SysSessionInterceptor sysSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor).excludePathPatterns("/login","/favicon","/log/**");
        registry.addInterceptor(sysSessionInterceptor).addPathPatterns("/","/sys/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods(
                        "GET",
                        "POST",
                        "OPTIONS"
                ).maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        super.addResourceHandlers(registry);
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:///D:/IdeaWorkspace/office-next-BE/upload/");
    }

}