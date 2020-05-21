package jp.smartcompany.job.modules.tmg.tmgresults.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 日別情報(編集用)VO
 *
 * @author Nie Wanqun
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class DailyEditVO {

    /**
     * ステータス
     */
    private String tdaCstatusflg;
    /**
     * エラーメッセージ
     */
    private String tdaCerrcode;
    /**
     * 日付
     */
    private String tdaDyyyymmdd;
    /**
     * 日付(表示)
     */
    private String tdaDyyyymmddDy;
    /**
     * [実績]就業区分
     */
    private String tdaCworkingidR;
    /**
     * [実績]就業区分名称
     */
    private String tdaCworkingidRBame;
    /**
     * [申請反映]始業時刻
     */
    private String tdaNopenN;
    /**
     * [申請反映]終業時刻
     */
    private String tdaNcloseN;
    /**
     * 打刻始業時刻
     */
    private String tdaNopenTp;
    /**
     * 打刻終業時刻
     */
    private String tdaNcloseTp;
    /**
     * [実績]始業時刻
     */
    private String tdaNopenR;
    /**
     * [実績終業時刻
     */
    private String tdaNcloseR;
    /**
     * [実績]休憩開始時刻
     */
    private String tdaNrestopenR;
    /**
     * [実績]休憩終了時刻
     */
    private String tdaNrestcloseR;
    /**
     * 超勤開始時刻
     */
    private String tdaNopenO;
    /**
     * 超勤終了時刻
     */
    private String tdaNcloseO;
    /**
     * 超勤コメント
     */
    private String tdaCcommentO;
    /**
     * 本人コメント
     */
    private String tdaCowncommentR;
    /**
     * 承認者コメント
     */
    private String tdaCbosscommentR;
    /**
     * コメント記入者
     */
    private String tdaCmodifieruseridR;
    /**
     * コメント記載日
     */
    private String tdaDmodifieddateR;
    /**
     * [予定]始業時刻
     */
    private String tdaNopenP;
    /**
     * [予定]終業時刻
     */
    private String tdaNcloseP;
    /**
     * 出張区分
     */
    private String tdaCbusinesstripidR;
    /**
     * [実績]出張区分名称
     */
    private String tdaCbusinesstripidRName;
    /**
     * [予定]休憩45分選択
     */
    private String tdaNrest45P;
    /**
     * [予定]休憩45分選択
     */
    private String tdaNclosePAtRest45;
    /**
     * [標準]勤務時間数
     */
    private String ntime;
    /**
     * [hidden用]始業時刻
     */
    private String tdaNopenRHidden;
    /**
     * [hidden用]終業時刻
     */
    private String tdaNcloseRHidden;
    /**
     * ノー残業デー表示メッセージ
     */
    private String noOvertimeDaysMsg;


}
