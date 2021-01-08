package jp.smartcompany.boot.configuration.security;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.authentication.AuthenticationProcessingFilter;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring Security核心配置文件
 * @author Xiao Wenpeng
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${info.basePath}")
    private String basePath;
    private final ObjectMapper objectMapper;
    private final AuthBusiness authBusiness;
    private final SecurityProperties securityProperties;
    private final AuthenticationProcessingFilter authenticationProcessingFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
          http.csrf().ignoringAntMatchers(securityProperties.getCsrfWhiteList())
                .and()
                  // 允许跨域
                  .cors()
                .and()
                  // 防止iframe 造成跨域
                  .headers().frameOptions().disable()
                .and()
                  .formLogin().usernameParameter("username").passwordParameter("password")
                .and()
                  // 访问认证和授权配置
                  .authorizeRequests()
                    .antMatchers(securityProperties.getWhiteList()).permitAll()
                    .anyRequest().access("@hasUrlPermission.hasPermission(request,authentication)")
                .and()
                  // 添加自定义认证filter
                  .addFilterAt(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                  // 登出时进行的处理
                  .logout(logout ->
                       logout
                           .addLogoutHandler((req, resp, auth)-> authBusiness.logout(req))
                           .logoutSuccessHandler((req,resp, auth)-> {
                               if (SysUtil.isAjaxRequest(req)){
                                   resp.setCharacterEncoding("UTF-8");
                                   resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                   GlobalResponse r = GlobalResponse.ok(SecurityConstant.LOGOUT_SUCCESS);
                                   resp.getWriter().write(objectMapper.writeValueAsString(r));
                               } else {
                                   resp.sendRedirect(securityProperties.getLogoutSuccessUrl());
                               }
                           })
                  )
                // 自定义过滤器在登录时认证用户名、密码
                .exceptionHandling()
                // 认证失败后异常处理切入点
                .authenticationEntryPoint((req,resp,e) -> {
                    if (SysUtil.isAjaxRequest(req)) {
                        resp.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
                        resp.setCharacterEncoding("UTF-8");
                        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        resp.getWriter().write(JSONUtil.toJsonStr(GlobalResponse.error(HttpStatus.REQUEST_TIMEOUT.value(),
                                SecurityConstant.LOGIN_TIMEOUT)));
                    } else {
                        resp.sendRedirect(basePath+"login");
                    }
                 })
                // 授权处理器
                .accessDeniedHandler((req, resp, e) -> {
                    resp.setStatus(HttpStatus.FORBIDDEN.value());
                    if (SysUtil.isAjaxRequest(req)) {
                        configResponseJsonHeader(resp, cn.hutool.http.HttpStatus.HTTP_FORBIDDEN);
                        GlobalResponse r = GlobalResponse.error(cn.hutool.http.HttpStatus.HTTP_FORBIDDEN,e.getMessage());
                        resp.getWriter().write(JSONUtil.toJsonStr(r));
                    } else {
                        req.setAttribute("error",e.getMessage());
                        RequestDispatcher dispatcher = req.getRequestDispatcher(securityProperties.getAccessDeniedUrl());
                        dispatcher.forward(req, resp);
                    }
                });
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(HttpMethod.GET, securityProperties.getResourceList())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    private void configResponseJsonHeader(HttpServletResponse resp, int httpForbidden) {
        resp.setStatus(httpForbidden);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
    }


}
