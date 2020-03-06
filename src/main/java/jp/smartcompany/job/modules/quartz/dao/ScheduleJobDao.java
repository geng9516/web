package jp.smartcompany.job.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobDO> {

    /**
     * 批量更新定时任务
     * @param map 更新的数据
     * @return int
     */
    int updateBatch(Map<String, Object> map);

}
