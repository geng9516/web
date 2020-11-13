package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LiquidationPeriodListDto {

    private String employeeid;
    private String employeename;
    private String worktypeid;
    private String worktypename;
    private String startdate;
    private String enddate;
    private String modifieduser;
    private String modifieddate;
    private String newflag;
    private String status;

}
