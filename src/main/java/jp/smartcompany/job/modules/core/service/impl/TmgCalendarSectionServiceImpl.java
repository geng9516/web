package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCalendarSectionDO;
import jp.smartcompany.job.modules.core.mapper.TmgCalendarSection.TmgCalendarSectionMapper;
import jp.smartcompany.job.modules.core.service.ITmgCalendarSectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.calendar.dto.CalendarColumnDto;
import jp.smartcompany.job.modules.tmg.calendar.vo.CalendarDispVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * [勤怠]カレンダー : 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
        public class TmgCalendarSectionServiceImpl extends ServiceImpl<TmgCalendarSectionMapper, TmgCalendarSectionDO> implements ITmgCalendarSectionService {

        /**
         * カレンダー情報(組織)を取得します
         */
        @Override
        public List<CalendarDispVo> selectCalenderDisp(String custID, String compCode, String targetSec,
                                                String targetGroup, String year,String psSection, String psMode){
                return baseMapper.selectCalenderDisp( custID,  compCode,  targetSec,
                         targetGroup,year,  psSection,  psMode);
        }


        /**
         * カレンダー情報を登録します。
         */
        @Override
        public int insertCalendarSecton(String custID, String compCode, String targetSec, String userCode, String targetGroup,String month, List<String> monthDto){
                return baseMapper.insertCalendarSecton( custID,  compCode,  targetSec, userCode, targetGroup,month, monthDto);
        }


        /**
         * カレンダー情報を登録します。
         */
        @Override
        public int updateCalendar(String custID, String compCode, String targetSec, String targetGroup, String userCode,String month, List<CalendarColumnDto> holFlgList){
                return baseMapper.updateCalendar( custID, compCode, targetSec, targetGroup, userCode,month, holFlgList);
        }
        }
