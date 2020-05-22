package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 日別詳細情報（非勤務）VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class GenericDetailVO {
    /**
     * MGD_CMASTERCODE
     */
    private String mgdCmastercode;
    /**
     * MGD_CGENERICDETAILDESC
     */
    private String mgdCgenericdetaildesc;
}
