package jp.smartcompany.boot.configuration.security.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义过滤器，为后端提供一些公共参数，如判断客户端是否为移动端等
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor
public class SmartParameterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {
        if (StrUtil.endWithAny(req.getRequestURI(),".js",".css",".ico",".json",".moc","mtn")) {
            chain.doFilter(req, resp);
            return;
        }
        String uaStr = req.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        req.setAttribute("isMobile",ua.isMobile());

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
        chain.doFilter(req, resp);
    }

}