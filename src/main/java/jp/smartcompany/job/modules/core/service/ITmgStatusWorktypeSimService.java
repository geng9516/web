package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;

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
}
