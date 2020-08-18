package jp.smartcompany.job.modules.tmg.tmgacquired5daysHoliday.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 一覧/編集画面検索用VO
 * @author Nie Wanqun
 *
 **/
@Getter
@Setter
@ToString
public class Acquired5DaysListVO {

    /**
     * 社員番号
     */
    private String taCemployeeid;
    /**
     * 社員名
     */
    private String cemployeeName;
    /**
     * 所属Id
     */
    private String workertypeId;
    /**
     * 所属名
     */
    private String workertypeNm;
    /**
     * 付与日
     */
    private String taDyyyymmdd;
    /**
     * 付与日数
     */
    private String taFuyodays;
    /**
     * 基準日
     */
    private String taKijyunbi;
    /**
     * チェック期間
     */
    private String taKikanbi;
    /**
     * 一覧画面表示取得日数
     */
    private String sumSyutokudays;
    /**
     * 必要日数
     */
    private String taMustdays;
    /**
     * 不足日数
     */
    private String taLessdays;
    /**
     * ROWPAN
     */
    private String taRowpan;
    /**
     * 重複Flg
     */
    private String taCduplicateflg;
    /**
     * 年休調査期間（開始日
     */
    private String tpDntfSurveyStart;
    /**
     * 年休調査期間（終了日）
     */
    private String tpDntfSurveyEnd;
    /**
     * 修正基準日
     */
    private String tpfKijyunbi;
    /**
     * 修正チェック期間
     */
    private String tpfKikanbi;
    /**
     * 修正取得日数
     */
    private String tpfSyutokudays;
    /**
     * 調整取得日数
     */
    private String tpfSyutokudaysAdjust;
    /**
     * 修正必要日数
     */
    private String tpfMustdays;
    /**
     * 修正不足日数
     */
    private String tpfLessdays;
    /**
     * 編集画面表示取得日数
     */
    private String tpSyutokudays;
    /**
     * 修正終期
     */
    private String tpfHolidayendfix;

    /**
     * 修正必要日数
     */
    private String tpfNusedaysfix ;

}
