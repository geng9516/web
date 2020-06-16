package jp.smartcompany.job.modules.tmg.PatternSetting;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.core.service.IPatternSettingService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.RestTimeLimitDTO;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternAppliesDTO;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternDTO;
import jp.smartcompany.job.modules.tmg.PatternSetting.vo.TmgPatternVO;
import jp.smartcompany.job.modules.tmg.schedule.TmgScheduleBean;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 勤務パターン
 * @objectSource ps.c01.tmg.PatternSetting.PatternSettingBean
 * @date 2020/06/12
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatternSettingBean {

    private final Logger logger = LoggerFactory.getLogger(PatternSettingBean.class);
    private PsDBBean psDBBean;
    private TmgReferList referList;
    private final IPatternSettingService iPatternSettingService;

    /**
     * パラメータを初期化する
     */
    public void setExecuteParameters(String pBaseDate, PsDBBean psDBBean) {
        if (null == pBaseDate || "".equals(pBaseDate)) {
            pBaseDate = DateUtil.format(new Date(), "yyyy/MM/dd");
        }
        this.psDBBean = psDBBean;
        this.setReferList(pBaseDate);
    }

    /**
     * 汎用リンクコンポーネントを生成します。
     */
    private void setReferList(String pBaseDate) {
        try {
            referList = new TmgReferList(psDBBean, "PatternSetting", pBaseDate, TmgReferList.TREEVIEW_TYPE_LIST, true, true);
        } catch (Exception e) {
            logger.error("汎用リンクコンポーネントを生成することは失敗しました", e);
        }
    }

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する
     *
     * @param groupId
     * @return
     */
    public List<TmgPatternDTO> selectTmgPattern(String groupId) {
        if (null == groupId || "".equals(groupId)) {
            logger.warn("GROUPIDが空です");
            return null;
        }
        return iPatternSettingService.selectTmgPattern(psDBBean.getCustID(), psDBBean.getCompCode(), groupId);
    }

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する(自組織分)
     *
     * @param groupId
     * @return
     */
    public List<TmgPatternDTO> selectTmgPatternOwn(String groupId, String sectionId) {
        if (null == groupId || "".equals(groupId)) {
            logger.warn("GROUPIDが空です");
            return null;
        }
        if (null == sectionId || "".equals(sectionId)) {
            logger.warn("SECTIONIDが空です");
            return null;
        }
        return iPatternSettingService.selectTmgPatternOwn(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId);
    }

    /**
     * 該当者毎に設定されている勤務パターンの情報を取得する（部署社員リスト）
     *
     * @param baseDate --> 改訂日指定 画面から
     * @return
     */
    public List<TmgPatternAppliesDTO> selectTmgPatternApplies(String baseDate) {
        if (null == baseDate || "".equals(baseDate)) {
            logger.warn("基準時間が空です");
            return null;
        }
        String targetEmployees = "";
        if (null != referList) {
            targetEmployees = referList.buildSQLForSelectEmployees();
        }

        if (null == targetEmployees || "".equals(targetEmployees)) {
            logger.warn("関する社員番号リストSQLが空です");
            return null;
        }
        return iPatternSettingService.selectTmgPatternApplies(baseDate, targetEmployees, psDBBean.getLanguage());
    }

    /**
     * 勤務時間・休憩時間の制限値取得
     *
     * @return
     */
    public List<RestTimeLimitDTO> selectRestTimeLimit() {
        return iPatternSettingService.selectRestTimeLimit(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage());

    }

    /**
     * パターン編集画面のパターン情報
     *
     * @param groupId
     * @param patternId
     * @return
     */
    private TmgPatternDTO selectTmgPatternById(String groupId, String patternId) {
        return iPatternSettingService.selectTmgPatternById(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, patternId);
    }

    /**
     * 編集画面の翌日勤務パターンリスト
     *
     * @param groupId
     * @param patternId
     * @return
     */
    private List<HashMap<String, String>> selectPatternSelectList(String groupId, String patternId) {
        List<HashMap<String, String>> results = new ArrayList<HashMap<String, String>>();
        List<TmgPatternDTO> tmgPatternDTOList = iPatternSettingService.selectPatternSelectList(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, patternId);
        HashMap<String, String> mapList = null;
        for (int i = 0; i < tmgPatternDTOList.size(); i++) {
            TmgPatternDTO tmgPatternDTO = tmgPatternDTOList.get(i);
            mapList = new HashMap<String, String>();
            mapList.put("PatternId", tmgPatternDTO.getTpa_cpatternid());
            mapList.put("PatternValue", tmgPatternDTO.getTpa_cpatternname());
            results.add(mapList);
        }
        return results;
    }

    /**
     * 編集パターン情報
     *
     * @param groupId
     * @param patternId
     * @return
     */
    public TmgPatternVO selectEditPatternInfo(String groupId, String patternId) {
        if (null == groupId || "".equals(groupId)) {
            logger.warn("所属番号が空です");
            return null;
        }
        if (null == patternId || "".equals(patternId)) {
            logger.warn("パターン番号が空です");
            return null;
        }
        TmgPatternVO tmgPatternVO = new TmgPatternVO();
        tmgPatternVO.setTmgPatternDTO(this.selectTmgPatternById(groupId, patternId));
        tmgPatternVO.setPatternList(this.selectPatternSelectList(groupId, patternId));
        return tmgPatternVO;
    }


}
