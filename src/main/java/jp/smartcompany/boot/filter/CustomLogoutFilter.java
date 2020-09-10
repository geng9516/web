package jp.smartcompany.boot.filter;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.business.AuthBusiness;
import jp.smartcompany.job.modules.core.util.PsSession;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CustomLogoutFilter extends LogoutFilter {

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest)request;
        //清除HTTPSession的用户信息
        HttpSession session = req.getSession();
        PsSession psSession = (PsSession) session.getAttribute(Constant.PS_SESSION);
        if (psSession!=null) {
            LRUCache<Object, Object> lruCache = SpringUtil.getBean(LRUCache.class);
            TimedCache<String, Object> timedCache = SpringUtil.getBean(TimedCache.class);

            AuthBusiness authBusiness = SpringUtil.getBean(AuthBusiness.class);
            lruCache.clear();
            timedCache.clear();
            authBusiness.saveLoginInfo(false, psSession.getLoginUser());
            try {
                Subject subject = getSubject(request, response);
                subject.logout();
            } catch (SessionException ise) {
                ise.printStackTrace();
            }
            issueRedirect(request, response, "/login");
        } else {
            issueRedirect(request, response, "/");
        }
        return false;
    }

}
