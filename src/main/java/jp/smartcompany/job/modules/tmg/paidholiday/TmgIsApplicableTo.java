package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.IMastGenericDetailService;
import jp.smartcompany.job.modules.tmg.util.TmgPKG;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author Nie Wanqun
 * TMG_F_IS_APPLICABLE_TO
 * 申請エラーチェック（申請適用可否判定）
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgIsApplicableTo extends BaseExecute {

    /**
     * TmgGetMgdN
     */
    private final TmgGetMgdN tmgGetMgdN;

    /**
     *プラガブル用メイン処理
     *
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param yyyymmdd   基準日
     * @param workingID  　就業区分
     * @param applyTpye  　適用タイプ(0：終日 1:部分 2:出張 3:振替元 4:振替先)
     * @return int 適用可否(1：可、0：否)
     */
    public int init(String customerId, String companyId, Date yyyymmdd, String workingID, int applyTpye) {
        // 適用可否（1：可）
        int ok = 1;
        // 適用可否（0：否）
        int ng = 0;

        // 申請反映対象区分取得
        Long division = tmgGetMgdN.init(customerId,companyId,yyyymmdd,workingID, TmgPKG.CN_SPARENUM_5,"ja");

        // 申請適用可否判定
        if(division == null || StringUtils.isEmpty(applyTpye)){
            return ng;
        } else {
            int a = division.intValue();
            int b = (int) Math.pow(2, applyTpye);
            int c = a|b;
            if (c == 0) {
                return ng;
            }else {
                return ok;
            }
        }
    }
}
