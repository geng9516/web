package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.core.service.ITmgEmployeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 * TMG_F_GET_BEGINDATE_EMP
 * TMG_EMPLOYEESの開始日取得
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgGetBegindateEmp extends BaseExecute {

    /**
     * ITmgEmployeesService
     */
    private final ITmgEmployeesService iTmgEmployeesService;

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
     * TMG_EMPLOYEESの開始日取得
     *
     * @param customerId 法人コード
     * @param companyId 顧客コード
     * @param employeeId 社員番号
     * @param yyyymmdd 基準日
     * @return Date 開始日
     */
    public Date init(String customerId, String companyId, String employeeId, Date yyyymmdd){

        //　就業管理対象外の最終日を求める
        Date endDate = iTmgEmployeesService.selectEndDate(customerId, companyId, employeeId, yyyymmdd);

        // 存在しない場合、システム最小日付をセットする
        if(endDate == null) {
            endDate = DateUtil.parseDate("1900/01/01");
        }

        // 就業管理対象の開始日を求める
        return iTmgEmployeesService.selectStartDate(customerId, companyId, employeeId, yyyymmdd, endDate);

    }
}
