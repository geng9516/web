package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.base.service.PluggableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 *
 * 端数処理
 * TMG_F_CONV_FRACTION4NYK
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgConvFraction4nyk extends BaseExecute {

    /**
     * プラガブルサービス
     */
    private final PluggableService pluggableService;

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
     * 年休付与区分判定（プラガブル）
     *
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @param pnTime 分単位の数値
     * @param customerId 顧客コード
     * @param companyId 法人コード
     *
     * @return int 端数処理
     */
    public int init(String employeeId, Date yyyymmdd, Double pnTime, String customerId, String companyId) {

        // プラガブルDTO設定
        PluggableDTO pluggableDTO = new PluggableDTO();
        pluggableDTO.setCustomerId(customerId);
        pluggableDTO.setCompanyId(companyId);
        pluggableDTO.setYyyymmdd(yyyymmdd);
        pluggableDTO.setEmployeeId(employeeId);
        pluggableDTO.setPnTime(pnTime);
        pluggableDTO.setCphase("TMG_F_CONV_FRACTION4NYK");

        // 端数処理
        return (int)pluggableService.execute(pluggableDTO);
    }

}
