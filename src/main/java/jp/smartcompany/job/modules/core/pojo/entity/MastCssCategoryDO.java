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
 * スタイルシートカテゴリマスタ
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
@TableName("mast_css_category")
public class MastCssCategoryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mcc_id", type = IdType.AUTO)
                private Long mccId;

        /**
         * カテゴリid
         */
    @TableField("mcc_ccategoryid")
        private String mccCcategoryid;

        /**
         * カテゴリ名称
         */
    @TableField("mcc_ccategoryname")
        private String mccCcategoryname;

        /**
         * 項目名（日本語）
         */
    @TableField("mcc_ccategorynameja")
        private String mccCcategorynameja;

        /**
         * 項目名（英語）
         */
    @TableField("mcc_ccategorynameen")
        private String mccCcategorynameen;

        /**
         * 項目名（中国語）
         */
    @TableField("mcc_ccategorynamech")
        private String mccCcategorynamech;

        /**
         * 項目名（予備１）
         */
    @TableField("mcc_ccategoryname01")
        private String mccCcategoryname01;

        /**
         * 項目名（予備２）
         */
    @TableField("mcc_ccategoryname02")
        private String mccCcategoryname02;

        /**
         * cssファイル接頭辞
         */
    @TableField("mcc_ccssprefix")
        private String mccCcssprefix;

        /**
         * デフォルトcssフラグ
         */
    @TableField("mcc_ccssdefaultflg")
        private String mccCcssdefaultflg;

        /**
         * 最終更新者
         */
    @TableField("mcc_cmodifieruserid")
        private String mccCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mcc_dmodifieddate")
        private Date mccDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }