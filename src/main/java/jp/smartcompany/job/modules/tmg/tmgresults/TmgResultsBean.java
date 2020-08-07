package jp.smartcompany.job.modules.tmg.tmgresults;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyCheckDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgDailyDetailCheckDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgErrmsgDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.*;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.*;
import jp.smartcompany.job.modules.tmg.util.CommonUI;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.boot.util.SysUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 勤怠登録・承認申請
 * ps.c01.tmg.TmgResults.TmgResultsBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgResultsBean {


    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * ITmgEmployeeAttributeService
     */
    private final ITmgEmployeeAttributeService iTmgEmployeeAttributeService;

    /**
     * ITmgMonthlyService
     */
    private final ITmgMonthlyService iTmgMonthlyService;

    /**
     * ITmgEmployeesService
     */
    private final ITmgEmployeesService iTmgEmployeesService;

    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;

    /**
     * ITmgDailyService
     */
    private final ITmgCompanyService iTmgCompanyService;

    /**
     * ITmgDailyDetailService
     */
    private final ITmgDailyDetailService iTmgDailyDetailService;

    /**
     * IMastOrganisationService
     */
    private final IMastOrganisationService iMastOrganisationService;

    /**
     * ITmgDailyCheckService
     */
    private final ITmgDailyCheckService iTmgDailyCheckService;

    /**
     * ITmgDailyDetailCheckService
     */
    private final ITmgDailyDetailCheckService iTmgDailyDetailCheckService;

    /**
     * ITmgErrmsgService
     */
    private final ITmgErrmsgService iTmgErrmsgService;

    /**
     * ITmgTriggerService
     */
    private final ITmgTriggerService iTmgTriggerService;

    /**
     * ITmgMastWorker4discretionaryService
     */
    private final ITmgMastWorker4discretionaryService iTmgMastWorker4discretionaryService;
    /**
     * ITmgDailyActionlogService
     */
    private final ITmgDailyActionlogService iTmgDailyActionlogService;

    private final IHistSuspensionService iHistSuspensionService;

    /**
     * TmgReferList
     */
    private TmgReferList referList = null;

    // サイト識別子
    /**
     * 勤怠入力サイト
     */
    public static final String SITE_TI = "TMG_INP";

    /**
     * 月別一覧画面表示
     */
    public static final String ACT_DISP_RMONTHLY = "ACT_DISP_RMONTHLY";

    /**
     * 日別登録画面表示
     */
    public static final String ACT_EDITINP_RDAILY = "ACT_EDITINP_RDAILY";

    /**
     * 月別一覧画面_月次承認
     */
    public static final String ACT_FIXED = "ACT_FIXED";

    /**
     * 月別一覧画面_月次承認解除
     */
    public static final String ACT_RESCISSION = "ACT_RESCISSION";

    /**
     * 勤務状況確認
     */
    public static final String TYPE_ITEM_WORK_STATUS = "TMG_ITEMS|WorkStatus";

    /**
     * 健康状態確認
     */
    public static final String TYPE_ITEM_HEALTH_STATUS = "TMG_ITEMS|HealthStatus";

    /**
     * 出張区分ドロップボックス
     */
    public static final String GROUPID_TMG_BUSINESS_TRIP = "TMG_BUSINESS_TRIP";

    // 属性コードの使用可否
    /**
     * 計算用項目は表示しない
     */
    public static final String ATTRIBUTE_ENABLE_ONLY = "1";

    /**
     * 非勤務
     */
    public static final String CATEGORY_NONDUTY = "TMG_CATEGORY|NonDuty";

    /**
     * 超過勤務
     */
    public static final String CATEGORY_OVERHOURS = "TMG_CATEGORY|Overhours";

    /**
     * YES
     */
    private final static String Cs_YES = "yes";

    /**
     * 未入力
     */
    public static final String STATUS_UNINPUT = "TMG_DATASTATUS|0";

    /**
     * 承認済
     */
    public static final String STATUS_APPROVED = "TMG_DATASTATUS|5";

    /**
     * LOG出力用ディスクリプタ
     */
    private final String BEAN_DESC = "TmgResults";

    /**
     * 表示対象日
     */
    private String gsDay = null;

    /**
     * 表示対象日
     */
    public void setDay(String s) {
        gsDay = s;
    }

    public String getDay() {
        return gsDay;
    }

    /**
     * 表示対象月
     */
    private String gsMonth = null;

    /**
     * 表示対象月
     */
    public void setMonth(String s) {
        gsMonth = s;
    }

    public String getMonth() {
        return gsMonth;
    }

    /**
     * 今日の日付(組織ツリーの基準日がセットされる為、必ずしもgsToday=システム日付ではない)
     */
    private String gsToday = null;

    /**
     * 今日の日付
     */
    public void setToday(String s) {
        gsToday = s;
    }

    public String getToday() {
        return gsToday;
    }

    /**
     * 今月
     */
    private String gsThisMonth = null;

    public void setThisMonth(String s) {
        gsThisMonth = s;
    }

    public String getThisMonth() {
        return gsThisMonth;
    }

    /**
     * 月別一覧画面を表示するメソッド
     */
    private void showMonthly(String action, PsDBBean psDBBean) {

//        TODO
        setMonth("2019/10/01");

        // 打刻反映処理
        execReflectionTimePunch(action, psDBBean);

        // 月次情報表示項目を取得しセット
        List<ItemVO> dispMonthlyItems = this.setDispMonthlyItems(psDBBean);

        List<String> monthlyItems = new ArrayList<String>();
        List<Map> monthlyTilteMapList = new ArrayList<Map>();
        for (ItemVO dispMonthlyItem : dispMonthlyItems) {

            monthlyItems.add(dispMonthlyItem.getMgdCsql() + " AS " + dispMonthlyItem.getMgdCcolumnkey());
            Map monthlyTilteMap = new HashMap();
            monthlyTilteMap.put("title", dispMonthlyItem.getMgdCheader());
            monthlyTilteMap.put("key", dispMonthlyItem.getMgdCcolumnkey());
            monthlyTilteMapList.add(monthlyTilteMap);
        }
//        modelMap.addAttribute("monthlyTilteMapList", monthlyTilteMapList);


        // 検索
        // 0 今日 TODO 削除待ち
        // vQuery.add(buildSQLForSelectDailyToday());

        // 1 月別
        HashMap monthlyMap = iTmgMonthlyService.buildSQLForSelectMonthly(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getMonth(),
                monthlyItems
        );
//        modelMap.addAttribute("monthlyMap", monthlyMap);

        // 月45時間を越える超勤の申請理由起動時処理
        // TODO 標準版ではないので、削除待ち
        // execOverHours45();


        // 勤務状況確認、健康状態確認の使用可否設定を行います。 TODO 削除待ち
        // setEditableWorkHealthChk();


        // 日次情報表示項目を取得しセット
        List<ItemVO> dispDailyItems = this.setDispDailyItems(psDBBean);

        List<Map> dailyTittleMapList = this.setMapList(dispDailyItems);
//        modelMap.addAttribute("dailyTittleMapList", dailyTittleMapList);

        List<String> dailyItems = new ArrayList<String>();
        for (ItemVO dispDailyItem : dispDailyItems) {
            dailyItems.add(dispDailyItem.getMgdCsql() + " AS " + dispDailyItem.getMgdCcolumnid());
        }

        // 2 日別
        List<HashMap> dailyMapList = iTmgDailyService.buildSQLForSelectDaily(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                getMonth(),
                TmgUtil.Cs_MGD_MANAGEFLG_0,
                dailyItems
        );

        // 3 休日
        String targetUser;
        String sectionid;
        String groupid;
        if (referList == null) {
            targetUser = psDBBean.getTargetUser();
            sectionid = "";
            groupid = "";
        } else {
            targetUser = "";
            sectionid = referList.getTargetSec();
            groupid = referList.getTargetGroup();
        }
        String year = getMonth().substring(0, 4);
        Map calendarMap = iTmgMonthlyService.buildSQLForSelectCalendar(psDBBean.getCustID(),
                psDBBean.getCompCode(), targetUser, sectionid, groupid, year, getMonth());

        int i = 1;
        for (Map dalyMap : dailyMapList) {
            dalyMap.put("TMG_HOLFLG", calendarMap.get("TCA_CHOLFLG" + StrUtil.fillBefore(String.valueOf(i), '0', 2)));
            i++;
        }
//        modelMap.addAttribute("dailyMapList", dailyMapList);

        // 4 前月・翌月
        MonthlyLinkVO monthlyLinkVO = iTmgMonthlyService.buildSQLForSelectMonthlyLink(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getMonth()
        );
//        modelMap.addAttribute("monthlyLinkVO", monthlyLinkVO);


        // TODO 画面未使用（削除待ち）
        // vQuery.add(buildSQLForHalfwayPaidHolidayInfo());    // 5 月中有給付与情報

        ///  6 勤務状況
        List<TmgEmployeeAttributeVO> workStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_WORK_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);
//        modelMap.addAttribute("workStatus", workStatus);

        //  7 健康状態
        List<TmgEmployeeAttributeVO> healthStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_HEALTH_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);
//        modelMap.addAttribute("healthStatus", healthStatus);

        //  8 月45時間を越える超勤の申請理由
        // TODO　標準版ではないので、削除待ち
//        List<TmgEmployeeAttributeVO> overhoursReason = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.escDBString(psDBBean.getCustID())
//                , psDBBean.escDBString(psDBBean.getCompCode())
//                , getToday()
//                , psDBBean.escDBString(psDBBean.getTargetUser())
//                , getMonth()
//                , TYPE_ITEM_HEALTH_STATUS
//                , TYPE_ITEM_OVERHOURS_REASON);
//         modelMap.addAttribute("overhoursReason", overhoursReason);

        //TODO　標準版ではないので、削除待ち
        //vQuery.add(buildSQLForSelectgetMgdDescriptions(GROUPID_OVERHOURS_REASON));       //  9 申請理由ドロップボックス

        // 10 裁量労働対象者判定
        //TODO　標準版ではないので、削除待ち
//        List<MgdAttributeVO> mgdAttributeList = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
//                psDBBean.escDBString(psDBBean.getCustID()),
//                psDBBean.escDBString(psDBBean.getCompCode()),
//                psDBBean.escDBString(psDBBean.getTargetUser()),
//                psDBBean.escDBString(psDBBean.getLanguage()),
//                psDBBean.escDBString(psDBBean.getSiteId()),
//                getDay(),
//                ATTRIBUTE_ENABLE_ONLY,
//                CATEGORY_DISCRETIONWORK);
//        modelMap.addAttribute("mgdAttributeList", mgdAttributeList);

        // 11 エフォート対象者判定
        //TODO　標準版ではないので、削除待ち
//        List<MgdAttributeVO> mgdAttributeEffortList = iMastGenericDetailService.buildSQLForSelectgetMgdAttributeEffort(
//                psDBBean.escDBString(psDBBean.getCustID()),
//                psDBBean.escDBString(psDBBean.getCompCode()),
//                psDBBean.escDBString(psDBBean.getTargetUser()),
//                psDBBean.escDBString(psDBBean.getLanguage()),
//                psDBBean.escDBString(psDBBean.getSiteId()),
//                getDay(),
//                getMonth(),
//                TYPE_ITEM_EFFORT,
//                TmgUtil.Cs_MGD_ONOFF_1,
//                ATTRIBUTE_ENABLE_ONLY,
//                CATEGORY_EFFORTMANAGEMENT);
//        modelMap.addAttribute("mgdAttributeEffortList", mgdAttributeEffortList);

        // 12 表示月遷移リスト情報取得
        List<DispMonthlyVO> dispMonthlyVOList = iTmgMonthlyService.buildSQLForSelectDispMonthlyList(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getToday()
        );
