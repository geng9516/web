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
@TableName("t_login_audit")
@KeySequence("T_LOGIN_AUDIT_AUDIT_ID_SEQ")
@ToString
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "auditId")
public class LoginAuditDO {

  @TableId
  private Long auditId;
  private String username;
  private String operation;
  private Boolean status;
  private String ip;
  private String userAgent;
  @TableField(fill = FieldFill.INSERT)
  private Date createTime;
  @TableField(fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

}
