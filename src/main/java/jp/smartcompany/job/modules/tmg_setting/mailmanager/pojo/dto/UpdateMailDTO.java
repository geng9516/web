package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class UpdateMailDTO {

    private Long id;
    @Email
    private String email;

}
