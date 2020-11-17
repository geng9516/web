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
@TableName("t_error_audit")
@KeySequence("T_ERROR_AUDIT_AUDIT_ID_SEQ")
@ToString
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "auditId")
public class ErrorAuditDO {

  @TableId
  private Long auditId;
  private String url;
  private String username;
  private String calledMethod;
  private String method;
  private String params;
  private String ip;
  private String userAgent;
  private String message;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

}
