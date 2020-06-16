package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;

/**
 * @author 陳毅力
 * @description パターンインサートDTO
 * @objectSource
 * @date 2020/06/16
 **/
@Getter
@Setter
@ToString
public class TmgPatternInsertDTO {

    private String custId;
    private String compCode;
    private String sectionId;
    private String groupId;
    private String minDate;
    private String maxDate;
    private String employeeId;
    private String modifierprogramid;
    private String patternId;
    private String patternName;
    private String defaultFlag;
    /**
     * 日付切替時刻
     */
    private String changeTime;
    /**
     * 翌日勤務パターン
     */
    private String nextptn;

    /**
     * 翌日勤務パターン
     * (ある場合：TMG_ONOFF|1　　ない場合：TMG_ONOFF|0)
     */
    private String c2caldays;
    /**
     * 始業時刻
     */
    private String nopen;
    /**
     * 終業時刻
     */
    private String nclose;
    /**
     * 休憩時間
     * KEY   txtRESTOPEN  txtRESTCLOSE
     */
    private List<HashMap<String,String>> restList;

}
