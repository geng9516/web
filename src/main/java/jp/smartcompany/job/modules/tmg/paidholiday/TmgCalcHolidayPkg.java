package jp.smartcompany.job.modules.tmg.paidholiday;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.base.pojo.dto.PluggableDTO;
import jp.smartcompany.job.modules.base.service.BaseExecute;
import jp.smartcompany.job.util.SysDateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Nie Wanqun
 * 年次休暇情報計算処理(勤怠種別：)
 * TMG_P_CALC_HOLIDAY_PKG
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgCalcHolidayPkg extends BaseExecute {

    /**
     * TmgGetInvestType
     */
    private final TmgGetInvestType tmgGetInvestType;

    @Override
    public Object execute(PluggableDTO pluggableDTO) {

        this.init(pluggableDTO.getCustomerId(), pluggableDTO.getCompanyId(), pluggableDTO.getEmployeeId()
                , pluggableDTO.getYyyymmdd(), pluggableDTO.getUserId(), pluggableDTO.getProgramId());
        return null;
    }

    /**
     * 年次休暇情報計算処理(勤怠種別：)
     *
     * @param customerId 顧客コード
     * @param companyId  法人コード
     * @param employeeId 社員番号
     * @param yyyyMm     該当年月
     * @param userId     更新者
     * @param programId  更新プログラムID
     */
    public void init(String customerId, String companyId, String employeeId, Date yyyyMm, String userId, String programId) {

        // 付与区分
        String wsInvestType;
        // 次回付与日
        Date wdInvestHoliday = yyyyMm;

        Date lastDay = DateUtil.endOfMonth(yyyyMm);
        while (!SysDateUtil.isGreater(wdInvestHoliday, lastDay)) {
            // 付与区分取得
            wsInvestType = tmgGetInvestType.init(customerId, companyId, employeeId, wdInvestHoliday);

            if (!StrUtil.hasEmpty(wsInvestType)) {
                // 処理対象リストに追加

            }

            wdInvestHoliday = DateUtil.offsetDay(wdInvestHoliday, 1);
        }


    }
}
