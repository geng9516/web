package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 今月の月中有給付与の情報
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class HalfwayPaidHolidayDTO {

    private String  tmo_npaid_begining_days;
    private String  tmo_npaid_begining_hours;
    private String  tmo_npaid_rest_days;
    private String  tmo_npaid_rest_hours;
    private String  tph_dyyyymmdd;

}
