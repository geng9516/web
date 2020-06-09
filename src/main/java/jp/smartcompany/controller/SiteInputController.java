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
                                 @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        TmgResultsDto dto = this.ssss();
        tgResultsBean.actDispRmonthly(modelMap);
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
                                @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
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
                           @RequestParam("menuId") Long menuId, ModelMap modelMap) {
        modelMap.addAttribute("moduleIndex",moduleIndex)
                .addAttribute("menuId",menuId);
        return "sys/input/oconfirm";
    }

    private TmgResultsDto ssss(){
        TmgResultsDto dto = new TmgResultsDto();
       dto.setHoliday(null);
       dto.setSelMGD_CBUSINESS_TRIP("TMG_BUSINESS_TRIP|00");
        dto.setSelTDA_CWORKINGID_R("TMG_WORK|000");
        dto.setTxtTDA_CBOSSCOMMENT_R("承認者承認済み");
        dto.setTxtTDA_COWNCOMMENT_R("本人登録");
        dto.setTxtTDA_NOPEN_R("8:30");
        dto.setTxtTDA_NCLOSE_R("17:30");

        List<DetailDto> nonDutyList = new ArrayList<>();

        dto.setNonDutyList(nonDutyList);
        List<DetailDto> overHoursList= new ArrayList<>();
        dto.setOverHoursList(overHoursList);
        return dto;
    }
}