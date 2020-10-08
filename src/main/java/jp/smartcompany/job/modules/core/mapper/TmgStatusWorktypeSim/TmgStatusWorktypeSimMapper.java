package jp.smartcompany.job.modules.core.mapper.TmgStatusWorktypeSim;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ConditionColDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationDataDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationInsertDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 段階導入シュミレーション登録 Mapper 接口
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */

@Mapper
public interface TmgStatusWorktypeSimMapper extends BaseMapper<TmgStatusWorktypeSimDO> {

    /**
     * HR連携除外条件区分マスタ情報の件数を取得する
     *
     * @param params
     * @return
     */
    List<ConditionColDTO> selectExcludecondCtl(HashMap<String, Object> params);

    /**
     * HR連携除外条件マスタ情報を取得する
     *
     * @param params
     * @return
     */
    List<SimulationDataDTO> selectSimulationMaster(HashMap<String, Object> params);


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
     * @param params
     * @return
     */
    int updateTmgStatusWorkTypeSim(HashMap<String, Object> params);

    /**
     * 期間のマスタを取得する
     *
     * @param params
     * @return
     */
    List<SimulationDataDTO> selectSimulationMasterByDate(HashMap<String, Object> params);

    /**
     * 名称マスタ詳細情報を削除する
     *
     * @param params
     * @return
     */
    int deleteMastGenericDetail(HashMap<String, Object> params);

    /**
     * 期間時間をチャックする
     *
     * @param params
     * @return
     */
    int checkPeriodDate(HashMap<String, Object> params);

    /**
     * 臨時マスタデータをオンラインデータに確定する
     *
     * @param params
     * @return
     */
    int insertOnlineMasterData(HashMap<String, Object> params);

    /**
     * TMG_TRIGGERを削除するクエリを返します
     *
     * @param params
     * @return
     */
    int buildSQLForDeleteTmgTrigger(HashMap<String, Object> params);

    /**
     * TMG_TRIGGERへINSERTするクエリを返します
     *
     * @param params
     * @return
     */
    int buildSQLForInsertTmgTrigger(HashMap<String, Object> params);

    /**
     * シミュレーション状態系
     * @param params
     * @return
     */
    HashMap<Object,Object> buildSQLForSelectTmgStatusWorkTypeSim(HashMap<String, Object> params);

}
