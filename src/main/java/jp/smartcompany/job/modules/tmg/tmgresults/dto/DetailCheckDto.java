package jp.smartcompany.job.modules.tmg.tmgresults.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * パラメータDTO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DetailCheckDto {

    private String custID;
    private String compCode;
    private String targetUser;
    private String userCode;
    private String txtAction;
    private String day;
    private String month;
    private String year;
    private String itemCode;
    private String txtTDAD_NOPEN;
    private String txtTDAD_NCLOSE;
    private String txtTDAD_CSPARECHAR1;
    private String txtTDAD_NSPARENUM1;
    private String txtTDAD_CCODE01;
    private String categoryCode;
    private String site;
    private String hasAuthority;
    private int nIdx;
    private String txtTDAD_CSPARECHAR2;





}
