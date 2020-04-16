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
 * グループ判定結果データ
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
@TableName("temp_groupcheck")
public class TempGroupcheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "tgc_id", type = IdType.AUTO)
                private Long tgcId;

        /**
         * 顧客コード
         */
    @TableField("tgc_ccustomerid")
        private String tgcCcustomerid;

        /**
         * 法人コード
         */
    @TableField("tgc_ccompanyid")
        private String tgcCcompanyid;

        /**
         * 社員番号
         */
    @TableField("tgc_cemployeeid")
        private String tgcCemployeeid;

        /**
         * ユーザid
         */
    @TableField("tgc_cuserid")
        private String tgcCuserid;

        /**
         * システムid
         */
    @TableField("tgc_csystemid")
        private String tgcCsystemid;

        /**
         * 開始日
         */
    @TableField("tgc_dstartdate")
        private Date tgcDstartdate;

        /**
         * 終了日
         */
    @TableField("tgc_denddate")
        private Date tgcDenddate;

        /**
         * グループid
         */
    @TableField("tgc_cgroupid")
        private String tgcCgroupid;

        /**
         * 優先順
         */
    @TableField("tgc_nweightage")
        private Long tgcNweightage;

        /**
         * グループ定義作成日
         */
    @TableField("tgc_dbasedate")
        private Date tgcDbasedate;

        /**
         * 最終更新者
         */
    @TableField("tgc_cmodifieruserid")
        private String tgcCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("tgc_dmodifieddate")
        private Date tgcDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }