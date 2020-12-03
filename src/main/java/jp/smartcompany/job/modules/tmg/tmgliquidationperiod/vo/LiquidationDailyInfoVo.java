package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LiquidationDailyInfoVo {
    private String yyyymmdd;
    private String kubun;
    private String kubunid;
    private String workhours;
    private String week;
    private String starttime;
    private String endtime;
    private String reststarttime1;
    private String restendtime1;
    private String reststarttime2;
    private String restendtime2;
    private String reststarttime3;
    private String restendtime3;
    private String reststarttime4;
    private String restendtime4;
    private String status;
    private String ntfStatus;
    private String holflg;
    private String ntftype;
    private String patternid;
    private String patternname;
    private boolean disabled;
}
