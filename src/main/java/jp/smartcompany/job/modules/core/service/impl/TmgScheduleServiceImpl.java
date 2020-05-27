package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgScheduleMapper;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * @author 陳毅力
 * @description 予定作成
 * @objectSource
 * @date 2020/05/25
 **/
@Repository
public class TmgScheduleServiceImpl extends ServiceImpl<TmgScheduleMapper, Object> implements ITmgScheduleService {
    @Override
    public NpaidRestDTO selectTmgMonthly(String employeeId, String workYear, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("workYear", workYear);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectTmgMonthly(params);
    }

    @Override
    public ScheduleDataDTO selectSchedule(String ctpye_plan, String startDispDate, String endDispDate, String dStart, String dEnd, String manageflg, String employeeId, String compCode, String custId, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("startDispDate", startDispDate);
        params.put("endDispDate", endDispDate);
        params.put("dStart", dStart);
        params.put("dEnd", dEnd);
        params.put("manageflg", manageflg);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectSchedule(params);
    }

    @Override
    public HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(String baseDate, String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectBaseDateOf4WeeksBeforeDay(params);
    }

    @Override
    public String selectLinkOfNextMonth(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectLinkOfNextMonth(params);
    }

    @Override
    public HashMap<String, Object> selectLinkOfPreMonth(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectLinkOfPreMonth(params);
    }

    @Override
    public TargetUserDetailDTO selectTargetUserDetail(String employeeId, String baseDate, String custId, String compCode, String startDispDate, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("startDispDate", startDispDate);
        params.put("language", language);
        return baseMapper.selectTargetUserDetail(params);
    }

    @Override
    public String selectWorkingHours(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectWorkingHours(params);
    }

    @Override
    public HalfwayPaidHolidayDTO selectHalfwayPaidHoliday(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectHalfwayPaidHoliday(params);
    }

    @Override
    public TmgWeekPatternDTO selectTmgWeekPattern(String employeeId, String baseDate, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectTmgWeekPattern(params);
    }

    @Override
    public int selectVariationalWorkInfo(String employeeId, String targetDate, String custId, String compCode, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectVariationalWorkInfo(params);
    }

    @Override
    public int selectVariationalWorkDays(String employeeId, String targetDate, String custId, String compCode, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("targetDate", targetDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        return baseMapper.selectVariationalWorkDays(params);
    }

    @Override
    public HashMap<String, Object> selectDsipDate(String employeeId, String baseDate, String lastday, String custId, String compCode) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("lastday", lastday);
        return baseMapper.selectDsipDate(params);
    }




}
