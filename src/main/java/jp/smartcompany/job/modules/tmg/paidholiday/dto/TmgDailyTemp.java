package jp.smartcompany.job.modules.tmg.paidholiday.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Nie Wanqun
 * CURSOR_TMG_DAILY
 */
@Getter
@Setter
@ToString
public class TmgDailyTemp {
    private String customerId;
    private String companyId;
    private String employeeId;
    private String workTypeId;
    private int yyyy;
    private Date yyyyMm;
    private Date yyyyMmDd;
    private String holFlg;
}
