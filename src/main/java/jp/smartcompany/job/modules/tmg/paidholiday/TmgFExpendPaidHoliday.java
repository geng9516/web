package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.modules.base.service.PluggableService;
import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgExpendPaidHoliday;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class TmgFExpendPaidHoliday extends BaseExecute {

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
        return init(pluggableDTO.getCustomerId(),pluggableDTO.getCompanyId(),pluggableDTO.getEmployeeId(),pluggableDTO.getStartDate(),
                pluggableDTO.getEndDate(),pluggableDTO.getRestDays(),pluggableDTO.getRestHours(),pluggableDTO.getNtfNo());
    }

    /**
     * 年次休暇情報計算処理（プラガブル）
     *
     * @param customerId 顧客コード
     * @param companyId 法人コード
     * @param employeeId 社員番号
     * @param beginDate 期間開始日
     * @param endDate 期間終了日
     * @param paidRestDays 消化前残日数
     * @param paidRestHours 消化前残時間数
     * @param ntfNo 申請番号
     * @return List<TmgExpendPaidHoliday> 年次休暇情報計算処理
     */
    public List<TmgExpendPaidHoliday> init(String customerId, String companyId, String employeeId, Date beginDate, Date endDate ,
                                           int paidRestDays, int paidRestHours, String ntfNo) {


        if(StrUtil.hasEmpty(ntfNo)){
            ntfNo="0";
        }
        // プラガブルBO設定
        PluggableDTO pluggableDTO = new PluggableDTO();
        pluggableDTO.setCustomerId(customerId);
        pluggableDTO.setCompanyId(companyId);
        pluggableDTO.setEmployeeId(employeeId);
        pluggableDTO.setStartDate(beginDate);
        pluggableDTO.setEndDate(endDate);
        pluggableDTO.setRestDays(paidRestDays);
        pluggableDTO.setRestHours(paidRestHours);
        pluggableDTO.setNtfNo(ntfNo);
        pluggableDTO.setCphase("TMG_F_EXPEND_PAID_HOLIDAY");

        return (List<TmgExpendPaidHoliday>)pluggableService.execute(pluggableDTO);
    }
}
