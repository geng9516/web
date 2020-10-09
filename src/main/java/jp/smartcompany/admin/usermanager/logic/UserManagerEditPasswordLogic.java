package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.dto.ChangePasswordDTO;
import jp.smartcompany.admin.usermanager.form.UserManagerEditPasswordForm;

import java.util.Map;

public interface UserManagerEditPasswordLogic {

    Map<String,Object> showChangePassword(ChangePasswordDTO changePasswordDTO);

    Map<String,Object> changePassword(UserManagerEditPasswordForm form);

}
