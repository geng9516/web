package jp.smartcompany.boot.configuration.security.handler;

import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final SecurityProperties securityProperties;
  private final AuthBusiness authBusiness;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
                                      Authentication auth) throws IOException {
    authBusiness.saveLoginInfo(true,auth.getName());
    if (SysUtil.isAjaxRequest(req)) {
      resp.setCharacterEncoding("UTF-8");
      resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
      GlobalResponse r = GlobalResponse.ok("ログイン成功");
      resp.getWriter().write(JSONUtil.toJsonStr(r));
    } else {
      resp.sendRedirect(securityProperties.getLoginSuccessUrl());
    }
  }

}
