package jp.smartcompany.job.modules.tmg.tmgledger;

public class TmgLedgerConst {
    // ==========================================
    // 定数
    // ==========================================
    /** 出力対象タイプ：個人系 */
    public static final String TARGETTYPE_EMP       = "EMP";
    /** 出力対象タイプ：組織系 */
    public static final String TARGETTYPE_ORG       = "ORG";
    /** 出力対象日付タイプ：年指定 */
    public static final String TARGETDATETYPE_YEAR  = "YEAR";
    /** 出力対象日付タイプ：年月指定 */
    public static final String TARGETDATETYPE_MONTH = "MONTH";
    /** 出力対象日付タイプ：期間指定 */
    public static final String TARGETDATETYPE_TERM  = "TERM";
    /** 帳票出力 レポートファイル配置フォルダパス */
    public static final String DIRPATH_REPORTFILES  = "jasperreport\\";

    // ==========================================
    // リクエストパラメータ名
    // ==========================================
    /** リクエスト：アクション */
    public static final String REQ_ACTION              = "txtAction";
    /** リクエスト：ページ名 */
    public static final String REQ_PAGENAME            = "PageName";
    /** リクエスト：組織ツリー基準日 */
    public static final String REQ_RECORD_DATA         = "RecordDateCalendar";
    /** リクエスト：選択帳票種別ID */
    public static final String REQ_TARGET_LEDGER_SHEET = "txtLedgerSheetId";
    /** リクエスト：選択対象職員名 */
    public static final String REQ_TARGET_EMP          = "txtEmp";
    /** リクエスト：選択対象職員ID */
    public static final String REQ_TARGET_EMPID        = "hidEmpId";
    /** リクエスト：選択対象部署名 */
    public static final String REQ_TARGET_ORG          = "txtOrg";
    /** リクエスト：選択対象部署ID */
    public static final String REQ_TARGET_ORGID        = "hidOrgId";
    /** リクエスト：選択対象年 */
    public static final String REQ_TARGET_YYYY         = "txtYYYY";
    /** リクエスト：選択対象年月 */
    public static final String REQ_TARGET_YYYYMM       = "txtYYYYMM";
    /** リクエスト：選択対象期間・開始 */
    public static final String REQ_TARGET_TERM_FROM    = "txtTermFrom";
    /** リクエスト：選択対象期間・終了 */
    public static final String REQ_TARGET_TERM_TO      = "txtTermTo";
    /** リクエスト：表示開始月：出勤簿（年） */
    public static final String REQ_ATDBOOK_TERM_FROM   = "txtAtdBookTermFrom";
    /** リクエスト：法定内超勤を含める */
    public static final String REQ_INCLUDE_TO100_FLG   = "txtIncludeOt100Flg";

    // ==========================================
    // PDFファイルダウンロード　パラメータ名
    // ==========================================
    /** PDFファイルパラメータ：サブレポートディレクトリ */
    public static final String SUBREPORT_DIR           = "SUBREPORT_DIR";
    /** PDFファイルパラメータ：顧客ID */
    public static final String PARAM_CUST_ID           = "PARAM_CUST_ID";
    /** PDFファイルパラメータ：法人ID */
    public static final String PARAM_COMP_ID           = "PARAM_COMP_ID";
    /** PDFファイルパラメータ：職員番号 */
    public static final String PARAM_EMP_ID            = "PARAM_EMP_ID";
    /** PDFファイルパラメータ：組織コード */
    public static final String PARAM_ORG_ID            = "PARAM_ORG_ID";
    /** PDFファイルパラメータ：対象年 */
    public static final String PARAM_TARGET_YYYY       = "PARAM_TARGET_YYYY";
    /** PDFファイルパラメータ：対象年月 */
    public static final String PARAM_TARGET_YYYYMM     = "PARAM_TARGET_YYYYMM";
    /** PDFファイルパラメータ：対象期間・開始日 */
    public static final String PARAM_TARGET_TERM_FROM  = "PARAM_TARGET_TERM_FROM";
    /** PDFファイルパラメータ：対象期間・終了日 */
    public static final String PARAM_TARGET_TERM_TO    = "PARAM_TARGET_TERM_TO";
    /** PDFファイルパラメータ：出勤簿・年・表示開始月 */
    public static final String PARAM_ATDBOOK_TERM_FROM = "PARAM_ATDBOOK_TERM_FROM";
    /** PDFファイルパラメータ：出勤簿・年・表示終了月 */
    public static final String PARAM_ATDBOOK_TERM_TO   = "PARAM_ATDBOOK_TERM_TO";
    /** PDFファイルパラメータ：超過勤務実施状況リスト・法定内超勤を含める */
    public static final String PARAM_OT100FLG          = "PARAM_OT100FLG";
    /** PDFファイルパラメータ：言語区分 */
    public static final String PARAM_LANG              = "PARAM_LANG";

