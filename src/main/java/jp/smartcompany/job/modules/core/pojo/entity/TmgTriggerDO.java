package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tmg_trigger")
public class TmgTriggerDO {

    @TableId(type = IdType.INPUT,value = "ttr_ccustomerid")
    private String ttrCcustomerid;
    @TableField(value="ttr_ccompanyid")
    private String ttrCcompanyid;
    @TableField(value="ttr_cemployeeid")
    private String ttrCemployeeid;
    @TableField(value="ttr_dstartdate")
    private Date ttrDstartdate;
    @TableField(value="ttr_denddate")
    private Date ttrDenddate;
    @TableField(value="ttr_cmodifieruserid")
    private String ttrCmodifieruserid;
    @TableField(value="ttr_dmodifieddate")
    private Date ttrDmodifieddate;
    @TableField(value="ttr_cmodifierprogramid")
    private String ttrCmodifierprogramid;
    @TableField(value="ttr_cprogramid")
    private String ttrCprogramid;
    @TableField(value="ttr_nparameter1")
    private Double ttrNparameter1;
    @TableField(value="ttr_nparameter2")
    private Double ttrNparameter2;
    @TableField(value="ttr_nparameter3")
    private Double ttrNparameter3;
    @TableField(value="ttr_nparameter4")
    private Double ttrNparameter4;
    @TableField(value="ttr_nparameter5")
    private Double ttrNparameter5;
    @TableField(value="ttr_cparameter1")
    private String ttrCparameter1;
    @TableField(value="ttr_cparameter2")
    private String ttrCparameter2;
    @TableField(value="ttr_cparameter3")
    private String ttrCparameter3;
    @TableField(value="ttr_cparameter4")
    private String ttrCparameter4;
    @TableField(value="ttr_cparameter5")
    private String ttrCparameter5;
    @TableField(value="ttr_dparameter1")
    private Date ttrDparameter1;
    @TableField(value="ttr_dparameter2")
    private Date ttrDparameter2;
    @TableField(value="ttr_dparameter3")
    private Date ttrDparameter3;
    @TableField(value="ttr_dparameter4")
    private Date ttrDparameter4;
    @TableField(value="ttr_dparameter5")
    private Date ttrDparameter5;

}
