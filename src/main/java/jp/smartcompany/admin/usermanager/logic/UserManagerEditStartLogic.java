package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditStartForm;

import java.util.Map;

public interface UserManagerEditStartLogic {

    Map<String,Object> showChangeStartDate(ShowLimitDateForm form);

    Map<String,Object> changeStartDate(UserManagerEditStartForm form);

}
