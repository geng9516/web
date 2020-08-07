package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ConditionColDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationDataDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationInsertDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 段階導入シュミレーション登録 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgStatusWorktypeSimService extends IService<TmgStatusWorktypeSimDO> {

    /**
     * 段階導入シュミレーション登録情報を取得する
     *
     * @param custID   顧客コード
     * @param compCode 法人コード
     * @param language 言語
     */
    StatusWorkTypeSimDto buildSQLForSelectTmgStatusWorkTypeSim(String custID, String compCode, String language);

    /**
     * 段階導入シミュレーション登録情報に登録する
     *
     * @param custId    顧客コード
     * @param compCode  　法人コード
     * @param userCode  　更新者
     * @param programId 　更新プログラムID
     * @param status    　ステータス
     * @return 件数
     */
    int buildSQLForUpdateTmgStatusWorkTypeSim(String custId, String compCode, String userCode, String programId, String status);

    /**
     * HR連携除外条件区分マスタ情報の件数を取得する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param editFlag
     * @return
     */
    List<ConditionColDTO> selectExcludecondCtl(String custID, String compCode, String language, String editFlag);

    /**
     * HR連携除外条件マスタ情報を取得する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @return
     */
    List<SimulationDataDTO> selectSimulationMaster(String custID, String compCode, String language, String genericgroupId, String editFlag);


    /**
     * マスタデータを入力する
     *
     * @param simulationInsertDTOList
     * @return
     */
    int insertMastGenericDetail(List<SimulationInsertDTO> simulationInsertDTOList);

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
    int updateTmgStatusWorkTypeSim(String psCustId, String psCompId, String psModifierProgramId, String psModifierUserId, String psStatus);

    /**
     * 期間のマスタを取得する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param editFlag
     * @param startDate
     * @param endDate
     * @return
     */
    List<SimulationDataDTO> selectSimulationMasterByDate(String custID, String compCode, String language, String genericgroupId, String editFlag, String startDate, String endDate);


    /**
     * 名称マスタ詳細情報を削除する
     *
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    int deleteMastGenericDetail(String custID, String compCode, String language, String genericgroupId, String startDate, String endDate);

    /**
     * 期間時間をチャックする
     * @param custID
     * @param compCode
     * @param language
     * @param genericgroupId
     * @param startDate
     * @param endDate
     * @return
     */
    int checkPeriodDate(String custID, String compCode, String language, String genericgroupId, String startDate, String endDate);

}
