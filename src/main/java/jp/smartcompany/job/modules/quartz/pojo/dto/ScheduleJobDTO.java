package jp.smartcompany.job.modules.quartz.pojo.dto;

import jp.smartcompany.job.group.UpdateGroup;
import jp.smartcompany.job.modules.quartz.JobValidateMessage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Xiao Wenpeng
 */
@Getter
@Setter
@ToString
public class ScheduleJobDTO {

    /**
     * 任务ID
     */
    @NotNull(message= JobValidateMessage.JOB_ID_EMPTY,groups = UpdateGroup.class)
    private Integer jobId;
    /**
     * spring bean名称
     */
    @NotBlank(message= JobValidateMessage.BEAN_NAME_EMPTY)
    @Length(min=1,max=100,message = JobValidateMessage.BEAN_NAME_LENGTH_1_100)
    private String beanName;
    /**
     * 参数
     */
    @Length(min=1,max=100,message = JobValidateMessage.PARAM_LENGTH_1_100)
    private String params;
    /**
     * cron表达式
     */
    @NotBlank(message=JobValidateMessage.JOB_EXPRESS_EMPTY)
    @Length(min=1,max=100,message = JobValidateMessage.EXPRESS_LENGTH_1_100)
    private String cronExpression;
    /**
     * 任务状态
     */
    @NotNull(message=JobValidateMessage.TASK_STATUS_EMPTY)
    @Range(min=0,max = 1,message=JobValidateMessage.TASK_STATUS_INVALID)
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
