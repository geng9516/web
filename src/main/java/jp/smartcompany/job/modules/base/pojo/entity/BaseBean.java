package jp.smartcompany.job.modules.base.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 逻辑删除bean
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public abstract class BaseBean {

    @TableField(fill = FieldFill.INSERT)
    protected Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;

}
