package jp.smartcompany.boot.configuration.security.access.impl;

import jp.smartcompany.boot.configuration.security.SecurityProperties;
import jp.smartcompany.boot.configuration.security.access.HasUrlPermission;
import jp.smartcompany.boot.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service("hasUrlPermission")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HasUrlPermissionImpl implements HasUrlPermission {

    private final SecurityProperties securityProperties;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean hasPermission = false;
        if (SecurityUtil.isAuthenticated()) {
            hasPermission = true;
            return hasPermission;
        } else {
           return hasPermission;
        }
    }
}
