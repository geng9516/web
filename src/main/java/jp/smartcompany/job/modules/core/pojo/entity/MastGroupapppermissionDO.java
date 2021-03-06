package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * グループ別アプリケーション権限マスタ
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(of={"mgpCsystemid","mgpCgroupid","mgpCobjectid","mgpDstartdate"})
@Accessors(chain = true)
@TableName("mast_groupapppermission")
@KeySequence("MAST_GROUPAPPPERMISSION_SEQ")
public class MastGroupapppermissionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mgp_id", type = IdType.INPUT)
                private Long mgpId;

        /**
         * 法人コード
         */
    @TableField("mgp_ccompanyid")
        private String mgpCcompanyid;

        /**
         * システムid
         */
    @TableField("mgp_csystemid")
        private String mgpCsystemid;

        /**
         * グループコード
         */
    @TableField("mgp_cgroupid")
        private String mgpCgroupid;

        /**
         * オブジェクトid
         */
    @TableField("mgp_cobjectid")
        private String mgpCobjectid;

        /**
         * サイトid
         */
    @TableField("mgp_csite")
        private String mgpCsite;

        /**
         * アプリケーションid
         */
    @TableField("mgp_capp")
        private String mgpCapp;

        /**
         * サブアプリケーションid
         */
    @TableField("mgp_csubapp")
        private String mgpCsubapp;

        /**
         * ボタンid
         */
    @TableField("mgp_cbutton")
        private String mgpCbutton;

        /**
         * 画面id
         */
    @TableField("mgp_cscreen")
        private String mgpCscreen;

        /**
         * 実行権限
         */
    @TableField("mgp_cpermission")
        private String mgpCpermission;

        /**
         * 実行拒否設定
         */
    @TableField("mgp_creject")
        private String mgpCreject;

        /**
         * 開始日
         */
    @TableField("mgp_dstartdate")
        private Date mgpDstartdate;

        /**
         * 終了日
         */
    @TableField("mgp_denddate")
        private Date mgpDenddate;

        /**
         * 最終更新者
         */
    @TableField("mgp_cmodifieruserid")
        private String mgpCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mgp_dmodifieddate")
        private Date mgpDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }