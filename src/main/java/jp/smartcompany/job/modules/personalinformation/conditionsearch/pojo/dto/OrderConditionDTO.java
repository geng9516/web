package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderConditionDTO {

    private String columnName;
    private String columnDescription;
    private String orderBy;

}
