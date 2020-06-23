package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.permStatList.PermStatListBean;
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
 * 就業承認・管理Controller
 */
@Controller
@RequestMapping("sys/manage")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteManageController {

    private final PermStatListBean permStatListBean;
    /**
     * 跳转到部署別統計情報確認界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("wsum")
    public String toManageWSum(@RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex)
            .addAttribute("menuId",menuId);
      return "sys/manage/wsum";
    }

    /**
     * 跳转到勤務パターン設定界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("wpattern")
    public String toManageAddWork(@RequestParam("moduleIndex") Integer moduleIndex,
                                 @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/manage/wpattern";
    }

    /**
     * 跳转到年5日時季指定取得確認界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("require5days")
    public String toManageVApply(@RequestParam("moduleIndex") Integer moduleIndex,
                                @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/manage/require5days";
    }
    @GetMapping("attendancebook")
    public String toAttendanceBook(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute("targetGroup",referList.getTargetGroup())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP,TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP,TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/attendancebook";
    }

    @GetMapping("toTmgSchedule")
    public String toTmgSchedule(
            @RequestAttribute("BeanName") PsDBBean psDBBean,
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) throws Exception {
        String baseDate = DateUtil.format(DateUtil.date(), TmgReferList.DEFAULT_DATE_FORMAT);
        TmgReferList referList = new TmgReferList(psDBBean, psDBBean.getAppId(), baseDate, TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, false, false, true);
        modelMap
                .addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute("targetGroup",referList.getTargetGroup())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP,TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_GROUP,TmgReferList.TREEVIEW_KEY_PERM_TARGET_GROUP);
        return "sys/manage/tmgschedule";
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
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId)
                .addAttribute("targetSection",referList.getTargetSec())
                .addAttribute(TmgReferList.ATTR_TREEVIEW_RECORD_DATE,TmgReferList.TREEVIEW_KEY_RECORD_DATE)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_REFRESH_FLG,TmgReferList.TREEVIEW_KEY_REFRESH_FLG)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_EMP,TmgReferList.TREEVIEW_KEY_PERM_TARGET_EMP)
                .addAttribute(TmgReferList.ATTR_TREEVIEW_PERM_TARGET_SECTION,TmgReferList.TREEVIEW_KEY_PERM_TARGET_SECTION);
        return "sys/manage/permstatlist";
    }

    /**
     * 跳转到休暇承認界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("tmgnotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex,
                               @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/manage/tmgnotification";
    }
}
