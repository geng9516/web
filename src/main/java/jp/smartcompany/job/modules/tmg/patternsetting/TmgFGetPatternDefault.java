package jp.smartcompany.job.modules.tmg.PatternSetting;


import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.base.service.PluggableService;
import jp.smartcompany.job.modules.tmg.PatternSetting.dto.TmgPatternRow;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public  class TmgFGetPatternDefault extends BaseExecute {


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

        // init（）を呼び出す
        return init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getYyyymmdd());
    }

    /**
     * デフォルト勤務パターン取得（プラガブル）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return TMG_PATTERN_ROW 年休付与区分
     */
    public TmgPatternRow init(String customerId, String companyId, String employeeId, Date yyyymmdd) {


        // プラガブルBO設定
        PluggableDTO pluggableDTO = new PluggableDTO();
        pluggableDTO.setCustomerId(customerId);
        pluggableDTO.setCompanyId(companyId);
        pluggableDTO.setYyyymmdd(yyyymmdd);
        pluggableDTO.setEmployeeId(employeeId);
        pluggableDTO.setCphase("TMG_F_GET_PATTERN_DEFAULT");

        // プラガブルを実行して
        //勤務パターン
        return (TmgPatternRow)pluggableService.execute(pluggableDTO);
    }
}
