package jp.smartcompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestAttribute;


/**
 * @author Xiao Wenpeng
 * 就業承認・管理Controller
 */
@Controller
@RequestMapping("sys/input")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteInputController {

    /**
     * 跳转到打刻界面
     */
    @GetMapping("sign")
    public String toInputSign(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex);
      return "sys/input/sign";
    }

    /**
     * 跳转到就業登録界面
     */
    @GetMapping("addwork")
    public String toInputAddWork(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                                 @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isMobile) {
            return "mobile/index";
        }
        return "sys/input/addwork";
    }

    /**
     * 跳转到休暇・休業申請
     */
    @GetMapping("vapply")
    public String toInputVApply(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                                @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isMobile) {
            return "mobile/restapply";
        }
        return "sys/input/vapply";
    }

    /**
     * 跳转到出勤簿
     */
    @GetMapping("wschedule")
    public String toInputWSchedule(
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/input/wschedule";
    }

    /**
     * 予定確認
     */
    @GetMapping("oconfirm")
    public String oConfirm(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                           @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isMobile) {
            return "mobile/scheduleconfirm";
        }
        return "sys/input/oconfirm";
    }

}
