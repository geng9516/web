package jp.smartcompany.job.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.schedule.vo.PaidHolidayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author 陳毅力
 * @description 予定作成コントロール
 * @objectSource
 * @date 2020/05/29
 **/
@RestController
@RequestMapping("sys/schedule")
public class ScheduleController {

    @Autowired
    private TmgScheduleBean tmgScheduleBean;


    @GetMapping("selectPaidHolidayInfo")
    @ResponseBody
    public PaidHolidayVO selectPaidHolidayInfo(@RequestParam("employeeId") String employeeId,
                                               @RequestParam("year") String year,
                                               @RequestParam("month") String month, ModelMap modelMap) throws Exception {
        //初期化
        tmgScheduleBean.setExecuteParameters(year, month, employeeId, modelMap);
        return tmgScheduleBean.selectPaidHolidayInfo(year, month, employeeId);
    }


}
