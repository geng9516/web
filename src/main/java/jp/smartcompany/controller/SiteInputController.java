package jp.smartcompany.controller;

import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.DetailDto;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiao Wenpeng
 * 就業承認・管理Controller
 */
@Controller
@RequestMapping("sys/input")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SiteInputController {

    private final TmgResultsBean tgResultsBean;
    /**
     * 跳转到打刻界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("sign")
    public String toInputSign(@RequestParam("moduleIndex") Integer moduleIndex,
                          @RequestParam("menuId") Long menuId, ModelMap modelMap) {
      modelMap.addAttribute("moduleIndex",moduleIndex)
            .addAttribute("menuId",menuId);
      return "sys/input/sign";
    }

    /**
     * 跳转到就業登録界面
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("addwork")
    public String toInputAddWork(@RequestParam("moduleIndex") Integer moduleIndex,
                                 @RequestParam("menuId") Long menuId, ModelMap modelMap,
                                 @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        if (isMobile) {
            return "mobile/index";
        }
//        TmgResultsDto dto = this.ssss();
////        tgResultsBean.actDispRmonthly(psDBBean);
        return "sys/input/addwork";
    }

    /**
     * 跳转到休暇・休業申請
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("vapply")
    public String toInputVApply(@RequestParam("moduleIndex") Integer moduleIndex,
                                @RequestParam("menuId") Long menuId, ModelMap modelMap,
                                @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        if (isMobile) {
            return "mobile/restapply";
        }
        return "sys/input/vapply";
    }

    /**
     * 跳转到出勤簿
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("wschedule")
    public String toInputWSchedule(
            @RequestParam("moduleIndex") Integer moduleIndex,
            @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/input/wschedule";
    }

    /**
     * 予定確認
     * @param moduleIndex
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("oconfirm")
    public String oConfirm(@RequestParam("moduleIndex") Integer moduleIndex,
                           @RequestParam("menuId") Long menuId, ModelMap modelMap,
                           @RequestAttribute("isMobile")Boolean isMobile) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        if (isMobile) {
            return "mobile/scheduleconfirm";
        }
        return "sys/input/oconfirm";
    }

}
