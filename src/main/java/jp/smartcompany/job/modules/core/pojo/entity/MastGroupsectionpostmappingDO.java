package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * グループ定義条件マスタ（組織、役職）
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
@TableName("mast_groupsectionpostmapping")
@KeySequence("MAST_GROUPSECTIONPOSTMAP_SEQ")
public class MastGroupsectionpostmappingDO implements Serializable {

        private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mag_id")
                private Long magId;

        /**
         * 顧客コード
         */
    @TableField("mag_ccustomerid_ck_fk")
        private String magCcustomeridCkFk;

        /**
         * システムコード
         */
    @TableField("mag_csystemid_ck")
        private String magCsystemidCk;

        /**
         * グループコード
         */
    @TableField("mag_cgroupid_fk")
        private String magCgroupidFk;

        /**
         * 開始日
         */
    @TableField("mag_dstartdate_ck")
        private Date magDstartdateCk;

        /**
         * 終了日
         */
    @TableField("mag_denddate")
        private Date magDenddate;

        /**
         * 定義区分
         */
    @TableField("mag_ctypeid")
        private String magCtypeid;

        /**
         * 法人コード
         */
    @TableField("mag_ccompanyid")
        private String magCcompanyid;

        /**
         * 組織コード
         */
    @TableField("mag_csectionid")
        private String magCsectionid;

        /**
         * 役職コード
         */
    @TableField("mag_cpostid")
        private String magCpostid;

        /**
         * 社員番号
         */
    @TableField("mag_cemployeeid")
        private String magCemployeeid;

        /**
         * 最終更新者
         */
    @TableField("mag_cmodifieruserid")
        private String magCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mag_dmodifieddate")
        private Date magDmodifieddate;

        /**
         * バージョンno
         */
        @Version
    @TableField("versionno")
        private Long versionno;


}