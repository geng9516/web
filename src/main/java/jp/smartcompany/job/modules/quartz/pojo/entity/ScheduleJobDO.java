package jp.smartcompany.job.modules.quartz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import jp.smartcompany.job.modules.base.pojo.entity.BaseBean;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.job.modules.quartz.pojo.enums.JobState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@TableName("t_schedule_job")
@Accessors(chain = true)
public class ScheduleJobDO extends BaseBean implements Serializable {

    private static final long serialVersionUID = 3674665247834665128L;

    @TableId
    private Long jobId;
    /**
     * spring bean名称
     */
    private String beanName;
    /**
     * 参数
     */
    private String params;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务状态
     */
    @TableField(value = "status")
    private JobState jobState;
    /**
     * 备注
     */
    private String remark;

}
