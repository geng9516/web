package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LiquidationDailyInfoVo {
    private String yyyymmdd;
    private String kuben;
    private String kubenid;
    private String workhours;
    private String week;
    private String starttime;
    private String endtime;
    private String reststarttime1;
    private String restendtime1;
    private String reststarttime2;
    private String restendtime2;
    private String status;
    private String ntfStatus;
    private String holflg;
    private String ntftype;
    private String patternid;
    private String patternname;
    private boolean disabled;
}
