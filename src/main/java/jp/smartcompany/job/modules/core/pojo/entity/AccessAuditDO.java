package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Xiao Wenpeng
 */
@TableName("t_access_audit")
@KeySequence("T_ACCESS_AUDIT_AUDIT_ID_SEQ")
@ToString
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "auditId")
public class AccessAuditDO {

    @TableId
    private Long auditId;
    private String url;
    private String method;
    private Integer status;
    private Long time;
    private String ip;
    private Date requestTime;
    private Date responseTime;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
