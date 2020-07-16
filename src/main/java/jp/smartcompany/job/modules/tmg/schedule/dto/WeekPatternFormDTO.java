package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 陳毅力
 * @description 週勤務パターンフォンDTO
 * @objectSource
 * @date 2020/07/16
 **/
@Setter
@Getter
public class WeekPatternFormDTO {
    /**
     * 曜日
     */
    private String workFlag;
    /**
     * 区分
     */
    private String workDivisionId;
    /**
     * 始業
     */
    private String startTime;
    /**
     * 終業
     */
    private String endTime;
    /**
     * 休憩开始
     */
    private String restStartTime;
    /**
     * 休憩结束
     */
    private String restEndTime;


}
