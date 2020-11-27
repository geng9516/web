package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SelectPatternDto {
    private String patternid;
    private String patternname;
    private String opentime;
    private String closetime;
    private String restopen;
    private String restclose;
    private long seq;



}
