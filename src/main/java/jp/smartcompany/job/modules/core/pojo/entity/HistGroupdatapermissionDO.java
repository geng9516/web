package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * グループ別アプリケーション検索対象範囲設定テーブル
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
@TableName("hist_groupdatapermission")
@KeySequence("HIST_GROUPDATAPERMISSION_SEQ")
public class HistGroupdatapermissionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "hgp_id")
                private Long hgpId;

        /**
         * 顧客コード
         */
    @TableField("hgp_ccustomerid")
        private String hgpCcustomerid;

        /**
         * オブジェクトid
         */
    @TableField("hgp_cobjectid")
        private String hgpCobjectid;

        /**
         * サイトid
         */
    @TableField("hgp_csiteid")
        private String hgpCsiteid;

        /**
         * アプリケーションid
         */
    @TableField("hgp_cappid")
        private String hgpCappid;

        /**
         * システムコード
         */
    @TableField("hgp_csystemid")
        private String hgpCsystemid;

        /**
         * グループid
         */
    @TableField("hgp_cgroupid")
        private String hgpCgroupid;

        /**
         * データ開始日
         */
    @TableField("hgp_dstartdate")
        private Date hgpDstartdate;

        /**
         * データ終了日
         */
    @TableField("hgp_denddate")
        private Date hgpDenddate;

        /**
         * 必要条件定義id
         */
    @TableField("hgp_cpermnecessity")
        private String hgpCpermnecessity;

        /**
         * 必須条件定義id
         */
    @TableField("hgp_cpermmust")
        private String hgpCpermmust;

        /**
         * 必要条件基点組織使用フラグ
         */
    @TableField("hgp_cbasesection_flag_need")
        private String hgpCbasesectionFlagNeed;

        /**
         * 必須条件基点組織使用フラグ
         */
    @TableField("hgp_cbasesection_flag_must")
        private String hgpCbasesectionFlagMust;

        /**
         * 最終更新者
         */
    @TableField("hgp_cmodifieruserid")
        private String hgpCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("hgp_dmodifieddate")
        private Date hgpDmodifieddate;

        /**
         * バージョンno
         */
        @Version
    @TableField("versionno")
        private Long versionno;

        /**
         * 退職者検索対象範囲
         */
    @TableField("hgp_cperm_retired")
        private String hgpCpermRetired;


        }