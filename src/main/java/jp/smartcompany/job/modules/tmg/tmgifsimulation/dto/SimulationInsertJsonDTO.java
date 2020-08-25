package jp.smartcompany.job.modules.tmg.tmgifsimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/08/04
 **/
@Setter
@Getter
@ToString
public class SimulationInsertJsonDTO {

    private String startDate;
    private String endDate;
    private String psStartDate;
    private String psEndDate;
    private String employeId;
    private String custId;
    private String compCode;
    private String language;
    private String psGroupId;
    private String operType;
    private List<ConditionColDTO> conditionColDTOList;

}
