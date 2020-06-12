package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
public class SimulationMasterDto {

    /**
     * 絞込み項目区分
     */
    private String mgdExcludecondType;
    /**
     * 範囲(FROM)
     */
    private String mgdExcludecondFrom;
    /**
     * 範囲(TO)
     */
    private String mgdExcludecondTo;
    /**
     * 開始日
     */
    private String mgdDstartCk;
    /**
     * 終了日
     */
    private String mgdDend;
}
