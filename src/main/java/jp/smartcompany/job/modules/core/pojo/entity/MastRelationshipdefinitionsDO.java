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
 * 役割関係マスタ
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
@TableName("mast_relationshipdefinitions")
public class MastRelationshipdefinitionsDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mrd_id", type = IdType.AUTO)
                private Long mrdId;

        /**
         * 顧客コード
         */
    @TableField("mrd_ccustomerid")
        private String mrdCcustomerid;

        /**
         * システムコード
         */
    @TableField("mrd_csystemid")
        private String mrdCsystemid;

        /**
         * コンテンツ区分
         */
    @TableField("mrd_ccontenttype")
        private String mrdCcontenttype;

        /**
         * 開始日
         */
    @TableField("mrd_dstartdate")
        private Date mrdDstartdate;

        /**
         * 終了日
         */
    @TableField("mrd_denddate")
        private Date mrdDenddate;

        /**
         * 最終更新者
         */
    @TableField("mrd_cmodifieruserid")
        private String mrdCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mrd_dmodifieddate")
        private Date mrdDmodifieddate;

        /**
         * 検索者ユーザid
         */
    @TableField("mrd_cuserid_from")
        private String mrdCuseridFrom;

        /**
         * 被検索者ユーザid
         */
    @TableField("mrd_cuserid_to")
        private String mrdCuseridTo;

        /**
         * リレーションid
         */
    @TableField("mrd_crelationshipid")
        private String mrdCrelationshipid;

        /**
         * 確定フラグ
         */
    @TableField("mrd_cfixed")
        private String mrdCfixed;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }