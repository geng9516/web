package jp.smartcompany.job.modules.core.mapper.TmgCalendarSection;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarSectionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarMonthDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * [勤怠]カレンダー : Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgCalendarSectionMapper extends BaseMapper<TmgCalendarSectionDO> {

    List<CalendarDispVo> selectCalenderDisp(@Param("custID") String custID,
                                            @Param("compCode")String compCode,
                                            @Param("targetSec")String targetSec,
                                            @Param("targetGroup")String targetGroup,
                                            @Param("year")String year,
                                            @Param("psSection")String psSection,
                                            @Param("psMode")String psMode);

    int insertCalendarSecton(@Param("custID")String custID,
                             @Param("compCode")String compCode,
                             @Param("targetSec")String targetSec,
                             @Param("userCode")String userCode,
                             @Param("targetGroup")String targetGroup,
                             @Param("month")String month,
                             @Param("monthDto")List<String> monthDto);

    int updateCalendar(@Param("custID")String custID,
                       @Param("compCode")String compCode,
                       @Param("targetSec")String targetSec,
                       @Param("targetGroup")String targetGroup,
                       @Param("userCode")String userCode,
                       @Param("month")String month,
                       @Param("holFlgList")List<CalendarColumnDto> holFlgList);
}
