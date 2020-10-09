package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.form.UserManagerEditPersonalForm;

import java.util.Map;

public interface UserManagerEditPersonalLogic {

    Map<String,Object> display(String userId);

    Map<String,Object> updatePersonal(UserManagerEditPersonalForm form);
}
