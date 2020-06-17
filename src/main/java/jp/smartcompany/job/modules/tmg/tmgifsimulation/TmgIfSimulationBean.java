package jp.smartcompany.job.modules.tmg.tmgifsimulation;

import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationMasterDto;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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

//    /**
//     * 連携対象者マスタ設定一覧画面
//     * ACT_DISP
//     */
//    public void actDisp(ModelMap modoMap) {
//
//        showDisp(TmgUtil.Cs_MG_TMG_EXCLUDE4THW_SIM);
//    }
//
//    /**
//     * 画面表示用処理
//     */
//    private void showDisp(String psGroupId) {
//
//        // 画面明細用
//        List<SimulationMasterDto> simulationMasterDtoList= iMastGenericDetailService.buildSQLForSelectSimulationMaster(psDBBean.getCustID(),
//                psDBBean.getCompCode(), psDBBean.getLanguage(), psGroupId);
//
//        // シミュレーション状態系
//        iTmgStatusWorktypeSimService.buildSQLForSelectTmgStatusWorkTypeSim(psDBBean.getCustID(),
//                psDBBean.getCompCode(),
//                "TRUNC(SYSDATE)",
//                "TRUNC(SYSDATE)",
//                psDBBean.getLanguage());
//
//        setItemValue();
//
//        setItemHederValue();
//
//        // 日付の集計値を格納する(行数は日付区切り)
//        buildSQLForSelectSumSimulationMaster(psDBBean.getCustID(),psDBBean.getCompCode(), psDBBean.getLanguage(), psGroupId);
//
//        gsExcludecond4thwCount = this.getCount(0);
//
//
//        buildSQLForSelectExcludecondCtl(psDBBean.getCustID(), psDBBean.getCompCode(), "TRUNC(SYSDATE)", "TRUNC(SYSDATE)", psDBBean.getLanguage());
//
//    }
//
//
//    private Integer gsExcludecond4thwCount = null;
//
//    public Integer getExcludecond4thwCount() {
//        return this.gsExcludecond4thwCount;
//    }
//
//
//
//    /**
//     * 画面項目保持用メソッド
//     *
//     * @throws
//     */
//    private void setItemValue() throws Exception {
//
//        String sOldkey = new String();
//
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//
//        for (int i = 0; i <= this.getCount(0); i++) {
//
//            if (i == this.getCount(0)) {
//                setLSimMaster(hashMap);
//                hashMap = new HashMap<String, String>();
//                break;
//            }
//
//            String sNewKey = valueAtColumnRow(0, 3, i);
//
//            // 日付単位(StartDate～EndDate)で１行とする為、いったん格納
//            if (isKeyDistinction(sNewKey, sOldkey)) {
//                setLSimMaster(hashMap);
//                hashMap = new HashMap<String, String>();
//            }
//
//            hashMap = getHashValue(hashMap, valueAtColumnRow(0, 0, i), i);
//            // todo:日付の保持を別メソッドにしてもいいかもしれない
//            hashMap.put(START_DATE, valueAtColumnRow(0, 3, i));
//            hashMap.put(END_DATE, valueAtColumnRow(0, 4, i));
//
//            sOldkey = sNewKey;
//
//        }
//
//    }
//
//    private String gsSimStatusName = null;
//
//    public String getSimStatusName() {
//        return this.gsSimStatusName;
//    }
//
//    public void setSimStatusName(String simStatusName) {
//        gsSimStatusName = simStatusName;
//    }
//
//    private String gsSimStatus = null;
//
//    public String getSimStatus() {
//        return this.gsSimStatus;
//    }
//
//    public void setSimStatus(String psSimStatus) {
//        gsSimStatus = psSimStatus;
//    }
//
//
//    /**
//     * 画面項目ヘッダ(ステータス状態部分)保持用メソッド
//     *
//     * @throws
//     */
//    private void setItemHederValue() throws Exception {
//
//        setSimStatus(valueAtColumnRow(1, 0, 0));
//
//        setSimStatusName(valueAtColumnRow(1, 1, 0));
//
//    }
}
