package jp.smartcompany.boot.configuration.security.access;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface HasUrlPermission {

    boolean hasPermission(HttpServletRequest request, Authentication authentication) throws SQLException;

}
