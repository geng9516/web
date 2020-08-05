package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/07/29
 **/
@Setter
@Getter
@ToString
public class SimulationDataDTO {

    /**
     * 期間（開始時間）
     */
    private String start_time;
    /**
     * 期間（終了時間）
     */
    private String end_time;

    /**
     * 期間
     */
    private String period;

    /**
     * 　条件
     */
    private String mgd_excludecond_type;

    /**
     * 　条件
     */
    private String mgd_excludecond_type_name;


    /**
     * 値
     */
    private String excludecond;

}
