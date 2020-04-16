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
 * システムプロパティカテゴリマスタ
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
@TableName("conf_syscategory")
public class ConfSyscategoryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * idカラム
         */
                @TableId(value = "csc_id", type = IdType.AUTO)
                private Long cscId;

        /**
         * 顧客id
         */
    @TableField("csc_ccustomerid")
        private String cscCcustomerid;

        /**
         * カテゴリ区分
         */
    @TableField("csc_ccategoryid")
        private String cscCcategoryid;

        /**
         * カテゴリ名称
         */
    @TableField("csc_ccategoryname")
        private String cscCcategoryname;

        /**
         * 最終更新者
         */
    @TableField("csc_cmodifieruserid")
        private String cscCmodifieruserid;

        /**
         * 最終更新日
         */
    @TableField("csc_dmodifieddate")
        private Date cscDmodifieddate;

        /**
         * バージョンno
         */
    @TableField("versionno")
        private Long versionno;


        }