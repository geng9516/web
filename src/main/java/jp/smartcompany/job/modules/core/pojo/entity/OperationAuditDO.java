package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.job.modules.base.pojo.entity.BaseBean;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author Xiao Wenpeng
 */
@TableName("t_operation_audit")
@ToString
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(of = "auditId",callSuper = true)
public class OperationAuditDO extends BaseBean {

  @TableId
  private Long auditId;
  private String username;
  private String operation;
  private String url;
  private String method;
  private String params;
  private Long time;
  private String ip;

}
