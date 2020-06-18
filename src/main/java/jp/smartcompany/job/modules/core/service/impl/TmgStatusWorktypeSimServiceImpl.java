package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgStatusWorktypeSimDO;
import jp.smartcompany.job.modules.core.mapper.TmgStatusWorktypeSimMapper;
import jp.smartcompany.job.modules.core.service.ITmgStatusWorktypeSimService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.smartcompany.job.modules.tmg.tmgifsimulation.dto.StatusWorkTypeSimDto;
import org.springframework.stereotype.Repository;

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
     * 段階導入シュミレーション登録情報を取得するSQL文を返却します。
     *
     * @param custID 顧客コード
     * @param compCode 法人コード
     * @param language 言語
     */
    @Override
    public StatusWorkTypeSimDto buildSQLForSelectTmgStatusWorkTypeSim(String custID, String compCode, String language) {
        return baseMapper.buildSQLForSelectTmgStatusWorkTypeSim(custID, compCode, language);
    }
}
