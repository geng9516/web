package jp.smartcompany.controller;


import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.MonthlyOutputBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.OvertimeInstructBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.ResultMonthlyVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/sumdate")
public class MonthlyOutputController {


    @Autowired
    private MonthlyOutputBean monthlyOutputBean;



    /**
     * 超過勤務実績一覧画面表示
     *
     */
    @GetMapping("monthlyOutputDisp")
    public void actionExecuteDispResult(@RequestParam(value = "action",required=false)String action,
                                                         @RequestParam("baseMonth")String baseMonth
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList();
         monthlyOutputBean.actionExecuteDispRMonthlyOutput(action,psDBBean,referList);
    }
}
