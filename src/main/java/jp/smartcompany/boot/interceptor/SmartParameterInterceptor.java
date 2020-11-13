package jp.smartcompany.boot.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class SmartParameterInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) {
        String uaStr = req.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        Platform platform = ua.getPlatform();
        req.setAttribute("isMobile",platform.isMobile());
        req.setAttribute("isIPhoneOrIPod", uaStr.contains("iPhone"));
        req.setAttribute("isIPad",uaStr.contains("iPad"));
        req.setAttribute("isAndroid",platform.isAndroid());

        String origin = req.getHeader("Origin");
        if(StrUtil.isBlank(origin)) {
            origin = req.getHeader("Referer");
        }
        if (StrUtil.isNotBlank(origin)) {
            resp.setHeader("Access-Control-Allow-Origin", origin);
        }
        resp.setHeader("Access-Control-Allow-Credentials","true");
        if(RequestMethod.OPTIONS.toString().equals(req.getMethod())) {
            String allowMethod = req.getHeader("Access-Control-Request-Method");
            String allowHeaders = req.getHeader("Access-Control-Request-Headers");
            // 允许浏览器在预检请求成功之后发送的实际请求方法名
            resp.setHeader("Access-Control-Allow-Methods", allowMethod);
            // 允许浏览器发送的请求消息头
            resp.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        return true;
    }


}
