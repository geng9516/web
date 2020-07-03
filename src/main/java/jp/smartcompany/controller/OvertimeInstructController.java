package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.OvertimeInstructBean;
import jp.smartcompany.job.modules.tmg.overtimeInstruct.vo.*;
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
public class OvertimeInstructController extends AbstractController {

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


    /**
     * 超過勤務実績月別平均画面
     */
    @GetMapping("avgTime")
    public AvgMonthlyVo getTableTop(@RequestParam("baseMonth")String baseMonth,
                                    @RequestParam("empId")String empId,
                                    @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return overtimeInstructBean.actionexecuteDisp6MonthsAvg(empId,baseMonth,psDBBean);
    }


    /**
     * 超過勤務编辑画面
     */
    @GetMapping("editData")
    public List<DailyOverTimeVo> getEditdata(@RequestParam("baseDay")String baseDay,
                                    @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
         return overtimeInstructBean.actionExecuteEdit(baseDay,psDBBean);
    }
}
