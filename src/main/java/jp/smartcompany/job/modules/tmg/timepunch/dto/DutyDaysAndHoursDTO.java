package jp.smartcompany.job.modules.tmg.timepunch.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 出勤日数と時間数DTO
 * @objectSource
 * @date 2020/06/26
 **/
@Getter
@Setter
@ToString
public class DutyDaysAndHoursDTO {
    /**
     * 勤務日数　勤務時間　　指標
     */
    private String mgd_cheader;

    /**
     * 　実行SQL
     */
    private String mgd_csql;

    /**
     * エイリアス
     */
    private String mgd_csql_alias;

    /**
     * 　DUTYDAYS　　DUTYHOURS　　指標コード
     *//*
    private String mgd_ccolumnkey;*/

}
