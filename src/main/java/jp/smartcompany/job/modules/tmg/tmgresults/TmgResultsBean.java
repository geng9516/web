package jp.smartcompany.job.modules.tmg.tmgresults;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.service.*;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.*;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.DateFormat;
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
     * PsDBBean
     */
    private final PsDBBean psDBBean;

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
     * TmgReferList
     */
    private TmgReferList referList = null;


    // サイト識別子

    public static final String SITE_TI = "TMG_INP";             // 勤怠入力サイト
    public static final String SITE_TP = "TMG_PERM";            // 勤怠承認サイト
    public static final String SITE_TA = "TMG_ADMIN";           // 勤怠管理サイト

    // 月別一覧画面表示
    public static final String ACT_DISP_RMONTHLY = "ACT_DISP_RMONTHLY";
    public static final String ACT_EDITINP_RDAILY      = "ACT_EDITINP_RDAILY";     // 日別登録画面表示

    // 勤務状況確認
    public static final String TYPE_ITEM_WORK_STATUS        = "TMG_ITEMS|WorkStatus";
    // 健康状態確認
    public static final String TYPE_ITEM_HEALTH_STATUS      = "TMG_ITEMS|HealthStatus";

    // 出張区分ドロップボックス
    public static final String GROUPID_TMG_BUSINESS_TRIP    = "TMG_BUSINESS_TRIP";

    // 属性コードの使用可否
    public static final String ATTRIBUTE_ENABLE_ONLY = "1"; // 計算用項目は表示しない
    public static final String CATEGORY_NONDUTY          = "TMG_CATEGORY|NonDuty";          // 非勤務
    public static final String CATEGORY_OVERHOURS        = "TMG_CATEGORY|Overhours";        // 超過勤務
    private final static String Cs_YES = "yes";


    private void setSysControl(){
        psDBBean.setCustID("01");
        psDBBean.setCompCode("01");
        psDBBean.setLanguage("ja");
        psDBBean.setTargetUser("34370889");
        this.setDay("2019/09/01");
        this.setToday("2019/09/01");
        this.setMonth("2019/09/01");
        psDBBean.setTargetComp("01");
        psDBBean.setTargetCust("01");

    }

    /**
     * 入力サイト > 日別登録画面表示 > ACT_DISP_RMONTHLY
     */
    public void actDispRmonthly(ModelMap modelMap) {
        this.setSysControl();


        if (psDBBean.getTargetUser() != null && psDBBean.getTargetUser().length() != 0) {

            showMonthly(modelMap);
        }
    }

    /**
     * 入力サイト > 月別一覧画面 > ACT_DISP_RMONTHLY
     */
    public void actEditinpRdaily(ModelMap modelMap) {

        this.showInp(modelMap);
    }
    private void showInp(ModelMap modelMap) {

        // 打刻反映処理
        execReflectionTimePunch(ACT_EDITINP_RDAILY);

        // 日別
        DailyEditVO dailyEditVO =iTmgDailyService.buildSQLForSelectDailyEdit(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay(),
                getToday(),
                psDBBean.getSiteId(),
                psDBBean.getLanguage()
        );
        modelMap.addAttribute("dailyEditVO", dailyEditVO);

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
        modelMap.addAttribute("dailyDetail0List", dailyDetail0List);


        // 予定出社・退社時間の基準値
        CompanyVO companyVO = iTmgCompanyService.buildSQLSelectCompany(psDBBean.getCustID(), psDBBean.getCompCode(), getDay());
        modelMap.addAttribute("companyVO", companyVO);

        // 出勤日判定用
        List<MgdCsparechar4VO> MgdCsparechar4VOList = iMastGenericDetailService.buildSQLSelectGetMgdCsparechar4(psDBBean.getCustID(), psDBBean.getCompCode());
        modelMap.addAttribute("companyVO", companyVO);

        // 非勤務ドロップダウン用
        List<MgdAttributeVO> categoryNonduty = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                // TODO
                //psDBBean.getSiteId(),
                "TMG_INP",
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_NONDUTY);
        modelMap.addAttribute("categoryNonduty", categoryNonduty);

        // 超過勤務ドロップダウン用
        List<MgdAttributeVO> categoryOverhours = iMastGenericDetailService.buildSQLForSelectgetMgdAttribute(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                psDBBean.getLanguage(),
                // TODO
                //psDBBean.getSiteId(),
                "TMG_INP",
                getDay(),
                ATTRIBUTE_ENABLE_ONLY,
                CATEGORY_OVERHOURS);
        modelMap.addAttribute("categoryOverhours", categoryOverhours);

        // 詳細：非勤務
        List<DetailNonDutyVO> detailNonDutyVOList = iTmgDailyService.buildSQLForSelectDetailNonDuty(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                // TODO
                //psDBBean.getSiteId(),
                "TMG_INP",
                this.getDay(),
                psDBBean.getLanguage()
        );

        modelMap.addAttribute("detailNonDutyVOList", detailNonDutyVOList);

        // 詳細：超過勤務
        List<DetailOverhoursVO> detailOverhoursVOList = iTmgDailyService.buildSQLForSelectDetailOverhours(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                // TODO
                //psDBBean.getSiteId(),
                "TMG_INP",
                this.getDay(),
                psDBBean.getLanguage(),
                this.isShowOvertimeNotification()
        );

        modelMap.addAttribute("detailOverhoursVOList", detailOverhoursVOList);

        // 出張区分ドロップダウン用
        List<GenericDetailVO> mgdDescriptions = iMastGenericDetailService.buildSQLForSelectgetMgdDescriptions(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                getDay(),
                GROUPID_TMG_BUSINESS_TRIP
        );
        modelMap.addAttribute("mgdDescriptions", mgdDescriptions);

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
        modelMap.addAttribute("workStatus", workStatus);

        // 就業区分マスタ
        List<GenericDetailVO> genericDetailVOList =  iMastGenericDetailService.buildSQLForSelectGenericDetail(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getTargetUser(),
                getDay(),
                psDBBean.getLanguage()
        );
        modelMap.addAttribute("genericDetailVOList", genericDetailVOList);


        // 日次超勤限度時間取得
        // TODO
