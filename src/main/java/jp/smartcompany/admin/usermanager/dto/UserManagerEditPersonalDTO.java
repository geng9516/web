package jp.smartcompany.admin.usermanager.dto;

import jp.smartcompany.job.modules.core.pojo.entity.MastAccountDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserManagerEditPersonalDTO {

    /**ユーザID*/
    private String userId;
    /**パスワード発行区分*/
    private Integer passwordType;
    /**パスワード*/
    private String password;
    /**次回ログインパスワード変更フラグ*/
    private Boolean forceChangePassword;
    private Date baseDate;
    /** 基準日(社員基本情報コンポーネント引渡し用) */
    private MastAccountDO account;

}
