package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPluggableDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * プラガブル情報マスタ 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPluggableService extends IService<TmgPluggableDO> {

    /**
     * 勤怠種別実行処理取得
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param workType   勤務種別
     * @param cphase     勤務種別  CPHASE
     * @return List<TmgPluggableDO>
     */
    List<TmgPluggableDO> listTmgPluggableDO(String customerId, String companyId, Date yyyymmdd, String workType, String cphase);

}
