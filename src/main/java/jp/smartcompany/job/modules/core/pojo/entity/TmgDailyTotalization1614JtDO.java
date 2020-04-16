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
 * public.tmg_daily_totalization_1614_jt
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
@TableName("tmg_daily_totalization_1614_jt")
public class TmgDailyTotalization1614JtDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tdt_ccustomerid
         */
    @TableField("tdt_ccustomerid")
        private String tdtCcustomerid;

        /**
         * tdt_ccompanyid
         */
    @TableField("tdt_ccompanyid")
        private String tdtCcompanyid;

        /**
         * tdt_cemployeeid
         */
    @TableField("tdt_cemployeeid")
        private String tdtCemployeeid;

        /**
         * tdt_dstartdate
         */
    @TableField("tdt_dstartdate")
        private Date tdtDstartdate;

        /**
         * tdt_denddate
         */
    @TableField("tdt_denddate")
        private Date tdtDenddate;

        /**
         * tdt_cmodifieruserid
         */
    @TableField("tdt_cmodifieruserid")
        private String tdtCmodifieruserid;

        /**
         * tdt_dmodifieddate
         */
    @TableField("tdt_dmodifieddate")
        private Date tdtDmodifieddate;

        /**
         * tdt_cmodifierprogramid
         */
    @TableField("tdt_cmodifierprogramid")
        private String tdtCmodifierprogramid;

        /**
         * tdt_dyyyymmdd
         */
    @TableField("tdt_dyyyymmdd")
        private Date tdtDyyyymmdd;

        /**
         * tdt_dtargetdate
         */
    @TableField("tdt_dtargetdate")
        private Date tdtDtargetdate;

        /**
         * tdt_ctotalizationid
         */
    @TableField("tdt_ctotalizationid")
        private String tdtCtotalizationid;

        /**
         * tdt_cjournalizingid
         */
    @TableField("tdt_cjournalizingid")
        private String tdtCjournalizingid;

        /**
         * tdt_nvalue
         */
    @TableField("tdt_nvalue")
        private Long tdtNvalue;


        }