package jp.smartcompany.job.modules.core.service;

import jp.smartcompany.job.modules.core.pojo.entity.TmgPaidHolidayDO;
import com.baomidou.mybatisplus.extension.service.IService;

import jp.smartcompany.job.modules.tmg.paidholiday.dto.TmgPaidHolidayDto;
import jp.smartcompany.job.modules.tmg.paidholiday.vo.PaidHolidayDispVO;
import jp.smartcompany.job.modules.tmg.tmgnotification.dto.paramNotificationListDto;

import jp.smartcompany.job.modules.tmg.tmgnotification.vo.restYearPaidHolidayVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * [勤怠]年次休暇情報 服务类
 * </p>
 *
 * @author Xiao Wenpeng
 * @since 2020-04-16
 */
public interface ITmgPaidHolidayService extends IService<TmgPaidHolidayDO> {

    List<Date> selectNykLoseDate(String customerId, String companyId, Date startDate, Date endDate, String employeeId);


    /**
     * 年次休暇残日数及び時間
     *
     * @param params 　params
     * @return String パターン
     */
    List<restYearPaidHolidayVo> selectNenjikyukazannissu(paramNotificationListDto params, int detailFlg);

        /**
         *[勤怠]年次休暇情報より、年次休暇付与状況一覧を取得する
         *
         * @param custID 顧客コード
         * @param compCode　法人コード
         * @param userCode　対象者
         * @param baseDate　基準日
         * @param targetDate　年休付与日
         * @return
         */
    List<PaidHolidayDispVO> buildSQLForSelectPaidHoliday(String custID, String compCode, String userCode, String baseDate, String targetDate);

    /**
     * [勤怠]年次休暇情報を更新する
     * @param tmgPaidHolidayDto TmgPaidHolidayDto
     * @return
     */
    int buildSQLForUpdatePaidHolyday( TmgPaidHolidayDto tmgPaidHolidayDto);
}
