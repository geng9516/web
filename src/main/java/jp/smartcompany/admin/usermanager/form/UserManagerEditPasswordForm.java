package jp.smartcompany.admin.usermanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class UserManagerEditPasswordForm {

    /**ユーザID*/
    private List<String> userIds;
    /**パスワード発行区分*/
    private Integer passwordType;
    /**パスワード*/
    private String password;
    /**次回ログインパスワード変更フラグ*/
    private Boolean forceChangePassword;

}
