package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import jp.smartcompany.job.modules.core.mapper.TmgCalendarMapper;
import jp.smartcompany.job.modules.core.service.ITmgCalendarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.CalenderVo;
import jp.smartcompany.job.modules.tmg.OvertimeInstruct.vo.OneMonthDetailVo;
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
}
