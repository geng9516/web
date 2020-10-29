package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_liquidation_daily_check")
public class TmgLiquidationDailyCheckDO {

    private static final long serialVersionUID=1L;

    /**
     * 顧客ｺｰﾄﾞ     固定：01
     */
    @TableField("tldc_ccustomerid")
    private String tldcCcustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    @TableField("tldc_ccompanyid")
    private String tldcCcompanyid;

    /**
     * 職員番号
     */
    @TableId(value = "tldc_cemployeeid")
    private String tldcCemployeeid;

    /**
     * ﾃﾞｰﾀ開始日
     */
    @TableField("tldc_dstartdate")
    private Date tldcDstartdate;

    /**
     * ﾃﾞｰﾀ終了日
     */
    @TableField("tldc_denddate")
    private Date tldcDenddate;

    /**
     * 更新者
     */
    @TableField("tldc_cmodifieruserid")
    private String tldcCmodifieruserid;

    /**
     * 更新日
     */
    @TableField("tldc_dmodifieddate")
    private Date tldcDmodifieddate;

    /**
     * 适用週
     */
    @TableField("tldc_cweeks")
    private String tldcCweeks;


    /**
     * 适用月
     */
    @TableField("tldc_dyyyymm")
    private Date tldcDyyyymm;

    /**
     * 适用日
     */
    @TableField("tldc_dyyyymmdd")
    private Date tldcDyyyymmdd;

    /**
     * エラーコード
     */
    @TableField("tldc_cerrcode")
    private String tldcCerrcode;

    /**
     * エラーメッセージ
     */
    @TableField("tldc_cerrmsg")
    private String tldcCerrmsg;


    /**
     * 予備文字列1
     */
    @TableField("tldc_csparechar1")
    private String tldcCsparechar1;
    /**
     * 予備文字列2
     */
    @TableField("tldc_csparechar2")
    private String tldcCsparechar2;
    /**
     * 予備文字列3
     */
    @TableField("tldc_csparechar3")
    private String tldcCsparechar3;


    /**
     * 予備文字列４
     */
    @TableField("tldc_csparechar4")
    private String tldcCsparechar4;

    /**
     * 予備文字列５
     */
    @TableField("tldc_csparechar5")
    private String tldcCsparechar5;

    /**
     * 予備数値１
     */
    @TableField("tldc_nsparenum1")
    private Long tldcNsparenum1;

    /**
     * 予備数値２
     */
    @TableField("tldc_nsparenum2")
    private Long tldcNsparenum2;

    /**
     * 予備数値３
     */
    @TableField("tldc_nsparenum3")
    private Long tldcNsparenum3;

    /**
     * 予備数値４
     */
    @TableField("tldc_nsparenum4")
    private Long tldcNsparenum4;

    /**
     * 予備数値５
     */
    @TableField("tldc_nsparenum5")
    private Long tldcNsparenum5;

    /**
     * 予備日付１
     */
    @TableField("tldc_dsparedate1")
    private Date tldcDsparedate1;

    /**
     * 予備日付２
     */
    @TableField("tldc_dsparedate2")
    private Date tldcDsparedate2;

    /**
     * 予備日付３
     */
    @TableField("tldc_dsparedate3")
    private Date tldcDsparedate3;

    /**
     * 予備日付４
     */
    @TableField("tldc_dsparedate4")
    private Date tldcDsparedate4;

    /**
     * 予備日付５
     */
    @TableField("tldc_dsparedate5")
    private Date tldcDsparedate5;
}
