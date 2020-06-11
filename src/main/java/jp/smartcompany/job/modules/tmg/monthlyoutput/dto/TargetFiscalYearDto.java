package jp.smartcompany.job.modules.tmg.monthlyoutput.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TargetFiscalYearDto {

    private String targetYearDate;

    private String preYearDate;

    private String nextYearDate;

    private String currYearDate;
}
