package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_liquidation_period")
public class TmgLiquidationPeriodDO implements Serializable {


    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableField("tlp_ctlpid")
    private String tlpCtlpid;
    /**
     * 顧客ｺｰﾄﾞ     固定：01
     */
    @TableField("tlp_ccustomerid")
    private String tlpCcustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    @TableField("tlp_ccompanyid")
    private String tlpCcompanyid;

    /**
     * 職員番号
     */
    @TableId(value = "tlp_cemployeeid")
    private String tlpCemployeeid;

    /**
     * ﾃﾞｰﾀ開始日
     */
    @TableField("tlp_dstartdate")
    private Date tlpDstartdate;

    /**
     * ﾃﾞｰﾀ終了日
     */
    @TableField("tlp_denddate")
    private Date tlpDenddate;

    /**
     * 更新者
     */
    @TableField("tlp_cmodifieruserid")
    private String tlpCmodifieruserid;

    /**
     * 更新日
     */
    @TableField("tlp_dmodifieddate")
    private Date tlpDmodifieddate;

    /**
     * 勤怠種別     mgd:tmg_worktype
     */
    @TableField("tlp_cworktypeid")
    private String tlpCworktypeid;


    /**
     *
     */
    @TableField("tlp_cavgworktime")
    private long tlpCavgworktime;

    /**
     *
     */
    @TableField("tlp_cmaxdayhours")
    private long tlpCmaxdayhours;

    /**
     *
     */
    @TableField("tlp_cmaxweekhours")
    private long tlpCmaxweekhours;

    /**
     *
     */
    @TableField("tlp_ctotalworkdays")
    private long tlpCtotalworkdays;

    /**
     *
     */
    @TableField("tlp_cmaxcontiweek")
    private long tlpCmaxcontiweek;

    /**
     *
     */
    @TableField("tlp_cmaxcontiday")
    private long tlpCmaxcontiday;

    /**
     *
     */
    @TableField("tlp_coverweekcount")
    private long tlpCoverweekcount;
    /**
     * 予備文字列1
     */
    @TableField("tlp_csparechar1")
    private String tlpCsparechar1;
    /**
     * 予備文字列2
     */
    @TableField("tlp_csparechar2")
    private String tlpCsparechar2;
    /**
     * 予備文字列3
     */
    @TableField("tlp_csparechar3")
    private String tlpCsparechar3;


    /**
     * 予備文字列４
     */
    @TableField("tlp_csparechar4")
    private String tlpCsparechar4;

    /**
     * 予備文字列５
     */
    @TableField("tlp_csparechar5")
    private String tlpCsparechar5;

    /**
     * 予備数値１
     */
    @TableField("tlp_nsparenum1")
    private Long tlpNsparenum1;

    /**
     * 予備数値２
     */
    @TableField("tlp_nsparenum2")
    private Long tlpNsparenum2;

    /**
     * 予備数値３
     */
    @TableField("tlp_nsparenum3")
    private Long tlpNsparenum3;

    /**
     * 予備数値４
     */
    @TableField("tlp_nsparenum4")
    private Long tlpNsparenum4;

    /**
     * 予備数値５
     */
    @TableField("tlp_nsparenum5")
    private Long tlpNsparenum5;

    /**
     * 予備日付１
     */
    @TableField("tlp_dsparedate1")
    private Date tlpDsparedate1;

    /**
     * 予備日付２
     */
    @TableField("tlp_dsparedate2")
    private Date tlpDsparedate2;

    /**
     * 予備日付３
     */
    @TableField("tlp_dsparedate3")
    private Date tlpDsparedate3;

    /**
     * 予備日付４
     */
    @TableField("tlp_dsparedate4")
    private Date tlpDsparedate4;

    /**
     * 予備日付５
     */
    @TableField("tlp_dsparedate5")
    private Date tlpDsparedate5;

   
}
