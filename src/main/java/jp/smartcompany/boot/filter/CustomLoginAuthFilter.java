package jp.smartcompany.boot.filter;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ShiroUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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
        WebUtils.toHttp(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        WebUtils.toHttp(response).setCharacterEncoding("UTF-8");
        WebUtils.toHttp(response).setStatus(HttpStatus.FORBIDDEN.value());
        WebUtils.toHttp(response).getWriter().print(JSONUtil.toJsonStr(GlobalResponse.error(HttpStatus.FORBIDDEN.value(),e.getMessage())));
        return false;
    }
}
