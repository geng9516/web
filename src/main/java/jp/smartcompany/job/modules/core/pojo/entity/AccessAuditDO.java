package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.job.modules.base.pojo.entity.BaseBean;
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
@EqualsAndHashCode(of = "auditId",callSuper = true)
public class AccessAuditDO extends BaseBean {

    @TableId
    private Long auditId;
    private String url;
    private String method;
    private Integer status;
    private Long time;
    private String ip;
    private Date requestTime;
    private Date responseTime;

}
