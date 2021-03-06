package jp.smartcompany.controller;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.TmgLiquidationPeriodBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.*;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDispVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wang Ziyue
 * @description test controller
 * @objectSource
 * @date 2020/06/11
 **/
@RestController
@RequestMapping("sys/tmgliquidationperiod")
public class TmgLiquidationPeriodController {

    @Autowired
    private TmgLiquidationPeriodBean tmgLiquidationPeriodBean;


    /**
     *获取职种列表
     * @return　エラー
     */
    @GetMapping("WorkTypeList")
    public List<WorkTypeGroupDto> getWorkTypeList(
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return tmgLiquidationPeriodBean.getWorkTypeList(psDBBean);
    }
    /**
     *主页一览
     * @return　エラー
     */
    @GetMapping("Disp")
    public LiquidationDispVo getDispInfo(
            @RequestParam(value = "workType",required = false) String workType,
            @RequestParam(value = "type",required = false) String type,
            @RequestParam(value = "searchText",required = false) String searchText,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        TmgReferList referList= new TmgReferList(psDBBean, "TmgLiquidationPeriodBean",  TmgUtil.getSysdate(), TmgReferList.TREEVIEW_TYPE_EMP, true,
                true, true, false, true);
        if(!StrUtil.hasEmpty(workType)){
            return tmgLiquidationPeriodBean.getLiquidationDisp(workType,psDBBean,referList);
        }else{
            return tmgLiquidationPeriodBean.getLiquidationDisp(type,searchText,psDBBean,referList);
        }

    }
    /**
     *详细页面
     * @return　エラー
     */
    @GetMapping("EditDisp")
    public EditDispVo getEditDisp(
            @RequestParam(value = "empId",required = false) String empId,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return tmgLiquidationPeriodBean.getEditDisp(empId,startDate,endDate,psDBBean);
    }


    /**
     * 新规
     * @return　エラー
     */
    @PostMapping("insertLiquidation")
    @ResponseBody
    public GlobalResponse insertLiquidation(
            @RequestBody LiquidationUpdateListDto liquidationUpdateListDto,
            @RequestAttribute("BeanName") PsDBBean psDBBean){
        return tmgLiquidationPeriodBean.insertLiquidation(liquidationUpdateListDto,psDBBean);
    }

    /**
     * 删除
     * @return　エラー
     */
    @PostMapping("deleteLiquidation")
    public GlobalResponse deleteLiquidation(
            @RequestParam(value = "empId",required = false) String empId,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgLiquidationPeriodBean.deleteLiquidation(empId,startDate,endDate);
    }


    /**
     *pattern 管理
     * @return　エラー
     */
    @GetMapping("getPattern")
    public Map<String ,Object> getPattern(
            @RequestParam(value = "empId",required = false) String empId,
            @RequestAttribute("BeanName") PsDBBean psDBBean){
        Map<String ,Object> patternInfo = new HashMap<>();
        patternInfo.put("patternList",tmgLiquidationPeriodBean.getPatternList(empId,TmgUtil.getSysdate(),psDBBean.getCustID(),psDBBean.getCompCode()));
        patternInfo.put("sectionList",tmgLiquidationPeriodBean.getSectionList(psDBBean));
        return patternInfo;
    }


    /**
     * 月編集画面
     * @return　エラー
     */
    @GetMapping("EditMonthDisp")
    public Map<String, Object> EditMonthDisp(
            @RequestParam("empId") String empId,
            @RequestParam("yyyymm") String yyyymm,
            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgLiquidationPeriodBean.EditMonthDisp(empId,yyyymm,psDBBean);
    }


    /**
     * 月データを変更する
     * @return　エラー
     */
    @PostMapping("UpdateLiquidationDaily")
    @ResponseBody
    public int UpdateLiquidationDaily(
            @RequestBody MonthDto monthList,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws ParseException {
        return tmgLiquidationPeriodBean.UpdateLiquidationDaily(monthList,monthList.getEmpId(),monthList.getStartDate(),monthList.getEndDate(),psDBBean);
    }


    /**
     * 新規パターン
     * @return　エラー
     */
    @PostMapping("insertPattern")
    @ResponseBody
    public GlobalResponse insertPattern(
            @RequestBody PatternInfoDto patternInfoDto,
            @RequestAttribute("BeanName") PsDBBean psDBBean){
         return tmgLiquidationPeriodBean.insertPattern(patternInfoDto,psDBBean);
    }


    /**
     * delete パターン
     * @return　エラー
     */
    @PostMapping("deletePattern")
    @ResponseBody
    public GlobalResponse deletePattern(
            @RequestParam("patternId") String patternId,
            @RequestAttribute("BeanName") PsDBBean psDBBean){
        return tmgLiquidationPeriodBean.deletePattern(patternId,psDBBean);
    }

    /**
     * 精算check
     * @return　エラー
     */
    @PostMapping("checkLiquidationDaily")
    public int checkLiquidationDaily(
            @RequestParam(value = "empId",required = false) String empId,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgLiquidationPeriodBean.checkLiquidationDaily(empId,startDate,psDBBean);
    }

    /**
     * 最後登録
     * @return　エラー
     */
    @PostMapping("upload")
    public GlobalResponse upload(
            @RequestParam(value = "empId",required = false) String empId,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws InterruptedException {
        return tmgLiquidationPeriodBean.upload(empId,startDate,endDate,psDBBean);
    }
}
