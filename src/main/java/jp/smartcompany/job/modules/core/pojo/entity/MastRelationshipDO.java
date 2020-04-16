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
 * リレーションシップマスタ
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
@TableName("mast_relationship")
public class MastRelationshipDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mr_id", type = IdType.AUTO)
                private Long mrId;

        /**
         * ドメインid
         */
    @TableField("mr_cdomainid_fk")
        private String mrCdomainidFk;

        /**
         * リレーションシップid
         */
    @TableField("mr_crelationshipid_pk")
        private String mrCrelationshipidPk;

        /**
         * リレーションシップ名
         */
    @TableField("mr_crelationshipdesc")
        private String mrCrelationshipdesc;

        /**
         * 言語
         */
    @TableField("mr_clanguage")
        private String mrClanguage;

        /**
         * 作成日
         */
    @TableField("mr_dcreateddate")
        private Date mrDcreateddate;

        /**
         * 削除フラグ
         */
    @TableField("mr_cdelflag")
        private String mrCdelflag;

        /**
         * 削除日
         */
    @TableField("mr_ddeldate")
        private Date mrDdeldate;

        /**
         * 評価レベル名
         */
    @TableField("mr_cevaluationlevelname")
        private String mrCevaluationlevelname;

        /**
         * 評価レベル名（日本語）
         */
    @TableField("mr_cevaluationlevelnameja")
        private String mrCevaluationlevelnameja;

        /**
         * 評価レベル名（英語）
         */
    @TableField("mr_cevaluationlevelnameen")
        private String mrCevaluationlevelnameen;

        /**
         * 評価レベル名（中国語）
         */
    @TableField("mr_cevaluationlevelnamech")
        private String mrCevaluationlevelnamech;

        /**
         * 評価レベル名（予備１）
         */
    @TableField("mr_cevaluationlevelname01")
        private String mrCevaluationlevelname01;

        /**
         * 評価レベル名（予備２）
         */
    @TableField("mr_cevaluationlevelname02")
        private String mrCevaluationlevelname02;

        /**
         * 評価レベル名（評価者用）
         */
    @TableField("mr_cevaluatorlevelname")
        private String mrCevaluatorlevelname;

        /**
         * 評価レベル名（評価者用_日本語）
         */
    @TableField("mr_cevaluatorlevelnameja")
        private String mrCevaluatorlevelnameja;

        /**
         * 評価レベル名（評価者用_英語）
         */
    @TableField("mr_cevaluatorlevelnameen")
        private String mrCevaluatorlevelnameen;

        /**
         * 評価レベル名（評価者用_中国語）
         */
    @TableField("mr_cevaluatorlevelnamech")
        private String mrCevaluatorlevelnamech;

        /**
         * 評価レベル名（評価者用_予備１）
         */
    @TableField("mr_cevaluatorlevelname01")
        private String mrCevaluatorlevelname01;

        /**
         * 評価レベル名（評価者用_予備２）
         */
    @TableField("mr_cevaluatorlevelname02")
        private String mrCevaluatorlevelname02;

        /**
         * システムコード
         */
    @TableField("mr_csystemid")
        private String mrCsystemid;

        /**
         * 優先順位
         */
    @TableField("mr_nweightage")
        private Long mrNweightage;

        /**
         * クラス名
         */
    @TableField("mr_cclassname")
        private String mrCclassname;

        /**
         * 評価系区分
         */
    @TableField("mr_nevaluation")
        private Long mrNevaluation;

        /**
         * 役割関係使用区分
         */
    @TableField("mr_cusage")
        private String mrCusage;

        /**
         * 評価除外リレーション区分
         */
    @TableField("mr_cothers")
        private String mrCothers;

        /**
         * 最終更新者
         */
    @TableField("mr_cmodifieruserid")
        private String mrCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mr_dmodifieddate")
        private Date mrDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }