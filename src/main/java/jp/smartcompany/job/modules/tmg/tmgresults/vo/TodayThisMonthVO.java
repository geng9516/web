package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 今日と今月情報
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class TodayThisMonthVO {
    /**
     * 今日
     */
    private String today;
    /**
     * 今月
     */
    private String thisMonth;
}
