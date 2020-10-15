package jp.smartcompany.job.modules.tmg.attendanceBook.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 対象職員の出勤簿情報が存在する年度情報
 * @objectSource
 * @date 2020/06/12
 **/
@Getter
@Setter
@ToString
public class AttendanceExistsVO {

    /**
     * 前月
     */
    private String lastYear;
    /**
     * 該当年
     */
    private String thisYear;
    /**
     * 翌年
     */
    private String nextYear;

    /**
     * 当年
     */
    private String currentYear;

}
