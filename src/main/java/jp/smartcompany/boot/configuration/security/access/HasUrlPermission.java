package jp.smartcompany.boot.configuration.security.access;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface HasUrlPermission {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
