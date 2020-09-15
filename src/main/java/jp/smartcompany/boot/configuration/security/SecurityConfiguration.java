package jp.smartcompany.boot.configuration.security;

import jp.smartcompany.boot.configuration.security.authentication.LoginEntryPoint;
import jp.smartcompany.boot.configuration.security.filter.SmartParameterFilter;
import jp.smartcompany.boot.configuration.security.handler.UrlAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Spring Security核心配置文件
 * @author Xiao Wenpeng
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UrlAccessDeniedHandler accessDeniedHandler;
    private final LoginEntryPoint loginEntryPoint;
    private final SmartLogoutHandler logoutHandler;
    private final SmartLogoutSuccessHandler logoutSuccessHandler;

    private final AuthenticationProcessingFilter authenticationProcessingFilter;
    private final BeforeAuthorizationFilter beforeAuthorizationFilter;

    private final SmartAccessDecisionManager accessDecisionManager;
    private final SmartFilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    private final SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用CSRF 开启跨域
        http.csrf().disable().cors()
                .and().formLogin().usernameParameter("username").passwordParameter("password")
                .and().logout().addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutSuccessHandler).invalidateHttpSession(true).deleteCookies().clearAuthentication(true).permitAll()
                // 防止iframe 造成跨域
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // url权限认证处理
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                })
                .and()
                .addFilterBefore(new SmartParameterFilter(), SecurityContextPersistenceFilter.class)
                .addFilterBefore(beforeAuthorizationFilter, BasicAuthenticationFilter.class)
                .addFilterAt(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                // 自定义过滤器在登录时认证用户名、密码
                .exceptionHandling()
                // 未登录认证异常
                .authenticationEntryPoint(loginEntryPoint)
                // 登录过后访问无权限的接口时自定义403响应内容
                .accessDeniedHandler(accessDeniedHandler);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.GET,
                securityProperties.getResourceList()
        );
    }

}
