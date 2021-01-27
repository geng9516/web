package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColumnQueryDefinitionOptionDTO {

    private String name;
    private String description;
    private String dialogType;
    private String type;
    private Integer sort;

}
