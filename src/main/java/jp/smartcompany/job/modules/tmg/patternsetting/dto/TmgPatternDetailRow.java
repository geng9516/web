package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 */
@Getter
@Setter
@ToString
public class TmgPatternDetailRow {

    /**
     *顧客コード
     * */
    @TableField("TPA_CCUSTOMERID")
    private String tpaCCustomerId;

    /**
     *法人コード
     * */
    @TableField("TPA_CCOMPANYID")
    private String tpaCCompanyId;
    /**
     *部局コード
     * */
    @TableField("TPA_CSECTIONID")
    private String tpaCSectionId;
    /**
     *部局名称
     * */
    @TableField("TPA_CSECTIONNAME")
    private String tpaCSectionName;
    /**
     *部局名称
     * */
    @TableField("TPA_CSECTIONNICK")
    private String tpaCSectionNick;
    /**
     *グループコード
     * */
    @TableField("TPA_CGROUPID")
    private String tpaCGroupId;
    /**
     *グループ名称
     * */
    @TableField("TPA_CGROUPNAME")
    private String tpaCGroupName;
    /**
     *データ開始日
     * */
    @TableField("TPA_DSTARTDATE")
    private Date tpaDStartDate;
    /**
     *データ終了日
     * */
    @TableField("TPA_DENDDATE")
    private Date tpaDEndDate;
    /**
     *更新者
     * */
    @TableField("TPA_CMODIFIERUSERID")
    private String tpaCModifierUserId;
    /**
     *更新日
     * */
    @TableField("TPA_DMODIFIEDDATE")
    private Date tpaDModifiedDate;
    /**
     *更新プログラムID
     * */
    @TableField("TPA_CMODIFIERPROGRAMID")
    private String tpaCModifierProgramId;
    /**
     *勤務パターンID
     * */
    @TableField("TPA_CPATTERNID")
    private String tpaCPatternId;
    /**
     *勤務パターン名称
     * */
    @TableField("TPA_CPATTERNNAME")
    private String tpa_CPatterName;
    /**
     *デフォルトフラグ
     * */
    @TableField("TPA_CDEFAULTFLG")
    private String tpaCDefaultFlg;
    /**
     *始業時刻
     * */
    @TableField("TPA_NOPEN")
    private int tpaNOpen;
    /**
     *終業時刻
     * */
    @TableField("TPA_NCLOSE")
    private int tpaNClose;
    /**
     *
     * */
    @TableField("TPA_C2CALDAYS")
    private String tpa_C2CalDays;
    /**
     *
     * */
    @TableField("TPA_CNEXTPTN")
    private String tpaCNextPtn;
    /**
     *
     * */
    @TableField("TPA_NDATE_CHANGE_TIME")
    private int tpaNDateChangeTime;

}
