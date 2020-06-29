package jp.smartcompany.job.modules.tmg.timepunch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description 予定データ
 * @objectSource
 * @date 2020/06/26
 **/
@Setter
@Getter
@ToString
public class ScheduleInfoDTO {

    /**
     * 　社員番号
     */
    private String tda_cemployeeid;

    /**
     * 　該当年月日
     */
    private String tda_dyyyymmdd;

    /**
     * 　出勤時間
     */
    private String tda_nopen_p;

    /**
     * 退勤時間
     */
    private String tda_nclose_p;

    /**
     * 　休憩時間json str
     */
    private String timerange;

    /**
     * 　休憩時間json
     */
    private Object[] timerange_arr;

}
