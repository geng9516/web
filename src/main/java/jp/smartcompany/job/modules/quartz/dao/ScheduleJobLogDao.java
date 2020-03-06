package jp.smartcompany.job.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.quartz.pojo.entity.ScheduleJobLogDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Xiao Wenpeng
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogDO> {
}
