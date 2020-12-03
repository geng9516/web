package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MailConfigDTO implements Serializable {

    private String host;
    private String username;
    private String password;
    private String port;

}