//        String targetSec = (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()))
//                ? this.referList.getTargetSec()
//                : (String) psDBBean.getDept().get(0);
        String targetSec ="";

        LimitOfBasedateVO limitOfBasedateVO = iMastOrganisationService.buildSQLForLimitOfBasedate(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                targetSec,
                getDay()
        );
        modelMap.addAttribute("limitOfBasedateVO", limitOfBasedateVO);


        // 超過勤務対象有無取得,
        String targetForOverTime = iTmgEmployeeAttributeService.buildSQLForSelectTargetForOverTime(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getDay()
        );
        modelMap.addAttribute("targetForOverTime", targetForOverTime);

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
        modelMap.addAttribute("dailyDetail3List", dailyDetail3List);

        // コメント欄の最大値取得
        String tmgVMgdMaxLengthCheck = iMastGenericDetailService.buildSQLForSelectTmgVMgdMaxLengthCheck(
                psDBBean.getCustID(),
                psDBBean.getTargetComp(),
                psDBBean.getLanguage(),
                getDay(),
                TmgUtil.Cs_MGD_TMG_MAX_LENGTH_CHECK_TMG_MAX_LENGTH_CHECK);
        modelMap.addAttribute("tmgVMgdMaxLengthCheck", tmgVMgdMaxLengthCheck);

        // 裁量労働-勤務状況の値を格納
        boolean isDiscretionWorkFixForDB = false;
        if (workStatus.size()>0){
            isDiscretionWorkFixForDB = workStatus.get(0).getTesCattribute().equals("TMG_ONOFF|1");
        }
        modelMap.addAttribute("isDiscretionWorkFixForDB", isDiscretionWorkFixForDB);
    }


    /**
     * システムプロパティから値を取得後、超勤申請の事前事後登録情報を表示利用するかどうか判定し値を返却します
     *
     * @return boolean(true:使用する、false:使用しない)
     */
    public boolean isShowOvertimeNotification(){

        // TODO psDBBean.getSystemProperty 未実装
        //psDBBean.getSystemProperty(TmgUtil.Cs_CYC_PROP_NAME_TMG_SHOW_OVERTIMENOTIFICATION);
        String overtimeNotification = "yes";


        if (Cs_YES.equalsIgnoreCase(overtimeNotification)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 月別一覧画面を表示するメソッド
     */
    private void showMonthly(ModelMap modelMap) {

        // 月次情報表示項目を取得しセット
        List<ItemVO> dispMonthlyItems = this.setDispMonthlyItems();

        // TODO 月次情報表示項目設定の実装がまだです
        List<String> monthlyItems = new ArrayList<String>();
        for (ItemVO dispMonthlyItem : dispMonthlyItems) {
            monthlyItems.add(dispMonthlyItem.getMgdCsql() + " AS " + dispMonthlyItem.getMgdCcolumnkey());
        }

        // 日次情報表示項目を取得しセット
        List<ItemVO> dispDailyItems = this.setDispDailyItems();
        // TODO 日次情報表示項目設定の実装がまだです

        List<String> dailyItems = new ArrayList<String>();
        for (ItemVO dispDailyItem : dispDailyItems) {
            dailyItems.add(dispDailyItem.getMgdCsql() + " AS " + dispDailyItem.getMgdCcolumnid());
        }

        // 月45時間を越える超勤の申請理由起動時処理
        // TODO 標準版ではないので、削除待ち
        // execOverHours45();

        // 打刻反映処理
        execReflectionTimePunch(ACT_DISP_RMONTHLY);

        // 勤務状況確認、健康状態確認の使用可否設定を行います。 TODO 削除待ち
        // setEditableWorkHealthChk();

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
        modelMap.addAttribute("monthlyMap", monthlyMap);

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
        modelMap.addAttribute("dailyMapList", dailyMapList);

        // 4 前月・翌月
        MonthlyLinkVO monthlyLinkVO = iTmgMonthlyService.buildSQLForSelectMonthlyLink(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getMonth()
        );
        modelMap.addAttribute("monthlyLinkVO", monthlyLinkVO);


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
        modelMap.addAttribute("workStatus", workStatus);

        //  7 健康状態
        List<TmgEmployeeAttributeVO> healthStatus = iTmgEmployeeAttributeService.buildSQLForSelectTmgEmployeeAttribute(psDBBean.getCustID()
                , psDBBean.getCompCode()
                , getToday()
                , psDBBean.getTargetUser()
                , getMonth()
                , TYPE_ITEM_HEALTH_STATUS
                , TYPE_ITEM_OVERHOURS_REASON);
        modelMap.addAttribute("healthStatus", healthStatus);

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
        List<DispMonthlyVO> dispMonthlyVOList =  iTmgMonthlyService.buildSQLForSelectDispMonthlyList(
                psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getTargetUser(),
                getToday()
        );
        modelMap.addAttribute("dispMonthlyVOList", dispMonthlyVOList);

        // 勤怠承認・管理サイトは社員情報も
        // TODO getSiteId 問題がある
        //if (!SITE_TI.equals(psDBBean.getSiteId())) {
        if (!SITE_TI.equals(null)) {

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

            modelMap.addAttribute("countNotApprovalDay", countNotApprovalDay);
        }
    }

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
     * TMG_DISPMONTHLYITEMSマスタより取得した月次情報のヘッダー・SQLをリストに格納する
     */
    public List<ItemVO> setDispMonthlyItems() {

        return iMastGenericDetailService.buildSQLForSelectTmgDispMonthlyItems(psDBBean.getCustID(), psDBBean.getCompCode(),
                psDBBean.getLanguage(), getDay());
    }


    /**
     * TMG_DISPDAILYITEMSマスタより取得した日次情報のヘッダー・SQL・表示幅をmapに格納する
     */
    public List<ItemVO> setDispDailyItems() {

        return iMastGenericDetailService.buildSQLForSelectTmgDispDailyItems(psDBBean.getCustID(), psDBBean.getCompCode(),
                psDBBean.getLanguage(), getDay());
    }

//
//    /**
//     * 起動時に「月45時間を超える超過勤務の申請理由」用の処理を実行するか
//     * チェックし、必要に応じて実行する
//     */
//    // TODO　削除待ち
//    private void execOverHours45() {
//
//        if (isUseNtf45OverTime()) {
//
//            overHours45(ACT_DISP_RMONTHLY);
//            overHours45ViewCheck();
//        }
//    }
//
//    /**
//     * システムプロパティ：就業入力サイトでの月45時間を超える超過勤務の申請理由登録機能を使用するか判定し制御します
//     */
//    private final static String SYSPROP_TMG_USE_NTF45OVERTIME = "TMG_USE_NTF45OVERTIME";
//    private Boolean gbUseNtf45OverTime = null;

//
//    /**
//     * システムプロパティから値を取得後、月45時間を超える超過勤務の申請理由登録機能を使用するか判定し値を返却します
//     *
//     * @return boolean(true : 使用する 、 false : 使用しない)
//     */
//    // TODO 画面表示用
//    public boolean isUseNtf45OverTime() {
//
//        if (gbUseNtf45OverTime == null) {
//
//            // TODO
//            String sEdiTableResult4Inp = psDBBean.getSystemProperty(SYSPROP_TMG_USE_NTF45OVERTIME);
//
//            if (sEdiTableResult4Inp != null && Cs_YES.equalsIgnoreCase(sEdiTableResult4Inp)) {
//                gbUseNtf45OverTime = true;
//            } else {
//                gbUseNtf45OverTime = false;
//            }
//        }
//
//        return gbUseNtf45OverTime;
//    }


    /**
     * 月45時間を越える超勤の申請理由
     */
    public static final String TYPE_ITEM_OVERHOURS_REASON = "TMG_ITEMS|OverhoursReason";

    /**
     * 承認ボタン
     */
    // TODO 画面表示判断用
    public boolean approvalBtn = false;

    private final String BEAN_DESC = "TmgResults";     // LOG出力用ディスクリプタ
    public static final String ACT_APPL_OVERHOURS = "ACT_APPL_OVERHOURS";     // 月別一覧画面_月45時間を越える超勤の申請

    /**
     * 起動時に「月45時間を超える超過勤務の申請理由」の「遅延理由」の存在チェックし、
     * レコードが存在しない場合は追加、存在する場合は更新する。
     *
     * @param psStatus 分岐
     */
    private void overHours45(String psStatus) {

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
        if (this.monthSumOverWork() <= OVER_WORK_45) {
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
    private double monthSumOverWork() {
        // ▼ 2010/07/22 isolsuzuki #1122 【障害報告：nttw-341】超勤４５Ｈ超えのカウントに
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
        // ▲ 2010/07/22 isolsuzuki #1122 【障害報告：nttw-341】超勤４５Ｈ超えのカウントに
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
    private void overHours45ViewCheck() {

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
     * 月次一覧、また日次登録（承認）画面表示時の打刻反映処理
     */
    private void execReflectionTimePunch(String act) {

        String action = act;

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

        // 打刻反映処理 TODO trigger なし，実装待ち
//        Vector vQuery = new Vector();
//        vQuery.add(buildSQLForInsertTriggerByTimePunch(action, stDate, endDate));
//        vQuery.add(buildSQLForDeleteTrigger());
//        TmgUtil.checkInsertErrors(setInsertValues(vQuery, BEAN_DESC), session, BEAN_DESC);
    }

    /**
     * 勤務状況確認欄の使用判定変数
     */
    // TODO 画面表示用
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
    // TODO 画面表示用
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
    private void setEditableWorkHealthChk() {

        String custId = psDBBean.getTargetCust(); // 表示対象職員の顧客コード
        String compId = psDBBean.getTargetComp(); // 表示対象職員の法人コード
        String empId = psDBBean.getTargetUser(); // 表示対象職員の職員番号
        String lang = psDBBean.getLanguage();   // 言語区分

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
    // TODO 画面判断用
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
     * 県境状況確認の確定ボタンを表示するか
     */
    // TODO 画面判断用
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
//
//    /**
//     * 勤務状況確認の未確認対象期間を警告表示する期間メッセージを取得する。
//     * @return
//     */
//    // TODO 画面表示用
//    public String getMsgWorkChkUnfinishConf() {
//
//        String custId = psDBBean.getTargetCust(); // 表示対象職員の顧客コード
//        String compId = psDBBean.getTargetComp(); // 表示対象職員の法人コード
//        String empId  = psDBBean.getTargetUser(); // 表示対象職員の職員番号
//
//        Vector vQuery = new Vector();
//        vQuery.add(buildMsgWorkChkUnfinishConf(custId, compId, empId, getToday()));
//
//        String sMsg = null;
//        try {
//
//            PsResult res = (ps.core.PsResult)getValuesforMultiquery(vQuery, BEAN_DESC);
//
//            Vector vQrys = res.getResult();
//            Vector vRows = (Vector)vQrys.get(0);
//            for(int i = 0; i < vRows.size(); i++){
//                Vector vCols = (Vector)vRows.get(i);
//                sMsg   = (String)vCols.get(0);
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//
//        // 検索結果がなければ、空文字を返却する。
//        if (StrUtil.isEmpty(sMsg)) {
//            return "";
//        }
//        return sMsg;
//    }
//
//    /**
//     * 健康状態確認の未確認対象期間を警告表示する期間メッセージを取得する。
//     * @return
//     */
//    // TODO 画面表示用
//    public String getMsgHealthChkUnfinishConf() {
//
//        String custId = psDBBean.getTargetCust(); // 表示対象職員の顧客コード
//        String compId = psDBBean.getTargetComp(); // 表示対象職員の法人コード
//        String empId  = psDBBean.getTargetUser(); // 表示対象職員の職員番号
//
//        Vector vQuery = new Vector();
//        vQuery.add(buildMsgHealthChkUnfinishConf(custId, compId, empId, getToday()));
//
//        String sMsg = null;
//        try {
//
//            PsResult res = (ps.core.PsResult)getValuesforMultiquery(vQuery, BEAN_DESC);
//
//            Vector vQrys = res.getResult();
//            Vector vRows = (Vector)vQrys.get(0);
//            for(int i = 0; i < vRows.size(); i++){
//                Vector vCols = (Vector)vRows.get(i);
//                sMsg   = (String)vCols.get(0);
//            }
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//
//        // 検索結果がなければ、空文字を返却する。
//        if (StrUtil.isEmpty(sMsg)) {
//            return "";
//        }
//        return sMsg;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準月)
//     */
//    // TODO 画面表示用
//    boolean _authorityMonth = false;
//
//    /**
//     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
//     */
//    public void setAuthorityMonth(boolean bValue) {
//        _authorityMonth = bValue;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準日の翌月)取得メソッド
//     */
//    public boolean getAuthorityMonth() {
//        return _authorityMonth;
//    }
//

//
//    private static final String FORMAT_ZERO = "00";
//
//    /**
//     * 承認権限(月次)があるかどうか
//     *
//     * @param sEmp  社員番号
//     * @param sDate 日付
//     * @return boolean true:権限有り/false:なし
//     * @throws boolean
//     */
//    // TODO 画面表示用
//    public boolean isMonthlyApproval(String sEmp, String sDate) {
//
//        // 承認サイトのみ判定を行う
//        if (!TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())) {
//            return true;
//        }
//
//        try {
//            Calendar cal = new GregorianCalendar(Integer.parseInt(getMonth().substring(0, 4)),
//                    Integer.parseInt(getMonth().substring(5, 7)) - 1, 1);
//            return referList.hasAuthorityAtEmployee(sDate,
//                    getMonth().substring(0, 8) + new DecimalFormat(FORMAT_ZERO).format((double) cal.getActualMaximum(Calendar.DAY_OF_MONTH)),
//                    sEmp, TmgUtil.Cs_AUTHORITY_MONTHLYAPPROVAL);
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * 就業承認/月別一覧画面で、日次一括承認機能を利用するかどうか判定し値を返却します
//     * ※使用サイトが入力サイトの場合、必ずfalseを返します。
//     *
//     * @return boolean(true : 使用する 、 false : 使用しない)
//     */
//    // TODO 画面表示用
//    public boolean isAllApprovalDaily() {
//
//        return isUseBulkPerm4Results() && !TmgUtil.Cs_SITE_ID_TMG_INP.equals(psDBBean.getSiteId());
//    }
//
//    /**
//     * システムプロパティ：就業承認/月別一覧画面で、日次一括承認機能を利用するかどうか制御します
//     */
//    private final String SYSPROP_TMG_USE_BULKPERM4RESULTS = "TMG_USE_BULKPERM4RESULTS";
//    private Boolean gbUseBulkPerm4Results = null;
//
//    /**
//     * システムプロパティから値を取得後、就業承認/月別一覧画面で、日次一括承認機能を利用するかどうか判定し値を返却します
//     *
//     * @return boolean(true : 使用する 、 false : 使用しない)
//     */
//    public boolean isUseBulkPerm4Results() {
//
//        if (gbUseBulkPerm4Results == null) {
//
//            String gUseBulkPerm4Results = psDBBean.getSystemProperty(SYSPROP_TMG_USE_BULKPERM4RESULTS);
//
//            if (gUseBulkPerm4Results != null && Cs_YES.equalsIgnoreCase(gUseBulkPerm4Results)) {
//                gbUseBulkPerm4Results = true;
//            } else {
//                gbUseBulkPerm4Results = false;
//            }
//        }
//        return gbUseBulkPerm4Results;
//    }
//
//    // TODO 画面表示用
//    private String gsThisMonth = null;  // 今月
//
//    /**
//     * 今月
//     */
//    public void setThisMonth(String s) {
//        gsThisMonth = s;
//    }
//
//    public String getThisMonth() {
//        return gsThisMonth;
//    }
//
//    /**
//     * 勤務状況確認の開始月
//     */
//    // TODO 画面表示用
//    public int getKinmujokyoEnd() {
//        int retval = 0;
//
//        //表示中の月
//        String sMonth = getMonth();
//        Date dMonth;
//        try {
//            dMonth = DateFormat.getDateInstance().parse(sMonth);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//            return 0;
//        }
//        Calendar cMonth = new GregorianCalendar();
//        cMonth.setTime(dMonth);
//
//        //表示開始の日付
//        int iMonth = cMonth.get(Calendar.MONTH) + 1;
//        iMonth = ((int) ((iMonth + 2) / 3)) * 3;
//
//        retval = iMonth;
//
//        return retval;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準日の翌月)
//     */
//    // TODO 画面表示用
//    boolean _authorityNextMonth = false;
//
//    /**
//     * 勤怠シートの参照権限(基準月)設定メソッド
//     */
//    public void setAuthorityNextMonth(boolean bValue) {
//        _authorityNextMonth = bValue;
//    }
//
//    /**
//     * 勤怠シートの参照権限(基準月)取得メソッド
//     */
//    public boolean getAuthorityNextMonth() {
//        return _authorityNextMonth;
//    }
//
//    /**
//     * 基準日が過去日付か判定して値を返却
//     *
//     * @return boolean
//     * @author
//     */
//    // TODO 画面表示用
//    public boolean getCheckToDayFlg() {
//        if (referList != null) {
//            if (referList.isCheckToDayFlg() == false) {
//                if (compareDateString(getThisMonth(), getMonth()) == 0) {
//                    return referList.isCheckToDayFlg();
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * 日付比較用
//     *
//     * @param str1
//     * @param str2
//     * @return
//     */
//    public int compareDateString(String str1, String str2) {
//        //nullだったりyyyy/mm/dd以外の時は-1より小さい値を返す
//        if (str1 == null || str2 == null) {
//            return -2;
//        }
//        Date date1;
//        Date date2;
//        try {
//            date1 = DateFormat.getDateInstance().parse(str1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -3;
//        }
//        try {
//            date2 = DateFormat.getDateInstance().parse(str2);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return -4;
//        }
//        return date1.compareTo(date2);
//    }


}
