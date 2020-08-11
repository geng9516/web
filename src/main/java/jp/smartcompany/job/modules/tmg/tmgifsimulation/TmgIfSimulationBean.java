package jp.smartcompany.job.modules.tmg.tmgifsimulation;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.*;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

/**
 * 連携対象者マスタ設定
 * ps.c01.tmg.TmgIfSimulation.TmgIfSimulationBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgIfSimulationBean {

    private final String TAG1 = ",";
    private final String TAG2 = "-";
    private final String TAG3 = "|";
    private final int batchNum = 20;
    private final String BEAN_DESC = "TmgIfSimulationBean";
    private final String GENERICGROUPID = "TMG_EXCLUDE4THW_SIM";
    private final String ONLINEGROUPID = "TMG_EXCLUDECOND4THW";
    private final String MINDATE = "1900/01/01";
    private final String MAXDATE = "2222/12/31";
    private final Logger logger = LoggerFactory.getLogger(TmgIfSimulationBean.class);
    private final ITmgStatusWorktypeSimService iTmgStatusWorktypeSimService;


    /**
     * マスタリスト
     *
     * @param custId
     * @param compCode
     * @param language
     * @return
     */
    public List<ConditionColDTO> selectExcludecondCtl(String custId, String compCode, String language, String genericgroupId, String startDate, String endDate) {
        if (ObjectUtil.isNotNull(startDate) && ObjectUtil.isNotEmpty(startDate) && ObjectUtil.isNotNull(endDate) && ObjectUtil.isNotEmpty(endDate)) {
            //更新
            return this.selectSimulationMasterByDate(custId, compCode, language, genericgroupId, startDate, endDate);
        } else {
            //新規
            return iTmgStatusWorktypeSimService.selectExcludecondCtl(custId, compCode, language, TmgUtil.Cs_MGD_ONOFF_1);
        }

    }


    /**
     * @param custId
     * @param compCode
     * @param language
     * @param psGroupId
     * @return
     */
    public List<SimulationDTO> selectSimulationMasterList(String custId, String compCode, String language, String psGroupId) {
        //結果
        List<SimulationDTO> results = new ArrayList<SimulationDTO>();
        //条件タイトルを取得する
        List<ConditionColDTO> conditionColDTOList = iTmgStatusWorktypeSimService.selectExcludecondCtl(custId, compCode, language, TmgUtil.Cs_MGD_ONOFF_1);
        //HR連携除外条件マスタ情報を取得する
        List<SimulationDataDTO> simulationDataDTOList = iTmgStatusWorktypeSimService.selectSimulationMaster(custId, compCode, language, psGroupId, TmgUtil.Cs_MGD_ONOFF_1);
        //period コラムで、リストをグループする
        Map<String, List<SimulationDataDTO>> collect = simulationDataDTOList.stream().collect(groupingBy(SimulationDataDTO::getPeriod));
        //Mapをアクセスすると、Listオブジェクトに変える
        SimulationDTO simulationDTO = null;
        for (String key : collect.keySet()) {
            simulationDTO = new SimulationDTO();
            String startTime = key.substring(0, key.indexOf(TAG2));
            String endTime = key.substring(key.indexOf(TAG2) + 1, key.length());
            simulationDTO.setStartTime(startTime);
            simulationDTO.setEndTime(endTime);
            //simulationDTO.setSimulationDataDTOList(collect.get(key));
            simulationDTO.setSimulationDataDTOList(this.compareData(collect.get(key), conditionColDTOList));
            results.add(simulationDTO);
        }
        return results;
    }

    /**
     * 期間のマスタを取得する
     *
     * @param custId
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    public List<ConditionColDTO> selectSimulationMasterByDate(String custId, String compCode, String language, String genericgroupId, String startDate, String endDate) {
        List<SimulationDataDTO> simulationDataDTOList = iTmgStatusWorktypeSimService.selectSimulationMasterByDate(custId, compCode, language, genericgroupId, TmgUtil.Cs_MGD_ONOFF_1, startDate, endDate);
        List<ConditionColDTO> conditionColDTOList = new ArrayList<ConditionColDTO>();
        if (null != simulationDataDTOList) {
            if (simulationDataDTOList.size() > 0) {
                ConditionColDTO conditionColDTO = null;
                for (int i = 0; i < simulationDataDTOList.size(); i++) {
                    SimulationDataDTO simulationDataDTO = simulationDataDTOList.get(i);
                    conditionColDTO = new ConditionColDTO();
                    conditionColDTO.setMgd_excludecond_type(simulationDataDTO.getMgd_excludecond_type());
                    conditionColDTO.setMgd_excludecond_type_name(simulationDataDTO.getMgd_excludecond_type_name());
                    conditionColDTO.setValue(simulationDataDTO.getExcludecond());
                    conditionColDTOList.add(conditionColDTO);
                }
            }
        } else {
            logger.error("期間のマスタを取得することが失敗しました");
        }
        return conditionColDTOList;
    }


    /**
     * マスタ指標を比べる
     *
     * @param userData
     * @param masterData
     */
    private List<SimulationDataDTO> compareData(List<SimulationDataDTO> userData, List<ConditionColDTO> masterData) {
        //userData データが　masterDataの中にない場合、userDataまで追加する
        if (null != masterData && null != userData && !masterData.isEmpty() && !userData.isEmpty()) {
            List<SimulationDataDTO> result = userData;
            //先ずは、オブジェクトリストを文字タイプに変わる
            Map<String, String> userDataMap = new HashMap<String, String>();
            Map<String, String> userDataMap_add = new HashMap<String, String>();
            for (int i = 0; i < userData.size(); i++) {
                SimulationDataDTO simulationDataDTO = userData.get(i);
                userDataMap.put(simulationDataDTO.getMgd_excludecond_type(), simulationDataDTO.getMgd_excludecond_type_name());
            }
            //次は、masterDataをMapに変わる
            Map<String, String> masterDataMap = new HashMap<String, String>();
            for (int i = 0; i < masterData.size(); i++) {
                ConditionColDTO conditionColDTO = masterData.get(i);
                masterDataMap.put(conditionColDTO.getMgd_excludecond_type(), conditionColDTO.getMgd_excludecond_type_name());
            }

            //最後、データを比べる
            for (String key : masterDataMap.keySet()) {
                if (!userDataMap.containsKey(key)) {
                    //含めていない場合、新たなMapオブジェクトに追加する
                    userDataMap_add.put(key, masterDataMap.get(key));
                }
            }
            //データを組み立てる
            SimulationDataDTO simulationDataDTO = userData.get(0);
            String startTime = simulationDataDTO.getStart_time();
            String endTime = simulationDataDTO.getEnd_time();
            String period = simulationDataDTO.getPeriod();
            SimulationDataDTO simulationDataDTO_bak = null;
            for (String key : userDataMap_add.keySet()) {
                simulationDataDTO_bak = new SimulationDataDTO();
                simulationDataDTO_bak.setStart_time(startTime);
                simulationDataDTO_bak.setEnd_time(endTime);
                simulationDataDTO_bak.setPeriod(period);
                simulationDataDTO_bak.setMgd_excludecond_type(key);
                simulationDataDTO_bak.setMgd_excludecond_type_name(userDataMap_add.get(key));
                simulationDataDTO_bak.setExcludecond("");
                result.add(simulationDataDTO_bak);
            }

            //結果の並び順
            result.stream().sorted((x, y) -> {
                return x.getMgd_excludecond_type().compareTo(y.getMgd_excludecond_type());
            });

            return result;
        } else {
            logger.warn("メタデータが空です");
            return null;
        }

    }

    /**
     * マスタ対象をmergeする
     *
     * @param simulationMergeJson
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse simulationMerge(String simulationMergeJson) {
        GlobalResponse globalResponse = GlobalResponse.ok("処理しました");
        //先ずは、jsonオブジェクトをチェックする
        if (null != simulationMergeJson && !"".equals(simulationMergeJson)) {
            if (JSONUtil.isJsonObj(simulationMergeJson)) {
                //次は、json文字を処理する
                SimulationInsertJsonDTO simulationInsertJsonDTO = this.simulationJsonConvert(simulationMergeJson);
                String actionCode = "";
                String modifierProgramId = "";
                if ("insert".equals(simulationInsertJsonDTO.getOperType())) {
                    actionCode = "ACT_SIM_INSERT";
                } else if ("update".equals(simulationInsertJsonDTO.getOperType())) {
                    actionCode = "ACT_SIM_UPDATE";
                    //更新の場合、古いデータを削除する
                    boolean flag = this.deleteMastGenericDetail_notrans(simulationInsertJsonDTO.getCustId(), simulationInsertJsonDTO.getCompCode(), simulationInsertJsonDTO.getLanguage(), simulationInsertJsonDTO.getPsGroupId(), simulationInsertJsonDTO.getPsStartDate(), simulationInsertJsonDTO.getPsEndDate());
                    if (!flag) {
                        //処理失敗しました
                        logger.warn("マスタ対象をmergeすることが失敗しました");
                        globalResponse = GlobalResponse.error("マスタ対象をmergeすることが失敗しました");
                        return globalResponse;
                    }
                }
                modifierProgramId = BEAN_DESC + "_" + actionCode;
                //その後、目標対象組み立てる
                List<SimulationInsertDTO> simulationInsertDTOList = this.getSimulationInsertDTOList(simulationInsertJsonDTO);
                //バッチ処理
                int page = 1;
                List<SimulationInsertDTO> batchBulk = new ArrayList<SimulationInsertDTO>();
                for (int i = 0; i < simulationInsertDTOList.size(); i++) {
                    SimulationInsertDTO simulationInsertDTO = simulationInsertDTOList.get(i);
                    if (i < batchNum * page) {
                        batchBulk.add(simulationInsertDTO);
                    } else if (i == batchNum * page) {
                        //db 処理
                        logger.info("マスタデータを入力する第" + page + "弾、処理中");
                        this.insertMastGenericDetail(batchBulk);
                        //処理後、batchBulkをリセットする
                        batchBulk.clear();
                        batchBulk.add(simulationInsertDTO);
                        //page 増加になります
                        ++page;
                    } else if (batchNum * page < i && i < batchNum * (page + 1)) {
                        //残るデータは、最後に処理する
                        batchBulk.add(simulationInsertDTO);
                    } else {
                        //普通の場合、この分岐が空はずです
                        logger.warn("マスタ対象をmergeする中でバッチ処理分岐のロジックが間違っちゃった");
                    }
                }
                //以上の三目の分岐、残るデータをdbで処理
                if (batchBulk.size() > 0) {
                    page = page + 1;
                    logger.info("マスタデータを入力する第" + page + "弾、処理中");
                    this.insertMastGenericDetail(batchBulk);
                    //最後、データをリセット
                    batchBulk.clear();
                }
                logger.info("データは「" + page + "」ページで処理完了しました");
                //db 処理
                this.updateTmgStatusWorkTypeSim(simulationInsertJsonDTO.getCustId(), simulationInsertJsonDTO.getCompCode(), modifierProgramId, simulationInsertJsonDTO.getEmployeId(), TmgUtil.Cs_MGD_TMG_WTSIMSTATUS_010);
                logger.info("段階導入シミュレーション登録情報に登録しました");
                globalResponse = GlobalResponse.ok("登録しました");
            } else {
                logger.error("JSON対象ではありません");
                globalResponse = GlobalResponse.error("JSON対象ではありません");
            }
        } else {
            logger.warn("マスタデータが空です");
            globalResponse = GlobalResponse.error("マスタデータが空です");
        }
        return globalResponse;
    }

    /**
     * マスタ」データが削除する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean deleteMastGenericDetail(String custID, String compCode, String language, String genericgroupId, String startDate, String endDate) {
        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(language) && ObjectUtil.isNotEmpty(genericgroupId) && ObjectUtil.isNotEmpty(startDate) && ObjectUtil.isNotEmpty(endDate)) {
            int count = iTmgStatusWorktypeSimService.deleteMastGenericDetail(custID, compCode, language, genericgroupId, startDate, endDate);
            logger.info("名称マスタ詳細情報を削除することが完了しました,総計「" + count + "」件");
            return true;
        } else {
            logger.warn("パラメータが不正です");
            return false;
        }
    }

    /**
     * マスタ」データが削除する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean deleteMastGenericDetail_notrans(String custID, String compCode, String language, String genericgroupId, String startDate, String endDate) {
        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(language) && ObjectUtil.isNotEmpty(genericgroupId)) {
            int count = iTmgStatusWorktypeSimService.deleteMastGenericDetail(custID, compCode, language, genericgroupId, startDate, endDate);
            logger.info("名称マスタ詳細情報を削除することが完了しました,総計「" + count + "」件");
            return true;
        } else {
            logger.warn("パラメータが不正です");
            return false;
        }
    }


    /**
     * マスタデータを入力する
     *
     * @param simulationInsertDTOList
     * @return
     */
    private int insertMastGenericDetail(List<SimulationInsertDTO> simulationInsertDTOList) {
        return iTmgStatusWorktypeSimService.insertMastGenericDetail(simulationInsertDTOList);
    }

    /**
     * 段階導入シミュレーション登録情報に登録する
     *
     * @param psCustId
     * @param psCompId
     * @param psModifierProgramId
     * @param psModifierUserId
     * @param psStatus
     * @return
     */
    private int updateTmgStatusWorkTypeSim(String psCustId, String psCompId, String psModifierProgramId, String psModifierUserId, String psStatus) {

        return iTmgStatusWorktypeSimService.updateTmgStatusWorkTypeSim(psCustId, psCompId, psModifierProgramId, psModifierUserId, psStatus);
    }

    /**
     * @param simulationInsertJsonDTO
     * @return
     */
    private List<SimulationInsertDTO> getSimulationInsertDTOList(SimulationInsertJsonDTO simulationInsertJsonDTO) {
        List<SimulationInsertDTO> simulationInsertDTOList = new ArrayList<SimulationInsertDTO>();
        SimulationInsertDTO simulationInsertDTO = null;
        if (null != simulationInsertJsonDTO) {
            List<ConditionColDTO> conditionColDTOList = simulationInsertJsonDTO.getConditionColDTOList();
            if (null != conditionColDTOList) {
                String value_tmp = "";
                String ramStr = "";
                for (int i = 0; i < conditionColDTOList.size(); i++) {
                    ConditionColDTO conditionColDTO = conditionColDTOList.get(i);
                    value_tmp = conditionColDTO.getValue();
                    if (null != value_tmp && !"NULL".equals(value_tmp) && !"".equals(value_tmp)) {
                        String[] result = value_tmp.split(TAG1);
                        for (String item : result) {
                            simulationInsertDTO = new SimulationInsertDTO();
                            simulationInsertDTO.setPsCustId(simulationInsertJsonDTO.getCustId());
                            simulationInsertDTO.setPsCompId(simulationInsertJsonDTO.getCompCode());
                            simulationInsertDTO.setPsGroupCode(simulationInsertJsonDTO.getPsGroupId());
                            simulationInsertDTO.setPsLanguage(simulationInsertJsonDTO.getLanguage());
                            simulationInsertDTO.setPsStartDate(simulationInsertJsonDTO.getPsStartDate());
                            simulationInsertDTO.setPsEndDate(simulationInsertJsonDTO.getPsEndDate());
                            simulationInsertDTO.setPsUpdateUser(simulationInsertJsonDTO.getEmployeId());
                            simulationInsertDTO.setPsExuludecondType(conditionColDTO.getMgd_excludecond_type());
                            ramStr = this.getRandomStr();
                            simulationInsertDTO.setPsDetailId(ramStr);
                            simulationInsertDTO.setPsMasterCode(simulationInsertJsonDTO.getPsGroupId() + TAG3 + ramStr);
                            if (item.indexOf(TAG2) > 0) {
                                simulationInsertDTO.setPsExuludecondFrom(item.substring(0, item.indexOf(TAG2)));
                                simulationInsertDTO.setPsExuludecondTo(item.substring(item.indexOf(TAG2) + 1, item.length()));
                            } else {
                                simulationInsertDTO.setPsExuludecondFrom(item);
                                simulationInsertDTO.setPsExuludecondTo("");
                            }
                            simulationInsertDTOList.add(simulationInsertDTO);
                        }
                    } else {
                        logger.warn("マスタ値が空です");
                    }
                }
            }
        } else {
            logger.warn("マスタインサート対象が空です");
        }
        return simulationInsertDTOList;
    }


    /**
     * マスタデータ処理
     */
    private SimulationInsertJsonDTO simulationJsonConvert(String simulationMerge) {
        JSONObject jsonObject = JSONUtil.parseObj(simulationMerge);
        if (null != JSONUtil.parseObj(simulationMerge)) {
            SimulationInsertJsonDTO simulationInsertJsonDTO = jsonObject.toBean(SimulationInsertJsonDTO.class);
            List<ConditionColDTO> conditionColDTOList = null;
            if (null != simulationInsertJsonDTO) {
                if (null != jsonObject.get("masterList")) {
                    conditionColDTOList = JSONUtil.parseArray(jsonObject.get("masterList")).toList(ConditionColDTO.class);
                    simulationInsertJsonDTO.setConditionColDTOList(conditionColDTOList);
                    return simulationInsertJsonDTO;
                } else {
                    logger.error("マスタリストデータが未取得しています");
                }
            } else {
                logger.warn("マスタリスト対象をjsonオブジェクトに変更できない");
            }
        } else {
            logger.error("JSON対象がオブジェクトに変更することが失敗しました");
        }

        return null;
    }

    /**
     * 期間時間をチェックする
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return FALSE: 時間帯不正
     */
    public boolean checkPeriodDate(String custID, String compCode, String language, String genericgroupId, String startDate, String endDate) {
        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(language) && ObjectUtil.isNotEmpty(genericgroupId) && ObjectUtil.isNotEmpty(startDate) && ObjectUtil.isNotEmpty(endDate)) {
            int count = iTmgStatusWorktypeSimService.checkPeriodDate(custID, compCode, language, genericgroupId, startDate, endDate);
            if (count == 0) {
                return true;
            }
        } else {
            logger.warn("パラメータが不正です");
        }

        return false;
    }

    /**
     * 確定する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param employeId
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public GlobalResponse dicisionMasterDate(String custID, String compCode, String language, String employeId) {

        //先ずは、既存レコードを削除する
        boolean flag1 = this.deleteMastGenericDetail_notrans(custID, compCode, language, ONLINEGROUPID, null, null);
        //次は、データをインサートする
        boolean flag2 = this.insertOnlineMasterData(custID, compCode, language, GENERICGROUPID, ONLINEGROUPID);
        //最後、 階導入シミュレーション登録情報に登録する
        String modifierProgramId = BEAN_DESC + "_" + "ACT_SIM_INSERT";
        this.updateTmgStatusWorkTypeSim(custID, compCode, modifierProgramId, employeId, TmgUtil.Cs_MGD_TMG_WTSIMSTATUS_010);

        if (flag1 && flag2) {
            return GlobalResponse.ok("確定完了しました");
        }

        if (!flag1 || !flag2) {
            return GlobalResponse.error("確定失敗しました");
        }
        return GlobalResponse.error("確定失敗しました");
    }


    /**
     * 臨時マスタデータをオンラインデータに確定する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param onlinegroupId
     * @return
     */
    private boolean insertOnlineMasterData(String custID, String compCode, String language, String genericgroupId, String onlinegroupId) {

        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(language) && ObjectUtil.isNotEmpty(genericgroupId) && ObjectUtil.isNotEmpty(onlinegroupId)) {
            int count = iTmgStatusWorktypeSimService.insertOnlineMasterData(custID, compCode, language, genericgroupId, onlinegroupId);
            logger.info("臨時マスタデータをオンラインデータに確定する数は「" + count + "」です");
            return true;
        } else {
            logger.warn("パラメータが不正です");
        }
        return false;
    }

    /**
     * TMG_TRIGGERを削除するクエリを返します
     *
     * @param psCustId
     * @param psCompId
     * @param psUserId
     * @param psModifierProgramId
     */
    private boolean buildSQLForDeleteTmgTrgger(String psCustId, String psCompId, String psUserId, String psModifierProgramId) {
        if (ObjectUtil.isNotEmpty(psCustId) && ObjectUtil.isNotEmpty(psCompId) && ObjectUtil.isNotEmpty(psUserId) && ObjectUtil.isNotEmpty(psModifierProgramId)) {
            int count = iTmgStatusWorktypeSimService.buildSQLForDeleteTmgTrgger(psCustId, psCompId, psUserId, psModifierProgramId);
            logger.info("TMG_TRIGGERを削除するクエリを返する数は「" + count + "」です");
            return true;
        } else {
            logger.warn("パラメータが不正です");
        }
        return false;
    }

    /**
     * TMG_TRIGGERへINSERTするクエリを返します
     *
     * @param custID
     * @param compCode
     * @param employeeId
     * @param minDate
     * @param maxDate
     * @param modifierprogramid
     * @return
     */
    private boolean buildSQLForInsertTmgTrgger(String custID, String compCode, String employeeId, String minDate, String maxDate, String modifierprogramid) {
        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(employeeId) && ObjectUtil.isNotEmpty(minDate) && ObjectUtil.isNotEmpty(maxDate) && ObjectUtil.isNotEmpty(modifierprogramid)) {
            int count = iTmgStatusWorktypeSimService.buildSQLForInsertTmgTrgger(custID, compCode, employeeId, minDate, maxDate, modifierprogramid);
            logger.info("TMG_TRIGGERへINSERTするクエリを返する数は「" + count + "」です");
            return true;
        } else {
            logger.warn("パラメータが不正です");
        }
        return false;
    }

    /**
     * シミュレーションの実行を行います
     *
     * @param custID
     * @param compCode
     * @param employeeId
     * @param actionCode
     * @return
     */
    @Transactional(rollbackFor = GlobalException.class)
    public boolean execSim(String custID, String compCode, String employeeId, String actionCode) {
        String modifierprogramid = BEAN_DESC +"_"+ actionCode;
        boolean flag1 = this.buildSQLForDeleteTmgTrgger(custID, compCode, employeeId, modifierprogramid);
        boolean flag2 = this.buildSQLForInsertTmgTrgger(custID, compCode, employeeId, MINDATE, MAXDATE, modifierprogramid);
        boolean flag3 = this.buildSQLForDeleteTmgTrgger(custID, compCode, employeeId, modifierprogramid);
        if (flag1 && flag2 && flag3) {
            logger.info("シミュレーションの実行を行っています");
            return true;
        }
        logger.error("シミュレーションの実行を行うことが失敗しました");
        return false;
    }

    /**
     * 段階導入シュミレーション登録情報を取得する
     * TMG_WTSIMSTATUS|010	未実行
     * TMG_WTSIMSTATUS|020	実行中
     * TMG_WTSIMSTATUS|120	実行中
     * TMG_WTSIMSTATUS|130	実行済
     * TMG_WTSIMSTATUS|230	取消済
     * TMG_WTSIMSTATUS|910	エラー
     * @param custID
     * @param compCode
     * @param language
     * @return
     */
    public HashMap<Object, Object> buildSQLForSelectTmgStatusWorkTypeSim(String custID, String compCode, String language) {
        if (ObjectUtil.isNotEmpty(custID) && ObjectUtil.isNotEmpty(compCode) && ObjectUtil.isNotEmpty(language)) {
            return iTmgStatusWorktypeSimService.buildSQLForSelectTmgStatusWorkTypeSim(custID, compCode, language);
        } else {
            logger.warn("パラメータが不正です");
            return null;
        }
    }


    /**
     * 乱数生成
     *
     * @return
     */
    private String getRandomStr() {
        return DateUtil.format(new Date(), "HHmmssSSS").toString() + RandomUtil.randomString(6);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.format(new Date(), "HHmmssSSS").toString() + RandomUtil.randomString(4));
    }


}

