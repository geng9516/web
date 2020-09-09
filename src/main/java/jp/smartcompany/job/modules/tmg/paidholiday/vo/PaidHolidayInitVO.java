package jp.smartcompany.job.modules.tmg.paidholiday.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class PaidHolidayInitVO {

    private String tphCemployeeid;
    private String name;
    private String workertypenm;
    private String tphDyyyymm;
    private String tphDyyyymmdd;
    private Integer seq;
    private Double investDaysSum;
    private Double investHours;
    private Double throughoutDaysSum;
    private Double throughoutHoursSum;

}
