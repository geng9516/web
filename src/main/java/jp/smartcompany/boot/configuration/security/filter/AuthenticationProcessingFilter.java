package jp.smartcompany.boot.configuration.security.filter;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  private final ObjectMapper objectMapper;

  public AuthenticationProcessingFilter(
          SecurityProperties securityProperties, AuthBusiness authBusiness,
          ObjectMapper objectMapper) {
    super(new AntPathRequestMatcher("/login", "POST"));
    this.objectMapper = objectMapper;
    setAuthenticationSuccessHandler((req, resp, auth) -> {
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
    setAuthenticationFailureHandler(( req,resp,e) -> {
      String msg = getLoginErrorMessage(e);
      resp.setStatus(HttpStatus.UNAUTHORIZED.value());
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
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UsernamePasswordAuthenticationToken authRequest;
    try (InputStream is = request.getInputStream()) {
      JsonNode loginForm = objectMapper.readTree(is);
      String username = loginForm.get("username").textValue();
      String password = loginForm.get("password").textValue();
      authRequest = new UsernamePasswordAuthenticationToken(username,password);
      authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    } catch (IOException e) {
      e.printStackTrace();
      throw new BadCredentialsException(SecurityConstant.USERNAME_OR_PASSWORD_NOT_EXISTS);
    }
    return this.getAuthenticationManager().authenticate(authRequest);
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
