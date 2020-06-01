package jp.smartcompany.job.modules.core.mapper;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.calendarDto;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.dateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * [勤怠]カレンダー Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgCalendarMapper extends BaseMapper<TmgCalendarDO> {


        calendarDto selectCalendar(@Param("custId")String custId,
                                   @Param("compId")String compId,
                                   @Param("year")int year,
                                   @Param("baseDate")String baseDate);
        }
