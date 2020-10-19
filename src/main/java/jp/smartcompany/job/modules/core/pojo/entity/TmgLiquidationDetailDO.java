package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tmg_liquidation_detail")
public class TmgLiquidationDetailDO implements Serializable {
    private static final long serialVersionUID=1L;

    /**
     *
     */
    @TableField("tld_ctldid")
    private String tldCtldid;
    /**
     * 顧客ｺｰﾄﾞ     固定：01
     */
    @TableField("tld_ccustomerid")
    private String tldCcustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    @TableField("tld_ccompanyid")
    private String tldCcompanyid;

    /**
     * 職員番号
     */
    @TableId(value = "tld_cemployeeid")
    private String tldCemployeeid;

    /**
     * ﾃﾞｰﾀ開始日
     */
    @TableField("tld_dstartdate")
    private Date tldDstartdate;

    /**
     * ﾃﾞｰﾀ終了日
     */
    @TableField("tld_denddate")
    private Date tldDenddate;

    /**
     * 更新者
     */
    @TableField("tld_cmodifieruserid")
    private String tldCmodifieruserid;

    /**
     * 更新日
     */
    @TableField("tld_dmodifieddate")
    private Date tldDmodifieddate;

    /**
     * 适用年月
     */
    @TableField("tld_dyyyymm")
    private String tldDyyyymm;


    /**
     * 標準時間
     */
    @TableField("tld_cstandardtime")
    private String tldCstandardtime;

    /**
     * 調整時間
     */
    @TableField("tld_cresulttime")
    private String tldCresulttime;

    /**
     * 調整数値
     */
    @TableField("tld_cadjusttime")
    private String tldCadjusttime;

    /**
     * 予備文字列1
     */
    @TableField("tld_csparechar1")
    private String tldCsparechar1;
    /**
     * 予備文字列2
     */
    @TableField("tld_csparechar2")
    private String tldCsparechar2;
    /**
     * 予備文字列3
     */
    @TableField("tld_csparechar3")
    private String tldCsparechar3;


    /**
     * 予備文字列４
     */
    @TableField("tld_csparechar4")
    private String tldCsparechar4;

    /**
     * 予備文字列５
     */
    @TableField("tld_csparechar5")
    private String tldCsparechar5;

    /**
     * 予備数値１
     */
    @TableField("tld_nsparenum1")
    private Long tldNsparenum1;

    /**
     * 予備数値２
     */
    @TableField("tld_nsparenum2")
    private Long tldNsparenum2;

    /**
     * 予備数値３
     */
    @TableField("tld_nsparenum3")
    private Long tldNsparenum3;

    /**
     * 予備数値４
     */
    @TableField("tld_nsparenum4")
    private Long tldNsparenum4;

    /**
     * 予備数値５
     */
    @TableField("tld_nsparenum5")
    private Long tldNsparenum5;

    /**
     * 予備日付１
     */
    @TableField("tld_dsparedate1")
    private Date tldDsparedate1;

    /**
     * 予備日付２
     */
    @TableField("tld_dsparedate2")
    private Date tldDsparedate2;

    /**
     * 予備日付３
     */
    @TableField("tld_dsparedate3")
    private Date tldDsparedate3;

    /**
     * 予備日付４
     */
    @TableField("tld_dsparedate4")
    private Date tldDsparedate4;

    /**
     * 予備日付５
     */
    @TableField("tld_dsparedate5")
    private Date tldDsparedate5;
}