    // ==========================
    // 帳票画面名
    // ==========================
    /** 遷移先名：帳票出力選択画面-表示 */
    public static final String SCREEN_LEDGER_SELECT_DISP = "LedgerSelectDisp";
    /** 遷移先名：対象職員選択ダイアログ画面-表示*/
    public static final String SCREEN_SEARCH_EMP_DISP    = "SearchEmpDisp";
    /** 遷移先名：対象部署選択ダイアログ画面-表示*/
    public static final String SCREEN_SEARCH_ORG_DISP    = "SearchOrgDisp";

    // ================================
    // アクション名
    // ================================
    /** アクション：帳票出力選択画面-表示 */
    public static final String ACT_LEDGER_SELECT_DISP  = "ACT_DISP";
    /** アクション：対象職員ダイアログ-表示 */
    public static final String ACT_SEARCH_EMP_DIALOG   = "ACT_SEARCH_EMP_DIALOG";
    /** アクション：対象部署ダイアログ-表示 */
    public static final String ACT_SEARCH_ORG_DIALOG   = "ACT_SEARCH_ORG_DIALOG";
    /** アクション：ＰＤＦダウンロード */
    public static final String ACT_LEDGER_PDF_DOWNLOAD = "ACT_LEDGER_PDF_DOWNLOAD";
    /** アクション：ＣＳＶダウンロード */
    public static final String ACT_LEDGER_CSV_DOWNLOAD = "ACT_LEDGER_CSV_DOWNLOAD";

    // ============================
    // クエリ番号
    // =============================
    /** クエリ番号：帳票出力選択画面 表示時クエリ番号０ */
    public static final int QUERY_IDX_DISP_SELECT_LEDGER     = 0;
    /** クエリ番号：帳票出力選択画面 表示時クエリ番号１ */
    public static final int QUERY_IDX_DISP_SELECT_TARGETDATE_YEAR = 1;
    /** クエリ番号：帳票出力選択画面 表示時クエリ番号１ */
    public static final int QUERY_IDX_DISP_SELECT_TARGETDATE_MONTH = 2;

    /** クエリ番号：部署選択ダイアログ画面 表示時クエリ番号０ */
    public static final int QUERY_IDX_SEARCHORG_REFERABLE_SEC = 0;

    // ==========================================
    // カラム番号
    // ==========================================
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）の識別コード */
    public static final int COL_IND_SELECT_LEDGER_ID             = 0;
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）の名称 */
    public static final int COL_IND_SELECT_LEDGER_NEME           = 1;
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）の出力対象タイプ */
    public static final int COL_IND_SELECT_LEDGER_TARGETTYPE     = 2;
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）の出力対象日付タイプ */
    public static final int COL_IND_SELECT_LEDGER_TARGETDATETYPE = 3;
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）のPDFダウンロード表示フラグ */
    public static final int COL_IND_SELECT_LEDGER_DISP_PDF_FLG   = 4;
    /** カラム番号：帳票出力選択画面 表示時クエリ（帳票種別検索）のPDFダウンロード表示フラグ */
    public static final int COL_IND_SELECT_LEDGER_DISP_CSV_FLG   = 5;

    /** カラム番号：帳票出力選択画面 表示時クエリ（対象年リスト検索）の値 */
    public static final int COL_IND_SELECT_TARGETDATE_YEAR_VAL    = 0;
    /** カラム番号：帳票出力選択画面 表示時クエリ（対象年リスト検索）の名称 */
    public static final int COL_IND_SELECT_TARGETDATE_YEAR_DISP   = 1;

    /** カラム番号：帳票出力選択画面 表示時クエリ（対象年月リスト検索）の値 */
    public static final int COL_IND_SELECT_TARGETDATE_MONTH_VAL   = 0;
    /** カラム番号：帳票出力選択画面 表示時クエリ（対象年月リスト検索）の名称 */
    public static final int COL_IND_SELECT_TARGETDATE_MONTH_DISP  = 1;

    /** クエリ番号：部署選択ダイアログ画面 表示時クエリ番号０の部署コード */
    public static final int COL_IDX_SEARCHORG_REFERABLE_SEC_SECID = 0;

}
