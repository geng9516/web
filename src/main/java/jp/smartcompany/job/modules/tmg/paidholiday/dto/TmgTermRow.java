package jp.smartcompany.job.modules.tmg.paidholiday.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgTermRow {

    private String cType;
    private Date dOpen;
    private Date dClose;
    private String CValueA;
    private String CValueB;

}
