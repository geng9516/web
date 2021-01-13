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
@RequiredArgsConstructor
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
                               @RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                               @RequestAttribute("isAndroid") Boolean isAndroid,
                               @RequestAttribute("isAndroidPad") Boolean isAndroidPad) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isIPhoneOrIPod || isAndroid) {
            if (isAndroidPad) {
                return "sys/input/addwork";
            }
            return "mobile/index";
        }
        return "sys/input/addwork";
    }

    /**
     * 跳转到休暇・休業申請
     */
    @GetMapping("TmgNotification")
    public String toTmgNotification(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                                    @RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                                    @RequestAttribute("isAndroid") Boolean isAndroid,
                                    @RequestAttribute("isAndroidPad") Boolean isAndroidPad) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isIPhoneOrIPod || isAndroid) {
            if (isAndroidPad) {
                return "sys/input/vapply";
            }
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
                                     @RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                                     @RequestAttribute("isAndroid") Boolean isAndroid,
                                     @RequestAttribute("isAndroidPad") Boolean isAndroidPad) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isIPhoneOrIPod || isAndroid) {
            if (isAndroidPad) {
                return "sys/input/oconfirm";
            }
            return "mobile/scheduleconfirm";
        }
        return "sys/input/oconfirm";
    }
    /**
     * 跳转到在宅勤务
     */
    @GetMapping("TmgHomeWork")
    public String toHomeWork(@RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap,
                             @RequestAttribute("isIPhoneOrIPod") Boolean isIPhoneOrIPod,
                             @RequestAttribute("isAndroid") Boolean isAndroid,
                             @RequestAttribute("isAndroidPad") Boolean isAndroidPad) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        if (isIPhoneOrIPod || isAndroid) {
            if (isAndroidPad) {
                return "sys/input/inphomework";
            }
            return "mobile/mobhomework";
        }
        return "sys/input/inphomework";
    }

    /**
     * 跳转到揭示板
     */
    @GetMapping("NoticeBoard")
    public String toNoticeBoard(
            @RequestParam("moduleIndex") Integer moduleIndex, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex);
        return "sys/input/noticeboard";
    }
}
