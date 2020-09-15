package jp.smartcompany.boot.configuration.security.handler;

import jp.smartcompany.job.modules.core.business.AuthBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登出时的处理器
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmartLogoutHandler implements LogoutHandler {

    private final AuthBusiness authBusiness;

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) {
        authBusiness.logout(req);
    }

}
