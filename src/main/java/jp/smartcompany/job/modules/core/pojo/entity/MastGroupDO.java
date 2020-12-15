package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * グループ定義マスタ
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
@TableName("mast_group")
@KeySequence("MAST_GROUP_SEQ")
public class MastGroupDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * ｉｄカラム
         */
                @TableId(value = "mg_id")
                private Long mgId;

    /**
     * 1:発表あり　0:発表無し
     */
    @TableField(value="mg_publishing")
    private Boolean mgPublishing;
        /**
         * 顧客コード
         */
    @TableField("mg_ccustomerid")
        private String mgCcustomerid;

        /**
         * システムコード
         */
    @TableField("mg_csystemid_ck_fk")
        private String mgCsystemidCkFk;

        /**
         * グループid
         */
    @TableField("mg_cgroupid_pk")
        private String mgCgroupidPk;

        /**
         * 言語区分
         */
    @TableField("mg_clanguage")
        private String mgClanguage;

        /**
         * データ開始日
         */
    @TableField("mg_dstartdate")
        private Date mgDstartdate;

        /**
         * データ終了日
         */
    @TableField("mg_denddate")
        private Date mgDenddate;

        /**
         * グループ名称
         */
    @TableField("mg_cgroupdescription")
        private String mgCgroupdescription;

        /**
         * グループ名称（日本語）
         */
    @TableField("mg_cgroupdescriptionja")
        private String mgCgroupdescriptionja;

        /**
         * グループ名称（英語）
         */
    @TableField("mg_cgroupdescriptionen")
        private String mgCgroupdescriptionen;

        /**
         * グループ名称（中国語）
         */
    @TableField("mg_cgroupdescriptionch")
        private String mgCgroupdescriptionch;

        /**
         * グループ名称（予備01）
         */
    @TableField("mg_cgroupdescription01")
        private String mgCgroupdescription01;

        /**
         * グループ名称（予備02）
         */
    @TableField("mg_cgroupdescription02")
        private String mgCgroupdescription02;

        /**
         * 法人コード（未使用）
         */
    @TableField("mg_ccompanyid")
        private String mgCcompanyid;

        /**
         * 適正数
         */
    @TableField("mg_npartinentnumber")
        private Long mgNpartinentnumber;

        /**
         * 優先順
         */
    @TableField("mg_nweightage")
        private Long mgNweightage;

        /**
         * 備考欄
         */
    @TableField("mg_ctext")
        private String mgCtext;

        /**
         * 最終更新者
         */
    @TableField("mg_cmodifieruserid")
        private String mgCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mg_dmodifieddate")
        private Date mgDmodifieddate;

        /**
         * バージョンno
         */
    @Version
    @TableField("versionno")
        private Long versionno;


        }