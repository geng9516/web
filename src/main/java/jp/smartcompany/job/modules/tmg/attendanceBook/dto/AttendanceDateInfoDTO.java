package jp.smartcompany.job.modules.tmg.attendanceBook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description ディフォルト表示時間BO
 * @objectSource
 * @date 2020/05/18
 **/
@Getter
@Setter
@ToString
public class AttendanceDateInfoDTO {

    /**
     * 月
     */
    private String mgd_ndefault_month;

    /**
     * 　this year
     */
    private String dispterm_start;

    /**
     * last year
     */
    private String dispterm_end;


}
