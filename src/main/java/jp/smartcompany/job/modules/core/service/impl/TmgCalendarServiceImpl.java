package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import jp.smartcompany.job.modules.core.mapper.TmgCalendarMapper;
import jp.smartcompany.job.modules.core.service.ITmgCalendarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarMonthDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.calendarDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]カレンダー 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgCalendarServiceImpl extends ServiceImpl<TmgCalendarMapper, TmgCalendarDO> implements ITmgCalendarService {


    /**
     * 前翌年度有無判定を取得するSQLを返す
     */
    @Override
    public calendarDto selectCalendar(String custId, String compId, int year, String baseDate) {
        return baseMapper.selectCalendar(custId, compId, year, baseDate);
    }

    /**
     * 対象勤務年月の1ヶ月間の日付・曜日を取得
     */
    @Override
    public List<OneMonthDetailVo> selectDayCount(String baseDay) {
        return baseMapper.selectDayCount(baseDay);
    }

    /**
     * カレンダーテーブルより休日フラグを取得。
     */
    @Override
    public List<CalenderVo> selectGetCalendarList(String custId, String compId, String secId, String groupId, String targetYYYY, String sBaseDate) {
        return baseMapper.selectGetCalendarList(custId, compId, secId, groupId, targetYYYY, sBaseDate);
    }

    /**
     * カレンダー情報(全学)を取得します
     */
    @Override
    public List<CalendarDispVo> selectCalenderDisp(String custID, String compCode, String year){
        return baseMapper.selectCalenderDisp(custID, compCode, year);
    }


    /**
     * カレンダー情報を更新します。
     */
    @Override
    public int updateCalendar(String custID, String compCode, String userCode,String month, List<CalendarColumnDto> monthDto){
        return baseMapper.updateCalendar( custID, compCode, userCode,month, monthDto);
    }
}
