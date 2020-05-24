package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgCompanyDO;
import com.baomidou.mybatisplus.extension.service.IService;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.CompanyVO;

/**
 * <p>
 * [勤怠]法人情報                      2007/01/31項目追加｢年度の開始月」 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgCompanyService extends IService<TmgCompanyDO> {

    /**
     * [勤怠]TMG_COMPANYから予定出社・退社時間の基準値を取得。
     *
     * @param custCode   顧客コード
     * @param compCode   法人コード
     * @param targetDate 基準日
     * @return
     */
    CompanyVO buildSQLSelectCompany(String custCode, String compCode, String targetDate);
}
