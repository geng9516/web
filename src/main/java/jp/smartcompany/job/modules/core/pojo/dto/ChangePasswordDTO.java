package jp.smartcompany.job.modules.core.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ChangePasswordDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String repeatPassword;

}
