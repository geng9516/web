package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import jp.smartcompany.job.modules.core.mapper.TmgStatusWorktypeSimMapper;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.ConditionColDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.SimulationDataDTO;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 段階導入シュミレーション登録 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgStatusWorktypeSimServiceImpl extends ServiceImpl<TmgStatusWorktypeSimMapper, TmgStatusWorktypeSimDO> implements ITmgStatusWorktypeSimService {

    /**
     * 段階導入シュミレーション登録情報を取得する
     *
     * @param custID   顧客コード
     * @param compCode 法人コード
     * @param language 言語
     */
    @Override
    public StatusWorkTypeSimDto buildSQLForSelectTmgStatusWorkTypeSim(String custID, String compCode, String language) {
        return baseMapper.buildSQLForSelectTmgStatusWorkTypeSim(custID, compCode, language);
    }

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
    @Override
    public int buildSQLForUpdateTmgStatusWorkTypeSim(String custId, String compCode, String userCode, String programId, String status) {
        return baseMapper.buildSQLForUpdateTmgStatusWorkTypeSim(custId, compCode, userCode, programId, status);
    }

    @Override
    public List<ConditionColDTO> selectExcludecondCtl(String custID, String compCode, String language, String editFlag) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custID);
        params.put("compCode", compCode);
        params.put("language", language);
        params.put("editFlag", editFlag);
        return baseMapper.selectExcludecondCtl(params);
    }

    @Override
    public List<SimulationDataDTO> selectSimulationMaster(String custID, String compCode, String language, String genericgroupId,String editFlag) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("custId", custID);
        params.put("compCode", compCode);
        params.put("language", language);
        params.put("editFlag", editFlag);
        params.put("genericgroupId", genericgroupId);
        return baseMapper.selectSimulationMaster(params);
    }
}
