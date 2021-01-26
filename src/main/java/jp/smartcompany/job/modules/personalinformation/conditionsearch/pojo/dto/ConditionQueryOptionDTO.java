package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConditionQueryOptionDTO {

    private Long id;
    private String settingId;
    private String andOr;
    private String leftParentheses;
    private String tableId;
    private String columnId;
    private String columnName;
    private String columnType;
    private String operator;
    private String displayValue;
    private String value;
    private String rightParentheses;
    private Integer sort;
    private String tableName;

}
