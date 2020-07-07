package jp.smartcompany.job.modules.tmg.empattrsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvgTimeForUpdateDto {
    // 平均勤務時間 時間
    private String sAvgWorkingHours = "";
    // 平均勤務時間 分
    private String sAvgWorkingMinutes = "";
    /** 週勤務日数 */
    private String sWorkingdaysWeek = "";
}
