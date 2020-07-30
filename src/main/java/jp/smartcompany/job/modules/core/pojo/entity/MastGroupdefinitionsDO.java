package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.KeySequence;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * グループ定義条件マスタ
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
@TableName("mast_groupdefinitions")
@KeySequence("MAST_GROUPDEFINITIONS_SEQ")
public class MastGroupdefinitionsDO implements Serializable {

       private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
        @TableId(value = "mgp_id")
        private Long mgpId;

        /**
         * 顧客コード
         */
    @TableField("mgp_ccustomerid_ck_fk")
        private String mgpCcustomeridCkFk;

        /**
         * システムコード
         */
    @TableField("mgp_csystemid_ck")
        private String mgpCsystemidCk;

        /**
         * グループコード
         */
    @TableField("mgp_cgroupid_ck_fk")
        private String mgpCgroupidCkFk;

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
         * グループ定義種別
         */
    @TableField("mgp_baseflag")
        private String mgpBaseflag;

        /**
         * グループ判定sql
         */
    @TableField("mgp_cquery")
        private String mgpCquery;

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