//        modelMap.addAttribute("dispMonthlyVOList", dispMonthlyVOList);

        // 勤怠承認・管理サイトは社員情報も
        if (!SITE_TI.equals(psDBBean.getSiteId())) {

            // TODO 画面未使用（削除待ち）
            //vQuery.add(buildSQLForSelectCountNotApproval());              // 13 未承認データ
            // 13 未承認データ
            //modelMap.addAttribute("buildSQLForSelectCountNotApproval", 1);

            // TODO 画面未使用（削除待ち）
            //vQuery.add(buildSQLForSelectCountDownLoadData()); // 14 ダウンロード可能データ
            // 14 ダウンロード可能データ
            //modelMap.addAttribute("buildSQLForSelectCountDownLoadData", 1);
            // TODO 画面未使用（削除待ち）
            // vQuery.add(buildSQLForSelectEmployee());          // 15 社員情報

            //16 対象社員について対象月の未承認日数
            String countNotApprovalDay = iTmgDailyService.buildSQLForSelectCountNotApprovalDay(
                    getMonth(),
                    psDBBean.getTargetComp(),
                    psDBBean.getTargetCust(),
                    psDBBean.getTargetUser(),
                    TmgUtil.Cs_MGD_DATASTATUS_9,
                    TmgUtil.Cs_MGD_DATASTATUS_5
            );

//            modelMap.addAttribute("countNotApprovalDay", countNotApprovalDay);
        }
    }

    private void showInp(String action, PsDBBean psDBBean) {

        // 打刻反映処理
        execReflectionTimePunch(action, psDBBean);

        // 日別
        DailyEditVO dailyEditVO = iTmgDailyService.buildSQLForSelectDailyEdit(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                getToday(),
                psDBBean.getSiteId(),
                psDBBean.getLanguage()
        );
//        modelMap.addAttribute("dailyEditVO", dailyEditVO);

        // 詳細:欠勤離籍以外
        List<DailyDetailVO> dailyDetail0List = iTmgDailyDetailService.buildSQLForSelectDetail(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage(),
                0,
                true
        );
//        modelMap.addAttribute("dailyDetail0List", dailyDetail0List);

        // 予定出社・退社時間の基準値
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(psDBBean.getCustID(), psDBBean.getCompCode(), getDay());
//        modelMap.addAttribute("companyVO", companyVO);

        // 出勤日判定用
        List<MgdCsparechar4VO> MgdCsparechar4VOList = iMastGenericDetailService.buildSQLSelectGetMgdCsparechar4(psDBBean.getCustID(), psDBBean.getCompCode());
//        modelMap.addAttribute("MgdCsparechar4VOList", MgdCsparechar4VOList);

        // 非勤務ドロップダウン用
        List<MgdAttributeVO> categoryNonduty = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_NONDUTY);
//        modelMap.addAttribute("categoryNonduty", categoryNonduty);

        // 超過勤務ドロップダウン用
        List<MgdAttributeVO> categoryOverhours = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_OVERHOURS);
//        modelMap.addAttribute("categoryOverhours", categoryOverhours);

        // 詳細：非勤務
        List<DetailNonDutyVO> detailNonDutyVOList = iTmgDailyService.buildSQLForSelectDetailNonDuty(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage()
        );

//        modelMap.addAttribute("detailNonDutyVOList", detailNonDutyVOList);

        // 詳細：超過勤務
        List<DetailOverhoursVO> detailOverhoursVOList = iTmgDailyService.buildSQLForSelectDetailOverhours(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage(),
                this.isShowOvertimeNotification(psDBBean)
        );

//        modelMap.addAttribute("detailOverhoursVOList", detailOverhoursVOList);

        // 出張区分ドロップダウン用
        List<GenericDetailVO> mgdDescriptions = iMastGenericDetailService.buildSQLForSelectgetMgdDescriptions(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                getDay(),
                GROUPID_TMG_BUSINESS_TRIP
        );
//        modelMap.addAttribute("mgdDescriptions", mgdDescriptions);

        // 裁量労働-勤務状況の状態を取得
        // 勤務状況
        List<TmgEmployeeAttributeVO> workStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(
                psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_WORK_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);
//        modelMap.addAttribute("workStatus", workStatus);

        // 就業区分マスタ
        List<GenericDetailVO> genericDetailVOList = iMastGenericDetailService.buildSQLForSelectGenericDetail(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage()
        );
//        modelMap.addAttribute("genericDetailVOList", genericDetailVOList);

        // 日次超勤限度時間取得
        String targetSec = (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()))
                ? this.referList.getTargetSec()
                : (String) psDBBean.getDept().get(0);

        LimitOfBasedateVO limitOfBasedateVO = iMastOrganisationService.buildSQLForLimitOfBasedate(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                targetSec,
                getDay()
        );
//        modelMap.addAttribute("limitOfBasedateVO", limitOfBasedateVO);


        // 超過勤務対象有無取得,
        String targetForOverTime = iTmgEmployeeAttributeService.buildSQLForSelectTargetForOverTime(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay()
        );
//        modelMap.addAttribute("targetForOverTime", targetForOverTime);

        // 休憩予定取得
        List<DailyDetailVO> dailyDetail3List = iTmgDailyDetailService.buildSQLForSelectDetail(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage(),
                3,
                true
        );
//        modelMap.addAttribute("dailyDetail3List", dailyDetail3List);

        // コメント欄の最大値取得
        String tmgVMgdMaxLengthCheck = iMastGenericDetailService.buildSQLForSelectTmgVMgdMaxLengthCheck(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getLanguage(),
                getDay(),
                TmgUtil.Cs_MGD_TMG_MAX_LENGTH_CHECK_TMG_MAX_LENGTH_CHECK);
//        modelMap.addAttribute("tmgVMgdMaxLengthCheck", tmgVMgdMaxLengthCheck);

        // 裁量労働-勤務状況の値を格納
        boolean isDiscretionWorkFixForDB = false;
        if (workStatus.size() > 0) {
            isDiscretionWorkFixForDB = workStatus.get(0).getTesCattribute().equals("TMG_ONOFF|1");
        }
//        modelMap.addAttribute("isDiscretionWorkFixForDB", isDiscretionWorkFixForDB);
    }


    /**
     * 差戻処理を行うメソッド
     *
     * @return なし
     */
    public GlobalResponse updateRemandsStatus(TmgResultsDto dto, PsDBBean psDBBean) {

        if(StrUtil.hasEmpty(dto.getTxtAction())){
            return GlobalResponse.error("アクションがない");
        }
        // トリガー削除
        this.buildSQLForDeleteTrigger(dto.getTxtAction(), psDBBean);

        // エラーチェック削除
        this.buildSQLForDeleteDailyCheck(psDBBean);

        // 日別
        DailyCheckDto dailyCheckDto = new DailyCheckDto();
        dailyCheckDto.setCustID(psDBBean.getCustID());
        dailyCheckDto.setCompCode(psDBBean.getCompCode());
        dailyCheckDto.setTargetUser(psDBBean.getTargetUser());
        dailyCheckDto.setDay(this.getDay());
        dailyCheckDto.setUserCode(psDBBean.getUserCode());
        dailyCheckDto.setMgdCbusinessTrip(dto.getSelMgdCbusinessTrip());
        dailyCheckDto.setTdaNopenR(dto.getTxtTdaNopenR());
        dailyCheckDto.setTdaNcloseR(dto.getTxtTdaNcloseR());

        dailyCheckDto.setAction(dto.getTxtAction());

        dailyCheckDto.setHoliday(dto.getHoliday());
        dailyCheckDto.setTdaCworkingidR(dto.getWorkingId());
        dailyCheckDto.setSiteId(dto.getPsSite());
        dailyCheckDto.setTdaCbosscommentR(dto.getTxtTdaCbosscommentR());
        dailyCheckDto.setTdaCowncommentR(dto.getTdaCowncommentR());
        iTmgDailyCheckService.buildSQLForInsertDailyCheck(dailyCheckDto);

        // トリガー追加
        iTmgTriggerService.buildSQLForInsertTrigger(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getUserCode(),
                dto.getTxtAction()
        );
        // トリガー削除
        this.buildSQLForDeleteTrigger(dto.getTxtAction(), psDBBean);

        // エラーチェック削除
        this.buildSQLForDeleteDailyCheck(psDBBean);
        return GlobalResponse.ok();
    }

    /**
     * 日別承認画面を表示するメソッド
     */
    private void showPerm(String action, PsDBBean psDBBean) {

        // 打刻反映処理
        execReflectionTimePunch(action, psDBBean);

        // 日別
        DailyEditVO dailyEditVO = iTmgDailyService.buildSQLForSelectDailyEdit(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                getToday(),
                psDBBean.getSiteId(),
                psDBBean.getLanguage()
        );
//        modelMap.addAttribute("dailyEditVO", dailyEditVO);
        // 詳細:欠勤離籍以外
        List<DailyDetailVO> dailyDetail0List = iTmgDailyDetailService.buildSQLForSelectDetail(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage(),
                0,
                false
        );
//        modelMap.addAttribute("dailyDetail0List", dailyDetail0List);
        // 社員情報
        //vQuery.add(buildSQLForSelectEmployee());
        // 就業区分マスタ
        List<GenericDetailVO> genericDetailVOList = iMastGenericDetailService.buildSQLForSelectGenericDetail(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage()
        );
//        modelMap.addAttribute("genericDetailVOList", genericDetailVOList);

        // 予定出社・退社時間の基準値
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(psDBBean.getCustID(), psDBBean.getCompCode(), getDay());
//        modelMap.addAttribute("companyVO", companyVO);

        // 出勤日判定用
        List<MgdCsparechar4VO> MgdCsparechar4VOList = iMastGenericDetailService.buildSQLSelectGetMgdCsparechar4(psDBBean.getCustID(), psDBBean.getCompCode());
//        modelMap.addAttribute("MgdCsparechar4VOList", MgdCsparechar4VOList);

        // 非勤務ドロップダウン用
        List<MgdAttributeVO> categoryNonduty = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_NONDUTY);
//        modelMap.addAttribute("categoryNonduty", categoryNonduty);

        // 超過勤務ドロップダウン用
        List<MgdAttributeVO> categoryOverhours = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_OVERHOURS);
//        modelMap.addAttribute("categoryOverhours", categoryOverhours);

        // 詳細：非勤務
        List<DetailNonDutyVO> detailNonDutyVOList = iTmgDailyService.buildSQLForSelectDetailNonDuty(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage()
        );

//        modelMap.addAttribute("detailNonDutyVOList", detailNonDutyVOList);

        // 詳細：超過勤務
        List<DetailOverhoursVO> detailOverhoursVOList = iTmgDailyService.buildSQLForSelectDetailOverhours(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage(),
                this.isShowOvertimeNotification(psDBBean)
        );
//        modelMap.addAttribute("detailOverhoursVOList", detailOverhoursVOList);

        // 出張区分ドロップダウン用
        List<GenericDetailVO> mgdDescriptions = iMastGenericDetailService.buildSQLForSelectgetMgdDescriptions(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                getDay(),
                GROUPID_TMG_BUSINESS_TRIP
        );
//        modelMap.addAttribute("mgdDescriptions", mgdDescriptions);

        // 日次超勤限度時間取得

        String targetSec = (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()))
                ? this.referList.getTargetSec()
                : (String) psDBBean.getDept().get(0);

        LimitOfBasedateVO limitOfBasedateVO = iMastOrganisationService.buildSQLForLimitOfBasedate(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                targetSec,
                getDay()
        );
//        modelMap.addAttribute("limitOfBasedateVO", limitOfBasedateVO);

        // 超過勤務対象有無取得,
        String targetForOverTime = iTmgEmployeeAttributeService.buildSQLForSelectTargetForOverTime(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay()
        );
//        modelMap.addAttribute("targetForOverTime", targetForOverTime);

        // 休憩予定取得
        List<DailyDetailVO> dailyDetail3List = iTmgDailyDetailService.buildSQLForSelectDetail(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage(),
                3,
                true
        );
