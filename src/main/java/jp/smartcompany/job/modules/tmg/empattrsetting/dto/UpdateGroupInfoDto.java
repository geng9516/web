package jp.smartcompany.job.modules.tmg.empattrsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateGroupInfoDto {

    private  String employeeId;

    private String initGroup;

    private String updaeGroup;
}
