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
 * public.tmg_daynight_limit_check
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
@TableName("tmg_daynight_limit_check")
public class TmgDaynightLimitCheckDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tdnl_ccustomerid
         */
    @TableId(value="tdnl_ccustomerid",type = IdType.INPUT)
        private String tdnlCcustomerid;

        /**
         * tdnl_ccompanyid
         */
    @TableField("tdnl_ccompanyid")
        private String tdnlCcompanyid;

        /**
         * tdnl_dstartdate
         */
    @TableField("tdnl_dstartdate")
        private Date tdnlDstartdate;

        /**
         * tdnl_denddate
         */
    @TableField("tdnl_denddate")
        private Date tdnlDenddate;

        /**
         * tdnl_cmodifieruserid
         */
    @TableField("tdnl_cmodifieruserid")
        private String tdnlCmodifieruserid;

        /**
         * tdnl_dmodifieddate
         */
    @TableField("tdnl_dmodifieddate")
        private Date tdnlDmodifieddate;

        /**
         * tdnl_cmodifierprogramid
         */
    @TableField("tdnl_cmodifierprogramid")
        private String tdnlCmodifierprogramid;

        /**
         * tdnl_csectionid
         */
    @TableField("tdnl_csectionid")
        private String tdnlCsectionid;

        /**
         * tdnl_cgroupid
         */
    @TableField("tdnl_cgroupid")
        private String tdnlCgroupid;

        /**
         * tdnl_cpostid
         */
    @TableField("tdnl_cpostid")
        private String tdnlCpostid;

        /**
         * tdnl_ndayduty_weekly
         */
    @TableField("tdnl_ndayduty_weekly")
        private Long tdnlNdaydutyWeekly;

        /**
         * tdnl_nnightduty_weekly
         */
    @TableField("tdnl_nnightduty_weekly")
        private Long tdnlNnightdutyWeekly;

        /**
         * tdnl_ndayduty_monthly
         */
    @TableField("tdnl_ndayduty_monthly")
        private Long tdnlNdaydutyMonthly;

        /**
         * tdnl_nnightduty_monthly
         */
    @TableField("tdnl_nnightduty_monthly")
        private Long tdnlNnightdutyMonthly;


        }