package jp.smartcompany.job.modules.core.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.mapper.TmgTimepunch.TmgTimepunchMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTimepunchDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.ITmgTimepunchService;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.tmg.timepunch.dto.BaseTimesDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.DutyDaysAndHoursDTO;
import jp.smartcompany.job.modules.tmg.timepunch.dto.ScheduleInfoDTO;
import jp.smartcompany.job.modules.tmg.timepunch.vo.ClockInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * [勤怠]打刻
 *
 * @author X02461
 */
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgTimepunchServiceImpl extends ServiceImpl<TmgTimepunchMapper, TmgTimepunchDO> implements ITmgTimepunchService {

    private final ITmgTriggerService tmgTriggerService;

    @Override
    public void insertTmgTimePunch(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid, String ctpTypeid) {
        tmgTriggerService.stamp(ctpTypeid,employeeId,modifierprogramid, SysUtil.transStringToDate(PsConst.MINDATE) , SysUtil.transStringToDate(PsConst.MAXDATE));
    }

    @Override
    public void insertTmgTrgger(String custId, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid) {
        TmgTriggerDO tmgTriggerDO = new TmgTriggerDO();
        tmgTriggerDO.setTtrCcompanyid(compCode);
        tmgTriggerDO.setTtrCcustomerid(custId);
        tmgTriggerDO.setTtrCemployeeid(employeeId);
        tmgTriggerDO.setTtrCprogramid(modifierprogramid);
        tmgTriggerDO.setTtrDmodifieddate(DateUtil.date());
        tmgTriggerDO.setTtrCmodifieruserid(employeeId);
        tmgTriggerDO.setTtrCmodifierprogramid(modifierprogramid);
        tmgTriggerDO.setTtrDstartdate(SysUtil.transStringToDate(minDate));
        tmgTriggerDO.setTtrDenddate(SysUtil.transStringToDate(maxDate));
        tmgTriggerService.save(tmgTriggerDO);
    }

    @Override
    public void deleteTmgTrgger(String custId, String compCode, String employeeId, String modifierprogramid) {
        Map<String, Object> params = MapUtil.newHashMap();
        params.put("ttr_ccompanyid", compCode);
        params.put("ttr_ccustomerid", custId);
        params.put("ttr_cemployeeid", employeeId);
        params.put("ttr_cmodifieruserid",employeeId);
        params.put("ttr_cmodifierprogramid",modifierprogramid);
        tmgTriggerService.removeByMap(params);
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

    @Override
    public ClockInfoVO selectClockInfo(String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("custId", custId);
        params.put("compCode", compCode);
        return baseMapper.selectClockInfo(params);
    }

    @Override
    public int selectEmpPattern(String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("custId", custId);
        params.put("compCode", compCode);
        return baseMapper.selectEmpPattern(params);
    }

    @Override
    public int selectPatternChangeTime(String custId, String compCode, String sectionId, String patternId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("sectionId", sectionId);
        params.put("patternId", patternId);
        return baseMapper.selectPatternChangeTime(params);
    }

}
