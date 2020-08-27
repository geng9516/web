package jp.smartcompany.job.modules.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.core.mapper.TmgScheduleMapper;
import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.tmg.schedule.dto.*;
import jp.smartcompany.job.modules.tmg.schedule.vo.TmgWeekPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.vo.WeekWorkType;
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
    public List<ScheduleDataDTO> selectSchedule(String ctpye_plan, String dStart, String dEnd, boolean isVariationalWorkDays, String manageflg, String employeeId, String compCode, String custId, String language) {
        System.out.println("dStart:"+dStart+" dEnd："+dEnd);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ctpye_plan", ctpye_plan);
        params.put("employeeId", employeeId);
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
    public HashMap<String, Object> selectBaseDateOf4WeeksBeforeDay(String baseDate, String detailPeriod, String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("detailPeriod", detailPeriod);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.selectBaseDateOf4WeeksBeforeDay(params);
    }

    @Override
    public HashMap<String, Object> SelectBaseDateOf4WeeksAfterDay(String baseDate, String detailPeriod, String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("detailPeriod", detailPeriod);
        params.put("compCode", compCode);
        params.put("custId", custId);
        return baseMapper.SelectBaseDateOf4WeeksAfterDay(params);
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
    public List<TmgWeekPatternDTO> selectTmgWeekPattern(String employeeId, String baseDate, String custId, String compCode, boolean isAfter) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("isAfter", isAfter);
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

    @Override
    public void deleteDailyCheck(String employeeId, String compCode, String custId, String sTargetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("sTargetDate", sTargetDate);
        baseMapper.deleteDailyCheck(params);
    }

    @Override
    public void deleteDailyDetailCheck(String employeeId, String compCode, String custId, String sTargetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("employeeId", employeeId);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("sTargetDate", sTargetDate);
        baseMapper.deleteDailyDetailCheck(params);
    }

    @Override
    public void insertTmgDailyCheck(String sLoginUserCode, String tmg_schedule_cmodifierprogramid, boolean isClearResult, String cs_mgd_holflg, String tda_cworkingid_p, String tda_nopen_p, String tda_nclose_p, boolean bNoWorking,
                                    String tda_cbusinesstripid_p, String tda_ccomment_p, String employeeid, String dyyyymmdd, String compCode, String custId, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("sLoginUserCode", sLoginUserCode);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        params.put("isClearResult", isClearResult);
        params.put("cs_mgd_holflg", cs_mgd_holflg);
        params.put("tda_cworkingid_p", tda_cworkingid_p);
        params.put("tda_nopen_p", tda_nopen_p);
        params.put("tda_nclose_p", tda_nclose_p);
        params.put("bNoWorking", bNoWorking);
        params.put("tda_cbusinesstripid_p", tda_cbusinesstripid_p);
        params.put("tda_ccomment_p", tda_ccomment_p);
        params.put("employeeid", employeeid);
        params.put("dyyyymmdd", dyyyymmdd);
        params.put("compCode", compCode);
        params.put("custId", custId);
        params.put("language", language);
        baseMapper.insertTmgDailyCheck(params);
    }

    @Override
    public void insertTmgDailyDetailCheckRest(String custId, String compCode, String employeeId, String minDate, String maxDate, String sLoginUserCode, String tmg_schedule_cmodifierprogramid, String sTargetDate, String sNotWorkId,
                                              String nRestOpen, String nRestClose, String notworkingid_plan_rest, String notworkingid_notice_rest, String notworkingid_result_rest, boolean isClearResult, boolean isNotWorkId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        params.put("sLoginUserCode", sLoginUserCode);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        params.put("sTargetDate", sTargetDate);
        params.put("sNotWorkId", sNotWorkId);
        params.put("nRestOpen", nRestOpen);
        params.put("nRestClose", nRestClose);
        params.put("notworkingid_plan_rest", notworkingid_plan_rest);
        params.put("notworkingid_notice_rest", notworkingid_notice_rest);
        params.put("notworkingid_result_rest", notworkingid_result_rest);
        params.put("isClearResult", isClearResult);
        params.put("isNotWorkId", isNotWorkId);
        baseMapper.insertTmgDailyDetailCheckRest(params);
    }

    @Override
    public void insertTmgTrigger(String custId, String compCode, String employeeId, String minDate, String maxDate, String sLoginUserCode, String tmg_schedule_cmodifierprogramid, String sTargetDate, String act_editmonthly_uschedule) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        params.put("sLoginUserCode", sLoginUserCode);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        params.put("sTargetDate", sTargetDate);
        params.put("act_editmonthly_uschedule", act_editmonthly_uschedule);
        baseMapper.insertTmgTrigger(params);

    }

    @Override
    public void deleteTmgTrigger(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        baseMapper.deleteTmgTrigger(params);
    }

    @Override
    public void deleteErrMsg(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        baseMapper.deleteErrMsg(params);
    }

    @Override
    public void deleteWeekPatternCheck(String custId, String compCode, String employeeId, String tmg_schedule_cmodifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("tmg_schedule_cmodifierprogramid", tmg_schedule_cmodifierprogramid);
        baseMapper.deleteWeekPatternCheck(params);
    }

    @Override
    public void deleteDetailCheck(String custId, String compCode, String sLoginUserCode, String sTargetDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("sLoginUserCode", sLoginUserCode);
        params.put("sTargetDate", sTargetDate);
        baseMapper.deleteDetailCheck(params);
    }

    @Override
    public TmgWeekPatternVO selectCsvReference(String custId, String compCode, String language, String employeeId, int twp_nid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("twp_nid", twp_nid);
        params.put("language", language);
        return baseMapper.selectCsvReference(params);
    }

    /**
     * 週勤務パターンリストを取得する
     *
     * @param custId
     * @param compCode
     * @param language
     * @param employeeId
     * @return
     */
    @Override
    public List<TmgWeekPatternVO> selectCsvReferenceList(String custId, String compCode, String language, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("language", language);
        return baseMapper.selectCsvReferenceList(params);
    }


    /**
     * [勤怠]週次勤務パターン（エラーチェック用）登録
     *
     * @param tmgWeekPatternCheckDTO
     */
    @Override
    public void insertTmgWeekPatternCheck(TmgWeekPatternCheckDTO tmgWeekPatternCheckDTO) {
        baseMapper.insertTmgWeekPatternCheck(tmgWeekPatternCheckDTO);
    }

    @Override
    public void insertErrMsg(String custId, String compCode, String language, String employeeId, String modifieruserid, String modifierprogramid, String minDate, String maxDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("modifieruserid", modifieruserid);
        params.put("language", language);
        params.put("modifierprogramid", modifierprogramid);
        params.put("minDate", minDate);
        params.put("maxDate", maxDate);
        baseMapper.insertErrMsg(params);

    }

    @Override
    public String selectErrMsg(String custId, String compCode, String modifieruserid, String modifierprogramid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("modifieruserid", modifieruserid);
        params.put("modifierprogramid", modifierprogramid);
        return baseMapper.selectErrMsg(params);
    }

    @Override
    public void insertTrigger(String custId, String compCode, String employeeId, String sLoginUserCode, String modifierprogramid, String sAction) {

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("sLoginUserCode", sLoginUserCode);
        params.put("modifierprogramid", modifierprogramid);
        params.put("employeeId", employeeId);
        params.put("sAction", sAction);
        baseMapper.insertTrigger(params);

    }

    @Override
    public String selectBaseDateFor4Week(String custId, String compCode, String employeeId, String startDate, String endDate, int param4WeekAfter) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("employeeId", employeeId);
        params.put("param4WeekAfter", param4WeekAfter);

        return baseMapper.selectBaseDateFor4Week(params);
    }

    @Override
    public HashMap<String, String> selectTmgDailyMinAndMax(String custId, String compCode, String employeeId) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        return baseMapper.selectTmgDailyMinAndMax(params);
    }

    @Override
    public String selectMonthlyModifiedDate(String custId, String compCode, String employeeId, String baseDate) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        return baseMapper.selectMonthlyModifiedDate(params);
    }

    @Override
    public void updateSchedulePermStatus(String custId, String compCode, String employeeId, String baseDate, String modifierprogramid, String onff, String type) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("baseDate", baseDate);
        params.put("modifierprogramid", modifierprogramid);
        params.put("onff", onff);
        params.put("type", type);
        baseMapper.updateSchedulePermStatus(params);
    }

    @Override
    public List<WeekWorkType> selectWeekPtn(String custId, String compCode, String language) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("language", language);
        return baseMapper.selectWeekPtn(params);
    }

    @Override
    public void deleteWeekPtn(String custId, String compCode, String twp_nid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("twp_nid", twp_nid);
        baseMapper.deleteWeekPtn(params);
    }

    @Override
    public int buildSQLForSelectInsertTmgWeekPatternCheck(String custId, String compCode, String employeeId, String modifieruserid, String modifierprogramid, String twp_dstartdate, String twp_denddate, String twp_nid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("modifieruserid", modifieruserid);
        params.put("modifierprogramid", modifierprogramid);
        params.put("twp_dstartdate", twp_dstartdate);
        params.put("twp_denddate", twp_denddate);
        params.put("twp_nid", twp_nid);
        return baseMapper.buildSQLForSelectInsertTmgWeekPatternCheck(params);
    }

    @Override
    public int updateWeekPattern(String twp_dstartdate, String twp_denddate, String twp_nid,String modifieruserid) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("twp_dstartdate", twp_dstartdate);
        params.put("twp_denddate", twp_denddate);
        params.put("twp_nid", twp_nid);
        params.put("modifieruserid", modifieruserid);
        return baseMapper.updateWeekPattern(params);
    }

    @Override
    public TmgStatusDTO buildSQLForSelectTmgStatus(String custId, String compCode, String employeeId, String dstart, String dend, String group) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("dstart", dstart);
        params.put("dend", dend);
        params.put("group", group);
        return baseMapper.buildSQLForSelectTmgStatus(params);
    }

    @Override
    public List<TmgStatusDTO> buildSQLForSelectTmgStatus2(String custId, String compCode, String employeeId, String dyyyymmdd) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custId);
        params.put("compCode", compCode);
        params.put("employeeId", employeeId);
        params.put("dyyyymmdd", dyyyymmdd);
        return baseMapper.buildSQLForSelectTmgStatus2(params);
    }

}
