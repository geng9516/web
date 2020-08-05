package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 連携対象マスタ設定dto
 * @objectSource
 * @date 2020/07/29
 **/
@Setter
@Getter
@ToString
public class SimulationDTO {

    /**
     * 期間（開始時間）
     */
    private String startTime;
    /**
     * 期間（終了時間）
     */
    private String endTime;
    /**
     * 連携対象マスタ設定 条件
     */
    private List<SimulationDataDTO> simulationDataDTOList;

}
