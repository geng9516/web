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
     * 職員番号
     */
    private String cemployeeid0;
    /**
     * 職員名
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
    private String tpfNmustdaysFix14;
    /**
     * 修正页面用必要日数
     */
    private String fixMustdays15;
    /**
     * 修正页面用取得日数
     */
    private String fixSumSyutokudays16;
    /**
     * 修正页面用取得日数
     */
    private String fixTaKikanbi17;

}
