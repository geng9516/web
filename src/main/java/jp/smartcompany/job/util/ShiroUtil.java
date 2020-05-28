package jp.smartcompany.job.util;

import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.enums.ErrorMessage;
import jp.smartcompany.job.modules.core.pojo.bo.LoginAccountBO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;


/**
 * @author Xiao Wenpeng
 */
public class ShiroUtil {

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static LoginAccountBO getLoginUser() {
		return (LoginAccountBO) SecurityUtils.getSubject().getPrincipal();
	}

	public static String getUserId() {
		LoginAccountBO loginAccountBO = getLoginUser();
		if (loginAccountBO == null){
			throw new GlobalException(ErrorMessage.SESSION_EXPIRE);
		}
		return loginAccountBO.getHdCuserid();
	}

	public static String getUsername() {
		LoginAccountBO loginAccountBO = getLoginUser();
		if (loginAccountBO == null){
			return null;
		}
		return loginAccountBO.getHdCuserid();
	}

	public static boolean isAuthenticated() {
		return getSubject().isAuthenticated();
	}

}