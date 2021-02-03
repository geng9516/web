package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.dto.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConditionWhereValueDTO {

    /**
     * 値名称
     * */
    private String hswCname;
    /**
     * IDカラム
     */
    private Long hswId;
    /**
     * 設定ID
     */
    private Long hswNsettingid;
    /**
     * テーブルID
     */
    private String hswCtable;
    /**
     * カラムID
     */
    private String hswCcolumn;
    /**
     * 値
     */
    private String hswCvalue;
    /**
     * 条件使用有無
     */
    private String hswCuse;
}
