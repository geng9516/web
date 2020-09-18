package jp.smartcompany.controller.tmg_inp;

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
@RequestMapping("tmg_inp")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgInpController {

    /**
     * 跳转到打刻界面
     */
    @GetMapping("TmgTimePunch")
    public String toTmgTimePunch(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex);
      return "sys/input/sign";
    }

    /**
     * 跳转到就業登録界面
     */
    @GetMapping("TmgResults")
    public String toTmgResults(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
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
    @GetMapping("TmgNotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
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
    @GetMapping("AttendanceBook")
    public String toAttendanceBook(
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/input/wschedule";
    }

    /**
     * 予定確認
     */
    @GetMapping("TmgScheduleCheck")
    public String toTmgScheduleCheck(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                           @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isMobile) {
            return "mobile/scheduleconfirm";
        }
        return "sys/input/oconfirm";
    }

}
