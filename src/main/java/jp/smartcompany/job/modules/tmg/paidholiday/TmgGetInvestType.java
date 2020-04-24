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
 * 年休付与区分判定（プラガブル）
 * TMG_F_GET_INVEST_TYPE
 */
//@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TmgGetInvestType extends BaseExecute {

    /**
     * プラガブルサービス
     */
    private final PluggableService pluggableService;

    /**
     * TmgGetBegindateWork
     */
    private final TmgGetBegindateWork tmgGetBegindateWork;


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
     * 年休付与区分判定（プラガブル）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return String 年休付与区分
     */
    public String init(String customerId,String companyId,String employeeId, Date yyyymmdd) {

        // 勤務開始日取得
        Date beginDateWork = tmgGetBegindateWork.init(customerId, companyId, employeeId, yyyymmdd);

        // プラガブルBO設定
        PluggableDTO pluggableDTO = new PluggableDTO();
        pluggableDTO.setCustomerId(customerId);
        pluggableDTO.setCompanyId(companyId);
        pluggableDTO.setYyyymmdd(yyyymmdd);
        pluggableDTO.setBeginDateWork(beginDateWork);
        pluggableDTO.setCphase("TMG_F_GET_INVEST_TYPE");

        // プラガブルを実行して、年休付与区分を取得する
        //年休付与区分判定
        return (String)pluggableService.execute(pluggableDTO);
    }
}
