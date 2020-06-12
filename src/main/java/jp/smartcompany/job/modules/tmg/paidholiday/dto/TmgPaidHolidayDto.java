package jp.smartcompany.job.modules.tmg.paidholiday.dto;

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
public class TmgPaidHolidayDto {
    private String tphCmodifieruserid;
    private String tphCmodifierprogramid;
    private String tphNinvest;
    private String tphNadjust;
    private String tphNadjustHours;
    private String tphNadjustTo;
    private String tphNadjustHoursTo;
    private String tphDexpireAdjust;
    private String tphDexpireAdjustTo;
    private String tphCcomment;

    private String tphCemployeeid;
    private String tphDyyyymmdd;
    private String tphCcompanyid;
    private String tphCcustomerid;


}
