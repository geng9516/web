package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import jp.smartcompany.job.modules.core.mapper.TmgAttendanceBookMapper;
import jp.smartcompany.job.modules.core.service.ITmgAttendanceBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceBookDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceDateInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.AttendanceEndueTimeInfoDTO;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.MastGenericDetailDTO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public AttendanceDateInfoDTO selectDateInfo(String dyyyymmdd, String firstDayOfYear, String employeeId, String compCode, String custId, String language) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dyyyymmdd", dyyyymmdd);
        params.put("firstDayOfYear", firstDayOfYear);
        params.put("employeeId", employeeId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectDateInfo(params);
    }

    @Override
    public AttendanceEndueTimeInfoDTO selectEndueTimeInfo(String dyyyymmdd, String employeeId, String preMonthDay, String nextYearDay, String compCode, String custId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dyyyymmdd", dyyyymmdd);
        params.put("employeeId", employeeId);
        params.put("preMonthDay", preMonthDay);
        params.put("nextYearDay", nextYearDay);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectEndueTimeInfo(params);
    }

    @Override
    public List<AttendanceBookDTO> selectAttendanceBookList(String employeeId, String curMonthDay, String nextYearDay, String compCode, String custId, List<String> list) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("curMonthDay", curMonthDay);
        params.put("nextYearDay", nextYearDay);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("list", list);
        return baseMapper.selectAttendanceBookList(params);
    }

    @Override
    public boolean updateComment(String employeeId, String modirieruserId, String yearLastDay, String comment, String compCode, String custId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("employeeId", employeeId);
        params.put("modirieruserId", modirieruserId);
        params.put("yearLastDay", yearLastDay);
        params.put("comment", comment);
        params.put("compCode", compCode);
        params.put("custId", custId);
        try {
            baseMapper.updateComment(params);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HashMap<String, String> selectComment(String employeeId, String yearLastDay, String compCode, String custId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("employeeId", employeeId);
        params.put("yearLastDay", yearLastDay);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectComment(params);
    }

    @Override
    public List<MastGenericDetailDTO> selectTotalDataQueryList(String items, int displayLine) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("items", items);
        params.put("displayLine", displayLine);
        return baseMapper.selectTotalDataQueryList(params);
    }
}
