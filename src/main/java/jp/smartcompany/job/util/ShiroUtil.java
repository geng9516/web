package jp.smartcompany.job.util;

import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.enums.ErrorMessage;
import jp.smartcompany.job.modules.base.pojo.enums.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author Xiao Wenpeng
 */
public class ShiroUtil {

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

//	public static EmpBO getLoginEmp() {
//		return (EmpBO) SecurityUtils.getSubject().getPrincipal();
//	}
//
//	public static Long getEmpId() {
//		EmpBO empBO = getLoginEmp();
//		if (empBO == null){
//			throw new GlobalException(ErrorMessage.SESSION_EXPIRE);
//		}
//		return empBO.getEmpId();
//	}
//
//	public static String getUsername() {
//		EmpBO empBO = getLoginEmp();
//		if (empBO == null){
//			return null;
//		}
//		return empBO.getUsername();
//	}
//
//	public static List<Role> getLoginUserRoleList() {
//		EmpRoleManager empRoleManager = SpringUtil.getBean(CommonBean.Manager.EMP_ROLE);
//		return empRoleManager.listByEmpId(getEmpId());
//	}

//	public static boolean isOnlyGeneralAffair() {
//		List<Role> roleList = getLoginUserRoleList();
//		boolean isOnlyGeneralAffair = true;
//		for (Role role : roleList) {
//			if (role==Role.DEPT_LEADER||role==Role.MANAGER||role==Role.ADMIN||role==Role.NORMAL) {
//				isOnlyGeneralAffair = false;
//				break;
//			}
//		}
//		return isOnlyGeneralAffair && roleList.contains(Role.GENERAL_AFFAIR);
//	}
//
//	public static boolean isOnlyDeptLeader() {
//		List<Role> roleList = getLoginUserRoleList();
//		boolean isOnlyDeptLeader = true;
//		for (Role role : roleList) {
//			if (role==Role.GENERAL_AFFAIR||role==Role.MANAGER||role==Role.ADMIN||role==Role.NORMAL) {
//				isOnlyDeptLeader = false;
//				break;
//			}
//		}
//		return isOnlyDeptLeader&& roleList.contains(Role.DEPT_LEADER);
//	}
//
//	public static boolean isAdmin() {
//		List<Role> roleList = getLoginUserRoleList();
//		return roleList.contains(Role.ADMIN);
//	}
//
//	public static boolean isOnlyAdmin() {
//		List<Role> roleList = getLoginUserRoleList();
//		boolean isOnlyAdmin = true;
//		for (Role role : roleList) {
//			if (role==Role.GENERAL_AFFAIR||role==Role.MANAGER||role==Role.NORMAL || role==Role.DEPT_LEADER) {
//				isOnlyAdmin = false;
//				break;
//			}
//		}
//		return isOnlyAdmin&& roleList.contains(Role.ADMIN);
//	}
//
//	public static boolean isAboveDeptLeader() {
//		List<Role> roleList = getLoginUserRoleList();
//        return isAdmin()||roleList.contains(Role.GENERAL_AFFAIR)||roleList.contains(Role.MANAGER);
//	}
}