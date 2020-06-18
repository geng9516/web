package jp.smartcompany.job.modules.tmg.tmgifsimulation;

import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ExcludecondCtlDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 連携対象者マスタ設定
 * ps.c01.tmg.TmgIfSimulation.TmgIfSimulationBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgIfSimulationBean {

    /**
     * PsDBBean
     */
    private PsDBBean psDBBean;
    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;
    /**
     * ITmgStatusWorktypeSimService
     */
    private final ITmgStatusWorktypeSimService iTmgStatusWorktypeSimService;

    /**
     * 連携対象者マスタ設定一覧画面
     * ACT_DISP
     */
    public void actDisp(ModelMap modoMap) {

        showDisp(TmgUtil.Cs_MG_TMG_EXCLUDE4THW_SIM);
    }

    /**
     * 連携対象者マスタ設定編集画面
     * ACT_EDIT
     */
    public void actEdit(ModelMap modoMap) {

        showDisp(TmgUtil.Cs_MG_TMG_EXCLUDE4THW_SIM);
    }

    /**
     * 連携対象者マスタ設定編集画面
     * ACT_REFERENCE
     */
    public void actReference(ModelMap modoMap) {

        showDisp(TmgUtil.Cs_MG_TMG_EXCLUDECOND4THW);
    }

    private static final String START_DATE = "START_DATE";
    private static final String END_DATE   = "END_DATE";

    /**
     * 最後が「,」で終わっている場合、その「,」を削除して返却します。
     * @param psObj
     */
    private String getCommaDel(String psObj){

        if (psObj != null){
            if(",".equals(psObj.substring(psObj.length() - 1, psObj.length()))){
                return psObj.substring(0, psObj.length() - 1);
            }
        }

        return psObj;
    }

    /**
     * 画面項目値を加工して(必要に応じて「,」または「-」を付与)返却します。
     * @param
     */
    private String getCommaColumn(String psObj, String psFromValue, String psToValue){

        if (psObj == null){
            if (StringUtils.isEmpty(psToValue)){
                psObj = psFromValue;
            } else {
                psObj = psFromValue + "-" + psToValue;
            }
        } else {
            if (StringUtils.isEmpty(psToValue)){
                psObj = psObj + "," + psFromValue;
            } else {
                psObj = psObj + "," + psFromValue + "-" + psToValue;
            }
        }

        return psObj;
    }

    /**
     * キーブレイク判定用メソッド
     *
     * @param
     * @throws
     */
    private HashMap<String, String> getHashValue(HashMap<String, String> hashMap,
                                                 String psKey, String start, String end) {

        String comaDel = getCommaDel(getCommaColumn(hashMap.get(psKey), start, end));
        hashMap.put(psKey, comaDel);

        return hashMap;
    }

    /**
     * キーブレイク判定用メソッド
     * @param
     */
    private boolean isKeyDistinction(String psNewKey, String psOldKey){

        if (psNewKey == null){return false;}

        if (!psNewKey.equals(psOldKey) && !StringUtils.isEmpty(psOldKey)){return true;}

        return false;

    }

    /**
     * 画面表示用処理
     */
    private void showDisp(String psGroupId) {

        // 画面明細用
        List<SimulationMasterDto> simulationMasterDtoList = iMastGenericDetailService.buildSQLForSelectSimulationMaster(psDBBean.getCustID(),
                psDBBean.getCompCode(), psDBBean.getLanguage(), psGroupId);

        List<HashMap<String, String>> lSimMaster = new ArrayList<HashMap<String, String>>();
        String sOldkey = "";

        HashMap<String, String> hashMap = new HashMap<String, String>();

        for (SimulationMasterDto simulationMasterDto : simulationMasterDtoList) {
            String sNewKey = simulationMasterDto.getMgdDstartCk();
            // 日付単位(StartDate～EndDate)で１行とする為、いったん格納
            if (isKeyDistinction(sNewKey, sOldkey)) {
                lSimMaster.add(hashMap);
                hashMap = new HashMap<String, String>();
            }

            hashMap = getHashValue(hashMap, simulationMasterDto.getMgdExcludecondType(), simulationMasterDto.getMgdDstartCk(), simulationMasterDto.getMgdDend());
            // todo:日付の保持を別メソッドにしてもいいかもしれない
            hashMap.put(START_DATE, simulationMasterDto.getMgdDstartCk());
            hashMap.put(END_DATE, simulationMasterDto.getMgdDend());

            sOldkey = sNewKey;
        }

        // シミュレーション状態系
        StatusWorkTypeSimDto statusWorkTypeSimDto = iTmgStatusWorktypeSimService.buildSQLForSelectTmgStatusWorkTypeSim(psDBBean.getCustID(),
                psDBBean.getCompCode(),
                psDBBean.getLanguage());

        // 日付の集計値を格納する(行数は日付区切り)
        int gsExcludecond4thwCount = iMastGenericDetailService.buildSQLForSelectSumSimulationMaster(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage(), psGroupId).size();

        List<ExcludecondCtlDto> excludecondCtlDtoList = iMastGenericDetailService.buildSQLForSelectExcludecondCtl(psDBBean.getCustID(), psDBBean.getCompCode(), psDBBean.getLanguage());

    }

    private String gsSimStatusName = null;

    public String getSimStatusName() {
        return this.gsSimStatusName;
    }

    public void setSimStatusName(String simStatusName) {
        gsSimStatusName = simStatusName;
    }

    private String gsSimStatus = null;

    public String getSimStatus() {
        return this.gsSimStatus;
    }

    public void setSimStatus(String psSimStatus) {
        gsSimStatus = psSimStatus;
    }



}
