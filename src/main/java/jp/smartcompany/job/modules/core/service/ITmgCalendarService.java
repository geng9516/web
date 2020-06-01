package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.calendarDto;

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
        calendarDto selectCalendar(String custId,String compId,int year,String baseDate);

        }
