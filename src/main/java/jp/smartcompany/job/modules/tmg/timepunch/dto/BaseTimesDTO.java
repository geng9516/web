package jp.smartcompany.job.modules.tmg.timepunch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 本日の日付情報と、法人情報(TMG_COMPANY)の開始時刻
 * @objectSource
 * @date 2020/06/25
 **/
@Getter
@Setter
@ToString
public class BaseTimesDTO {

    private String sToday;
    private String sYesterday;
    private String sNow;
    private String sStartMinutes;
    private String sEndMinutes;


}
