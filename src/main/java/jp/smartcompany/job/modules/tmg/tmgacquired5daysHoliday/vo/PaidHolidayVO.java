package jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 詳細画面検索用VO
 * @author Nie Wanqun
 *
 **/
@Getter
@Setter
@ToString
public class PaidHolidayVO {
    /**
     * 取得日
     */
    private String tdaDyyyymmdd;
    /**
     * 年次有給休暇名称
     */
    private String tdaCworkingname;
    /**
     * 年次有給休暇ID
     */
    private String tdaCworkingidR;
}
