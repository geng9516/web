package jp.smartcompany.job.modules.core.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChangePasswordDTO {

    private String username;
    private String newPassword;
    private String oldPassword;
    private String repeatPassword;

}
