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
    private String workTypeId;
    private String sectionId;
    private String curDateFrom;
    private String curDateTo;
    private String avgWorkTime;
    private String dailyMaxWorkTime;
    private String weeklyMaxWorkTime;
    private String totalWorkDays;
    private String maxContiDays;
    private String maxContiWeeks;
    private String overContiWeeks;
    private String beginOfWeek;
}
