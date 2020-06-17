package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 勤務パターンDTO
 * @objectSource
 * @date 2020/06/12
 **/
@Getter
@Setter
@ToString
public class TmgPatternDTO {

    private String tpa_ccustomerid;
    private String tpa_ccompanyid;
    private String tpa_csectionid;
    private String tpa_cgroupid;
    private String tpa_dstartdate;
    private String tpa_denddate;
    private String tpa_cpatternid;
    private String tpa_cpatternname;
    private String tpa_cdefaultflg;
    private String timerange;
    /**
     * 日付切替時刻
     */
    private String tpa_ndate_change_time;
    /**
     * 翌日勤務パターン スイッチ
     */
    private String tpa_c2caldays;
    /**
     * 翌日勤務パターン
     */
    private String tpa_cnextptn;


}
