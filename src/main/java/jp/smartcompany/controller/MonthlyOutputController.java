package jp.smartcompany.controller;


import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.MonthlyOutputBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotAppSectionListVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.NotApprovalListVo;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.TmgMoYearListVo;
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
    public List<TmgMoYearListVo> actionExecuteDispResult(@RequestParam("year")String year
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",year,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDispRMonthlyOutput(year,psDBBean,referList);
    }


    /**
     * 未承認者一覧画面表示処理
     *
     */
    @GetMapping("notApprovalList")
    public NotApprovalListVo actionExecuteDispResult(@RequestParam("baseDate")String baseDate,
                                                     @RequestParam("sPage")String sPage
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteNotAppListRNotAppList( baseDate, sPage, psDBBean, referList);
    }

    /**
     * 締め未完了部局一覧画面表示処理
     *
     */
    @GetMapping("notAppSectionList")
    public NotAppSectionListVo actionExecuteNotAppListRNotAppSectionList(@RequestParam("baseDate")String baseDate,
                                                       @RequestParam("sPage")String sPage
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteNotAppListRNotAppSectionList( baseDate, sPage, psDBBean, referList);
    }


    /**
     * 締め処理
     *
     */
    @GetMapping("approval")
    public GlobalResponse actionExecuteDispUFIXESMONTHLY(@RequestParam("baseDate")String baseDate
                    , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDispUFIXESMONTHLY( baseDate, psDBBean, referList);
    }

    /**
     * 締め取消処理
     *
     */
    @GetMapping("approvalCancel")
    public GlobalResponse actionExecuteDisp_DFIXESMONTHLY(@RequestParam("baseDate")String baseDate
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDisp_DFIXESMONTHLY( baseDate, psDBBean, referList);
    }

}
