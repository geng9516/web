package jp.smartcompany.job.modules.tmg.PatternSetting.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/06/12
 **/
@Getter
@Setter
@ToString
public class TmgPatternAppliesDTO {

    private String cust;
    private String comp;
    private String empid;
    private String empname;
    private String tpaa_cpatternid;
    private String tpa_cpatternname;
    private String cworktypeid;
    private String cworktypename;

}
