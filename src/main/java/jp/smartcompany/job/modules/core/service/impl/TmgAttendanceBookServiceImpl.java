package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgAttendanceBookDO;
import jp.smartcompany.job.modules.core.mapper.TmgAttendanceBookMapper;
import jp.smartcompany.job.modules.core.service.ITmgAttendanceBookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.attendanceBook.dto.*;
import jp.smartcompany.job.modules.tmg.attendanceBook.vo.AttendanceExistsVO;
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
    public AttendanceDateInfoDTO selectTargetDateInfo(String dyyyymmdd,String month, String firstDayOfYear, String employeeId, String compCode, String custId, String language) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dyyyymmdd", dyyyymmdd);
        params.put("month", month);
        params.put("firstDayOfYear", firstDayOfYear);
        params.put("employeeId", employeeId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectTargetDateInfo(params);
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
    public void updateComment(String employeeId, String modifieruserId, String yearLastDay, String comment, String compCode, String custId) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("employeeId", employeeId);
        params.put("modifieruserId", modifieruserId);
        params.put("yearLastDay", yearLastDay);
        params.put("comment", comment);
        params.put("compCode", compCode);
        params.put("custId", custId);
        baseMapper.updateComment(params);
    }

    @Override
    public AttendanceBookCommentDTO selectComment(String employeeId, String yearLastDay, String compCode, String custId) {
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

    @Override
    public AttendanceBookEmpDTO selectEmployeesBasicInfo(String employeeId, String queryMonthDay, String nextYearDay, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("queryMonthDay", queryMonthDay);
        params.put("nextYearDay", nextYearDay);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectEmployeesBasicInfo(params);
    }

    @Override
    public AttendanceExistsVO selectExistsAttendanceBook(String baseDate, String employeeId, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectExistsAttendanceBook(params);
    }
}
