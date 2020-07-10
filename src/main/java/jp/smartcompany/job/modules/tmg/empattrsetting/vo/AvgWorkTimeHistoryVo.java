package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AvgWorkTimeHistoryVo {
    /**
     * 开始时间
     */
    private String tphaDstartdate;
    /**
     * 结束时间
     */
    private String tphaDenddate;
    /**
     * 周勤务时间
     */
    private String tphaNavgworktime;
    /**
     * 周勤务日数
     */
    private String tphaNworkingdaysWeek;


    private String  content;

}
