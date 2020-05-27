package jp.smartcompany.job.modules.tmg.schedule;

import cn.hutool.core.util.ObjectUtil;
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
     * 表示開始日
     */
    private String _startDispDate = null;

    /**
     * 終了日
     */
    private String _endDispDate = null;

    /**
     * 基準日(開始日)
     */
    protected String _baseDate = null;

    /**
     * 終了日
     */
    protected String _endDate = null;


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

    /**
     * 対象ユーザー情報
     *
     * @param employeeId
     * @param baseDate
     * @return
     */
    public TargetUserDetailDTO selectTargetUserDetail(String employeeId, String baseDate) {

        if (ObjectUtil.isNull(employeeId) || ObjectUtil.isEmpty(employeeId)) {
            logger.error("社員IDは空です");
            return null;
        }

        if (ObjectUtil.isNull(baseDate) || ObjectUtil.isEmpty(baseDate)) {
            logger.error("検索時間は空です");
            return null;
        }

        String custId = psDBBean.getCustID();
        String compCode = psDBBean.getCompCode();
        String language = psDBBean.getLanguage();

        baseDate += "/01";

        return null;

    }

    /**
     * * 基本労働制対象者の表示開始?終了日を設定 月初の週が日曜日以外から始まる場合、前月末の日曜から
     * * 月末の週が土曜日以外で終わる場合、翌月頭の土曜日までを求める
     */
    private void setDispDate() {

        if (_isVariationalWorkType || _baseDate == null) {
            // 基準日が初期化されていない場合は何もしない
            return;
        }




    }


}
