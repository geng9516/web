package jp.smartcompany.boot.configuration.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登出成功的处理器
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartLogoutSuccessHandler implements LogoutSuccessHandler {

    private final SecurityProperties securityProperties;
    private final ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException {
        if (SysUtil.isAjaxRequest(req)){
           resp.setCharacterEncoding("UTF-8");
           resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
           GlobalResponse r = GlobalResponse.ok("ログアウトしました");
           resp.getWriter().write(objectMapper.writeValueAsString(r));
       } else {
           resp.sendRedirect(securityProperties.getLogoutSuccessUrl());
       }
    }

}
