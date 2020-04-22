package jp.smartcompany.job.modules.tmg.patternsetting;


import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;

import jp.smartcompany.job.modules.core.service.ITmgPersonalPatternService;
import jp.smartcompany.job.modules.tmg.patternsetting.dto.TmgPersonalPatternRow;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetPersonalPattern  extends BaseExecute {


    private final ITmgPersonalPatternService iTmgPersonalPatternService;
    /**
     *プラガブル用メイン処理
     * @param pluggableDTO PluggableDTO
     * @return Object
     */
    @Override
    public Object execute(PluggableDTO pluggableDTO) {

        // init()を呼び出す
        return this.init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getYyyymmdd());
    }

    /**
     * デフォルト勤務パターン取得（標準）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return List<TmgPatternRow></> デフォルト勤務パターン
     */
    protected TmgPersonalPatternRow init(String customerId, String companyId, String employeeId, Date yyyymmdd) {


        return iTmgPersonalPatternService.selectPersonalPatternRow(customerId,companyId,employeeId,yyyymmdd);

    }
}
