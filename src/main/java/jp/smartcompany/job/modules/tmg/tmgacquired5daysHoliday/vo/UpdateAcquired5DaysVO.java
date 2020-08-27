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
     * 修正基準日
     */
    private String kijunbiEdit;
    /**
     * 修正チェック期間
     */
    private String dkikanbiFix;
    /**
     * 調整取得日数
     */
    private String usedDaysEdit;

    /**
     * 1:登録　2:クリア
     */
    private String txtUpdateflg;
    /**
     * 年次休暇付与日
     */
    private String txtFuyobi;
//    /**
//     * 対象年度
//     */
//    private String txtYear;
//    /**
//     * 基準日
//     */
//    private String kijunbi;
//    /**
//     * 修正終期
//     */
//    private String txtDpaidholidayEnd;
    /**
     * 修正必要日数
     */
    private String txtNusedaysDays;

}
