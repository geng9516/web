package jp.smartcompany.controller;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.TmgLiquidationPeriodBean;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.LiquidationUpdateListDto;
import jp.smartcompany.job.modules.tmg.tmgliquidationperiod.dto.WorkTypeGroupDto;
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
    public List<EditDispVo> getEditDisp(
            @RequestParam(value = "tlpId",required = false) String tlpId,
            @RequestParam(value = "startDate",required = false) String startDate,
            @RequestParam(value = "endDate",required = false) String endDate,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {

        return tmgLiquidationPeriodBean.getEditDisop(tlpId,startDate,endDate,psDBBean);
    }


    /**
     * 新规
     * @return　エラー
     */
    @PostMapping("InsertLiquidation")
    @ResponseBody
    public GlobalResponse insertLiquidation(
            @RequestBody LiquidationUpdateListDto liquidationUpdateListDto,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgLiquidationPeriodBean.insertLiquidation(liquidationUpdateListDto,psDBBean);
    }

    /**
     * 删除
     * @return　エラー
     */
    @PostMapping("deleteLiquidation")
    public GlobalResponse deleteLiquidation(
            @RequestParam("tlpId") String tlpId,
            @RequestAttribute("BeanName") PsDBBean psDBBean) throws Exception {
        return tmgLiquidationPeriodBean.deleteLiquidation(tlpId);
    }
}
