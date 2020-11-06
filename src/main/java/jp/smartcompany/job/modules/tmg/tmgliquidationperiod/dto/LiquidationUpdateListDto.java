package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LiquidationUpdateListDto {
    private String empId;
    private String worktypeId;
    private String startDate;
    private String endDate;
}
