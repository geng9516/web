package jp.smartcompany.job.modules.tmg.evaluatersetting;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.dbaccess.DbControllerLogic;
import jp.smartcompany.job.modules.core.util.AjaxBean;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsDBBeanUtil;
import jp.smartcompany.job.modules.core.util.PsResult;
import jp.smartcompany.job.modules.tmg.evaluatersetting.dto.*;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EditMemberVO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorGroupVO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorMemberRightVO;
import jp.smartcompany.job.modules.tmg.evaluatersetting.vo.EvaluatorMemberVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgSearchEmpList;
import jp.smartcompany.job.modules.tmg.util.TmgSearchRangeUtil;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.List;

/**
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EvaluatorSettingBean {

    private TmgReferList referList;
    private final TmgSearchRangeUtil tmgSearchRangeUtil;
    private final ScCacheUtil scCacheUtil;
    private final AjaxBean ajaxBean;
    private final PsDBBeanUtil psDBBeanUtil;
    private final DbControllerLogic dbControllerLogic;
    private final DataSource dataSource;

    // ?????????????????????
    private final static int IDX_LIST			 = 0;	// ???????????????????????????????????????
    private final static int IDX_EVALNUM		 = 1;	// ???????????????????????????
    private final static int IDX_DATE			 = 2;	// ?????????????????????
    private final static int IDX_EVALEMPNUM	 = 3;	// ??????????????????????????????????????????

    private static final int QUERY_SHOWMAKEGROUP_OverTimeLimit = 0;
    private static final int QUERY_SHOWEDITGROUP_OverTimeLimit = 1;

    public static final int COL_OVERTIMELIMIT_OT_MONTLY_01          = 0;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_02          = 1;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_03          = 2;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_04          = 3;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_05          = 4;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_01          = 5;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_02          = 6;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_03          = 7;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_04          = 8;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_05          = 9;
    public static final int COL_OVERTIMELIMIT_OT_MONTHLY_COUNT      = 10;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_01_MGD      = 11;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_02_MGD      = 12;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_03_MGD      = 13;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_04_MGD      = 14;
    public static final int COL_OVERTIMELIMIT_OT_MONTLY_05_MGD      = 15;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_01_MGD      = 16;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_02_MGD      = 17;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_03_MGD      = 18;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_04_MGD      = 19;
    public static final int COL_OVERTIMELIMIT_OT_YEARLY_05_MGD      = 20;
    public static final int COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD  = 21;
    public static final int COL_OVERTIMELIMIT_OT_DAILY_01           = 22;
    public static final int COL_OVERTIMELIMIT_OT_DAILY_01_MGD       = 23;
    public static final int COL_OVERTIMELIMIT_OT_MONTHLY_AVG      = 24;//??????????????????????????????
    public static final int COL_OVERTIMELIMIT_OT_MONTHLY_AVG_MGD  = 25;


    public static final int QUERY_SHOWMAKEGROUP_HolidayTimeLimit = 1;
    public static final int QUERY_SHOWEDITGROUP_HolidayTimeLimit = 2;

    public static final int COL_OVERTIMELIMIT_HT_MONTLY_01      = 0;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_02      = 1;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_03      = 2;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_04      = 3;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_05      = 4;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_01_MGD  = 5;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_02_MGD  = 6;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_03_MGD  = 7;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_04_MGD  = 8;
    public static final int COL_OVERTIMELIMIT_HT_MONTLY_05_MGD  = 9;


    public static final int QUERY_SHOWMAKEGROUP_NightLimitSelf = 2;
    public static final int QUERY_SHOWEDITGROUP_NightLimitSelf = 5;

    public static final int COL_NightLimitSelf_DAYNIGHTDUTY_WEEKLY  = 0;
    public static final int COL_NightLimitSelf_DAYNIGHTDUTY_MONTHLY = 1;

    public static final int QUERY_SHOWMAKEGROUP_DayLimitSelf = 3;
    public static final int QUERY_SHOWEDITGROUP_DayLimitSelf = 6;

    public static final int COL_DayLimitSelf_DAYNIGHTDUTY_WEEKLY  = 0;
    public static final int COL_DayLimitSelf_DAYNIGHTDUTY_MONTHLY = 1;

    public static final int QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf = 4;

    public static final int COL_HolidayTimeLimitSelf_HT_MONTLY_01 = 0;
    public static final int COL_HolidayTimeLimitSelf_HT_MONTLY_02 = 1;
    public static final int COL_HolidayTimeLimitSelf_HT_MONTLY_03 = 2;
    public static final int COL_HolidayTimeLimitSelf_HT_MONTLY_04 = 3;
    public static final int COL_HolidayTimeLimitSelf_HT_MONTLY_05 = 4;

    /** ???????????????????????????????????????????????????????????????????????????????????????????????? */
    public static final int QUERY_SHOWEDITGROUP_GroupAttribute = 5;

    /** ?????????????????????????????????????????????????????? */
    public static final int COL_GroupAttribute_AutoSet_Eva  = 0;

    private final int IDX_GROUPNAME = 0; // ??????????????????

    public static final int QUERY_SHOWEDITGROUP_OverTimeLimitSelf = 3;

    public static final int COL_OverTimeLimitSelf_OT_MONTLY_01     = 0;
    public static final int COL_OverTimeLimitSelf_OT_MONTLY_02     = 1;
    public static final int COL_OverTimeLimitSelf_OT_MONTLY_03     = 2;
    public static final int COL_OverTimeLimitSelf_OT_MONTLY_04     = 3;
    public static final int COL_OverTimeLimitSelf_OT_MONTLY_05     = 4;
    public static final int COL_OverTimeLimitSelf_OT_YEARLY_01     = 5;
    public static final int COL_OverTimeLimitSelf_OT_YEARLY_02     = 6;
    public static final int COL_OverTimeLimitSelf_OT_YEARLY_03     = 7;
    public static final int COL_OverTimeLimitSelf_OT_YEARLY_04     = 8;
    public static final int COL_OverTimeLimitSelf_OT_YEARLY_05     = 9;
    public static final int COL_OverTimeLimitSelf_OT_MONTHLY_COUNT = 10;
    public static final int COL_OverTimeLimitSelf_OT_DAILY_01      = 11;
    public static final int COL_OverTimeLimitSelf_OT_MONTHLY_AVG      = 12;//??????????????????????????????

    /*
     * ========================= ??????????????????handler ==================
     */
    public Map<String,Object> defaultLevelHandler(PsDBBean bean,String empId) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setCompanyId(bean.getCompCode());
        params.setCustomerID(bean.getCustID());
        params.setLanguage(bean.getLanguage());
        params.setEmployee(empId);
        Map<String,Object> map = MapUtil.newHashMap();
        try (Connection conn = dataSource.getConnection()) {
            Vector<Vector<Object>> rs = dbControllerLogic.executeQuery(buildSQLForSelectDefaultApproval(params), conn);
            map.put("level",rs.get(1).get(0));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return map;
    }

    public Map<String,Object> dispHandler(PsDBBean psDBBean) throws Exception {
        Map<String,Object> result = MapUtil.newHashMap();
        // ???????????????????????????????????????????????????????????????????????????????????????
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        boolean haveAuthority = processParams(psDBBean,params);
        referList = new TmgReferList(psDBBean, EvaluatorSettingConst.BEAN_DESC,params.getYYYYMMDD(),
                TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
        // REQUEST:??????
        params.setSection(referList.getTargetSec());
        params.setSectionName(referList.getTargetSecName());

        // REQUEST:??????????????????
        params.setRootGroup(referList.getTargetSec() + '|' + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        // ?????????????????????????????????????????????????????????????????????????????????
        // V1.5?????? tmd#1162 ?????????????????????????????????????????????????????????????????????????????????????????????
        //	gbHaveAuthority = referList.hasAuthority(evaluaterSettingParam.getYYYYMMDD(), TmgUtil.Cs_AUTHORITY_AUTHORITY);
        String recordDate = (String)psDBBean.getRequestHash().get(TmgReferList.TREEVIEW_KEY_RECORD_DATE);
        haveAuthority = referList.hasAuthority(recordDate, TmgUtil.Cs_AUTHORITY_AUTHORITY);

        boolean btnEditMember = haveAuthority;
        boolean btnMakeGroup = haveAuthority;
        boolean btnAddEval = haveAuthority;
        result.put("btnEditMember",btnEditMember);
        result.put("btnMakeGroup",btnMakeGroup);
        result.put("btnAddEval",btnAddEval);
        result.put("YYYYMMDD",params.getYYYYMMDD());
        String adminInitMsg = null;
        // ???????????????????????????????????????????????????????????????
        if (!params.isSection()) {
            adminInitMsg = scCacheUtil.getSystemProperty("MSG_NO_SELECT_SECTION");
            result.put("adminInitMsg",adminInitMsg);
        }
        showDisp(params,psDBBean,result,haveAuthority);

        // ????????????????????????????????????
        return result;
    }

    public GlobalResponse makeGroupHandler(PsDBBean psDBBean, String targetSectionId, String targetGroupId, String lastTargetGroupId, String groupName, String empId) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        params.setSite(psDBBean.getSiteId());
        params.setLanguage(psDBBean.getLanguage());
        String txtAction = (String)psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION);
        psDBBean.getRequestHash().put("txtGroupName",groupName);
        if (StrUtil.isNotBlank(txtAction)) {
            params.setAction(txtAction);
        } else {
            params.setAction(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP);
        }
        configYYYYMMDD(psDBBean, params);
        params.setCompanyId(psDBBean.getCompCode());
        params.setCustomerID(psDBBean.getCustID());
        params.setSection(targetSectionId);
        params.setRootGroup(targetSectionId+"|"+TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);
        if (StrUtil.isNotBlank(targetGroupId)){
            params.setGroup(targetGroupId);
        } else if (StrUtil.isNotBlank(lastTargetGroupId)){
            params.setGroup(lastTargetGroupId);
        }
        params.setGroupName(groupName);
        if (StrUtil.isNotBlank(empId)){
            params.setEmployee(empId);
        } else {
            params.setEmployee(psDBBean.getUserCode());
        }
        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));  // ??????????????????????????????
        vQuery.add(buildSQLForInsertGroupCheck(params));   // ????????????????????????????????????????????????
        vQuery.add(buildSQLForInsertGroupErrMsg(params));  // ??????????????????????????????
        vQuery.add(buildSQLForInsertGroupTrigger(params)); // ??????????????????
        vQuery.add(buildSQLForSelectGroupErrMsg(params));  // ??????????????????????????????
        vQuery.add(buildSQLForDeleteGroupTrigger(params)); // ??????????????????
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));  // ??????????????????????????????
        vQuery.add(buildSQLForDeleteGroupCheck(params));   // ???????????????????????????
        Vector<Object> msgList = (Vector<Object>)ajaxBean.exeSQLs(vQuery,psDBBean).getResult().get(0);
        Vector<Object> msg = (Vector<Object>)msgList.get(0);
        if (CollUtil.isNotEmpty(msg)) {
            String errorMsg = (String)msg.get(0);
            if (StrUtil.equals(errorMsg ,"0")) {
               return GlobalResponse.ok("??????????????????");
            } else {
                return GlobalResponse.error(errorMsg);
            }
        }
        return GlobalResponse.error("???????????????????????????");
    }

    /**
     * ???????????????????????????????????????????????????????????????
     */
    public Map<String,Object> showEditGroupHandler(PsDBBean psDBBean,String sectionId,String groupId) {
        Map<String,Object> map = MapUtil.newHashMap(true);
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        params.setSite(psDBBean.getSiteId());
        params.setLanguage(psDBBean.getLanguage());
        String txtAction = (String)psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION);
        if (StrUtil.isNotBlank(txtAction)) {
            params.setAction(txtAction);
        } else {
            params.setAction(EvaluatorSettingConst.ACT_EDITGROUP_RGROUP);
        }
        params.setGroup(groupId);
        params.setSection(sectionId);
        params.setCustomerID(psDBBean.getCustID());
        params.setCompanyId(psDBBean.getCompCode());
        params.setRootGroup(sectionId+"|"+TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);
        configYYYYMMDD(psDBBean, params);
        // ??????
        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForSelectGroupName(params));            // ????????????????????????
        vQuery.add(buildSQLForSelectOverTimeLimit(params));        // ?????????????????????????????????????????????
        vQuery.add(buildSQLForSelectHolidayTimeLimit(params));     // ???????????????????????????????????????????????????
        vQuery.add(buildSQLForSelectOverTimeLimitSelf(params));    // ?????????????????????????????????
        vQuery.add(buildSQLForSelectHolidayTimeLimitSelf(params)); // ???????????????????????????????????????
        vQuery.add(buildSQLForSelectGroupAttribute(params));		  // ??????????????????????????????
        PsResult psResult;
        try {
            psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,psDBBean);
        } catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
        /**
         * ==============================
         *  Start ???????????????????????????
         * ==============================
         */
        // ????????????????????????????????????????????????????????????????????????

        boolean bEditOvertimeAnyItem = false;
        if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")
                ||psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_YEARLY_01_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_YEARLY_02_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_YEARLY_03_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_YEARLY_04_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_YEARLY_05_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_DAILY_01_MGD, 0).equals("TMG_ONOFF|1")) {
            bEditOvertimeAnyItem = true;
        }
        // ????????????????????????????????????????????????????????????????????????
        boolean bEditHolidaytimeAnyItem = false;
        if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit, COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")
                ||psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit, COL_OVERTIMELIMIT_HT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit, COL_OVERTIMELIMIT_HT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")
                || psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit, COL_OVERTIMELIMIT_HT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
            bEditHolidaytimeAnyItem = true;
        }
        // ??????????????????????????????????????????????????????????????????
        boolean bEditGroupName = false;
        if (!params.getGroup().equals(
                params.getRootGroup())) {
            bEditGroupName = true;
        }
        boolean bEditGroupAnyItem = false;
        if (bEditGroupName || bEditOvertimeAnyItem || bEditHolidaytimeAnyItem) {
            bEditGroupAnyItem = true;
        }
        map.put("enableEditGroupName",bEditGroupName);
        map.put("YYYYMMDD",params.getYYYYMMDD());
        map.put("enableEditGroupAnyItem",bEditGroupAnyItem);

        if (bEditGroupName) {
//            map.put("groupName",psDBBean.valueAtColumnRow(psResult,IDX_GROUPNAME,EvaluatorSettingConst.COL_ADD_GROUPNAME,0));
            map.put("groupName",psDBBeanUtil.valueAtColumnRow(psResult,IDX_GROUPNAME,0,0));
        }

        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        boolean sAutosetChecked = false;
        if (isDefaultGroup(params.getGroup())){
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_GroupAttribute,COL_GroupAttribute_AutoSet_Eva, 0) == null ||
                    TmgUtil.Cs_MGD_ONOFF_1.equals(psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_GroupAttribute,COL_GroupAttribute_AutoSet_Eva, 0)) ){
                sAutosetChecked = true;
            }
        }
        map.put("autoStart",sAutosetChecked);

        map.put("editOverTimeAnyItem",bEditGroupAnyItem);
        if (bEditOvertimeAnyItem) {

              // ????????????????????????(??????)(??????:?????????1)
              Map<String,Object> dailyOverTime = MapUtil.newHashMap();
              boolean showDailyOverTime = false;
              if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit, COL_OVERTIMELIMIT_OT_DAILY_01_MGD, 0).equals("TMG_ONOFF|1")) {
                 showDailyOverTime = true;
                 String dailyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_DAILY_01,0);
                 String dailyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_DAILY_01,0);
                 dailyOverTime.put("self",dailyOverTimeSelf);
                 dailyOverTime.put("basic",dailyOverTimeBasic);
              }
              dailyOverTime.put("showDailyOverTime",showDailyOverTime);
              map.put("dailyOverTime",dailyOverTime);

              // ????????????????????????(??????)(??????:??????)
              Map<String,Object> monthlyOverTimeYellow = MapUtil.newHashMap();
              boolean showMonthlyOverTimeYellow = false;
              if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
                  showMonthlyOverTimeYellow = true;
                String monthlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTLY_01,0);
                String monthlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_01,0);
                  monthlyOverTimeYellow.put("self",monthlyOverTimeSelf);
                  monthlyOverTimeYellow.put("basic",monthlyOverTimeBasic);
              }
              monthlyOverTimeYellow.put("showMonthlyOverTime",showMonthlyOverTimeYellow);
              map.put("monthlyOverTimeYellow",monthlyOverTimeYellow);

            // ????????????????????????(??????)(??????:????????????)
            Map<String,Object> monthlyOverTimeOrange = MapUtil.newHashMap();
            boolean showMonthlyOverTimeOrange = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyOverTimeOrange = true;
                String monthlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTLY_02,0);
                String monthlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_02,0);
                monthlyOverTimeOrange.put("self",monthlyOverTimeSelf);
                monthlyOverTimeOrange.put("basic",monthlyOverTimeBasic);
            }
            monthlyOverTimeOrange.put("showMonthlyOverTimeOrange",showMonthlyOverTimeOrange);
            map.put("monthlyOverTimeOrange",monthlyOverTimeOrange);

            // ????????????????????????(??????)(??????:?????????)
            Map<String,Object> monthlyOverTimePink = MapUtil.newHashMap();
            boolean showMonthlyOverTimePink = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyOverTimePink = true;
                String monthlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTLY_03,0);
                String monthlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_03,0);
                monthlyOverTimePink.put("self",monthlyOverTimeSelf);
                monthlyOverTimePink.put("basic",monthlyOverTimeBasic);
            }
            monthlyOverTimePink.put("showMonthlyOverTimePink",showMonthlyOverTimePink);
            map.put("monthlyOverTimePink",monthlyOverTimePink);

            // ????????????????????????(??????)(??????:??????)
            Map<String,Object> monthlyOverTimeRed = MapUtil.newHashMap();
            boolean showMonthlyOverTimeRed = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyOverTimeRed = true;
                String monthlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTLY_04,0);
                String monthlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_04,0);
                monthlyOverTimeRed.put("self",monthlyOverTimeSelf);
                monthlyOverTimeRed.put("basic",monthlyOverTimeBasic);
            }
            monthlyOverTimeRed.put("showMonthlyOverTimeRed",showMonthlyOverTimeRed);
            map.put("monthlyOverTimeRed",monthlyOverTimeRed);

            // ????????????????????????(??????)(??????:??????)
            Map<String,Object> monthlyOverTimeBackUp = MapUtil.newHashMap();
            boolean showMonthlyOverTimeBackUp = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyOverTimeBackUp = true;
                String monthlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTLY_05,0);
                String monthlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTLY_05,0);
                monthlyOverTimeBackUp.put("self",monthlyOverTimeSelf);
                monthlyOverTimeBackUp.put("basic",monthlyOverTimeBasic);
            }
            monthlyOverTimeBackUp.put("showMonthlyOverTimeBackUp",showMonthlyOverTimeBackUp);
            map.put("monthlyOverTimeBackUp",monthlyOverTimeBackUp);

            // ????????????????????????(??????)(??????:??????)
            Map<String,Object> yearlyOverTimeYellow = MapUtil.newHashMap();
            boolean showYearlyOverTimeYellow= false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
                showYearlyOverTimeYellow = true;
                String yearlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_YEARLY_01,0);
                String yearlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_01,0);
                yearlyOverTimeYellow.put("self",yearlyOverTimeSelf);
                yearlyOverTimeYellow.put("basic",yearlyOverTimeBasic);
            }
            yearlyOverTimeYellow.put("showYearlyOverTimeYellow",showYearlyOverTimeYellow);
            map.put("yearlyOverTimeYellow",yearlyOverTimeYellow);

            // ????????????????????????(??????)(??????:????????????)
            Map<String,Object> yearlyOverTimeOrange = MapUtil.newHashMap();
            boolean showYearlyOverTimeOrange = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
                showYearlyOverTimeOrange  = true;
                String yearlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_YEARLY_02,0);
                String yearlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_02,0);
                yearlyOverTimeOrange.put("self",yearlyOverTimeSelf);
                yearlyOverTimeOrange.put("basic",yearlyOverTimeBasic);
            }
            yearlyOverTimeOrange.put("showYearlyOverTimeOrange",showYearlyOverTimeOrange);
            map.put("yearlyOverTimeOrange",yearlyOverTimeOrange);

            // ????????????????????????(??????)(??????:?????????)
            Map<String,Object> yearlyOverTimePink = MapUtil.newHashMap();
            boolean showYearlyOverTimePink = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
                showYearlyOverTimePink  = true;
                String yearlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_YEARLY_03,0);
                String yearlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_03,0);
                yearlyOverTimePink.put("self",yearlyOverTimeSelf);
                yearlyOverTimePink.put("basic",yearlyOverTimeBasic);
            }
            yearlyOverTimePink.put("showYearlyOverTimePink",showYearlyOverTimePink);
            map.put("yearlyOverTimePink",yearlyOverTimePink);

            // ????????????????????????(??????)(??????:??????)
            Map<String,Object> yearlyOverTimeRed = MapUtil.newHashMap();
            boolean showYearlyOverTimeRed = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
                showYearlyOverTimeRed  = true;
                String yearlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_YEARLY_04,0);
                String yearlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_04,0);
                yearlyOverTimeRed.put("self",yearlyOverTimeSelf);
                yearlyOverTimeRed.put("basic",yearlyOverTimeBasic);
            }
            yearlyOverTimeRed.put("showYearlyOverTimeRed",showYearlyOverTimeRed);
            map.put("yearlyOverTimeRed",yearlyOverTimeRed);

            // ????????????????????????(??????)(??????:??????)
            Map<String,Object> yearlyOverTimeBackUp = MapUtil.newHashMap();
            boolean showYearlyOverTimeBackUp = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
                showYearlyOverTimeBackUp  = true;
                String yearlyOverTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_YEARLY_05,0);
                String yearlyOverTimeBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_YEARLY_05,0);
                yearlyOverTimeBackUp.put("self",yearlyOverTimeSelf);
                yearlyOverTimeBackUp.put("basic",yearlyOverTimeBasic);
            }
            yearlyOverTimeBackUp.put("showYearlyOverTimeBackUp",showYearlyOverTimeBackUp);
            map.put("yearlyOverTimeBackUp",yearlyOverTimeBackUp);

            // ??????????????????????????????????????????
            Map<String,Object> countOverTime = MapUtil.newHashMap();
            boolean showCountOverTime = false;

            // ??????????????????????????????
            Map<String,Object> avgOverTime = MapUtil.newHashMap();
            boolean showAvgOverTime = false;

            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTHLY_COUNT_MGD, 0).equals("TMG_ONOFF|1")) {
                showCountOverTime  = true;
                String countSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTHLY_COUNT,0);
                String countBasic =psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTHLY_COUNT,0);
                countOverTime.put("self",countSelf);
                countOverTime.put("basic",countBasic);

                showAvgOverTime = true;
                String avgSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimitSelf,COL_OverTimeLimitSelf_OT_MONTHLY_AVG,0);
                String avgBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_OverTimeLimit,COL_OVERTIMELIMIT_OT_MONTHLY_AVG,0);
                avgOverTime.put("self",avgSelf);
                avgOverTime.put("basic",avgBasic);
            }
            countOverTime.put("showCountOverTime",showCountOverTime);
            map.put("countOverTime", countOverTime);
            avgOverTime.put("showAvgOverTime", showAvgOverTime);
            map.put("avgOverTime", avgOverTime);

        }

        // ====== ??????????????????  ????????????????????????????????????????????????????????????????????????  ======
        map.put("editHolidayTimeAnyItem",bEditHolidaytimeAnyItem);
        if (bEditHolidaytimeAnyItem) {

            // ?????????(??????)(??????:?????????1)
            Map<String,Object> monthlyHolidayTimeLevel1 = MapUtil.newHashMap();
            boolean showMonthlyHolidayTimeLevel1 = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_01_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyHolidayTimeLevel1 = true;
                String holidayTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf,COL_HolidayTimeLimitSelf_HT_MONTLY_01,0);
                String holidayBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_01,0);
                monthlyHolidayTimeLevel1.put("self",holidayTimeSelf);
                monthlyHolidayTimeLevel1.put("basic",holidayBasic);
            }
            monthlyHolidayTimeLevel1.put("showMonthlyHolidayTimeLevel1",showMonthlyHolidayTimeLevel1 );
            map.put("monthlyHolidayTimeLevel1",monthlyHolidayTimeLevel1 );

            // ?????????(??????)(??????:?????????2)
            Map<String,Object> monthlyHolidayTimeLevel2= MapUtil.newHashMap();
            boolean showMonthlyHolidayTimeLevel2 = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_02_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyHolidayTimeLevel2 = true;
                String holidayTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf,COL_HolidayTimeLimitSelf_HT_MONTLY_02,0);
                String holidayBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_02,0);
                monthlyHolidayTimeLevel2.put("self",holidayTimeSelf);
                monthlyHolidayTimeLevel2.put("basic",holidayBasic);
            }
            monthlyHolidayTimeLevel2.put("showMonthlyHolidayTimeLevel2",showMonthlyHolidayTimeLevel2);
            map.put("monthlyHolidayTimeLevel2",monthlyHolidayTimeLevel2);

            // ?????????(??????)(??????:?????????3)
            Map<String,Object> monthlyHolidayTimeLevel3 = MapUtil.newHashMap();
            boolean showMonthlyHolidayTimeLevel3 = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_03_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyHolidayTimeLevel3 = true;
                String holidayTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf,COL_HolidayTimeLimitSelf_HT_MONTLY_03,0);
                String holidayBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_03,0);
                monthlyHolidayTimeLevel3.put("self",holidayTimeSelf);
                monthlyHolidayTimeLevel3.put("basic",holidayBasic);
            }
            monthlyHolidayTimeLevel3.put("showMonthlyHolidayTimeLevel3",showMonthlyHolidayTimeLevel3);
            map.put("monthlyHolidayTimeLevel3",monthlyHolidayTimeLevel3);

            // ?????????(??????)(??????:?????????4)
            Map<String,Object> monthlyHolidayTimeLevel4= MapUtil.newHashMap();
            boolean showMonthlyHolidayTimeLevel4 = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_04_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyHolidayTimeLevel4 = true;
                String holidayTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf,COL_HolidayTimeLimitSelf_HT_MONTLY_04,0);
                String holidayBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_04,0);
                monthlyHolidayTimeLevel4.put("self",holidayTimeSelf);
                monthlyHolidayTimeLevel4.put("basic",holidayBasic);
            }
            monthlyHolidayTimeLevel4.put("showMonthlyHolidayTimeLevel4",showMonthlyHolidayTimeLevel4);
            map.put("monthlyHolidayTimeLevel4",monthlyHolidayTimeLevel4);

            // ?????????(??????)(??????:?????????5)
            Map<String,Object> monthlyHolidayTimeLevel5 = MapUtil.newHashMap();
            boolean showMonthlyHolidayTimeLevel5 = false;
            if (psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_05_MGD, 0).equals("TMG_ONOFF|1")) {
                showMonthlyHolidayTimeLevel5 = true;
                String holidayTimeSelf = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimitSelf,COL_HolidayTimeLimitSelf_HT_MONTLY_05,0);
                String holidayBasic = psDBBeanUtil.valueAtColumnRow(psResult,QUERY_SHOWEDITGROUP_HolidayTimeLimit,COL_OVERTIMELIMIT_HT_MONTLY_05,0);
                monthlyHolidayTimeLevel5.put("self",holidayTimeSelf);
                monthlyHolidayTimeLevel5.put("basic",holidayBasic);
            }
            monthlyHolidayTimeLevel5.put("showMonthlyHolidayTimeLevel5",showMonthlyHolidayTimeLevel5);
            map.put("monthlyHolidayTimeLevel5",monthlyHolidayTimeLevel5);

        }

        if (!bEditGroupAnyItem) {
            map.put("MSG_NO_SELECT_SECTION",SysUtil.getpropertyvalue("ja","MSG_NO_SELECT_SECTION",TmgUtil.class.getName()));
        }
        return map;
    }

    public GlobalResponse deleteGroupHandler(PsDBBean bean,String sectionId,String groupId) {
        if (StrUtil.endWith(groupId,TmgUtil.Cs_DEFAULT_GROUPSEQUENCE)) {
            throw new GlobalException("??????????????????????????????????????????????????????");
        }
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setSection(sectionId);
        params.setGroup(groupId);
        params.setCustomerID(bean.getCustID());
        params.setCompanyId(bean.getCompCode());
        params.setRootGroup(sectionId + '|' + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForDeleteGroup(params));        // ?????????????????????????????????????????????????????????
        vQuery.add(buildSQLForDeleteEvaluater(params));    // ??????????????????????????????????????????????????????????????????
        vQuery.add(buildSQLForUpdateMovingEval(params));   // ??????????????????????????????????????????????????????????????????????????????????????????
        vQuery.add(buildSQLForDeletePattern(params));      // ???????????????????????????????????????????????????????????????
        vQuery.add(buildSQLForDeletePatternRest(params));  // ????????????????????????????????????????????????(??????)???????????????
        vQuery.add(buildSQLForDeletePatternApply(params)); // ?????????????????????????????????????????????????????????????????????
        int rows = psDBBeanUtil.setInsertValues(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
        if (rows == 0) {
            return GlobalResponse.ok("??????????????????");
        }
        return GlobalResponse.error("???????????????????????????");
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    public GlobalResponse editGroupNameProcHandler(PsDBBean psDBBean, EditGroupDTO dto) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        params.setEmployee(psDBBean.getUserCode());
        params.setAction(EvaluatorSettingConst.ACT_EDITGROUP_UGROUP);
        params.setCustomerID(psDBBean.getCustID());
        params.setCompanyId(psDBBean.getCompCode());
        params.setLanguage(psDBBean.getLanguage());
        configYYYYMMDD(psDBBean,params);

        Vector<String> vQuery = new Vector<>();
        // ??????????????????????????????
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));
        // ????????????????????????????????????????????????
        vQuery.add(buildSQLForInsertGroupAttributeCheck(params,dto));
        // ??????????????????????????????
        vQuery.add(buildSQLForInsertGroupAttributeErrMsg(params));
        // ??????????????????
        vQuery.add(buildSQLForInsertGroupTrigger(params));
        // ??????????????????????????????
        vQuery.add(buildSQLForSelectGroupErrMsg(params));
        // ??????????????????
        vQuery.add(buildSQLForDeleteGroupTrigger(params));
        // ??????????????????????????????
        vQuery.add(buildSQLForDeleteGroupErrMsg(params));
        // ???????????????????????????
        vQuery.add(buildSQLForDeleteGroupAttributeCheck(params));
        // DB??????
        int rows = psDBBeanUtil.setInsertValues(vQuery, EvaluatorSettingConst.BEAN_DESC,psDBBean);
        if (rows >0) {
            return GlobalResponse.ok("??????????????????");
        }
        return GlobalResponse.error("???????????????????????????");
    }

    // ?????????????????????
    private final int IDX_GROUP_LIST		= 0;	// ?????????????????????
    private final int IDX_APPROVAL      = 1;    // ?????????????????????

    public Map<String,Object> showAddEvalHandler(PsDBBean bean,String sectionId) {
        Map<String,Object> map = MapUtil.newHashMap();
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setSection(sectionId);
        params.setCompanyId(bean.getCompCode());
        params.setCustomerID(bean.getCustID());
        params.setLanguage(bean.getLanguage());

        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForSelectGroupList(params));         // ????????????????????????
        vQuery.add(buildSQLForSelectApprovalLevelList(params)); // ?????????????????????
        PsResult psResult;

        int groupListCount;
        int levelCount;
        try {
           referList = new TmgReferList(bean, EvaluatorSettingConst.BEAN_DESC,params.getYYYYMMDD(),
                    TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
           psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
           groupListCount = psDBBeanUtil.getCount(psResult,IDX_GROUP_LIST);
           levelCount = psDBBeanUtil.getCount(psResult,IDX_APPROVAL);
        } catch (Exception e) {
           throw new GlobalException(e.getMessage());
        }

        boolean hasAuthMonthApprove = hasAuthority(TmgUtil.Cs_AUTHORITY_MONTHLYAPPROVAL,params);
        map.put("enableMonthlyResult", hasAuthMonthApprove);

        List<Map<String,String>> groupList = CollUtil.newArrayList();
        for (int i = 0; i < groupListCount; i++) {
            String sCode  = psDBBeanUtil.valueAtColumnRow(psResult,IDX_GROUP_LIST, 0, i);
            String sName = psDBBeanUtil.valueAtColumnRow(psResult,IDX_GROUP_LIST, 1, i);
            Map<String,String> item = MapUtil.<String,String>builder().put(sCode,sName).build();
            groupList.add(item);
        }

        List<Map<String,String>> levelList = CollUtil.newArrayList();
        for (int i = 0; i < levelCount; i++) {
            String sCode  = psDBBeanUtil.valueAtColumnRow(psResult,IDX_APPROVAL, 0, i);
            String sName = psDBBeanUtil.valueAtColumnRow(psResult,IDX_APPROVAL, 1, i);
            Map<String,String> item = MapUtil.<String,String>builder().put(sCode,sName).build();
            levelList.add(item);
        }

        map.put("groupList",groupList);
        map.put("levelList",levelList);

        return map;
    }

    public static final String TMG_EVASET_VIEWALL = "TMG_EVASET_VIEWALL";

    public Map<String,Object> showSearchEmpHandler(PsDBBean bean,String sectionId) {
        Map<String,Object> map = MapUtil.newHashMap();
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setSection(sectionId);
        params.setSite(bean.getSiteId());
        params.setAction(EvaluatorSettingConst.ACT_SEARCHEMP_REMPLIST);
        // ??????????????????????????? TMG_EVASET_VIEWALL ??? ON ??????????????????????????????????????????????????????????????????????????????
        boolean isViewAll = "ON".equalsIgnoreCase(scCacheUtil.getSystemProperty(TMG_EVASET_VIEWALL)) || params.isSiteTa();
        TmgSearchEmpList searchEmpList =
                new TmgSearchEmpList(bean,                                // bean??????????????????
                        "TmgSample",                          // bean??????
                        params.getSection(),   // ?????????????????????
                        params.getYYYYMMDD(),  // ?????????
                        isViewAll,                            // ?????????????????????????????????????????????
                        true);
        map.put("empList", JSONUtil.parseArray(searchEmpList.getJSONArrayForEmpList()));
        if (isViewAll) {
            map.put("orgList",JSONUtil.parseArray(searchEmpList.getJSONArrayForOrgTree()));
        }
        return map;
    }

    public GlobalResponse addEvalHandler(PsDBBean bean, AddEvaluatorDTO dto) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        params.setSite(bean.getSiteId());
        params.setLanguage(bean.getLanguage());
        params.setCompanyId(bean.getCompCode());
        params.setCustomerID(bean.getCustID());
        params.setSection(dto.getSectionId());
        String action = (String)bean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION);
        if (StrUtil.isNotBlank(action)) {
            params.setAction(action);
        } else {
            params.setAction(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP);
        }
        params.setRootGroup(params.getSection() + '|' + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);
        configYYYYMMDD(bean,params);
        params.setGroup(dto.getGroupId());
        if (StrUtil.isBlank(dto.getEmpId())) {
            params.setEmployee(bean.getUserCode());
        } else {
            params.setEmployee(dto.getEmpId());
        }

        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForDeleteEvalTrigger(params,bean)); // ??????????????????
        vQuery.add(buildSQLForDeleteEvalErrMsg(params,bean));  // ??????????????????????????????
        vQuery.add(buildSQLForDeleteEvalCheck(params,bean));   // ???????????????????????????
        vQuery.add(buildSQLForInsertEvalCheck(params,dto,bean));   // ??????????????????????????????????????????
        vQuery.add(buildSQLForInsertEvalErrMsg(params,bean));  // ??????????????????????????????
        vQuery.add(buildSQLForInsertEvalTrigger(params,bean)); // ??????????????????
        vQuery.add(buildSQLForSelectEvalErrMsg(params,bean));  // ??????????????????????????????
        vQuery.add(buildSQLForDeleteEvalTrigger(params,bean)); // ??????????????????
        vQuery.add(buildSQLForDeleteEvalErrMsg(params,bean));  // ??????????????????????????????
        vQuery.add(buildSQLForDeleteEvalCheck(params,bean));   // ???????????????????????????

        Vector<Object> msgList = (Vector<Object>)ajaxBean.exeSQLs(vQuery,bean).getResult().get(0);
        Vector<Object> msg = (Vector<Object>)msgList.get(0);
        if (CollUtil.isNotEmpty(msg)) {
            String errorMsg = (String)msg.get(0);
            if (StrUtil.equals(errorMsg ,"0")) {
                return GlobalResponse.ok("??????????????????");
            } else {
                return GlobalResponse.error(errorMsg);
            }
        }
        return GlobalResponse.error("???????????????????????????");
    }

    /**
     * ????????????????????????????????????????????????????????????
     */
    public Map<String,Object> showEditMemberHandler(PsDBBean bean,String sectionId) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setAction(EvaluatorSettingConst.ACT_EDITMEMBER_RMEMBER);
        params.setCustomerID(bean.getCustID());
        params.setSection(sectionId);
        params.setCompanyId(bean.getCompCode());
        Map<String,Object> map = MapUtil.newHashMap();
        // ??????
        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForSelectMember(params));    // ????????????????????????
        vQuery.add(buildSQLForSelectGroupList(params)); // ??????????????????????????????????????????????????????
        PsResult psResult;
        // ?????????????????????
        int IDX_MEMBER		= 0;	// ??????????????????
        int IDX_GROUPNAME	= 1;	// ??????????????????
        map.put("YYYYMMDD",params.getYYYYMMDD());
        try {
            psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
            List<EditMemberVO> memberList = CollUtil.newArrayList();
            for (int i = 0;i< psDBBeanUtil.getCount(psResult,IDX_MEMBER);i++) {
                EditMemberVO vo = new EditMemberVO();
                vo.setEmpId(psDBBeanUtil.valueAtColumnRow(psResult,IDX_MEMBER,EvaluatorSettingConst.COL_MEMBER_EMPLOYEEID,i));
                vo.setEmpName(psDBBeanUtil.valueAtColumnRow(psResult,IDX_MEMBER,EvaluatorSettingConst.COL_MEMBER_EMPLOYEENAME,i));
                vo.setGroupId(psDBBeanUtil.valueAtColumnRow(psResult,IDX_MEMBER,EvaluatorSettingConst.COL_MEMBER_GROUPID,i));
                vo.setGroupName(psDBBeanUtil.valueAtColumnRow(psResult,IDX_MEMBER,EvaluatorSettingConst.COL_MEMBER_GROUPNAME,i));
                List<Map<String,String>> groupList = CollUtil.newArrayList();
                for (int j = 0; j <psDBBeanUtil.getCount(psResult,IDX_GROUPNAME);j++) {
                    Map<String,String> item = MapUtil.<String,String>builder()
                           .put(
                                   psDBBeanUtil.valueAtColumnRow(psResult,IDX_GROUPNAME,0,j),
                                   psDBBeanUtil.valueAtColumnRow(psResult,IDX_GROUPNAME,1,j)
                           ).build();
                   groupList.add(item);
                }
                vo.setGroupList(groupList);
                memberList.add(vo);
            }
            map.put("memberList",memberList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
        return map;
    }

    public GlobalResponse editMemberHandler(PsDBBean bean, List<EditMemberDTO> dtoList) {
        // ??????
        Vector<String> vQuery = new Vector<>();

        for (EditMemberDTO dto:dtoList) {
            if (StrUtil.equals(dto.getGroupId(),dto.getOriginalGroupId())) {
                continue;
            }
            EvaluatorSettingParam params = new EvaluatorSettingParam();
            configYYYYMMDD(bean, params);
            params.setSection(dto.getSectionId());
            params.setCompanyId(bean.getCompCode());
            params.setCustomerID(bean.getCustID());
            params.setAction(EvaluatorSettingConst.ACT_EDITMEMBER_UMEMBER);

            params.setGroup(dto.getGroupId());
            params.setEmployee(dto.getEmpId());
            // ??????????????????
            // ????????????????????????
            vQuery.add(buildSQLForInsertMemberBackward(params, bean));
            // ?????????????????????????????????
            vQuery.add(buildSQLForDeleteMember(params));
            // ?????????????????????????????????(?????????)
            vQuery.add(buildSQLForUpdateMemberWithStartDate(params, bean));
            // ?????????????????????????????????(?????????)
            vQuery.add(buildSQLForUpdateMemberWithEndDate(params, bean));
            // ????????????????????????
            vQuery.add(buildSQLForInsertMemberForward(params, bean));
            vQuery.add(buildSQLForDeleteMemberBackward(params, bean));

        }

        int rows = psDBBeanUtil.setInsertValues(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
        if (rows >0) {
            return GlobalResponse.ok("??????????????????");
        }
        return GlobalResponse.error("???????????????????????????");
    }

    public Map<String,Object> showEditEvalHandler(PsDBBean bean,String sectionId,String groupId,String empId) {
        EvaluatorSettingParam params = new EvaluatorSettingParam();
        configYYYYMMDD(bean,params);
        params.setCompanyId(bean.getCompCode());
        params.setCustomerID(bean.getCustID());
        params.setLanguage(bean.getLanguage());
        params.setGroup(groupId);
        params.setAction(EvaluatorSettingConst.ACT_EDITEVAL_REVAL);

        params.setSection(sectionId);
        params.setEmployee(empId);

        Map<String,Object> map = MapUtil.newHashMap();
        // ??????
        Vector<String> vQuery = new Vector<>();
        vQuery.add(buildSQLForSelectEvaluator(params));         // 0 ?????????????????????
        vQuery.add(buildSQLForSelectApprovalLevelList(params)); // 1 ?????????????????????
        vQuery.add(buildSQLForSelectDefaultApproval(params));   // 2 ????????????????????????????????????
        vQuery.add(buildSQLForSelectDesigTerm(params));         // 3 ????????????????????????

        int IDX_EVALUATOR = 0;	// ???????????????
        int IDX_APPROVAL = 1;    // ?????????????????????
        int IDX_DEFAULT = 2;    // ??????????????????????????????

        PsResult psResult;
        int evaluatorCount;
        int levelCount;
        try {
            referList = new TmgReferList(bean, EvaluatorSettingConst.BEAN_DESC,params.getYYYYMMDD(),
                    TmgReferList.TREEVIEW_TYPE_LIST_SEC, true);
            psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
            evaluatorCount = psDBBeanUtil.getCount(psResult,IDX_EVALUATOR);
            levelCount = psDBBeanUtil.getCount(psResult,IDX_APPROVAL);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }

        // REQUEST:??????????????????
        params.setRootGroup(referList.getTargetSec() + '|' + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        boolean hasAuthMonthApprove = hasAuthority(TmgUtil.Cs_AUTHORITY_MONTHLYAPPROVAL,params);
        map.put("enableMonthlyResult", hasAuthMonthApprove);

        map.put("groupName",psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR,EvaluatorSettingConst.COL_EVAL_GROUPNAME,0));
        map.put("empName",psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR,EvaluatorSettingConst.COL_EVAL_EMPLOYEENAME,0));
        map.put("sectionName",psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR,EvaluatorSettingConst.COL_EVAL_SECTIONNAME,0));
        map.put("postName",psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR,EvaluatorSettingConst.COL_EVAL_POSTNAME,0));

        //???????????????????????????????????????????????????????????????????????????????????????disable?????????
        boolean enableEditAuthority = StrUtil.equals(params.getRootGroup(),groupId);
        map.put("enableEditAuthority",enableEditAuthority);

        map.put("defaultLevel",psDBBeanUtil.valueAtColumnRow(psResult,IDX_DEFAULT, EvaluatorSettingConst.COL_MGD_DEFAULT_APPLEVEL, 0));

        List<Map<String,Object>> authInfoList = CollUtil.newArrayList();
        for (int i = 0; i < evaluatorCount; i++) {
            Map<String,Object> authInfo = MapUtil.newHashMap();
            // ????????????
            boolean dailyResult = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_RESULTS, 0));
            // ????????????
            boolean monthlyDailyResult = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_MONTHLYRESULTS, 0));
            // ?????????????????????
            boolean notificationResult = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_NOTIFICATION, 0));
            // ??????????????????
            boolean overTime = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_OVERTIME, 0));
            // ????????????
            boolean schedule = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_SCHEDULE, 0));
            // ????????????
            boolean authority = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult, IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_AUTHORITY, 0));

            //????????????
            String level =psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR,EvaluatorSettingConst.COL_EVAL_APPROVALLEVEL,i);

            String startDate = psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_TERM_FROM, i);
            String endDate = psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALUATOR, EvaluatorSettingConst.COL_EVAL_TERM_TO, i);

            authInfo.put("dailyResult",dailyResult);
            authInfo.put("monthlyDailyResult",monthlyDailyResult);
            authInfo.put("notificationResult",notificationResult);
            authInfo.put("overTime",overTime );
            authInfo.put("schedule",schedule);
            authInfo.put("authority",authority);
            authInfo.put("startDate",startDate);
            authInfo.put("endDate",endDate);
            authInfo.put("level",level);

            authInfoList.add(authInfo);
        }

        List<Map<String,String>> levelList = CollUtil.newArrayList();
        for (int i = 0; i < levelCount; i++) {
            String sCode  = psDBBeanUtil.valueAtColumnRow(psResult,IDX_APPROVAL, 0, i);
            String sName = psDBBeanUtil.valueAtColumnRow(psResult,IDX_APPROVAL, 1, i);
            Map<String,String> item = MapUtil.<String,String>builder().put(sCode,sName).build();
            levelList.add(item);
        }
        map.put("levelList",levelList);
        map.put("authInfoList",authInfoList);

        return map;
    }

    public GlobalResponse editEvalHandler(PsDBBean bean, EditAuthorityDTO authorityDTO) {
        Vector<String> vQuery = new Vector<>();
        for (EditAuthorityItemDTO dto: authorityDTO.getList()) {
            EvaluatorSettingParam params = new EvaluatorSettingParam();
            params.setCompanyId(bean.getCompCode());
            params.setCustomerID(bean.getCustID());
            params.setAction(EvaluatorSettingConst.ACT_EDITEVAL_UEVAL);
            params.setGroup(authorityDTO.getGroupId());
            params.setSection(authorityDTO.getSectionId());
            params.setEmployee(authorityDTO.getEmpId());
            // ????????????????????????????????????????????????????????????????????????????????????????????????
            if (dto.getDelete()) {
                vQuery.add(buildSQLForDeleteEval(params, dto));      // ??????????????????(DEL)
                // ???????????????????????????????????????????????????????????????????????????????????????????????????
            } else if (dto.getNewLine()) {
                // ??????????????????????????????INSERT?????????????????????
                vQuery.add(buildSQLForInsertAuthority(params,dto,bean));
            } else {
                vQuery.add(buildSQLForUpdateAuthority(params, dto, bean)); // ??????????????????(UPD)
            }
        }
        int rows = psDBBeanUtil.setInsertValues(vQuery, EvaluatorSettingConst.BEAN_DESC,bean);
        if (rows >0) {
            return GlobalResponse.ok("??????????????????");
        }
        return GlobalResponse.error("???????????????????????????");
    }


    /*
     * ========================= ??????????????????Start ==================
     */

    /**
     * ???????????????????????????SQL?????????
     *
     * @param evaluaterSettingParam ???????????????????????????
     */
    private String buildSQLForInsertAuthority(EvaluatorSettingParam evaluaterSettingParam,EditAuthorityItemDTO dto,PsDBBean bean) {
        String sResults        = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sNotification   = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sOvertime       = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sSchedule       = TmgUtil.Cs_MGD_ONOFF_0; // ????????????????????? #427
        String sAuthority      = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sApprovalLevel  = null;                   // ???????????????
        String sAdminFlg       = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????0

        if (dto.getDailyResult()) {
            sResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getNotification()) {
            sNotification = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getOverTime()) {
            sOvertime = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getAuthority()) {
            sAuthority = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getSchedule()) {
            sSchedule = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getMonthlyResult()) {
            sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1 ????????????????????????"null"????????????????????????????????????"null"?????????????????????????????????
        if (StrUtil.isNotBlank(dto.getApprovalLevel())) {
            sApprovalLevel = escDBString(dto.getApprovalLevel());
        }

        // ?????????????????????1
        if (evaluaterSettingParam.isSiteTa()) {
            sAdminFlg = TmgUtil.Cs_MGD_ONOFF_1;
        }

        StringBuilder sbSQL = new StringBuilder();

        sbSQL.append(" INSERT INTO ");
        sbSQL.append("     TMG_EVALUATER E ");
        sbSQL.append(" ( ");
        sbSQL.append("     TEV_CCUSTOMERID, ");
        sbSQL.append("     TEV_CCOMPANYID,  ");
        sbSQL.append("     TEV_CEMPLOYEEID, ");
        sbSQL.append("     TEV_DSTARTDATE,  ");
        sbSQL.append("     TEV_DENDDATE,    ");
        sbSQL.append("     TEV_CMODIFIERUSERID,    ");
        sbSQL.append("     TEV_DMODIFIEDDATE,      ");
        sbSQL.append("     TEV_CMODIFIERPROGRAMID, ");
        sbSQL.append("     TEV_CSECTIONID,     ");
        sbSQL.append("     TEV_CGROUPID,       ");
        sbSQL.append("     TEV_CEDITABLEFLG,   ");
        sbSQL.append("     TEV_CRESULTS,       ");
        sbSQL.append("     TEV_CNOTIFICATION,  ");
        sbSQL.append("     TEV_COVERTIME,      ");
        sbSQL.append("     TEV_CSCHEDULE,      ");
        sbSQL.append("     TEV_CAUTHORITY,     ");
        sbSQL.append("     TEV_CADMINFLG,      ");
        sbSQL.append("     TEV_CSECTIONEVALUATER, ");
        sbSQL.append("     TEV_CMONTHLYAPPROVAL,  ");
        sbSQL.append("     TEV_CAPPROVAL_LEVEL ");
        sbSQL.append(" ) VALUES ( ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getCustomerId()) + ", ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getCompanyId())  + ", ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getEmployee())    + ", ");
        sbSQL.append(      SysUtil.transDateNullToDB(dto.getStartDate())      + ", ");
        sbSQL.append(      SysUtil.transDateNullToDB(dto.getEndDate())        + ", ");
        sbSQL.append(      escDBString(bean.getUserCode()) + ", ");
        sbSQL.append("     SYSDATE, ");
        sbSQL.append(      escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_EDITEVAL_UEVAL) + ", ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getSection()) + ", ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getGroup())   + ", ");
        sbSQL.append(      escDBString(TmgUtil.Cs_MGD_ONOFF_1)             + ",");
        sbSQL.append(      escDBString(sResults)         + ", ");
        sbSQL.append(      escDBString(sNotification)    + ", ");
        sbSQL.append(      escDBString(sOvertime)        + ", ");
        sbSQL.append(      escDBString(sSchedule)        + ", ");
        sbSQL.append(      escDBString(sAuthority)       + ", ");
        sbSQL.append(      escDBString(sAdminFlg)        + ", ");
        sbSQL.append(      escDBString(TmgUtil.Cs_MGD_ONOFF_0) + ",");
        sbSQL.append(      escDBString(sMonthlyResults)  + ", ");
        sbSQL.append(      sApprovalLevel);
        sbSQL.append(" ) ");

        return sbSQL.toString();
    }


    /**
     * ???????????????????????????SQL?????????
     *
     * @param evaluaterSettingParam ???????????????????????????
     */
    private String buildSQLForUpdateAuthority(EvaluatorSettingParam evaluaterSettingParam, EditAuthorityItemDTO dto,PsDBBean bean) {

        String sResults        = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sNotification   = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sOvertime       = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sSchedule       = TmgUtil.Cs_MGD_ONOFF_0; // ????????????????????? #427
        String sAuthority      = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sApprovalLevel  = null;                   // ???????????????

        if (dto.getDailyResult()) {
            sResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getNotification()) {
            sNotification = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getOverTime()) {
            sOvertime = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getAuthority()) {
            sAuthority = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getSchedule()) {
            sSchedule = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getMonthlyResult()) {
            sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1 ????????????????????????"null"????????????????????????????????????"null"?????????????????????????????????
        if (StrUtil.isNotBlank(dto.getApprovalLevel())) {
            sApprovalLevel = escDBString(dto.getApprovalLevel());
        }

        return " UPDATE " +
                "     TMG_EVALUATER E " +
                " SET " +
                "     E.TEV_CMODIFIERUSERID    = " + escDBString(bean.getUserCode()) + ", " +
                "     E.TEV_DMODIFIEDDATE      = SYSDATE, " +
                "     E.TEV_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_EDITEVAL_UEVAL) + ", " +
                "     E.TEV_DSTARTDATE         = " + SysUtil.transDateNullToDB(dto.getStartDate()) + ", " +
                "     E.TEV_DENDDATE           = " + SysUtil.transDateNullToDB(dto.getEndDate()) + ", " +
                "     E.TEV_CRESULTS           = " + escDBString(sResults) + ", " +
                "     E.TEV_CNOTIFICATION      = " + escDBString(sNotification) + ", " +
                "     E.TEV_COVERTIME          = " + escDBString(sOvertime) + ", " +
                "     E.TEV_CSCHEDULE          = " + escDBString(sSchedule) + ", " +
                "     E.TEV_CMONTHLYAPPROVAL   = " + escDBString(sMonthlyResults) + ", " +
                "     E.TEV_CAUTHORITY         = " + escDBString(sAuthority) + ", " +
                "     E.TEV_CAPPROVAL_LEVEL    = " + sApprovalLevel +
                " WHERE " +
                "     E.TEV_CEMPLOYEEID = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND E.TEV_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection()) +
                " AND E.TEV_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup()) +
                " AND E.TEV_DSTARTDATE  = " + SysUtil.transDateNullToDB(dto.getBeforeStartDate()) +
                " AND E.TEV_DENDDATE    = " + SysUtil.transDateNullToDB(dto.getBeforeEndDate()) +
                " AND E.TEV_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND E.TEV_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId());
    }


    /**
     * ????????????????????????SQL?????????
     * @param evaluaterSettingParam ???????????????????????????
     */
    private String buildSQLForDeleteEval(EvaluatorSettingParam evaluaterSettingParam, EditAuthorityItemDTO dto) {
        return " DELETE FROM " +
                "     TMG_EVALUATER E " +
                " WHERE " +
                "     E.TEV_CEMPLOYEEID = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND E.TEV_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection()) +
                " AND E.TEV_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup()) +
                " AND E.TEV_DSTARTDATE  = " + SysUtil.transDateNullToDB(dto.getStartDate()) +
                " AND E.TEV_DENDDATE    = " + SysUtil.transDateNullToDB(dto.getEndDate()) +
                " AND E.TEV_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND E.TEV_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId());
    }

    /**
     * ???????????????????????????????????????SQL????????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectDesigTerm(EvaluatorSettingParam evaluaterSettingParam) {

        String sCustId = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId = escDBString(evaluaterSettingParam.getCompanyId());
        String sEmpId  = escDBString(evaluaterSettingParam.getEmployee());
        String sSecId  = escDBString(evaluaterSettingParam.getSection());

        return " SELECT " +
                "     TO_CHAR(HD_DSTARTDATE_CK, 'YYYY/MM/DD') as HD_DSTARTDATE_CK, " + // 0 ???????????????
                "     TO_CHAR(HD_DENDDATE,      'YYYY/MM/DD') as HD_DENDDATE " +       // 1 ???????????????
                " FROM " +
                "     HIST_DESIGNATION " +
                " WHERE " +
                "     HD_CCUSTOMERID_CK = " + sCustId +
                " AND HD_CCOMPANYID_CK  = " + sCompId +
                " AND HD_CEMPLOYEEID_CK = " + sEmpId +
                " AND HD_CSECTIONID_FK  = " + sSecId +
                " ORDER BY " +
                "     HD_DSTARTDATE_CK ";
    }


    /**
     * ?????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForSelectDefaultApproval(EvaluatorSettingParam evaluaterSettingParam){

        String sBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String sCustId   = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId   = escDBString(evaluaterSettingParam.getCompanyId());
        String sEmpId    = escDBString(evaluaterSettingParam.getEmployee());
        String sLangage  = escDBString(evaluaterSettingParam.getLanguage());

        return  "SELECT TMG_F_GET_DEFAULT_APPLEVEL( " +
                "                               " + sEmpId + ", " +
                "                               " + sBaseDate + ", " +
                "                               " + sCustId + ", " +
                "                               " + sCompId + ", " +
                "                               " + sLangage +
                " ) " +
                "FROM DUAL";
    }

    /**
     * ??????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectEvaluator(EvaluatorSettingParam evaluaterSettingParam) {

        // ???????????????????????????????????????????????????
        String sDBCustId   = escDBString(evaluaterSettingParam.getCustomerId()); // ???????????????
        String sDBCompId   = escDBString(evaluaterSettingParam.getCompanyId());  // ???????????????
        String sDBGroupId  = escDBString(evaluaterSettingParam.getGroup());      // ?????????????????????
        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());      // ?????????
        String sDBLang     = escDBString(evaluaterSettingParam.getLanguage());   // ????????????

        return " SELECT " +
                "     TMG_F_GET_ME_NAME(" + escDBString(evaluaterSettingParam.getEmployee()) + ", " + sDBBaseDate + ", 0 ) as TEV_CEMPLOYEEID, " +
                "     TMG_F_GET_MO(D.HD_CSECTIONID_FK, " + sDBBaseDate + ", 1, D.HD_CCUSTOMERID_CK,D.HD_CCOMPANYID_CK, " + sDBLang + ") as HD_CSECTIONID_FK, " +
                "     TMG_F_GET_MP(D.HD_CPOSTID_FK, " + sDBBaseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + sDBLang + ") as HD_CPOSTID_FK, " +
                "     E.TEV_CRESULTS, " +
                "     E.TEV_CNOTIFICATION, " +
                "     E.TEV_COVERTIME, " +
                "     E.TEV_CSCHEDULE, " +
                "     E.TEV_CAUTHORITY, " +
                "     (SELECT G.TGR_CGROUPNAME FROM TMG_GROUP G " +
                "      WHERE  G.TGR_CGROUPID  = " + sDBGroupId +
                "      AND G.TGR_DSTARTDATE  <= " + sDBBaseDate +
                "      AND G.TGR_CCOMPANYID   = " + sDBCompId +
                "      AND G.TGR_CCUSTOMERID  = " + sDBCustId +
                "      AND G.TGR_DENDDATE    >= " + sDBBaseDate + ") as TGR_CGROUPNAME, " +
                "     E.TEV_CMONTHLYAPPROVAL, " +
                "     E.TEV_CAPPROVAL_LEVEL, " +
                "     TO_CHAR(E.TEV_DSTARTDATE, 'yyyy/mm/dd') AS TEV_DSTARTDATE, " +
                "     TO_CHAR(E.TEV_DENDDATE,   'yyyy/mm/dd') AS TEV_DENDDATE " +
                " FROM " +
                "     TMG_EVALUATER E, HIST_DESIGNATION D " +
                " WHERE " +
                "     E.TEV_CEMPLOYEEID      = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND E.TEV_CSECTIONID       = " + escDBString(evaluaterSettingParam.getSection()) +
                " AND E.TEV_CGROUPID         = " + sDBGroupId +
                " AND E.TEV_CCOMPANYID       = " + sDBCompId +
                " AND E.TEV_CCUSTOMERID      = " + sDBCustId +
                " AND D.HD_CCUSTOMERID_CK(+) = E.TEV_CCUSTOMERID " +
                " AND D.HD_CCOMPANYID_CK(+)  = E.TEV_CCOMPANYID " +
                " AND D.HD_CEMPLOYEEID_CK(+) = E.TEV_CEMPLOYEEID " +
                " AND D.HD_DSTARTDATE_CK(+) <= " + sDBBaseDate +
                " AND D.HD_DENDDATE(+)      >= " + sDBBaseDate +
                " AND D.HD_CIFKEYORADDITIONALROLE(+) = '0' " +
                " ORDER BY  " +
                "     E.TEV_DSTARTDATE ";
    }

    /** ?????????????????? ??????????????????????????????????????????(???????????????????????????????????????) */
    public static final String SYSTEM_VAL_MEMBER_SORT = "group";

    /**
     * @return String SQL
     */
    private String buildSQLForDeleteMemberBackward(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        return " DELETE FROM TMG_GROUP_MEMBER_CHECK " +
                " WHERE " +
                "     TGRM_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId()) +
                " AND TGRM_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND TGRM_CEMPLOYEEID        = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND TGRM_CMODIFIERUSERID    = " + escDBString(bean.getUserCode()) + " " +
                " AND TGRM_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction());
    }

    /**
     * @return String SQL
     */
    private String buildSQLForUpdateMemberWithEndDate(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {

        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()); // ??????????????????

        String sbSQL = " UPDATE " +
                "     TMG_GROUP_MEMBER " +
                " SET " +
                "     TGRM_DSTARTDATE = " +
                "     LEAST( " +
                "         TMG_F_GET_GROUP_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CGROUPID, " + sDBBaseDate + "), " +
                "         TMG_F_GET_DESIG_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CEMPLOYEEID, " + sDBBaseDate + ") " +
                "     ) + 1,  " +
                "     TGRM_CMODIFIERUSERID    = " + escDBString(bean.getUserCode()) + ", " +
                "     TGRM_DMODIFIEDDATE      = SYSDATE, " +
                "     TGRM_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) +
                " WHERE " +
                "     TGRM_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId()) +
                " AND TGRM_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND TGRM_CEMPLOYEEID = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND TGRM_DSTARTDATE <= " +
                " LEAST( " +
                "     TMG_F_GET_GROUP_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CGROUPID, " + sDBBaseDate + "), " +
                "     TMG_F_GET_DESIG_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CEMPLOYEEID, " + sDBBaseDate + ") " +
                " ) " +
                " AND TGRM_DENDDATE >= " +
                " LEAST( " +
                "     TMG_F_GET_GROUP_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CGROUPID, " + sDBBaseDate + "), " +
                "     TMG_F_GET_DESIG_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CEMPLOYEEID, " + sDBBaseDate + ") " +
                " ) ";
        return sbSQL;
    }

    /**
     *
     * @param evaluaterSettingParam
     * @return String SQL
     */
    private String buildSQLForInsertMemberForward(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        String sbSQL = " INSERT INTO TMG_GROUP_MEMBER " +
                " SELECT " +
                "     TGRM_CCUSTOMERID, " +
                "     TGRM_CCOMPANYID, " +
                "     TGRM_CEMPLOYEEID, " +
                "     TGRM_DSTARTDATE, " +
                "     TGRM_DENDDATE, " +
                "     TGRM_CMODIFIERUSERID, " +
                "     SYSDATE, " +
                "     TGRM_CMODIFIERPROGRAMID, " +
                "     TGRM_CSECTIONID, " +
                "     TGRM_CGROUPID, " +
                "     TGRM_CCHAR01, " +
                "     TGRM_CBASE_SECTIONID, " +
                "     TGRM_CBASE_GROUPID " +
                " FROM " +
                "     TMG_GROUP_MEMBER_CHECK " +
                " WHERE " +
                "     TGRM_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId()) +
                " AND TGRM_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND TGRM_CEMPLOYEEID        = " + escDBString(evaluaterSettingParam.getEmployee()) +
                " AND TGRM_CMODIFIERUSERID    = " + escDBString(bean.getUserCode()) +
                " AND TGRM_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction());
        return sbSQL;
    }

    /**
     *
     * @param evaluaterSettingParam
     * @return String SQL
     */
    private String buildSQLForUpdateMemberWithStartDate(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append(" UPDATE ");
        sbSQL.append("     TMG_GROUP_MEMBER ");
        sbSQL.append(" SET ");
        sbSQL.append("     TGRM_DENDDATE           = " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + " - 1, "); // ??????????????????
        sbSQL.append("     TGRM_CMODIFIERUSERID    = " + escDBString(bean.getUserCode()) + ", ");
        sbSQL.append("     TGRM_DMODIFIEDDATE      = SYSDATE, ");
        sbSQL.append("     TGRM_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()));
        sbSQL.append(" WHERE ");
        sbSQL.append("     TGRM_CCUSTOMERID  = " + escDBString(evaluaterSettingParam.getCustomerId()));
        sbSQL.append(" AND TGRM_CCOMPANYID   = " + escDBString(evaluaterSettingParam.getCompanyId()));
        sbSQL.append(" AND TGRM_CEMPLOYEEID  = " + escDBString(evaluaterSettingParam.getEmployee()));
        sbSQL.append(" AND TGRM_DSTARTDATE  <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        sbSQL.append(" AND TGRM_DENDDATE    >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        return sbSQL.toString();
    }

    /**
     * TMG_GROUP_MEMBER ???????????????????????????
     *
     * @param evaluaterSettingParam
     * @return String SQL
     */
    private String buildSQLForDeleteMember(EvaluatorSettingParam evaluaterSettingParam) {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" DELETE FROM ");
        sbSQL.append("     TMG_GROUP_MEMBER ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     TGRM_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId()));
        sbSQL.append(" AND TGRM_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()));
        sbSQL.append(" AND TGRM_CEMPLOYEEID = " + escDBString(evaluaterSettingParam.getEmployee()));
        // ????????????(????????????LEAST(????????????.?????????, ?????????.?????????))???????????????????????????????????????
        sbSQL.append(" AND TGRM_DSTARTDATE >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        sbSQL.append(" AND TGRM_DENDDATE   <=  ");
        sbSQL.append(" LEAST( ");
        sbSQL.append("     TMG_F_GET_GROUP_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CGROUPID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + "), ");
        sbSQL.append("     TMG_F_GET_DESIG_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CEMPLOYEEID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ") ");
        sbSQL.append(" ) ");
        return sbSQL.toString();
    }

    /**
     *
     * @param evaluaterSettingParam
     * @return String SQL
     */
    private String buildSQLForInsertMemberBackward(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append(" INSERT INTO TMG_GROUP_MEMBER_CHECK ");
        sbSQL.append(" SELECT ");
        sbSQL.append("     TGRM_CCUSTOMERID, ");
        sbSQL.append("     TGRM_CCOMPANYID, ");
        sbSQL.append("     TGRM_CEMPLOYEEID, ");
        sbSQL.append("     TO_DATE(" + escDBString(evaluaterSettingParam.getYYYYMMDD()) + ", " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS TGRM_DSTARTDATE, ");
        sbSQL.append("     LEAST( ");
        sbSQL.append("         TMG_F_GET_GROUP_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CGROUPID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + "), ");
        sbSQL.append("         TMG_F_GET_DESIG_ENDDATE(TGRM_CCUSTOMERID, TGRM_CCOMPANYID, TGRM_CSECTIONID, TGRM_CEMPLOYEEID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) +") ");
        sbSQL.append("     ) AS TGRM_DENDDATE, ");
        sbSQL.append(      escDBString(bean.getUserCode()) + " AS TGRM_CMODIFIERUSERID, ");
        sbSQL.append("     SYSDATE, ");
        sbSQL.append(      escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + " AS TGRM_CMODIFIERPROGRAMID, ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getSection()) + ", ");
        sbSQL.append(      escDBString(evaluaterSettingParam.getGroup()) + ", ");
        sbSQL.append("     TGRM_CCHAR01, ");
        sbSQL.append("     TGRM_CBASE_SECTIONID, ");
        sbSQL.append("     TGRM_CBASE_GROUPID ");
        sbSQL.append(" FROM ");
        sbSQL.append("     TMG_GROUP_MEMBER ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     TGRM_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId()));
        sbSQL.append(" AND TGRM_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()));
        sbSQL.append(" AND TGRM_CEMPLOYEEID = " + escDBString(evaluaterSettingParam.getEmployee()));
        sbSQL.append(" AND TGRM_DSTARTDATE <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        sbSQL.append(" AND TGRM_DENDDATE   >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        return sbSQL.toString();
    }

    /**
     * ???????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForSelectMember(EvaluatorSettingParam evaluaterSettingParam) {

        String sSQL = " SELECT "
                + "     M.TGRM_CEMPLOYEEID, "
                + "     TMG_F_GET_ME_NAME( M.TGRM_CEMPLOYEEID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ", 0, M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID ) as TGRM_CEMPLOYEEID_NAME, "
                + "     G.TGR_CGROUPID, "
                + "     G.TGR_CGROUPNAME, "
                + "     M.TGRM_CBASE_SECTIONID, "
                + "     M.TGRM_CBASE_GROUPID "
                + " FROM  ";
        if (EvaluatorSettingBean.SYSTEM_VAL_MEMBER_SORT.equals(scCacheUtil.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_MEMBER_SORT))) {
            sSQL += " TMG_GROUP G, TMG_GROUP_MEMBER M, HIST_DESIGNATION ";
        } else {
            sSQL += " TMG_GROUP G, TMG_GROUP_MEMBER M ";
        }

        sSQL += " WHERE  "
                + "     G.TGR_CSECTIONID   = " + escDBString(evaluaterSettingParam.getSection())
                + " AND G.TGR_DSTARTDATE  <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD())
                + " AND G.TGR_CCOMPANYID   = " + escDBString(evaluaterSettingParam.getCompanyId())
                + " AND G.TGR_CCUSTOMERID  = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND G.TGR_DENDDATE    >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD())
                + " AND M.TGRM_CSECTIONID  = G.TGR_CSECTIONID "
                + " AND M.TGRM_CGROUPID    = G.TGR_CGROUPID "
                + " AND M.TGRM_DSTARTDATE <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD())
                + " AND M.TGRM_CCOMPANYID  = G.TGR_CCOMPANYID "
                + " AND M.TGRM_CCUSTOMERID = G.TGR_CCUSTOMERID "
                + " AND M.TGRM_DENDDATE   >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD())
                + " AND TMG_F_IS_MANAGEFLG(M.TGRM_CEMPLOYEEID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ", " +
                SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ", "
                + " M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID) <> " + escDBString(TmgUtil.Cs_MGD_MANAGEFLG_0); // ????????????????????????

        if (EvaluatorSettingBean.SYSTEM_VAL_MEMBER_SORT.equals(scCacheUtil.getSystemProperty(TmgUtil.Cs_CYC_PROPNAME_MEMBER_SORT))) {
            // where??????????????????
            sSQL += " AND HD_CCUSTOMERID_CK = M.TGRM_CCUSTOMERID "
                    + " AND HD_CCOMPANYID_CK  = M.TGRM_CCOMPANYID "
                    + " AND HD_CEMPLOYEEID_CK = M.TGRM_CEMPLOYEEID "
                    + " AND HD_DSTARTDATE_CK <= TRUNC(SYSDATE) "
                    + " AND HD_DENDDATE      >= TRUNC(SYSDATE) "
                    + " AND HD_CIFKEYORADDITIONALROLE = '0' ";

            sSQL += " ORDER BY "
                    + " G.TGR_CSECTIONID, "
                    + " G.TGR_CGROUPID, "
                    + " TMG_F_GET_MP_WEIGHT(HD_CPOSTID_FK, TRUNC(SYSDATE), M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID), "
                    + " TMG_F_GET_ME_NAME(M.TGRM_CEMPLOYEEID, TRUNC(SYSDATE), 1, M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID), "
                    + " M.TGRM_CEMPLOYEEID, "
                    + " M.TGRM_DSTARTDATE ";

        } else {
            sSQL += " ORDER BY "
                    + " G.TGR_CSECTIONID, "
                    + " DECODE( SUBSTR(G.TGR_CGROUPID,INSTR(G.TGR_CGROUPID,'|')+1,6), " + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0'||G.TGR_CGROUPNAME, '1'||G.TGR_CGROUPNAME), "
                    + " M.TGRM_CEMPLOYEEID, "
                    + " M.TGRM_DSTARTDATE ";
        }

        return sSQL;
    }

    /**
     * ???????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForSelectEvalErrMsg(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        return " SELECT "
                + "     TMG_F_GET_ERRORMESSAGE("
                + "E.TER_CERRCODE,"
                + escDBString(evaluaterSettingParam.getCompanyId())	+ ", "
                + "TO_DATE(" + escDBString(evaluaterSettingParam.getYYYYMMDD()) + ", " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + "), "
                + escDBString(evaluaterSettingParam.getLanguage()) + ") "
                + " FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CMODIFIERUSERID    = " + escDBString(bean.getUserCode())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)
                + " AND E.TER_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND E.TER_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }

    /**
     * ???????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertEvalTrigger(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {

        return " INSERT INTO TMG_TRIGGER ( "
                + " TTR_CCUSTOMERID, "
                + " TTR_CCOMPANYID, "
                + " TTR_CEMPLOYEEID, "
                + " TTR_DSTARTDATE, "
                + " TTR_DENDDATE, "
                + " TTR_CMODIFIERUSERID, "
                + " TTR_DMODIFIEDDATE, "
                + " TTR_CMODIFIERPROGRAMID, "
                + " TTR_CPROGRAMID, "
                + " TTR_CPARAMETER1 "
                + " ) VALUES ( "
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + escDBString(bean.getUserCode()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(bean.getUserCode()) + ", "
                + " SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL) + ", "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL) + ", "
                + escDBString(EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)
                + " )";
    }


    /**
     * ???????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertEvalErrMsg(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {

        return " INSERT INTO TMG_ERRMSG ( "
                + " TER_CCUSTOMERID, "
                + " TER_CCOMPANYID, "
                + " TER_DSTARTDATE, "
                + " TER_DENDDATE, "
                + " TER_CMODIFIERUSERID, "
                + " TER_DMODIFIEDDATE, "
                + " TER_CMODIFIERPROGRAMID, "
                + " TER_CERRCODE, "
                + " TER_CLANGUAGE "
                + " ) VALUES ("
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(bean.getUserCode()) + ", "
                + " SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL) + ", "
                + " TMG_F_CHECK_EVALUATER(" + escDBString(bean.getUserCode()) + "," + escDBString(
                        EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL) + "),"
                + escDBString(evaluaterSettingParam.getLanguage())
                + " ) ";
    }

    /**
     * ???????????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertEvalCheck(EvaluatorSettingParam evaluaterSettingParam,AddEvaluatorDTO dto,PsDBBean bean) {

        String baseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());

        String sResults = TmgUtil.Cs_MGD_ONOFF_0;        // ?????????????????????
        String sNotification = TmgUtil.Cs_MGD_ONOFF_0;   // ?????????????????????
        String sOvertime = TmgUtil.Cs_MGD_ONOFF_0;       // ?????????????????????
        String sSchedule = TmgUtil.Cs_MGD_ONOFF_0;       // ????????????????????? #427
        String sAuthority = TmgUtil.Cs_MGD_ONOFF_0;      // ?????????????????????
        String sAdminFlg = TmgUtil.Cs_MGD_ONOFF_0;       // ?????????????????????0
        String sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_0; // ?????????????????????
        String sApprovalLevel = null;

        if (dto.getDailyResult()) {
            sResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getNotification()) {
            sNotification = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getOverTime()) {
            sOvertime = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getSchedule()) {
            sSchedule = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getAuthority()) {
            sAuthority = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (dto.getMonthlyResult()) {
            sMonthlyResults = TmgUtil.Cs_MGD_ONOFF_1;
        } // ??????????????????????????????1
        if (StrUtil.isNotBlank(dto.getApprovalLevel())) {
            sApprovalLevel = escDBString(dto.getApprovalLevel());
        }

        // ?????????????????????1
        if (evaluaterSettingParam.isSiteTa()) {
            sAdminFlg = TmgUtil.Cs_MGD_ONOFF_1;
        }

        StringBuilder sSQL = new StringBuilder();
        String sCustId  = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId  = escDBString(evaluaterSettingParam.getCompanyId());
        String sEmpId   = escDBString(evaluaterSettingParam.getEmployee());
        String sSecId   = escDBString(dto.getSectionId());
        String sGroupId = escDBString(dto.getGroupId());

        sSQL.append(" INSERT INTO TMG_EVALUATER_CHECK(");
        sSQL.append("     TEV_CCUSTOMERID, ");
        sSQL.append("     TEV_CCOMPANYID, ");
        sSQL.append("     TEV_CEMPLOYEEID, ");
        sSQL.append("     TEV_DSTARTDATE,");
        sSQL.append("     TEV_DENDDATE, ");
        sSQL.append("     TEV_CMODIFIERUSERID, ");
        sSQL.append("     TEV_DMODIFIEDDATE,");
        sSQL.append("     TEV_CMODIFIERPROGRAMID, ");
        sSQL.append("     TEV_CSECTIONID, ");
        sSQL.append("     TEV_CGROUPID, ");
        sSQL.append("     TEV_CEDITABLEFLG,");
        sSQL.append("     TEV_CRESULTS, ");
        sSQL.append("     TEV_CNOTIFICATION, ");
        sSQL.append("     TEV_COVERTIME, ");
        sSQL.append("     TEV_CSCHEDULE,");
        sSQL.append("     TEV_CAUTHORITY, ");
        sSQL.append("     TEV_CADMINFLG, ");
        sSQL.append("     TEV_CSECTIONEVALUATER, ");
        sSQL.append("     TEV_CMONTHLYAPPROVAL, ");
        sSQL.append("     TEV_CAPPROVAL_LEVEL ");
        sSQL.append(" )");
        sSQL.append(" SELECT ");
        sSQL.append("     G.TGR_CCUSTOMERID,");
        sSQL.append("     G.TGR_CCOMPANYID,");
        sSQL.append(      sEmpId + ",");
        sSQL.append(      SysUtil.transDateNullToDB(dto.getStartDate()) + ",");
        sSQL.append(      SysUtil.transDateNullToDB(dto.getEndDate())   + ",");
        sSQL.append(      escDBString(bean.getUserCode())+",");
        sSQL.append("     SYSDATE,");
        sSQL.append(      escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)+",");
        sSQL.append("     G.TGR_CSECTIONID,");
        sSQL.append("     G.TGR_CGROUPID,");
        sSQL.append(      escDBString(TmgUtil.Cs_MGD_ONOFF_1)+",");
        sSQL.append(      escDBString(sResults)+",");
        sSQL.append(      escDBString(sNotification)+",");
        sSQL.append(      escDBString(sOvertime)+",");
        sSQL.append(      escDBString(sSchedule)+",");
        sSQL.append(      escDBString(sAuthority)+",");
        sSQL.append(      escDBString(sAdminFlg)+",");
        sSQL.append(      escDBString(TmgUtil.Cs_MGD_ONOFF_0)+",");
        sSQL.append(      escDBString(sMonthlyResults)+",");
        sSQL.append(      sApprovalLevel);
        sSQL.append(" FROM");
        sSQL.append("     TMG_GROUP G");
        sSQL.append(" WHERE");
        sSQL.append("     G.TGR_CCUSTOMERID = " + sCustId);
        sSQL.append(" AND G.TGR_CCOMPANYID  = " + sCompId);
        sSQL.append(" AND G.TGR_CSECTIONID  = " + sSecId);
        sSQL.append(" AND G.TGR_CGROUPID    = " + sGroupId);
        sSQL.append(" AND G.TGR_DSTARTDATE <= " + baseDate);
        sSQL.append(" AND G.TGR_DENDDATE   >= " + baseDate);

        return sSQL.toString();
    }

    /**
     * ???????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeleteEvalTrigger(EvaluatorSettingParam params,PsDBBean bean) {
        return " DELETE FROM "
                + "     TMG_TRIGGER T "
                + " WHERE "
                + "     T.TTR_CMODIFIERUSERID    = " + escDBString(bean.getUserCode())
                + " AND T.TTR_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)
                + " AND T.TTR_CCUSTOMERID        = " + escDBString(params.getCustomerId())
                + " AND T.TTR_CCOMPANYID         = " + escDBString(params.getCompanyId());
    }

    /**
     * ????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeleteEvalCheck(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        return " DELETE FROM "
                + "     TMG_EVALUATER_CHECK E "
                + " WHERE "
                + "       E.TEV_CMODIFIERUSERID    = " + escDBString(bean.getUserCode())
                + " AND   E.TEV_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)
                + " AND   E.TEV_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND   E.TEV_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }


    /**
     * ???????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeleteEvalErrMsg(EvaluatorSettingParam evaluaterSettingParam,PsDBBean bean) {
        return " DELETE FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CMODIFIERUSERID    = " + escDBString(bean.getUserCode())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + EvaluatorSettingConst.ACT_ADDEVAL_CEVAL)
                + " AND E.TER_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND E.TER_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * @param psGroupId
     * @return
     */
    private boolean isDefaultGroup(String psGroupId){
        if (psGroupId == null) {
            return false;
        }
        return psGroupId.endsWith(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);
    }

    private boolean hasAuthority(String sAuthority,EvaluatorSettingParam params){
        try{
            return referList.hasAuthority(params.getYYYYMMDD(), sAuthority);
        }catch(Exception e){
            return false;
        }
    }

    /**
     * ??????????????????????????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForDeleteGroup(EvaluatorSettingParam params) {
        return " DELETE FROM "
                + "     TMG_GROUP T1 "
                + " WHERE "
                + "     T1.TGR_CCUSTOMERID = " + escDBString(params.getCustomerId())
                + " AND T1.TGR_CCOMPANYID  = " + escDBString(params.getCompanyId())
                + " AND T1.TGR_CSECTIONID  = " + escDBString(params.getSection())
                + " AND T1.TGR_CGROUPID    = " + escDBString(params.getGroup())
                + " AND T1.TGR_DSTARTDATE >= " + "TMG_F_GET_ORG_STARTDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + SysUtil.transDateNullToDB(params.getYYYYMMDD()) + ") "
                + " AND T1.TGR_DENDDATE   <= " + "TMG_F_GET_ORG_ENDDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + SysUtil.transDateNullToDB(params.getYYYYMMDD()) + ") ";
    }

    private void configYYYYMMDD(PsDBBean psDBBean, EvaluatorSettingParam params) {
        // REQUEST:?????????
        String YYYYMMDD = (String) psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_YYYYMMDD);

        // ????????????????????????????????????????????????????????????????????????
        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())) {
            // ??????????????????????????????????????????????????????????????????????????????
            if (StrUtil.isBlank(YYYYMMDD)) {
                YYYYMMDD = TmgUtil.getSysdate().substring(0, 8).concat("01");
                params.setYYYYMMDD(YYYYMMDD);
            } else {
                params.setYYYYMMDD(YYYYMMDD);
            }
        } else {
            if (StrUtil.isBlank(YYYYMMDD)) {
                params.setYYYYMMDD(TmgUtil.getSysdate());
                Vector vecSQL = new Vector();
                vecSQL.add(buildSQLForTransitionDate(params));
                PsResult psResult = null;
                try {
                    psResult = psDBBeanUtil.getValuesforMultiquery(vecSQL, EvaluatorSettingConst.BEAN_DESC,psDBBean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String baseDate = psDBBeanUtil.valueAtColumnRow(psResult,0, 0, 0);
                if (StrUtil.isNotBlank(baseDate)) {
                    params.setYYYYMMDD(baseDate);
                }
            } else {
                params.setYYYYMMDD(YYYYMMDD);
            }
        }
    }

    /**
     * ??????????????????????????????????????????????????????
     */
    private void showDisp(EvaluatorSettingParam params,PsDBBean psDBBean,Map<String,Object> result,boolean haveAuthority) {

        /** ??????????????????????????????( */
        String authNotValidMsg;

        // ?????????????????????????????????????????????????????????
        PsResult psResult;
        List<EvaluatorGroupVO> dataList = CollUtil.newArrayList();
        if (params.isSection()) {
             //?????????????????????????????????????????????????????????????????????????????????return
            // ????????????????????????????????????
            if (params.isSiteTa()) {
                // ??????????????????????????????
                if (!hasAuthOfSect(params.getSection(),psDBBean)) {
                    authNotValidMsg = scCacheUtil.getSystemProperty("MSG_NO_SELECT_SECTION");
                    result.put("authNotValidMsg",authNotValidMsg);
                    return;
                }
            }
            // ??????
            Vector <String> vQuery = new Vector <>();
            vQuery.add(buildSQLForSelectGroupAndEvaluater(params));  // 0 ???????????????????????????????????????
            vQuery.add(buildSQLForSelectEvaluaterNum(params));       // 1 ?????????????????????????????????????????????????????????
            vQuery.add(buildSQLForTransitionDate(params));           // 2 ???????????????????????????????????????(?????????)
            vQuery.add(buildSQLForSelectEvaluaterEmpNum(params));    // 3 ???????????????????????????????????????????????????????????????????????????
            try {
                 psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,psDBBean);
                 // ????????????????????????????????????????????????????????????????????????????????????
                 Map<String,Object> groupEmpDataMap = MapUtil.newHashMap();
                 for (int i=0; i <= psDBBeanUtil.getCount(psResult,IDX_EVALEMPNUM); i++) {
                     // ?????????????????????ID_????????????????????????
                     String key= psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_GROUPID, i) +
                                 "_" +
                             psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_EMPID, i);
                     // ???????????????????????????????????????????????????????????????????????????
                     String value = psDBBeanUtil.valueAtColumnRow(psResult,IDX_EVALEMPNUM, EvaluatorSettingConst.COL_EVALEMPNUM_DATACNT, i);
                     if (StrUtil.isNotBlank(value)) {
                         groupEmpDataMap.put(key,value);
                     }
                 }

                String sGroupName_Group = "";	// ????????????????????????ID
                String sGroupName_Eval  = "";	// ????????????????????????????????????ID
                boolean sEmpNameLinkTagFlg;		// ??????????????????????????????????????????
                boolean sEmpNameBoldTagFlg;		// ???????????????????????????????????????

                // ???????????????????????????
                for (int i=0;i<psDBBeanUtil.getCount(psResult, IDX_LIST);i++){

                    EvaluatorGroupVO vo = new EvaluatorGroupVO();

                    sGroupName_Eval = psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i);
                    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                    /* 2007/06/28  H.Kawabata      ??????????????????????????? */
                    if(!sGroupName_Group.equals(sGroupName_Eval)) {
                        sGroupName_Group = sGroupName_Eval;	//?????????????????????ID????????????

                        //????????????????????????????????????????????????????????????
                        if (i == 0) { //2008/10/10
                               vo.setMain(true);
                        } else {
                               vo.setMain(false);
                        }
                        // ?????? ????????????????????? ???????????????????????????????????? ?????? ??????????????????????????? ?????????????????????????????????????????????????????????????????????????????????
                        if ((params.isSiteTp() && i != 0 && haveAuthority) || params.isSiteTa()) {
                            // ??????????????????????????????????????????????????????????????????
                            // ????????????????????????????????????????????????????????????????????????????????????????????????
                            String groupId = psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i);
                            if (StrUtil.equals(
                                    groupId,
                                    params.getRootGroup())) {
                                vo.setEnableEditGroup(true);
                                vo.setEnableEditGroupName(false);
                            } else {
                                vo.setEnableEditGroup(false);
                                vo.setEnableEditGroupName(true);
                            }
                        } else {
                            vo.setEnableEditGroup(false);
                            vo.setEnableEditGroupName(false);
                        }
                        vo.setGroup(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i));
                        vo.setGroupId(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i));
                    } else {
                        vo.setGroup(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i-1));
                        vo.setGroupId(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i-1));
                    }

                    // ??? ????????????????????????????????? ???
                    // ???????????????????????????????????????????????????"?????????"??????????????????
                    String sEvalEmpId = psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEEID,i);

                    List<EvaluatorMemberVO> memberList = CollUtil.newArrayList();

                    if (sEvalEmpId == null || StrUtil.equals("null",sEvalEmpId)) {
                        vo.setMemberList(memberList);
                    } else {
                        EvaluatorMemberVO memberVO = new EvaluatorMemberVO();

                        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        if (i==0
                                || !psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i-1).equals(sGroupName_Eval)
                                || !psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEEID,i-1).equals(sEvalEmpId))
                        {
                            /*
                             * ??????????????????????????????????????????????????????????????????????????????????????????
                             *
                             * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                             * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                             */
                            sEmpNameLinkTagFlg = false;	// ????????????????????????????????????
                            sEmpNameBoldTagFlg = false;	// ?????????????????????????????????
                            if (isEditLink(
                                    psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EDITABLEFLG,i),
                                    psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_ADMINFLG,i),
                                    params, haveAuthority
                            )) {
                                sEmpNameLinkTagFlg = true;
                                // ??????????????????????????????????????????????????????????????????
                                if (isSecEvaluater(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_SECTIONEVALUATER,i))) {
                                    sEmpNameBoldTagFlg = true;
                                }
                           // ??????????????????????????????????????????????????????????????????????????????????????????
                            } else {
                            // ??????????????????????????????
                               sEmpNameBoldTagFlg = true;
                            }
                            memberVO.setEnableEmpEdit(sEmpNameLinkTagFlg);
                            memberVO.setNameBold(sEmpNameBoldTagFlg);

                            memberVO.setGroupId(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPID,i));
                            memberVO.setGroupName(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_GROUPNAME,i));
                            memberVO.setPostName(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_POSTNAME,i));

                            memberVO.setEmpId(sEvalEmpId);
                            memberVO.setName(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_EMPLOYEENAME,i));

                        }

                        // ??? ???????????????????????? ???
                        List<EvaluatorMemberRightVO> rightList = CollUtil.newArrayList();
                        EvaluatorMemberRightVO memberRightVO = new EvaluatorMemberRightVO();

                        // ??????????????????
                        memberRightVO.setWorkConfirm(
                          isAuthority(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_RESULTS,i))
                        );
                        // ??????????????????
                        memberRightVO.setMonthConfirm(
                          isAuthority(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_MONTHLYRESULTS,i))
                        );
                        // ??????????????????
                        memberRightVO.setOverWorkConfirm(
                          isAuthority(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_OVERTIME,i))
                        );
                        // ???????????????????????????????????????????????????
                        memberRightVO.setScheduleCreate(
                          isAuthority(
                                  psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_SCHEDULE,i)));
                        // ???????????????????????????????????????????????????
                        memberRightVO.setPermissionGive(
                          isAuthority(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_AUTHORITY,i)));
                        // ????????????????????????????????????????????????????????????
                        boolean restConfirm = isAuthority(psDBBeanUtil.valueAtColumnRow(psResult,IDX_LIST,EvaluatorSettingConst.COL_EVALLIST_NOTIFICATION,i));
                        memberRightVO.setRestConfirm(restConfirm);
                        // ????????????????????????????????????????????????????????????????????????
                        if (restConfirm) {
                            String level = psDBBeanUtil.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_APPROVALLEVEL, i);
                            if (StrUtil.isNotBlank(level) && !StrUtil.equalsIgnoreCase(level,"null")) {
                                memberRightVO.setLevel(Integer.parseInt(level));
                            }
                        }
                        memberRightVO.setValidStartDate(psDBBeanUtil.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_TERM_FROM, i));
                        memberRightVO.setValidEndDate(psDBBeanUtil.valueAtColumnRow(psResult, IDX_LIST, EvaluatorSettingConst.COL_EVALLIST_TERM_TO, i));

                        rightList.add(memberRightVO);
                        memberVO.setValidList(rightList);

                        memberList.add(memberVO);

                        vo.setMemberList(memberList);
                    }
                    dataList.add(vo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }

        // ???????????????????????????????????????
        List<EvaluatorGroupVO> formatGroupList = CollUtil.distinct(dataList);

        for (EvaluatorGroupVO groupVO : dataList) {
            for (EvaluatorGroupVO formatGroupVO : formatGroupList) {
                if (StrUtil.equals(groupVO.getGroupId(),formatGroupVO.getGroupId())) {
                   formatGroupVO.getMemberList().addAll(groupVO.getMemberList());
                }
            }
        }

        // ???empId?????????????????????empId
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
               for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                   EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                   // ????????????????????????1?????????????????????npe??????
                   if (StrUtil.isBlank(memberVO.getEmpId()) && evaluatorGroupVO.getMemberList().size()>1) {
                       EvaluatorMemberVO preMemberVO = evaluatorGroupVO.getMemberList().get(i-1);
                       memberVO.setEmpId(preMemberVO.getEmpId());
                       memberVO.setName(preMemberVO.getName());
                   }
               }
        }

        // ?????????????????????????????????????????????
        Map<String,List<EvaluatorMemberRightVO>> multiRigtMap = MapUtil.newHashMap();
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size(); i++) {

                EvaluatorMemberVO memberVO =  evaluatorGroupVO.getMemberList().get(i);
                if (StrUtil.isBlank(memberVO.getGroupName())) {
                    if (!multiRigtMap.containsKey(memberVO.getEmpId())) {
                        multiRigtMap.put(memberVO.getEmpId(),memberVO.getValidList());
                    } else {
                        List<EvaluatorMemberRightVO> rightList = multiRigtMap.get(memberVO.getEmpId());
                        rightList.addAll(memberVO.getValidList());
                    }
                }

            }
        }
        // ??????memberList?????????????????????
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            List<EvaluatorMemberVO> distMemberList = CollUtil.newArrayList();
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                if (!distMemberList.contains(memberVO)) {
                    distMemberList.add(memberVO);
                }
            }
            evaluatorGroupVO.setMemberList(distMemberList);
        }

        // ??????????????????????????????????????????????????????????????????
        for (EvaluatorGroupVO evaluatorGroupVO : formatGroupList) {
            for (int i = 0; i < evaluatorGroupVO.getMemberList().size();i++) {
                EvaluatorMemberVO memberVO = evaluatorGroupVO.getMemberList().get(i);
                if (multiRigtMap.containsKey(memberVO.getEmpId())) {
                    List<EvaluatorMemberRightVO> validList = memberVO.getValidList();
                    validList.addAll(multiRigtMap.get(memberVO.getEmpId()));
                    memberVO.setValidList(validList);
                }
            }
        }

        // ??????????????????????????????name???null?????????
        Map<Integer,List<EvaluatorMemberVO>> removeMap = MapUtil.newHashMap();
        for (int i = 0; i < formatGroupList.size(); i++) {
            EvaluatorGroupVO groupVo = formatGroupList.get(i);
            List<EvaluatorMemberVO> removeList = CollUtil.newArrayList();
            for (int j = 0; j < groupVo.getMemberList().size(); j++) {
                EvaluatorMemberVO memberVO = groupVo.getMemberList().get(j);
                memberVO.setId(j+1);
                if (StrUtil.equalsIgnoreCase("null",memberVO.getName())) {
                    removeList.add(memberVO);
                }
            }
            removeMap.put(i,removeList);
        }

        removeMap.forEach((key,value)-> {
            EvaluatorGroupVO groupVo = formatGroupList.get(key);
            groupVo.getMemberList().removeAll(value);
        });

        result.put("bottomMessage",getEvasetMessage(psDBBean,params, EvaluatorSettingConst.TMG_EVASET_MESSAGE_DISPALERT));
        result.put("list",formatGroupList);
    }

    /**
     * ???????????????????????????????????????????????????
     * @param psDBBean
     * @param params
     * @return
     */
    private boolean processParams(PsDBBean psDBBean, EvaluatorSettingParam params) {
        boolean haveAuthority = true;
        // ????????????????????????????????????
        try {
            params.setAction((String) psDBBean.getRequestHash().get(EvaluatorSettingConst.REQUEST_KEY_ACTION));
            params.setSite(psDBBean.getSiteId());
            params.setLanguage(psDBBean.getLanguage());
            params.setCustomerID(psDBBean.getCustID());
            params.setCompanyId(psDBBean.getCompCode());
            params.setEmployee(psDBBean.getUserCode());
            /*
             * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
             * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
             * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
             */
            String adminTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
            String permTargetSection = psDBBean.getReqParam(TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
            if (params.isSiteTa() && StrUtil.isNotBlank(adminTargetSection)) {
                params.setSection(adminTargetSection);
            } else if (params.isSiteTp() && StrUtil.isNotBlank(permTargetSection)) {
                params.setSection(permTargetSection);
            }
            configYYYYMMDD(psDBBean, params);
        }  catch (Exception e) {
            e.printStackTrace();
            haveAuthority = false;
        }
        return haveAuthority;
    }

    private String escDBString(String sString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(sString));
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @return ????????????(???:true??????:false)
     */
    private boolean hasAuthOfSect(String section,PsDBBean psDBBean) {
        // ??????
        Vector vQuery = new Vector();
        vQuery.add(buildSQLForSelectHasAuth(section,psDBBean));
        String sConut = null;
        int nCount = 0;
        try {
            PsResult psResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,psDBBean);
            // ?????????????????????????????????false
            sConut = psDBBeanUtil.valueAtColumnRow(psResult,0, 0, 0);
            nCount = Integer.parseInt(sConut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nCount > 0){
            return true;
        }
        return false;
    }


    /**
     * ???????????????????????????????????????????????????????????????SQL?????????????????????
     * @return SQL
     */
    private String buildSQLForSelectHasAuth(String section,PsDBBean psDBBean) {
        StringBuffer sSQL = new StringBuffer();
        String sExists = getOrgTreeSearchRangeForTreeBuild(psDBBean);
        sSQL.append(" SELECT ");
        sSQL.append("     COUNT(1) ");
        sSQL.append(" FROM MAST_ORGANISATION o ");
        sSQL.append(" WHERE ");
        sSQL.append("     o.MO_CCUSTOMERID_CK_FK = " + escDBString(psDBBean.getCustID()));
        sSQL.append(" AND o.MO_CCOMPANYID_CK_FK  = " + escDBString(psDBBean.getCompCode()));
        sSQL.append(" AND o.MO_CSECTIONID_CK     = " + escDBString(section));  // ???????????????????????????????????????
        sSQL.append(" AND o.MO_DSTART           <= TRUNC(SYSDATE) ");
        sSQL.append(" AND o.MO_DEND             >= TRUNC(SYSDATE) ");
        sSQL.append(" AND o.MO_CLANGUAGE         = " + escDBString(psDBBean.getLanguage()));
        sSQL.append(sExists);
        return sSQL.toString();
    }

    /**
     * ?????????????????????????????????(????????????????????????????????????????????????????????????Tree????????????????????????????????????????????????????????????????????????????????????)
     * @return
     */
    public String getOrgTreeSearchRangeForTreeBuild(PsDBBean psDBBean) {
        String sExists;
        try {
            sExists = tmgSearchRangeUtil.getExistsQueryOrganisation(psDBBean, ContextUtil.getHttpRequest().getSession(), "o.MO_CLAYEREDSECTIONID");
        } catch(Exception e) {
            sExists = "";
        }
        return sExists;
    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param params
     * @return String SQL
     */
    public String buildSQLForTransitionDate(EvaluatorSettingParam params) {

        // ???????????????????????????????????????????????????
        String sDBCustId   = escDBString(params.getCustomerId()); // ???????????????
        String sDBCompId   = escDBString(params.getCompanyId());  // ???????????????
        String sDBSecId    = escDBString(params.getSection());    // ???????????????
        String sDBBaseDate = SysUtil.transDateNullToDB(params.getYYYYMMDD());      // ?????????

        String sbSQL = " SELECT " +
                "     ( " + // ??????????????????????????????????????????
                "         SELECT TO_CHAR(MAX(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "               SELECT TGR_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP" +
                "               WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "               AND    TGR_CCOMPANYID  = " + sDBCompId +
                "               AND    TGR_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP_MEMBER " +
                "               WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "               AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "               AND    TGRM_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TEV_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_EVALUATER " +
                "               WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "               AND    TEV_CCOMPANYID  = " + sDBCompId +
                "               AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "         WHERE " +
                "             STARTDATE = " +
                "                 ( " + // TRUNC(SYSDATE)?????????????????????????????????
                "                  SELECT MAX(STARTDATE) AS STARTDATE " +
                "                  FROM (" +
                "                        SELECT TGR_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_GROUP " +
                "                        WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "                        AND    TGR_CCOMPANYID  = " + sDBCompId +
                "                        AND    TGR_CSECTIONID  = " + sDBSecId +
                "                        UNION " +
                "                        SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_GROUP_MEMBER " +
                "                        WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "                        AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "                        AND    TGRM_CSECTIONID  = " + sDBSecId +
                "                        UNION " +
                "                        SELECT TEV_DSTARTDATE AS STARTDATE " +
                "                        FROM   TMG_EVALUATER " +
                "                        WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "                        AND    TEV_CCOMPANYID  = " + sDBCompId +
                "                        AND    TEV_CSECTIONID  = " + sDBSecId +
                "                       ) " +
                "                  WHERE " +
                "                      STARTDATE < " + sDBBaseDate +
                "                 )  " +
                "     ), " +
                "     ( " + // TRUNC(SYSDATE)?????????????????????????????????
                "         SELECT TO_CHAR(MAX(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "               SELECT TGR_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP " +
                "               WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "               AND    TGR_CCOMPANYID  = " + sDBCompId +
                "               AND    TGR_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_GROUP_MEMBER " +
                "               WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "               AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "               AND    TGRM_CSECTIONID  = " + sDBSecId +
                "               UNION " +
                "               SELECT TEV_DSTARTDATE AS STARTDATE " +
                "               FROM   TMG_EVALUATER " +
                "               WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "               AND    TEV_CCOMPANYID  = " + sDBCompId +
                "               AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "          WHERE " +
                "              STARTDATE <= TRUNC(SYSDATE) " +
                "     ), " +
                "     ( " + // ??????????????????????????????????????????
                "         SELECT TO_CHAR(MIN(STARTDATE), " + escDBString(EvaluatorSettingConst.DATE_FORMAT) + ") AS STARTDATE " +
                "         FROM ( " +
                "                SELECT TGR_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_GROUP " +
                "                WHERE  TGR_CCUSTOMERID = " + sDBCustId +
                "                AND    TGR_CCOMPANYID  = " + sDBCompId +
                "                AND    TGR_CSECTIONID  = " + sDBSecId +
                "                UNION " +
                "                SELECT TGRM_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_GROUP_MEMBER " +
                "                WHERE  TGRM_CCUSTOMERID = " + sDBCustId +
                "                AND    TGRM_CCOMPANYID  = " + sDBCompId +
                "                AND    TGRM_CSECTIONID  = " + sDBSecId +
                "                UNION " +
                "                SELECT TEV_DSTARTDATE AS STARTDATE " +
                "                FROM   TMG_EVALUATER " +
                "                WHERE  TEV_CCUSTOMERID = " + sDBCustId +
                "                AND    TEV_CCOMPANYID  = " + sDBCompId +
                "                AND    TEV_CSECTIONID  = " + sDBSecId +
                "              ) " +
                "         WHERE " +
                "             STARTDATE > " + sDBBaseDate +
                "     ) " +
                " FROM " +
                "     DUAL ";
        return sbSQL;
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectGroupAndEvaluater(EvaluatorSettingParam evaluaterSettingParam) {
        StringBuilder sSQL = new StringBuilder();
        String custId   = escDBString(evaluaterSettingParam.getCustomerId());
        String compId   = escDBString(evaluaterSettingParam.getCompanyId());
        String baseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String lang     = escDBString(evaluaterSettingParam.getLanguage());

        sSQL.append(" SELECT ");
        sSQL.append("     G.TGR_CGROUPID, ");
        sSQL.append("     G.TGR_CGROUPNAME, ");
        sSQL.append("     E.TEV_CEMPLOYEEID, ");
        sSQL.append("     TMG_F_GET_ME_NAME(E.TEV_CEMPLOYEEID, " + baseDate + ", 0, E.TEV_CCUSTOMERID, E.TEV_CCOMPANYID) as TEV_CEMPLOYEEID_NAME, ");
        sSQL.append("     TMG_F_GET_MO(D.HD_CSECTIONID_FK, " + baseDate + ", 1, D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") as HD_CSECTIONID_FK, ");
        sSQL.append("     TMG_F_GET_MP(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") as HD_CPOSTID_FK, ");
        /*
         * "?????????????????????"???????????????????????????????????????????????????????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????MAX???????????????
         */
//		sSQL.append("     E.TEV_CEDITABLEFLG, ");
        sSQL.append("     (");
        sSQL.append("      SELECT MAX(E2.TEV_CEDITABLEFLG) ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("     ) AS TEV_CEDITABLEFLG, ");
        sSQL.append("     E.TEV_CRESULTS, ");
        sSQL.append("     E.TEV_CNOTIFICATION, ");
        sSQL.append("     E.TEV_COVERTIME, ");
        sSQL.append("     E.TEV_CSCHEDULE, ");
        sSQL.append("     E.TEV_CAUTHORITY, ");
        /*
         * "?????????????????????"???????????????????????????????????????????????????????????????????????????????????????
         * ???????????????????????????????????????????????????????????????????????????????????????????????????MIN???????????????
         */
//		sSQL.append("     E.TEV_CADMINFLG, ");
        sSQL.append("     (");
        sSQL.append("      SELECT MIN(E2.TEV_CADMINFLG) ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("     ) AS TEV_CADMINFLG, ");
        sSQL.append("     E.TEV_CMONTHLYAPPROVAL, ");
        /*
         * "?????????????????????????????????"???????????????????????????????????????????????????????????????????????????
         * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         */
//		sSQL.append("     E.TEV_CSECTIONEVALUATER, ");
        sSQL.append("     NVL((");
        sSQL.append("      SELECT E2.TEV_CSECTIONEVALUATER ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ), " + escDBString(TmgUtil.Cs_MGD_ONOFF_0) + ") AS TEV_CSECTIONEVALUATER, ");
        sSQL.append("     TMG_F_GET_MGD(E.TEV_CAPPROVAL_LEVEL," + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") AS CSECTIONEVALUATER, ");
        sSQL.append("     TO_CHAR(E.TEV_DSTARTDATE, 'yyyy/mm/dd') AS TEV_DSTARTDATE, ");
        sSQL.append("     TO_CHAR(E.TEV_DENDDATE,   'yyyy/mm/dd') AS TEV_DENDDATE, ");

        // ?????????????????????
        sSQL.append("     DECODE(SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID,'|') + 1, 6), " + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME) AS SORT1, ");
        sSQL.append("     (");
        sSQL.append("      SELECT E2.TEV_CEDITABLEFLG ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ) AS SORT2, ");
        sSQL.append("     (");
        sSQL.append("      SELECT E2.TEV_CADMINFLG ");
        sSQL.append("      FROM TMG_EVALUATER E2 ");
        sSQL.append("      WHERE E2.TEV_CCUSTOMERID = " + custId);
        sSQL.append("      AND   E2.TEV_CCOMPANYID  = " + compId);
        sSQL.append("      AND   E2.TEV_CEMPLOYEEID = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND   E2.TEV_CSECTIONID  = E.TEV_CSECTIONID");
        sSQL.append("      AND   E2.TEV_CGROUPID    = E.TEV_CGROUPID");
        sSQL.append("      AND   E2.TEV_DSTARTDATE <= " + baseDate);
        sSQL.append("      AND   E2.TEV_DENDDATE   >= " + baseDate);
        sSQL.append("     ) AS SORT3, ");
        sSQL.append("     TMG_F_GET_MP_WEIGHT(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + ") AS SORT4, ");
        sSQL.append("     E.TEV_CEMPLOYEEID AS SORT5, ");
        sSQL.append("     E.TEV_DSTARTDATE  AS SORT6  ");
        sSQL.append(" FROM ");
        sSQL.append("     TMG_GROUP G, ");
        sSQL.append("     TMG_EVALUATER E, ");
        sSQL.append("     HIST_DESIGNATION D");
        sSQL.append(" WHERE ");
        sSQL.append("     G.TGR_CCUSTOMERID      = " + custId);
        sSQL.append(" AND G.TGR_CCOMPANYID       = " + compId);
        sSQL.append(" AND G.TGR_CSECTIONID       = " + escDBString(evaluaterSettingParam.getSection()));
        sSQL.append(" AND G.TGR_DSTARTDATE      <= " + baseDate);
        sSQL.append(" AND G.TGR_DENDDATE        >= " + baseDate);
        sSQL.append(" AND E.TEV_CCUSTOMERID(+)   = G.TGR_CCUSTOMERID");
        sSQL.append(" AND E.TEV_CCOMPANYID(+)    = G.TGR_CCOMPANYID");
        sSQL.append(" AND E.TEV_CSECTIONID(+)    = G.TGR_CSECTIONID");
        sSQL.append(" AND E.TEV_CGROUPID(+)      = G.TGR_CGROUPID");
        sSQL.append(" AND D.HD_CCUSTOMERID_CK(+) = E.TEV_CCUSTOMERID");
        sSQL.append(" AND D.HD_CCOMPANYID_CK(+)  = E.TEV_CCOMPANYID");
        sSQL.append(" AND D.HD_CEMPLOYEEID_CK(+) = E.TEV_CEMPLOYEEID");
        sSQL.append(" AND D.HD_DSTARTDATE_CK(+) <= " + baseDate);
        sSQL.append(" AND D.HD_DENDDATE(+)      >= " + baseDate);
        sSQL.append(" AND D.HD_CIFKEYORADDITIONALROLE(+) = '0'");
        sSQL.append(" ORDER BY ");
        sSQL.append("     SORT1, ");            // ????????????ID
        sSQL.append("     SORT2 nulls last, "); // ????????????????????????????????????
        sSQL.append("     SORT3 nulls last, "); // ????????????????????????????????????
        sSQL.append("     SORT4 nulls last, "); // ???????????????????????????
        sSQL.append("     SORT5 nulls last, "); // ???????????????????????????
        sSQL.append("     SORT6 nulls last  "); // ????????????????????????

        return sSQL.toString();
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectEvaluaterNum(EvaluatorSettingParam evaluaterSettingParam) {

        StringBuilder sbSQL = new StringBuilder();

        String sYyyyMmDd      = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String sCustId        = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId        = escDBString(evaluaterSettingParam.getCompanyId());
        String sSecId         = escDBString(evaluaterSettingParam.getSection());
        String sGroupSequence = escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE);

        sbSQL.append(" SELECT ");
        sbSQL.append("     G.TGR_CGROUPID, ");
        sbSQL.append("     ( ");
        sbSQL.append("      SELECT ");
        sbSQL.append("          DECODE(COUNT(E.TEV_CGROUPID),0,1,COUNT(E.TEV_CGROUPID)) ");
        sbSQL.append("      FROM ");
        sbSQL.append("          TMG_EVALUATER E ");
        sbSQL.append("      WHERE ");
        sbSQL.append("          E.TEV_CCUSTOMERID  = G.TGR_CCUSTOMERID ");
        sbSQL.append("      AND E.TEV_CCOMPANYID   = G.TGR_CCOMPANYID ");
        sbSQL.append("      AND E.TEV_CSECTIONID   = G.TGR_CSECTIONID ");
        sbSQL.append("      AND E.TEV_CGROUPID     = G.TGR_CGROUPID ");
        sbSQL.append("     ) AS TEV_CEMPLOYEEID ");
        sbSQL.append(" FROM ");
        sbSQL.append("     TMG_GROUP G ");
        sbSQL.append(" WHERE ");
        sbSQL.append("     G.TGR_CCUSTOMERID   = " + sCustId);
        sbSQL.append(" AND G.TGR_CCOMPANYID    = " + sCompId);
        sbSQL.append(" AND G.TGR_CSECTIONID    = " + sSecId);
        sbSQL.append(" AND G.TGR_DSTARTDATE   <= " + sYyyyMmDd);
        sbSQL.append(" AND G.TGR_DENDDATE     >= " + sYyyyMmDd);
        sbSQL.append(" ORDER BY ");
        sbSQL.append("     DECODE( ");
        sbSQL.append("         SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID, '|') + 1, 6), "+sGroupSequence + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME ");
        sbSQL.append("     ) ");

        return sbSQL.toString();

    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectEvaluaterEmpNum(EvaluatorSettingParam evaluaterSettingParam) {

        StringBuilder sSQL = new StringBuilder();

        String baseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String lang     = escDBString(evaluaterSettingParam.getLanguage());

        sSQL.append(" SELECT ");
        sSQL.append("     R.TGR_CGROUPID, ");
        sSQL.append("     R.TEV_CEMPLOYEEID, ");
        sSQL.append("     COUNT(R.TEV_CEMPLOYEEID) ");
        sSQL.append(" FROM ");
        sSQL.append("     ( ");
        sSQL.append("      SELECT ");
        sSQL.append("          G.TGR_CGROUPID, ");
        sSQL.append("          E.TEV_CEMPLOYEEID ");
        sSQL.append("      FROM ");
        sSQL.append("          TMG_GROUP G, ");
        sSQL.append("          TMG_EVALUATER E, ");
        sSQL.append("          HIST_DESIGNATION D");
        sSQL.append("      WHERE ");
        sSQL.append("          G.TGR_CCUSTOMERID      = " + escDBString(evaluaterSettingParam.getCustomerId()));
        sSQL.append("      AND G.TGR_CCOMPANYID       = " + escDBString(evaluaterSettingParam.getCompanyId()));
        sSQL.append("      AND G.TGR_CSECTIONID       = " + escDBString(evaluaterSettingParam.getSection()));
        sSQL.append("      AND G.TGR_DSTARTDATE      <= " + baseDate);
        sSQL.append("      AND G.TGR_DENDDATE        >= " + baseDate);
        sSQL.append("      AND E.TEV_CCUSTOMERID(+)   = G.TGR_CCUSTOMERID");
        sSQL.append("      AND E.TEV_CCOMPANYID(+)    = G.TGR_CCOMPANYID");
        sSQL.append("      AND E.TEV_CSECTIONID(+)    = G.TGR_CSECTIONID");
        sSQL.append("      AND E.TEV_CGROUPID(+)      = G.TGR_CGROUPID");
        sSQL.append("      AND D.HD_CCUSTOMERID_CK(+) = E.TEV_CCUSTOMERID");
        sSQL.append("      AND D.HD_CCOMPANYID_CK(+)  = E.TEV_CCOMPANYID");
        sSQL.append("      AND D.HD_CEMPLOYEEID_CK(+) = E.TEV_CEMPLOYEEID");
        sSQL.append("      AND D.HD_DSTARTDATE_CK(+) <= " + baseDate);
        sSQL.append("      AND D.HD_DENDDATE(+)      >= " + baseDate);
        sSQL.append("      AND D.HD_CIFKEYORADDITIONALROLE(+) = '0'");
        sSQL.append("      ORDER BY ");
        sSQL.append("          DECODE(SUBSTR(G.TGR_CGROUPID, INSTR(G.TGR_CGROUPID,'|') + 1, 6), " + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0' || G.TGR_CGROUPNAME, '1' || G.TGR_CGROUPNAME), ");
        sSQL.append("          E.TEV_CEDITABLEFLG, ");
        sSQL.append("          TMG_F_GET_MP_WEIGHT(D.HD_CPOSTID_FK, " + baseDate + ", D.HD_CCUSTOMERID_CK, D.HD_CCOMPANYID_CK, " + lang + "), ");
        sSQL.append("          E.TEV_CEMPLOYEEID, ");
        sSQL.append("          E.TEV_DSTARTDATE   ");
        sSQL.append("     ) R");
        sSQL.append(" GROUP BY ");
        sSQL.append("     R.TGR_CGROUPID, ");
        sSQL.append("     R.TEV_CEMPLOYEEID ");

        return sSQL.toString();
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param sEditableFlg ???????????????
     * @param sAdminFlg    ?????????????????????
     * @return ?????????????????????????????????true????????????????????????false???
     */
    public boolean isEditLink(String sEditableFlg, String sAdminFlg,EvaluatorSettingParam params, boolean haveAuthority) {
        // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        return (isAuthority(sEditableFlg) && !isAuthority(sAdminFlg) && haveAuthority)
                || (params.isSiteTa() && isAuthority(sEditableFlg) && isAuthority(sAdminFlg));
    }

    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * @param evaluaterSettingParam
     * @param psMasterCode
     * @return String(??????????????????????????????????????????)
     */
    public String getEvasetMessage(PsDBBean psDBBean,EvaluatorSettingParam evaluaterSettingParam, String psMasterCode){
        PsResult psMesResult;
        Vector <String> vQuery = new Vector <String>();
        vQuery.add(buildSQL4SelectTmgVMgdEvasetMessage(evaluaterSettingParam));
        try {
            psMesResult = psDBBeanUtil.getValuesforMultiquery(vQuery, EvaluatorSettingConst.BEAN_DESC,psDBBean);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        Vector vsMessage = ((Vector)psMesResult.getResult().elementAt(0));
        // ??????Hashmap??????????????????????????????????????????????????????????????????????????????
        for (Iterator< Vector > ite = vsMessage.iterator(); ite.hasNext();) {
            Vector <String> vsEvasetMessage = ite.next();
            if(vsEvasetMessage.get(0).equals(psMasterCode)){
                return vsEvasetMessage.get(1);
            }
        }
        return "";

    }

    /**
     * ???????????????????????????
     * @return boolean ????????????????????????ture
     */
    private boolean isAuthority(String sAuthority) {
        return sAuthority != null && sAuthority.equals(TmgUtil.Cs_MGD_ONOFF_1);
    }

    /**
     * ??????????????????????????????????????????
     *
     * @return boolean ????????????
     */
    private boolean isSecEvaluater(String sSecEvalFlg) {
        return TmgUtil.Cs_MGD_ONOFF_1.equals(sSecEvalFlg);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     *
     * @param params
     * @return String SQL
     */
    private String buildSQL4SelectTmgVMgdEvasetMessage(EvaluatorSettingParam params) {
        StringBuilder sbSql = new StringBuilder();
        sbSql.append(" SELECT ");
        sbSql.append("     MGD_CMASTERCODE, ");
        sbSql.append("     MGD_CMSG ");
        sbSql.append(" FROM ");
        sbSql.append("     TMG_V_MGD_EVASET_MESSAGE ");
        sbSql.append(" WHERE ");
        sbSql.append("     MGD_CCUSTOMERID       = " + escDBString(params.getCustomerId()));
        sbSql.append(" AND MGD_CCOMPANYID_CK_FK  = " + escDBString(params.getCompanyId()));
        sbSql.append(" AND MGD_DSTART_CK        <= " + escDBString(params.getYYYYMMDD()));
        sbSql.append(" AND MGD_DEND             >= " + escDBString(params.getYYYYMMDD()));
        sbSql.append(" AND MGD_CLANGUAGE_CK      = " + escDBString(params.getLanguage()));
        return sbSql.toString();
    }

    /**
     * ???????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForDeleteGroupErrMsg(EvaluatorSettingParam params) {
        return " DELETE FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CMODIFIERUSERID    = " + escDBString(params.getEmployee())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + params.getAction())
                + " AND E.TER_CCUSTOMERID        = " + escDBString(params.getCustomerId())
                + " AND E.TER_CCOMPANYID         = " + escDBString(params.getCompanyId());
    }

    /**
     * ?????????????????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertGroupCheck(EvaluatorSettingParam evaluaterSettingParam) {

        // ???????????????????????????????????????????????????
        String sDBCustId   = escDBString(evaluaterSettingParam.getCustomerId()); // ???????????????
        String sDBCompId   = escDBString(evaluaterSettingParam.getCompanyId());  // ???????????????
        String sDBSecId    = escDBString(evaluaterSettingParam.getSection());    // ???????????????
        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());      // ?????????

        StringBuilder sbSQL = new StringBuilder();
        sbSQL.append(" INSERT INTO TMG_GROUP_CHECK ");
        sbSQL.append(" ( ");
        sbSQL.append("     TGR_CCUSTOMERID, ");
        sbSQL.append("     TGR_CCOMPANYID, ");
        sbSQL.append("     TGR_DSTARTDATE, ");
        sbSQL.append("     TGR_DENDDATE, ");
        sbSQL.append("     TGR_CMODIFIERUSERID, ");
        sbSQL.append("     TGR_DMODIFIEDDATE, ");
        sbSQL.append("     TGR_CMODIFIERPROGRAMID, ");
        sbSQL.append("     TGR_CSECTIONID, ");
        sbSQL.append("     TGR_CGROUPID, ");
        sbSQL.append("     TGR_CGROUPNAME, ");
        sbSQL.append("     TGR_OT_MONTLY_01, ");
        sbSQL.append("     TGR_OT_MONTLY_02, ");
        sbSQL.append("     TGR_OT_MONTLY_03, ");
        sbSQL.append("     TGR_OT_MONTLY_04, ");
        sbSQL.append("     TGR_OT_MONTLY_05, ");
        sbSQL.append("     TGR_OT_YEARLY_01, ");
        sbSQL.append("     TGR_OT_YEARLY_02, ");
        sbSQL.append("     TGR_OT_YEARLY_03, ");
        sbSQL.append("     TGR_OT_YEARLY_04, ");
        sbSQL.append("     TGR_OT_YEARLY_05, ");
        sbSQL.append("     TGR_OT_MONTHLY_COUNT, ");
        sbSQL.append("     TGR_HT_MONTLY_01, ");
        sbSQL.append("     TGR_HT_MONTLY_02, ");
        sbSQL.append("     TGR_HT_MONTLY_03, ");
        sbSQL.append("     TGR_HT_MONTLY_04, ");
        sbSQL.append("     TGR_HT_MONTLY_05, ");
        sbSQL.append("     TGR_OT_DAILY_01 ");
        sbSQL.append("     ,TGR_OT_MONTHLY_AVG");//??????????????????????????????
        sbSQL.append(" ) ");
        sbSQL.append(" SELECT ");
        sbSQL.append("     T1.TGR_CCUSTOMERID, ");
        sbSQL.append("     T1.TGR_CCOMPANYID, ");

        if (evaluaterSettingParam.getAction() != null && evaluaterSettingParam.getAction().equals(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP)) {
            // ???????????????STARTDATE?????????????????????
            sbSQL.append(" TMG_F_GET_ORG_STARTDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + sDBBaseDate + ") AS STARTDATE, ");
        } else {
            sbSQL.append(sDBBaseDate + " AS STARTDATE, ");
        }

        // ???????????????STARTDATE?????????????????????
        sbSQL.append(" TMG_F_GET_ORG_ENDDATE(T1.TGR_CCUSTOMERID, T1.TGR_CCOMPANYID, T1.TGR_CSECTIONID, " + sDBBaseDate + ") AS ENDDATE, ");
        sbSQL.append(escDBString(evaluaterSettingParam.getEmployee()) + ", ");
        sbSQL.append(" SYSDATE, ");
        sbSQL.append(escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", ");
        sbSQL.append(" T1.TGR_CSECTIONID, ");

        if (evaluaterSettingParam.getAction() != null && evaluaterSettingParam.getAction().equals(EvaluatorSettingConst.ACT_MAKEGROUP_CGROUP)) {
            // ?????? ????????????????????????(?????? ?????????????????????????????????)
            sbSQL.append(buildSQL4NextGroupId(sDBSecId, " T1.TGR_CCUSTOMERID ", " T1.TGR_CCOMPANYID ") + ", ");
        } else {
            // ?????? ????????????????????????(?????? ?????????????????????????????????)
            sbSQL.append(escDBString(evaluaterSettingParam.getGroup()) + ", ");
        }

        if (StrUtil.isNotBlank(evaluaterSettingParam.getGroupName())) {
            sbSQL.append(escDBString(evaluaterSettingParam.getGroupName()) + ", ");
        } else {
            sbSQL.append(" T2.TGR_CGROUPNAME, ");
        }
        sbSQL.append("     T2.TGR_OT_MONTLY_01, ");
        sbSQL.append("     T2.TGR_OT_MONTLY_02, ");
        sbSQL.append("     T2.TGR_OT_MONTLY_03, ");
        sbSQL.append("     T2.TGR_OT_MONTLY_04, ");
        sbSQL.append("     T2.TGR_OT_MONTLY_05, ");
        sbSQL.append("     T2.TGR_OT_YEARLY_01, ");
        sbSQL.append("     T2.TGR_OT_YEARLY_02, ");
        sbSQL.append("     T2.TGR_OT_YEARLY_03, ");
        sbSQL.append("     T2.TGR_OT_YEARLY_04, ");
        sbSQL.append("     T2.TGR_OT_YEARLY_05, ");
        sbSQL.append("     T2.TGR_OT_MONTHLY_COUNT, ");
        sbSQL.append("     T2.TGR_HT_MONTLY_01, ");
        sbSQL.append("     T2.TGR_HT_MONTLY_02, ");
        sbSQL.append("     T2.TGR_HT_MONTLY_03, ");
        sbSQL.append("     T2.TGR_HT_MONTLY_04, ");
        sbSQL.append("     T2.TGR_HT_MONTLY_05, ");
        sbSQL.append("     T2.TGR_OT_DAILY_01 ");
        sbSQL.append("     ,T2.TGR_OT_MONTHLY_AVG ");//??????????????????????????????
        sbSQL.append(" FROM TMG_GROUP T1, ");
        sbSQL.append("      TMG_GROUP T2 ");
        sbSQL.append(" WHERE T1.TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append(" AND   T1.TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append(" AND   T1.TGR_CSECTIONID  = " + sDBSecId);
        sbSQL.append(" AND   T1.TGR_CGROUPID    = " + escDBString(evaluaterSettingParam.getSection() + "|" + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE));
        sbSQL.append(" AND   T1.TGR_DSTARTDATE <= " + sDBBaseDate);
        sbSQL.append(" AND   T1.TGR_DENDDATE   >= " + sDBBaseDate);
        sbSQL.append(" AND   T2.TGR_CCUSTOMERID = " + sDBCustId);
        sbSQL.append(" AND   T2.TGR_CCOMPANYID  = " + sDBCompId);
        sbSQL.append(" AND   T2.TGR_CSECTIONID  = " + sDBSecId);
        if (StrUtil.isBlank(evaluaterSettingParam.getGroup())) {
            sbSQL.append(" AND   T2.TGR_CGROUPID = " + escDBString(evaluaterSettingParam.getSection() + "|" + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE));
        } else {
            sbSQL.append(" AND   T2.TGR_CGROUPID = " + escDBString(evaluaterSettingParam.getGroup()));
        }
        sbSQL.append(" AND   T2.TGR_DSTARTDATE <= " + sDBBaseDate);
        sbSQL.append(" AND   T2.TGR_DENDDATE   >= " + sDBBaseDate);
        return sbSQL.toString();
    }

    /**
     * ????????????TGR_CGROUPID?????????????????????????????????ID????????????+1??????????????????SQL???????????????
     *
     * @param psSecId  ???????????????
     * @param psCustId ???????????????
     * @param psCompId ???????????????
     * @return String SQL
     */
    private String buildSQL4NextGroupId(String psSecId, String psCustId, String psCompId){
        return " (T1.TGR_CSECTIONID || '|' || ( " +
                " SELECT " +
                " TO_CHAR(NVL(SUBSTR(MAX(TGR_CGROUPID), INSTR(MAX(TGR_CGROUPID),'|') + 1, " +
                " LENGTH(MAX(TGR_CGROUPID))), 0) + 1, 'FM099999') " +
                " FROM " +
                " TMG_GROUP " +
                " WHERE " +
                " TGR_CCUSTOMERID  =  " + psCustId +
                " AND TGR_CCOMPANYID   =  " + psCompId +
                " AND TGR_CSECTIONID   =  " + psSecId +
                " ) " +
                " ) ";
    }

    /**
     * ???????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertGroupErrMsg(EvaluatorSettingParam evaluaterSettingParam) {
        return " INSERT INTO TMG_ERRMSG ( "
                + " TER_CCUSTOMERID, "
                + " TER_CCOMPANYID, "
                + " TER_DSTARTDATE, "
                + " TER_DENDDATE, "
                + " TER_CMODIFIERUSERID, "
                + " TER_DMODIFIEDDATE, "
                + " TER_CMODIFIERPROGRAMID, "
                + " TER_CERRCODE, "
                + " TER_CLANGUAGE "
                + " ) VALUES ( "
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + "SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + "TMG_F_CHECK_GROUP(" + escDBString(evaluaterSettingParam.getEmployee()) + ", " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + "), "
                + escDBString(evaluaterSettingParam.getLanguage()) + ") ";
    }

    /**
     * ???????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertGroupTrigger(EvaluatorSettingParam evaluaterSettingParam) {
        return " INSERT INTO TMG_TRIGGER ( "
                + " TTR_CCUSTOMERID, "
                + " TTR_CCOMPANYID, "
                + " TTR_CEMPLOYEEID, "
                + " TTR_DSTARTDATE, "
                + " TTR_DENDDATE, "
                + " TTR_CMODIFIERUSERID, "
                + " TTR_DMODIFIEDDATE, "
                + " TTR_CMODIFIERPROGRAMID, "
                + " TTR_CPROGRAMID, "
                + " TTR_CPARAMETER1 "
                + " ) VALUES ( "
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(evaluaterSettingParam.getEmployee()) + ", "
                + " SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", "
                + escDBString(evaluaterSettingParam.getAction()) + ")";
    }

    /**
     * ???????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectGroupErrMsg(EvaluatorSettingParam evaluaterSettingParam) {
        return " SELECT "
                + "     TMG_F_GET_ERRORMESSAGE(E.TER_CERRCODE,"
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ", "
                + escDBString(evaluaterSettingParam.getLanguage())
                + "     ) "
                + " FROM "
                + "     TMG_ERRMSG E "
                + " WHERE "
                + "     E.TER_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId()) + " "
                + " AND E.TER_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId()) + " "
                + " AND E.TER_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND E.TER_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction());
    }

    /**
     * ???????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForDeleteGroupTrigger(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_TRIGGER T "
                + " WHERE "
                + "     T.TTR_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND T.TTR_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction())
                + " AND T.TTR_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND T.TTR_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }

    /**
     * ????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForDeleteGroupCheck(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_GROUP_CHECK G "
                + " WHERE "
                + "     G.TGR_CMODIFIERUSERID    = " + escDBString(evaluaterSettingParam.getEmployee())
                + " AND G.TGR_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction())
                + " AND G.TGR_CCUSTOMERID        = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND G.TGR_CCOMPANYID         = " + escDBString(evaluaterSettingParam.getCompanyId());
    }

    /**
     * ?????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForSelectGroupName(EvaluatorSettingParam params) {
        String sSQL = " SELECT "
                + "     G.TGR_CGROUPNAME "
                + " FROM "
                + "     TMG_GROUP G "
                + " WHERE "
                + "     G.TGR_CCUSTOMERID = " + escDBString(params.getCustomerId())
                + " AND G.TGR_CCOMPANYID  = " + escDBString(params.getCompanyId())
                + " AND G.TGR_CSECTIONID  = " + escDBString(params.getSection())
                + " AND G.TGR_CGROUPID    = " + escDBString(params.getGroup())
                + " AND G.TGR_DSTARTDATE <= " + SysUtil.transDateNullToDB(params.getYYYYMMDD())
                + " AND G.TGR_DENDDATE   >= " + SysUtil.transDateNullToDB(params.getYYYYMMDD());
        return sSQL;
    }

    private String buildSQLForSelectOverTimeLimit(EvaluatorSettingParam evaluaterSettingParam) {

        // ???????????????????????????????????????????????????
        String sDBCustId   = escDBString(evaluaterSettingParam.getCustomerId()); // ???????????????
        String sDBCompId   = escDBString(evaluaterSettingParam.getCompanyId());  // ???????????????
        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());      // ?????????

        String sbSQL = " SELECT " +
                "     OT_MONTLY_01, " +
                "     OT_MONTLY_02, " +
                "     OT_MONTLY_03, " +
                "     OT_MONTLY_04, " +
                "     OT_MONTLY_05, " +
                "     OT_YEARLY_01, " +
                "     OT_YEARLY_02, " +
                "     OT_YEARLY_03, " +
                "     OT_YEARLY_04, " +
                "     OT_YEARLY_05, " +
                "     OT_MONTHLY_COUNT, " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTLY_01', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTLY_02', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTLY_03', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTLY_04', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTLY_05', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_YEARLY_01', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_YEARLY_02', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_YEARLY_03', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_YEARLY_04', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_YEARLY_05', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTHLY_COUNT', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     OT_DAILY_01, " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_DAILY_01', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0') " +
                "     ,OT_MONTHLY_AVG " +//??????????????????????????????
                "     ,NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|OT_MONTHLY_AVG', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0') " +
                " FROM " +
                "     TABLE( " +
                "         TMG_F_GET_OVERTIMELIMT(" + sDBCustId + ", " + sDBCompId + ", " + escDBString(evaluaterSettingParam.getSection()) + ", " + escDBString(evaluaterSettingParam.getGroup()) + ", " + sDBBaseDate + ") " +
                "     ) ";
        return sbSQL;
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param evaluaterSettingParam
     * @return
     */
    private String buildSQLForSelectHolidayTimeLimit(EvaluatorSettingParam evaluaterSettingParam) {

        // ???????????????????????????????????????????????????
        String sDBCustId   = escDBString(evaluaterSettingParam.getCustomerId()); // ???????????????
        String sDBCompId   = escDBString(evaluaterSettingParam.getCompanyId());  // ???????????????
        String sDBBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());      // ?????????

        String sbSQL = " SELECT " +
                "     HT_MONTLY_01, " +
                "     HT_MONTLY_02, " +
                "     HT_MONTLY_03, " +
                "     HT_MONTLY_04, " +
                "     HT_MONTLY_05, " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|HT_MONTLY_01', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|HT_MONTLY_02', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|HT_MONTLY_03', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|HT_MONTLY_04', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0'), " +
                "     NVL(TMG_F_GET_MGD_C ('TMG_LIMIT|HT_MONTLY_05', " + sDBBaseDate + ", 4, " + sDBCustId + ", " + sDBCompId + ", 'ja'), 'TMG_ONOFF|0') " +
                " FROM " +
                "     TABLE( " +
                "         TMG_F_GET_HOLIDAYTIMELIMT("
                + sDBCustId + ", "
                + sDBCompId + ", "
                + escDBString(evaluaterSettingParam.getSection()) + ", "
                + escDBString(evaluaterSettingParam.getGroup()) + ", "
                + sDBBaseDate +
                "         ) " +
                "     ) ";
        return sbSQL;
    }

    /**
     * ?????????????????????????????????
     *
     * @param evaluaterSettingParam
     * @return
     */
    private String buildSQLForSelectOverTimeLimitSelf(EvaluatorSettingParam evaluaterSettingParam) {
        StringBuffer sbSQL = new StringBuffer();
        sbSQL.append(" SELECT ");
        sbSQL.append("     OT_MONTLY_01, ");
        sbSQL.append("     OT_MONTLY_02, ");
        sbSQL.append("     OT_MONTLY_03, ");
        sbSQL.append("     OT_MONTLY_04, ");
        sbSQL.append("     OT_MONTLY_05, ");
        sbSQL.append("     OT_YEARLY_01, ");
        sbSQL.append("     OT_YEARLY_02, ");
        sbSQL.append("     OT_YEARLY_03, ");
        sbSQL.append("     OT_YEARLY_04, ");
        sbSQL.append("     OT_YEARLY_05, ");
        sbSQL.append("     OT_MONTHLY_COUNT, ");
        sbSQL.append("     OT_DAILY_01 ");
        sbSQL.append("     ,OT_MONTHLY_AVG ");//??????????????????????????????
        sbSQL.append(" FROM ");
        sbSQL.append("     TABLE( ");
        sbSQL.append("         TMG_F_GET_OVERTIMELIMT_SELF("
                + escDBString(evaluaterSettingParam.getCustomerId()) + ", "
                + escDBString(evaluaterSettingParam.getCompanyId()) + ", "
                + escDBString(evaluaterSettingParam.getSection()) + ", "
                + escDBString(evaluaterSettingParam.getGroup()) + ", "
                + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()));
        sbSQL.append("         ) ");
        sbSQL.append("     ) ");
        return sbSQL.toString();
    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param params
     */
    private String buildSQLForSelectHolidayTimeLimitSelf(EvaluatorSettingParam params) {

        String sbSQL = " SELECT " +
                "     HT_MONTLY_01, " +
                "     HT_MONTLY_02, " +
                "     HT_MONTLY_03, " +
                "     HT_MONTLY_04, " +
                "     HT_MONTLY_05 " +
                " FROM " +
                "     TABLE( " +
                "         TMG_F_GET_HOLIDAYTIMELIMT_SELF("
                + escDBString(params.getCustomerId()) + ", "
                + escDBString(params.getCompanyId()) + ", "
                + escDBString(params.getSection()) + ", "
                + escDBString(params.getGroup()) + ", "
                + SysUtil.transDateNullToDB(params.getYYYYMMDD()) +
                "         ) " +
                "     ) ";
        return sbSQL;
    }

    /**
     * ??????????????????????????????????????????????????????
     * @param params
     */
    private String buildSQLForSelectGroupAttribute(EvaluatorSettingParam params) {
        return " SELECT "
                + "TGRA_CAUTOSET_EVA "
                + " FROM "
                + "TMG_GROUP_ATTRIBUTE "
                + " WHERE "
                + "     TGRA_CCUSTOMERID = " + escDBString(params.getCustomerId())
                + " AND TGRA_CCOMPANYID  = " + escDBString(params.getCompanyId())
                + " AND TGRA_CSECTIONID  = " + escDBString(params.getSection())
                + " AND TGRA_CGROUPID    = " + escDBString(params.getGroup())
                + " AND TGRA_DSTARTDATE <= " + SysUtil.transDateNullToDB(params.getYYYYMMDD())
                + " AND TGRA_DENDDATE   >= " + SysUtil.transDateNullToDB(params.getYYYYMMDD());
    }

    /**
     * ??????????????????????????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForDeleteEvaluater(EvaluatorSettingParam evaluaterSettingParam) {
        return "DELETE FROM "
                + "     TMG_EVALUATER E "
                + " WHERE "
                + "     E.TEV_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection())
                + " AND E.TEV_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup())
                + " AND E.TEV_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId())
                + " AND E.TEV_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId());
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForUpdateMovingEval(EvaluatorSettingParam evaluaterSettingParam) {
        return " UPDATE "
                + "     TMG_GROUP_MEMBER M "
                + " SET "
                + "     M.TGRM_CGROUPID = " + escDBString(evaluaterSettingParam.getRootGroup())
                + " WHERE "
                + "     M.TGRM_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND M.TGRM_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId())
                + " AND M.TGRM_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection())
                + " AND M.TGRM_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup())
                + " AND M.TGRM_DENDDATE   >= " + "TMG_F_GET_ORG_STARTDATE(M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID, M.TGRM_CSECTIONID, " +  SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ") "
                + " AND M.TGRM_DSTARTDATE <= " + "TMG_F_GET_ORG_ENDDATE(M.TGRM_CCUSTOMERID, M.TGRM_CCOMPANYID, M.TGRM_CSECTIONID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + ") ";
    }

    /**
     * ?????????????????????????????????????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeletePattern(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_PATTERN "
                + " WHERE "
                + "     TPA_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND TPA_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId())
                + " AND TPA_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection())
                + " AND TPA_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup())
                + " AND TPA_DSTARTDATE <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD())
                + " AND TPA_DENDDATE   >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
    }

    /**
     * ????????????????????????????????????????????????(??????)?????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeletePatternRest(EvaluatorSettingParam params) {
        return " DELETE FROM "
                + "     TMG_PATTERN_REST "
                + " WHERE "
                + "     TPR_CCUSTOMERID = " + escDBString(params.getCustomerId())
                + " AND TPR_CCOMPANYID  = " + escDBString(params.getCompanyId())
                + " AND TPR_CSECTIONID  = " + escDBString(params.getSection())
                + " AND TPR_CGROUPID    = " + escDBString(params.getGroup())
                + " AND TPR_DSTARTDATE <= " + SysUtil.transDateNullToDB(params.getYYYYMMDD())
                + " AND TPR_DENDDATE   >= " + SysUtil.transDateNullToDB(params.getYYYYMMDD());
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeletePatternApply(EvaluatorSettingParam evaluaterSettingParam) {
        return " DELETE FROM "
                + "     TMG_PATTERN_APPLIES "
                + " WHERE "
                + "     TPAA_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId())
                + " AND TPAA_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId())
                + " AND TPAA_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection())
                + " AND TPAA_CGROUPID    = " + escDBString(evaluaterSettingParam.getGroup())
                + " AND TPAA_DENDDATE   >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
    }

    /**
     * ?????????????????????????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForInsertGroupAttributeCheck(EvaluatorSettingParam evaluaterSettingParam,EditGroupDTO dto) {

        String sCustomerId = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompanyId = escDBString(evaluaterSettingParam.getCompanyId());
        String sSection = escDBString(dto.getSectionId());
        String sGroupId = dto.getSectionId() + "|" + TmgUtil.Cs_DEFAULT_GROUPSEQUENCE;
        String sYYYYMMDD = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());

        // ???????????????????????????????????????????????????????????????????????????
        String sDefaultFlg;
        if (dto.getAutoStart()) {
            sDefaultFlg = TmgUtil.Cs_MGD_ONOFF_1;
        } else {
            sDefaultFlg = TmgUtil.Cs_MGD_ONOFF_0;
        }

        return "INSERT INTO TMG_GROUP_ATTRIBUTE_CHECK " +
                "( " +
                "TGRA_CCUSTOMERID, " +
                "TGRA_CCOMPANYID, " +
                "TGRA_CSECTIONID, " +
                "TGRA_CGROUPID, " +
                "TGRA_DSTARTDATE, " +
                "TGRA_DENDDATE, " +
                "TGRA_CMODIFIERUSERID, " +
                "TGRA_DMODIFIEDDATE, " +
                "TGRA_CAUTOSET_EVA, " +
                "TGRA_CMODIFIERPROGRAMID, " +
                "TGRA_OT_MONTLY_01, " +
                "TGRA_OT_MONTLY_02, " +
                "TGRA_OT_MONTLY_03, " +
                "TGRA_OT_MONTLY_04, " +
                "TGRA_OT_MONTLY_05, " +
                "TGRA_OT_YEARLY_01, " +
                "TGRA_OT_YEARLY_02, " +
                "TGRA_OT_YEARLY_03, " +
                "TGRA_OT_YEARLY_04, " +
                "TGRA_OT_YEARLY_05, " +
                "TGRA_OT_MONTHLY_COUNT, " +
                "TGRA_HT_MONTLY_01, " +
                "TGRA_HT_MONTLY_02, " +
                "TGRA_HT_MONTLY_03, " +
                "TGRA_HT_MONTLY_04, " +
                "TGRA_HT_MONTLY_05, " +
                "TGRA_OT_DAILY_01 " +
                ",TGRA_OT_MONTHLY_AVG " +//??????????????????????????????
                ") " +
                "VALUES ( " +
                sCustomerId + ", " +
                sCompanyId + ", " +
                sSection + ", " +
                escDBString(sGroupId) + ", " +
                sYYYYMMDD + ", " +
                "TMG_F_GET_GROUP_ENDDATE(" + sCustomerId + ", " + sCompanyId + ", " + sSection + ", " + escDBString(sGroupId) + ", " + sYYYYMMDD + "), " +
                escDBString(evaluaterSettingParam.getEmployee()) + ", " +
                "SYSDATE, " +
                escDBString(sDefaultFlg) + ", " +
                escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + evaluaterSettingParam.getAction()) + ", " +
                escDBString(dto.getMonthlyOverTimeYellow()) + ", " +
                escDBString(dto.getMonthlyOverTimeOrange()) + ", " +
                escDBString(dto.getMonthlyOverTimePink()) + ", " +
                escDBString(dto.getMonthlyOverTimeRed()) + ", " +
                escDBString(dto.getMonthlyOverTimeBackUp()) + ", " +
                escDBString(dto.getYearlyOverTimeYellow()) + ", " +
                escDBString(dto.getYearlyOverTimeOrange()) + ", " +
                escDBString(dto.getYearlyOverTimePink()) + ", " +
                escDBString(dto.getYearlyOverTimeRed()) + ", " +
                escDBString(dto.getYearlyOverTimeBackUp()) + ", " +
                escDBString(dto.getCountOverTime()+"") + ", " +
                escDBString(dto.getMonthlyHolidayTimeLevel1()) + ", " +
                escDBString(dto.getMonthlyHolidayTimeLevel2()) + ", " +
                escDBString(dto.getMonthlyHolidayTimeLevel3()) + ", " +
                escDBString(dto.getMonthlyHolidayTimeLevel4()) + ", " +
                escDBString(dto.getMonthlyHolidayTimeLevel5()) + ", " +
                escDBString(dto.getDailyOverTime()) + ", " +
                escDBString(dto.getAvgOverTime()+"") + ")";
    }

    /**
     * ???????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForInsertGroupAttributeErrMsg(EvaluatorSettingParam params) {
        return " INSERT INTO TMG_ERRMSG ( "
                + " TER_CCUSTOMERID, "
                + " TER_CCOMPANYID, "
                + " TER_DSTARTDATE, "
                + " TER_DENDDATE, "
                + " TER_CMODIFIERUSERID, "
                + " TER_DMODIFIEDDATE, "
                + " TER_CMODIFIERPROGRAMID, "
                + " TER_CERRCODE, "
                + " TER_CLANGUAGE "
                + " ) VALUES ( "
                + escDBString(params.getCustomerId()) + ", "
                + escDBString(params.getCompanyId()) + ", "
                + TmgUtil.Cs_MINDATE + ", "
                + TmgUtil.Cs_MAXDATE + ", "
                + escDBString(params.getEmployee()) + ", "
                + "SYSDATE, "
                + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + params.getAction()) + ", "
                + "0,"
                + escDBString(params.getLanguage()) + ") ";
    }

    /**
     * ????????????????????????????????????SQL?????????
     *
     * @return String SQL
     */
    private String buildSQLForDeleteGroupAttributeCheck(EvaluatorSettingParam params) {
        return " DELETE FROM "
                + "     TMG_GROUP_ATTRIBUTE_CHECK G "
                + " WHERE "
                + "     G.TGRA_CMODIFIERUSERID    = " + escDBString(params.getEmployee())
                + " AND G.TGRA_CMODIFIERPROGRAMID = " + escDBString(EvaluatorSettingConst.BEAN_DESC + "_" + params.getAction())
                + " AND G.TGRA_CCUSTOMERID        = " + escDBString(params.getCustomerId())
                + " AND G.TGRA_CCOMPANYID         = " + escDBString(params.getCompanyId());
    }

    /**
     * ?????????????????????????????????SQL???????????????????????????????????????????????????????????????????????????
     *
     * @return String SQL
     */
    private String buildSQLForSelectGroupList(EvaluatorSettingParam evaluaterSettingParam) {
        return " SELECT " +
                "     G.TGR_CGROUPID, " +
                "     G.TGR_CGROUPNAME, " +
                "     TO_CHAR(TMG_F_GET_GROUP_ENDDATE(G.TGR_CCUSTOMERID, G.TGR_CCOMPANYID, G.TGR_CSECTIONID, G.TGR_CGROUPID, " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) + "), 'yyyy/mm/dd') as ENDDATE " +
                " FROM " +
                "     TMG_GROUP G " +
                " WHERE " +
                "     G.TGR_CSECTIONID  = " + escDBString(evaluaterSettingParam.getSection()) +
                " AND G.TGR_DSTARTDATE <= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) +
                " AND G.TGR_CCOMPANYID  = " + escDBString(evaluaterSettingParam.getCompanyId()) +
                " AND G.TGR_CCUSTOMERID = " + escDBString(evaluaterSettingParam.getCustomerId()) +
                " AND G.TGR_DENDDATE   >= " + SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD()) +
                " ORDER BY " +
                "     G.TGR_CSECTIONID, " +
                "     DECODE( SUBSTR(G.TGR_CGROUPID,INSTR(G.TGR_CGROUPID,'|') + 1, 6)," + escDBString(TmgUtil.Cs_DEFAULT_GROUPSEQUENCE) + ", '0'||G.TGR_CGROUPNAME, '1'||G.TGR_CGROUPNAME) ";
    }

    /**
     * ????????????????????????????????????SQL?????????
     * @return String SQL
     */
    private String buildSQLForSelectApprovalLevelList(EvaluatorSettingParam evaluaterSettingParam){
        String sBaseDate = SysUtil.transDateNullToDB(evaluaterSettingParam.getYYYYMMDD());
        String sCustId   = escDBString(evaluaterSettingParam.getCustomerId());
        String sCompId   = escDBString(evaluaterSettingParam.getCompanyId());
        String sLangage  = escDBString(evaluaterSettingParam.getLanguage());
        return " SELECT " +
                "     MGD_CMASTERCODE," +
                "     MGD_CGENERICDETAILDESC " +
                " FROM    " +
                "     MAST_GENERIC_DETAIL " +
                " WHERE" +
                "     MGD_CCUSTOMERID       = " + sCustId +
                " AND MGD_CCOMPANYID_CK_FK  = " + sCompId +
                " AND MGD_CGENERICGROUPID   = " + escDBString(TmgUtil.Cs_MG_TMG_APPROVAL_LEVEL) +
                " AND MGD_CLANGUAGE_CK      = " + sLangage +
                " AND MGD_DSTART_CK        <= " + sBaseDate +
                " AND MGD_DEND             >= " + sBaseDate +
                " ORDER BY" +
                "     MGD_NSPARENUM1";
    }

}
