package jp.smartcompany.controller;


import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.MonthlyOutputBean;
import jp.smartcompany.job.modules.tmg.monthlyoutput.vo.*;
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
     * 集計時の問題(アラート)ダイアログの表示プロセス
     *
     */
    @GetMapping("executeNotAppList_RAlertList")
    public NotAppSectionListVo actionExecuteNotAppList_RAlertList(@RequestParam("baseDate")String baseDate,
                                                                         @RequestParam("sPage")String sPage
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.executeNotAppList_RAlertList( baseDate, sPage, psDBBean, referList);
    }


    /**
     * 締め処理
     *
     */
    @PostMapping("approval")
    public GlobalResponse actionExecuteDispUFIXESMONTHLY(@RequestParam("baseDate")String baseDate
                    , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDispUFIXESMONTHLY( baseDate, psDBBean, referList);
    }

    /**
     * 締め取消処理
     *
     */
    @PostMapping("approvalCancel")
    public GlobalResponse actionExecuteDisp_DFIXESMONTHLY(@RequestParam("baseDate")String baseDate
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDisp_DFIXESMONTHLY( baseDate, psDBBean, referList);
    }

    /**
     * 確認・確認取消処理
     *action　C：確認　D：取消
     */
    @PostMapping("changeFix")
    public GlobalResponse actionExecuteChangeFix(@RequestParam("baseDate")String baseDate,
                                                 @RequestParam("action")String action,
             @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteChangeFix( baseDate,  action,  psDBBean,  referList);
    }


    /**
     * 集計処理表示
     *
     */
    @GetMapping("totalDisp")
    public List<SectionAdminMailVo> actionExecuteDispRCalc(@RequestParam("baseDate")String baseDate
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteDispRCalc( baseDate, psDBBean, referList);
    }


    /**
     * 集計処理
     *
     */
    @PostMapping("totalStart")
    public GlobalResponse actionExecuteCALCCCALC(@RequestParam("baseDate")String baseDate
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        return  monthlyOutputBean.actionExecuteCALCCCALC( baseDate, psDBBean, referList);
    }


    /**
     * ダウンロード画面表示
     *
     */
    @GetMapping("downloadDisp")
    public List<MoDLTypeVo> actionExecuteDispRDownloadView(@RequestParam("baseDate")String baseDate
            , @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return  monthlyOutputBean.actionExecuteDispRDownloadView( baseDate, psDBBean);
    }

    /**
     * ダウンロード画面表示
     *
     */
    @GetMapping("downloadFile")
    public void actionexecuteDownloadCDownload(@RequestParam("checkValue")boolean checked,
                                               @RequestParam("baseDate")String baseDate,
                                               @RequestParam("sRetroFlg")String sRetroFlg,
                                               @RequestParam("dlTypeId")String dlTypeId,
                                               @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        TmgReferList referList =new TmgReferList(psDBBean,"MonthlyOutput",baseDate,TmgReferList.TREEVIEW_TYPE_DIVLIST,true);
        monthlyOutputBean.actionexecuteDownloadCDownload(checked, baseDate,sRetroFlg ,dlTypeId,psDBBean,referList);
    }

}
