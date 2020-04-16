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
 * public.tmg_group_post
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
@TableName("tmg_group_post")
public class TmgGroupPostDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * 顧客ｺｰﾄﾞ                        固定：01
         */
    @TableField("tgrp_ccustomerid")
        private String tgrpCcustomerid;

        /**
         * 法人ｺｰﾄﾞ
         */
    @TableField("tgrp_ccompanyid")
        private String tgrpCcompanyid;

        /**
         * ﾃﾞｰﾀ開始日
         */
    @TableField("tgrp_dstartdate")
        private Date tgrpDstartdate;

        /**
         * ﾃﾞｰﾀ終了日
         */
    @TableField("tgrp_denddate")
        private Date tgrpDenddate;

        /**
         * 更新者
         */
    @TableField("tgrp_cmodifieruserid")
        private String tgrpCmodifieruserid;

        /**
         * 更新日
         */
    @TableField("tgrp_dmodifieddate")
        private Date tgrpDmodifieddate;

        /**
         * 更新プログラムid
         */
    @TableField("tgrp_cmodifierprogramid")
        private String tgrpCmodifierprogramid;

        /**
         * 部署コード                                                       mo:mo_csectionid_ck
         */
    @TableField("tgrp_csectionid")
        private String tgrpCsectionid;

        /**
         * グループコード                       グループ作成時にidを付番
         */
    @TableField("tgrp_cgroupid")
        private String tgrpCgroupid;

        /**
         * 役職コード
         */
    @TableField("tgrp_cpostid")
        private String tgrpCpostid;

        /**
         * 日直回数上限値(週次)
         */
    @TableField("tgrp_ndayduty_weekly")
        private Long tgrpNdaydutyWeekly;

        /**
         * 宿直回数上限値(週次)
         */
    @TableField("tgrp_nnightduty_weekly")
        private Long tgrpNnightdutyWeekly;

        /**
         * 日直回数上限値(月次)
         */
    @TableField("tgrp_ndayduty_monthly")
        private Long tgrpNdaydutyMonthly;

        /**
         * 宿直回数上限値(月次)
         */
    @TableField("tgrp_nnightduty_monthly")
        private Long tgrpNnightdutyMonthly;


        }