package jp.smartcompany.job.modules.tmg.patternsetting;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.job.modules.core.service.IPatternSettingService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.*;
import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingConst;
import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingUtil;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.PatternSettingParam;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.TmgPatternVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * パターンを削除
     *
     * @param groupId
     * @param sectionId
     * @param patternId
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void deletePattern(String groupId, String sectionId, String patternId) {
        if (null == groupId || "".equals(groupId)) {
            logger.warn("GROUP番号が空です");
        }
        if (null == sectionId || "".equals(sectionId)) {
            logger.warn("SECTION番号が空です");
        }
        if (null == patternId || "".equals(patternId)) {
            logger.warn("パターン番号が空です");
            return;
        }

        iPatternSettingService.deleteTmgPattern(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, patternId);
        iPatternSettingService.deleteTmgPatternApplies(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, patternId);
        iPatternSettingService.deleteTmgPatternRest(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, patternId);

    }

    /**
     * デフォルト設定値 選択すれば
     *
     * @param groupId
     * @param sectionId
     * @param employeeId
     */
    private void updateTmgPattern(String groupId, String sectionId, String employeeId) {
        iPatternSettingService.updateTmgPattern(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, employeeId, PatternSettingConst.modifierProgramId);
    }

    /**
     * 勤務パターンをインサート
     *
     * @param tmgPatternInsertDTO
     */
    private void insertPattern(TmgPatternInsertDTO tmgPatternInsertDTO) {
        if (null != tmgPatternInsertDTO) {
            tmgPatternInsertDTO.setCustId(psDBBean.getCustID());
            tmgPatternInsertDTO.setCompCode(psDBBean.getCompCode());
            tmgPatternInsertDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            tmgPatternInsertDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);
            tmgPatternInsertDTO.setEmployeeId(psDBBean.getUserCode());
            iPatternSettingService.insertTmgPattern(tmgPatternInsertDTO);
        } else {
            logger.error("勤務パターン対象が空です");
        }
    }

    /**
     * テストデータ
     * 臨時ファンクショ　　本番環境では削除します
     *
     * @return
     */
    private TmgPatternInsertDTO tmgPatternInsertDTOData() {

        TmgPatternInsertDTO tmgPatternInsertDTO = new TmgPatternInsertDTO();
        tmgPatternInsertDTO.setCustId("01");
        tmgPatternInsertDTO.setCompCode("01");
        tmgPatternInsertDTO.setSectionId("000000000000");
        tmgPatternInsertDTO.setGroupId("000000000000|000000");
        tmgPatternInsertDTO.setEmployeeId("46402406");
        tmgPatternInsertDTO.setPatternId("23242323");
        tmgPatternInsertDTO.setPatternName("23236698989");
        tmgPatternInsertDTO.setDefaultFlag(PatternSettingConst.tmgOn);
        tmgPatternInsertDTO.setChangeTime("22:22");
        tmgPatternInsertDTO.setNextptn("09001330");
        tmgPatternInsertDTO.setC2caldays(PatternSettingConst.tmgOn);
        tmgPatternInsertDTO.setNopen("10:00");
        tmgPatternInsertDTO.setNclose("19:00");
        List<HashMap<String, String>> restList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> rest1 = new HashMap<String, String>();
        rest1.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, "12:00");
        rest1.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, "13:00");
        HashMap<String, String> rest2 = new HashMap<String, String>();
        rest2.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, "13:00");
        rest2.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, "13:30");
        restList.add(rest1);
        restList.add(rest2);
        tmgPatternInsertDTO.setRestList(restList);
        return tmgPatternInsertDTO;
    }


    /**
     * 勤務パターン更新またはインサート
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void modifiEditAndNew() {
        /**
         * 画面から
         */
        TmgPatternInsertDTO tmgPatternInsertDTO = this.tmgPatternInsertDTOData();

        if (null != tmgPatternInsertDTO) {
            tmgPatternInsertDTO.setModifierprogramid(PatternSettingConst.modifierProgramId);
            tmgPatternInsertDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            tmgPatternInsertDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);

            if (PatternSettingUtil.isEmpty(tmgPatternInsertDTO.getGroupId())) {
                tmgPatternInsertDTO.setGroupId(tmgPatternInsertDTO.getSectionId() + "|000000");
            }
            iPatternSettingService.deleteTmgPattern(psDBBean.getCustID(), psDBBean.getCompCode(), tmgPatternInsertDTO.getGroupId(), tmgPatternInsertDTO.getSectionId(), tmgPatternInsertDTO.getPatternId());
            if (tmgPatternInsertDTO.getDefaultFlag().equals(PatternSettingConst.tmgOn)) {
                // デフォルト設定値 選択すれば
                updateTmgPattern(tmgPatternInsertDTO.getGroupId(), tmgPatternInsertDTO.getSectionId(), psDBBean.getUserCode());
            }
            // 勤務パターン　保存する
            this.insertPattern(tmgPatternInsertDTO);
            // 勤務パターンの休憩時間を削除する
            iPatternSettingService.deleteTmgPatternRest(psDBBean.getCustID(), psDBBean.getCompCode(), tmgPatternInsertDTO.getGroupId(), tmgPatternInsertDTO.getSectionId(), tmgPatternInsertDTO.getPatternId());
            List<HashMap<String, String>> restList = tmgPatternInsertDTO.getRestList();
            TmgPatternRestDTO tmgPatternRestDTO = new TmgPatternRestDTO();
            tmgPatternRestDTO.setCustId(tmgPatternInsertDTO.getCustId());
            tmgPatternRestDTO.setCompCode(tmgPatternInsertDTO.getCompCode());
            tmgPatternRestDTO.setSectionId(tmgPatternInsertDTO.getSectionId());
            tmgPatternRestDTO.setGroupId(tmgPatternInsertDTO.getGroupId());
            tmgPatternRestDTO.setMinDate(tmgPatternInsertDTO.getMinDate());
            tmgPatternRestDTO.setMaxDate(tmgPatternInsertDTO.getMaxDate());
            tmgPatternRestDTO.setEmployeeId(tmgPatternInsertDTO.getEmployeeId());
            tmgPatternRestDTO.setModifierprogramid(tmgPatternInsertDTO.getModifierprogramid());
            tmgPatternRestDTO.setPatternId(tmgPatternInsertDTO.getPatternId());

            if (null != restList) {
                String restOpen = "";
                String restClose = "";
                for (int i = 0; i < restList.size(); i++) {
                    HashMap<String, String> rest = restList.get(i);
                    restOpen = rest.get(PatternSettingConst.REQUEST_KEY_RESTOPEN);
                    restClose = rest.get(PatternSettingConst.REQUEST_KEY_RESTCLOSE);
                    tmgPatternRestDTO.setRestOpen(restOpen);
                    tmgPatternRestDTO.setRestClose(restClose);
                    if (ObjectUtil.isNotNull(restOpen) && ObjectUtil.isNotNull(restClose)) {
                        iPatternSettingService.insertTmgPatternRestPlural(tmgPatternRestDTO);
                    }
                }
            }

        } else {
            logger.warn("勤務パターン更新またはインサート対象が空です");
        }
    }

    /**
     * 勤務パターンを適用可能な最少日付を取得
     *
     * @return
     */
    public String selectEditPeriodDate() {
        return iPatternSettingService.selectEditPeriodDate();
    }

}
