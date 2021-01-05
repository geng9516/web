package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PatternInfoDto {
    private String sectionId;
    private String worktypeId;
    private String empId;
    private String target;
    private String modifierUser;
    private String modifierdDate;
    private String patternName;
    private String patternId;
    private String startTime;
    private String endTime;
    private List<PattternRestTime> restTime=new ArrayList<>();
}
