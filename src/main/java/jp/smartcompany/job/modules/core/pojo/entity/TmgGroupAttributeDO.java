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
 * グループ属性テーブル
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
@TableName("tmg_group_attribute")
public class TmgGroupAttributeDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("tgra_ccustomerid")
        private String tgraCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tgra_ccompanyid")
        private String tgraCcompanyid;

        /**
         * 組織コード
         */
    @TableField("tgra_csectionid")
        private String tgraCsectionid;

        /**
         * グループコード
         */
                @TableId(value = "tgra_cgroupid", type = IdType.AUTO)
                private String tgraCgroupid;

        /**
         * 開始日
         */
    @TableField("tgra_dstartdate")
        private Date tgraDstartdate;

        /**
         * 終了日
         */
    @TableField("tgra_denddate")
        private Date tgraDenddate;

        /**
         * 更新者
         */
    @TableField("tgra_cmodifieruserid")
        private String tgraCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tgra_dmodifieddate")
        private Date tgraDmodifieddate;

        /**
         * デフォルト承認者自動設定フラグ
         */
    @TableField("tgra_cautoset_eva")
        private String tgraCautosetEva;

        /**
         * 更新プログラムid
         */
    @TableField("tgra_cmodifierprogramid")
        private String tgraCmodifierprogramid;

        /**
         * 超勤実績警告値(月)01
         */
    @TableField("tgra_ot_montly_01")
        private Long tgraOtMontly01;

        /**
         * 超勤実績警告値(月)02
         */
    @TableField("tgra_ot_montly_02")
        private Long tgraOtMontly02;

        /**
         * 超勤実績警告値(月)03
         */
    @TableField("tgra_ot_montly_03")
        private Long tgraOtMontly03;

        /**
         * 超勤実績警告値(月)04
         */
    @TableField("tgra_ot_montly_04")
        private Long tgraOtMontly04;

        /**
         * 超勤実績警告値(月)05
         */
    @TableField("tgra_ot_montly_05")
        private Long tgraOtMontly05;

        /**
         * 超勤実績警告値(年)01
         */
    @TableField("tgra_ot_yearly_01")
        private Long tgraOtYearly01;

        /**
         * 超勤実績警告値(年)02
         */
    @TableField("tgra_ot_yearly_02")
        private Long tgraOtYearly02;

        /**
         * 超勤実績警告値(年)03
         */
    @TableField("tgra_ot_yearly_03")
        private Long tgraOtYearly03;

        /**
         * 超勤実績警告値(年)04
         */
    @TableField("tgra_ot_yearly_04")
        private Long tgraOtYearly04;

        /**
         * 超勤実績警告値(年)05
         */
    @TableField("tgra_ot_yearly_05")
        private Long tgraOtYearly05;

        /**
         * 月次警告値超過回数
         */
    @TableField("tgra_ot_monthly_count")
        private Long tgraOtMonthlyCount;

        /**
         * 休暇勤務日数警告値(月)01
         */
    @TableField("tgra_ht_montly_01")
        private Long tgraHtMontly01;

        /**
         * 休暇勤務日数警告値(月)02
         */
    @TableField("tgra_ht_montly_02")
        private Long tgraHtMontly02;

        /**
         * 休暇勤務日数警告値(月)03
         */
    @TableField("tgra_ht_montly_03")
        private Long tgraHtMontly03;

        /**
         * 休暇勤務日数警告値(月)04
         */
    @TableField("tgra_ht_montly_04")
        private Long tgraHtMontly04;

        /**
         * 休暇勤務日数警告値(月)05
         */
    @TableField("tgra_ht_montly_05")
        private Long tgraHtMontly05;

        /**
         * 勤務時間警告値(日)01
         */
    @TableField("tgra_ot_daily_01")
        private Long tgraOtDaily01;

        /**
         * 複数月平均時間
         */
    @TableField("tgra_ot_monthly_avg")
        private Long tgraOtMonthlyAvg;


        }