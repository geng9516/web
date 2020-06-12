package jp.smartcompany.job.modules.tmg.monthlyoutput.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TargetDateLimit {
    private String targetStartDate;

    private String targetEndDate;
}
