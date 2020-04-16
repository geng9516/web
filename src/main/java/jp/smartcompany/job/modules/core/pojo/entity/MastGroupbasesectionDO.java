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
 * グループ別基点組織マスタ
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
@TableName("mast_groupbasesection")
public class MastGroupbasesectionDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ｉｄカラム
         */
                @TableId(value = "mgbs_id", type = IdType.AUTO)
                private Long mgbsId;

        /**
         * 顧客コード
         */
    @TableField("mgbs_ccustomerid")
        private String mgbsCcustomerid;

        /**
         * システムコード
         */
    @TableField("mgbs_csystemid")
        private String mgbsCsystemid;

        /**
         * グループid
         */
    @TableField("mgbs_cgroupid")
        private String mgbsCgroupid;

        /**
         * 開始日
         */
    @TableField("mgbs_dstartdate")
        private Date mgbsDstartdate;

        /**
         * 終了日
         */
    @TableField("mgbs_denddate")
        private Date mgbsDenddate;

        /**
         * 法人コード
         */
    @TableField("mgbs_ccompanyid")
        private String mgbsCcompanyid;

        /**
         * 組織コード
         */
    @TableField("mgbs_csectionid")
        private String mgbsCsectionid;

        /**
         * 組織階層コード
         */
    @TableField("mgbs_clayeredsectionid")
        private String mgbsClayeredsectionid;

        /**
         * 最終更新者
         */
    @TableField("mgbs_cmodifieruserid")
        private String mgbsCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mgbs_dmodifieddate")
        private Date mgbsDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;

        /**
         * 以下・のみフラグ
         */
    @TableField("mgbs_cbeloworsingle")
        private String mgbsCbeloworsingle;


        }