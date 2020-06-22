package jp.smartcompany.job.modules.tmg.tmgnotification.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.security.PrivateKey;

/**
 * @author Wang Ziyue
 *
 */
@Getter
@Setter
@ToString
public class CalendarDto {

    private String minMonth;// 0 年月の最小
    private String maxMonth;// 1 年月の最大
    private String startYearDate;// 2 年度開始日
    private String endYearDate;// 3 年度終了日
}
