package jp.smartcompany.job.modules.tmg.patternsetting;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.framework.util.CSVTokenizer;
import jp.smartcompany.job.modules.core.service.IPatternSettingService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.*;
import jp.smartcompany.job.modules.tmg.patternsetting.util.BufferedCSVReader;
import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingConst;
import jp.smartcompany.job.modules.tmg.patternsetting.util.PatternSettingUtil;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.PatternSettingParam;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.TmgPatternVO;
import jp.smartcompany.job.modules.tmg.patternsetting.vo.ModifiCSVVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

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
    private final String beanDesc = "PatternSetting";
    private final String planTypeKey = "CID";
    private final String restKey = "TMG_ITEMS|PlanRest";
    private final String dutyKey = "TMG_ITEMS|PlanDuty";
    private final String dutyOpenKey = "NOPEN";
    private final String dutyCloseKey = "NCLOSE";


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
            referList = new TmgReferList(psDBBean, beanDesc, pBaseDate, TmgReferList.TREEVIEW_TYPE_LIST, true, true);
        } catch (Exception e) {
            logger.error("汎用リンクコンポーネントを生成することは失敗しました", e);
        }
    }

    /**
     * TMG_PATTERNより利用可能な勤務パターンを取得する
     *
     * @param sectionId
     * @param groupId
     * @return
     */
    public List<TmgPatternDTO> selectTmgPattern(String sectionId, String groupId) {
        if (null == sectionId || "".equals(sectionId)) {
            sectionId = referList.getTargetSec();
        }
        //TMG_PATTERNより利用可能な勤務パターン
        List<TmgPatternDTO> tmgPatternDTOS = iPatternSettingService.selectTmgPattern(psDBBean.getCustID(), psDBBean.getCompCode(), sectionId, groupId);
        //勤務時間
        List<JSONObject> dutyArray = null;
        //休憩時間
        List<JSONObject> restArray = null;

        if ("null".equals(groupId)) {
            logger.warn("GROUPIDが空です");
            groupId = null;
        }
        for (int i = 0; i < tmgPatternDTOS.size(); i++) {
            TmgPatternDTO tmgPatternDTO = tmgPatternDTOS.get(i);
            //勤務パターンの編集や削除バターンを有効にするかどうか　チェックする
            boolean flag = (PatternSettingUtil.isEmpty(groupId) && tmgPatternDTO.getTpa_csectionid().equals(sectionId) && tmgPatternDTO.getTpa_cgroupid().equals(sectionId + "|000000"))
                    || (!PatternSettingUtil.isEmpty(groupId) && tmgPatternDTO.getTpa_csectionid().equals(sectionId) && tmgPatternDTO.getTpa_cgroupid().equals(groupId));
            if (flag) {
                tmgPatternDTO.setEdit(true);
            } else {
                tmgPatternDTO.setEdit(false);
            }
            dutyArray = new ArrayList<JSONObject>();
            restArray = new ArrayList<JSONObject>();
            JSONArray jsonArray = JSONUtil.parseArray(tmgPatternDTO.getTimerange());

            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject o = (JSONObject) jsonArray.get(j);
                if (o.get(planTypeKey).equals(dutyKey)) {
                    dutyArray.add(o);

                }
                if (o.get(planTypeKey).equals(restKey)) {
                    restArray.add(o);
                }
            }
            //勤務予定時間
            tmgPatternDTO.setPlanDuty(dutyArray);
            //休憩時間
            tmgPatternDTO.setPlanRest(restArray);
            //データリセット
            tmgPatternDTOS.set(i, tmgPatternDTO);
        }
        return tmgPatternDTOS;

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
            //return null;
        }
        if (null == sectionId || "".equals(sectionId)) {
            logger.warn("SECTIONIDが空です");
            //return null;
        }
        //TMG_PATTERNより利用可能な勤務パターン
        List<TmgPatternDTO> tmgPatternDTOS = iPatternSettingService.selectTmgPatternOwn(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId);
        List<JSONObject> dutyArray = null;
        List<JSONObject> restArray = null;

        for (int i = 0; i < tmgPatternDTOS.size(); i++) {
            TmgPatternDTO tmgPatternDTO = tmgPatternDTOS.get(i);
            //勤務パターンの編集や削除バターンを有効にするかどうか　チェックする
            if ((PatternSettingUtil.isEmpty(groupId) && tmgPatternDTO.getTpa_csectionid().equals(sectionId) && tmgPatternDTO.getTpa_cgroupid().equals(sectionId + "|000000"))
                    || (!PatternSettingUtil.isEmpty(groupId) && tmgPatternDTO.getTpa_csectionid().equals(sectionId) && tmgPatternDTO.getTpa_cgroupid().equals(groupId))
            ) {
                tmgPatternDTO.setEdit(true);
            }

            dutyArray = new ArrayList<JSONObject>();
            restArray = new ArrayList<JSONObject>();
            JSONArray jsonArray = JSONUtil.parseArray(tmgPatternDTO.getTimerange());

            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject o = (JSONObject) jsonArray.get(j);
                if (o.get(planTypeKey).equals(dutyKey)) {
                    dutyArray.add(o);
                }
                if (o.get(planTypeKey).equals(restKey)) {
                    restArray.add(o);
                }
            }
            //勤務予定時間
            tmgPatternDTO.setPlanDuty(dutyArray);
            //休憩時間
            tmgPatternDTO.setPlanRest(restArray);
            //データリセット
            tmgPatternDTOS.set(i, tmgPatternDTO);
        }
        return tmgPatternDTOS;


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
        //目標対象
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
        //編集画面の翌日勤務パターンリスト
        List<TmgPatternDTO> tmgPatternDTOList = iPatternSettingService.selectPatternSelectList(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, patternId);
        HashMap<String, String> mapList = null;
        for (int i = 0; i < tmgPatternDTOList.size(); i++) {
            TmgPatternDTO tmgPatternDTO = tmgPatternDTOList.get(i);
            mapList = new HashMap<String, String>();
            //勤務パターンid
            mapList.put("PatternId", tmgPatternDTO.getTpa_cpatternid());
            //勤務パターン名称
            mapList.put("PatternValue", tmgPatternDTO.getTpa_cpatternname());
            results.add(mapList);
        }
        return results;
    }

    /**
     * 編集パターン情報は存在かをチェックする
     *
     * @param patternId
     * @return
     */
    public boolean selectEditPatternExist(String patternId) {
        //ルートノード
        String groupId = "000000";
        if (null == patternId || "".equals(patternId)) {
            logger.warn("パターン番号が空です");
            return false;
        }
        if (null != this.selectTmgPatternById(groupId, patternId)) {
            return true;
        }
        return false;
    }

    /**
     * 新規パターンが
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
        //パターン編集画面のパターン情報
        tmgPatternVO.setTmgPatternDTO(this.selectTmgPatternById(groupId, patternId));
        //編集画面の翌日勤務パターンリスト
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
        iPatternSettingService.deleteTmgPatternRest(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, patternId);
        iPatternSettingService.deleteTmgPatternApplies(psDBBean.getCustID(), psDBBean.getCompCode(), groupId, sectionId, patternId);
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
            //01
            tmgPatternInsertDTO.setCustId(psDBBean.getCustID());
            //01
            tmgPatternInsertDTO.setCompCode(psDBBean.getCompCode());
            //MIN DATE
            tmgPatternInsertDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            //MAX DATE
            tmgPatternInsertDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);
            //操作ユーザー
            tmgPatternInsertDTO.setEmployeeId(psDBBean.getUserCode());
            iPatternSettingService.insertTmgPattern(tmgPatternInsertDTO);
        } else {
            logger.error("勤務パターン対象が空です");
        }
    }

    /**
     * 勤務パターンをインサート
     *
     * @param pstParam
     */
    private void insertPatternCsv(PatternSettingParam pstParam) {
        if (null != pstParam) {
            TmgPatternInsertDTO tmgPatternInsertDTO = new TmgPatternInsertDTO();
            //01
            tmgPatternInsertDTO.setCustId(pstParam.getCustomerId());
            //01
            tmgPatternInsertDTO.setCompCode(pstParam.getCompanyId());
            //グループ
            tmgPatternInsertDTO.setGroupId(pstParam.getGroupId());
            //部署
            tmgPatternInsertDTO.setSectionId(pstParam.getSectionId());
            //更新プログラム
            tmgPatternInsertDTO.setModifierprogramid(PatternSettingConst.modifierProgramId);
            //勤務パターンid
            tmgPatternInsertDTO.setPatternId(pstParam.getPatternId());
            //勤務パターン名称
            tmgPatternInsertDTO.setPatternName(pstParam.getPatternName());
            //ディフォルト勤務パターンか
            tmgPatternInsertDTO.setDefaultFlag(pstParam.getDefaultFlg());
            //翌日勤務パターン変更時間
            tmgPatternInsertDTO.setChangeTime(pstParam.getDateChangeTime());
            //翌日変更する勤務パターン
            tmgPatternInsertDTO.setNextptn(pstParam.getNextPatternId());
            //MIN DATE
            tmgPatternInsertDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            //MAX DATE
            tmgPatternInsertDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);
            //登録ユーザー
            tmgPatternInsertDTO.setEmployeeId(pstParam.getModifierUserId());
            if (StrUtil.isBlank(pstParam.getNextPatternId())) {
                /**
                 * 翌日勤務パターン
                 * (ある場合：TMG_ONOFF|1　　ない場合：TMG_ONOFF|0)
                 */
                tmgPatternInsertDTO.setC2caldays(TmgUtil.Cs_MGD_ONOFF_0);
            } else {
                tmgPatternInsertDTO.setC2caldays(TmgUtil.Cs_MGD_ONOFF_1);
            }
            //始業時刻
            if (null != pstParam.getWorkingTime().get(PatternSettingConst.OPEN)) {
                tmgPatternInsertDTO.setNopen(pstParam.getWorkingTime().get(PatternSettingConst.OPEN).toString());
            }
            //終業時刻
            if (null != pstParam.getWorkingTime().get(PatternSettingConst.CLOSE)) {
                tmgPatternInsertDTO.setNclose(pstParam.getWorkingTime().get(PatternSettingConst.CLOSE).toString());
            }
            iPatternSettingService.insertTmgPattern(tmgPatternInsertDTO);
        } else {
            logger.error("勤務パターンCSV対象が空です");
        }
    }

    /**
     * 勤務パターン休憩時間　保存する（取り込み）
     *
     * @param pstParam
     * @param rest
     * @return
     */
    private void insertTmgPatternRestPluralCsv(PatternSettingParam pstParam, Map rest) {
        if (null != pstParam) {
            TmgPatternRestDTO tmgPatternRestDTO = new TmgPatternRestDTO();
            //01
            tmgPatternRestDTO.setCustId(psDBBean.getCustID());
            //01
            tmgPatternRestDTO.setCompCode(psDBBean.getCompCode());
            //MIN DATE
            tmgPatternRestDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            //MAX DATE
            tmgPatternRestDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);
            //登録ユーザー
            tmgPatternRestDTO.setEmployeeId(psDBBean.getUserCode());
            //グループ
            tmgPatternRestDTO.setGroupId(pstParam.getGroupId());
            //部署
            tmgPatternRestDTO.setSectionId(pstParam.getSectionId());
            //更新プログラム
            tmgPatternRestDTO.setModifierprogramid(PatternSettingConst.modifierProgramId);
            //勤務パターンid
            tmgPatternRestDTO.setPatternId(pstParam.getPatternId());
            //休憩開始時間
            tmgPatternRestDTO.setRestOpen(rest.get(PatternSettingConst.OPEN) == null ? "" : rest.get(PatternSettingConst.OPEN).toString());
            //休憩終了時間
            tmgPatternRestDTO.setRestClose(rest.get(PatternSettingConst.CLOSE) == null ? "" : rest.get(PatternSettingConst.CLOSE).toString());
            iPatternSettingService.insertTmgPatternRestPlural(tmgPatternRestDTO);
        } else {
            logger.error("勤務パターンCSV対象が空です");
        }

    }


    /**
     * 勤務パターンを登録する
     *
     * @return
     */
    private TmgPatternInsertDTO tmgPatternInsertDTOData(TmgPatternMergeDTO tmgPatternMergeDTO) {
        if (null == tmgPatternMergeDTO) {
            logger.error("更新オブジェクトが空です");
            return null;
        }
        TmgPatternInsertDTO tmgPatternInsertDTO = new TmgPatternInsertDTO();
        //01
        tmgPatternInsertDTO.setCustId(psDBBean.getCustID());
        //01
        tmgPatternInsertDTO.setCompCode(psDBBean.getCompCode());
        //部署
        tmgPatternInsertDTO.setSectionId(tmgPatternMergeDTO.getTpa_csectionid());
        //グループ
        tmgPatternInsertDTO.setGroupId(tmgPatternMergeDTO.getTpa_cgroupid());
        //登録ユーザー
        tmgPatternInsertDTO.setEmployeeId(psDBBean.getUserCode());
        //勤務パターンid
        tmgPatternInsertDTO.setPatternId(tmgPatternMergeDTO.getTpa_cpatternid());
        //勤務パターン名称
        tmgPatternInsertDTO.setPatternName(tmgPatternMergeDTO.getTpa_cpatternname());
        //ディフォルト
        tmgPatternInsertDTO.setDefaultFlag(tmgPatternMergeDTO.getFlag());
        //翌日勤務パターン変更時間
        tmgPatternInsertDTO.setChangeTime(tmgPatternMergeDTO.getTpa_ndate_change_time() == null ? "" : tmgPatternMergeDTO.getTpa_ndate_change_time());
        //翌日変更する勤務パターン
        tmgPatternInsertDTO.setNextptn(tmgPatternMergeDTO.getTpa_cnextptn() == null ? "" : tmgPatternMergeDTO.getTpa_cnextptn());
        /**
         * 翌日勤務パターン
         * (ある場合：TMG_ONOFF|1　　ない場合：TMG_ONOFF|0)
         */
        if (null != tmgPatternMergeDTO.getTpa_ndate_change_time() && !"".equals(tmgPatternMergeDTO.getTpa_ndate_change_time())) {
            tmgPatternInsertDTO.setC2caldays(PatternSettingConst.tmgOn);
        } else {
            tmgPatternInsertDTO.setC2caldays(PatternSettingConst.tmgOff);
        }

        //　勤務時間
        if (tmgPatternMergeDTO.getDutyTime().length > 0) {
            tmgPatternInsertDTO.setNopen(tmgPatternMergeDTO.getDutyTime()[0].toString());
            tmgPatternInsertDTO.setNclose(tmgPatternMergeDTO.getDutyTime()[1].toString());
        } else {
            logger.error("勤務時間が空です");
            return null;
        }
        // 休憩時間
        List<HashMap<String, String>> restList = new ArrayList<HashMap<String, String>>();
        if (null != tmgPatternMergeDTO.getPlanRest1()) {
            if (tmgPatternMergeDTO.getPlanRest1().length > 0) {
                HashMap<String, String> rest1 = new HashMap<String, String>();
                if (null != tmgPatternMergeDTO.getPlanRest1()[0] && !"".equals(tmgPatternMergeDTO.getPlanRest1()[0]) && null != tmgPatternMergeDTO.getPlanRest1()[1] && !"".equals(tmgPatternMergeDTO.getPlanRest1()[1])) {
                    rest1.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, (tmgPatternMergeDTO.getPlanRest1()[0].toString()));
                    rest1.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, (tmgPatternMergeDTO.getPlanRest1()[1].toString()));
                    restList.add(rest1);
                }
            } else {
                logger.warn("休憩時間1が空です");
            }
        } else {
            logger.warn("休憩時間1が空です");
        }

        if (null != tmgPatternMergeDTO.getPlanRest2()) {
            if (tmgPatternMergeDTO.getPlanRest2().length > 0) {
                HashMap<String, String> rest2 = new HashMap<String, String>();
                if (null != tmgPatternMergeDTO.getPlanRest2()[0] && !"".equals(tmgPatternMergeDTO.getPlanRest2()[0]) && null != tmgPatternMergeDTO.getPlanRest2()[1] && !"".equals(tmgPatternMergeDTO.getPlanRest2()[1])) {
                    rest2.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, (tmgPatternMergeDTO.getPlanRest2()[0].toString()));
                    rest2.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, (tmgPatternMergeDTO.getPlanRest2()[1].toString()));
                    restList.add(rest2);
                }

            } else {
                logger.warn("休憩時間2が空です");
            }
        } else {
            logger.warn("休憩時間2が空です");
        }

        if (null != tmgPatternMergeDTO.getPlanRest3()) {
            if (tmgPatternMergeDTO.getPlanRest3().length > 0) {
                HashMap<String, String> rest3 = new HashMap<String, String>();
                if (null != tmgPatternMergeDTO.getPlanRest3()[0] && !"".equals(tmgPatternMergeDTO.getPlanRest3()[0]) && null != tmgPatternMergeDTO.getPlanRest3()[1] && !"".equals(tmgPatternMergeDTO.getPlanRest3()[1])) {
                    rest3.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, (tmgPatternMergeDTO.getPlanRest3()[0].toString()));
                    rest3.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, (tmgPatternMergeDTO.getPlanRest3()[1].toString()));
                    restList.add(rest3);
                }

            } else {
                logger.warn("休憩時間3が空です");
            }
        } else {
            logger.warn("休憩時間3が空です");
        }

        if (null != tmgPatternMergeDTO.getPlanRest4()) {
            if (tmgPatternMergeDTO.getPlanRest4().length > 0) {
                HashMap<String, String> rest4 = new HashMap<String, String>();
                if (null != tmgPatternMergeDTO.getPlanRest4()[0] && !"".equals(tmgPatternMergeDTO.getPlanRest4()[0]) && null != tmgPatternMergeDTO.getPlanRest4()[1] && !"".equals(tmgPatternMergeDTO.getPlanRest4()[1])) {
                    rest4.put(PatternSettingConst.REQUEST_KEY_RESTOPEN, (tmgPatternMergeDTO.getPlanRest4()[0].toString()));
                    rest4.put(PatternSettingConst.REQUEST_KEY_RESTCLOSE, (tmgPatternMergeDTO.getPlanRest4()[1].toString()));
                    restList.add(rest4);
                }

            } else {
                logger.warn("休憩時間4が空です");
            }
        } else {
            logger.warn("休憩時間4が空です");
        }


        tmgPatternInsertDTO.setRestList(restList);
        return tmgPatternInsertDTO;
    }

    /**
     * 勤務パターン更新またはインサート
     */
    @Transactional(rollbackFor = GlobalException.class)
    public void modifiEditAndNew(TmgPatternMergeDTO tmgPatternMergeDTO) {
        /**
         * 画面から
         */
        TmgPatternInsertDTO tmgPatternInsertDTO = this.tmgPatternInsertDTOData(tmgPatternMergeDTO);

        if (null != tmgPatternInsertDTO) {
            //更新プログラム
            tmgPatternInsertDTO.setModifierprogramid(PatternSettingConst.modifierProgramId);
            //MIN
            tmgPatternInsertDTO.setMinDate(PatternSettingConst.SQL_MIN_DATE);
            //MAX
            tmgPatternInsertDTO.setMaxDate(PatternSettingConst.SQL_MAX_DATE);
            //グループ
            if (PatternSettingUtil.isEmpty(tmgPatternInsertDTO.getGroupId())) {
                tmgPatternInsertDTO.setGroupId(tmgPatternInsertDTO.getSectionId() + "|000000");
            }
            //DELETE TMG_PATTERN
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
            //01
            tmgPatternRestDTO.setCustId(tmgPatternInsertDTO.getCustId());
            //01
            tmgPatternRestDTO.setCompCode(tmgPatternInsertDTO.getCompCode());
            //部署
            tmgPatternRestDTO.setSectionId(tmgPatternInsertDTO.getSectionId());
            //グループ
            tmgPatternRestDTO.setGroupId(tmgPatternInsertDTO.getGroupId());
            //MIN
            tmgPatternRestDTO.setMinDate(tmgPatternInsertDTO.getMinDate());
            //MAX
            tmgPatternRestDTO.setMaxDate(tmgPatternInsertDTO.getMaxDate());
            //職員id
            tmgPatternRestDTO.setEmployeeId(tmgPatternInsertDTO.getEmployeeId());
            //更新プログラム
            tmgPatternRestDTO.setModifierprogramid(tmgPatternInsertDTO.getModifierprogramid());
            //勤務パターンid
            tmgPatternRestDTO.setPatternId(tmgPatternInsertDTO.getPatternId());

            if (null != restList) {
                String restOpen = "";
                String restClose = "";
                for (int i = 0; i < restList.size(); i++) {
                    HashMap<String, String> rest = restList.get(i);
                    restOpen = rest.get(PatternSettingConst.REQUEST_KEY_RESTOPEN);
                    restClose = rest.get(PatternSettingConst.REQUEST_KEY_RESTCLOSE);
                    //休憩開始時間
                    tmgPatternRestDTO.setRestOpen(restOpen);
                    //休憩終了時間
                    tmgPatternRestDTO.setRestClose(restClose);
                    if (ObjectUtil.isNotNull(restOpen) && ObjectUtil.isNotNull(restClose)) {
                        //勤務パターン休憩時間　保存する
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

    /**
     * 勤務パターン CSV取り込み
     *
     * @param file
     */
    @Transactional(rollbackFor = GlobalException.class)
    public PatternSettingParam modifiCSVDwnload(MultipartFile file) {

        PatternSettingParam patternSettingParam = new PatternSettingParam();
        //実行プログラム
        patternSettingParam.setActionCode(PatternSettingConst.uploadProgramId);
        //基準日
        patternSettingParam.setBaseDate(null);
        //01
        patternSettingParam.setCustomerId(psDBBean.getCustID());
        //01
        patternSettingParam.setCompanyId(psDBBean.getCompCode());
        //部署
        if (null != referList.getTargetSec() && !"".equals(referList.getTargetSec())) {
            patternSettingParam.setSectionId(referList.getTargetSec());
        }
       /* if (null != referList.getTargetSecName() && !"".equals(referList.getTargetSecName())) {
            patternSettingParam.setSectionName(referList.getTargetSecName());
        }*/
       //グループ
        if (null != referList.getTargetGroup() && !"".equals(referList.getTargetGroup())) {
            patternSettingParam.setGroupId(referList.getTargetGroup());
        }
        /*if (null != referList.getTargetGroupName() && !"".equals(referList.getTargetGroupName())) {
            patternSettingParam.setGroupName(referList.getTargetGroupName());
        }*/
        //勤務パターンid
        patternSettingParam.setPatternId(null);
        //更新時間
        patternSettingParam.setModifiedDate(DateUtil.format(new Date(), "yyyy/MM/dd"));
        //更新ユーザー
        patternSettingParam.setModifierUserId(psDBBean.getTargetUser());
        //更新プログラム
        patternSettingParam.setModifierProgramId("PatternSetting_" + PatternSettingConst.uploadProgramId);
        //グループid
        if (PatternSettingUtil.isEmpty(patternSettingParam.getGroupId())) {
            patternSettingParam.setGroupId(patternSettingParam.getSectionId() + "|000000");
        }

        // メッセージボックス表示フラグをONにするsetMsgFlg
        patternSettingParam.setMsgFlg(true);
        // メッセージ格納配列を宣言
        List lMsgList = new ArrayList();
        List lMsgChild = new ArrayList();
        // 登録成功フラグ配列
        boolean bSuccessList[] = new boolean[0];
        // DB登録用パラメータ
        // 画面表示用
        List lPstParamList = new ArrayList();

        // 取り込めるデータが無いときは、エラー
        if (file.isEmpty()) {
            // データベース更新フラグを返却
            patternSettingParam.setInsertFlg(bSuccessList);
            // エラーメッセージを返却
            patternSettingParam.setMsgList(lMsgList);
            return patternSettingParam;
        }

        // ▼▼▼CSV取込処理 開始▼▼▼
        // リクエストからCSVデータ情報を取得
        byte byteCSVFile[] = null;
        try {
            byteCSVFile = file.getBytes();
        } catch (IOException e) {
            logger.error("ファイルが読み込むことができない", e);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteCSVFile);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedCSVReader csvReader = new BufferedCSVReader(reader);
        // CSVデータチェック処理
        String sBuffer = "";
        try {
            while ((sBuffer = csvReader.readLine()) != null) {
                lPstParamList.add(sBuffer);
            }
        } catch (IOException e) {
            logger.error("ファイルが読み込むことが失敗しました", e);
        }
        // 文字コードの制約で、1行目に不要な改行コードが入ってしまうため
        // 明示的に1行目だけは削除する
        // lPstParamList.remove(0);
        // 登録成功フラグ配列
        bSuccessList = new boolean[lPstParamList.size()];
        // CSVデータリストを返却
        patternSettingParam.setPatternSettingList(lPstParamList);
        // ▲▲ CSV取込処理 End ▲▲

        // ▼▼ 妥当性チェック Start▼▼
        // 妥当性フラグ 初期値:true
        boolean bValidity = true;
        List lValidityMsgList = new ArrayList();

        for (int i = 0; i < lPstParamList.size(); i++) {
            // 小メッセージ配列（行単位）を初期化する
            lMsgChild = new ArrayList();
            // 変数宣言
            String sIPATTERNID = "";
            String sIDEFAULTFLG = "";
            boolean bDefaultflg = true;
            CSVTokenizer iCsvRecord = new CSVTokenizer((String) lPstParamList.get(i));
            int nIndex = 0;
            while (iCsvRecord.hasMoreTokens()) {
                String sColumn = iCsvRecord.nextToken();
                if (PatternSettingConst.CSV_COLUMN_INDEX_PATTERNID == nIndex) {
                    sIPATTERNID = sColumn;
                } else if (PatternSettingConst.CSV_COLUMN_INDEX_CDEFAULTFLG == nIndex) {
                    sIDEFAULTFLG = sColumn;
                }
                nIndex++;
            }
            for (int k = 0; k < lPstParamList.size(); k++) {
                // 変数宣言
                String sKPATTERNID = "";
                String sKDEFAULTFLG = "";

                CSVTokenizer kCsvRecord = new CSVTokenizer((String) lPstParamList.get(k));
                int nKIndex = 0;
                while (kCsvRecord.hasMoreTokens()) {
                    String sColumn = kCsvRecord.nextToken();
                    if (PatternSettingConst.CSV_COLUMN_INDEX_PATTERNID == nKIndex) {
                        sKPATTERNID = sColumn;
                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_CDEFAULTFLG == nKIndex) {
                        sKDEFAULTFLG = sColumn;
                    }
                    nKIndex++;
                }

                // 勤務パターンID重複
                if (i != k && !StrUtil.isBlank(sIPATTERNID) && !StrUtil.isBlank(sKPATTERNID) && sIPATTERNID.equals(sKPATTERNID)) {

                    // メッセージ：CSVファイル内の勤務パターンIDが重複しています。
                    lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_DUPLICATION_ID));

                    // 妥当性フラグをfalseに変更
                    bValidity = false;
                }

                // 親ループデータ、子ループデータが両方ONの場合
                if (bDefaultflg && i != k && !StrUtil.isBlank(sIDEFAULTFLG) && !StrUtil.isBlank(sKDEFAULTFLG)
                        && TmgUtil.Cs_MGD_ONOFF_1.equals(sIDEFAULTFLG) && TmgUtil.Cs_MGD_ONOFF_1.equals(sKDEFAULTFLG)) {

                    // メッセージ：CSVファイル内にデフォルトフラグがONになっている勤務パターンが複数あります。
                    lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CDEFAULTFLG_ON));

                    // ONになっているデータが複数ある場合にエラーメッセージが複数表示されないようフラグをfalseに変更
                    bDefaultflg = false;
                    // 妥当性フラグをfalseに変更
                    bValidity = false;
                }
            }
            lValidityMsgList.add(lMsgChild);
        }
        if (!bValidity) {
            // データベース更新フラグを返却
            patternSettingParam.setInsertFlg(bSuccessList);
            patternSettingParam.setMsgList(lValidityMsgList);
            // 妥当性チェックのエラー時は全ての処理を終了しメッセージを表示する
            return patternSettingParam;
        }
        // ▲▲ 妥当性チェック End ▲▲

        // 行ループ
        for (int i = 0; i < lPstParamList.size(); i++) {
            // DB登録用パラメータ
            PatternSettingParam pstParam = new PatternSettingParam();
            // 小メッセージ配列（行単位）を初期化する
            lMsgChild = new ArrayList();
            // データベース更新フラグ
            boolean bSuccessFlg = false;

            // 引数より画面固有値を引き継ぐ（デフォルト値設定）
            pstParam.setSectionId(patternSettingParam.getSectionId());
            //グループ
            pstParam.setGroupId(patternSettingParam.getGroupId());
            //更新ユーザー
            pstParam.setModifierUserId(patternSettingParam.getModifierUserId());
            //更新プログラム
            pstParam.setModifierProgramId(patternSettingParam.getModifierProgramId());
            //更新時間
            pstParam.setModifiedDate(patternSettingParam.getModifiedDate());

            // システムプロパティから休憩の登録数のMAX値を取得
            int nRestMaxSize = this.getRestMaxSize();

            // TMG_COMPANYテーブルから時刻の規定範囲を取得
            HashMap<String, String> mTCOTime = iPatternSettingService.selectDayOpenClose();
            String sTCOOpen = mTCOTime.get(PatternSettingConst.OPEN);
            String sTCOClose = mTCOTime.get(PatternSettingConst.CLOSE);
            int nTCOOpen = PatternSettingUtil.toMinuteFromHHcMI60(sTCOOpen);
            int nTCOClose = PatternSettingUtil.toMinuteFromHHcMI60(sTCOClose);

            // 終業・終業時刻用Mapを宣言
            Map mWorkingTime = new HashMap();

            // 休憩開始・休憩終了時刻用リストMapを宣言
            List lBrakeTimeList = new ArrayList();

            // 休憩数分Mapを準備
            for (int j = 0; j < nRestMaxSize; j++) {
                lBrakeTimeList.add(new HashMap());
            }

            // CSVファイル解析用APIへ値をセット
            CSVTokenizer csvRecord = new CSVTokenizer(
                    (String) lPstParamList.get(i));

            // ▼▼ カラム数チェック Start▼▼
            if (csvRecord.countTokens() <= PatternSettingConst.CSV_COLUMN_INDEX_CLOSE) {
                // メッセージ：必須項目がありません。
                lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_REQUIRED_ITEM));
                // 次の行へ処理を以降
                lMsgList.add(lMsgChild);
                continue;
            }

            if ((PatternSettingConst.CSV_COLUMN_INDEX_REST + (nRestMaxSize * 2)) < csvRecord
                    .countTokens()) {
                // メッセージ：登録できる休憩時間数を超えています。
                lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_BREAK_TIME_MAX));
                // 次の行へ処理を以降
                lMsgList.add(lMsgChild);
                continue;
            }
            // ▲▲ カラム数チェック End ▲▲

            // インデックス
            int nIndex = 0;

            // 休憩データ用インデックス
            int nRestIndex = 0;

            try {
                while (csvRecord.hasMoreTokens()) {
                    // CSV項目
                    String sColumn = csvRecord.nextToken();
                    // ▼▼ 単項目チェック Start▼▼
                    if (PatternSettingConst.CSV_COLUMN_INDEX_CUSTOMERID == nIndex) {
                        // 顧客コード
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CUSTOMERID));

                        } else if (PatternSettingUtil.isDoubleByte(sColumn)) {
                            // 半角
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CUSTOMERID_DOUBLEBYTE));

                        } else if (sColumn.length() > PatternSettingConst.MAX_BYTE_CUSTOMERID) {
                            // バイト数
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CUSTOMERID_BYTE));
                        }
                        logger.warn("-------------->customerid:" + sColumn);
                        // 項目をセット
                        pstParam.setCustomerId(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_COMPANYID == nIndex) {
                        // 法人コード
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_COMPANYID));

                        } else if (PatternSettingUtil.isDoubleByte(sColumn)) {
                            // 半角
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_COMPANYID_DOUBLEBYTE));

                        } else if (sColumn.length() > PatternSettingConst.MAX_BYTE_COMPANYID) {
                            // バイト数
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_COMPANYID_BYTE));
                        }
                        logger.warn("-------------->companyid:" + sColumn);
                        // 項目をセット
                        pstParam.setCompanyId(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_PATTERNID == nIndex) {
                        // 勤務パターンID
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_PATTERNID));

                        } else if (PatternSettingUtil.isDoubleByte(sColumn)) {
                            // 半角
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_PATTERNID_DOUBLEBYTE));

                        } else if (sColumn.length() > PatternSettingConst.MAX_BYTE_PATTERNID) {
                            // バイト数
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_PATTERNID_BYTE));
                        }

                        // 項目をセット
                        pstParam.setPatternId(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_PATTERNNM == nIndex) {

                        // 勤務パターン名称
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_PATTERNNM));

                        } else if (sColumn.getBytes().length > PatternSettingConst.MAX_BYTE_PATTERNNM) {
                            // バイト数
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_PATTERNNM_BYTE));
                        }
                        // 項目をセット
                        pstParam.setPatternName(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_CDEFAULTFLG == nIndex) {

                        // デフォルトフラグ
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CDEFAULTFLG));

                        } else if (PatternSettingUtil.isDoubleByte(sColumn)) {
                            // 半角
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CDEFAULTFLG_DOUBLEBYTE));

                        } else if (sColumn.length() > PatternSettingConst.MAX_BYTE_CDEFAULTFLG) {
                            // バイト数
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CDEFAULTFLG_BYTE));

                        } else if (!TmgUtil.Cs_MGD_ONOFF_0.equals(sColumn) && !TmgUtil.Cs_MGD_ONOFF_1.equals(sColumn)) {
                            // マスタチェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CDEFAULTFLG_MASTER));

                        }

                        // 項目をセット
                        pstParam.setDefaultFlg(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_CHANGE_TIME == nIndex) {

                        // 日付切替時刻 hh:mm
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_DATE_CHANGE_TIME));
                        } else if (!PatternSettingUtil.formatChackHHMM(sColumn)) {

                            // 形式チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_DATE_CHANGE_TIME_FORMAT));
                        }

                        // 項目をセット
                        pstParam.setDateChangeTime(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_NEXTPTN == nIndex) {

                        if (StrUtil.isBlank(sColumn)) {
                            // 空白文字を登録しないためnullセット
                            sColumn = null;
                        }

                        // 翌日勤務パターン:項目をセット
                        pstParam.setNextPatternId(sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_OPEN == nIndex) {

                        // 始業時刻 hh:mm
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_OPEN));

                        } else if (!PatternSettingUtil.formatChackHHMM(sColumn)) {
                            // 形式チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_OPEN_FORMAT));

                        } else if (PatternSettingUtil.toMinuteFromHHcMI60(sColumn) < nTCOOpen
                                || PatternSettingUtil.toMinuteFromHHcMI60(sColumn) > nTCOClose) {

                            // 規定範囲チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_OPEN_RANGE_ORVER + "1")
                                    + sTCOOpen
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.WRD_WAVE_DASH)
                                    + sTCOClose
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_OPEN_RANGE_ORVER + "2"));
                        }

                        // 終業・終業時刻用Mapへ値をセット
                        mWorkingTime.put(PatternSettingConst.OPEN, sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_CLOSE == nIndex) {

                        // 終業時刻 hh:mm
                        if (StrUtil.isBlank(sColumn)) {
                            // 未入力
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CLOSE));

                        } else if (!PatternSettingUtil.formatChackHHMM(sColumn)) {
                            // 形式チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_CLOSE_FORMAT));

                        } else if (PatternSettingUtil.toMinuteFromHHcMI60(sColumn) < nTCOOpen
                                || PatternSettingUtil.toMinuteFromHHcMI60(sColumn) > nTCOClose) {

                            // 規定範囲チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CLOSE_RANGE_ORVER + "1")
                                    + sTCOOpen
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.WRD_WAVE_DASH)
                                    + sTCOClose
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CLOSE_RANGE_ORVER + "2"));
                        }
                        // 終業・終業時刻用Mapへ値をセット
                        mWorkingTime.put(PatternSettingConst.CLOSE, sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_REST <= nIndex
                            && nIndex < PatternSettingConst.CSV_COLUMN_INDEX_REST
                            + (nRestMaxSize * 2) && 1 == nIndex % 2) {

                        // 休憩開始時刻 hh:mm
                        if (!PatternSettingUtil.formatChackHHMM(sColumn)) {
                            // 形式チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_BREAK_OPEN_FORMAT));

                        } else if (PatternSettingUtil.toMinuteFromHHcMI60(sColumn) < nTCOOpen
                                || PatternSettingUtil.toMinuteFromHHcMI60(sColumn) > nTCOClose) {

                            // 規定範囲チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_BREAK_OPEN_RANGE_ORVER + "1")
                                    + sTCOOpen
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.WRD_WAVE_DASH)
                                    + sTCOClose
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_BREAK_OPEN_RANGE_ORVER + "2"));
                        }
                        // 休憩開始・休憩終了時刻用リストMapへ値をセット
                        ((HashMap) lBrakeTimeList.get(nRestIndex)).put(PatternSettingConst.OPEN, sColumn);

                    } else if (PatternSettingConst.CSV_COLUMN_INDEX_REST <= nIndex
                            && nIndex < PatternSettingConst.CSV_COLUMN_INDEX_REST
                            + (nRestMaxSize * 2) && 0 == nIndex % 2) {

                        // 休憩終了時刻 hh:mm
                        if (!PatternSettingUtil.formatChackHHMM(sColumn)) {
                            // 形式チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_BREAK_CLOSE_FORMAT));

                        } else if (PatternSettingUtil.toMinuteFromHHcMI60(sColumn) < nTCOOpen
                                || PatternSettingUtil.toMinuteFromHHcMI60(sColumn) > nTCOClose) {

                            // 規定範囲チェック
                            lMsgChild.add(PatternSettingUtil.getMessage(
                                    psDBBean.getLanguage(), PatternSettingConst.ERROR_BREAK_CLOSE_RANGE_ORVER + "1")
                                    + sTCOOpen
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.WRD_WAVE_DASH)
                                    + sTCOClose
                                    + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_BREAK_CLOSE_RANGE_ORVER + "2"));
                        }
                        // 休憩開始・休憩終了時刻用リストMapへ値をセット
                        ((HashMap) lBrakeTimeList.get(nRestIndex)).put(PatternSettingConst.CLOSE, sColumn);
                        nRestIndex++;
                    }
                    nIndex++;
                    // ▲▲ 単項目チェック End ▲▲
                }
            } catch (UnsupportedEncodingException e) {

            }
            // ▼▼ 相対性チェック Start ▼▼
            // 単項目チェックにて、エラーが出ていない場合のみ実行する
            if (0 == lMsgChild.size()) {
                // 終業・終業時刻比較チェック
                if (!PatternSettingUtil.chackOpenCloseTime(mWorkingTime)) {
                    lMsgChild.add(PatternSettingUtil.getMessage(
                            psDBBean.getLanguage(),
                            PatternSettingConst.ERROR_CSV_CLOSE_COMP));
                }
                // 項目をセット
                pstParam.setWorkingTime(mWorkingTime);
            }

            if (0 == lMsgChild.size()) {
                // 休憩開始・休憩終了時刻妥当性チェック
                int nBrakeIndex = 0;
                while (nBrakeIndex < lBrakeTimeList.size()) {
                    Map mTempMap = (Map) lBrakeTimeList.get(nBrakeIndex);
                    if (0 == mTempMap.size()) {
                        // 要素数が0の場合はマップを削除
                        lBrakeTimeList.remove(nBrakeIndex);
                    } else {
                        if (1 == mTempMap.size()) {
                            // メッセージ：休憩時刻は必ず開始時刻と終了時刻の両方を入力してください。
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_BREAK));
                        } else if (!PatternSettingUtil.chackOpenCloseTime(mTempMap)) {
                            // メッセージ：休憩終了時刻は休憩開始時刻より未来の時刻を入力してください。
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_BREAK_CLOSE_COMP));
                        }
                        // removeしなかった場合のみ加算
                        nBrakeIndex++;
                    }
                }
            }

            // 勤務時間開始
            int nWorkingOpen = PatternSettingUtil.toMinuteFromHHcMI60((String) mWorkingTime.get(PatternSettingConst.OPEN));
            //　勤務終了時間
            int nWorkingClose = PatternSettingUtil.toMinuteFromHHcMI60((String) mWorkingTime.get(PatternSettingConst.CLOSE));
            int nWork = nWorkingClose - nWorkingOpen;

            // 休憩時間合計
            int nRestSum = 0;

            if (0 == lMsgChild.size()) {
                jBrakeTimeList:
                for (int j = 0; j < lBrakeTimeList.size(); j++) {
                    int njOpen = PatternSettingUtil.toMinuteFromHHcMI60((String) ((Map) lBrakeTimeList.get(j)).get(PatternSettingConst.OPEN));
                    int njClose = PatternSettingUtil.toMinuteFromHHcMI60((String) ((Map) lBrakeTimeList.get(j)).get(PatternSettingConst.CLOSE));

                    for (int k = 0; k < lBrakeTimeList.size(); k++) {

                        int nkOpen = PatternSettingUtil.toMinuteFromHHcMI60((String) ((Map) lBrakeTimeList.get(k)).get(PatternSettingConst.OPEN));
                        int nkClose = PatternSettingUtil.toMinuteFromHHcMI60((String) ((Map) lBrakeTimeList.get(k)).get(PatternSettingConst.CLOSE));

                        // j＝Kでない場合において[A(j)<=A(k) かつ B(j)>=B(k)] もしくは
                        // [A(j)<=A(k) かつ B(j)<=B(k) かつ B(j)>A(k)]
                        if ((j != k) && ((njOpen <= nkOpen && njClose >= nkClose) || (njOpen <= nkOpen && njClose <= nkClose && njClose > nkOpen))) {
                            // メッセージ：休憩時刻が重複しています。
                            lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_BREAK_OVERLAP));

                            // 重複があった時点で2重ループを抜ける
                            break jBrakeTimeList;
                        }
                    }
                    nRestSum = nRestSum + (njClose - njOpen);
                }
                // 休憩時間を差し引く
                nWork = nWork - nRestSum;
            }

            if (0 == lMsgChild.size()) {
                // エラーがない場合のみ勤務時間・休憩時間チェックを行なう

                // 勤務時間・休憩時間の制限値をマスタから取得
                List<RestTimeLimitDTO> restTimeLimitDTOS = this.selectRestTimeLimit();

                for (int j = 0; j < restTimeLimitDTOS.size(); j++) {
                    RestTimeLimitDTO restTimeLimitDTO = restTimeLimitDTOS.get(j);
                    // [勤務時間下限]
                    int nWorkLower = Integer.parseInt((String) restTimeLimitDTO.getLimit_work_lower());
                    // [勤務時間上限]
                    int nWorkUpper = Integer.parseInt((String) restTimeLimitDTO.getLimit_work_upper());
                    // [休憩下限]
                    int nRestLower = Integer.parseInt((String) restTimeLimitDTO.getLimit_rest_lower());
                    // 勤務時間が[勤務時間下限]より多く[勤務時間上限]以下の場合に休憩時間が[休憩下限]未満だった場合
                    if (nWorkLower < nWork && nWork <= nWorkUpper && nRestSum < nRestLower) {
                        // メッセージ:[勤務時間下限]を超える勤務に対して[休憩下限]以上の休憩が必要です。
                        lMsgChild.add(restTimeLimitDTO.getGenericDesc());
                    }
                }
            }
            // ▲▲ 相対性チェック End ▲▲

            // ▼▼ DB勤務パターン重複チェック Start ▼▼
            if (0 == lMsgChild.size() && this.isPatternId(pstParam.getPatternId())) {
                // エラーがない場合は、DBとのID重複をチェックする
                lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_REPETITION));
            }
            // ▲▲ DB勤務パターン重複チェック End ▲▲

            // ▼▼ 翌日勤務パターン存在チェック Start ▼▼
            if (0 == lMsgChild.size() && (null != pstParam.getNextPatternId() && !this.isPatternId(pstParam.getNextPatternId()))) {
                // エラーがない場合は、DBに存在する勤務パターンかどうかチェックする
                lMsgChild.add(PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.ERROR_CSV_IS_NEXTPTN));
            }
            // ▲▲ 翌日勤務パターン存在チェック End ▲▲

            // ▼▼ データベース登録 Start ▼▼
            // チェック処理にて、エラーが出ていない場合のみ実行する
            if (0 == lMsgChild.size()) {
                Vector vecSQL = new Vector();
                if (TmgUtil.Cs_MGD_ONOFF_1.equals(pstParam.getDefaultFlg())) {
                    // デフォルトフラグがONの場合他のデフォルトフラグをOFFに更新する
                    this.updateTmgPattern(pstParam.getGroupId(), pstParam.getSectionId(), pstParam.getModifierUserId());
                }

                // CSVデータを登録する（パターン設定）
                this.insertPatternCsv(pstParam);

                // CSVデータを登録する（休憩情報）
                for (int j = 0; j < lBrakeTimeList.size(); j++) {
                    Map mapWork = (Map) lBrakeTimeList.get(j);
                    this.insertTmgPatternRestPluralCsv(pstParam, mapWork);
                }

                // DB登録完了後に登録フラグとと登録完了メッセージをセット
                bSuccessFlg = true;

                lMsgChild.add("【"
                        + pstParam.getPatternId()
                        + ":"
                        + pstParam.getPatternName()
                        + "】"
                        + PatternSettingUtil.getMessage(psDBBean.getLanguage(), PatternSettingConst.MSG_CSV_INSERT));

            }
            // 親メッセージ配列へ小メッセージ配列（行単位）を追加
            lMsgList.add(lMsgChild);

            // データベース更新フラグを格納
            bSuccessList[i] = bSuccessFlg;
        }
        // ▲▲ データベース登録 End ▲▲

        // データベース更新フラグを返却
        patternSettingParam.setInsertFlg(bSuccessList);

        // エラーメッセージを返却
        patternSettingParam.setMsgList(lMsgList);

        return patternSettingParam;
    }

    /**
     * CSV取り込み
     *
     * @param file
     * @return 結果対象
     */
    public ModifiCSVVO uploadCSV(MultipartFile file) {

        if (null == file) {
            logger.warn("アブロード対象が空です");
            return null;
        }
        ModifiCSVVO modifiCSVVO = new ModifiCSVVO();

        PatternSettingParam patternSettingParam = this.modifiCSVDwnload(file);
        //部署
        modifiCSVVO.setSSectionId(patternSettingParam.getSectionId());
        //グループ
        modifiCSVVO.setSGroupId(patternSettingParam.getGroupId());

        /**
         * データリスト
         */
        modifiCSVVO.setLPatternList(patternSettingParam.getPatternSettingList());
        /**
         * 取り込みの結果情報
         */
        modifiCSVVO.setLMsgList(patternSettingParam.getMsgList());

        /**
         * CSVデータ登録成功フラグ配列
         */
        modifiCSVVO.setInsertFlg(patternSettingParam.getInsertFlg());

        return modifiCSVVO;
    }


    /**
     * システムプロパティから休憩の登録数のMAX値を取得します、 システムプロパティから値が取得できなかった場合は1を返します
     *
     * @return int
     */
    private int getRestMaxSize() {

        try {
            // システムプロパティから休憩の登録数のMAX値を取得
            return Integer.parseInt(psDBBean.getSystemProperty(PatternSettingConst.SYSTEM_KEY_REST_MAX_SIZE));

        } catch (Exception e) {
            return 1;
        }

    }

    /**
     * 指定された勤務パターンIDがDBに存在している場合trueを返す
     *
     * @param patternId
     * @return
     */
    private boolean isPatternId(String patternId) {
        if (null == patternId || "".equals(patternId)) {
            logger.warn("パターン番号が空です");
            return false;
        }
        String patternIdResult = iPatternSettingService.checkPatternId(psDBBean.getCompCode(), patternId);
        if (null != patternIdResult && !"".equals(patternIdResult)) {
            return true;
        }
        return false;
    }
}
