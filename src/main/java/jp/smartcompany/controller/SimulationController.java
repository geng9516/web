package jp.smartcompany.controller;

import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.TmgIfSimulationBean;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ConditionColDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 陳毅力
 * @description 連携対象マスタ
 * @objectSource
 * @date 2020/07/30
 **/
@RestController
@RequestMapping("sys/simulation")
public class SimulationController {

    @Autowired
    private TmgIfSimulationBean tmgIfSimulationBean;

    /**
     * 連携対象マスタリスト
     * 　http://localhost:6879/sys/simulation/selectSimulationMasterList?custId=01&compCode=01&language=ja&psGroupId=TMG_EXCLUDE4THW_SIM
     *
     * @param custId
     * @param compCode
     * @param language
     * @param psGroupId
     * @return
     */
    @GetMapping("selectSimulationMasterList")
    public List<SimulationDTO> selectSimulationMasterList(
            @RequestParam("custId") String custId,
            @RequestParam("compCode") String compCode,
            @RequestParam("language") String language,
            @RequestParam("psGroupId") String psGroupId) {
        //
        return tmgIfSimulationBean.selectSimulationMasterList(custId, compCode, language, psGroupId);

    }

    /**
     * マスタリスト
     * http://localhost:6879/sys/simulation/selectExcludecondCtl?custId=01&compCode=01&language=ja
     *
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    @GetMapping("selectExcludecondCtl")
    public List<ConditionColDTO> selectExcludecondCtl(
            @RequestParam(value = "custId", required = true) String custId,
            @RequestParam(value = "compCode", required = true) String compCode,
            @RequestParam(value = "language", required = true) String language,
            @RequestParam(value = "psGroupId", required = true) String genericgroupId,
            @RequestParam(value = "startDate", required = true) String startDate,
            @RequestParam(value = "endDate", required = true) String endDate) {
        return tmgIfSimulationBean.selectExcludecondCtl(custId, compCode, language, genericgroupId, startDate, endDate);
    }

    /**
     * 　マスタを追加する又は更新する
     *
     * @param masterResult フォーム
     */
    @PostMapping("simulationMerge")
    public GlobalResponse simulationMerge(@RequestParam("masterResult") String masterResult) {
        return tmgIfSimulationBean.simulationMerge(masterResult);
    }

    /**
     * 期間時間をチャックする
     *
     * @param custId
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    @PostMapping("checkPeriodDate")
    public boolean checkPeriodDate(@RequestParam(value = "custId", required = true) String custId,
                                   @RequestParam(value = "compCode", required = true) String compCode,
                                   @RequestParam(value = "language", required = true) String language,
                                   @RequestParam(value = "psGroupId", required = true) String genericgroupId,
                                   @RequestParam(value = "startDate", required = true) String startDate,
                                   @RequestParam(value = "endDate", required = true) String endDate) {
        return tmgIfSimulationBean.checkPeriodDate(custId, compCode, language, genericgroupId, startDate, endDate);
    }

    /**
     * マスタ」データが削除する
     * @param custId
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    @PostMapping("deleteMastGeneric")
    public boolean deleteMastGenericDetail(@RequestParam(value = "custId", required = true) String custId,
                                           @RequestParam(value = "compCode", required = true) String compCode,
                                           @RequestParam(value = "language", required = true) String language,
                                           @RequestParam(value = "psGroupId", required = true) String genericgroupId,
                                           @RequestParam(value = "startDate", required = true) String startDate,
                                           @RequestParam(value = "endDate", required = true) String endDate){
        return tmgIfSimulationBean.deleteMastGenericDetail(custId, compCode, language, genericgroupId, startDate, endDate);
    }
}
