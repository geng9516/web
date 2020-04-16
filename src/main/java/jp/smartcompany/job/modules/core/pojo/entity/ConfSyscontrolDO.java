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
 * システムプロパティマスタ
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
@TableName("conf_syscontrol")
public class ConfSyscontrolDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "cs_id", type = IdType.AUTO)
                private Long csId;

        /**
         * 顧客id
         */
    @TableField("cs_ccustomerid")
        private String csCcustomerid;

        /**
         * プロパティ名称
         */
    @TableField("cs_cpropertyname")
        private String csCpropertyname;

        /**
         * プロパティ値
         */
    @TableField("cs_cpropertyvalue")
        private String csCpropertyvalue;

        /**
         * プロパティ説明
         */
    @TableField("cs_cpropertydesc")
        private String csCpropertydesc;

        /**
         * カテゴリ
         */
    @TableField("cs_ccategory")
        private String csCcategory;

        /**
         * 削除可否フラグ
         */
    @TableField("cs_cdelflg")
        private String csCdelflg;

        /**
         * 最終更新者
         */
    @TableField("cs_cmodifieruserid")
        private String csCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("cs_dmodifieddate")
        private Date csDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }