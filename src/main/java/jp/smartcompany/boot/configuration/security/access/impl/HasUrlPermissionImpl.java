package jp.smartcompany.boot.configuration.security.access.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.handler.EntityListHandler;
import cn.hutool.db.handler.NumberHandler;
import cn.hutool.db.sql.SqlExecutor;
import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.configuration.security.access.HasUrlPermission;
import jp.smartcompany.boot.util.SecurityUtil;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service("hasUrlPermission")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HasUrlPermissionImpl implements HasUrlPermission {

    private final DataSource dataSource;
    private final SecurityProperties securityProperties;

    private static final String URL_TMG_PERM = "/tmg_perm";

    private String[] urlList;
    private Connection connection;

    @PostConstruct
    public void init() throws SQLException {
        urlList = securityProperties.getOnlyAuthenticationList();
        connection = dataSource.getConnection();
    }

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) throws SQLException {
        System.out.println(request.getParameter("psSite"));
        System.out.println(request.getParameter("psApp"));

        // 根据psSite和psApp查询出当前登录用户在当前模块可访问的url
        boolean hasPermission = false;
        String requestUrl = request.getRequestURI();
        if (SecurityUtil.isAuthenticated()) {
            PathMatcher matcher = new AntPathMatcher();
            for (String url : urlList) {
                System.out.println(requestUrl+"-->"+url);
                if (matcher.match(requestUrl,url)) {
                    System.out.println("--");
                    hasPermission = true;
                    break;
                // 如果是承认site则需要是承认者才能访问
                } else if (matcher.match(requestUrl,URL_TMG_PERM)) {
                    String empId = SecurityUtil.getLoginUser().getHdCemployeeidCk();
                    String now = SysUtil.transDateToString(DateUtil.date());
                    String countEvaluator = "SELECT COUNT(TEV_CEMPLOYEEID) as count FROM TMG_EVALUATER WHERE TEV_CEMPLOYEEID = '"+empId+"' AND TEV_DSTARTDATE <= '"+now+"' AND TEV_DENDDATE >= '"+now+"'";
                    Number count = SqlExecutor.query(connection,countEvaluator,new NumberHandler());
                    System.out.println(count);
                    System.out.println("++");
                    if (count.intValue() > 0){
                        hasPermission = true;
                    }
                    break;
                } else {
                    System.out.println("**");
                    hasPermission = true;
                    break;
                }
            }
            return hasPermission;
        } else {
           return hasPermission;
        }
    }
}
