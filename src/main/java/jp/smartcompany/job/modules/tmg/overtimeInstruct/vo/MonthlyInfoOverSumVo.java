package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class MonthlyInfoOverSumVo {
        //0 対象月
        private String monthId;
        //1 出勤日超勤時間
        private String weekdaysOvertime;
        // 2 休日超勤時間
        private String sundaysOvertime;
        //3 超勤合計時間
        private String totalOvertime;
}
