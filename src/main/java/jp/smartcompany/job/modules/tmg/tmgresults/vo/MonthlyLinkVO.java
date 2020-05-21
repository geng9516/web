package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 前月・翌月の月別情報
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class MonthlyLinkVO {
    /**
     * 前月
     */
    private String previousMonth;
    /**
     * 翌月
     */
    private String nextMonth;
}
