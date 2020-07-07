package jp.smartcompany.job.modules.core.service.impl;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastWorker4discretionaryDO;
import jp.smartcompany.job.modules.core.mapper.TmgMastWorker4discretionaryMapper;
import jp.smartcompany.job.modules.core.service.ITmgMastWorker4discretionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 裁量労働身分マスタ 服务实现类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
@Repository
public class TmgMastWorker4discretionaryServiceImpl extends ServiceImpl<TmgMastWorker4discretionaryMapper, TmgMastWorker4discretionaryDO> implements ITmgMastWorker4discretionaryService {

    /**
     * 裁量労働対象者かどうかを判定
     *
     * @param custId       顧客コード
     * @param compCode     法人コード
     * @param employeeCode 社員番号
     * @param baseDate     日付
     * @return
     */
    @Override
    public boolean buildSQLForSelectDiscretion(String custId, String compCode, String employeeCode, String baseDate) {

        String discretion = baseMapper.buildSQLForSelectDiscretion(custId, compCode, employeeCode, baseDate);

        if ("1".equals(discretion)) {
            return true;
        }
        return false;
    }

}
