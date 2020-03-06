package jp.smartcompany.job.modules.quartz.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.common.Query;
import jp.smartcompany.job.modules.quartz.JobBean;
import jp.smartcompany.job.modules.quartz.JobConstant;
import jp.smartcompany.job.modules.quartz.dao.ScheduleJobDao;
import jp.smartcompany.job.modules.quartz.manager.ScheduleJobManager;
import jp.smartcompany.job.modules.quartz.pojo.dto.ScheduleJobDTO;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobDO;
import jp.smartcompany.job.modules.quartz.pojo.enums.JobState;
import jp.smartcompany.job.modules.quartz.util.ScheduleUtil;
import jp.smartcompany.job.util.PageUtil;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Repository(JobBean.Manager.SCHEDULE_JOB)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScheduleJobManagerImpl extends ServiceImpl<ScheduleJobDao, ScheduleJobDO> implements ScheduleJobManager {

    private final Scheduler scheduler;

    @PostConstruct
    public void init(){
        List<ScheduleJobDO> scheduleJobList = list();
        for(ScheduleJobDO scheduleJob : scheduleJobList){
            CronTrigger cronTrigger = ScheduleUtil.getCronTrigger(scheduler, scheduleJob.getJobId());
            if(cronTrigger == null) {
                ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }

    @Override
    public PageUtil getJobList(Map<String, Object> params) {
        String key = (String)params.get(JobConstant.KEY_KEYWORD);
        IPage<ScheduleJobDO> page = page(
                new Query<ScheduleJobDO>().getPage(params),
                SysUtil.<ScheduleJobDO>query()
                        .like(StrUtil.isNotBlank(key),"bean_name", key)
                        .or().like(StrUtil.isNotBlank(key),"remark", key)
        );
        return new PageUtil(page);
    }

    @Override
    public void saveJob(ScheduleJobDTO jobDTO) {
        ScheduleJobDO scheduleJob = new ScheduleJobDO();
        BeanUtil.copyProperties(jobDTO, scheduleJob);
        scheduleJob.setJobState(JobState.getByValue(jobDTO.getStatus()));
        save(scheduleJob);
        ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    public void updateJob(ScheduleJobDTO jobDTO) {
        ScheduleJobDO scheduleJob = new ScheduleJobDO();
        BeanUtil.copyProperties(jobDTO, scheduleJob);
        scheduleJob
                .setJobState(JobState.getByValue(jobDTO.getStatus()));
        ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
        updateById(scheduleJob);
    }

    @Override
    public void deleteBatch(List<Long> jobIds) {
        jobIds.forEach(jobId -> ScheduleUtil.deleteScheduleJob(scheduler, jobId));
        removeByIds(jobIds);
    }

    @Override
    public void updateBatch(List<Long> jobIds, JobState state) {
        Map<String, Object> map = MapUtil.newHashMap(2);
        map.put("list", jobIds);
        map.put("status", state.getValue());
        baseMapper.updateBatch(map);
    }

    @Override
    public void run(List<Long> ids) {
        ids.forEach(jobId -> ScheduleUtil.run(scheduler, getById(jobId)));
    }

    @Override
    public void pause(List<Long> ids) {
        ids.forEach(jobId -> ScheduleUtil.pauseJob(scheduler, jobId));
    }

    @Override
    public void resume(List<Long> ids) {
        ids.forEach(jobId -> ScheduleUtil.resumeJob(scheduler, jobId));
        updateBatch(ids, JobState.NORMAL);
    }

}
