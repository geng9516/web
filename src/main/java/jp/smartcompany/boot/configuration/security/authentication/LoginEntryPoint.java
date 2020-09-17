package jp.smartcompany.boot.configuration.security.authentication;

import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义Spring-Security登录失败后的处理
 * @author Xiao Wenpeng
 */
@Component
public class LoginEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws ServletException, IOException {
        if (SysUtil.isAjaxRequest(req)) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.getWriter().write(JSONUtil.toJsonStr(GlobalResponse.error(HttpStatus.UNAUTHORIZED.value(), SecurityConstant.LOGIN_TIMEOUT)));
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login");
            dispatcher.forward(req, resp);
        }
    }

}