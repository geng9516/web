package jp.smartcompany.job.util;

import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.enums.ErrorMessage;
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

	public static MastAccountDO getLoginUser() {
		return (MastAccountDO) SecurityUtils.getSubject().getPrincipal();
	}

	public static String getUserId() {
		MastAccountDO mastAccountDO = getLoginUser();
		if (mastAccountDO == null){
			throw new GlobalException(ErrorMessage.SESSION_EXPIRE);
		}
		return mastAccountDO.getMaCuserid();
	}

	public static String getUsername() {
		MastAccountDO mastAccountDO = getLoginUser();
		if (mastAccountDO == null){
			return null;
		}
		return mastAccountDO.getMaCaccount();
	}

	public static boolean isAuthenticated() {
		return getSubject().isAuthenticated();
	}

}