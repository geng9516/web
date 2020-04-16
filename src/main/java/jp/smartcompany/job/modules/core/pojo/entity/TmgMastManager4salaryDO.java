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
 * 給与用管理職判定マスタ                   人給発令データから連携した管理職ﾌﾗｸﾞ・指定職ﾌﾗｸﾞを元
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
@TableName("tmg_mast_manager4salary")
public class TmgMastManager4salaryDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tmms_ccustomerid")
        private String tmmsCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tmms_ccompanyid")
        private String tmmsCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tmms_dstartdate")
        private Date tmmsDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tmms_denddate")
        private Date tmmsDenddate;

        /**
         * 更新者
         */
    @TableField("tmms_cmodifieruserid")
        private String tmmsCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tmms_dmodifieddate")
        private Date tmmsDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tmms_cmodifierprogramid")
        private String tmmsCmodifierprogramid;

        /**
         * 管理職ﾌﾗｸﾞ
         */
    @TableField("tmms_cmanagerflg")
        private String tmmsCmanagerflg;

        /**
         * 指定職ﾌﾗｸﾞ
         */
                @TableId(value = "tmms_cspecifyflg", type = IdType.AUTO)
                private String tmmsCspecifyflg;

        /**
         * 管理職ｺｰﾄﾞ
         */
    @TableField("tmms_cmanagerid")
        private String tmmsCmanagerid;


        }