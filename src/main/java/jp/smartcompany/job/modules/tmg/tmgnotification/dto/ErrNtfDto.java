package jp.smartcompany.job.modules.tmg.tmgnotification.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrNtfDto {
    private String empName;
    private String ntfName;
    private String startDate;
    private String endDate;

}
