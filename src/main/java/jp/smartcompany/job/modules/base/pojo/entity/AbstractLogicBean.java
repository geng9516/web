package jp.smartcompany.job.modules.base.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public abstract class AbstractLogicBean extends BaseBean{

    @Version
    protected Long version;
    @TableLogic
    protected Integer delFlag;

}
