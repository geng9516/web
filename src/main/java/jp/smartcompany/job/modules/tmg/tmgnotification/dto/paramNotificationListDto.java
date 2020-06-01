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
public class paramNotificationListDto {

    /**承認者*/
    private String evaluator;

    /**顧客コード*/
    private String custId;
    /**法人コード*/
    private String compId;
    /**社員番号*/
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
    /**対象社員*/
    private String targetUser;
    /** 年度 */
    private int year;
    private int     thisYear;     // 今年度
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
    /**シーケンス*/
    private String seq;

    private String finalApprovalLevel;

    private String approvalLevel;

    private Date begin;

    private Date end;

    private String timeOpen;

    private String timeClose;

    private String timezoneOpen;

    private String timezoneClose;

    private String noreserved;
    /**曜日*/
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private String sun;

    private String typeNew;
    /**申請事由*/
    private String owncomment;

    private String bosscomment;
    /**傷病*/
    private String txtSickName;
    /**労災申請*/
    private String sickApply;
    /**起算日*/
    private Date txtPeriod;
    /**加算日数*/
    private String txtAddDate;
    private String txtRestOpen;
    private String txtRestClose;
    private String txtName;
    private String txtRelation;
    private Date txtBirthday;
    private String txtTargetNumber;
    private String ntfAction;
    private Date cancel;
    private Date txtDCancelEnd;
    private String cancelcomment;


}
