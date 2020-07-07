package jp.smartcompany.job.modules.tmg.empattrsetting.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EditDispVo {

    /**
     * 最大上限时间
     */
    private String limitTime;

    /**
     * 历史数据
     */
    private AvgWorkTimeHistoryVo historyList;

    /**
     * 当前平均时间
     */
    private AvgWorkTimeVo nowAvgWorkTime;
}
