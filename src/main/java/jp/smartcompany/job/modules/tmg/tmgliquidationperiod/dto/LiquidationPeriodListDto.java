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
    private String deptname;
    private String sectionid;
    private String worktypeid;
    private String worktypename;
    private String startdate;
    private String enddate;
    private String modifieduser;
    private String modifieddate;
    private String dailymaxworktime;
    private String weeklymaxworktime;
    private String totalworkdays;
    private String avgworktime;
    private String newflag;
    private String status;
    private String statusname;

}
