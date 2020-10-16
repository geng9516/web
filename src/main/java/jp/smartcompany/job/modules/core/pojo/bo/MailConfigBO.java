package jp.smartcompany.job.modules.core.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailConfigBO {

    private String host;
    private Integer port;
    private String username;
    private String password;

}
