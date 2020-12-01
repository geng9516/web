package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 名称マスタ
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
@TableName("mast_generic")
@KeySequence("mast_generic_seq")
public class MastGenericDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mg_id")
                private Long mgId;

        /**
         * 顧客コード
         */
    @TableField("mg_ccustomerid_ck_fk")
        private String mgCcustomeridCkFk;

        /**
         * 法人コード
         */
    @TableField("mg_ccompanyid_ck_fk")
        private String mgCcompanyidCkFk;

        /**
         * 名称マスタコード
         */
    @TableField("mg_cgenericgroupid_ck")
        private String mgCgenericgroupidCk;

        /**
         * 言語区分
         */
    @TableField("mg_clanguage_ck")
        private String mgClanguageCk;

        /**
         * 名称マスタ名称
         */
    @TableField("mg_cgenericgroupdesc")
        private String mgCgenericgroupdesc;

        /**
         * 履歴作成区分
         */
    @TableField("mg_cifhistorical")
        private String mgCifhistorical;

        /**
         * 編集可否区分
         */
    @TableField("mg_cifeditable")
        private String mgCifeditable;

        /**
         * 法人個別区分
         */
    @TableField("mg_cifcompanytype")
        private String mgCifcompanytype;

        /**
         * 作成日
         */
    @TableField("mg_dcreateddate")
        private Date mgDcreateddate;

        /**
         * 廃止フラグ
         */
    @TableField("mg_cdelflag")
        private String mgCdelflag;

        /**
         * 廃止日
         */
    @TableField("mg_ddeldate")
        private Date mgDdeldate;

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
         * カテゴリ区分
         */
    @TableField("mg_ccategoryid")
        private String mgCcategoryid;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;
    @TableField("MGD_CSPARECHAR_DESC_1")
    private String mgdCsparecharDesc1;
    @TableField("MGD_CSPARECHAR_DESC_2")
    private String mgdCsparecharDesc2;
    @TableField("MGD_CSPARECHAR_DESC_3")
    private String mgdCsparecharDesc3;
    @TableField("MGD_CSPARECHAR_DESC_4")
    private String mgdCsparecharDesc4;
    @TableField("MGD_CSPARECHAR_DESC_5")
    private String mgdCsparecharDesc5;
    @TableField("MGD_NSPARENUM_DESC_1")
    private String mgdNsparenumDesc1;
    @TableField("MGD_NSPARENUM_DESC_2")
    private String mgdNsparenumDesc2;
    @TableField("MGD_NSPARENUM_DESC_3")
    private String mgdNsparenumDesc3;
    @TableField("MGD_NSPARENUM_DESC_4")
    private String mgdNsparenumDesc4;
    @TableField("MGD_NSPARENUM_DESC_5")
    private String mgdNsparenumDesc5;
    @TableField("MGD_DSPAREDATE_DESC_1")
    private String mgdDsparedateDesc1;
    @TableField("MGD_DSPAREDATE_DESC_2")
    private String mgdDsparedateDesc2;
    @TableField("MGD_DSPAREDATE_DESC_3")
    private String mgdDsparedateDesc3;
    @TableField("MGD_DSPAREDATE_DESC_4")
    private String mgdDsparedateDesc4;
    @TableField("MGD_DSPAREDATE_DESC_5")
    private String mgdDsparedateDesc5;

}