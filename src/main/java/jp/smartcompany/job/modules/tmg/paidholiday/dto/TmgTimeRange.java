package jp.smartcompany.job.modules.tmg.paidholiday.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nie Wanqun
 * TMG_TIMERANGE
 */
@Getter
@Setter
@ToString
public class TmgTimeRange {

    private String cId;
    private String cType;
    private double nOpen;
    private double nClose;

}
