package jp.smartcompany.admin.appmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MastTemplateDTO implements Serializable {

    private Long matId;
    private String matCtemplateid;
    private String matCname;

}
