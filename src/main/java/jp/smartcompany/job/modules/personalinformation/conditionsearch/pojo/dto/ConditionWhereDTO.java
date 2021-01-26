package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ConditionWhereDTO {

    private String tableName;
    private String columnName;
    private String columnType;
    private String columnDescription;
    private String employFlag;
    private Integer sort;
    // 页面选择相应where条件后应该使用什么类型的弹窗标识
    private String dialogFlag;
    // 此条件是否可用
    private String useFlag;
    // 法人id（本项目未使用）
    private String companyId;

    private Integer whereType;

    private List<ConditionWhereOptionDTO> optionList;

}
