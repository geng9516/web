package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.CalendarDto;

import java.util.List;

/**
 * <p>
 * [勤怠]カレンダー 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgCalendarService extends IService<TmgCalendarDO> {
    /**
     * 前翌年度有無判定を取得するSQLを返す
     */
    CalendarDto selectCalendar(String custId, String compId, int year, String baseDate);

    /**
     * 対象勤務年月の1ヶ月間の日付・曜日を取得
     */
    List<OneMonthDetailVo> selectDayCount(String baseDay);

    /**
     * カレンダーテーブルより休日フラグを取得。
     */
    List<CalenderVo> selectGetCalendarList(String custId, String compId, String secId, String groupId, String targetYYYY, String sBaseDate);

    /**
     * カレンダー情報(全学)を取得します
     */
    List<CalendarDispVo> selectCalenderDisp(String custID, String compCode, String year);

    /**
     * カレンダー情報を更新します。
     */
    int updateCalendar(String custID, String compCode, String userCode,String month, List<CalendarColumnDto> monthDto);
}
