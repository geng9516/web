package jp.smartcompany.job.modules.personalinformation.conditionsearch.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_WHERE")
@KeySequence("HIST_SEARCH_WHERE_SEQ")
public class HistSearchWhereDO implements Serializable {

    private static final long serialVersionUID = 7532980701690158838L;

    /**
     * IDカラム
     */
    @TableId
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
    /**
     * 最終更新者
     */
    private String hswCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hswDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Long versionno;


    @TableField(exist = false)
    private String displayName;

}
