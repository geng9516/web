package jp.smartcompany.job.modules.tmg.empattrsetting.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateCheckInfoDto {

    private  String employeeId;
    //初始化时状态
    private boolean initCheckType;
    //更改完状态
    private boolean updaeCheckType;

}
