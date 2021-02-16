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

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("HIST_SEARCH_ORDER")
@KeySequence("HIST_SEARCH_ORDER_SEQ")
public class HistSearchOrderDO implements Serializable {

    /**
     *　IDカラム
     */
    @TableId
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
    /**
     * 最終更新者
     */
    private String hsoCmodifieruserid;
    /**
     *　最終更新日
     */
    private Date hsoDmodifieddate;
    /**
     * バージョンNo
     */
    @Version
    private Integer versionno;

}
