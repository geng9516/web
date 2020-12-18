package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class TestSendMail {

    @NotBlank
    private String email;
    @NotBlank
    private String content;

}
