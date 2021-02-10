package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

//　自由条件検索定義条件式データ
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_DEFINITIONS")
@KeySequence("HIST_SEARCH_DEFINITIONS_SEQ")
public class HistSearchDefinitionsDO implements Serializable {

    /**
     * IDカラム
     */
    @TableId
    private Long hsdId;
    /**
     * 設定ID
     */
    private Long hsdNsettingid;
    /**
     * 行番号
     */
    private Integer hsdNseq;
    /**
     * 論理演算子
     */
    private String hsdCandor;
    /**
     * カッコ
     */
    private String hsdCopenedparenthsis;
    /**
     * テーブルID
     */
    private String hsdCtableid;
    /**
     * カラムID
     */
    private String hsdCcolumnid;
    /**
     * カラム名
     */
    private String hsdCcolumnname;
    /**
     * データ型
     */
    private String hsdCtypeofcolumn;
    /**
     *　演算子
     */
    private String hsdCoperator;
    /**
     * 比較値
     */
    private String hsdCvalue;
    /**
     * 表示文字列
     */
    private String hsdCdisplayvalue;
    /**
     *　閉じカッコ
     */
    private String hsdCclosedparenthsis;
    /**
     *　最終更新者
     */
    private String hsdCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hsdDmodifieddate;
    /**
     *　バージョンNo
     */
    @Version
    private Long versionno;
}
