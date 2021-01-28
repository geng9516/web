package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class SqlDTO {

    /**
     * Select句
     */
    private String selectStatement;
    /**
     *From句
     */
    private List<String> fromStatementList;
    /**
     * 結合Where句
     */
    private List<String> joinWhereStatementList;
    /**
     * 条件Where句
     */
    private String whereConditionStatement;
    /**
     * order句
     */
    private String orderStatement;

}
