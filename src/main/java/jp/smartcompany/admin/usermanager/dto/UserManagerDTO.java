package jp.smartcompany.admin.usermanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserManagerDTO extends UserManagerListDTO {
    /** 氏名(リンク) */
    private String permaLink;
    /** パスワード */
    private String password;
    /** ステータス*/
    private String status;
    /** チェックボックス */
    private Boolean chkuser;
}
