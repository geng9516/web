package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 日別情報より予定データ
 * @objectSource
 * @date 2020/05/26
 **/
@Getter
@Setter
@ToString
public class ScheduleDataDTO {
    private String tda_nmmdd;
    private String tda_nmmday;
    private String tda_cworkingid_mm;
    private String tda_nopen_p;
    private String tda_nclose_p;
    private String tda_nrestopen_p;
    private String tda_nrestclose_p;
    private String tda_cmessage;
    private Integer tda_nlock_p;
    private String tda_cworkingid_p;
    private String tda_ccustomerid;
    private String tda_cstatusflg;
    private String tda_cbusinesstripid_p;
    private String tda_cbusinesstripid_mm;
    private String tda_ccomment_p;
    private String tda_dmmdd;
    private String avgworktime;
    private String mgd_csparechar4;
    private String timerange;
    private String duty;
    private String astem_cworktypeid;
    private String json;
    private String holflgCalendar;

}
