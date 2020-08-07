package jp.smartcompany.controller;

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
@RequestMapping("sys/wmanage")
public class SiteWManageController {

    /**
     * 跳转到カレンダー管理界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("calendar")
    public String toWManageCalendar(@RequestParam("moduleIndex") Integer moduleIndex,
                                    @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/wmanage/calendar";
    }

    /**
     * 跳转到年次休暇管理界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("vacation")
    public String toWManageVacation(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/vacation";
    }


    /**
     * 跳转到月次集計データ作成界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("sumdata")
    public String toWManageSumData(@RequestParam("moduleIndex") Integer moduleIndex,
                                   @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);

        return "sys/wmanage/sumdata";
    }

    /**
     * 跳转到帳票出力界面
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("csvoutput")
    public String toWManageCsvOutput(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/csvoutput";
    }
    /**
     * 跳转到CSV取込界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("csvimport")
    public String toWManageCsvImport(@RequestParam("moduleIndex") Integer moduleIndex,
                                     @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/wmanage/csvimport";
    }

    /**
     * 跳转到 出勤薄
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("attendancebook")
    public String toWManageAttendancebook(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/attendancebook";
    }

    /**
     * 跳转到承認状況一覧界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("permstatlist")
    public String toManagePermstatList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/permstatlist";
    }


    /**
     * 跳转到 超過勤務命令
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("overtimeinstruct")
    public String toWManageOvertimeInstruct(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/overtimeinstruct";
    }

    /**
     * 跳转到承認状況一覧界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("deptstatlist")
    public String toDeptStatList(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/wsum";
    }

    /**
     * 跳转到休暇承認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("tmgnotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex,
                                    @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/wmanage/tmgnotification";
    }

    /**
     * 跳转到 勤務パターン
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("patternsetting")
    public String patternSetting(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/patternsetting";
    }


    /**
     * 跳转到 連携対者マスタ設定
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("tmgifsimulation")
    public String simulation(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/simulation";
    }



    /**
     * 跳转到 予定作成
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("tmgschedule")
    public String toTmgSchedule(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/tmgschedule";
    }

    /**
     * 跳转到 年5日有給指定
     *
     * @param moduleIndex
     * @param menuId
     * @return
     */
    @RequestMapping("tmgacquired5daysholiday")
    public String toTmgAcquired5DaysHoliday(
            @RequestParam(value = "moduleIndex") Integer moduleIndex,
            @RequestParam(value = "menuId") Long menuId,
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            ModelMap modelMap
    ) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/TmgAcquired5DaysHoliday";
    }


    /**
     * 跳转到承認状況一覧界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("userprofile")
    public String toEmpattrsetting(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/empattrsetting";
    }


    /**
     * 跳转到就業承認界面
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("tmgresults")
    public String toTmgResults( @RequestAttribute("BeanName") PsDBBean psDBBean,
                                @RequestParam("moduleIndex") Integer moduleIndex,
                               @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {

        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId)
                .addAttribute("targetSection", referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE, TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG, TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION, TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/addwork";
    }

    /**
     * 跳转到权限设定
     *
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("evaluatersetting")
    public String toWManagePerms(
                          @RequestAttribute("BeanName") PsDBBean psDBBean,
                          @RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex)
                .addAttribute("menuId", menuId);
        return "sys/wmanage/evaluatersetting";
    }

}
