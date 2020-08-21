package jp.smartcompany.job.modules.tmg.util;

import cn.hutool.core.date.DateUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.core.util.PsResult;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * @author Xiao Wenpeng
 * 勤怠コンテンツで利用される定数や、汎用メソッドを提供するクラスです。
 */
public class TmgUtil {

    /**
     * 勤怠入力サイトのサイトIDを表す文字列です
     */
    public static final String Cs_SITE_ID_TMG_INP    = "TMG_INP";
    /**
     * 勤怠承認サイトのサイトIDを表す文字列です
     */
    public static final String Cs_SITE_ID_TMG_PERM   = "TMG_PERM";
    /**
     * 勤怠管理サイトのサイトIDを現す文字列です
     */
    public static final String Cs_SITE_ID_TMG_ADMIN  = "TMG_ADMIN";

    /**
     * 勤怠承認の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_RESULT		= "RESULT";			// 勤怠承認
    /**
     * 休暇承認の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_NOTIFICATION	= "NOTIFICATION";	// 休暇承認
    /**
     * 超勤命令の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_OVERTIME		= "OVERTIME";		// 超勤命令
    /**
     * 権限付与の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_AUTHORITY	    = "AUTHORITY";		// 権限付与

    /**
     * 予定作成の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_SCHEDULE	    = "SCHEDULE";		// 権限付与

    /**
     * 勤怠承認(月次)の権限を表す権限コードです<br>
     * TmgReferListの権限判定メソッドを実行する際に使用します
     */
    public static final String Cs_AUTHORITY_MONTHLYAPPROVAL	    = "MONTHLYAPPROVAL";		// 勤怠承認(月次)

    /**
     * 部署直下を表すグループに付けられる、デフォルトグループ番号です
     */
    public static final String Cs_DEFAULT_GROUPSEQUENCE = "000000";

    //フォーマット
    public static final String Cs_MM 			= "MM";
    public static final String Cs_YYYY 		= "YYYY";
    public static final String Cs_YYYYMM 		= "YYYYMM";
    public static final String Cs_YYYYMMDD	= "YYYYMMDD";
    public static final String Cs_FM00 		= "FM00";

    /**
     * レコードのデータ開始日の最小値です<BR>
     *   この文字列は、フォーマットを指定し、TO_DATE関数を使用しているので<B>そのまま使用できます。</B><BR><BR>
     *   使用例)<BR>
     *    "テーブル名.日付カラム <= " + Cs_MINDATE + " ";
     */
    public static final String Cs_MINDATE = "TO_DATE('1900/01/01', 'YYYY/MM/DD')"; //データ開始日
    /**
     * レコードのデータ終了日の最大値です<BR>
     *   この文字列は、フォーマットを指定し、TO_DATE関数を使用しているので<B>そのまま使用できます。</B><BR><BR>
     *   使用例)<BR>
     *    "テーブル名.日付カラム >= " + Cs_MAXDATE + " ";
     */
    public static final String Cs_MAXDATE = "TO_DATE('2222/12/31', 'YYYY/MM/DD')"; //データ終了日

    /**
     * レコードのデータ終了日の最大値です<BR>
     *   この文字列は、フォーマットを指定していません。</B><BR><BR>
     */
    public static final String Cs_DEFAULT_MAXDATE = "2222/12/31";    //データ終了日(TO_DATE関数なし)

    /**
     * 日本語の言語区分を表すコードです
     */
    public static final String Cs_CLANGUAGE_J = "ja";
    /**
     * 英語の言語区分を表すコードです
     */
    public static final String Cs_CLANGUAGE_E = "en";
    /**
     * ARTERIALのフェーズ
     * SQLの中で使うのでString
     */
    public static final String Cn_PHASE_SHEET_INSERT     = "100" ;  // 勤怠シート作成処理
    public static final String Cn_PHASE_SET_EMPLOYEES    = "200" ;  // 入社退職反映処理
    public static final String Cn_PHASE_SET_SUSPENSION   = "300" ;  // 休職反映処理
    public static final String Cn_PHASE_SET_NOTIFICATION = "400" ;  // 申請反映処理
    public static final String Cn_PHASE_SET_TIMEPUNCH    = "500" ;  // 打刻反映処理
    public static final String Cn_PHASE_VALIDATE_DAILY   = "600" ;  // 実績整合性チェック処理
    public static final String Cn_PHASE_CHECK_RESULTS    = "650" ;  // 勤怠承認時(論理エラーチェック時に使う)
    public static final String Cn_PHASE_APPROVE_HOLIDAY  = "700" ;  // 休暇日自動承認処理
    public static final String Cn_PHASE_CALC_DAILY       = "800" ;  // 日別勤怠計算処理
    public static final String Cn_PHASE_CALC_HOLIDAY     = "900" ;  // 年次休暇計算処理
    public static final String Cn_PHASE_CALC_MONTHLY     = "1000";  // 月別勤怠計算処理
    public static final String Cn_PHASE_SET_MONTHLY_INFO = "1100";  // 月単位日別情報更新処理


    /**
     * 論理エラーチェックの実行結果が「エラー無し」であることを表す文字列です
     */
    public static final String Cs_NO_ERROR_BY_AJAX_ERROR_CHECK = "0";

    // 2007.10.15 tanaka #175 想定外の例外が発生した場合のハンドリング処理
    /** SQL実行時の例外 */
    public static final int Cs_EXECUTE_QUERY_FAILER  = -2;
    /** コネクション取得時の例外 */
    public static final int Cs_GET_CONNECTION_FAILER = -1;
    /** sessionへ登録する想定外の例外キー */
    public static final String Cs_INSERT_ERROR_MSG = "txtInsertErrorMsg";

    private static TmgUtil tmgUtil = null;

    /** メール未送信:0 */
    public static final String Cs_MAIL_UNSEND  = "0";
    /** メール送信:1 */
    public static final String Cs_MAIL_SEND  = "1";

    /** 遅延理由を非表示:0 */
    public static final String Cs_DELAY_REASON_HIDDEN  = "0";
    /** 遅延理由を表示:1 */
    public static final String Cs_DELAY_REASON_VIEW  = "1";

    /** 組織ツリー検索タブで使用している検索項目・検索条件 */
    public static final String Cs_TREE_VIEW_ITEMS_KANANAME = "KANANAME";               // カナ氏名
    public static final String Cs_TREE_VIEW_ITEMS_KANJINAME = "KANJIMAME";             // 漢字氏名
    public static final String Cs_TREE_VIEW_ITEMS_EMPLOYEEID = "EMPLOYEEID";           // 職員番号
    public static final String Cs_TREE_VIEW_CONDITION_BROADMATCH = "BROADMATCH";       // 部分一致
    public static final String Cs_TREE_VIEW_CONDITION_PREFIXSEARCH = "PREFIXSEARCH";   // 前方一致
    public static final String Cs_TREE_VIEW_CONDITION_BACKWARDMATCH = "BACKWARDMATCH"; // 後方一致

    public static final String Cs_TmgDispLimit4TreeDefault = "100";

