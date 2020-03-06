package jp.smartcompany.job.modules.quartz.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.smartcompany.job.modules.base.pojo.entity.BaseBean;
import jp.smartcompany.job.modules.quartz.pojo.enums.JobState;
import lombok.*;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
@TableName("t_schedule_job_log")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="logId",callSuper = true)
public class ScheduleJobLogDO extends BaseBean {

    /**
     * 日志id
     */
    @TableId
    private Long logId;
    /**
     * 任务id
     */
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
     * 任务状态    0：成功    1：失败
     */
    private Boolean status;
    /**
     * 失败信息
     */
    private String error;
    /**
     * 耗时(单位：毫秒)
     */
    private Long time;
    /**
     * 任务状态
     */
    @TableField(value = "status")
    private JobState jobState;

}
