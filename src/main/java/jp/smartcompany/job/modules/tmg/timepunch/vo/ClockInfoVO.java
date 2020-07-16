package jp.smartcompany.job.modules.tmg.timepunch.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description 打刻情報
 * @objectSource
 * @date 2020/07/16
 **/
@Setter
@Getter
@ToString
public class ClockInfoVO {
    private String tda_cemployeeid;
    private String me_ckanjiname;
    private String nopen;
    private String nclose;
    private String tda_nopen_p;
    private String tda_nclose_p;
    private String rest_p;
    private String datetime;
}
