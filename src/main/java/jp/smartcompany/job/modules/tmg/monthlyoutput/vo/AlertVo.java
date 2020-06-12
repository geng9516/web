package jp.smartcompany.job.modules.tmg.monthlyoutput.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlertVo {
    private String sectionNick;

    private String EmpName;

    private String yyyymmdd;

    private String tamMessage;

    private String alertCount;
}
