package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConditionOrderDTO {

    /**
     * プロパティ変数
     */
    private String hsoCcolumnId;
    /**
     * プロパティ変数
     */
    private String mdCcolumnname;

    /**
     *　IDカラム
     */
    private Long hsoId;
    /**
     * 設定ID
     */
    private Long hsoNsettingid;
    /**
     * 行番号
     */
    private Integer hsoNseq;
    /**
     * カラムID
     */
    private String hsoCcolumn;
    /**
     * ソート順
     */
    private String hsoCorder;

}
