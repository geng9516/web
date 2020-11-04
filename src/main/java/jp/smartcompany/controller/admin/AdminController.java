package jp.smartcompany.controller.admin;

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
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    /**
     * 跳转到ユーザ管理界面
     */
    @GetMapping("usermanager")
    public String toUserManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex);
      return "sys/settings/usersettings";
    }

    /**
     * 跳转到アプリケーション設定界面
     */
    @GetMapping("appmanager")
    public String toAppManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/settings/appmanager";
    }

    /**
     * 跳转到グループ定義界面
     */
    @GetMapping("groupmanager")
    public String toGroupManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/settings/groupsettings";
    }

    /**
     * 跳转到起動権限設定界面
     */
    @GetMapping("groupappmanager")
    public String toGroupAppManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/settings/groupappmanager";
    }

    /**
     * 跳转到检索对象范围界面
     */
    @GetMapping("searchrangemanager")
    public String toSearchRangeManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/settings/searchrangemanager";
    }

}
