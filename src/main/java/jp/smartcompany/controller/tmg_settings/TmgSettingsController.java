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
        return "sys/master_settings/namesettings";
    }

}
