package jp.smartcompany.job.modules.tmg.timepunch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/06/26
 **/
@Getter
@Setter
@ToString
public class DutyAndRelaxDateDTO {

    /**
     * 出勤日数
     */
    private Object dutyDates;

    /**
     * 　出勤時間
     */
    private Object dutyHours;

    /**
     * 　超過勤務時間
     */
    private String overTime;

    /**
     * 　年次休暇
     */
    private String npaidRestDaysHour;

}
