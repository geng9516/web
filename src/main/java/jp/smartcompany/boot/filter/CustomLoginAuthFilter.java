package jp.smartcompany.boot.filter;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ShiroUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Xiao Wenpeng
 */
public class CustomLoginAuthFilter extends FormAuthenticationFilter {

    /**
     * 登录失败后的操作
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            }  else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        AuthBusiness authBusiness = SpringUtil.getBean(AuthBusiness.class);
        authBusiness.saveLoginInfo(true, ShiroUtil.getUsername());
        return true;
    }

    @SneakyThrows
    @Override
    public boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse resp = WebUtils.toHttp(response);
        HttpServletRequest req = WebUtils.toHttp(request);
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding("UTF-8");
        req.getSession().setAttribute("passwordExpiredUser",token.getPrincipal());
        int status = HttpStatus.SEE_OTHER.value();
        if (e instanceof ExpiredCredentialsException) {
            status = HttpStatus.FORBIDDEN.value();
        }
        response.getWriter().print(JSONUtil.toJsonStr(GlobalResponse.error(status, e.getMessage())));
        return false;
    }
}
