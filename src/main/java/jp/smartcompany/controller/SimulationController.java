package jp.smartcompany.controller;

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
            @RequestParam("custId") String custId,
            @RequestParam("compCode") String compCode,
            @RequestParam("language") String language) {
        return tmgIfSimulationBean.selectExcludecondCtl(custId, compCode, language);
    }

    /**
     *　マスタを追加する又は更新する
     * @param masterResult フォーム
     */
    @PostMapping("simulationMerge")
    @ResponseBody
    public void simulationMerge(@RequestParam("masterResult") String masterResult){
         tmgIfSimulationBean.simulationMerge(masterResult);
    }


}
