package jp.smartcompany.job.modules.tmg.schedule.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 陳毅力
 * @description
 * @objectSource
 * @date 2020/08/27
 **/
@Setter
@Getter
@ToString
public class TmgStatusDTO {

    private String fixed_monthly;
    private String fixed_salary;
    private String tda_cstatusflg;
    private String tmo_cstatusflg;
    private String is_future;

}
