package jp.smartcompany.job.modules.tmg.schedule;

import jp.smartcompany.job.modules.core.service.ITmgScheduleService;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.schedule.dto.TargetUserDetailDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 陳毅力
 * @description 予定作成
 * @objectSource ps.c01.tmg.TmgSchedule.TmgScheduleBean
 * @date 2020/05/25
 **/
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TmgScheduleBean {

    private final Logger logger = LoggerFactory.getLogger(TmgScheduleBean.class);
    private final PsDBBean psDBBean;
    private final ITmgScheduleService iTmgScheduleService;

    /**
     * 検索対象者が対象期間中に４週間変形労働制の場合はtrueになります
     */
    private boolean _isVariationalWorkType = false;

    /**
     * 対象者が4週間の変形労働制対象者か検索しフラグ値を設定します
     *
     * @param baseDate
     * @param employeeId
     */
    private void setVariationalWorkInfo(String baseDate, String employeeId) {
        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        String language = psDBBean.getLanguage();

        int tmp1 = iTmgScheduleService.selectVariationalWorkInfo(employeeId, baseDate, custId, compCode, language);
        int tmp2 = iTmgScheduleService.selectVariationalWorkDays(employeeId, baseDate, custId, compCode, language);

        if (tmp1 > 0) {
            _isVariationalWorkType = true;
        }
        if (tmp2 > 0) {
            _isVariationalWorkType = true;
        }
    }


}
