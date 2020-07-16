package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import jp.smartcompany.job.modules.core.mapper.TmgTimepunchMapper;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * [勤怠]打刻
 *
 * @author X02461
 */
@Repository
public class TmgTimepunchServiceImpl extends ServiceImpl<TmgTimepunchMapper, TmgTimepunchDO> implements ITmgTimepunchService {

    @Override
    public void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid, String ctpTypeid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        params.put("ctpTypeid", ctpTypeid);
        baseMapper.insertTmgTimePunch(params);
    }

    @Override
    public void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        baseMapper.insertTmgTrgger(params);
    }

    @Override
    public void deleteTmgTrgger(String custId, String compCode, String employeeId, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("modifierprogramid", modifierprogramid);
        baseMapper.deleteTmgTrgger(params);
    }

    @Override
    public String selectIsTimePunchTarget(String custId, String compCode, String employeeId, String targetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        return baseMapper.selectIsTimePunchTarget(params);
    }

    @Override
    public BaseTimesDTO selectBaseTimes(String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectBaseTimes(params);
    }

    @Override
    public String selectBaseTimesWithPattern(String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        return baseMapper.selectBaseTimesWithPattern(params);
    }

    @Override
    public List<DutyDaysAndHoursDTO> selectDutyDaysAndHoursSQL(String custId, String compCode, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectDutyDaysAndHoursSQL(params);
    }

    @Override
    public HashMap<String, Object> selectDutyDaysAndHours(String custId, String compCode, String employeeId, String targetDate, List<DutyDaysAndHoursDTO> dutyDaysAndHoursDTOList) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        params.put("list", dutyDaysAndHoursDTOList);
        return baseMapper.selectDutyDaysAndHours(params);
    }

    @Override
    public String selectOverTime(String custId, String compCode, String employeeId, String startDate, String endDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        return baseMapper.selectOverTime(params);
    }

    @Override
    public ScheduleInfoDTO selectScheduleInfo(String custId, String compCode, String employeeId, String targetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        return baseMapper.selectScheduleInfo(params);
    }

    @Override
    public String selectErrMsg(String employeeId, String targetDate, String timepunch, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        params.put("timepunch", timepunch);
        params.put("custId", custId);
        params.put("compCode", compCode);
        return baseMapper.selectErrMsg(params);
    }
}
