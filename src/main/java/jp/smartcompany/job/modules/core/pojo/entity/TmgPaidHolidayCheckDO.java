package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * [勤怠]エラーチェック用・年次休暇情報
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_paid_holiday_check")
public class TmgPaidHolidayCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tph_ccustomerid")
        private String tphCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tph_ccompanyid")
        private String tphCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tph_cemployeeid", type = IdType.AUTO)
                private String tphCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                       固定：1900/01/01
         */
    @TableField("tph_dstartdate")
        private Date tphDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                       固定：2222/12/31
         */
    @TableField("tph_denddate")
        private Date tphDenddate;

        /**
         * 更新者
         */
    @TableField("tph_cmodifieruserid")
        private String tphCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tph_dmodifieddate")
        private Date tphDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tph_cmodifierprogramid")
        private String tphCmodifierprogramid;

        /**
         * 付与年月                          yyyy/mm/01
         */
    @TableField("tph_dyyyymm")
        private Date tphDyyyymm;

        /**
         * 付与日数
         */
    @TableField("tph_ninvest")
        private Long tphNinvest;

        /**
         * 繰越日数
         */
    @TableField("tph_nthroughout")
        private Long tphNthroughout;

        /**
         * 調整日数(付与)
         */
    @TableField("tph_nadjust")
        private Long tphNadjust;

        /**
         * 予備数値１
         */
    @TableField("tph_nsparenum1")
        private Long tphNsparenum1;

        /**
         * 予備数値２
         */
    @TableField("tph_nsparenum2")
        private Long tphNsparenum2;

        /**
         * 予備数値３
         */
    @TableField("tph_nsparenum3")
        private Long tphNsparenum3;

        /**
         * 予備数値４
         */
    @TableField("tph_nsparenum4")
        private Long tphNsparenum4;

        /**
         * 予備数値５
         */
    @TableField("tph_nsparenum5")
        private Long tphNsparenum5;

        /**
         * 予備日付１
         */
    @TableField("tph_dsparedate1")
        private Date tphDsparedate1;

        /**
         * 予備日付２
         */
    @TableField("tph_dsparedate2")
        private Date tphDsparedate2;

        /**
         * 予備日付３
         */
    @TableField("tph_dsparedate3")
        private Date tphDsparedate3;

        /**
         * 予備日付４
         */
    @TableField("tph_dsparedate4")
        private Date tphDsparedate4;

        /**
         * 予備日付５
         */
    @TableField("tph_dsparedate5")
        private Date tphDsparedate5;

        /**
         * 認定出勤日数(計算)
         */
    @TableField("tph_nconfirm")
        private Long tphNconfirm;

        /**
         * 調整時間(付与)
         */
    @TableField("tph_nadjust_hours")
        private Long tphNadjustHours;

        /**
         * 調整日数(繰越)
         */
    @TableField("tph_nadjust_to")
        private Long tphNadjustTo;

        /**
         * 調整時間(繰越)
         */
    @TableField("tph_nadjust_hours_to")
        private Long tphNadjustHoursTo;

        /**
         * 認定出勤日数(編集)
         */
    @TableField("tph_nconfirm_edit")
        private Long tphNconfirmEdit;


        }