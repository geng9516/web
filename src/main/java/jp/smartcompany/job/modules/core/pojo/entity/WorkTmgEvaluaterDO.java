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
 * [勤怠]権限設定  データ開始日、終了日は親となる部署のデータ開始日、終了日とする
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
@TableName("work_tmg_evaluater")
public class WorkTmgEvaluaterDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tev_ccustomerid")
        private String tevCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tev_ccompanyid")
        private String tevCcompanyid;

        /**
         * 社員番号
         */
                @TableId(value = "tev_cemployeeid", type = IdType.AUTO)
                private String tevCemployeeid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tev_dstartdate")
        private Date tevDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tev_denddate")
        private Date tevDenddate;

        /**
         * 更新者
         */
    @TableField("tev_cmodifieruserid")
        private String tevCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tev_dmodifieddate")
        private Date tevDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tev_cmodifierprogramid")
        private String tevCmodifierprogramid;

        /**
         * 担当部署コード                                                     mo:mo_csectionid_ck
         */
    @TableField("tev_csectionid")
        private String tevCsectionid;

        /**
         * 担当グループコード                     グループでなく部署に対する設定の場合、null       tmg_group：tgr_csectionid
         */
    @TableField("tev_cgroupid")
        private String tevCgroupid;

        /**
         * 編集可能フラグ                       権限設定の変更可/不可                   mgd:tmg_onoff
         */
    @TableField("tev_ceditableflg")
        private String tevCeditableflg;

        /**
         * 権限：勤怠承認                       勤怠承認コンテンツの使用可/不可              mgd:tmg_onoff
         */
    @TableField("tev_cresults")
        private String tevCresults;

        /**
         * 権限：休暇休出承認                     休暇・休出承認コンテンツの使用可/不可           mgd:tmg_onoff
         */
    @TableField("tev_cnotification")
        private String tevCnotification;

        /**
         * 権限：超過勤務指示                     超過勤務指示コンテンツの使用可/不可            mgd:tmg_onoff
         */
    @TableField("tev_covertime")
        private String tevCovertime;

        /**
         * 権限：予定作成                       予定作成コンテンツの使用可/不可              mgd:tmg_onoff
         */
    @TableField("tev_cschedule")
        private String tevCschedule;

        /**
         * 権限：権限付与                       権限付与コンテンツの使用可/不可              mgd:tmg_onoff
         */
    @TableField("tev_cauthority")
        private String tevCauthority;

        /**
         * 人事管理ﾌﾗｸﾞ
         */
    @TableField("tev_cadminflg")
        private String tevCadminflg;

        /**
         * 担当部署デフォルト承認者フラグ
         */
    @TableField("tev_csectionevaluater")
        private String tevCsectionevaluater;

        /**
         * 権限：勤怠承認(月次)
         */
    @TableField("tev_cmonthlyapproval")
        private String tevCmonthlyapproval;

        /**
         * tev_capproval_level
         */
    @TableField("tev_capproval_level")
        private String tevCapprovalLevel;


        }