package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class ConditionQueryDTO {

    private String applyDate;
    private String companyId;
    private List<ConditionQueryOptionDTO> optionList;

}
