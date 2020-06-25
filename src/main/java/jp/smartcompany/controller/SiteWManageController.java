package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.OvertimeInstructBean;
import jp.smartcompany.job.modules.tmg.paidholiday.PaidholidayBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteWManageController {

    private final PaidholidayBean paidHolidayBean;
    private final OvertimeInstructBean overtimeInstructBean;
    /**
     * 跳转到カレンダー管理界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("calendar")
    public String toWManageCalendar(@RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex)
            .addAttribute("menuId",menuId);
      return "sys/wmanage/calendar";
    }

    /**
     * 跳转到年次休暇管理界面
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
        String baseDate = DateUtil.format(DateUtil.date(),TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/vacation";
    }


    /**
     * 跳转到月次集計データ作成界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("sumdata")
    public String toWManageSumData(@RequestParam("moduleIndex") Integer moduleIndex,
                                @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);

        return "sys/wmanage/sumdata";
    }

    /**
     * 跳转到帳票出力界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("csvoutput")
    public String toWManageCsvOutput(@RequestParam("moduleIndex") Integer moduleIndex,
                                   @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/wmanage/csvoutput";
    }

    /**
     * 跳转到CSV取込界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("csvimport")
    public String toWManageCsvImport(@RequestParam("moduleIndex") Integer moduleIndex,
                                     @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/wmanage/csvimport";
    }

    /**
     * 跳转到 出勤薄
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
        // 先同样作为管理site的树来看待
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/attendancebook";
    }

    /**
     * 跳转到承認状況一覧界面
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
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/permstatlist";
    }



    /**
     * 跳转到 出勤薄
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
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_EMP,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_ADMIN_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/overtimeinstruct";
    }

}
