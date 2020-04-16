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
 * public.tmg_hist_manager
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
@TableName("tmg_hist_manager")
public class TmgHistManagerDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客コード
         */
    @TableField("thm_ccustomerid")
        private String thmCcustomerid;

        /**
         * 法人コード
         */
    @TableField("thm_ccompanyid")
        private String thmCcompanyid;

        /**
         * 職員番号
         */
                @TableId(value = "thm_cemployeeid", type = IdType.AUTO)
                private String thmCemployeeid;

        /**
         * 開始日付
         */
    @TableField("thm_dstartdate")
        private Date thmDstartdate;

        /**
         * 終了日付
         */
    @TableField("thm_denddate")
        private Date thmDenddate;

        /**
         * 更新者
         */
    @TableField("thm_cmodifieruserid")
        private String thmCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("thm_dmodifieddate")
        private Date thmDmodifieddate;

        /**
         * 更新プログラム
         */
    @TableField("thm_cmodifierprogramid")
        private String thmCmodifierprogramid;

        /**
         * 管理職フラグ
         */
    @TableField("thm_cmanagerflg")
        private String thmCmanagerflg;

        /**
         * 指定職フラグ
         */
    @TableField("thm_cspecifyflg")
        private String thmCspecifyflg;


        }