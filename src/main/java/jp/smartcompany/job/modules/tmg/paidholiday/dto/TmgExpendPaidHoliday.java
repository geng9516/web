package jp.smartcompany.job.modules.tmg.paidholiday.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgExpendPaidHoliday {

    private double nRestDays;
    private int nRestHours;
    private double nExpendDays;
    private int nExpendHours;
    private int nUsedDays;
    private int nUsedHalfDays;
    private int nUsedHours;
    private int nSplitDays;
    private double nExpendDaysNtfsts2;
    private int nExpendHoursNtfsts2;
    private double nExpendDaysNtfsts5;
    private int nExpendHoursNtfsts5;
        }
