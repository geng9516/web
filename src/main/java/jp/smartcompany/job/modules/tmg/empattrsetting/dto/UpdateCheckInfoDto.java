package jp.smartcompany.job.modules.tmg.empattrsetting.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateCheckInfoDto {

    private  String employeeId;

    private boolean initCheckType;

    private boolean updaeCheckType;

}
