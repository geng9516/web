package jp.smartcompany.job.modules.tmg_setting.notificationsetting.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class mgdDto implements Serializable {
    private String ntfName;

    private String startDate;
    private String endDate;

    private String chart1;
    private String chart2;
    private String chart3;
    private String chart4;
    private String chart5;

    private Long num1;
    private Long num2;
    private Long num3;
    private Long num4;
    private Long num5;
}
