package jp.smartcompany.job.modules.tmg.attendanceBook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 年次休暇付与日数と付与時間BO
 * @objectSource
 * @date 2020/05/18
 **/
@Getter
@Setter
@ToString
public class AttendanceEndueTimeInfoDTO {

    private String endueDate;
    private long endueDays;
    private long endueHours;


}
