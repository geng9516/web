package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgMastWorker4discretionaryDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 裁量労働身分マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgMastWorker4discretionaryService extends IService<TmgMastWorker4discretionaryDO> {

    /**
     * 裁量労働対象者かどうかを判定
     *
     * @param custId       顧客コード
     * @param compCode     法人コード
     * @param employeeCode 社員番号
     * @param baseDate     日付
     * @return
     */
    boolean buildSQLForSelectDiscretion(String custId, String compCode, String employeeCode, String baseDate);
}
