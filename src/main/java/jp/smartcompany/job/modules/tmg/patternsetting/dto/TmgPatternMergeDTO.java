package jp.smartcompany.job.modules.tmg.patternsetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 勤務パターンmerge対象DTO
 * @objectSource
 * @date 2020/07/06
 **/
@Getter
@Setter
@ToString
public class TmgPatternMergeDTO {

    private String tpa_csectionid;
    private String tpa_cgroupid;
    private String tpa_cpatternid;
    private String tpa_cpatternname;
    private Object[] dutyTime;
    private Object[] planRest1;
    private Object[] planRest2;
    private Object[] planRest3;
    private Object[] planRest4;
    private String tpa_ndate_change_time;
    private String tpa_cnextptn;
    private String flag;

}
