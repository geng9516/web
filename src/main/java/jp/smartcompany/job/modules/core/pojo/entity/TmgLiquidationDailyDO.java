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
@TableName("tmg_liquidation_daily")
public class TmgLiquidationDailyDO {
    private static final long serialVersionUID=1L;

    /**
     * 顧客ｺｰﾄﾞ     固定：01
     */
    @TableField("tldd_ccustomerid")
    private String tlddCcustomerid;

    /**
     * 法人ｺｰﾄﾞ
     */
    @TableField("tldd_ccompanyid")
    private String tlddCcompanyid;

    /**
     * 職員番号
     */
    @TableId(value = "tldd_cemployeeid")
    private String tlddCemployeeid;

    /**
     * ﾃﾞｰﾀ開始日
     */
    @TableField("tldd_dstartdate")
    private Date tlddDstartdate;

    /**
     * ﾃﾞｰﾀ終了日
     */
    @TableField("tldd_denddate")
    private Date tlddDenddate;

    /**
     * 更新者
     */
    @TableField("tldd_cmodifieruserid")
    private String tlddCmodifieruserid;

    /**
     * 更新日
     */
    @TableField("tldd_dmodifieddate")
    private Date tlddDmodifieddate;

    /**
     * 适用週
     */
    @TableField("tldd_cweeks")
    private String tlddCweeks;

    /**
     * 适用区分
     */
    @TableField("tldd_ckuben")
    private String tlddCkuben;

    /**
     * 适用日
     */
    @TableField("tldd_dyyyymmdd")
    private Date tlddDyyyymmdd;

    /**
     * 労働時間
     */
    @TableField("tldd_cworkhours")
    private String tlddCworkhours;

    /**
     * 開始時間
     */
    @TableField("tldd_cstarttime")
    private String tlddCstarttime;
    /**
     * 終了時間
     */
    @TableField("tldd_cendtime")
    private String tlddCendtime;
    /**
     * 休憩開始時間
     */
    @TableField("tldd_creststarttime")
    private String tlddCreststarttime;
    /**
     * 休憩終了時間
     */
    @TableField("tldd_crestendtime")
    private String tlddCrestendtime;

    /**
     * 予備文字列1
     */
    @TableField("tldd_csparechar1")
    private String tlddCsparechar1;
    /**
     * 予備文字列2
     */
    @TableField("tldd_csparechar2")
    private String tlddCsparechar2;
    /**
     * 予備文字列3
     */
    @TableField("tldd_csparechar3")
    private String tlddCsparechar3;


    /**
     * 予備文字列４
     */
    @TableField("tldd_csparechar4")
    private String tlddCsparechar4;

    /**
     * 予備文字列５
     */
    @TableField("tldd_csparechar5")
    private String tlddCsparechar5;

    /**
     * 予備数値１
     */
    @TableField("tldd_nsparenum1")
    private Long tlddNsparenum1;

    /**
     * 予備数値２
     */
    @TableField("tldd_nsparenum2")
    private Long tlddNsparenum2;

    /**
     * 予備数値３
     */
    @TableField("tldd_nsparenum3")
    private Long tlddNsparenum3;

    /**
     * 予備数値４
     */
    @TableField("tldd_nsparenum4")
    private Long tlddNsparenum4;

    /**
     * 予備数値５
     */
    @TableField("tldd_nsparenum5")
    private Long tlddNsparenum5;

    /**
     * 予備日付１
     */
    @TableField("tldd_dsparedate1")
    private Date tlddDsparedate1;

    /**
     * 予備日付２
     */
    @TableField("tldd_dsparedate2")
    private Date tlddDsparedate2;

    /**
     * 予備日付３
     */
    @TableField("tldd_dsparedate3")
    private Date tlddDsparedate3;

    /**
     * 予備日付４
     */
    @TableField("tldd_dsparedate4")
    private Date tlddDsparedate4;

    /**
     * 予備日付５
     */
    @TableField("tldd_dsparedate5")
    private Date tlddDsparedate5;
}
