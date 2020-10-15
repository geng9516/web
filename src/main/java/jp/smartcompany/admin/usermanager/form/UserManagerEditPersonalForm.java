package jp.smartcompany.admin.usermanager.form;

import jp.smartcompany.admin.usermanager.dto.UpdatePersonalDTO;
import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserManagerEditPersonalForm {

    /**ユーザID*/
    private String userId;
    /**パスワード発行区分*/
    private Integer passwordType;
    /**パスワード*/
    @Pattern(regexp = "^[a-z0-9A-Z_]+$",message = "パスワードに使用できる文字は英数字のみです。")
    private String password;
    /**次回ログインパスワード変更フラグ*/
    private Boolean forceChangePassword;

    /** 基準日(職員基本情報コンポーネント引渡し用) */
    private UpdatePersonalDTO account;

}
