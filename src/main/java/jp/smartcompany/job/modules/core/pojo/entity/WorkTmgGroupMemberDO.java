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
 * public.work_tmg_group_member
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
@TableName("work_tmg_group_member")
public class WorkTmgGroupMemberDO implements Serializable {

private static final long serialVersionUID=1L;

        /**
         * tgrm_ccustomerid
         */
    @TableId(value="tgrm_ccustomerid",type=IdType.INPUT)
        private String tgrmCcustomerid;

        /**
         * tgrm_ccompanyid
         */
    @TableField(value="tgrm_ccompanyid")
        private String tgrmCcompanyid;

        /**
         * tgrm_cemployeeid
         */
    @TableField("tgrm_cemployeeid")
        private String tgrmCemployeeid;

        /**
         * tgrm_dstartdate
         */
    @TableField("tgrm_dstartdate")
        private Date tgrmDstartdate;

        /**
         * tgrm_denddate
         */
    @TableField("tgrm_denddate")
        private Date tgrmDenddate;

        /**
         * tgrm_cmodifieruserid
         */
    @TableField("tgrm_cmodifieruserid")
        private String tgrmCmodifieruserid;

        /**
         * tgrm_dmodifieddate
         */
    @TableField("tgrm_dmodifieddate")
        private Date tgrmDmodifieddate;

        /**
         * tgrm_cmodifierprogramid
         */
    @TableField("tgrm_cmodifierprogramid")
        private String tgrmCmodifierprogramid;

        /**
         * tgrm_csectionid
         */
    @TableField("tgrm_csectionid")
        private String tgrmCsectionid;

        /**
         * tgrm_cgroupid
         */
    @TableField("tgrm_cgroupid")
        private String tgrmCgroupid;

        /**
         * tgrm_cchar01
         */
    @TableField("tgrm_cchar01")
        private String tgrmCchar01;

        /**
         * tgrm_cbase_sectionid
         */
    @TableField("tgrm_cbase_sectionid")
        private String tgrmCbaseSectionid;

        /**
         * tgrm_cbase_groupid
         */
    @TableField("tgrm_cbase_groupid")
        private String tgrmCbaseGroupid;


        }