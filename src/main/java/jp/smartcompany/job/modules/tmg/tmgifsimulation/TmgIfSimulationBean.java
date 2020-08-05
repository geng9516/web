package jp.smartcompany.job.modules.tmg.tmgifsimulation;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jp.smartcompany.boot.common.GlobalResponse;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.pojo.entity.MastGenericDetailDO;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import jp.smartcompany.job.modules.core.service.ITmgTriggerService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.*;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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
    private final int batchNum = 500;
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
    public List<ConditionColDTO> selectExcludecondCtl(String custId, String compCode, String language) {
        List<ConditionColDTO> conditionColDTOList = iTmgStatusWorktypeSimService.selectExcludecondCtl(custId, compCode, language, TmgUtil.Cs_MGD_ONOFF_1);
        return conditionColDTOList;
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
    public GlobalResponse simulationMerge(String simulationMergeJson) {

        //先ずは、jsonオブジェクトをチェックする
        if (null != simulationMergeJson && !"".equals(simulationMergeJson)) {
            if (JSONUtil.isJsonObj(simulationMergeJson)) {
                //次は、json文字を処理する
                SimulationInsertJsonDTO simulationInsertJsonDTO = this.simulationJsonConvert(simulationMergeJson);
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
                        logger.info("insertMastGenericDetail1 。。。。。。");
                        //処理後、batchBulkをリセットする
                        batchBulk.clear();
                        batchBulk = new ArrayList<SimulationInsertDTO>();
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
                    logger.info("insertMastGenericDetail 2。。。。。。");

                    //最後、データをリセット
                    batchBulk.clear();
                }
                //db 処理
                logger.info("updateTmgStatusWorkTypeSim 。。。。。。");
            } else {
                logger.error("JSON対象ではありません");
            }
        } else {
            logger.warn("マスタデータが空です");
        }
        return null;
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

