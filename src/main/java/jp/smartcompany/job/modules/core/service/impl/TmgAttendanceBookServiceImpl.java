package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import jp.smartcompany.job.modules.core.mapper.TmgAttendanceBookMapper;
import jp.smartcompany.job.modules.core.service.ITmgAttendanceBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * [勤怠]出勤簿情報 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgAttendanceBookServiceImpl extends ServiceImpl<TmgAttendanceBookMapper, TmgAttendanceBookDO> implements ITmgAttendanceBookService {

    @Override
    public AttendanceDateInfoDTO selectDateInfo(String dyyyymmdd, String firstDayOfYear, String employeeId) {
        return null;
    }

    @Override
    public AttendanceEndueTimeInfoDTO selectEndueTimeInfo(String dyyyymmdd, String employeeId, String preMonthDay, String nextYearDay) {
        return null;
    }

    @Override
    public List<AttendanceBookDTO> selectAttendanceBookList(String employeeId, String curMonthDay, String nextYearDay) {
        return null;
    }

    @Override
    public boolean updateComment(String employeeId, String modifieruserId, String yearLastDay, String comment) {
        return false;
    }

    @Override
    public HashMap<String, String> selectComment(String employeeId, String yearLastDay) {
        return null;
    }
}
