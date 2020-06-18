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
     *段階導入シュミレーション登録情報を取得するSQL文を返却します。
     *
     * @param custID
     * @param compCode
     * @param language
     */
    StatusWorkTypeSimDto buildSQLForSelectTmgStatusWorkTypeSim(String custID, String compCode, String language);
}
