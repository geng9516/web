package jp.smartcompany.boot.configuration.security;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * spring-security路径配置（将application.yml里的配置映射过来）
 * @author Xiao Wenpeng
 */
@EnableConfigurationProperties(value = SecurityProperties.class)
@ConfigurationProperties(prefix = "security")
@Getter
@Setter
@ToString
@Component
public class SecurityProperties {

    private String[] whiteList;
    private String[] resourceList;
    private String[] onlyAuthenticationList;
    private String[] csrfWhiteList;

    private String logoutSuccessUrl;
    private String loginSuccessUrl;
    private String loginFailureUrl;
    private String accessDeniedUrl;

}
