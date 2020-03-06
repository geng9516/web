package jp.smartcompany.job.modules.quartz.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import jp.smartcompany.job.modules.quartz.JobBean;
import jp.smartcompany.job.modules.quartz.JobConstant;
import jp.smartcompany.job.modules.quartz.manager.ScheduleJobLogManager;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobDO;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobLogDO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * @author Xiao Wenpeng
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        ScheduleJobDO scheduleJob = (ScheduleJobDO) context.getMergedJobDataMap()
                .get(JobConstant.JOB_PARAM_KEY);
        ScheduleJobLogManager scheduleJobLogManager = SpringUtil.getBean(
                JobBean.Manager.SCHEDULE_JOB_LOG
        );
        // 数据库保存执行记录
        ScheduleJobLogDO jobLog = new ScheduleJobLogDO();
        jobLog.setJobId(scheduleJob.getJobId());
        jobLog.setBeanName(scheduleJob.getBeanName());
        jobLog.setParams(scheduleJob.getParams());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            //执行任务
            Object target = SpringUtil.getBean(scheduleJob.getBeanName());
            Method method = target.getClass().getDeclaredMethod("run", String.class);
            method.invoke(target, scheduleJob.getParams());
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTime(times);
            //任务状态 1：成功 0失败
            jobLog.setStatus(true);
        } catch (Exception e) {
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            jobLog.setTime(times);
            //任务状态 1：成功 0：失败
            jobLog.setStatus(false);
            jobLog.setError(StrUtil.sub(e.toString(), 0, 2000));
        }finally {
           scheduleJobLogManager.save(jobLog);
        }
    }

}
