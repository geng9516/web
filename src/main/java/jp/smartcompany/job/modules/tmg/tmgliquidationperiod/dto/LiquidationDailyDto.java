package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LiquidationDailyDto {
    private String yyyymmdd;
    private String week;
    private String patternid;
    private String workhours;
    private String kubun;
    private String kubunid;
    private String starttime;
    private String endtime;
    private String reststarttime1;
    private String restendtime1;
    private String reststarttime2;
    private String restendtime2;
    private String weekworktime;
    private List<String> ntftype;
    private String resttime;
}
