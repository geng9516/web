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
 * public.tmg_mast_paid_days
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
@TableName("tmg_mast_paid_days")
public class TmgMastPaidDaysDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄ
         */
    @TableField("tmpd_ccustomerid")
        private String tmpdCcustomerid;

        /**
         * 法人ｺｰﾄ
         */
                @TableId(value = "tmpd_ccompanyid", type = IdType.AUTO)
                private String tmpdCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmpd_dstartdate")
        private Date tmpdDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmpd_denddate")
        private Date tmpdDenddate;

        /**
         * 雇用形態区分：pt非常勤、ft常勤、re再雇用
         */
    @TableField("tmpd_cdiscriminate")
        private String tmpdCdiscriminate;

        /**
         * マスタコード
         */
    @TableField("tmpd_cmastcode")
        private String tmpdCmastcode;

        /**
         * 説明
         */
    @TableField("tmpd_cexplain")
        private String tmpdCexplain;

        /**
         * 週勤務天数
         */
    @TableField("tmpd_cweekdays")
        private String tmpdCweekdays;

        /**
         * 付与日数
         */
    @TableField("tmpd_ngetdays")
        private Long tmpdNgetdays;

        /**
         * 勤続月数下限
         */
    @TableField("tmpd_nfloor_m")
        private Long tmpdNfloorM;

        /**
         * 勤続月数上限
         */
    @TableField("tmpd_nupper_m")
        private Long tmpdNupperM;

        /**
         * 所定労働日数下限
         */
    @TableField("tmpd_nfloor_d")
        private Long tmpdNfloorD;

        /**
         * 所定労働日数上限
         */
    @TableField("tmpd_nupper_d")
        private Long tmpdNupperD;

        /**
         * 予備文字１
         */
    @TableField("tmpd_csparechar1")
        private String tmpdCsparechar1;

        /**
         * 予備文字2
         */
    @TableField("tmpd_csparechar2")
        private String tmpdCsparechar2;

        /**
         * 予備数字1
         */
    @TableField("tmpd_nsparenum1")
        private Long tmpdNsparenum1;

        /**
         * 予備数字2
         */
    @TableField("tmpd_nsparenum2")
        private Long tmpdNsparenum2;


        }