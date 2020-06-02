package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.calendarDto;

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
    calendarDto selectCalendar(String custId, String compId, int year, String baseDate);

    /**
     * 対象勤務年月の1ヶ月間の日付・曜日を取得
     */
    List<OneMonthDetailVo> selectDayCount(String baseDay);

    /**
     * カレンダーテーブルより休日フラグを取得。
     */
    List<CalenderVo> selectGetCalendarList(String custId, String compId, String secId, String groupId, String targetYYYY, String sBaseDate);
}
