package jp.smartcompany.job.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.paidholiday.PaidholidayBean;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayInitVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Xiao Wenpeng
 * 就業管理Controller
 */
@Controller
@RequestMapping("sys/wmanage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteWManageController {

    private final PaidholidayBean paidHolidayBean;

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
            ModelMap modelMap) {
        modelMap
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute(TmgReferList.TREEVIEW_KEY_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.TREEVIEW_KEY_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_EMP)
                .addAttribute(TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_ADMIN_TARGET_SECTION);
        return "sys/wmanage/vacation";
    }

    /**
     * 年次休暇管理一览界面数据获取
     */
    @GetMapping("vacation/list")
    @ResponseBody
    public List<PaidHolidayInitVO> wManageVacation(@RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(),TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_LIST_SEC, true,
                true, false, false, true);
        return paidHolidayBean.actionInitHandler(referList.buildSQLForSelectEmployees());
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
            @RequestParam(value = "menuId") Long menuId) throws Exception {
        return "sys/manage/attendancebook";
    }

}
