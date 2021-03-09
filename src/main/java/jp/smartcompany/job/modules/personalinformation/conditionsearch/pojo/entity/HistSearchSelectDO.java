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
@TableName("HIST_SEARCH_SELECT")
@KeySequence("HIST_SEARCH_SELECT_SEQ")
public class HistSearchSelectDO implements Serializable {

    private static final long serialVersionUID = 4564221834627082185L;

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
     * 最終更新者
     */
    private String hssCmodifieruserid;
    /**
     * 最終更新日
     */
    private Date hssDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Long versionno;

    @TableField(exist = false)
    private String tableName;

    @TableField(exist = false)
    private String columnType;

}
