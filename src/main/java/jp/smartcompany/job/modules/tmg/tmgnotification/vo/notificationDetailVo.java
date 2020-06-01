package jp.smartcompany.job.modules.tmg.tmgnotification.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 * Vo for  NotificationDetail
 */
@Getter
@Setter
@ToString
public class notificationDetailVo {
    //  0 申請者職員番号
    private String tntfCalteremployeeid;
    //  1 申請者氏名
    private String tntfCalteremployeeidName;
    //  2 申請番号
    private String tntfCntfno;
    //  3 対象者職員番号
    private String tntfCemployeeid;
    //  4 対象者氏名
    private String tntfCemployeeidName;
    //  5 承認者職員番号
    private String tntfCboss;
    //  6 承認者氏名
    private String tntfCbossName;
    //  7 申請解除職員番号
    private String tntfCcancel;
    //  8 申請解除者氏名
    private String tntfCcancelName;
    //  9 申請区分
    private String tntfCtype;
    // 10 申請区分名称
    private String tntfCtypeName;
    // 11 開始日
    private String tntfDbegin;
    // 12 終了日
    private String tntfDend;
    // 13 始業後の非勤務時間
    private int tntfNtimeOpen;
    // 14 終業前の非勤務時間
    private int tntfNtimeClose;
    // 15 開始時刻
    private String tntfNtimeOpenHhmi;
    // 16 終了時刻
    private String tntfNtimeCloseHhmi;
    // 17 月曜
    private int tntfNmon;
    // 18 火曜
    private int tntfNtue;
    // 19 水曜
    private int tntfNwed;
    // 20 木曜
    private int tntfNthu;
    // 21 金曜
    private int tntfNfri;
    // 22 土曜
    private int tntfNsat;
    // 23 日曜
    private int tntfNsun;
    // 24 申請事由
    private String tntfCowncomment;
    // 25 承認者コメント
    private String tntfCbosscomment;
    // 26 承認日
    private String tntfDboss;
    // 27 解除日
    private String tntfDcancel;
    // 28 解除者コメント
    private String tntfCcancelcomment;
    // 29 更新日
    private String tntfDmodifieddate;
    // 30 所属
    private String hdCsectionidFk;
    // 31 種別
    private String temCworktypeid;
    // 32 ステータスフラグ
    private String tntfCstatusflg;
    // 33 対象曜日：指定なし
    private int tntfNnoreserved;
    // 34 代休日
    private String tntfDdaikyu;
    // 35 特別休暇：傷病種類
    private String tntfCsickType;
    // 36 特別休暇：傷病名
    private String tntfCsickName;
    // 37 特別休暇：同一傷病区分
    private String tntfCsameSickType;
    // 38 特別休暇：災害申請区分
    private String tntfCdisaster;
    // 39 特別休暇：起算日
    private String tntfDperiodDate;
    // 40 特別休暇：上限加算
    private String tntfNuapperAddition;
    // 41 IMワークフロー用申請番号
    private String tntfCntfnoIm;
    // 42 休憩開始時刻
    private String tntfNrestopen;
    // 43 休憩終了時刻
    private String tntfNrestClose;
    // 44 漢字氏名
    private String tntfCkanjiname;
    // 45 続柄
    private String tntfCrelation;
    // 46 生年月日
    private String tntfDdateofbirth;
    // 47 申請区分表示項目タイプ
    private int tntfCtypeN2;
    // 48 労災申請ステータス名称
    private String tntfCdisasterDetail;
    // 49 申請区分の注釈
    private String tntfCtypeC3;
    // 50 対象の人数
    private String tntfNnumberOfTarget;
    // 51 決裁レベル
    private String approvelLevel;
    // 52 決裁レベル名称
    private String approvelLevelName;
    // 53 役職
    private String postid;
}
