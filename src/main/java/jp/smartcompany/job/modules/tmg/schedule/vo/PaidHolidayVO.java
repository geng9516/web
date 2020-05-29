package jp.smartcompany.job.modules.tmg.schedule.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 有休VO
 * @objectSource
 * @date 2020/05/29
 **/
@Getter
@Setter
@ToString
public class PaidHolidayVO {
    /**
     * 公休日数
     */
    private String nationalHolidayDays;

    /**
     * 基準日日数
     */
    private String dateOfRecordDays;

    /**
     * 基準時間
     */
    private String dateOfRecord;

    /**
     * 年次休暇残
     */
    private String npaidRestDaysHour;
}
