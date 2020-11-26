package jp.smartcompany.controller;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.TmgLiquidationPeriodBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.*;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.EditDispVo;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.vo.LiquidationDispVo;
import jp.smartcompany.job.modules.tmg.tmgresults.dto.TmgResultsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        if(!StrUtil.hasEmpty(workType)){
            return tmgLiquidationPeriodBean.getLiquidationDisp(workType,psDBBean);
        }else{
            return tmgLiquidationPeriodBean.getLiquidationDisp(type,searchText,psDBBean);
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

        return tmgLiquidationPeriodBean.getEditDisop(empId,startDate,endDate,psDBBean);
    }


    /**
     * 新规
     * @return　エラー
     */
    @PostMapping("InsertLiquidation")
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
     * 編集画面
     * @return　エラー
     */
    @GetMapping("getEditDisop")
    public EditDispVo getEditDisop(
            @RequestParam("empId") String empId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgLiquidationPeriodBean.getEditDisop(empId,startDate,endDate,psDBBean);
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
            @RequestAttribute("BeanName") PsDBBean psDBBean){
        return tmgLiquidationPeriodBean.UpdateLiquidationDaily(monthList,monthList.getEmpId(),monthList.getStartDate(),monthList.getEndDate(),psDBBean);
    }


    /**
     * 新規パターン
     * @return　エラー
     */
    @PostMapping("insertPattern")
    @ResponseBody
    public GlobalResponse insertPattern(
            PatternInfoDto patternInfoDto,
            @RequestAttribute("BeanName") PsDBBean psDBBean){
         return tmgLiquidationPeriodBean.insertPattern(patternInfoDto,psDBBean);
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
            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        return tmgLiquidationPeriodBean.upload(empId,startDate,endDate,psDBBean);
    }
}
