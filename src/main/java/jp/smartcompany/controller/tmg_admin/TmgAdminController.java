package jp.smartcompany.controller.tmg_admin;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Xiao Wenpeng
 * 就業管理Controller
 */
@Controller
@RequestMapping("tmg_admin")
public class TmgAdminController {

    /**
     * 跳转到カレンダー管理界面
     */
    @GetMapping("Calendar")
    public String toCalendar(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);
        return "sys/wmanage/calendar";
    }

    /**
     * 跳转到年次休暇管理界面
     */
    @GetMapping("PaidHoliday")
    public String toPaidHoliday(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/vacation";
    }


    /**
     * 跳转到月次集計データ作成界面
     */
    @GetMapping("MonthlyOutput")
    public String toMonthlyOutput(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);

        return "sys/wmanage/sumdata";
    }

    /**
     * 跳转到帳票出力界面
     */
    @RequestMapping("TmgLedger")
    public String toTmgLedger(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/csvoutput";
    }

    /**
     * 跳转到CSV取込界面
     */
    @GetMapping("csvimport")
    public String toWManageCsvImport(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);
        return "sys/wmanage/csvimport";
    }

    /**
     * 跳转到 出勤薄
     */
    @RequestMapping("AttendanceBook")
    public String toAttendanceBook(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/attendancebook";
    }

    /**
     * 跳转到承認状況一覧界面
     */
    @GetMapping("PermStatList")
    public String toPermStatList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/permstatlist";
    }


    /**
     * 跳转到 超過勤務命令
     */
    @RequestMapping("OvertimeInstruct")
    public String toOvertimeInstruct(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/overtimeinstruct";
    }

    /**
     * 跳转到部署别统计情报页面
     */
    @GetMapping("DeptStatList")
    public String toDeptStatList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/wsum";
    }

    /**
     * 跳转到休暇休业承認界面
     */
    @GetMapping("TmgNotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);
        return "sys/wmanage/tmgnotification";
    }

    /**
     * 跳转到 勤務パターン
     */
    @RequestMapping("PatternSetting")
    public String toPatternSetting(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/patternsetting";
    }


    /**
     * 跳转到 連携対者マスタ設定
     */
    @RequestMapping("TmgIfSimulation")
    public String toTmgIfSimulation(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/simulation";
    }



    /**
     * 跳转到 予定作成
     */
    @RequestMapping("TmgSchedule")
    public String toTmgSchedule(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/tmgschedule";
    }

    /**
     * 跳转到 年5日有給指定
     */
    @RequestMapping("TmgAcquired5DaysHoliday")
    public String toTmgAcquired5DaysHoliday(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/TmgAcquired5DaysHoliday";
    }


    /**
     * 跳转到个人属性设定界面
     */
    @GetMapping("EmpAttrSetting")
    public String toEmpAttrSetting(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/empattrsetting";
    }


    /**
     * 跳转到就業承認界面
     */
    @GetMapping("TmgResults")
    public String toTmgResults( @RequestAttribute("BeanName") PsDBBean psDBBean,
                                @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {

        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/addwork";
    }

    /**
     * 跳转到权限设定
     */
    @GetMapping("EvaluaterSetting")
    public String toEvaluaterSetting(
                          @RequestAttribute("BeanName") PsDBBean psDBBean,
                          @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);
        return "sys/wmanage/evaluatersetting";
    }


    /**
     * 跳转到休暇・休業登録界面
     */
    @GetMapping("TmgBulkNotification")
    public String toTmgBulkNotification( @RequestAttribute("BeanName") PsDBBean psDBBean,
                                @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {

        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/tmgbulknotification";
    }


    /**
     * 跳转到休暇・休業登録界面
     */
    @GetMapping("TmgLiquidationPeriod")
    public String toTmgLiquidationPeriod( @RequestAttribute("BeanName") PsDBBean psDBBean,
                                         @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) throws Exception {

        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/tmgliquidationperiod";
    }
}
