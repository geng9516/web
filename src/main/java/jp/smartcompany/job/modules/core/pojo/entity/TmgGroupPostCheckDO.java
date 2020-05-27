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
 * public.tmg_group_post_check
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
@TableName("tmg_group_post_check")
public class TmgGroupPostCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tgrp_ccustomerid
         */
    @TableId(value="tgrp_ccustomerid",type = IdType.INPUT)
        private String tgrpCcustomerid;

        /**
         * tgrp_ccompanyid
         */
    @TableField("tgrp_ccompanyid")
        private String tgrpCcompanyid;

        /**
         * tgrp_dstartdate
         */
    @TableField("tgrp_dstartdate")
        private Date tgrpDstartdate;

        /**
         * tgrp_denddate
         */
    @TableField("tgrp_denddate")
        private Date tgrpDenddate;

        /**
         * tgrp_cmodifieruserid
         */
    @TableField("tgrp_cmodifieruserid")
        private String tgrpCmodifieruserid;

        /**
         * tgrp_dmodifieddate
         */
    @TableField("tgrp_dmodifieddate")
        private Date tgrpDmodifieddate;

        /**
         * tgrp_cmodifierprogramid
         */
    @TableField("tgrp_cmodifierprogramid")
        private String tgrpCmodifierprogramid;

        /**
         * tgrp_csectionid
         */
    @TableField("tgrp_csectionid")
        private String tgrpCsectionid;

        /**
         * tgrp_cgroupid
         */
    @TableField("tgrp_cgroupid")
        private String tgrpCgroupid;

        /**
         * tgrp_cpostid
         */
    @TableField("tgrp_cpostid")
        private String tgrpCpostid;

        /**
         * tgrp_ndayduty_weekly
         */
    @TableField("tgrp_ndayduty_weekly")
        private Long tgrpNdaydutyWeekly;

        /**
         * tgrp_nnightduty_weekly
         */
    @TableField("tgrp_nnightduty_weekly")
        private Long tgrpNnightdutyWeekly;

        /**
         * tgrp_ndayduty_monthly
         */
    @TableField("tgrp_ndayduty_monthly")
        private Long tgrpNdaydutyMonthly;

        /**
         * tgrp_nnightduty_monthly
         */
    @TableField("tgrp_nnightduty_monthly")
        private Long tgrpNnightdutyMonthly;


        }