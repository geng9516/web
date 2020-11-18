package jp.smartcompany.controller.tmg_settings;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tmg_settings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgSettingsController {

    @GetMapping("genericmanager")
    public String toGenericManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/genericmanager";
    }

    /**
     * 跳转到グループ定義界面
     */
    @GetMapping("groupmanager")
    public String toGroupManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/groupmanager";
    }
    /**
     * 跳转到检索对象范围界面
     */
    @GetMapping("searchrangemanager")
    public String toSearchRangeManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/searchrangemanager";
    }

    /**
     * 跳转到属性设定页面
     */
    @GetMapping("propmanager")
    public String toPropManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/propmanager";
    }

    /**
     * 跳转到邮箱设定页面
     */
    @GetMapping("mailmanager")
    public String toMailManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/mailmanager";
    }

}
