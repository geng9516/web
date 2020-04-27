package jp.smartcompany.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Xiao Wenpeng
 * 就業承認・管理Controller
 */
@Controller
@RequestMapping("sys/manage")
public class SiteManageController {

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


}
