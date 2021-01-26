package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColumnOptionDTO {

    private String tableName;
    private String columnName;
    private String columnFieldName;

}
