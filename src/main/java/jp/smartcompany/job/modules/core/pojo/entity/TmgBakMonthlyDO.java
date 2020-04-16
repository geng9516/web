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
 * [勤怠]バックアップ・月別情報
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
@TableName("tmg_bak_monthly")
public class TmgBakMonthlyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 作成日
         */
    @TableField("tmo_dcreate")
        private Date tmoDcreate;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmo_ccustomerid")
        private String tmoCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmo_ccompanyid")
        private String tmoCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tmo_cemployeeid")
        private String tmoCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日                      固定：1900/01/01
         */
    @TableField("tmo_dstartdate")
        private Date tmoDstartdate;

        /**
         * ﾃﾞｰﾀ終了日                      固定：2222/12/31
         */
    @TableField("tmo_denddate")
        private Date tmoDenddate;

        /**
         * 更新者
         */
    @TableField("tmo_cmodifieruserid")
        private String tmoCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmo_dmodifieddate")
        private Date tmoDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmo_cmodifierprogramid")
        private String tmoCmodifierprogramid;

        /**
         * 該当年月                        yyyy/mm/01
         */
    @TableField("tmo_dyyyymm")
        private Date tmoDyyyymm;

        /**
         * 付与前：年休月初残日数
         */
    @TableField("tmo_npaid_bef_begining_days")
        private Long tmoNpaidBefBeginingDays;

        /**
         * 付与前：年休月初残時間数
         */
    @TableField("tmo_npaid_bef_begining_hours")
        private Long tmoNpaidBefBeginingHours;

        /**
         * 付与前：年休(終日)取得回数
         */
    @TableField("tmo_npaid_bef_used_days")
        private Long tmoNpaidBefUsedDays;

        /**
         * 付与前：年休(半休)取得回数
         */
    @TableField("tmo_npaid_bef_used_halfdays")
        private Long tmoNpaidBefUsedHalfdays;

        /**
         * 付与前：年休(時間)取得時間数
         */
    @TableField("tmo_npaid_bef_used_hours")
        private Long tmoNpaidBefUsedHours;

        /**
         * 付与後：年休繰越日数
         */
    @TableField("tmo_npaid_carryforward")
        private Long tmoNpaidCarryforward;

        /**
         * 付与後：年休付与日数
         */
    @TableField("tmo_npaid_add")
        private Long tmoNpaidAdd;

        /**
         * 付与後：年休調整付与日数
         */
    @TableField("tmg_npaid_ajust")
        private Long tmgNpaidAjust;

        /**
         * 付与後：年休調整繰越日数
         */
    @TableField("tmo_npaid_cr_ajust")
        private Long tmoNpaidCrAjust;

        /**
         * 付与後：年休調整付与時間
         */
    @TableField("tmo_npaid_add_ajust_hours")
        private Long tmoNpaidAddAjustHours;

        /**
         * 付与後：年休調整繰越時間
         */
    @TableField("tmo_npaid_cr_ajust_hours")
        private Long tmoNpaidCrAjustHours;

        /**
         * 付与後：年休月初残日数/付与前：年休残日数
         */
    @TableField("tmo_npaid_begining_days")
        private Long tmoNpaidBeginingDays;

        /**
         * 付与後：年休月初残時間数/付与前：年休残時間数
         */
    @TableField("tmo_npaid_begining_hours")
        private Long tmoNpaidBeginingHours;

        /**
         * 付与後：年休(終日)取得回数
         */
    @TableField("tmo_npaid_used_days")
        private Long tmoNpaidUsedDays;

        /**
         * 付与後：年休(半休)取得回数
         */
    @TableField("tmo_npaid_used_halfdays")
        private Long tmoNpaidUsedHalfdays;

        /**
         * 付与後：年休(時間)取得時間数
         */
    @TableField("tmo_npaid_used_hours")
        private Long tmoNpaidUsedHours;

        /**
         * 付与後：年休残日数
         */
    @TableField("tmo_npaid_rest_days")
        private Long tmoNpaidRestDays;

        /**
         * 付与後：年休残時間数
         */
    @TableField("tmo_npaid_rest_hours")
        private Long tmoNpaidRestHours;

        /**
         * 付与後：年休繰越時間数
         */
    @TableField("tmo_npaid_cr_hours")
        private Long tmoNpaidCrHours;

        /**
         * 付与前：付与前日残日数
         */
    @TableField("tmo_npaid_bef_rest_days")
        private Long tmoNpaidBefRestDays;

        /**
         * 付与前：付与前日残時間
         */
    @TableField("tmo_npaid_bef_rest_hours")
        private Long tmoNpaidBefRestHours;

        /**
         * 喪失日数
         */
    @TableField("tmo_nlose_days")
        private Long tmoNloseDays;

        /**
         * 喪失時間数
         */
    @TableField("tmo_nlose_hours")
        private Long tmoNloseHours;


        }