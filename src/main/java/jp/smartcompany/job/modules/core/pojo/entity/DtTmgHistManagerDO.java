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
 * public.dt_tmg_hist_manager
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
@TableName("dt_tmg_hist_manager")
public class DtTmgHistManagerDO implements Serializable {

private static final long serialVersionUID=1L;


    @TableId(value="thm_ccustomerid",type = IdType.INPUT)
        private String thmCcustomerid;


    @TableField("thm_ccompanyid")
        private String thmCcompanyid;


                private String thmCemployeeid;

    @TableField("thm_dstartdate")
        private Date thmDstartdate;


    @TableField("thm_denddate")
        private Date thmDenddate;


    @TableField("thm_cmodifieruserid")
        private String thmCmodifieruserid;


    @TableField("thm_dmodifieddate")
        private Date thmDmodifieddate;


    @TableField("thm_cmodifierprogramid")
        private String thmCmodifierprogramid;


    @TableField("thm_cmanagerflg")
        private String thmCmanagerflg;

    @TableField("thm_cspecifyflg")
        private String thmCspecifyflg;


        }