package jp.smartcompany.boot.configuration.security.authentication;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.configuration.security.handler.SmartAuthenticationFailureHandler;
import jp.smartcompany.boot.configuration.security.handler.SmartAuthenticationSuccessHandler;
import jp.smartcompany.boot.util.MultiReadHttpServletRequest;
import jp.smartcompany.job.modules.core.pojo.dto.LoginDTO;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

  public AuthenticationProcessingFilter(SmartAuthenticationManager authenticationManager, SmartAuthenticationSuccessHandler adminAuthenticationSuccessHandler, SmartAuthenticationFailureHandler authenticationFailureHandler) {
    super(new AntPathRequestMatcher("/login", "POST"));
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(adminAuthenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    String contentType = request.getContentType();
    if (StrUtil.isBlank(contentType) || !contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
      throw new AuthenticationServiceException("ログインContent-Typeはまちがいます");
    }
    UsernamePasswordAuthenticationToken authRequest;
    try {
      MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);
      // 将前端传递的数据转换成jsonBean数据格式
      LoginDTO form = JSONUtil.toBean(wrappedRequest.getBodyJsonStrByJson(wrappedRequest), LoginDTO.class);
      authRequest = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword(), null);
      authRequest.setDetails(authenticationDetailsSource.buildDetails(wrappedRequest));
    } catch (Exception e) {
      throw new AuthenticationServiceException(e.getMessage());
    }
    return this.getAuthenticationManager().authenticate(authRequest);
  }

}
