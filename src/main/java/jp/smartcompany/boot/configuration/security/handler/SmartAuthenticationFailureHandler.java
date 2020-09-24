package jp.smartcompany.boot.configuration.security.handler;

import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartAuthenticationFailureHandler implements AuthenticationFailureHandler {

  private final SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
    String msg = getLoginErrorMessage(e);
    resp.setStatus(401);
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
