package jp.smartcompany.controller;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.patternsetting.PatternSettingBean;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.RestTimeLimitDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternAppliesDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPatternMergeDTO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.ModifiCSVVO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.PeriodDateVO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.TmgPatternSettingAppliesVO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.TmgPatternVO;
import jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo.UpdateAcquired5DaysVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * http://localhost:6879/sys/patternSetting/selectTmgPattern?sectionId=100000000000
     *
     * @param sectionId
     */
    @GetMapping("selectTmgPattern")
    public List<TmgPatternDTO> selectTmgPattern(@RequestParam("groupId") String groupId,
                                                @RequestParam("sectionId") String sectionId,
                                                @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectTmgPattern(sectionId, groupId);

    }

    /**
     * 【表示】TMG_PATTERNより利用可能な勤務パターンを取得する(自組織分)
     * http://localhost:6879/sys/patternSetting/selectTmgPatternOwn?groupId=100000000000&sectionId=100000000000
     *
     * @param groupId
     * @param sectionId
     */
    @GetMapping("selectTmgPatternOwn")
    public List<TmgPatternDTO> selectTmgPatternOwn(@RequestParam("groupId") String groupId,
                                                   @RequestParam("sectionId") String sectionId,
                                                   @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectTmgPatternOwn(groupId, sectionId);

    }

    /**
     * 【表示】該当者毎に設定されている勤務パターンの情報を取得する（部署職員リスト）
     * http://localhost:6879/sys/patternSetting/selectTmgPatternApplies?baseDate=2020/06/15
     *
     * @param baseDate
     */
    @PostMapping("selectTmgPatternApplies")
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
    public TmgPatternVO selectEditPatternInfo(@RequestParam(value = "groupId", required = false) String groupId,
                                              @RequestParam(value = "patternId", required = true) String patternId,
                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.selectEditPatternInfo(groupId, patternId);
    }

    /**
     * 【編集】削除パターン
     * <p>パラメータの中で特殊文字があるから、 get request できない　　（post，form だけ）
     * <p>http://localhost:6879/sys/patternSetting/deletePattern?groupId=100000000000|000000&sectionId=100000000000&patternId=23238989
     *
     * @param groupId
     * @param patternId
     * @param psDBBean
     * @return
     */
    @PostMapping("deletePattern")
    public void deletePattern(@RequestParam(value = "groupId", required = false) String groupId,
                              @RequestParam("sectionId") String sectionId,
                              @RequestParam("patternId") String patternId,
                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        patternSettingBean.deletePattern(groupId, sectionId, patternId);
    }

    /**
     * 【編集&新規】勤務パターン更新またはインサート
     * <p>get request できない　　（post，form だけ）
     * http://localhost:6879/sys/patternSetting/modifiEditAndNew
     *
     * @param psDBBean
     * @return
     */
    @PostMapping(value = "modifiEditAndNew", produces = "application/json;charset=UTF-8")
    public void modifiEditAndNew(@RequestBody TmgPatternMergeDTO tmgPatternMergeDTO, @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        patternSettingBean.modifiEditAndNew(tmgPatternMergeDTO);
    }

    /**
     * 【編集&新規】勤務パターンを適用可能な最少日付を取得
     * <p>
     * http://localhost:6879/sys/patternSetting/selectEditPeriodDate
     *
     * @param psDBBean
     * @return
     */
    @GetMapping("selectEditPeriodDate")
    @ResponseBody
    public PeriodDateVO selectEditPeriodDate(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        //patternSettingBean.setExecuteParameters(null, psDBBean);
        String periodDate = patternSettingBean.selectEditPeriodDate();
        PeriodDateVO periodDateVO = new PeriodDateVO();
        periodDateVO.setPeriodDate(periodDate);
        return periodDateVO;
    }

    /**
     * * 勤務パターン CSV取り込み
     * http://localhost:6879/sys/patternSetting/modifiCSVDwnload
     *
     * @param file
     * @param psDBBean
     * @return
     */
    @PostMapping("modifiCSVDwnload")
    @ResponseBody
    public ModifiCSVVO modifiCSVDwnload(@RequestParam("file") MultipartFile file,
                                        @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        return patternSettingBean.uploadCSV(file, psDBBean);
    }

    /**
     * 勤務パターン適用 編集バターンをチェックする
     * http://localhost:6879/sys/patternSetting/checkAppliesButton
     *
     * @param psDBBean
     * @return TRUE の場合、enableになる　　FALSEの場合、disableになる
     */
    @GetMapping("checkAppliesButton")
    @ResponseBody
    public boolean checkAppliesButton(@RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(null, psDBBean);
        //勤務パターンを適用可能な最少日付
        String periodDate = patternSettingBean.selectEditPeriodDate();
        //TRUE の場合、enableになる　　FALSEの場合、disableになる
        boolean check = DateUtil.date().isAfter(DateUtil.parse(periodDate, "yyyy/MM/dd"));
        return check;
    }

    /**
     * 編集パターン情報は存在かをチェックする
     * http://localhost:6879/sys/patternSetting/selectEditPatternExist
     *
     * @return TRUE の場合、存在しました　　FALSEの場合、未存在
     */
    @GetMapping("selectEditPatternExist")
    @ResponseBody
    public boolean selectEditPatternExist(String patternId) {
        //勤務パターンを適用可能な最少日付
        boolean isExists = patternSettingBean.selectEditPatternExist(patternId);
        //TRUE の場合、存在しました　　FALSEの場合、未存在
        return isExists;
    }

    /**
     * 勤務パターンを適用しない区分を取得する
     * http://localhost:6879/sys/patternSetting/selectTmgNoPtnAppliesName?baseDate=2020/06/15
     *
     * @param baseDate
     */
    @PostMapping("selectTmgNoPtnAppliesName")
    public String selectTmgNoPtnAppliseName(@RequestParam("baseDate") String baseDate,
                                                              @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(baseDate, psDBBean);
        return patternSettingBean.selectTmgNoPtnAppliesName(baseDate);

    }

    /**
     * 勤務パターン設定の適用画面初期表示
     * http://localhost:6879/sys/patternSetting/selectTmgNoPtnAppliesName?baseDate=2020/06/15
     *
     * @param baseDate
     */
    @PostMapping("modifiApplies")
    public String modifiApplies(@RequestParam("baseDate") String baseDate,
                                            @RequestAttribute("BeanName") PsDBBean psDBBean) {
        //初期化対象
        patternSettingBean.setExecuteParameters(baseDate, psDBBean);
        return patternSettingBean.selectTmgNoPtnAppliesName(baseDate);
    }

    /**
     * 勤務パターン設定の適用登録
     * http://localhost:6879/sys/patternSetting/selectTmgNoPtnAppliesName?baseDate=2020/06/15
     *
     * @param baseDate
     */
    @PostMapping(value = "modifiAppliesRegister", produces = "application/json;charset=UTF-8")
    public void modifiAppliesRegister(@RequestAttribute("BeanName") PsDBBean psDBBean,@RequestBody TmgPatternSettingAppliesVO tmgPsaVO) throws Exception {

        patternSettingBean.modifiAppliesRegister(psDBBean,tmgPsaVO);
    }

}
