package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.OvertimeInstructBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.MonthlyInfoOtVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.OneMonthDetailVo;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.ResultMonthlyVo;
import jp.smartcompany.job.modules.tmg.tmgnotification.vo.TypeGroupVo;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wang Ziyue
 * @description test controller
 * @objectSource
 * @date 2020/06/11
 **/
@RestController
@RequestMapping("sys/overtimeinstruct")
public class OvertimeInstructController {

    @Autowired
    private OvertimeInstructBean overtimeInstructBean;

    /**
     * 超過勤務実績一覧画面表示
     *
     */
    @GetMapping("monthlyResult")
    public List<ResultMonthlyVo> actionExecuteDispResult(@RequestParam(value = "action",required=false)String action,
                                                         @RequestParam("baseMonth")String baseMonth
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return overtimeInstructBean.actionExecuteDispResult(action,baseMonth,psDBBean);
    }


    /**
     * 超過勤務命令一覧画面表示
     *
     */
    @GetMapping("monthlyResultOti")
    public List<MonthlyInfoOtVo> actionExecuteDisp(@RequestParam("baseMonth")String baseMonth
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return overtimeInstructBean.actionExecuteDisp(baseMonth,psDBBean);
    }


    /**
     * 表头
     */
    @GetMapping("tableTop")
    public List<OneMonthDetailVo> getTableTop(@RequestParam("baseMonth")String baseMonth
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return overtimeInstructBean.getTableTop(baseMonth,psDBBean);
    }
}
