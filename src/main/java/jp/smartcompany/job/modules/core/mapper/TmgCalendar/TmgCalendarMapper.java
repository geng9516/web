package jp.smartcompany.job.modules.core.mapper.TmgCalendar;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.CalendarDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    CalendarDto selectCalendar(@Param("custId") String custId,
                               @Param("compId") String compId,
                               @Param("year") int year,
                               @Param("baseDate") String baseDate);


    List<OneMonthDetailVo> selectDayCount(@Param("baseDate") String baseDate);


    CalenderVo selectGetCalendarList(@Param("custId") String custId,
                                           @Param("compId") String compId,
                                           @Param("secId") String secId,
                                           @Param("groupId") String groupId,
                                           @Param("targetYYYY") String targetYYYY,
                                           @Param("sBaseDate") String sBaseDate);

    List<CalendarDispVo> selectCalenderDisp(@Param("custID")String custID,
                                            @Param("compCode")String compCode,
                                            @Param("year")String year);

    int updateCalendar(@Param("custID")String custID,
                       @Param("compCode")String compCode,
                       @Param("userCode")String userCode,
                       @Param("month")String month,
                       @Param("List")List<CalendarColumnDto> monthDto);
}
