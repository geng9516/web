package jp.smartcompany.job.modules.quartz.manager.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.quartz.JobBean;
import jp.smartcompany.job.modules.quartz.dao.ScheduleJobLogDao;
import jp.smartcompany.job.modules.quartz.manager.ScheduleJobLogManager;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobLogDO;
import org.springframework.stereotype.Repository;

/**
 * @author Xiao Wenpeng
 */
@Repository(JobBean.Manager.SCHEDULE_JOB_LOG)
public class ScheduleJobLogManagerImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogDO> implements ScheduleJobLogManager {
}
