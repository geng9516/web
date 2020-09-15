package jp.smartcompany.boot.configuration.security.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 授权失败的处理器
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    private final SecurityProperties securityProperties;

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws ServletException, IOException {
        if (SysUtil.isAjaxRequest(req)) {
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            GlobalResponse r = GlobalResponse.error(HttpStatus.HTTP_FORBIDDEN,e.getMessage());
            resp.getWriter().write(JSONUtil.toJsonStr(r));
       } else {
           req.setAttribute("error",e.getMessage());
           RequestDispatcher dispatcher = req.getRequestDispatcher(securityProperties.getAccessDeniedUrl());
           dispatcher.forward(req, resp);
       }

    }

}
