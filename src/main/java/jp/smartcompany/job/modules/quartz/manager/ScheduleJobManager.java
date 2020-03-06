package jp.smartcompany.job.modules.quartz.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.quartz.pojo.dto.ScheduleJobDTO;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobDO;
import jp.smartcompany.job.modules.quartz.pojo.enums.JobState;
import jp.smartcompany.job.util.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
public interface ScheduleJobManager extends IService<ScheduleJobDO> {

    /**
     * 获取任务列表
     * @param params 查询参数
     * @return PageUtil
     */
    PageUtil getJobList(Map<String, Object> params);

    /**
     * 新建任务
     * @param jobDTO 新建任务的具体数据
     */
    void saveJob(ScheduleJobDTO jobDTO);

    /**
     * 更新任务
     * @param jobDTO 更新任务的具体数据
     */
    void updateJob(ScheduleJobDTO jobDTO);

    /**
     * 批量删除任务
     * @param jobIds 要删除的任务id
     */
    void deleteBatch(List<Long> jobIds);

    /**
     * 批量更新的任务id
     * @param jobIds 要更新的任务id
     * @param state 任务状态
     */
    void updateBatch(List<Long> jobIds, JobState state);

    /**
     * 运行任务
     * @param ids 任务id
     */
    void run(List<Long> ids);

    /**
     * 暂停任务
     * @param ids 任务id
     */
    void pause(List<Long> ids);

    /**
     * 唤醒任务
     * @param ids 任务id
     */
    void resume(List<Long> ids);

}
