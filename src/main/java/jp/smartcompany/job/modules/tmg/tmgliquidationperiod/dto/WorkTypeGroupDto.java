package jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class WorkTypeGroupDto {
    private String  worktypeid;
    private String  worktypename;
    private List<WorkTypeDto> child;
}
