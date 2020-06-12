package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvgWorkTimeHistoryVo {
    private String tphaDstartdate;

    private String tphaDenddate;

    private String tphaNavgworktime;

    private String tphaNworkingdaysWeek;

}
