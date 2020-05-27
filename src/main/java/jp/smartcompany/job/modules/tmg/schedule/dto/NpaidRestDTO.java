package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 年次休暇残
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class NpaidRestDTO {

    private String tmo_npaid_rest_days;
    private String tmo_npaid_rest_hours;
    private String tmo_nmonth_chk_flg;


}
