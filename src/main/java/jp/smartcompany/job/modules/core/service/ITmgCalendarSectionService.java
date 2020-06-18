package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarSectionDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarMonthDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;

import java.util.List;

/**
 * <p>
 * [勤怠]カレンダー : 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgCalendarSectionService extends IService<TmgCalendarSectionDO> {

    /**
     * カレンダー情報(組織)を取得します
     */
    List<CalendarDispVo> selectCalenderDisp(String custID, String compCode, String targetSec,
                                            String targetGroup,String year, String psSection, String psMode);

    /**
     * カレンダー情報を登録します。
     */
    int insertCalendarSecton(String custID, String compCode, String targetSec, String userCode, String targetGroup, List<String> monthDto);

    /**
     * カレンダー情報を登録します。
     */
    int updateCalendar(String custID, String compCode, String targetSec, String targetGroup, String userCode,String month, List<CalendarColumnDto> holFlgList);
}
