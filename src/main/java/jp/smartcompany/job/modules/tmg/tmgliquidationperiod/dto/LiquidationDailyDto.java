package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LiquidationDailyDto {
    private String yyyymmdd;
    private String week;
    private String workhours;
    private String kubun;
    private String starttime;
    private String endtime;
    private String resttimestart1;
    private String resttimeend1;
    private String resttimestart2;
    private String resttimeend2;
}
