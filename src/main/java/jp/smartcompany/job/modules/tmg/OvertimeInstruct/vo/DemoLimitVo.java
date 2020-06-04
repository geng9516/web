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
public class DemoLimitVo {

    /* 各種閾値 */
    /** 閾値(超過勤務:日) Level1 */
    private String LIMIT_OVER_WORK_LVL1           = "0";
    /** 閾値(合計:月) Level1 */
    private String LIMIT_SUM_MONTH_LVL1           = "0";
    /** 閾値(合計:月) Level2 */
    private String LIMIT_SUM_MONTH_LVL2           = "0";
    /** 閾値(合計:月) Level3 */
    private String LIMIT_SUM_MONTH_LVL3           = "0";
    /** 閾値(合計:月) Level4 */
    private String LIMIT_SUM_MONTH_LVL4           = "0";
    /** 閾値(合計:月) Level5 */
    private String LIMIT_SUM_MONTH_LVL5           = "0";
    /** 閾値(合計:年) Level1 */
    private String LIMIT_SUM_YEAR_LVL1            = "0";
    /** 閾値(合計:年) Level2 */
    private String LIMIT_SUM_YEAR_LVL2            = "0";
    /** 閾値(合計:年) Level3 */
    private String LIMIT_SUM_YEAR_LVL3            = "0";
    /** 閾値(合計:年) Level4 */
    private String LIMIT_SUM_YEAR_LVL4            = "0";
    /** 閾値(合計:年) Level5 */
    private String LIMIT_SUM_YEAR_LVL5            = "0";
    /** 閾値(月超勤回数) */
    private String LIMIT_COUNT                    = "0";
    /** 閾値(休日出勤回数) Level1 */
    private String LIMIT_HOL_CNT_LVL1             = "0";
    /** 閾値(休日出勤回数) Level2 */
    private String LIMIT_HOL_CNT_LVL2             = "0";
    /** 閾値(休日出勤回数) Level3 */
    private String LIMIT_HOL_CNT_LVL3             = "0";
    /** 閾値(休日出勤回数) Level4 */
    private String LIMIT_HOL_CNT_LVL4             = "0";
    /** 閾値(休日出勤回数) Level5 */
    private String LIMIT_HOL_CNT_LVL5             = "0";
    /** 閾値(月平均超勤時間) */
    private String LIMIT_OVERWORK_MONTH_AVG        = "0";
    /** 超勤実績平均(月) */
    private String LABEL_AVERAGE_TIME     		   = "0";

}
