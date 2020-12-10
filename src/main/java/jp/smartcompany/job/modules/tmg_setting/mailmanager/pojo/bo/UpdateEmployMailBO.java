package jp.smartcompany.job.modules.tmg_setting.mailmanager.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateEmployMailBO {

    private String email;
    private String empName;
    private String startDate;
    private String endDate;

}
