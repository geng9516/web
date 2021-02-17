package jp.smartcompany.job.modules.tmg.tmgnotification.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author Wang Ziyue
 * parameter for  NotificationList
 */
@Getter
@Setter
@ToString
public class ParamNotificationListDto {

    /**承認者*/
    private String evaluator;

    /**顧客コード*/
    private String custId;
    /**法人コード*/
    private String compId;
    /**職員番号*/
    private String userCode;
    /**today
     * yyyy/mm/dd
     * */
    private String today;
    /**today*/
    private Date todayD;
    /**言語*/
    private String lang;
    /**  アクション識別子*/
    private String action;
    /**表示ページ*/
    private String siteId;
    /**対象職員*/
    private String targetUser;
    /** 年度 */
    private String  year;
    private String  thisYear;     // 今年度
    private String  gsStartDate;  // 年度開始日
    private String  gsEndDate;  // 年度終了日
    private boolean gbPreviousYear  = false; // 前年度ボタン
    private boolean gbNextYear      = false; // 翌年度ボタン


    /** 表示中のページ */
    private int page;

    /** システムプロパティ「承認一覧画面表示対象範囲制御判定結果」*/
    private int isNtfTermUseCond;


    /** 勤怠シートの参照権限(基準日の翌月) */
    boolean authorityNextYear       = false;
    /** 勤怠シートの参照権限(基準月) */
    boolean authorityYear           = false;

//検索用
    /** 検索条件・氏名 */
    private String searchEmp;

    /** 申請期間検索条件・開始日・ */
    private String ntfTermBegin;
    /** 申請期間検索条件・終了日・ */
    private String ntfTermEnd;
    /** 検索条件・申請内容 */
    private String type;
    /** 検索条件・状態 */
    private String status;
    /** 検索条件・申請内容 */
    private String searchTypeIdx;

    private String  sTargetEmpId   = null;

    /** 申請番号 */
    private String ntfNo;

    private String mgdSql;

    private String employeeListSql;

//更新用
    /**
     * シーケンス 申请番号
     * */
    private String seq;

    /**
     * 決裁レベル判定用　最終レベル（代理申請用）
     */
    private boolean finalApprovalLevel;
    /**
     * 決裁レベル判定用　当前レベル（代理申請用）
     */
    private String approvelLevel;

    private String detailLevel;

    /**
     * 開始日/出勤にする休日
     */
    private Date begin;
    /**
     * 終了日/振休日とする出勤日
     */
    private Date end;
    /**
     * 始業後
     */
    private String timeOpen;
    /**
     * 終業前
     */
    private String timeClose;
    /**
     * 開始時刻
     */
    private String timezoneOpen;
    /**
     * 終了時刻
     */
    private String timezoneClose;

    /**指定なし*/
    private String noreserved;
    /**曜日*/
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;

    /**申請区分*/
    private String typeNew;

    /**申請事由*/
    private String owncomment;
    /**承認者事由*/
    private String bosscomment;
    /**傷病*/
    private String txtSickName;

    /**労災申請*/
    private String sickApply;

    /**起算日*/
    private Date txtPeriod;
    /**加算日数*/
    private String txtAddDate;

    /**休憩開始時刻*/
    private String txtRestOpen;
    /**休憩終了時刻*/
    private String txtRestClose;

    /**氏名*/
    private String txtName;
    /**続柄*/
    private String txtRelation;
    /**生年月日*/
    private Date txtBirthday;
    /**対象の人数*/
    private String txtTargetNumber;

    /**操作*/
    private String ntfAction;
//取消用
    /**申請取消日*/
    private Date cancel;
    /**取消終了日*/
    private Date txtDCancelEnd;
    /**取消事由*/
    private String cancelcomment;


}
