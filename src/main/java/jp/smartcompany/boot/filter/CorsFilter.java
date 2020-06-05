package jp.smartcompany.boot.filter;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Xiao Wenpeng
 */
@Order(1)
@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String uaStr = req.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        req.setAttribute("isMobile",ua.isMobile());

        String origin = req.getHeader("Origin");
        if(origin == null) {
            origin = req.getHeader("Referer");
        }
        resp.setHeader("Access-Control-Allow-Credentials","true");
        resp.setHeader("Access-Control-Allow-Origin", origin);
        if(RequestMethod.OPTIONS.toString().equals(req.getMethod())) {
            String allowMethod = req.getHeader("Access-Control-Request-Method");
            String allowHeaders = req.getHeader("Access-Control-Request-Headers");
            // 允许浏览器在预检请求成功之后发送的实际请求方法名
            resp.setHeader("Access-Control-Allow-Methods", allowMethod);
            // 允许浏览器发送的请求消息头
            resp.setHeader("Access-Control-Allow-Headers", allowHeaders);
        }
        chain.doFilter(req, resp);
    }

}
