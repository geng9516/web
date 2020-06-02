package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgScheduleMapper;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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
    public List<ScheduleDataDTO> selectSchedule(String ctpye_plan, String startDispDate, String endDispDate, String dStart, String dEnd, boolean isVariationalWorkDays, String manageflg, String employeeId, String compCode, String custId, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ctpye_plan", ctpye_plan);
        params.put("employeeId", employeeId);
        params.put("startDispDate", startDispDate);
        params.put("endDispDate", endDispDate);
        params.put("dStart", dStart);
        params.put("dEnd", dEnd);
        params.put("isVariationalWorkDays", isVariationalWorkDays);
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

    @Override
    public HashMap<String, Object> selectLinkOfNextMonthNextSaturday(String employeeId, String baseDate, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);

        return baseMapper.selectLinkOfNextMonthNextSaturday(params);
    }

    @Override
    public String selectDetailPeriod(String employeeId, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectDetailPeriod(params);
    }

    @Override
    public String selectIsStart4weeks(String targetDate, String detailPeriod) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("targetDate", targetDate);
        params.put("detailPeriod", detailPeriod);
        return baseMapper.selectIsStart4weeks(params);
    }

    @Override
    public List<HashMap<String, Object>> selectGenericDetail(String language, String baseDate, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("language", language);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectGenericDetail(params);
    }

    @Override
    public List<HashMap<String, Object>> selectBusinessTrip(String language, String baseDate, String compCode, String custId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("language", language);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectBusinessTrip(params);
    }

    @Override
    public List<HashMap<String, Object>> selectWorkPatternIkkatu(String compCode, String custId, String sectionid, String groupid, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("compCode", compCode);
        params.put("baseDate", baseDate);
        params.put("sectionid", sectionid);
        params.put("groupid", groupid);
        params.put("custId", custId);
        return baseMapper.selectWorkPatternIkkatu(params);
    }


}
