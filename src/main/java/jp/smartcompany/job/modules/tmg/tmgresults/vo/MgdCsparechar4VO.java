package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 出勤日判定用VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MgdCsparechar4VO {
    /**
     * MGD_CMASTERCODE
     */
    private String mgdCmastercode;

    /**
     * MGD_CSPARECHAR5
     */
    private String mgdCsparechar5;
}
