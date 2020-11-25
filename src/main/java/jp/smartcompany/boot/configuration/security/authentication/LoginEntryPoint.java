package jp.smartcompany.boot.configuration.security.authentication;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.configuration.security.SecurityConstant;
import jp.smartcompany.boot.util.SysUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 自定义Spring-Security登录失败后的处理
 * @author Xiao Wenpeng
 */
@Component
public class LoginEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException {
        if (SysUtil.isAjaxRequest(req)) {
            resp.setStatus(HttpStatus.REQUEST_TIMEOUT.value());
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.getWriter().write(JSONUtil.toJsonStr(GlobalResponse.error(HttpStatus.REQUEST_TIMEOUT.value(), SecurityConstant.LOGIN_TIMEOUT)));
        } else {
            String uri = req.getRequestURI();
            // 如果是浏览器访问页面请求则需要进行路径记录
            if (!StrUtil.equalsAny(uri,"/","/login") && !StrUtil.startWith(uri,"/sys")) {
                Enumeration<String> params = req.getParameterNames();
                StringBuilder sb = new StringBuilder();
                while (params.hasMoreElements()){
                    String paramKey = params.nextElement();
                    String paramValue = req.getParameter(paramKey);
                    sb.append(paramKey).append("=").append(paramValue).append("&");
                }
                if (sb.length()>0){
                    resp.sendRedirect("/login?from="+ uri+"?"+sb.substring(0,sb.length()-1));
                } else {
                    resp.sendRedirect("/login?from="+ uri);
                }
                return;
            }
            resp.sendRedirect("/login");
        }
    }

}