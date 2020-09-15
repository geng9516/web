package jp.smartcompany.boot.util;

import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.configuration.security.dto.SmartUserDetails;
import jp.smartcompany.boot.enums.ErrorMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @author Xiao Wenpeng
 */
public class SecurityUtil {

	public static SmartUserDetails getLoginUser() {
		Object principal =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal==null) {
			throw new GlobalException("principalNotFound");
		}
		return (SmartUserDetails)principal;
	}

	public static String getUserId() {
		SmartUserDetails loginUser = getLoginUser();
		if (!isAuthenticated()){
			throw new GlobalException(ErrorMessage.SESSION_EXPIRE);
		}
		return loginUser.getUsername();
	}

	public static String getUsername() {
		SmartUserDetails loginUser = getLoginUser();
		if (!isAuthenticated()){
			return null;
		}
		return loginUser.getUsername();
	}

	public static boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication instanceof UsernamePasswordAuthenticationToken && authentication.isAuthenticated();
	}

}