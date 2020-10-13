package jp.smartcompany.admin.usermanager.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-z0-9A-Z]+$",message = "パスワードに使用できる文字は英数字のみです。")
    private String password;
    /**次回ログインパスワード変更フラグ*/
    private Boolean forceChangePassword;

}