    /** 使用ブラウザ識別子　Internet Explorer */
    public static final String BROWSER_IE = "IE";
    /** 使用ブラウザ識別子　Firefox */
    public static final String BROWSER_FF = "FF";
    /** 使用ブラウザ識別子　Safari */
    public static final String BROWSER_SF = "SF";



    /*
	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	                      名称マスタのコード一覧

	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    */
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>計算ステータス</b>"を表すグループIDです
     */
    public static final String Cs_MGD_CALCSTATUS = "TMG_CALCSTATUS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>処理前</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CALCSTATUS_0 = "TMG_CALCSTATUS|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>成功</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CALCSTATUS_1 = "TMG_CALCSTATUS|1";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>失敗</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CALCSTATUS_9 = "TMG_CALCSTATUS|9";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>月別情報集計パターン</b>"を表すグループIDです
     */
    public static final String Cs_MGD_CALC_PATTERN = "TMG_CALC_PATTERN";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>日毎に四捨五入した時間を合計</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CALC_PATTERN_0 = "TMG_CALC_PATTERN|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>月の合計時間を四捨五入</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CALC_PATTERN_1 = "TMG_CALC_PATTERN|1";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>コンテンツ区分</b>"を表すグループIDです
     */
    public static final String Cs_MGD_CONTENTID = "TMG_CONTENTID";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示用</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CONTENTID_OTI = "TMG_CONTENTID|OTI";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務実績用</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CONTENTID_OTR = "TMG_CONTENTID|OTR";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務実績用(法定内含む)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CONTENTID_OTRA = "TMG_CONTENTID|OTRA";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>承認状況一覧用</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_CONTENTID_PSL = "TMG_CONTENTID|PSL";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>日別ユーザー定義項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DAILYITEMS = "TMG_DAILYITEMS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間125</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_01 = "TMG_DAILYITEMS|01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間135</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_02 = "TMG_DAILYITEMS|02";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間150</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_03 = "TMG_DAILYITEMS|03";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間160</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_04 = "TMG_DAILYITEMS|04";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>夜間勤務．時間25</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_05 = "TMG_DAILYITEMS|05";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休日勤務．時間135</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_06 = "TMG_DAILYITEMS|06";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_07 = "TMG_DAILYITEMS|07";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_08 = "TMG_DAILYITEMS|08";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>時給者．勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_09 = "TMG_DAILYITEMS|09";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間100</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_10 = "TMG_DAILYITEMS|10";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_11 = "TMG_DAILYITEMS|11";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤時数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_12 = "TMG_DAILYITEMS|12";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>給与計算用勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_13 = "TMG_DAILYITEMS|13";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>給与計算用勤務時数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_14 = "TMG_DAILYITEMS|14";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_15 = "TMG_DAILYITEMS|15";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_16 = "TMG_DAILYITEMS|16";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_17 = "TMG_DAILYITEMS|17";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_18 = "TMG_DAILYITEMS|18";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_19 = "TMG_DAILYITEMS|19";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DAILYITEMS_20 = "TMG_DAILYITEMS|20";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>データステータス</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DATASTATUS = "TMG_DATASTATUS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>未入力</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_0 = "TMG_DATASTATUS|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>打刻後未チェック</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_1 = "TMG_DATASTATUS|1";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>反映エラー</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_2 = "TMG_DATASTATUS|2";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>承認待</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_3 = "TMG_DATASTATUS|3";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>承認済</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_5 = "TMG_DATASTATUS|5";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>確定済</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_DATASTATUS_9 = "TMG_DATASTATUS|9";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>エラーコード</b>"を表すグループIDです
     */
    public static final String Cs_MGD_ERRCODE = "TMG_ERRCODE";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>既に登録されているグループです。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_EVAL_SET_001 = "TMG_ERRCODE|EVAL_SET_001";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>既に承認者として登録されている職員です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_EVAL_SET_002 = "TMG_ERRCODE|EVAL_SET_002";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>グループ名は空白以外の文字を1文字以上含む必要があります。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_EVAL_SET_003 = "TMG_ERRCODE|EVAL_SET_003";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>対象期間は@1@から@2@の範囲で指定してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_030 = "TMG_ERRCODE|NOTIFICATION_030";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>期間開始日には入社日以降を指定してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_032 = "TMG_ERRCODE|NOTIFICATION_032";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>期間終了日には退職日以前を指定してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_033 = "TMG_ERRCODE|NOTIFICATION_033";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護休業の期間が6ヶ月を超えています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_034 = "TMG_ERRCODE|NOTIFICATION_034";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護部分休業の期間が6ヶ月を超えています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_035 = "TMG_ERRCODE|NOTIFICATION_035";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時の非勤務時間は@1@分単位で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_036 = "TMG_ERRCODE|NOTIFICATION_036";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>終業時の非勤務時間は@1@分単位で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_037 = "TMG_ERRCODE|NOTIFICATION_037";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非勤務時間は@1@分単位で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_038 = "TMG_ERRCODE|NOTIFICATION_038";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の育児部分休業の開始時刻が17:00以前です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_039 = "TMG_ERRCODE|NOTIFICATION_039";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非勤務開始時刻が@1@の始業時刻以前です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_040 = "TMG_ERRCODE|NOTIFICATION_040";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非勤務終了時刻が@1@の終業時刻以降です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_041 = "TMG_ERRCODE|NOTIFICATION_041";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>申請の重複があります。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_042 = "TMG_ERRCODE|NOTIFICATION_042";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>申請が反映される勤務日がありません。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_043 = "TMG_ERRCODE|NOTIFICATION_043";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の@2@が1日の取得上限を超えます。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_044 = "TMG_ERRCODE|NOTIFICATION_044";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の部分休業時間の合計が1日の勤務時間を超えます。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_045 = "TMG_ERRCODE|NOTIFICATION_045";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇残日数が@1@日不足しています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_046 = "TMG_ERRCODE|NOTIFICATION_046";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇残時間数が@1@時間不足しています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_047 = "TMG_ERRCODE|NOTIFICATION_047";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の予定勤務時間が8時間ではありません。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_050 = "TMG_ERRCODE|NOTIFICATION_050";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>申請解除日には@1@以降を指定してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_NOTIFICATION_051 = "TMG_ERRCODE|NOTIFICATION_051";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@に@2@をセットするサンプルです。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_000 = "TMG_ERRCODE|PROGRAMID_000";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>出勤日でない日に対して始業時刻が入力されています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_001 = "TMG_ERRCODE|RESULTS_001";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>出勤日でない日に対して終業時刻が入力されています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_002 = "TMG_ERRCODE|RESULTS_002";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>出勤日でない日に対して休憩開始時刻が入力されています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_003 = "TMG_ERRCODE|RESULTS_003";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>出勤日でない日に対して休憩終了時刻が入力されています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_004 = "TMG_ERRCODE|RESULTS_004";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時刻入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_005 = "TMG_ERRCODE|RESULTS_005";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>終業時刻入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_006 = "TMG_ERRCODE|RESULTS_006";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時刻＜終業時刻となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_007 = "TMG_ERRCODE|RESULTS_007";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は@2@以降になるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_008 = "TMG_ERRCODE|RESULTS_008";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は@2@以前になるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_009 = "TMG_ERRCODE|RESULTS_009";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩開始時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_010 = "TMG_ERRCODE|RESULTS_010";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩終了時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_011 = "TMG_ERRCODE|RESULTS_011";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩開始時刻＜休憩終了時刻となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_012 = "TMG_ERRCODE|RESULTS_012";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時刻＜休憩開始時刻となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_013 = "TMG_ERRCODE|RESULTS_013";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩終了時刻＜終業時刻となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_014 = "TMG_ERRCODE|RESULTS_014";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤時間が予定勤務時間と重複しています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_015 = "TMG_ERRCODE|RESULTS_015";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩時間は@1@分間となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_016 = "TMG_ERRCODE|RESULTS_016";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩時間は@1@分間となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_017 = "TMG_ERRCODE|RESULTS_017";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時刻が休憩時間内です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_018 = "TMG_ERRCODE|RESULTS_018";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>終業時刻が休憩時間内です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_019 = "TMG_ERRCODE|RESULTS_019";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務開始時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_020 = "TMG_ERRCODE|RESULTS_020";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務終了時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_021 = "TMG_ERRCODE|RESULTS_021";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示が出ていない時間帯での勤務が合計30分以上です。\r\n予定又は超勤命令どおりに承認してよろしいですか？</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_022 = "TMG_ERRCODE|RESULTS_022";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示が出ていない時間帯での勤務が合計30分以上です。\r\n予定又は超勤命令どおりに承認してよろしいですか？</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_023 = "TMG_ERRCODE|RESULTS_023";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示が出ていない時間帯での勤務が合計30分以上です。\r\n予定又は超勤命令どおりに承認してよろしいですか？</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_024 = "TMG_ERRCODE|RESULTS_024";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示が出ていない時間帯での勤務が合計30分以上です。\r\n予定又は超勤命令どおりに承認してよろしいですか？</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_025 = "TMG_ERRCODE|RESULTS_025";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務指示が出ていない時間帯での勤務が合計30分以上です。\r\n予定又は超勤命令どおりに承認してよろしいですか？</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_026 = "TMG_ERRCODE|RESULTS_026";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の開始時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_027 = "TMG_ERRCODE|RESULTS_027";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@の終了時刻を入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_028 = "TMG_ERRCODE|RESULTS_028";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@を開始時刻＜終了時刻となるようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_029 = "TMG_ERRCODE|RESULTS_029";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は勤務時間の範囲内で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_030 = "TMG_ERRCODE|RESULTS_030";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は勤務時間の範囲内で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_031 = "TMG_ERRCODE|RESULTS_031";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は勤務時間の範囲内で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_032 = "TMG_ERRCODE|RESULTS_032";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は勤務時間の範囲内で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_033 = "TMG_ERRCODE|RESULTS_033";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は予定時間の範囲外で入力してください</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_034 = "TMG_ERRCODE|RESULTS_034";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は@2@以降に開始するようにしてください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_035 = "TMG_ERRCODE|RESULTS_035";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>始業時刻が非勤務時間内です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_036 = "TMG_ERRCODE|RESULTS_036";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>終業時刻が非勤務時間内です。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_037 = "TMG_ERRCODE|RESULTS_037";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@が休憩時間と重複しています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_038 = "TMG_ERRCODE|RESULTS_038";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>部分休業が重複しています。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_039 = "TMG_ERRCODE|RESULTS_039";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@は@2@分単位で入力してください。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_040 = "TMG_ERRCODE|RESULTS_040";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤時間が予定勤務時間から離れています。<br>離席時間をセットしますか。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_041 = "TMG_ERRCODE|RESULTS_041";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤時間が予定勤務時間から離れています。<br>離席時間をセットしますか。</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_RESULTS_042 = "TMG_ERRCODE|RESULTS_042";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>エラーです</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_SUNA_001 = "TMG_ERRCODE|SUNA_001";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>@1@と@2@がエラーです</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_SUNA_002 = "TMG_ERRCODE|SUNA_002";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>一括付与時残不足エラー</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_BULK_NTF_SUB_001 = "TMG_ERRCODE|BULK_NTF_SUB_001";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年休取得時残不足エラー</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_BULK_NTF_SUB_002 = "TMG_ERRCODE|BULK_NTF_SUB_002";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>月次承認対象月に未承認の休暇承認存在エラー</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ERRCODE_MONTHLYAPPROVAL_001 = "TMG_ERRCODE|MONTHLYAPPROVAL_001";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>例外デフォルト承認者</b>"を表すグループIDです
     */
    public static final String Cs_MGD_EVAL_EXCEPTION = "TMG_EVAL_EXCEPTION";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>休日フラグ</b>"を表すグループIDです
     */
    public static final String Cs_MGD_HOLFLG = "TMG_HOLFLG";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>平日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_HOLFLG_0 = "TMG_HOLFLG|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>週休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_HOLFLG_1 = "TMG_HOLFLG|1";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_HOLFLG_2 = "TMG_HOLFLG|2";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>*週休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_HOLFLG_3 = "TMG_HOLFLG|3";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、非常勤の休日 を表すマスターコードです
     */
    public static final String Cs_MGD_HOLFLG_HIJOKIN = "TMG_HOLFLG|4";	//#406 上記のコードなぜ、定数を名前につけている

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>閾値</b>"を表すグループIDです
     */
    public static final String Cs_MGD_LIMIT = "TMG_LIMIT";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の月次警告値超過回数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTHLY_COUNT = "TMG_LIMIT|OT_MONTHLY_COUNT";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(月次：黄色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTLY_01 = "TMG_LIMIT|OT_MONTLY_01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(月次：赤色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTLY_02 = "TMG_LIMIT|OT_MONTLY_02";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(月次：青色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTLY_03 = "TMG_LIMIT|OT_MONTLY_03";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(月次：オレンジ色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTLY_04 = "TMG_LIMIT|OT_MONTLY_04";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(月次：紫色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_MONTLY_05 = "TMG_LIMIT|OT_MONTLY_05";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(年次：黄色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_YEARLY_01 = "TMG_LIMIT|OT_YEARLY_01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(年次：赤色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_YEARLY_02 = "TMG_LIMIT|OT_YEARLY_02";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(年次：青色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_YEARLY_03 = "TMG_LIMIT|OT_YEARLY_03";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(年次：オレンジ色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_YEARLY_04 = "TMG_LIMIT|OT_YEARLY_04";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超勤実績の警告値(時間)(年次：紫色)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OT_YEARLY_05 = "TMG_LIMIT|OT_YEARLY_05";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非常勤時間雇用者の1日当たり労働時間数(基準)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OUTPUT_WEEKLY_01 = "TMG_LIMIT|OUTPUT_WEEKLY_01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非常勤時間雇用者の1日当たり労働時間数(上限)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_OUTPUT_WEEKLY_02 = "TMG_LIMIT|OUTPUT_WEEKLY_02";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>36協定における月の超勤限度時間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_LIMIT_MONTHLY_OVERTIME_36 = "TMG_LIMIT|MONTHLY_OVERTIME_36";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>月別ユーザー定義項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_MONTHLYITEMS = "TMG_MONTHLYITEMS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間125</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_001 = "TMG_MONTHLYITEMS|001";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間135</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_002 = "TMG_MONTHLYITEMS|002";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間150</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_003 = "TMG_MONTHLYITEMS|003";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間160</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_004 = "TMG_MONTHLYITEMS|004";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>夜間勤務．時間25</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_005 = "TMG_MONTHLYITEMS|005";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休日勤務．時間135</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_006 = "TMG_MONTHLYITEMS|006";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_007 = "TMG_MONTHLYITEMS|007";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_008 = "TMG_MONTHLYITEMS|008";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>時給者．勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_009 = "TMG_MONTHLYITEMS|009";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務．時間100</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_010 = "TMG_MONTHLYITEMS|010";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_011 = "TMG_MONTHLYITEMS|011";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤時数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_012 = "TMG_MONTHLYITEMS|012";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>給与計算用勤務日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_013 = "TMG_MONTHLYITEMS|013";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>給与計算用勤務時数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_014 = "TMG_MONTHLYITEMS|014";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>遅刻回数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_015 = "TMG_MONTHLYITEMS|015";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>早退回数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_016 = "TMG_MONTHLYITEMS|016";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休出日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_017 = "TMG_MONTHLYITEMS|017";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_018 = "TMG_MONTHLYITEMS|018";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_019 = "TMG_MONTHLYITEMS|019";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_020 = "TMG_MONTHLYITEMS|020";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_021 = "TMG_MONTHLYITEMS|021";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_022 = "TMG_MONTHLYITEMS|022";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_023 = "TMG_MONTHLYITEMS|023";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_024 = "TMG_MONTHLYITEMS|024";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_025 = "TMG_MONTHLYITEMS|025";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_026 = "TMG_MONTHLYITEMS|026";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_027 = "TMG_MONTHLYITEMS|027";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_028 = "TMG_MONTHLYITEMS|028";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_029 = "TMG_MONTHLYITEMS|029";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_030 = "TMG_MONTHLYITEMS|030";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_031 = "TMG_MONTHLYITEMS|031";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_032 = "TMG_MONTHLYITEMS|032";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_033 = "TMG_MONTHLYITEMS|033";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_034 = "TMG_MONTHLYITEMS|034";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_035 = "TMG_MONTHLYITEMS|035";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_036 = "TMG_MONTHLYITEMS|036";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_037 = "TMG_MONTHLYITEMS|037";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_038 = "TMG_MONTHLYITEMS|038";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_039 = "TMG_MONTHLYITEMS|039";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_040 = "TMG_MONTHLYITEMS|040";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_041 = "TMG_MONTHLYITEMS|041";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_042 = "TMG_MONTHLYITEMS|042";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_043 = "TMG_MONTHLYITEMS|043";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_044 = "TMG_MONTHLYITEMS|044";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_045 = "TMG_MONTHLYITEMS|045";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_046 = "TMG_MONTHLYITEMS|046";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_047 = "TMG_MONTHLYITEMS|047";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_048 = "TMG_MONTHLYITEMS|048";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_049 = "TMG_MONTHLYITEMS|049";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_050 = "TMG_MONTHLYITEMS|050";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_051 = "TMG_MONTHLYITEMS|051";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_052 = "TMG_MONTHLYITEMS|052";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_053 = "TMG_MONTHLYITEMS|053";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_054 = "TMG_MONTHLYITEMS|054";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_055 = "TMG_MONTHLYITEMS|055";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_056 = "TMG_MONTHLYITEMS|056";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_057 = "TMG_MONTHLYITEMS|057";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_058 = "TMG_MONTHLYITEMS|058";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_059 = "TMG_MONTHLYITEMS|059";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_060 = "TMG_MONTHLYITEMS|060";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_061 = "TMG_MONTHLYITEMS|061";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_062 = "TMG_MONTHLYITEMS|062";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_063 = "TMG_MONTHLYITEMS|063";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_064 = "TMG_MONTHLYITEMS|064";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_065 = "TMG_MONTHLYITEMS|065";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_066 = "TMG_MONTHLYITEMS|066";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_067 = "TMG_MONTHLYITEMS|067";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_068 = "TMG_MONTHLYITEMS|068";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_069 = "TMG_MONTHLYITEMS|069";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_070 = "TMG_MONTHLYITEMS|070";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_071 = "TMG_MONTHLYITEMS|071";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_072 = "TMG_MONTHLYITEMS|072";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_073 = "TMG_MONTHLYITEMS|073";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_074 = "TMG_MONTHLYITEMS|074";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_075 = "TMG_MONTHLYITEMS|075";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_076 = "TMG_MONTHLYITEMS|076";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_077 = "TMG_MONTHLYITEMS|077";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_078 = "TMG_MONTHLYITEMS|078";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_079 = "TMG_MONTHLYITEMS|079";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_080 = "TMG_MONTHLYITEMS|080";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_081 = "TMG_MONTHLYITEMS|081";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_082 = "TMG_MONTHLYITEMS|082";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_083 = "TMG_MONTHLYITEMS|083";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_084 = "TMG_MONTHLYITEMS|084";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_085 = "TMG_MONTHLYITEMS|085";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_086 = "TMG_MONTHLYITEMS|086";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_087 = "TMG_MONTHLYITEMS|087";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_088 = "TMG_MONTHLYITEMS|088";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_089 = "TMG_MONTHLYITEMS|089";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_090 = "TMG_MONTHLYITEMS|090";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_091 = "TMG_MONTHLYITEMS|091";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_092 = "TMG_MONTHLYITEMS|092";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_093 = "TMG_MONTHLYITEMS|093";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_094 = "TMG_MONTHLYITEMS|094";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_095 = "TMG_MONTHLYITEMS|095";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_096 = "TMG_MONTHLYITEMS|096";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_097 = "TMG_MONTHLYITEMS|097";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_098 = "TMG_MONTHLYITEMS|098";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_099 = "TMG_MONTHLYITEMS|099";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>(未設定)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MONTHLYITEMS_100 = "TMG_MONTHLYITEMS|100";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>非勤務区分</b>"を表すグループIDです
     */
    public static final String Cs_MGD_NOTWORK = "TMG_NOTWORK";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(半休・前半)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_011 = "TMG_NOTWORK|011";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(半休・後半)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_012 = "TMG_NOTWORK|012";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_015 = "TMG_NOTWORK|015";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_025 = "TMG_NOTWORK|025";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(時間・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_035 = "TMG_NOTWORK|035";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(時間・有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_037 = "TMG_NOTWORK|037";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(時間・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_045 = "TMG_NOTWORK|045";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(時間・有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_047 = "TMG_NOTWORK|047";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児部分休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_055 = "TMG_NOTWORK|055";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児早退休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_056 = "TMG_NOTWORK|056";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護部分休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_065 = "TMG_NOTWORK|065";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_905 = "TMG_NOTWORK|905";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>離席(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NOTWORK_910 = "TMG_NOTWORK|910";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>申請ステータス</b>"を表すグループIDです
     */
    public static final String Cs_MGD_NTFSTATUS = "TMG_NTFSTATUS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>取下</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFSTATUS_0 = "TMG_NTFSTATUS|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>承認待</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFSTATUS_2 = "TMG_NTFSTATUS|2";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>却下</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFSTATUS_3 = "TMG_NTFSTATUS|3";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>承認済</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFSTATUS_5 = "TMG_NTFSTATUS|5";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>申請種類</b>"を表すグループIDです
     */
    public static final String Cs_MGD_NTFTYPE = "TMG_NTFTYPE";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(終日)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_010 = "TMG_NTFTYPE|010";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(半休・前半)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_011 = "TMG_NTFTYPE|011";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(半休・後半)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_012 = "TMG_NTFTYPE|012";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_015 = "TMG_NTFTYPE|015";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇(終日)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_020 = "TMG_NTFTYPE|020";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇(時間)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_025 = "TMG_NTFTYPE|025";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(終日・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_030 = "TMG_NTFTYPE|030";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(終日・有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_032 = "TMG_NTFTYPE|032";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(時間・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_035 = "TMG_NTFTYPE|035";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(時間・有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_037 = "TMG_NTFTYPE|037";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_040 = "TMG_NTFTYPE|040";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_042 = "TMG_NTFTYPE|042";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(時間・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_045 = "TMG_NTFTYPE|045";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(時間・有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_047 = "TMG_NTFTYPE|047";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_050 = "TMG_NTFTYPE|050";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児部分休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_055 = "TMG_NTFTYPE|055";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児早退休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_056 = "TMG_NTFTYPE|056";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_060 = "TMG_NTFTYPE|060";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護部分休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_065 = "TMG_NTFTYPE|065";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休憩45分選択</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_070 = "TMG_NTFTYPE|070";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>自己啓発部分休業(時間・無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_080 = "TMG_NTFTYPE|080";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>代休暇</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_NTFTYPE_090 = "TMG_NTFTYPE|451";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>OnOffフラグ</b>"を表すグループIDです
     */
    public static final String Cs_MGD_ONOFF = "TMG_ONOFF";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>Off(非該当)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ONOFF_0 = "TMG_ONOFF|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>On(該当)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ONOFF_1 = "TMG_ONOFF|1";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>年次休暇付与日数</b>"を表すグループIDです
     */
    public static final String Cs_MGD_PAID_HOLIDAY = "TMG_PAID_HOLIDAY";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>1月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_0 = "TMG_PAID_HOLIDAY|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>1月を超え2月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_1 = "TMG_PAID_HOLIDAY|1";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>10月を超え11月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_10 = "TMG_PAID_HOLIDAY|10";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>11月を超え1年未満の期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_11 = "TMG_PAID_HOLIDAY|11";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>2月を超え3月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_2 = "TMG_PAID_HOLIDAY|2";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>3月を超え4月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_3 = "TMG_PAID_HOLIDAY|3";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>4月を超え5月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_4 = "TMG_PAID_HOLIDAY|4";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>5月を超え6月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_5 = "TMG_PAID_HOLIDAY|5";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>6月を超え7月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_6 = "TMG_PAID_HOLIDAY|6";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>7月を超え8月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_7 = "TMG_PAID_HOLIDAY|7";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>8月を超え9月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_8 = "TMG_PAID_HOLIDAY|8";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>9月を超え10月に達するまでの期間</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PAID_HOLIDAY_9 = "TMG_PAID_HOLIDAY|9";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>勤務パターン</b>"を表すグループIDです
     */
    public static final String Cs_MGD_PATTERN = "TMG_PATTERN";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>全学標準</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PATTERN_00 = "TMG_PATTERN|00";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>予定実績区分</b>"を表すグループIDです
     */
    public static final String Cs_MGD_PLANRESULT = "TMG_PLANRESULT";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>予定</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PLANRESULT_0 = "TMG_PLANRESULT|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>実績</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_PLANRESULT_1 = "TMG_PLANRESULT|1";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>月別システム定義項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS = "TMG_SYSMONTHLYITEMS";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇残日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_01 = "TMG_SYSMONTHLYITEMS|01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇残時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_02 = "TMG_SYSMONTHLYITEMS|02";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇取得日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_03 = "TMG_SYSMONTHLYITEMS|03";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇取得時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_04 = "TMG_SYSMONTHLYITEMS|04";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇取得日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_05 = "TMG_SYSMONTHLYITEMS|05";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇取得時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_06 = "TMG_SYSMONTHLYITEMS|06";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇取得日数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_07 = "TMG_SYSMONTHLYITEMS|07";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇取得時間数</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_SYSMONTHLYITEMS_08 = "TMG_SYSMONTHLYITEMS|08";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>就業区分</b>"を表すグループIDです
     */
    public static final String Cs_MGD_WORK = "TMG_WORK";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>出勤</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_000 = "TMG_WORK|000";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年次休暇</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_010 = "TMG_WORK|010";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_020 = "TMG_WORK|020";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>病気休暇(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_022 = "TMG_WORK|022";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_030 = "TMG_WORK|030";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>特別休暇(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_032 = "TMG_WORK|032";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_040 = "TMG_WORK|040";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>職専免(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_042 = "TMG_WORK|042";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>育児休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_050 = "TMG_WORK|050";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>介護休業</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_060 = "TMG_WORK|060";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>年休以外の休暇(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_102 = "TMG_WORK|102";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>就業禁止(有給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_200 = "TMG_WORK|200";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>就業禁止(無給)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_412 = "TMG_WORK|412";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務を要しない日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_480 = "TMG_WORK|480";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>週休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_500 = "TMG_WORK|500";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、非常勤の休日 を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_HIJOKINKYUJITU = "TMG_WORK|505"; //#406 2008/10/01

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休日出勤</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_510 = "TMG_WORK|510";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>代休暇</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_520 = "TMG_WORK|520";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_560 = "TMG_WORK|560";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>*週休日</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_570 = "TMG_WORK|570";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>休職</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_800 = "TMG_WORK|800";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>欠勤</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_900 = "TMG_WORK|900";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>??</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORK_990 = "TMG_WORK|990";
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>勤怠種別</b>"を表すグループIDです
     */
    public static final String Cs_MGD_WORKERTYPE = "TMG_WORKERTYPE";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>??</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORKERTYPE_00 = "TMG_WORKERTYPE|00";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>定員内</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORKERTYPE_10 = "TMG_WORKERTYPE|10";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非常勤(日々雇用)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORKERTYPE_20 = "TMG_WORKERTYPE|20";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>非常勤(時間雇用)</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_WORKERTYPE_30 = "TMG_WORKERTYPE|30";
    // 2007/08/20 tanaka #262 休暇・休業申請の仕様変更(4次分)
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>職種/申請リレーション</b>"を表すグループIDです
     */
    public static final String Cs_MGD_NTF_RELATION = "TMG_NTF_RELATION";

    // 2007/08/29 tanaka #277 月次集計データ作成(新規開発)
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>部局管理者</b>"を表すグループIDです
     */
    public static final String Cs_MGD_TMG_SECTION_ADMIN = "TMG_SECTION_ADMIN";

    // 2008.02.05 J.Okamoto #340 休憩時間未入力対応
    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>最小休憩時間定義</b>"を表すグループIDです
     */
    public static final String Cs_MGD_MINIMUM_REST = "TMG_MINIMUM_REST";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>勤務時間が6時間を超えた時、休憩時間が60分以上</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MINIMUM_REST_01 = "TMG_MINIMUM_REST|01";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>管理対象外</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MANAGEFLG_0 = "TMG_MANAGEFLG|0";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>管理対象</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_MANAGEFLG_1 = "TMG_MANAGEFLG|1";

    public static final String Cs_MGD_ITEMS_DayNight = "TMG_ITEMS|DayNight";

    public static final String Cs_MGD_ITEMS_DutyHours = "TMG_ITEMS|DutyHours";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>超過勤務</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ITEMS_Overhours = "TMG_ITEMS|Overhours";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>【予定】休憩</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ITEMS_PlanRest = "TMG_ITEMS|PlanRest";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>【実績】休憩</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ITEMS_ResultRest = "TMG_ITEMS|ResultRest";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"<b>中断</b>"を表すマスターコードです
     */
    public static final String Cs_MGD_ITEMS_Interruption = "TMG_ITEMS|Interruption";

    public static final String Cs_MGD_ITEMS_OverhoursReason = "TMG_ITEMS|OverhoursReason";

    public static final String Cs_MGD_MANAGER4SALARY = "TMG_MANAGER4SALARY|1";

    /** 遅延理由を表示するか */
    public static final String Cs_DELAY_RESON_ONOFF_1 = "1";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"未処理"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_010 = "TMG_BULKNTFSTATUS|010";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"反映開始"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_110 = "TMG_BULKNTFSTATUS|110";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"反映中"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_120 = "TMG_BULKNTFSTATUS|120";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"反映済"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_130 = "TMG_BULKNTFSTATUS|130";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"取消開始"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_210 = "TMG_BULKNTFSTATUS|210";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"取消中"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_220 = "TMG_BULKNTFSTATUS|220";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"取消済"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_230 = "TMG_BULKNTFSTATUS|230";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"エラー"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFSTATUS_910 = "TMG_BULKNTFSTATUS|910";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>休暇・休業一括登録区分</b>"を表すグループIDです
     */
    public static final String Cs_MG_TMG_BULKNTFTYPE = "TMG_BULKNTFTYPE";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"夏季休暇一斉付与"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_BULKNTFTYPE_336 = "TMG_BULKNTFTYPE|336";

    /**
     * 名称マスタ(MAST_GENERIC)において、"休暇・休業一括登録サービス提供時間"を表すグループIDです
     */
    public static final String Cs_MG_TMG_USETIME4BULKNTF = "TMG_USETIME4BULKNTF";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"休暇・休業一括登録サービス提供時間"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_USETIME4BULKNTF_1 = "TMG_USETIME4BULKNTF|1";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"月次集計ステータス：勤怠締め"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_STATUS_MONTHLYFIX = "TMG_STATUS|MONTHLY_FIX";
    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"月次集計ステータス：給与締め"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_STATUS_SALARYFIX  = "TMG_STATUS|SALARY_FIX";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"１日の法定労働時間"を表すマスタコードです
     */
    public static final String Cs_MGD_TMG_LEGAL_HOURSDAY = "TMG_LEGAL_HOURS|DAY";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>月次情報表示項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DISPMONTHLYITEMS  = "TMG_DISPMONTHLYITEMS";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>日次情報表示項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DISPDAILYITEMS   = "TMG_DISPDAILYITEMS";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>出勤簿月単位集計情報表示項目</b>"を表すグループIDです
     */
    public static final String Cs_MGD_DISPATTENDANCEITEMS  = "TMG_ATTENDANCEITEMS";

    /**
     * 名称マスタ詳細(MAST_GENERIC)において、"決済レベル"を表すマスタコードです
     */
    public static final String Cs_MG_TMG_APPROVAL_LEVEL = "TMG_APPROVAL_LEVEL";

    /**
     * 名称マスタ詳細(MAST_GENERIC)において、"申請ごとの決裁レベル"を表すマスタコードです
     */
    public static final String Cs_MG_TMG_NTFAPPROVE_LEVEL = "TMG_NTFAPPROVE_LEVEL";

    /**
     * 名称マスタ詳細(MAST_GENERIC)において、"申請アクション"を表すマスタコードです
     */
    public static final String Cs_MG_TMG_NTFACTION = "TMG_NTFACTION";

    /**
     * 名称マスタ(MAST_GENERIC)において、"勤務開始日"を表すマスタコードです
     */
    public static final String Cs_MG_TMG_DATEOFEMPLOYMENT = "TMG_DATEOFEMPLOYMENT";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・申請"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_1   = "TMG_NTFACTION|1";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・再申請"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_2   = "TMG_NTFACTION|2";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・代理申請"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_3   = "TMG_NTFACTION|3";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・取下"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_4   = "TMG_NTFACTION|4";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・差戻"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_5   = "TMG_NTFACTION|5";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・承認"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_6   = "TMG_NTFACTION|6";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"申請アクション・取消"を表すマスタコードです
     */
    public static final String Cs_MGD_NTFACTION_7   = "TMG_NTFACTION|7";

    /**
     * 名称マスタ(MAST_GENERIC)において、"月次集計データ作成/CSVデータ取得表関数"を表すグループID
     */
    public static final String Cs_MG_TMG_MOTABLEFUNCTION = "TMG_MOTABLEFUNCTION";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"月次集計データ作成/月例 ダウンロード"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_MOTABLEFUNCTION_1 = "TMG_MOTABLEFUNCTION|01";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"月次集計データ作成/遡及 ダウンロード"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_MOTABLEFUNCTION_2 = "TMG_MOTABLEFUNCTION|02";

    /**
     * 名称マスタ(MAST_GENERIC)において、"プロパティファイル提供メッセージ"を表すグループID
     */
    public static final String Cs_MG_TMG_PROPMSG = "TMG_PROPMSG";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"打刻対象外画面表示メッセージ"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_PROPMSG_NOT_TARGET = "TMG_PROPMSG|TimePunch_0";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"打刻区分/出勤"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_TPTYPE_01 = "TMG_TPTYPE|01";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"打刻区分/退勤"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_TPTYPE_02 = "TMG_TPTYPE|02";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>超勤申請表示組織設定</b>"を表すグループIDです
     */
    public static final String Cs_MGD_TMG_OT_REFERABLE_SEC = "TMG_OT_REFERABLE_SEC";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>帳票PDFダウンロードマスタ</b>"を表すグループIDです
     */
    public static final String Cs_MGD_LEDGER_PDF_DOWNLOAD = "TMG_LEDGER_PDF_DWLD";

    /**
     * 名称マスタ(MAST_GENERIC)において、"<b>帳票CSVダウンロードマスタ</b>"を表すグループIDです
     */
    public static final String Cs_MGD_LEDGER_CSV_DOWNLOAD = "TMG_LEDGER_CSV_DWLD";

    /**
     * 名称マスタ(MAST_GENERIC)において、"HR連携除外条件"を表すグループID
     */
    public static final String Cs_MG_TMG_EXCLUDECOND4THW = "TMG_EXCLUDECOND4THW";

    /**
     * 名称マスタ(MAST_GENERIC)において、"HR連携除外条件(シミュレーション用)"を表すグループID
     */
    public static final String Cs_MG_TMG_EXCLUDE4THW_SIM = "TMG_EXCLUDE4THW_SIM";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"シミュレーション状態/未実行"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_WTSIMSTATUS_010 = "TMG_WTSIMSTATUS|010";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"シミュレーション状態/開始待"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_WTSIMSTATUS_020 = "TMG_WTSIMSTATUS|020";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"シミュレーション状態/実行中"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_WTSIMSTATUS_120 = "TMG_WTSIMSTATUS|120";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"各コメント欄の最大値/本人コメント欄"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_MAX_LENGTH_CHECK_TMG_MAX_LENGTH_CHECK = "TMG_MAX_LENGTH_CHECK|TDA_COWNCOMENT_R";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"IF連携元消失データの処理区分「未処理」"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_DISAPPEARSTATUS_0 = "TMG_DISAPPEARSTATUS|0";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"IF連携元消失データの処理区分「削除済」"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_DISAPPEARSTATUS_1 = "TMG_DISAPPEARSTATUS|1";

    /**
     * 名称マスタ詳細(MAST_GENERIC_DETAIL)において、"IF連携元消失データの処理区分「無視」"を表すマスタコード
     */
    public static final String Cs_MGD_TMG_DISAPPEARSTATUS_9 = "TMG_DISAPPEARSTATUS|9";

/*
	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	                          プロパティ名一覧

	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
*/
    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>ユーザーマニュアルの表示/非表示</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_SHOWHELP = "TMG_SHOW_HELP";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>年次休暇管理/週勤務パターンおよび個人属性設定/週平均勤務時間数の編集可否</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_WORKINGDAYS = "TMG_EDIT_WORKINGDAYS";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>個人属性設定/管理対象者（対象者一括編集画面）の編集可否</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_EDITMANAGEFLG = "TMG_EDIT_MANAGEFLG";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>システム日付以前の予定を変更した際に実績を予定で上書きするか</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_RESULT = "TMG_CLEAR_RESULT";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>権限セッテイメンバー割付画面ソート順のデフォルトソート順/所属グループ順</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_MEMBER_SORT = "TMG_EVALSET_MEMBER_SORT";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>汎用ヘッダの役職表示/デフォルト表示</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_HEADEROUTPUTPOST = "TMG_HEADER_OUTPUT_POST";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>休暇休業一括登録の組織複数選択/単数選択制御</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_BULKNTF_SECLIST_MODE = "TMG_BULKNTF_SECLIST_MODE";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>帳票PDFダウンロード時DB接続設定（JDBCドライバ）</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_JASPERREPORTDB_DRIVER = "JasperReportDB_Driver";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>帳票PDFダウンロード時DB接続設定（JDBC URL）</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_JASPERREPORTDB_URL = "JasperReportDB_Url";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>帳票PDFダウンロード時DB接続設定（DB接続ユーザ）</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_JASPERREPORTDB_USER = "JasperReportDB_User";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>帳票PDFダウンロード時DB接続設定（DB接続パスワード）</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_JASPERREPORTDB_PASSWORD = "JasperReportDB_Password";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超過勤務前（勤務予定の終業時刻直後）における離席時間の入力有無チェック</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_RISEKI_CHK = "TMG_RISEKI_CHK";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超過勤務命令コンテンツ・氏名リンク使用設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_ENABLE_LINK_OVERTIME2RESULTS = "TMG_ENABLE_LINK_OVERTIME2RESULTS";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超過勤務命令における設定上限値</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_OVERTIME_MAX_SIZE = "TMG_OVERTIME_MAX_SIZE";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超過勤務命令における登録可能休憩時間数設定上限値</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_REST_MAX_SIZE = "TMG_REST_MAX_SIZE";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超過勤務命令画面における、休憩時間編集機能の使用可否設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_EDITABLE_REST_4OVERTIMEINSTRUCT = "TMG_EDITABLE_REST4OVERTIMEINSTRUCT";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>休暇休業一括登録コンテンツ・反映エラーＣＳＶダウンロード機能の使用可否設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_ENABLE_BULKNTF_ERRLIST_DOWNLOAD = "TMG_ENABLE_BULKNTF_ERRLIST_DOWNLOAD";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>システム利用開始年月</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_SYSTEM_INTRODUCTION_DATE = "TMG_SYSTEM_INTRODUCTION_DATE";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>承認状況一覧の一括承認画面にて、「「承認待」の超勤命令を有する未終業打刻者をチェックする機能」の使用可否設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_CHECK_NO_CLOSETP_WITH_OVERTIMEINST4PERMSTATLIST = "TMG_CHECK_NO_CLOSETP_WITH_OVERTIMEINST4PERMSTATLIST";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>休暇休業承認一覧にて、年ごと表示・期間指定表示の制御設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_NTF_TERM_CONDITION = "TMG_NTF_TERM_CONDITION";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>承認/管理サイトの各画面に表示する職員情報ヘッダについて、可変ヘッダを使用するかどうかの使用可否設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROPNAME_USE_VARIABLE_HEADER = "TMG_USE_VARIABLE_HEADER";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>日次実績（超過勤務申請含）の承認可否チェック機能の使用可否設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROP_NAME_TMG_DISABLE_NO_OVERHOURPERM_APPROVAL = "TMG_DISABLE_NO_OVERHOURPERM_APPROVAL";

    /**
     * システムプロパティ(CONF_SYSCONTROL)において、"<b>超勤申請の事前事後登録情報を表示利用するかどうかの設定</b>"を表すプロパティ名です
     */
    public static final String Cs_CYC_PROP_NAME_TMG_SHOW_OVERTIMENOTIFICATION = "TMG_SHOW_OVERTIMENOTIFICATION";

	/*
	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	                          リソースバンドル名一覧

	/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
*/
    /**
     * リソースバンドル TERM のキー名です。
     */
    public static final String Cs_UTIL_PROPERTY_KYE_TERM = "TERM";

    /**
     * リソースバンドル HEADER_HATUREITO のキー名です。
     */
    public static final String Cs_UTIL_PROPERTY_KYE_HEADER_HATUREITO = "HEADER_HATUREITO";

    /**
     * リソースバンドル TERM_SEPARATOR のキー名です。
     */
    public static final String Cs_UTIL_PROPERTY_KYE_TERM_SEPARATOR = "TERM_SEPARATOR";


    /** 最大日付 */
    public static final Date minDate = DateUtil.parse("1900-01-01");


    /** 最小日付 */
    public static final Date maxDate = DateUtil.parse("2222-12-31");

    /** 日付形式1 */
    public static final String Cs_FORMAT_DATE_TYPE1 = "yyyy/MM/dd";

    /** 今月 */
    public static final int Cs_PARAM_THIS_MONTH = -1;

    /** 日付形式2 */
    private static final String Cs_FORMAT_SLASH = "/";

    /**
     * システム日付を返す
     * @return String システム日付
     */
    public static String getSysdate(){
        SimpleDateFormat sdf = new SimpleDateFormat(Cs_FORMAT_DATE_TYPE1);
        return sdf.format(new Date());
    }

    /**
     * 引数で指定された値分だけ基準日の月を移動します。
     * @param date 基準日 (「yyyy/mm/dd」形式の文字列)
     * @param mvValue 移動したい値()
     * @return String 移動した年月日 - ※日は月初(1日)になります。
     */
    public static String getFirstDayOfMonth(String date, int mvValue) {
        Date objDate = null;

        try {
            String ymd[] = StringUtils.split(date, Cs_FORMAT_SLASH);
            Calendar calendar = Calendar.getInstance();

            // カレンダーに年月日を設定
            calendar.set(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]) + mvValue, 1);

            objDate = calendar.getTime();

        } catch (Exception e) {
            e.printStackTrace(); // 念の為
        }
        return new SimpleDateFormat(Cs_FORMAT_DATE_TYPE1).format(objDate);
    }


    /****************************************************************************
     * リソースバンドルファイル(TmgUtil_ja_JP.Properties)から値を取得します。
     *
     * @param	sKey	キー文字列
     * @return String	値
     */
    public static String getPropertyValue(String sKey){
        return SysUtil.getpropertyvalue("ja", sKey, "jp.smartcompany.job.modules.tmg.util.TmgUtil");
    }

    public static String Mintue2HHmi(int date){
        String HHmi;
        HHmi= (date/60) + "時" + (date%60) + "分";
        return HHmi;
    }

    /**
     * フレックスかどうかを判定
     *
     * @param bean PsDBBean
     * @param custId 顧客コード
     * @param compCode 法人コード
     * @param employeeCode 社員番号
     * @param date 日付
     *
     * @return true:フレックス対象者
     */
    @SuppressWarnings("unchecked")
    public static Boolean isFlex(PsDBBean bean
            , String custId
            , String compCode
            , String employeeCode
            , String date) {

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT ");
        sb.append(buildSQLForSelectFlex(bean.escDBString(custId)
                , bean.escDBString(compCode)
                , bean.escDBString(employeeCode)
                , date));
        sb.append("  FROM DUAL");

        Vector vecQuery = new Vector();
        vecQuery.add(sb.toString());

        PsResult psResult = null;
        String flexWork = null;

        try {
            psResult = bean.getValuesforMultiquery(vecQuery, "TmtUtil");
            flexWork = valueAtColumnRowFromPsResult(bean
                    , psResult
                    , 0
                    , 0
                    , 0);
        } catch(Exception e) {
            psResult = null;
        }

        // 裁量労働対象者判定
        String flexWorkTrue = "1";

        if (flexWorkTrue.equals(flexWork)) {
            return true;
        }
        return false;
    }

    /**
     * フレックス制か判断するSELECTクエリを返します
     *
     * @param custId 顧客コード
     * @param compCode 法人コード
     * @param employeeCode 社員番号
     * @param date 日付
     *
     * @return SELECT文
     */
    private static String buildSQLForSelectFlex(String custId
            , String compCode
            , String employeeCode
            , String date) {
        StringBuffer sql = new StringBuffer();

        sql.append(" TMG_F_IS_FLEX("+ custId);
        sql.append("  , "+ compCode);
        sql.append("  , "+ employeeCode);
        sql.append("  , "+ date + ") ");

        return sql.toString();
    }

    /**
     * 月間勤務時間に対して、足りない時間数を取得
     *
     * @param bean PsDBBean
     * @param custId 顧客コード
     * @param compCode 法人コード
     * @param employeeCode 社員番号
     * @param date 日付
     *
     * @return int:時間数
     */
    public static String getNeedTime4Flex(PsDBBean bean
            , String custId
            , String compCode
            , String employeeCode
            , String date) {

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT ");
        sb.append(buildSQLForSelectNeedTime4Flex(bean.escDBString(custId)
                , bean.escDBString(compCode)
                , bean.escDBString(employeeCode)
                , date));
        sb.append("  FROM DUAL");

        Vector vecQuery = new Vector();
        vecQuery.add(sb.toString());

        PsResult psResult = null;
        String needTime4Flex = null;

        try {
            psResult = bean.getValuesforMultiquery(vecQuery, "TmtUtil");
            needTime4Flex = valueAtColumnRowFromPsResult(bean
                    , psResult
                    , 0
                    , 0
                    , 0);
        } catch(Exception e) {
            psResult = null;
        }

        return needTime4Flex;
    }

    /**
     * 検索結果からデータを取り出す
     * @param bean
     * @param psResult
     * @param qry
     * @param col
     * @param row
     * @return
     */
    public static String valueAtColumnRowFromPsResult(PsDBBean bean, PsResult psResult, int qry, int col, int row){
        String retval = "";
        Vector vQrys;
        Vector vRows;
        Vector vCols;
        if(bean == null || psResult == null || qry < 0 || col < 0 || row < 0){
            retval = "";
            return retval;
        }

        vQrys = psResult.getResult();

        if(vQrys.size() <= qry){
            retval = "";
            return retval;
        }
        try{
            vRows = (Vector)psResult.getResult().get(qry);
        }catch (Exception e) {
            e.printStackTrace();
            retval = "";
            return retval;
        }

        if(vRows.size() <= row){
            retval = "";
            return retval;
        }
        try{
            vCols = (Vector)vRows.get(row);
        }catch (Exception e) {
            e.printStackTrace();
            retval = "";
            return retval;
        }

        if(vCols.size() <= col){
            retval = "";
            return retval;
        }
        try{
            retval = String.valueOf(vCols.get(col));
        }catch (Exception e) {
            e.printStackTrace();
            retval = "";
            return retval;
        }
        if(retval == null){
            retval = "";
        }
        return retval;
    }

    /**
     * 月間勤務時間に対して、足りない時間数を取得
     *
     * @param custId 顧客コード
     * @param compCode 法人コード
     * @param employeeCode 社員番号
     * @param date 日付
     *
     * @return SELECT文
     */
    private static String buildSQLForSelectNeedTime4Flex(String custId
            , String compCode
            , String employeeCode
            , String date) {
        StringBuffer sql = new StringBuffer();

        sql.append(" TMG_F_CONV_MIN2HHMI(TMG_4CALC_GET_NEED_TIME("+ employeeCode);
        sql.append("  , "+ date);
        sql.append("  , "+ custId);
        sql.append("  , "+ compCode + ")) ");

        return sql.toString();
    }

}
