package jp.smartcompany.job.modules.core.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class TableCombinationTypeDTO {
    /** テーブルID */
    private String tableName;

    /** カラムID */
    private String columnName;
}
