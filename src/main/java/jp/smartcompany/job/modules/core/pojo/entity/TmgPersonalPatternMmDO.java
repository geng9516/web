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
 * [勤怠]個人別勤務予定情報
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
@TableName("tmg_personal_pattern_mm")
public class TmgPersonalPatternMmDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ
         */
    @TableField("tppm_ccustomerid")
        private String tppmCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tppm_ccompanyid")
        private String tppmCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tppm_cemployeeid", type = IdType.AUTO)
                private String tppmCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tppm_dstartdate")
        private Date tppmDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tppm_denddate")
        private Date tppmDenddate;

        /**
         * 更新者
         */
    @TableField("tppm_cmodifieruserid")
        private String tppmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tppm_dmodifieddate")
        private Date tppmDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tppm_cmodifierprogramid")
        private String tppmCmodifierprogramid;

        /**
         * 該当年月
         */
    @TableField("tppm_dyyyymm")
        private Date tppmDyyyymm;

        /**
         * 日別勤務時間数：1日
         */
    @TableField("tppm_ninfo01")
        private Long tppmNinfo01;

        /**
         * 日別勤務時間数：2日
         */
    @TableField("tppm_ninfo02")
        private Long tppmNinfo02;

        /**
         * 日別勤務時間数：3日
         */
    @TableField("tppm_ninfo03")
        private Long tppmNinfo03;

        /**
         * 日別勤務時間数：4日
         */
    @TableField("tppm_ninfo04")
        private Long tppmNinfo04;

        /**
         * 日別勤務時間数：5日
         */
    @TableField("tppm_ninfo05")
        private Long tppmNinfo05;

        /**
         * 日別勤務時間数：6日
         */
    @TableField("tppm_ninfo06")
        private Long tppmNinfo06;

        /**
         * 日別勤務時間数：7日
         */
    @TableField("tppm_ninfo07")
        private Long tppmNinfo07;

        /**
         * 日別勤務時間数：8日
         */
    @TableField("tppm_ninfo08")
        private Long tppmNinfo08;

        /**
         * 日別勤務時間数：9日
         */
    @TableField("tppm_ninfo09")
        private Long tppmNinfo09;

        /**
         * 日別勤務時間数：10日
         */
    @TableField("tppm_ninfo10")
        private Long tppmNinfo10;

        /**
         * 日別勤務時間数：11日
         */
    @TableField("tppm_ninfo11")
        private Long tppmNinfo11;

        /**
         * 日別勤務時間数：12日
         */
    @TableField("tppm_ninfo12")
        private Long tppmNinfo12;

        /**
         * 日別勤務時間数：13日
         */
    @TableField("tppm_ninfo13")
        private Long tppmNinfo13;

        /**
         * 日別勤務時間数：14日
         */
    @TableField("tppm_ninfo14")
        private Long tppmNinfo14;

        /**
         * 日別勤務時間数：15日
         */
    @TableField("tppm_ninfo15")
        private Long tppmNinfo15;

        /**
         * 日別勤務時間数：16日
         */
    @TableField("tppm_ninfo16")
        private Long tppmNinfo16;

        /**
         * 日別勤務時間数：17日
         */
    @TableField("tppm_ninfo17")
        private Long tppmNinfo17;

        /**
         * 日別勤務時間数：18日
         */
    @TableField("tppm_ninfo18")
        private Long tppmNinfo18;

        /**
         * 日別勤務時間数：19日
         */
    @TableField("tppm_ninfo19")
        private Long tppmNinfo19;

        /**
         * 日別勤務時間数：20日
         */
    @TableField("tppm_ninfo20")
        private Long tppmNinfo20;

        /**
         * 日別勤務時間数：21日
         */
    @TableField("tppm_ninfo21")
        private Long tppmNinfo21;

        /**
         * 日別勤務時間数：22日
         */
    @TableField("tppm_ninfo22")
        private Long tppmNinfo22;

        /**
         * 日別勤務時間数：23日
         */
    @TableField("tppm_ninfo23")
        private Long tppmNinfo23;

        /**
         * 日別勤務時間数：24日
         */
    @TableField("tppm_ninfo24")
        private Long tppmNinfo24;

        /**
         * 日別勤務時間数：25日
         */
    @TableField("tppm_ninfo25")
        private Long tppmNinfo25;

        /**
         * 日別勤務時間数：26日
         */
    @TableField("tppm_ninfo26")
        private Long tppmNinfo26;

        /**
         * 日別勤務時間数：27日
         */
    @TableField("tppm_ninfo27")
        private Long tppmNinfo27;

        /**
         * 日別勤務時間数：28日
         */
    @TableField("tppm_ninfo28")
        private Long tppmNinfo28;

        /**
         * 日別勤務時間数：29日
         */
    @TableField("tppm_ninfo29")
        private Long tppmNinfo29;

        /**
         * 日別勤務時間数：30日
         */
    @TableField("tppm_ninfo30")
        private Long tppmNinfo30;

        /**
         * 日別勤務時間数：31日
         */
    @TableField("tppm_ninfo31")
        private Long tppmNinfo31;


        }