package jp.smartcompany.job.modules.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiao Wenpeng
 */
@TableName("t_session")
@ToString
@Getter
@Setter
@Accessors(chain = true)
public class SessionDO implements Serializable {

    private static final long serialVersionUID = -4571368078280432032L;
    @TableId(type= IdType.INPUT)
    private String sessionId;
    private String sessionValue;

}
