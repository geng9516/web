package jp.smartcompany.boot.configuration.security;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.authentication.AuthenticationProcessingFilter;
import jp.smartcompany.boot.configuration.security.authentication.LoginEntryPoint;
import jp.smartcompany.boot.configuration.security.authentication.SmartAuthenticationManager;
import jp.smartcompany.boot.configuration.security.handler.SmartLogoutHandler;
import jp.smartcompany.boot.configuration.security.handler.SmartLogoutSuccessHandler;
import jp.smartcompany.boot.configuration.security.handler.UrlAccessDeniedHandler;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.RequestDispatcher;

/**
 * Spring Security核心配置文件
 * @author Xiao Wenpeng
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UrlAccessDeniedHandler accessDeniedHandler;
    private final LoginEntryPoint loginEntryPoint;
    private final SmartLogoutHandler logoutHandler;
    private final SmartLogoutSuccessHandler logoutSuccessHandler;
    private final ObjectMapper objectMapper;
    private final AuthBusiness authBusiness;
    private final SecurityProperties securityProperties;
    private final SmartAuthenticationManager authenticationManager;

    public AuthenticationProcessingFilter authenticationProcessingFilter() throws Exception {
        AuthenticationProcessingFilter authFilter = new AuthenticationProcessingFilter(objectMapper);
        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        // 登录成功后的回调
        authFilter.setAuthenticationSuccessHandler((req, resp, auth) -> {
            authBusiness.saveLoginInfo(true,auth.getName());
            if (SysUtil.isAjaxRequest(req)) {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GlobalResponse r = GlobalResponse.ok(SecurityConstant.LOGIN_SUCCESS);
                resp.getWriter().write(JSONUtil.toJsonStr(r));
            } else {
                resp.sendRedirect(securityProperties.getLoginSuccessUrl());
            }
        });
        // 登录失败后的回调
        authFilter.setAuthenticationFailureHandler(( req,resp,e) -> {
            String msg = getLoginErrorMessage(e);
            resp.setStatus(HttpStatus.OK.value());
            if (SysUtil.isAjaxRequest(req)) {
                resp.setCharacterEncoding("UTF-8");
                resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                GlobalResponse r = GlobalResponse.error(HttpStatus.UNAUTHORIZED.value(), msg);
                resp.getWriter().write(JSONUtil.toJsonStr(r));
            } else {
                req.setAttribute("error",msg);
                RequestDispatcher dispatcher = req.getRequestDispatcher(securityProperties.getLoginFailureUrl());
                dispatcher.forward(req, resp);
            }
        });
        return authFilter;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
          http.csrf().ignoringAntMatchers(securityProperties.getCsrfWhiteList())
                .and().cors()
                .and().formLogin().usernameParameter("username").passwordParameter("password")
                .and().logout().logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(logoutSuccessHandler).invalidateHttpSession(true).deleteCookies().clearAuthentication(true).permitAll()
                // 防止iframe 造成跨域
                .and()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                  .antMatchers(securityProperties.getWhiteList()).permitAll()
                  .anyRequest().access("@hasUrlPermission.hasPermission(request,authentication)")
                .and()
                  .addFilterAt(authenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
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
        ).antMatchers("/error");
    }

    private String getLoginErrorMessage(AuthenticationException e) {
        String msg;
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException || e instanceof AuthenticationCredentialsNotFoundException || e instanceof LockedException) {
            msg = e.getMessage();
        } else if (e instanceof CredentialsExpiredException) {
            msg = SecurityConstant.PASSWORD_EXPIRED;
        } else if (e instanceof AccountExpiredException) {
            msg = SecurityConstant.ACCOUNT_EXPIRED;
        } else if (e instanceof DisabledException) {
            msg = SecurityConstant.ACCOUNT_DISABLED;
        } else {
            msg = SecurityConstant.UNKNOWN_LOGIN_ERROR;
        }
        return msg;
    }

}
