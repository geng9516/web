package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SendChangePasswordMailBO extends SendMailBO {

    /**
     * 变更密码的账号
     */
    private String account;
    /**
     * 变更后的密码
     */
    private String password;

}
