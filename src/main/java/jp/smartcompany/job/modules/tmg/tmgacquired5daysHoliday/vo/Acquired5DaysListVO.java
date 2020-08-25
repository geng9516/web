package jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 一覧/編集画面検索用VO
 *
 * @author Nie Wanqun
 **/
@Getter
@Setter
@ToString
public class Acquired5DaysListVO {

    /**
     * 社員番号
     */
    private String cemployeeid0;
    /**
     * 社員名
     */
    private String cemployeeName1;
    /**
     * 所属名
     */
    private String workertypeNm2;
    /**
     * 付与日
     */
    private String taDyyyymmdd3;
    /**
     * 付与日数
     */
    private String taFuyodays4;
    /**
     * 基準日
     */
    private String taKijyunbi5;
    /**
     * チェック期間
     */
    private String taKikanbi6;
    /**
     * 一覧画面表示取得日数
     */
    private String sumSyutokudays7;
    /**
     * 必要日数
     */
    private String taMustdays8;
    /**
     * 不足日数
     */
    private String taLessdays9;
    /**
     * ROWPAN
     */
    private String taRowpan10;
    /**
     * 重複Flg
     */
    private String taCduplicateflg;
    /**
     * 修正基準日
     */
    private String tpfDpaidHolidayFix11;
    /**
     * 修正チェック期間
     */
    private String tpfDkikanbiFix12;
    /**
     * 調整取得日数
     */
    private String tpfNusedaysAjdust13;
    /**
     * 修正必要日数
     */
    private String tpfNmustdaysDix14;

//    /**
//     * 所属Id
//     */
//    private String workertypeId;
//    /**
//     * 年休調査期間（開始日
//     */
//    private String tpDntfSurveyStart;
//    /**
//     * 年休調査期間（終了日）
//     */
//    private String tpDntfSurveyEnd;
//    /**
//     * 修正取得日数
//     */
//    private String tpfSyutokudays;
//    /**
//     * 修正不足日数
//     */
//    private String tpfLessdays;
//    /**
//     * 編集画面表示取得日数
//     */
//    private String tpSyutokudays;
//    /**
//     * 修正終期
//     */
//    private String tpfHolidayendfix;
//
//    /**
//     * 修正必要日数
//     */
//    private String tpfNusedaysfix ;

}
