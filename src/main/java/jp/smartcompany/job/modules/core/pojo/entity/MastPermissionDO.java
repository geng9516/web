package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mast_permission")
@KeySequence("MAST_PERMISSION_SEQ")
public class MastPermissionDO implements Serializable {

    @TableId(value="mp_id")
    private Long mpId;
    @TableField(value="MP_CCUSTOMERID")
    private String mpCcustomerid;
    @TableField(value="MP_CSYSTEMID")
    private String mpCsystemid;
    @TableField(value="MP_CDOMAINID")
    private String mpCdomainid;
    @TableField(value="MP_CGROUPID")
    private String mpCgroupid;
    @TableField(value="MP_CRELATIONSHIPID")
    private String mpCrelationshipid;
    @TableField(value="MP_CLEVELID")
    private String mpClevelid;
    @TableField(value="MP_DSTARTDATE")
    private Date mpDstartdate;
    @TableField(value="MP_DENDDATE")
    private Date mpDenddate;
    @TableField(value="MP_CMODIFIERUSERID")
    private String mpCmodifieruserid;
    @TableField(value="MP_DMODIFIEDDATE")
    private Date mpDmodifieddate;
    @TableField(value="MP_NPERMISSION")
    private Long mpNpermission;
    @TableField(value="MP_NREFUSAL")
    private Long mpNrefusal;
    @TableField(value="VERSIONNO")
    @Version
    private Long versionno;

}
