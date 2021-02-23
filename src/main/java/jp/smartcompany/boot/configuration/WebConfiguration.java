package jp.smartcompany.boot.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jp.smartcompany.boot.interceptor.AuditInterceptor;
import jp.smartcompany.boot.interceptor.LoginInfoInterceptor;
import jp.smartcompany.boot.interceptor.SmartParameterInterceptor;
import jp.smartcompany.boot.util.ScCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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
        registry.addInterceptor(auditInterceptor).excludePathPatterns("/upload/**","/login","/logout","/expirePassword","/favicon.ico","/sys/log/**","/static/**","/error");
        registry.addInterceptor(smartParameterInterceptor).excludePathPatterns("/upload/**","/static/**","/error");

        String[] loginInterceptorUrlPattern = {
                "/",
                "/index",
                "/sys/**",
                "/tmg_admin/**",
                "/tmg_inp/**",
                "/personalinformation/**",
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
        String[] uploadFilePaths = {
                "file:" + cacheUtil.getSystemProperty("TMG_NOTICE_BOARD_UPLOAD_PATH") + "\\",
                "file:" + cacheUtil.getSystemProperty("TMG_RICH_TEXT_NOTICE_BOARD_UPLOAD_PATH") + "\\",
                "file:" + cacheUtil.getSystemProperty("CSVFilePath")+"\\"
        };
        registry.addResourceHandler("/upload/**").addResourceLocations(
                uploadFilePaths
        );
        registry.setOrder(1);
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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 生成JSON时,将所有Long转换成String,防止返回long类型数据时出现精度丢失的问题
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        ObjectMapper mapper =
                Jackson2ObjectMapperBuilder.json()
                        .modules(
                                simpleModule
                        ).build();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES,true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.ALWAYS);
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
        mapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));

        converters.add(new MappingJackson2HttpMessageConverter(mapper));
    }

}