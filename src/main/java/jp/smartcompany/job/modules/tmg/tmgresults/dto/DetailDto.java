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
public class DetailDto {
    /**
     * 開始時刻
     */
    private String txtTDAD_NOPEN;
    /**
     * 終了時刻
     */
    private String txtTDAD_NCLOSE;
    /**
     * 備考
     */
    private String txtTDAD_NSPARENUM1;
    /**
     * 区分
     */
    private String txtTDAD_CCODE01;
    /**
     *
     */
    private String txtTDAD_CSPARECHAR1;
    /**
     *
     */
    private String txtTDAD_CSPARECHAR2;
    /**
     *
     */
    private String txtTDAD_CNOTWORKID;

    private String txtHasAuthorityOverhours;


}
