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
 * 法人ツリーマスタ
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
@TableName("mast_company")
public class MastCompanyDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mac_id", type = IdType.AUTO)
                private Long macId;

        /**
         * 顧客コード
         */
    @TableField("mac_ccustomerid_ck_fk")
        private String macCcustomeridCkFk;

        /**
         * 法人コード
         */
    @TableField("mac_ccompanyid_ck")
        private String macCcompanyidCk;

        /**
         * 法人階層コード
         */
    @TableField("mac_clayeredcompanyid")
        private String macClayeredcompanyid;

        /**
         * 上位法人コード
         */
    @TableField("mac_cparentid")
        private String macCparentid;

        /**
         * 階層レベル
         */
    @TableField("mac_nlevel")
        private Long macNlevel;

        /**
         * 行
         */
    @TableField("mac_nseq")
        private Long macNseq;

        /**
         * 法人名称
         */
    @TableField("mac_ccompanyname")
        private String macCcompanyname;

        /**
         * 法人名称（日本語）
         */
    @TableField("mac_ccompanynameja")
        private String macCcompanynameja;

        /**
         * 法人名称（英語）
         */
    @TableField("mac_ccompanynameen")
        private String macCcompanynameen;

        /**
         * 法人名称（中国語）
         */
    @TableField("mac_ccompanynamech")
        private String macCcompanynamech;

        /**
         * 法人名称（予備１）
         */
    @TableField("mac_ccompanyname01")
        private String macCcompanyname01;

        /**
         * 法人名称（予備２）
         */
    @TableField("mac_ccompanyname02")
        private String macCcompanyname02;

        /**
         * 法人略称（通称）
         */
    @TableField("mac_ccompanynick")
        private String macCcompanynick;

        /**
         * 法人略称（通称_日本語）
         */
    @TableField("mac_ccompanynickja")
        private String macCcompanynickja;

        /**
         * 法人略称（通称_英語）
         */
    @TableField("mac_ccompanynicken")
        private String macCcompanynicken;

        /**
         * 法人略称（通称_中国語）
         */
    @TableField("mac_ccompanynickch")
        private String macCcompanynickch;

        /**
         * 法人略称（通称_予備１）
         */
    @TableField("mac_ccompanynick01")
        private String macCcompanynick01;

        /**
         * 法人略称（通称_予備２）
         */
    @TableField("mac_ccompanynick02")
        private String macCcompanynick02;

        /**
         * 言語区分
         */
    @TableField("mac_clanguage")
        private String macClanguage;

        /**
         * データ開始日
         */
    @TableField("mac_dstart")
        private Date macDstart;

        /**
         * データ終了日
         */
    @TableField("mac_dend")
        private Date macDend;

        /**
         * 削除フラグ
         */
    @TableField("mac_cdelflag")
        private String macCdelflag;

        /**
         * 削除日付
         */
    @TableField("mac_ddeldate")
        private Date macDdeldate;

        /**
         * 最終更新者
         */
    @TableField("mac_cmodifieruserid")
        private String macCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mac_dmodifieddate")
        private Date macDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }