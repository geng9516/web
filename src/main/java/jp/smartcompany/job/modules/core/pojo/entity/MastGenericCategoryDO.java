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
 * 名称マスタカテゴリー
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
@TableName("mast_generic_category")
public class MastGenericCategoryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "mgc_id", type = IdType.AUTO)
                private Long mgcId;

        /**
         * 顧客区分
         */
    @TableField("mgc_ccustomerid")
        private String mgcCcustomerid;

        /**
         * カテゴリ区分
         */
    @TableField("mgc_ccategoryid")
        private String mgcCcategoryid;

        /**
         * カテゴリ名称
         */
    @TableField("mgc_ccategoryname")
        private String mgcCcategoryname;

        /**
         * 最終更新者
         */
    @TableField("mgc_cmodifieruserid")
        private String mgcCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("mgc_dmodifieddate")
        private Date mgcDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }