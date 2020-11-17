package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PatternInfoDto {
    private String sectionId;
    private String patternName;
    private String patternId;
    private String startTime;
    private String endTime;
    private String restTimeStart1;
    private String restTimeEnd1;
    private String restTimeStart2;
    private String restTimeEnd2;
    private String restTimeStart3;
    private String restTimeEnd3;
    private String restTimeStart4;
    private String restTimeEnd4;

}
