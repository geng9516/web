package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UpdateMailDTO {

    @NotBlank
    private String empId;
    private String empName;
    @Email
    @NotBlank
    private String email;

}
