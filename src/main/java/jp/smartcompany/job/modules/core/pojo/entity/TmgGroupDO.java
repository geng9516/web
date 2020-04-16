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
 * [勤怠]グループ                      データ開始日、終了日は親となる部署のデータ開始日、終了日とす
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
@TableName("tmg_group")
public class TmgGroupDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tgr_ccustomerid")
        private String tgrCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tgr_ccompanyid")
        private String tgrCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tgr_dstartdate")
        private Date tgrDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tgr_denddate")
        private Date tgrDenddate;

        /**
         * 更新者
         */
    @TableField("tgr_cmodifieruserid")
        private String tgrCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tgr_dmodifieddate")
        private Date tgrDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tgr_cmodifierprogramid")
        private String tgrCmodifierprogramid;

        /**
         * 部署コード                                                       mo:mo_csectionid_ck
         */
                @TableId(value = "tgr_csectionid", type = IdType.AUTO)
                private String tgrCsectionid;

        /**
         * グループコード                       グループ作成時にidを付番
         */
    @TableField("tgr_cgroupid")
        private String tgrCgroupid;

        /**
         * グループ名称
         */
    @TableField("tgr_cgroupname")
        private String tgrCgroupname;

        /**
         * 組織配下のグループ数 各組織のデフォルトグループにのみ保持
         */
    @TableField("tgr_nmaxseq")
        private Long tgrNmaxseq;

        /**
         * 超勤実績警告値(月)01
         */
    @TableField("tgr_ot_montly_01")
        private Long tgrOtMontly01;

        /**
         * 超勤実績警告値(月)02
         */
    @TableField("tgr_ot_montly_02")
        private Long tgrOtMontly02;

        /**
         * 超勤実績警告値(月)03
         */
    @TableField("tgr_ot_montly_03")
        private Long tgrOtMontly03;

        /**
         * 超勤実績警告値(月)04
         */
    @TableField("tgr_ot_montly_04")
        private Long tgrOtMontly04;

        /**
         * 超勤実績警告値(月)05
         */
    @TableField("tgr_ot_montly_05")
        private Long tgrOtMontly05;

        /**
         * 超勤実績警告値(年)01
         */
    @TableField("tgr_ot_yearly_01")
        private Long tgrOtYearly01;

        /**
         * 超勤実績警告値(年)02
         */
    @TableField("tgr_ot_yearly_02")
        private Long tgrOtYearly02;

        /**
         * 超勤実績警告値(年)03
         */
    @TableField("tgr_ot_yearly_03")
        private Long tgrOtYearly03;

        /**
         * 超勤実績警告値(年)04
         */
    @TableField("tgr_ot_yearly_04")
        private Long tgrOtYearly04;

        /**
         * 超勤実績警告値(年)05
         */
    @TableField("tgr_ot_yearly_05")
        private Long tgrOtYearly05;

        /**
         * 月次警告値超過回数
         */
    @TableField("tgr_ot_monthly_count")
        private Long tgrOtMonthlyCount;

        /**
         * tgr_cbureauid
         */
    @TableField("tgr_cbureauid")
        private String tgrCbureauid;

        /**
         * tgr_ccreate
         */
    @TableField("tgr_ccreate")
        private String tgrCcreate;

        /**
         * 休日勤務日数警告値(月)01
         */
    @TableField("tgr_ht_montly_01")
        private Long tgrHtMontly01;

        /**
         * 休日勤務日数警告値(月)02
         */
    @TableField("tgr_ht_montly_02")
        private Long tgrHtMontly02;

        /**
         * 休日勤務日数警告値(月)03
         */
    @TableField("tgr_ht_montly_03")
        private Long tgrHtMontly03;

        /**
         * 休日勤務日数警告値(月)04
         */
    @TableField("tgr_ht_montly_04")
        private Long tgrHtMontly04;

        /**
         * 休日勤務日数警告値(月)05
         */
    @TableField("tgr_ht_montly_05")
        private Long tgrHtMontly05;

        /**
         * 勤務時間警告値(日)01
         */
    @TableField("tgr_ot_daily_01")
        private Long tgrOtDaily01;

        /**
         * 複数月平均時間
         */
    @TableField("tgr_ot_monthly_avg")
        private Long tgrOtMonthlyAvg;


        }