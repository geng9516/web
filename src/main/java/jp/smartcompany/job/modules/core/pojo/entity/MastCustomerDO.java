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
 * 顧客マスタ
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
@TableName("mast_customer")
public class MastCustomerDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mc_id", type = IdType.AUTO)
                private Long mcId;

        /**
         * 顧客コード
         */
    @TableField("mc_ccustomerid_pk")
        private String mcCcustomeridPk;

        /**
         * 顧客名称
         */
    @TableField("mc_ccustomername")
        private String mcCcustomername;

        /**
         * 削除フラグ
         */
    @TableField("mc_cdelflag")
        private String mcCdelflag;

        /**
         * 削除日付
         */
    @TableField("mc_ddeldate")
        private Date mcDdeldate;

        /**
         * 作成日付
         */
    @TableField("mc_dcreateddate")
        private Date mcDcreateddate;

        /**
         * 言語区分
         */
    @TableField("mc_clanguage")
        private String mcClanguage;

        /**
         * 最終更新者
         */
    @TableField("mc_cmodifieruserid")
        private String mcCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mc_dmodifieddate")
        private Date mcDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }