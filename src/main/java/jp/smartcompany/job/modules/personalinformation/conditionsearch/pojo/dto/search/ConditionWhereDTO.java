package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Getter
@Setter
@ToString
public class ConditionWhereDTO {

    /**
     * IDカラム
     */
    private Long mswId;
    /**
     * 顧客コード
     */
    private String mswCcustomerid;
    /**
     * テーブルID
     */
    private String mswCtableid;
    /**
     * カラムID
     */
    private String mswCcolumnid;
    /**
     *  並び順
     */
    private Integer mswNseq;
    /**
     * 社員情報フラグ'
     */
    private String mswCemployee;

    /**
     * 選択値
     */
    private List<ConditionWhereValueDTO> selectValue;
    /**
     * 使用可否
     * */
    private Boolean use;

}
