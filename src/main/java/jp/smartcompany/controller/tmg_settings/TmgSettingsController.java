package jp.smartcompany.controller.tmg_settings;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("tmg_setting")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgSettingsController {

    @GetMapping("genericmanager")
    public String toGenericManager(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/genericmanager";
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

    @GetMapping("logviewer")
    public String toLogViewer(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/master_settings/logviewer";
    }

}
