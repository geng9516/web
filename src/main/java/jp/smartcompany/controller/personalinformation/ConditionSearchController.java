package jp.smartcompany.controller.personalinformation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("personalinformation")
public class ConditionSearchController {

    @GetMapping("conditionsearch")
    public String toConditionSearch(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex", moduleIndex);
        return "sys/personalinformation/conditionsearch";
    }

}
