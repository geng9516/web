package jp.smartcompany.job.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.paidholiday.PaidholidayBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
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
    @RequestMapping("vacation")
    public String toWManageVacation(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam(value = "moduleIndex",required = false) Integer moduleIndex,
            @RequestParam(value = "menuId",required = false) Long menuId,
            ModelMap modelMap) throws Exception {
        if (moduleIndex!=null) {
            modelMap.addAttribute("moduleIndex", moduleIndex)
                    .addAttribute("menuId", menuId);
        }
        paidHolidayBean.actionInitHandler(modelMap);
        String baseDate = DateUtil.format(DateUtil.date(),TmgReferList.DEFAULT_DATE_FORMAT);
        psDBBean.requestHash.put("SiteId", TmgUtil.Cs_SITE_ID_TMG_ADMIN);
        TmgReferList referList = new TmgReferList(psDBBean, "TmgSample", baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        referList.putReferList(modelMap);
        modelMap.addAttribute("psDBBean",psDBBean);
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

}
