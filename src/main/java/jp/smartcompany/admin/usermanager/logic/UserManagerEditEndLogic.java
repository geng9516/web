package jp.smartcompany.admin.usermanager.logic;

import jp.smartcompany.admin.usermanager.form.ShowLimitDateForm;
import jp.smartcompany.admin.usermanager.form.UserManagerEditEndForm;

import java.util.Map;

public interface UserManagerEditEndLogic {

    Map<String,Object> showChangeEndDate(ShowLimitDateForm form);
    /**
     * 利用終了日 変更
     */
    void changeEndDate(UserManagerEditEndForm dto);

}
