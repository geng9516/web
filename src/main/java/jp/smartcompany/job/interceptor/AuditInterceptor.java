package jp.smartcompany.job.interceptor;

import jp.smartcompany.job.modules.core.manager.AccessAuditManager;
import jp.smartcompany.job.modules.core.pojo.entity.AccessAuditDO;
import jp.smartcompany.job.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuditInterceptor implements HandlerInterceptor {

    private final static String REQUEST_TIME = "requestTime";

    private final AccessAuditManager accessAuditManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long requestTime = System.currentTimeMillis();
        request.setAttribute(REQUEST_TIME ,requestTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccessAuditDO accessAuditDO = new AccessAuditDO();
        long requestTime = (long)request.getAttribute(REQUEST_TIME);
        long responseTime = System.currentTimeMillis();
        long time = responseTime - requestTime;
        accessAuditDO
            .setStatus(response.getStatus())
            .setIp(IpUtil.getRemoteAddr(request))
            .setMethod(request.getMethod().toLowerCase())
            .setRequestTime(new Date(requestTime))
            .setResponseTime(new Date(responseTime))
            .setTime(time)
            .setUrl(request.getServletPath());
//        EmpBO empBO = ShiroUtil.getLoginEmp();
        accessAuditManager.save(accessAuditDO);
    }
}
