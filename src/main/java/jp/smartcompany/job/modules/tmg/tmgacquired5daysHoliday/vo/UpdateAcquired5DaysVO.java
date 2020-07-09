package jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 編集画面更新用VO
 *
 * @author Nie Wanqun
 **/
@Getter
@Setter
@ToString
public class UpdateAcquired5DaysVO {

    /**
     * recordDate
     */
    private String recordDate;
    /**
     * 対象者
     */
    private String txtUserCode;
    /**
     * 対象年度
     */
    private String txtYear;
    /**
     * 基準日
     */
    private String kijunbi;
    /**
     * 修正基準日
     */
    private String kijunbiEdit;
    /**
     * 調整取得日数
     */
    private String usedDaysEdit;

    /**
     * 1:登録　2:クリア
     */
    private String txtUpdateflg;

}