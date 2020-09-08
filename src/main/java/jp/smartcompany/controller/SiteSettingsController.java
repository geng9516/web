package jp.smartcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Xiao Wenpeng
 * システム管理メニューController
 */
@Controller
@RequestMapping("sys/settings")
@RequiredArgsConstructor
public class SiteSettingsController {

    /**
     * 跳转到ユーザ管理界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("usersettings")
    public String toSettingsUserSettings(@RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex)
            .addAttribute("menuId",menuId);
      return "sys/settings/usersettings";
    }

    /**
     * 跳转到グループ定義界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("groupsettings")
    public String toSettingsGroupSettings(@RequestParam("moduleIndex") Integer moduleIndex,
                                 @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/settings/groupsettings";
    }

    /**
     * 跳转到起動権限設定界面
     */
    @GetMapping("groupappmanager")
    public String toGroupAppManager(@RequestParam("moduleIndex") Integer moduleIndex,
                                    @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/settings/groupappmanager";
    }

}
