package jp.smartcompany.job.modules.tmg.overtimeInstruct.vo;

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
public class DailyVo {
    // 0  職員氏名
    private String  tdaCemployeeidName;
    // 1  [申請]開始時刻（HH:MI）
    private String  tdaNopenNC;
    // 2  [申請]終了時刻（HH:MI）
    private String  tdaNcloseNC;
    // 3  [超勤]開始時刻（HH:MI）
    private String  tdaNopenO;
    // 4  [超勤]終了時刻（HH:MI）
    private String  tdaNcloseO;
    // 5  部分休業メッセージ
    private String  tdaCmessage;
    // 6  [超勤]コメント
    private String  tdaCcommentO;
    // 7  日次ステータス
    private String  tdaCstatusflg;
    // 8  職員番号
    private String  tdaCemployeeid;
    // 9  [打刻]始業時刻
    private String  tdaNopenTpC;
    // 10 [打刻]終業時刻
    private String  tdaNcloseTpC;
    // 11 [実績]就業区分
    private String  tdaCworkingidR;
    // 12 [申請]休憩開始時間
    private String  tdaNrestopenN;
    // 13 [申請]休憩終了時間
    private String  tdaNrestcloseN;
    // 14 超過勤務対象有無フラグ
    private String  excludeOvertime;
    // 15 [申請]開始時刻（分数）
    private String  nopenN;
    // 16 [申請]終了時刻（分数）
    private String  ncloseN;
    // 17 [予定]開始時刻（分数）
    private String  nopenP;
    // 18 [予定]終了時刻（分数）
    private String  ncloseP;
    // 19 [申請]休憩時間数の合計
    private String  sumRest;
    // 20 予定勤務時間内の非勤務時間数の合計
    private String  sumNotice;
    // 21 予定休憩時間の登録数
    private String  cntPlanrest;

    private String tdaNopenTp;
    private String tdaNcloseTp;
    private String tdaNopenN;
    private String tdaNcloseN;
}