//        modelMap.addAttribute("dailyDetail3List", dailyDetail3List);

    }

    /**
     * 月次(承認・解除)処理を行うメソッド
     */
    public GlobalResponse updateMonth(String Action,PsDBBean psDBBean) {

        // 月次承認
        // 初期化
        String sStatusApproved = "";
        String sModifierProgramId = "";

        if (ACT_FIXED.equals(Action)) {
            sModifierProgramId = BEAN_DESC + "_" + ACT_FIXED;
            sStatusApproved = STATUS_APPROVED;
        } else if (ACT_RESCISSION.equals(Action)) {
            sModifierProgramId = BEAN_DESC + "_" + ACT_RESCISSION;
            sStatusApproved = STATUS_UNINPUT;
        }
        try{
            iTmgMonthlyService.buildSQLForUpdateMonthly(psDBBean.getCustID(),
                    psDBBean.getCompCode(),
                    psDBBean.getTargetUser(),
                    getMonth(),
                    psDBBean.getUserCode(),
                    sModifierProgramId,
                    sStatusApproved);

        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }
        return GlobalResponse.ok();
    }

    /**
     * 月次承認時エラーチェックAjax
     */
    public void getAjaxCheckForMonthlyApproval(PsDBBean psDBBean) {

        // エラーメッセージ削除
        this.buildSQLForDeleteErrMsg(psDBBean);

        // エラーメッセージ追加
        iTmgErrmsgService.buildSQLForInsertErrMsgForMonthlyApproval(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getUserCode(),
                BEAN_DESC + "_" + psDBBean.getReqParam("txtAction"),
                psDBBean.getLanguage(),
                psDBBean.getReqParam("txtCEMPLOYEEID"),
                psDBBean.getReqParam("txtDYYYYMM")
        );

        // エラーメッセージ取得
        ErrMsgDto ErrMsgDto = iTmgErrmsgService.buildSQLForSelectErrMsg(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser());

        // エラーメッセージ削除
        this.buildSQLForDeleteErrMsg(psDBBean);
    }


    /**
     * 月次一覧、また日次登録（承認）画面表示時の打刻反映処理
     */
    public void execReflectionTimePunch(String action, PsDBBean psDBBean) {

        // メニューから初期の月次一覧画面表示時はアクションが未設定なので、月次一覧画面表示アクションを設定する。
        if (StrUtil.isEmpty(action)) {

            action = ACT_DISP_RMONTHLY;
        }

        // 打刻反映処理対象の期間を取得
        String stDate;        // 開始日
        String endDate;        // 終了日

        if (action.equals(ACT_DISP_RMONTHLY)) {

            // 表示月のカレンダークラス
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(getMonth().split("/")[0]),
                    Integer.parseInt(getMonth().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(getMonth().split("/")[2]), 0, 0);
            // 日付を月末日にする。
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

            // 運用月のカレンダークラス
            Calendar sysCal = Calendar.getInstance();
            sysCal.set(Integer.parseInt(TmgUtil.getSysdate().split("/")[0]),
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[2]), 0, 0);

            // 運用日 <= 表示月末日の場合、表示月が運用月なので運用日を打刻反映処理の期間終了日とする。（未来については打刻反映処理対象外なので）
            if (sysCal.compareTo(cal) <= 0) {

                // 運用月の月初～運用日までを対象期間とする。
                stDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.getMinimum(Calendar.DATE);
                endDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.get(Calendar.DATE);
            } else {

                // 表示月の月初～月末までを対象期間とする。
                stDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getMinimum(Calendar.DATE);
                endDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getActualMaximum(Calendar.DATE);
            }
        } else {
            // 日次画面を表示する場合は打刻反映処理の対象が表示日のみなので開始、終了日共に表示日とする。
            stDate = getDay();
            endDate = getDay();
        }

        // 打刻反映処理
        iTmgTriggerService.buildSQLForInsertTriggerByTimePunch(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getUserCode(),
                action, stDate, endDate);

        // トリガー削除
        this.buildSQLForDeleteTrigger(action, psDBBean);

    }

    /**
     * 日付関連情報を取得するメソッド
     */
    private void getDate(PsDBBean psDBBean) {

        TodayThisMonthVO todayThisMonthVO = iMastGenericDetailService.buildSQLForSelectDate();

        // 今日の日付
        setToday(todayThisMonthVO.getToday());
        setThisMonth(todayThisMonthVO.getThisMonth());

        // 表示対象月
        // TODO 画面パラメータ
        if (psDBBean.getReqParam("txtDYYYYMM") == null || "".equals(psDBBean.getReqParam("txtDYYYYMM"))) {  // 初回起動時
            setMonth(getThisMonth());
        } else {
            setMonth(psDBBean.getReqParam("txtDYYYYMM"));
        }

        // 表示対象日
        if (psDBBean.getReqParam("txtDYYYYMMDD") == null || "".equals(psDBBean.getReqParam("txtDYYYYMMDD"))) {
            setDay(getToday());
        } else {
            setDay(psDBBean.getReqParam("txtDYYYYMMDD"));
        }

    }

    /**
     * 一覧のタイトル様式設定
     *
     * @param mgdDispDailyItemsVOList
     * @return List<Map>
     */
    private List<Map> setMapList(List<ItemVO> mgdDispDailyItemsVOList) {

        List<Map> titles = new ArrayList<Map>();

        Map title = new HashMap();
        title.put(CommonUI.TITLE, "日");
        title.put(CommonUI.SLOT, "TDA_DYYYYMMDD_DD");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.WIDTH, CommonUI.WIDTH_25);

        titles.add(title);

        title = new HashMap();
        title.put(CommonUI.TITLE, "曜日");
        title.put(CommonUI.SLOT, "TDA_DYYYYMMDD_DY");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.WIDTH, CommonUI.WIDTH_25);
        titles.add(title);

        title = new HashMap();
        title.put(CommonUI.TITLE, "承認");
        title.put(CommonUI.SLOT, "TDA_CSTATUSFLG_NAME");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.WIDTH, CommonUI.WIDTH_25);
        titles.add(title);

        title = new HashMap();
        title.put(CommonUI.TITLE, "届");
        title.put(CommonUI.SLOT, "TDA_CNTFSTATUSFLG_NAME");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.WIDTH, CommonUI.WIDTH_25);
        titles.add(title);

        title = new HashMap();
        title.put(CommonUI.TITLE, "区分");
        title.put(CommonUI.SLOT, "TDA_CWORKINGID_R_NAME");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.MIN_WIDTH, CommonUI.WIDTH_36);
        titles.add(title);

        for (ItemVO mgdDispDailyItemsVO : mgdDispDailyItemsVOList) {
            title = new HashMap();
            title.put(CommonUI.TITLE, mgdDispDailyItemsVO.getMgdCheader());
            title.put(CommonUI.KEY, mgdDispDailyItemsVO.getMgdCcolumnid());
            title.put(CommonUI.MIN_WIDTH, CommonUI.WIDTH_36);
            //     title.put(CommonUI.WIDTH, mgdDispDailyItemsVO.getMgdNwidth());
            title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
            titles.add(title);
        }
        title = new HashMap();
        title.put(CommonUI.TITLE, "備考");
        title.put(CommonUI.KEY, "CCOMMENT");
        title.put(CommonUI.ALIGN, CommonUI.ALIGN_CENTER);
        title.put(CommonUI.MIN_WIDTH, CommonUI.WIDTH_70);
        titles.add(title);

        return titles;

    }

    /**
     * システムプロパティから値を取得後、超勤申請の事前事後登録情報を表示利用するかどうか判定し値を返却します
     *
     * @return boolean(true : 使用する 、 false : 使用しない)
     */
    public boolean isShowOvertimeNotification(PsDBBean psDBBean) {

        // TODO psDBBean.getSystemProperty 未実装

        String overtimeNotification = psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROP_NAME_TMG_SHOW_OVERTIMENOTIFICATION);

        if (Cs_YES.equalsIgnoreCase(overtimeNotification)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * TMG_DISPMONTHLYITEMSマスタより取得した月次情報のヘッダー・SQLをリストに格納する
     */
    public List<ItemVO> setDispMonthlyItems(PsDBBean psDBBean) {

        return iMastGenericDetailService.buildSQLForSelectTmgDispMonthlyItems(psDBBean.getCustID(), psDBBean.getCompCode(),
                psDBBean.getLanguage(), getDay());
    }

    /**
     * TMG_DISPDAILYITEMSマスタより取得した日次情報のヘッダー・SQL・表示幅をmapに格納する
     */
    public List<ItemVO> setDispDailyItems(PsDBBean psDBBean) {

        return iMastGenericDetailService.buildSQLForSelectTmgDispDailyItems(psDBBean.getCustID(), psDBBean.getCompCode(),
                psDBBean.getLanguage(), getDay());
    }

    /**
     * 月45時間を越える超勤の申請理由
     */
    public static final String TYPE_ITEM_OVERHOURS_REASON = "TMG_ITEMS|OverhoursReason";

    /**
     * 承認ボタン
     */
    // TODO 画面表示判断用
    public boolean approvalBtn = false;


    public static final String ACT_APPL_OVERHOURS = "ACT_APPL_OVERHOURS";     // 月別一覧画面_月45時間を越える超勤の申請

    /**
     * 起動時に「月45時間を超える超過勤務の申請理由」の「遅延理由」の存在チェックし、
     * レコードが存在しない場合は追加、存在する場合は更新する。
     *
     * @param psStatus 分岐
     */
    private void overHours45(String psStatus, PsDBBean psDBBean) {

        // システム日付の月より表示する月が小さいか ※月末を過ぎたかどうか
        if (psDBBean.getSysDate().compareTo(getMonth()) < 0) {
            return;
        }

        List<TmgEmployeeAttributeVO> tmgEmployeeAttributeVOList = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_OVERHOURS_REASON
                , TYPE_ITEM_OVERHOURS_REASON);

        int attributeCount = tmgEmployeeAttributeVOList.size();
        String attribute = "";
        if (attributeCount > 0) {
            attribute = tmgEmployeeAttributeVOList.get(0).getTesCattribute();
        }

        // 表示する月の超勤時間が45時間を超えているか
        if (this.monthSumOverWork(psDBBean) <= OVER_WORK_45) {
            return;
        } else if (TmgUtil.Cs_MGD_ONOFF_1.equals(attribute)) { // 申請 却下・未申請

            approvalBtn = false; // 押下不可
        } else {
            approvalBtn = true;  // 押下可能
        }

        // レコード追加
        if (attributeCount == 0) {

            iTmgEmployeeAttributeService.buildSQLForInsertTmgEmployeeAttributeActDisp(TYPE_ITEM_OVERHOURS_REASON
                    , "BEAN_DESC_" + psStatus
                    , psDBBean.getCustID()
                    , psDBBean.getCompCode()
                    , psDBBean.getTargetUser()
                    , getMonth()
                    , psDBBean.getUserCode()
                    , TmgUtil.Cs_MGD_ONOFF_0
                    , TmgUtil.Cs_DELAY_REASON_VIEW
                    , TmgUtil.Cs_MAIL_UNSEND);
        }

        // レコード更新
        // データが存在する、かつ、画面上からの操作としては未登録の場合（画面初期表示の自動INSERT以降、いじられてない場合）
        if (attributeCount > 0 && TmgUtil.Cs_MGD_ONOFF_0.equals(attribute)) {
            iTmgEmployeeAttributeService.buildSQLForUpdateTmgEmployeeAttribute(psDBBean.getUserCode()
                    , psDBBean.getCustID()
                    , psDBBean.getCompCode()
                    , psDBBean.getTargetUser()
                    , getMonth()
                    , TYPE_ITEM_OVERHOURS_REASON
                    , BEAN_DESC + "_" + ACT_APPL_OVERHOURS
                    , TmgUtil.Cs_DELAY_REASON_VIEW
                    , TmgUtil.Cs_MAIL_UNSEND);
        }
    }

    /**
     * 月45時間を超える超過勤務
     */
    public static final double OVER_WORK_45 = 45;

    /**
     * 表示する月の合計超勤時間
     *
     * @return 合計超勤時間
     */
    private double monthSumOverWork(PsDBBean psDBBean) {

        // 当メソッド内の処理をTMG_F_GET_SUM_OVERTIMEを使用する様に一新

        List<String> OverTimeList = iTmgMonthlyService.buildSQLForSelectMonthlyOverTime(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , psDBBean.getTargetUser()
                , this.getMonth()
        );

        if (OverTimeList.size() > 0) {
            return Double.parseDouble(OverTimeList.get(0));
        } else {
            return 0.0;
        }

    }

    /**
     * 申請
     */
    // TODO 画面表示用　削除待ち
    public boolean overWorkApplication = false;
    /**
     * 再申請
     */
    // TODO 画面表示用 削除待ち
    public boolean overWorkReApplication = false;
    /**
     * 取下
     */
    // TODO 画面表示用　削除待ち
    public boolean overWorkWithdrawal = false;
    /**
     * 遅延理由
     */
    // TODO 画面表示用　削除待ち
    public boolean overWorkDelayReason = false;

    /**
     * 起動時にボタン・テキストエリア項目を表示か非表示かを判定
     */
    private void overHours45ViewCheck(PsDBBean psDBBean) {

        List<TmgEmployeeAttributeVO> vec = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_OVERHOURS_REASON
                , TYPE_ITEM_OVERHOURS_REASON);

        int attributeCount = vec.size(); // 検索数

        if (attributeCount == 0) {
            overWorkApplication = true; // 申請ボタン表示用
            return;
        }

        String attribute = vec.get(0).getTesCattribute(); // 申請か却下・未申請
        String sparenum1 = String.valueOf(vec.get(0).getNsparenum1()); // 予備数値1

        if (TmgUtil.Cs_MGD_ONOFF_0.equals(attribute)) {

            overWorkApplication = true; // 申請ボタン表示用

        } else if (TmgUtil.Cs_MGD_ONOFF_1.equals(attribute)) {

            overWorkReApplication = true; // 再申請ボタン表示用
            overWorkWithdrawal = true; // 取下ボタン表示用
        }

        if (TmgUtil.Cs_DELAY_RESON_ONOFF_1.equals(sparenum1)) {
            overWorkDelayReason = true; // 遅延理由テキストエリア表示用
        }
    }

    /**
     * 勤務状況確認欄の使用判定変数
     */
    private boolean isWorkChkFlg = false;

    /**
     * 勤務状況確認欄の使用判定変数のsetter
     *
     * @param flg
     */
    public void setIsWorkChkFlg(boolean flg) {
        isWorkChkFlg = flg;
    }

    /**
     * 勤務状況確認欄の使用判定変数のgetter
     *
     * @return 勤務状況確認欄の使用判定結果
     */
    public boolean getIsWorkChkFlg() {
        return isWorkChkFlg;
    }

    /**
     * 健康状態確認欄の使用判定変数
     */
    private boolean isHealthChkFlg = false;

    /**
     * 健康状態確認欄の使用判定変数のsetter
     *
     * @param flg
     */
    public void setIsHealthChkFlg(boolean flg) {
        isHealthChkFlg = flg;
    }

    /**
     * 健康状態確認欄の使用判定変数のgetter
     *
     * @return 健康状態確認欄の使用判定結果
     */
    public boolean getIsHealthChkFlg() {
        return isHealthChkFlg;
    }

    /**
     * 勤務状況確認欄、健康状態確認欄の使用可否設定を行います。
     */
    private void setEditableWorkHealthChk(PsDBBean psDBBean) {


        // 表示対象職員の顧客コード
        String custId = psDBBean.getTargetCust();
        // 表示対象職員の法人コード
        String compId = psDBBean.getTargetComp();
        // 表示対象職員の職員番号
        String empId = psDBBean.getTargetUser();
        // 言語区分
        String lang = psDBBean.getLanguage();

        IsWorkHealthChkVO IsWorkHealthChkVO = iTmgEmployeesService.buildIsWorkHealthChk(custId, compId, empId, lang, getMonth());

        // 勤務状況一覧の使用可否設定が"1"ならば、使用可として判定
        if (1 == IsWorkHealthChkVO.getWorkChk()) {
            setIsWorkChkFlg(true);
        } else {
            setIsWorkChkFlg(false);
        }

        // 健康状態一覧の使用可否設定が"1"ならば、使用可として判定
        if (1 == IsWorkHealthChkVO.getHealthChk()) {
            setIsHealthChkFlg(true);
        } else {
            setIsHealthChkFlg(false);
        }
    }

    /**
     * 勤務状況確認の確定ボタンを表示するか
     */
    public boolean isKinmujokyoKakunin() {
        boolean retval = false;

        //表示中の月
        String sMonth = getMonth();
        Date dMonth;
        try {
            dMonth = DateFormat.getDateInstance().parse(sMonth);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        Calendar cMonth = new GregorianCalendar();
        cMonth.setTime(dMonth);

        //今日
        String sToday = getToday();
        Date dToday;
        try {
            dToday = DateFormat.getDateInstance().parse(sToday);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        Calendar cToday = new GregorianCalendar();
        cToday.setTime(dToday);

        //表示開始の日付
        int iMonth = cMonth.get(Calendar.MONTH) + 1;
        iMonth = ((int) ((iMonth + 2) / 3)) * 3;

        Calendar cDisp = new GregorianCalendar();
        cDisp.setTime(dMonth);
        cDisp.set(cMonth.get(Calendar.YEAR), iMonth - 1, 1);
        cDisp.set(Calendar.DAY_OF_MONTH, cDisp.getActualMaximum(Calendar.DAY_OF_MONTH) - 7);

        retval = cToday.compareTo(cDisp) >= 0 ? true : false;

        return retval;
    }

    /**
     * 健康状況確認の確定ボタンを表示するか
     */
    public boolean isKenkojotaiKakunin() {
        boolean retval = false;

        //表示中の月
        String sMonth = getMonth();
        Date dMonth;
        try {
            dMonth = DateFormat.getDateInstance().parse(sMonth);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        Calendar cMonth = new GregorianCalendar();
        cMonth.setTime(dMonth);

        //今日
        String sToday = getToday();
        Date dToday;
        try {
            dToday = DateFormat.getDateInstance().parse(sToday);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
        Calendar cToday = new GregorianCalendar();
        cToday.setTime(dToday);

        //表示開始の日付
        int iMonth = cMonth.get(Calendar.MONTH) + 1;
        iMonth = ((int) ((iMonth + 5) / 6)) * 6;

        Calendar cDisp = new GregorianCalendar();
        cDisp.setTime(dToday);
        cDisp.set(cMonth.get(Calendar.YEAR), iMonth - 1, 1);
        cDisp.set(Calendar.DAY_OF_MONTH, cDisp.getActualMaximum(Calendar.DAY_OF_MONTH) - 7);

        retval = cToday.compareTo(cDisp) >= 0 ? true : false;

        return retval;
    }

    /** 日付形式1 */
    private static final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";
    private static final String FORMAT_SLASH      = "/";
    private static final int BASEDATE = 1; // 勤務状況確認、健康状態確認の基準月

    private static final int CYCLEDATE_WORK = 3; // 勤務状況確認

    private static final int CYCLEDATE_HEALTH = 6; // 健康状態確認

    private static final int CYCLEDATE_OVERHOURS = 0; // 超勤申請

    /**
     * 引数で指定されたyyyy/mm/dd形式の日付文字列を「/」で分割しint型配列に格納します。
     * @param date 基準日 (「yyyy/mm/dd」形式の文字列)
     * @exception NumberFormatException
     * @exception ArrayIndexOutOfBoundsException
     */
    public int[] devideDateFormatString(String date)
            throws NumberFormatException, ArrayIndexOutOfBoundsException {

        int[] dates = new int[3]; //日付値格納配列

        try {
            StringTokenizer st = new StringTokenizer(date, FORMAT_SLASH);
            for(int i = 0; st.hasMoreTokens(); i++) {
                dates[i] = Integer.parseInt(st.nextToken());
            }
        } catch (NumberFormatException e) {

        } catch (ArrayIndexOutOfBoundsException e) {

        }
        return dates;
    }
    /**
     * 勤務状況確認、健康状態確認の適応期間開始日取得
     * @param   psMonth 対象月
     * @param   psType 勤務状況確認、健康状態確認
     * @param   pnTarget 取得対象(1:開始日、2：終了日)
     * @return  適応開始日
     */
    private String getCycleDay(String psMonth, String psType, int pnTarget){
        // 日付文字列を分割
        int nDates[] = devideDateFormatString(psMonth);
        // 月のみ取得
        int sMonth = nDates[1];

        // 適応期間の間隔を設定
        int nCycleDate = 0;
        // 勤務状況の場合
        if(psType.equals(TYPE_ITEM_WORK_STATUS)){
            if(CYCLEDATE_WORK > 0){
                nCycleDate = CYCLEDATE_WORK - 1;
            }
        }
        // 健康状態の確認
        if(psType.equals(TYPE_ITEM_HEALTH_STATUS)){
            if(CYCLEDATE_HEALTH > 0){
                nCycleDate = CYCLEDATE_HEALTH - 1;
            }
        }
        // 超勤の申請理由
        if(psType.equals(TYPE_ITEM_OVERHOURS_REASON)){
            if(CYCLEDATE_OVERHOURS > 0){
                nCycleDate = CYCLEDATE_OVERHOURS - 1;
            }
        }

        // どの範囲に含まれているかチェック
        int nStartDate = BASEDATE;
        int nEndDate = nStartDate + nCycleDate;
        // 適応される期間を取得
        while(nEndDate <= 12){
            if( nStartDate <= sMonth && sMonth <= nEndDate){
                break;
            }
            nStartDate = nEndDate + 1;
            nEndDate = nStartDate + nCycleDate;
        }

        // 開始月を返す
        if(pnTarget == 1){
            return getFormatDate(nDates[0],nStartDate,1);
            // 終了月を返す
        }else if(pnTarget == 2){
            return getFormatDate(nDates[0],nEndDate,1);
        }

        // 当てはまらない場合
        return getFormatDate(nDates[0],sMonth,nDates[2]);
    }
    /**
     * 引数で指定された年月日をyyyy/mm/ddの形式で返します。
     * @param year 年
     * @param month 月
     * @param day 日
     * @return String 年月日(yyyy/mm/dd)
     */
    public String getFormatDate(int year, int month, int day) {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_DATE_TYPE1);

        try {

            Date objDate = null;

            Calendar calendar = Calendar.getInstance();

            calendar.set(year,month-1,day); // カレンダーに設定

            objDate = calendar.getTime();

            return df.format(objDate);

        } catch (Exception e) {}

        return null;
    }

    /**
     * 勤務状況確認の開始月
     */
    public int getKinmujokyoEnd(){
        int retval = 0;

        //表示中の月
        String sMonth = getMonth();
        Date   dMonth;
        try{
            dMonth = DateFormat.getDateInstance().parse(sMonth);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return 0;
        }
        Calendar cMonth = new GregorianCalendar();
        cMonth.setTime(dMonth);

        //表示開始の日付
        int iMonth = cMonth.get(Calendar.MONTH) + 1;
        iMonth = ((int)((iMonth + 2) / 3)) * 3;

        retval = iMonth;

        return retval;
    }

    /**
     * メイン処理
     */
    public void execute(PsDBBean psDBBean) {

        // 日付関連取得
        getDate(psDBBean);

        // 組織ツリー情報での再構築
        this.setOrganizationTreeInf(psDBBean);

        // URL(psTargetUser部分)を書換えられた場合の対策

        if (TmgUtil.Cs_SITE_ID_TMG_INP.equals(psDBBean.getSiteId())) {
            psDBBean.setTargetUser(psDBBean.getUserCode());
        }
        try {

            // ■初期表示時：
            //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
            //   勤怠登録承認コンテンツの参照権限をチェックする。
            //   参照権限がある場合は、問題なく勤怠登録承認を表示する。
            //   　しかし、参照権限が無い場合は1ヶ月遡った月の参照権限をチェックする。
            //   1ヶ月遡った月の参照権限があればその月の勤怠登録承認を表示し、
            //   1ヶ月遡った月の参照権限も無い場合は画面に「参照できる社員が存在しません」(文言変更有り)
            //   メッセージを画面へ表示する。
            // ■初期表示以外：
            //   選択した組織、(もしくはグループ)の対象年月時点での勤怠登録承認コンテンツの参照権限をチェックする。
            //   権限があれば問題なく勤怠登録承認を表示する。
            //   権限が無い場合は画面に「参照できる社員が存在しません」(文言変更有り)
            //   メッセージを画面へ表示する。
            //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
            //     権限が無いのと同じ扱いとする。
            // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
                String sAction = psDBBean.getReqParam("txtAction");
                String sTargetSec = getReferList().getTargetSec();
                // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
                // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
                if ((TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) && (sAction == null || sAction.length() == 0))
                        || (TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && !(sTargetSec == null || sTargetSec.length() == 0) && (sAction == null || sAction.length() == 0))) {
                    // 参照権限チェック(現在時点での年月)
                    if (getReferList().existsAnyone(getThisMonth()) && getReferList().isThereSomeEmployees(getThisMonth())) {
                        setAuthorityMonth(CB_CAN_REFER);

                        // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
                    } else {
                        String sPrevMonth = TmgUtil.getFirstDayOfMonth(getThisMonth(), PARAM_PREV_MONTH);

                        // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
                        setReferList(sPrevMonth, TmgReferList.TREEVIEW_TYPE_EMP, psDBBean);

                        // 参照権限の設定:
                        // 初期表示時の対象年月の時点の参照権限が無い場合に、
                        // 1ヶ月過去の参照権限を判定し参照権限がある場合は1ヶ月過去のシートを参照する。
                        // 権限が無い場合は、参照できない。
                        if (getReferList().existsAnyone(sPrevMonth) && getReferList().isThereSomeEmployees(sPrevMonth)) {

                            // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
                            // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
                            // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
                            // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
                            // 「対象年月が現在の年月の場合」という条件を実装しています。
                            if (getThisMonth().equals(getMonth())) {
                                // 対象年月を1ヶ月過去に設定します
                                setMonth(sPrevMonth);
                            }
                            setAuthorityMonth(CB_CAN_REFER);
                        } else {
                            // 対象年月を元に戻します
                            setReferList(getMonth(), TmgReferList.TREEVIEW_TYPE_EMP, psDBBean);

                            setAuthorityMonth(CB_CANT_REFER);
                        }
                    }

                    // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                    // 翌月の権限があればリンク「>」を画面に表示する。
                    // 権限が無い場合は「>」を表示しない。
                    // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
                    //   権限が無いのと同じ扱いとする。
                    String sNextMonth = TmgUtil.getFirstDayOfMonth(getMonth(), PARAM_NEXT_MONTH);
                    if (getReferList().existsAnyone(sNextMonth) && getReferList().isThereSomeEmployees(sNextMonth)) {
                        setAuthorityNextMonth(CB_CAN_REFER);
                    } else {
                        setAuthorityNextMonth(CB_CANT_REFER);
                    }

                    // 初期表示時以外
                    // ※組織を選択していないときは権限チェックを行わない。
                    // 　勤怠管理サイトで組織未選択時に権限チェックを行うとえらーで落ちてしまうので
                    // 　それを防ぐ為に「組織を選択しているとき」という条件を実装しています。
                } else if (!(sTargetSec == null || sTargetSec.length() == 0)) {
                    // 参照権限の判定：設定(当月分)
                    // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
                    String sPrevMonth = TmgUtil.getFirstDayOfMonth(getThisMonth(), PARAM_PREV_MONTH);
                    if ((getReferList().existsAnyone(getThisMonth()) && getReferList().isThereSomeEmployees(getThisMonth())) ||
                            (getReferList().existsAnyone(sPrevMonth) && getReferList().isThereSomeEmployees(sPrevMonth))) {
                        setAuthorityMonth(CB_CAN_REFER);
                    } else {
                        setAuthorityMonth(CB_CANT_REFER);
                    }

                    // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                    // 翌月の権限があればリンク「>」を画面に表示する。
                    // 権限が無い場合は「>」を表示しない。
                    // ※また、権限はあるが選択している組織(もしくはグループ)に所属している社員が存在しない場合も
                    //   権限が無いのと同じ扱いとする。
                    String sNextMonth = TmgUtil.getFirstDayOfMonth(getMonth(), PARAM_NEXT_MONTH);
                    if (getReferList().existsAnyone(sNextMonth) && getReferList().isThereSomeEmployees(sNextMonth)) {
                        setAuthorityNextMonth(CB_CAN_REFER);
                    } else {
                        setAuthorityNextMonth(CB_CANT_REFER);
                    }

                }
                // その他のサイトの場合
            } else {
                setAuthorityMonth(CB_CAN_REFER);
                setAuthorityNextMonth(CB_CAN_REFER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * リクエストキー - 再表示ボタン使用判定用
     */
    private static final String TREEVIEW_KEY_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";

    /**
     * 組織情報再構築するメソッド
     *
     * @param
     * @return なし
     * @throws
     */
    private void setOrganizationTreeInf(PsDBBean psDBBean) {

        try {
            // 表示対象者
            if (!SITE_TI.equals(psDBBean.getSiteId())) {

                // 勤怠承認・管理サイト
                setReferList(TmgReferList.TREEVIEW_TYPE_EMP, psDBBean);   // 汎用参照リスト
                psDBBean.setTargetUser(referList.getTargetEmployee());
                // 当日・当月日付の情報を再格納する
                if (referList.getRecordDate() != null) {
                    setToday(referList.getRecordDate());
                    setThisMonth(TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), TmgUtil.Cs_PARAM_THIS_MONTH));
                }
                // 組織ツリー基準日情報チェック(再表示ボタンが押されたかも判定)
                if (referList.getRecordDate() == null ||
                        (psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG) == null || "".equals(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG)))) {
                    // 表示対象月
                    if (psDBBean.getReqParam("txtDYYYYMM") == null || "".equals(psDBBean.getReqParam("txtDYYYYMM"))) {  // 初回起動時
                        setMonth(getThisMonth());
                    } else {
                        setMonth(psDBBean.getReqParam("txtDYYYYMM"));
                    }
                } else {
                    // 組織ツリーの基準日を使用
                    setMonth(TmgUtil.getFirstDayOfMonth(referList.getRecordDate(), TmgUtil.Cs_PARAM_THIS_MONTH));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return;

    }

    /**
     * 1ヵ月後
     */
    private static final int PARAM_NEXT_MONTH = 0;
    /**
     * 今月
     */
    private static final int PARAM_THIS_MONTH = -1;
    /**
     * 1ヶ月前
     */
    private static final int PARAM_PREV_MONTH = -2;
    /**
     * 参照権限：参照可能
     */
    private static final boolean CB_CAN_REFER = true;
    /**
     * 参照権限：参照不可能
     */
    private static final boolean CB_CANT_REFER = false;

    /**
     * 生成したReferListを返す
     *
     * @return _referList
     */
    public TmgReferList getReferList() {
        return referList;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)
     */
    boolean _authorityNextMonth = false;
    /**
     * 勤怠シートの参照権限(基準月)
     */
    boolean _authorityMonth = false;

    /**
     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
     */
    public void setAuthorityMonth(boolean bValue) {
        _authorityMonth = bValue;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)取得メソッド
     */
    public boolean getAuthorityMonth() {
        return _authorityMonth;
    }

    /**
     * 勤怠シートの参照権限(基準月)設定メソッド
     */
    public void setAuthorityNextMonth(boolean bValue) {
        _authorityNextMonth = bValue;
    }

    /**
     * 勤怠シートの参照権限(基準月)取得メソッド
     */
    public boolean getAuthorityNextMonth() {
        return _authorityNextMonth;
    }

    /**
     * 汎用参照リストの処理をするメソッド
     *
     * @param iTree ツリータイプ
     * @return なし
     * @throws
     */
    public void setReferList(int iTree, PsDBBean psDBBean) {

        try {
            referList = new TmgReferList(psDBBean, BEAN_DESC, getThisMonth(), iTree, true, true, false, false, true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 汎用参照リストの処理をするメソッド
     *
     * @param sDate ツリータイプ
     * @return なし
     * @throws
     */
    public void setReferList(String sDate, int iTree, PsDBBean psDBBean) {

        try {
            referList = new TmgReferList(psDBBean, BEAN_DESC, sDate, iTree, true, true, false, false, true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * エラーチェック削除
     */
    private int buildSQLForDeleteDailyCheck(PsDBBean psDBBean) {
        int result = iTmgDailyCheckService.getBaseMapper().delete(SysUtil.<TmgDailyCheckDO>query().eq("TDA_CCUSTOMERID", psDBBean.getCustID())
                .eq("TDA_CCOMPANYID", psDBBean.getCompCode())
                .eq("TDA_CMODIFIERUSERID", psDBBean.getUserCode()));
        return result;
    }

    /**
     * エラーチェック削除
     */
    private int buildSQLForDeleteDetailCheck(PsDBBean psDBBean) {
        int result = iTmgDailyDetailCheckService.getBaseMapper().delete(SysUtil.<TmgDailyDetailCheckDO>query().eq("TDAD_CCUSTOMERID", psDBBean.getCustID())
                .eq("TDAD_CCOMPANYID", psDBBean.getCompCode())
                .eq("TDAD_CMODIFIERUSERID", psDBBean.getUserCode()));
        return result;
    }

    /**
     * エラーメッセージ削除
     */
    private int buildSQLForDeleteErrMsg(PsDBBean psDBBean) {
        int result = iTmgErrmsgService.getBaseMapper().delete(SysUtil.<TmgErrmsgDO>query().eq("TER_CCUSTOMERID", psDBBean.getCustID())
                .eq("TER_CCOMPANYID", psDBBean.getCompCode()).eq("TER_CMODIFIERUSERID", psDBBean.getUserCode()));
        return result;
    }

    /**
     * トリガー削除
     */
    private int buildSQLForDeleteTrigger(String action, PsDBBean psDBBean) {
        int result = iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", psDBBean.getUserCode())
                .eq("TTR_CMODIFIERPROGRAMID", BEAN_DESC + "_" + action));
        return result;
    }

    public static final String ACT_EDITPERM_EDIT = "ACT_EDITPERM_EDIT";      // 日別承認画面_確定済みデータの編集


    /**
     * 承認権限があるかどうか
     *
     * @return boolean true:権限有り/false:なし
     */
    public boolean isResult(String sEmp, String sDate, String siteId) {

        // 承認サイトのみ判定を行う
        if (!TmgUtil.Cs_SITE_ID_TMG_PERM.equals(siteId)) {
            return true;
        }

        try {
            return referList.hasAuthorityAtEmployee(sDate, sEmp, TmgUtil.Cs_AUTHORITY_RESULT);
        } catch (Exception e) {
            return false;
        }
    }


    // ボタン表示制御
    public boolean isEditable(TmgStatus tmgStatus ,PsDBBean psDBBean, String action) {

        boolean isEditable = false;

        // 未来日付はサイト関係なく常に「編集不可」
        if ("1".equals(tmgStatus.getIsFuture())) {
            isEditable = false;
        }
        // 「給与確定済」の場合も、サイト関係なく常に「編集不可」
        else if ("1".equals(tmgStatus.getFixedSalary())) {
            isEditable = false;
        }
        // 「勤怠締め完了済」の場合、管理サイトかつアクションが「締め完了済みデータ編集」でなければ編集不可
        else if ("1".equals(tmgStatus.getFixedMonthly()) && !(TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && ACT_EDITPERM_EDIT.equals(action))) {
            isEditable = false;
        }

        // 入力サイトのみ、月次ステータスが「承認済」、または、日次ステータスが「承認済」以上の場合、編集不可
        else if (TmgUtil.Cs_SITE_ID_TMG_INP.equals(psDBBean.getSiteId()) &&
                (TmgUtil.Cs_MGD_DATASTATUS_5.equals(tmgStatus.getTmoCstatusflg()) || TmgUtil.Cs_MGD_DATASTATUS_5.equals(tmgStatus.getTdaCstatusflg()) || TmgUtil.Cs_MGD_DATASTATUS_9.equals(tmgStatus.getTdaCstatusflg()))) {
            isEditable = false;
        }
        // 勤務実績編集権限を持っていない場合、編集不可
        else if (!isResult(psDBBean.getTargetUser(), getDay(), psDBBean.getSiteId())) {
            isEditable = false;
        }
        // いずれの条件も満たさなければ編集可
        else {
            isEditable = true;
        }

        return isEditable;
    }


    /**
     * システムプロパティから値を取得後、就業入力サイトでの就業実績編集機能を使用するか判定し値を返却します
     *
     * @return boolean(true : 使用する 、 false : 使用しない)
     */
    public boolean isEdiTableResult4Inp(PsDBBean psDBBean) {

        boolean ediTableResult4Inp = false;
        String strEdiTableResult4Inp = psDBBean.getSystemProperty("TMG_EDITABLE_RESULTS4INP");

        if (Cs_YES.equals(strEdiTableResult4Inp)) {
            ediTableResult4Inp = true;
        }
        return ediTableResult4Inp;

    }

    /**
     * システムプロパティから値を取得後、就業登録・日次登録画面と就業承認・日次登録画面で、裁量労働制の場合、超過勤務申請欄の表示可否を制御する値を返却します
     *
     * @return boolean(true : 使用する 、 false : 使用しない)
     */
    public boolean isDiscretionaryLabor(PsDBBean psDBBean) {

        boolean gbDiscretionaryLabor = false;

        String bWorkPlantime4Discretion = psDBBean.getSystemProperty("TMG_SHOW_COMMON_DISCRETIONARY_LABOR");

        if (bWorkPlantime4Discretion != null && Cs_YES.equalsIgnoreCase(bWorkPlantime4Discretion)) {
            gbDiscretionaryLabor = true;
        }

        return gbDiscretionaryLabor;
    }


    /**
     * 裁量労働対象者か判断する
     *
     * @param custId
     * @param compCode
     * @param employeeCode
     * @param baseDate
     * @return
     */
    public boolean isDiscretion(String custId, String compCode, String employeeCode, String baseDate) {
        return iTmgMastWorker4discretionaryService.buildSQLForSelectDiscretion(custId, compCode, employeeCode, baseDate);
    }


    /**
     * 裁量労働制の場合、当日の勤務予定欄の表示可否の判定
     *
     * @return boolean(true : 表示する 、 false : 表示しない)
     */
    public boolean isCommonDiscretionaryLabor(PsDBBean psDBBean) {

        // 「裁量労働制の場合、当日の勤務予定欄の表示可否の制御を行う」設定がオフの場合、勤務予定は通常通りの表示
        if(isDiscretion(psDBBean.getCustID(), psDBBean.getTargetComp(), psDBBean.getTargetUser(), getDay())){
            if (!isDiscretionaryLabor(psDBBean)) {
                return false;
                // 裁量労働制の場合、表示しない（基準日時点の裁量労働判定）
            }
        }else{
            return true;
        }
        return true;
    }

    ////////////////////////入力サイト///////////////////////////////////////////////////////////////////
    ///////////////////就業登録一覧画面表示開始////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 対象年月月リスト一覧取得
     * @param psDBBean
     * @return
     */
    public List<DispMonthlyVO> tmgInpInit(PsDBBean psDBBean) {

        // 対象月リスト一覧取得
        TodayThisMonthVO todayThisMonthVO = iMastGenericDetailService.buildSQLForSelectDate();

        // 今日の日付
        setToday(todayThisMonthVO.getToday());
        setThisMonth(todayThisMonthVO.getThisMonth());

        // 12 表示月遷移リスト情報取得
        List<DispMonthlyVO> dispMonthlyVOList = iTmgMonthlyService.buildSQLForSelectDispMonthlyList(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getToday()
        );
        return dispMonthlyVOList;
    }

    /**
     * 月次情報と日次情報を取得する
     *
     * @param psDBBean 　PsDBBean
     * @return 月次情報と日次情報
     */
    public Map<String, Object> getTitleData(PsDBBean psDBBean) {

        execReflectionTimePunch(null, psDBBean);

        Map<String, Object> monthlyMap = MapUtil.newHashMap();
        // 月次情報表示項目を取得しセット
        List<ItemVO> dispMonthlyItems = this.setDispMonthlyItems(psDBBean);

        List<String> monthlyItems = new ArrayList<String>();
        List<Map> monthlyTilteMapList = new ArrayList<Map>();
        for (ItemVO dispMonthlyItem : dispMonthlyItems) {

            monthlyItems.add(dispMonthlyItem.getMgdCsql() + " AS " + dispMonthlyItem.getMgdCcolumnkey());
            Map monthlyTilteMap = new HashMap();
            monthlyTilteMap.put("title", dispMonthlyItem.getMgdCheader());
            monthlyTilteMap.put("key", dispMonthlyItem.getMgdCcolumnkey());
            monthlyTilteMapList.add(monthlyTilteMap);
        }

        monthlyMap.put("monthlyTilteMapList", monthlyTilteMapList);

        // 1 月別
        HashMap monthlyDateMap = iTmgMonthlyService.buildSQLForSelectMonthly(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getMonth(),
                monthlyItems
        );
        monthlyMap.put("monthlyDateMap", monthlyDateMap);

        // 日次情報表示項目を取得しセット
        List<ItemVO> dispDailyItems = this.setDispDailyItems(psDBBean);

        List<Map> dailyTittleMapList = this.setMapList(dispDailyItems);
        monthlyMap.put("dailyTittleMapList", dailyTittleMapList);

        List<String> dailyItems = new ArrayList<String>();
        for (ItemVO dispDailyItem : dispDailyItems) {
            dailyItems.add(dispDailyItem.getMgdCsql() + " AS " + dispDailyItem.getMgdCcolumnid());
        }

        // 2 日別
        List<HashMap> dailyMapList = iTmgDailyService.buildSQLForSelectDaily(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                getMonth(),
                TmgUtil.Cs_MGD_MANAGEFLG_0,
                dailyItems
        );

        // 3 休日
        String targetUser = psDBBean.getTargetUser();
        String sectionid = "";
        String groupid = "";

        String year = getMonth().substring(0, 4);
        Map calendarMap = iTmgMonthlyService.buildSQLForSelectCalendar(psDBBean.getCustID(),
                psDBBean.getCompCode(), targetUser, sectionid, groupid, year, getMonth());

        int i = 1;
        for (Map dalyMap : dailyMapList) {
            dalyMap.put("TMG_HOLFLG", calendarMap.get("TCA_CHOLFLG" + StrUtil.fillBefore(String.valueOf(i), '0', 2)));
            i++;
        }

        monthlyMap.put("dailyMapList", dailyMapList);

        this.setEditableWorkHealthChk(psDBBean);
        ///  6 勤務状況
        List<TmgEmployeeAttributeVO> workStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_WORK_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);

        //  7 健康状態
        List<TmgEmployeeAttributeVO> healthStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_HEALTH_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);

        // 勤務状況確認表示可否フラグ
        boolean bDispKinmujokyoKakunin = this.getIsWorkChkFlg() && this.isKinmujokyoKakunin();
        // 健康状態確認表示可否フラグ
        boolean bDispKenkojotaiKakunin = this.getIsHealthChkFlg() && this.isKenkojotaiKakunin();


        // 現在の状況（勤務状況確認）:true(確認済)、false(未確認)
        boolean bWorkChkStatus = workStatus.size() > 0 && TmgUtil.Cs_MGD_ONOFF_1.equals(workStatus.get(0).getTesCattribute()) ;
        // 現在の状況（健康状態確認）:true(確認済)、false(未確認)
        boolean bHealthChkStatus = healthStatus.size() > 0 && TmgUtil.Cs_MGD_ONOFF_1.equals(healthStatus.get(0).getTesCattribute());

        WorkHealthChkVO workHealthChkVO = new WorkHealthChkVO();
        workHealthChkVO.setBDispKinmujokyoKakunin(bDispKinmujokyoKakunin);
        workHealthChkVO.setBDispKenkojotaiKakunin(bDispKenkojotaiKakunin);
        workHealthChkVO.setBWorkChkStatus(bWorkChkStatus);
        workHealthChkVO.setBHealthChkStatus(bHealthChkStatus);
        workHealthChkVO.setKinmujokyoEnd(getKinmujokyoEnd());
        if( healthStatus.size() > 0 && TmgUtil.Cs_MGD_ONOFF_1.equals(healthStatus.get(0).getCcode01())){
            workHealthChkVO.setSelHealthStatus(TmgUtil.Cs_MGD_ONOFF_1);
        }else{
            workHealthChkVO.setSelHealthStatus(TmgUtil.Cs_MGD_ONOFF_0);
        }

        monthlyMap.put("workHealthChkVO", workHealthChkVO);

        //16 対象社員について対象月の未承認日数
        String countNotApprovalDay = iTmgDailyService.buildSQLForSelectCountNotApprovalDay(
                getMonth(),
                psDBBean.getCompCode(),
                psDBBean.getCustID(),
                psDBBean.getTargetUser(),
                TmgUtil.Cs_MGD_DATASTATUS_9,
                TmgUtil.Cs_MGD_DATASTATUS_5
        );

        monthlyMap.put("countNotApprovalDay", countNotApprovalDay);

        return monthlyMap;
    }

    /**
     * 勤務・健康状況の確認/解除を行うメソッド
     *
     * @param psStatus 確認/解除
     * @return なし
     */
    public void updateStatus(PsDBBean psDBBean, String psStatus, String action, String selHealthStatus) {

        iTmgEmployeeAttributeService.buildSQLForDeleteTmgEmployeeAttribute(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                this.getCycleDay(getMonth(), psStatus, 1),
                psStatus
        );
        iTmgEmployeeAttributeService.buildSQLForInsertTmgEmployeeAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                this.getCycleDay(getMonth(), psStatus, 1),
                this.getCycleDay(getMonth(), psStatus, 2),
                psDBBean.getUserCode(),
                BEAN_DESC + '_' + action,
                psStatus,
                action,
                selHealthStatus);

    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////就業登録一覧画面表示終了////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////就業登録画面開始////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 就業登録画面初期化データ取得
     *
     * @param psDBBean 　PsDBBean
     * @param action   　action
     * @return 画面表示用データ
     */
    public Map dailyDetail(PsDBBean psDBBean, String action) {

        execReflectionTimePunch(action, psDBBean);

        Map<String, Object> dailyMap = MapUtil.newHashMap();

        // 就業区分マスタ
        List<GenericDetailVO> workIdList = iMastGenericDetailService.buildSQLForSelectGenericDetail(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage()
        );
        dailyMap.put("workIdList", workIdList);

        // 出張区分ドロップダウン用
        List<GenericDetailVO> businessTripList = iMastGenericDetailService.buildSQLForSelectgetMgdDescriptions(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                getDay(),
                GROUPID_TMG_BUSINESS_TRIP
        );
        dailyMap.put("businessTripList", businessTripList);

        // 非勤務ドロップダウン用
        List<MgdAttributeVO> categoryNondutyList = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_NONDUTY);
        dailyMap.put("categoryNondutyList", categoryNondutyList);

        // 超過勤務ドロップダウン用
        List<MgdAttributeVO> categoryOverhoursList = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                psDBBean.getSiteId(),
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_OVERHOURS);
        dailyMap.put("categoryOverhoursList", categoryOverhoursList);

        // 日別
        DailyEditVO dailyEditVO = iTmgDailyService.buildSQLForSelectDailyEdit(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                getToday(),
                psDBBean.getSiteId(),
                psDBBean.getLanguage()
        );
        dailyMap.put("dailyEditVO", dailyEditVO);

        // 詳細:欠勤離籍以外
        List<DailyDetailVO> dailyDetail0List = iTmgDailyDetailService.buildSQLForSelectDetail(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage(),
                0,
                true
        );
        dailyMap.put("dailyDetail0List", dailyDetail0List);

        // 詳細：非勤務
        List<DetailNonDutyVO> detailNonDutyVOList = iTmgDailyService.buildSQLForSelectDetailNonDuty(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage()
        );

        dailyMap.put("detailNonDutyVOList", detailNonDutyVOList);

        // 詳細：超過勤務
        List<DetailOverhoursVO> detailOverhoursVOList = iTmgDailyService.buildSQLForSelectDetailOverhours(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getSiteId(),
                this.getDay(),
                psDBBean.getLanguage(),
                this.isShowOvertimeNotification(psDBBean)
        );

        dailyMap.put("detailOverhoursVOList", detailOverhoursVOList);

        // 裁量労働-勤務状況の状態を取得
        // 勤務状況
        List<TmgEmployeeAttributeVO> workStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(
                psDBBean.getCustID()
                , psDBBean.getCompCode()
                , this.getDay()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_WORK_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);
        dailyMap.put("workStatus", workStatus);


        TmgStatus tmgStatus = iTmgDailyService.buildSQLForSelectTmgStatus(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getUserCode(),
                this.getDay());

        // 画面編集制御
        boolean isEditable = this.isEditable(tmgStatus,psDBBean, action);
        dailyMap.put("isEditable", isEditable);

        // 就業入力サイトでの就業実績編集機能を使用するか判定し値を返却します
        boolean isEdiTableResult4Inp = isEdiTableResult4Inp(psDBBean);
        dailyMap.put("isEdiTableResult4Inp", isEdiTableResult4Inp);
        //*締め済データの編集
        boolean isShowFixedMonthlyEditButton =isShowFixedMonthlyEditButton(tmgStatus,psDBBean,action);
        dailyMap.put("isShowFixedMonthlyEditButton", isShowFixedMonthlyEditButton);
        // 裁量労働制の場合、当日の勤務予定欄の表示可否の判定
        boolean isCommonDiscretionaryLabor = isCommonDiscretionaryLabor(psDBBean);
        dailyMap.put("isCommonDiscretionaryLabor", isCommonDiscretionaryLabor);
        boolean isDiscretion=isDiscretion(psDBBean.getCustID(), psDBBean.getTargetComp(), psDBBean.getTargetUser(), getDay());
        dailyMap.put("isDiscretion", isDiscretion);
        List<DailyLogVO> dailyLogVOList = iTmgDailyActionlogService.buildSQLForSelectTmgSelectDailyActionLog(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , psDBBean.getTargetUser()
                , this.getDay()
                , psDBBean.getLanguage());

        dailyMap.put("columnsDailyLog", dailyLogVOList);

        // 予備項目4を取得「0:出勤日,それ以外は出勤日ではない」
        List<MgdCsparechar4VO> mgdCsparechar4VOList = iMastGenericDetailService.buildSQLSelectGetMgdCsparechar4( psDBBean.getCustID(), psDBBean.getCompCode());
        dailyMap.put("mgdCsparechar4VOList", mgdCsparechar4VOList);

        // 日次超勤限度時間取得
        PsSession psSession = (PsSession) ContextUtil.getHttpRequest().getSession().getAttribute(Constant.PS_SESSION);
        String targetSec = psSession.getLoginDesignation().get(0).getSection();
        LimitOfBasedateVO limitOfBasedateVO = iMastOrganisationService.buildSQLForLimitOfBasedate(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                targetSec,
                getDay()
        );

        dailyMap.put("limitOfBasedateVO", limitOfBasedateVO);

        // 超過勤務対象有無取得,
        String targetForOverTime = iTmgEmployeeAttributeService.buildSQLForSelectTargetForOverTime(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay()
        );
        dailyMap.put("targetForOverTime", targetForOverTime);

        return dailyMap;
    }

    /**
     * 日別登録画面_本人コメント登録処理
     *
     * @param dto TmgResultsDto
     * @param psDBBean PsDBBean
     * @return GlobalResponse
     */
    public GlobalResponse updateInp(PsDBBean psDBBean, TmgResultsDto dto) {

        int result = 0;

        String txtAction= dto.getTxtAction();

        // トリガー削除
        this.buildSQLForDeleteTrigger(txtAction, psDBBean);

        // エラーチェック削除
        this.buildSQLForDeleteDailyCheck(psDBBean);

        // 日別
        DailyCheckDto dailyCheckDto = new DailyCheckDto();
        dailyCheckDto.setCustID(psDBBean.getCustID());
        dailyCheckDto.setCompCode(psDBBean.getCompCode());
        dailyCheckDto.setTargetUser(psDBBean.getTargetUser());
        dailyCheckDto.setDay(this.getDay());
        dailyCheckDto.setUserCode(psDBBean.getUserCode());
        dailyCheckDto.setMgdCbusinessTrip(dto.getSelMgdCbusinessTrip());
        dailyCheckDto.setTdaNopenR(dto.getTxtTdaNopenR());
        dailyCheckDto.setTdaNcloseR(dto.getTxtTdaNcloseR());
        dailyCheckDto.setAction(txtAction);
        dailyCheckDto.setHoliday(dto.getHoliday());
        dailyCheckDto.setTdaCworkingidR(dto.getWorkingId());
        dailyCheckDto.setSiteId(psDBBean.getSiteId());
        dailyCheckDto.setTdaCowncommentR(dto.getTdaCowncommentR());
        dailyCheckDto.setTdaCbosscommentR(dto.getTxtTdaCbosscommentR());

        result = iTmgDailyCheckService.buildSQLForInsertDailyCheck(dailyCheckDto);
        if (result != 1) {
            return GlobalResponse.error();
        }

        // トリガー追加
        result = iTmgTriggerService.buildSQLForInsertTrigger(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getUserCode(),
                txtAction
        );
        if (result != 1) {
            return GlobalResponse.error();
        }

        // トリガー削除
        result = this.buildSQLForDeleteTrigger(txtAction, psDBBean);
        if (result != 1) {
            return GlobalResponse.error();
        }

        // エラーチェック削除
        result = this.buildSQLForDeleteDailyCheck(psDBBean);
        if (result != 1) {
            return GlobalResponse.error();
        }

        return GlobalResponse.ok();
    }


//    /**
//     * 承認SQLを返すメソッド
//     *
//     * @return Vector  SQL
//     */
//    @Transactional(rollbackFor = GlobalException.class)
//    public ErrMsgDto getSQLVecForAjax(TmgResultsDto dto, PsDBBean psDBBean) {
//
//        // エラーチェック削除
//        this.buildSQLForDeleteDailyCheck(psDBBean);
//
//        // エラーチェック削除
//        this.buildSQLForDeleteDetailCheck(psDBBean);
//
//        // 日別
//        DailyCheckDto dailyCheckDto = new DailyCheckDto();
//        dailyCheckDto.setCustID(psDBBean.getCustID());
//        dailyCheckDto.setCompCode(psDBBean.getCompCode());
//        dailyCheckDto.setTargetUser(psDBBean.getTargetUser());
//        dailyCheckDto.setDay(this.getDay());
//        dailyCheckDto.setUserCode(psDBBean.getUserCode());
//        dailyCheckDto.setMgdCbusinessTrip(dto.getSelMgdCbusinessTrip());
//        dailyCheckDto.setTdaNopenR(dto.getTxtTdaNopenR());
//        dailyCheckDto.setTdaNcloseR(dto.getTxtTdaNcloseR());
//        dailyCheckDto.setAction(dto.getTxtAction());
//        dailyCheckDto.setHoliday(dto.getHoliday());
//        dailyCheckDto.setTdaCworkingidR(dto.getWorkingId());
//        dailyCheckDto.setSiteId(dto.getPsSite());
//        dailyCheckDto.setTdaCbosscommentR(dto.getTxtTdaCbosscommentR());
//        dailyCheckDto.setTdaCowncommentR(dto.getTdaCowncommentR());
//        iTmgDailyCheckService.buildSQLForInsertDailyCheck(dailyCheckDto);
//
//        // 詳細
//        iTmgDailyDetailCheckService.buildSQLForInsertDetailCheckEtc(
//                psDBBean.getCustID(),
//                psDBBean.getCompCode(),
//                psDBBean.getTargetUser(),
//                getDay(),
//                psDBBean.getUserCode(),
//                dto.getTxtAction()
//        );
//
//        // 画面非表示項目
//        iTmgDailyDetailCheckService.buildSQLForInsertDetailCheckNotDisp(
//                psDBBean.getCustID(),
//                psDBBean.getCompCode(),
//                psDBBean.getTargetUser(),
//                getDay(),
//                psDBBean.getUserCode(),
//                dto.getTxtAction(),
//                dto.getPsSite()
//        );
//
//        int i = 1;
//        // 非勤務
//        for (DetailNonDutyVO nonDuty : dto.getNonDutyList()) {
//            DetailCheckDto nonDutyDetailCheckDto = new DetailCheckDto();
//            nonDutyDetailCheckDto.setCustID(psDBBean.getCustID());
//            nonDutyDetailCheckDto.setCompCode(psDBBean.getCompCode());
//            nonDutyDetailCheckDto.setTargetUser(psDBBean.getTargetUser());
//            nonDutyDetailCheckDto.setUserCode(psDBBean.getUserCode());
//            nonDutyDetailCheckDto.setTxtAction(dto.getTxtAction());
//            nonDutyDetailCheckDto.setDay(this.getDay());
//            nonDutyDetailCheckDto.setMonth(getDay().substring(0, 7));
//            nonDutyDetailCheckDto.setYear(getDay().substring(0, 4));
//            nonDutyDetailCheckDto.setItemCode(nonDuty.getTdadCnotworkid());
//            nonDutyDetailCheckDto.setTxtTDAD_NOPEN(nonDuty.getTdadNopen());
//            nonDutyDetailCheckDto.setTxtTDAD_NCLOSE(nonDuty.getTdadNclose());
//            nonDutyDetailCheckDto.setTxtTDAD_CSPARECHAR1(nonDuty.getTdadCsparechar1());
//            nonDutyDetailCheckDto.setTxtTDAD_NSPARENUM1(nonDuty.getTdadNsparenum1());
//            nonDutyDetailCheckDto.setTxtTDAD_CCODE01(nonDuty.getTdadCcode01());
//            nonDutyDetailCheckDto.setCategoryCode(CATEGORY_NONDUTY);
//            nonDutyDetailCheckDto.setSite(dto.getPsSite());
//            nonDutyDetailCheckDto.setHasAuthority(dto.getHasAuthority());
//            nonDutyDetailCheckDto.setNIdx(i);
//            iTmgDailyDetailCheckService.buildSQLForInsertDetailCheck(nonDutyDetailCheckDto);
//
//            i++;
//        }
//
//        i = 1;
//        // 超過勤務
//        for (DetailOverhoursVO overHours : dto.getOverHoursList()) {
//            DetailCheckDto overHoursDetailCheckDto = new DetailCheckDto();
//            overHoursDetailCheckDto.setCustID(psDBBean.getCustID());
//            overHoursDetailCheckDto.setCompCode(psDBBean.getCompCode());
//            overHoursDetailCheckDto.setTargetUser(psDBBean.getTargetUser());
//            overHoursDetailCheckDto.setUserCode(psDBBean.getUserCode());
//            overHoursDetailCheckDto.setTxtAction(dto.getTxtAction());
//            overHoursDetailCheckDto.setDay(this.getDay());
//            overHoursDetailCheckDto.setMonth(getDay().substring(0, 7));
//            overHoursDetailCheckDto.setYear(getDay().substring(0, 4));
//            overHoursDetailCheckDto.setItemCode(overHours.getTdadCnotworkid());
//            overHoursDetailCheckDto.setTxtTDAD_NOPEN(overHours.getTdadNopen());
//            overHoursDetailCheckDto.setTxtTDAD_NCLOSE(overHours.getTdadNclose());
//            overHoursDetailCheckDto.setTxtTDAD_CSPARECHAR1(overHours.getTdadCsparechar1());
//            overHoursDetailCheckDto.setTxtTDAD_NSPARENUM1(overHours.getTdadNsparenum1());
//            overHoursDetailCheckDto.setTxtTDAD_CCODE01(overHours.getTdadCcode01());
//            overHoursDetailCheckDto.setCategoryCode(CATEGORY_OVERHOURS);
//            overHoursDetailCheckDto.setSite(dto.getPsSite());
//            overHoursDetailCheckDto.setHasAuthority(dto.getHasAuthority());
//            overHoursDetailCheckDto.setNIdx(i);
//            iTmgDailyDetailCheckService.buildSQLForInsertDetailCheck(overHoursDetailCheckDto);
//            i++;
//        }
//
//        // エラーメッセージ削除
//        this.buildSQLForDeleteErrMsg(psDBBean);
//
//        // エラーメッセージ追加
//        iTmgErrmsgService.buildSQLForInsertErrMsg(
//                psDBBean.getCustID(),
//                psDBBean.getCompCode(),
//                psDBBean.getUserCode(),
//                BEAN_DESC + "_" + dto.getTxtAction(),
//                psDBBean.getLanguage(),
//                psDBBean.getTargetUser(),
//                getDay()
//        );
//
//
//        // エラーメッセージ取得
//        ErrMsgDto errMsgDto = iTmgErrmsgService.buildSQLForSelectErrMsg(psDBBean.getCustID(),
//                psDBBean.getCompCode(),
//                psDBBean.getTargetUser());
//
//        // エラーメッセージ削除
//        this.buildSQLForDeleteErrMsg(psDBBean);
//
//
//        // エラーチェック削除
//        this.buildSQLForDeleteDailyCheck(psDBBean);
//
//        // エラーチェック削除
//        this.buildSQLForDeleteDetailCheck(psDBBean);
//
//        return errMsgDto;
//    }

    /**
     * 日別登録画面_登録と承認画面_承認
     * @param psDBBean PsDBBean
     * @param dto TmgResultsDto
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse updateDaily(PsDBBean psDBBean,TmgResultsDto dto ) {

        try{
            // エラーチェック削除
            this.buildSQLForDeleteDailyCheck(psDBBean);

            // エラーチェック削除
            this.buildSQLForDeleteDetailCheck(psDBBean);

            // 日別
            DailyCheckDto dailyCheckDto = new DailyCheckDto();
            dailyCheckDto.setCustID(psDBBean.getCustID());
            dailyCheckDto.setCompCode(psDBBean.getCompCode());
            dailyCheckDto.setTargetUser(psDBBean.getTargetUser());
            dailyCheckDto.setDay(this.getDay());
            dailyCheckDto.setUserCode(psDBBean.getUserCode());
            dailyCheckDto.setMgdCbusinessTrip(dto.getSelMgdCbusinessTrip());
            dailyCheckDto.setTdaNopenR(dto.getTxtTdaNopenR());
            dailyCheckDto.setTdaNcloseR(dto.getTxtTdaNcloseR());

            dailyCheckDto.setAction(dto.getTxtAction());

            dailyCheckDto.setHoliday(dto.getHoliday());
            dailyCheckDto.setTdaCworkingidR(dto.getWorkingId());
            dailyCheckDto.setSiteId(dto.getPsSite());
            dailyCheckDto.setTdaCbosscommentR(dto.getTxtTdaCbosscommentR());
            dailyCheckDto.setTdaCowncommentR(dto.getTdaCowncommentR());
            iTmgDailyCheckService.buildSQLForInsertDailyCheck(dailyCheckDto);
            // 詳細
            iTmgDailyDetailCheckService.buildSQLForInsertDetailCheckEtc(
                    psDBBean.getCustID(),
                    psDBBean.getCompCode(),
                    psDBBean.getTargetUser(),
                    getDay(),
                    psDBBean.getUserCode(),
                    dto.getTxtAction()
            );
            // 画面非表示項目
            iTmgDailyDetailCheckService.buildSQLForInsertDetailCheckNotDisp(
                    psDBBean.getCustID(),
                    psDBBean.getCompCode(),
                    psDBBean.getTargetUser(),
                    getDay(),
                    psDBBean.getUserCode(),
                    dto.getTxtAction(),
                    dto.getPsSite()
            );

            int i = 1;
            // 非勤務
            for (DetailNonDutyVO nonDuty : dto.getNonDutyList()) {
                DetailCheckDto nonDutyDetailCheckDto = new DetailCheckDto();
                nonDutyDetailCheckDto.setCustID(psDBBean.getCustID());
                nonDutyDetailCheckDto.setCompCode(psDBBean.getCompCode());
                nonDutyDetailCheckDto.setTargetUser(psDBBean.getTargetUser());
                nonDutyDetailCheckDto.setUserCode(psDBBean.getUserCode());
                nonDutyDetailCheckDto.setTxtAction(dto.getTxtAction());
                nonDutyDetailCheckDto.setDay(this.getDay());
                nonDutyDetailCheckDto.setMonth(getDay().substring(0, 7));
                nonDutyDetailCheckDto.setYear(getDay().substring(0, 4));

                nonDutyDetailCheckDto.setItemCode(nonDuty.getTdadCnotworkid());
                nonDutyDetailCheckDto.setTxtTDAD_NOPEN(nonDuty.getTdadNopen());
                nonDutyDetailCheckDto.setTxtTDAD_NCLOSE(nonDuty.getTdadNclose());
                nonDutyDetailCheckDto.setTxtTDAD_CSPARECHAR1(nonDuty.getTdadCsparechar1());
                nonDutyDetailCheckDto.setTxtTDAD_NSPARENUM1(nonDuty.getTdadNsparenum1());

                nonDutyDetailCheckDto.setTxtTDAD_CCODE01(nonDuty.getTdadCcode01());
                nonDutyDetailCheckDto.setHasAuthority(dto.getHasAuthority());
                nonDutyDetailCheckDto.setCategoryCode(CATEGORY_NONDUTY);
                nonDutyDetailCheckDto.setSite(dto.getPsSite());
                nonDutyDetailCheckDto.setNIdx(i);
                iTmgDailyDetailCheckService.buildSQLForInsertDetailCheck(nonDutyDetailCheckDto);
                i++;
            }

            i = 1;
            // 超過勤務
            for (DetailOverhoursVO overHours : dto.getOverHoursList()) {
                DetailCheckDto overHoursDetailCheckDto = new DetailCheckDto();
                overHoursDetailCheckDto.setCustID(psDBBean.getCustID());
                overHoursDetailCheckDto.setCompCode(psDBBean.getCompCode());
                overHoursDetailCheckDto.setTargetUser(psDBBean.getTargetUser());
                overHoursDetailCheckDto.setUserCode(psDBBean.getUserCode());
                overHoursDetailCheckDto.setTxtAction(dto.getTxtAction());
                overHoursDetailCheckDto.setDay(this.getDay());
                overHoursDetailCheckDto.setMonth(getDay().substring(0, 7));
                overHoursDetailCheckDto.setYear(getDay().substring(0, 4));

                overHoursDetailCheckDto.setItemCode(overHours.getTdadCnotworkid());
                overHoursDetailCheckDto.setTxtTDAD_NOPEN(overHours.getTdadNopen());
                overHoursDetailCheckDto.setTxtTDAD_NCLOSE(overHours.getTdadNclose());
                overHoursDetailCheckDto.setTxtTDAD_CSPARECHAR1(overHours.getTdadCsparechar1());
                overHoursDetailCheckDto.setTxtTDAD_NSPARENUM1(overHours.getTdadNsparenum1());
                overHoursDetailCheckDto.setTxtTDAD_CCODE01(overHours.getTdadCcode01());
                overHoursDetailCheckDto.setCategoryCode(CATEGORY_OVERHOURS);
                overHoursDetailCheckDto.setSite(dto.getPsSite());
                overHoursDetailCheckDto.setHasAuthority(dto.getHasAuthority());
                overHoursDetailCheckDto.setNIdx(i);
                iTmgDailyDetailCheckService.buildSQLForInsertDetailCheck(overHoursDetailCheckDto);
                i++;
            }

            // エラーメッセージ削除
            this.buildSQLForDeleteErrMsg(psDBBean);

            // エラーメッセージ追加
            iTmgErrmsgService.buildSQLForInsertErrMsg(
                    psDBBean.getCustID(),
                    psDBBean.getCompCode(),
                    psDBBean.getUserCode(),
                    BEAN_DESC + "_" + dto.getTxtAction(),
                    psDBBean.getLanguage(),
                    psDBBean.getTargetUser(),
                    getDay()
            );

            // エラーメッセージ取得
            ErrMsgDto ErrMsgDto = iTmgErrmsgService.buildSQLForSelectErrMsg(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getUserCode());

            if(ErrMsgDto!=null && ErrMsgDto.getTerCerrcode().equals("0")){
                // トリガー追加
                iTmgTriggerService.buildSQLForInsertTrigger(
                        psDBBean.getCustID(),
                        psDBBean.getCompCode(),
                        psDBBean.getTargetUser(),
                        getDay(),
                        psDBBean.getUserCode(),
                        dto.getTxtAction()
                );
                return GlobalResponse.ok("0");
            }else if(ErrMsgDto.getTerCerrcode().indexOf("ERRTYPE_10")>-1){
                return GlobalResponse.ok(ErrMsgDto.getTerCerrcode());
            }else if(ErrMsgDto.getTerCerrcode().indexOf("ERRTYPE_20")>-1){
                // トリガー追加
                iTmgTriggerService.buildSQLForInsertTrigger(
                        psDBBean.getCustID(),
                        psDBBean.getCompCode(),
                        psDBBean.getTargetUser(),
                        getDay(),
                        psDBBean.getUserCode(),
                        dto.getTxtAction()
                );

                return GlobalResponse.ok(ErrMsgDto.getTerCerrcode());
            }
        }catch (GlobalException e){
            return GlobalResponse.error(e.getMessage());
        }finally {
            // トリガー削除
            this.buildSQLForDeleteTrigger(dto.getTxtAction(), psDBBean);

            // エラーメッセージ削除
            this.buildSQLForDeleteErrMsg(psDBBean);

            // エラーチェック削除
            this.buildSQLForDeleteDailyCheck(psDBBean);

            // エラーチェック削除
            this.buildSQLForDeleteDetailCheck(psDBBean);
        }

        return GlobalResponse.ok();
    }

    public boolean isShowFixedMonthlyEditButton(TmgStatus tmgStatus,PsDBBean psDBBean,String action){
        // 管理サイト、かつ、給与未確定、かつ、勤怠締め完了済み、の場合は、「締め完了済みデータの編集」ボタンを表示する
        // ※現在のアクションが「締め完了済みデータの編集」であれば表示しない
        if( TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())
                && !"1".equals(tmgStatus.getIsFuture())
                && !"1".equals(tmgStatus.getFixedSalary())
                && "1".equals(tmgStatus.getFixedMonthly())
                && !ACT_EDITPERM_EDIT.equals(action) ){
            return true;
        }else{
            return  false;
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////就業登録画面終了////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * 発令日表示処理
     * @param psDBBean PsDBBean
    　 @param baseDate TmgReferList.RecordDate
     */
    public HatuReiVo getHatuReiVoInfo(String baseDate,PsDBBean psDBBean){
        HatuReiDto dto=iHistSuspensionService.getHatuRei(psDBBean.getCustID(),psDBBean.getCompCode(),
        psDBBean.getTargetUser(),baseDate);
        if(dto!=null){
            HatuReiVo vo = new HatuReiVo();
            vo.setName(dto.getHsCsuspensiontypecd());
            vo.setTimerange(dto.getHsDstartdate()+"~"+dto.getHsDenddate());
            return  vo;
        }else{
            return null;
        }

    }



}
