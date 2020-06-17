package jp.smartcompany.controller;

import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.patternsetting.PatternSettingBean;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.RestTimeLimitDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternAppliesDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.TmgPatternVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 陳毅力
 * @description 勤務設定コントロール
 * @objectSource
 * @date 2020/06/15
 **/
@RestController
@RequestMapping("sys/patternSetting")
public class PatternSettingController {

    @Autowired
    private PatternSettingBean patternSettingBean;

    /**
     * 【表示】TMG_PATTERNより利用可能な勤務パターンを取得する
     * tpa_csectionid = tpa_cgroupid.substr(0,indexOf(tpa_cgroupid,'|'))の場合、編集できます
     * パラメータの中で特殊文字があるから、 get request できない　　（post，form だけ）
     * http://localhost:6879/sys/patternSetting/selectTmgPattern?groupId=100000000000
     *
     * @param groupId
     */
    @PostMapping("selectTmgPattern")
    public List<TmgPatternDTO> selectTmgPattern(@RequestParam("groupId") String groupId,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectTmgPattern(groupId);

    }

    /**
     * 【表示】TMG_PATTERNより利用可能な勤務パターンを取得する(自組織分)
     * http://localhost:6879/sys/patternSetting/selectTmgPatternOwn?groupId=100000000000&sectionId=100000000000
     *
     * @param groupId
     * @param sectionId
     */
    @PostMapping("selectTmgPatternOwn")
    public List<TmgPatternDTO> selectTmgPatternOwn(@RequestParam("groupId") String groupId,
                                                   @RequestParam("sectionId") String sectionId,
                                                   @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectTmgPatternOwn(groupId, sectionId);

    }

    /**
     * 【表示】該当者毎に設定されている勤務パターンの情報を取得する（部署社員リスト）
     * http://localhost:6879/sys/patternSetting/selectTmgPatternApplies?baseDate=2020/06/15
     *
     * @param baseDate
     */
    @GetMapping("selectTmgPatternApplies")
    public List<TmgPatternAppliesDTO> selectTmgPatternApplies(@RequestParam("baseDate") String baseDate,
                                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(baseDate, psDBBean);
        return patternSettingBean.selectTmgPatternApplies(baseDate);

    }

    /**
     * 【編集】勤務時間・休憩時間の制限値取得
     * http://localhost:6879/sys/patternSetting/selectRestTimeLimit
     *
     * @param psDBBean
     */
    @GetMapping("selectRestTimeLimit")
    public List<RestTimeLimitDTO> selectRestTimeLimit(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectRestTimeLimit();
    }

    /**
     * 【編集】編集パターン情報
     * 　   　* パラメータの中で特殊文字があるから、 get request できない　　（post，form だけ）
     * http://localhost:6879/sys/patternSetting/selectEditPatternInfo?groupId=100000000000&patternId=1234
     *
     * @param groupId
     * @param patternId
     * @param psDBBean
     * @return
     */
    @PostMapping("selectEditPatternInfo")
    public TmgPatternVO selectEditPatternInfo(@RequestParam("groupId") String groupId,
                                              @RequestParam("patternId") String patternId,
                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectEditPatternInfo(groupId, patternId);
    }

    /**
     * 【編集】編集パターン情報
     * パラメータの中で特殊文字があるから、 get request できない　　（post，form だけ）
     * http://localhost:6879/sys/patternSetting/deletePattern?groupId=100000000000|000000&sectionId=100000000000&patternId=23238989
     *
     * @param groupId
     * @param patternId
     * @param psDBBean
     * @return
     */
    @PostMapping("deletePattern")
    public void deletePattern(@RequestParam("groupId") String groupId,
                              @RequestParam("sectionId") String sectionId,
                              @RequestParam("patternId") String patternId,
                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        patternSettingBean.deletePattern(groupId, sectionId, patternId);
    }


}