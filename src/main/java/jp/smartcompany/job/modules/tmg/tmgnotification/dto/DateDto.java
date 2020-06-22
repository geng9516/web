package jp.smartcompany.job.modules.tmg.tmgnotification.dto;


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
public class DateDto {
    private String startDate;// 0 開始日
    private String endDate;// 1 終了日
    private String baseDate; // 2 本日
}
