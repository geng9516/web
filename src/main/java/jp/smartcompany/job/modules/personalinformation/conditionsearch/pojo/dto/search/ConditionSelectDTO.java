package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConditionSelectDTO {

    /**
     * IDカラム
     */
    @TableId
    private Long hssId;
    /**
     * 設定ID
     */
    private Long hssNsettingid;
    /**
     * 行番号
     */
    private Integer hssNseq;
    /**
     * カラムID
     */
    private String hssCcolumn;
    /**
     * プロパティ変数
     */
    private String columnName;

}
