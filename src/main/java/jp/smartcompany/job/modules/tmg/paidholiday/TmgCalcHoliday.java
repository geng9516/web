package jp.smartcompany.job.modules.tmg.paidholiday;

import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.base.service.PluggableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
//@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgCalcHoliday extends BaseExecute {

    /**
     * プラガブルサービス
     */
    private final PluggableService pluggableService;

    @Override
    public Object execute(PluggableDTO pluggableDTO) {
        return null;
    }

    /**
     * 年次休暇情報計算処理
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd   該当年月
     * @param userId     更新者
     * @param programId  更新プログラムID
     */
    public void init(String customerId, String companyId, String employeeId, Date yyyymmdd, String userId, String programId) {

        // プラガブルBO設定
        PluggableDTO pluggableDTO = new PluggableDTO();
        pluggableDTO.setCustomerId(customerId);
        pluggableDTO.setCompanyId(companyId);
        pluggableDTO.setYyyymmdd(yyyymmdd);
        pluggableDTO.setUserId(userId);
        pluggableDTO.setProgramId(programId);
        pluggableDTO.setCphase("TMG_P_CALC_HOLIDAY");

        // プラガブルを実行して、年休付与区分を取得する
        //年休付与区分判定
        pluggableService.execute2(pluggableDTO);

    }
}
