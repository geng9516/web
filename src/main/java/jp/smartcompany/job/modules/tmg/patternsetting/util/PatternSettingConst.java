package jp.smartcompany.job.modules.tmg.patternsetting.util;

import java.text.DecimalFormat;

/**
 * @author 陳毅力
 * @description 勤務パターン設定
 * @objectSource ps.c01.tmg.PatternSetting.PatternSettingConst
 * @date 2020/06/16
 **/
public class PatternSettingConst {

    public static final String modifierProgramId = "PatternSetting_ACTION_EDIT_SAVE";

    /**
     * プログラム名
     */
    public static final String PROGRAM_ID = "PatternSetting";

    /**
     * リクエスト：組織ツリー基準日
     */
    public static final String REQUEST_KEY_TREE_YYYYMMDD = "RecordDateCalendar";

    /**
     * リクエスト：組織ツリー再表示ボタンクリックフラグ
     */
    public static final String REQUEST_KEY_TREE_REFRESH_FLG = "txtTmgReferListTreeViewRefreshFlg";

    /**
     * リクエスト：Actionコード
     */
    public static final String REQUEST_KEY_ACTION = "txtACTION";

    /**
     * リクエスト：PageName
     */
    public static final String REQUEST_KEY_PAGE_NAME = "PageName";

    /**
     * リクエスト：基準日
     */
    public static final String REQUEST_KEY_YYYYMMDD = "txtYYYYMMDD";

    /**
     * リクエスト：勤務パターンコード
     */
    public static final String REQUEST_KEY_PATTERNID = "txtPATTERNID";

    /**
     * リクエスト：勤務パターン名称
     */
    public static final String REQUEST_KEY_PATTERNNAME = "txtPATTERNNAME";

    /**
     * リクエスト：デフォルトフラグ
     */
    public static final String REQUEST_KEY_DEFAULTFLG = "txtDEFAULTFLG";

    /**
     * リクエスト：勤務開始時刻
     */
    public static final String REQUEST_KEY_OPEN = "txtOPEN";

    /**
     * リクエスト：勤務終了時刻
     */
    public static final String REQUEST_KEY_CLOSE = "txtCLOSE";

    /**
     * リクエスト：休憩開始時刻
     */
    public static final String REQUEST_KEY_RESTOPEN = "txtRESTOPEN";

    /**
     * リクエスト：休憩終了時刻
     */
    public static final String REQUEST_KEY_RESTCLOSE = "txtRESTCLOSE";

    /**
     * リクエスト：職員番号
     */
    public static final String REQUEST_KEY_EMPLOYEEID = "txtEMPLOYEEID";

    /**
     * リクエスト：更新日付
     */
    public static final String REQUEST_KEY_UPDATEDATE = "txtUPDATEDATE";

    /**
     * リクエスト：ダウンロードファイル
     */
    public static final String REQUEST_KEY_UPFILE = "upfile";

    /**
     * リクエスト：削除用チェックボックス
     */
    public static final String REQUEST_KEY_DELETE_REST = "lstDeleteRest";

    /**
     * リクエスト：勤務パターンコード(退避用)
     */
    public static final String REQUEST_KEY_SUBPATTERNID = "txtSUBPATTERNID";

    /**
     * リクエスト：日付切替時刻
     */
    public static final String REQUEST_KEY_DATE_CHANGE_TIME = "txtDATECHANGETIME";

    /**
     * リクエスト：翌日勤務パターン
     */
    public static final String REQUEST_KEY_NEXT_PATTERNID = "txtNEXTPATTERNID";

    /**
     * チェックボックス
     */
    public static final String REQUEST_KEY_CHECK_APPROVAL = "checkApproval_hn_";

    /**
     * 適用画面対象職員件数
     */
    public static final String REQUEST_KEY_EMP_ROW_CNT = "txtEMP_ROW_CNT";

    /**
     * チェックボックスValiue
     */
    public static final String CHECKBOX_VALUE = "ON";

    /**
     * システムプロパティ「TMG_PTNSETTING_MODE」設定値：PKG
     */
    public static final String TMG_PTNSETTING_MODE_PKG = "PKG";

    /**
     * システムプロパティ「TMG_PTNSETTING_MODE」設定値：V0941
     */
    public static final String TMG_PTNSETTING_MODE_V0941 = "V0941";

    /**
     * クエリ：勤務パターン一覧
     */
    public static final int QUERY_RESULT_TMG_PATTERN = 0;

    /**
     * クエリ：勤務パターン適用一覧
     */
    public static final int QUERY_RESULT_TMG_PATTERN_APPLIES = 1;

    /**
     * クエリ：名称マスタ(凡例)
     */
    public static final int QUERY_RESULT_MAST_GENERIC_DETAIL = 2;

    /**
     * クエリ：休憩時間の制限値
     */
    public static final int QUERY_RESULT_REST_LIMIT = 3;

    /**
     * クエリ：勤務パターン一覧(自部局表示)
     */
    public static final int QUERY_RESULT_TMG_PATTERN_OWN_SECTION = 3;

    /**
     * アクション：一覧画面-表示
     */
    public static final String ACTION_DISP_DISP = "ACTION_DISP_DISP";

    /**
     * アクション：一覧画面-削除
     */
    public static final String ACTION_DISP_DELETE = "ACTION_DISP_DELETE";

    /**
     * アクション：編集画面-新規
     */
    public static final String ACTION_EDIT_NEW = "ACTION_EDIT_NEW";

    /**
     * アクション：編集画面-編集
     */
    public static final String ACTION_EDIT_UPDATE = "ACTION_EDIT_UPDATE";

    /**
     * アクション：編集画面-登録
     */
    public static final String ACTION_EDIT_SAVE = "ACTION_EDIT_SAVE";

    /**
     * アクション：適用画面-表示
     */
    public static final String ACTION_APPLIES_DISP = "ACTION_APPLIES_DISP";

    /**
     * アクション：適用画面-登録
     */
    public static final String ACTION_APPLIES_SAVE = "ACTION_APPLIES_SAVE";

    /**
     * アクション：適用画面-CSVダウンロード
     */
    public static final String ACTION_CSV_DOWNLOAD = "ACTION_CSV_DOWNLOAD";

    /**
     * ゼロ詰めフォーマット
     */
    public static final DecimalFormat DF_ZERO_PADDING = new DecimalFormat("0000");

    /**
     * アプリケーションの絶対パス
     */
    public static final String PATTEN_SETTING_PAGE_NAME = "ps.c01.tmg.PatternSetting.PatternSetting";

    /**
     * システムプロパティー キー値 休憩MAX値
     */
    public static final String SYSTEM_KEY_REST_MAX_SIZE = "TMG_REST_MAX_SIZE";

    /**
     * システムプロパティー キー値 適用画面の項目（身分、現在の勤務パターン、週勤務パターン）欄表示制御用の設定値
     */
    public static final String SYSTEM_KEY_PTNSETTING_MODE = "TMG_PTNSETTING_MODE";

    /**
     * リソースバンドルキー
     */
    public static final String MSG_CSV_INSERT = "MSG_CSV_INSERT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_DATA = "ERROR_DATA";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CUSTOMERID = "ERROR_CSV_CUSTOMERID";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CUSTOMERID_DOUBLEBYTE = "ERROR_CSV_CUSTOMERID_DOUBLEBYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CUSTOMERID_BYTE = "ERROR_CSV_CUSTOMERID_BYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_COMPANYID = "ERROR_CSV_COMPANYID";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_COMPANYID_DOUBLEBYTE = "ERROR_CSV_COMPANYID_DOUBLEBYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_COMPANYID_BYTE = "ERROR_CSV_COMPANYID_BYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_PATTERNID = "ERROR_CSV_PATTERNID";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_PATTERNID_DOUBLEBYTE = "ERROR_CSV_PATTERNID_DOUBLEBYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_PATTERNID_BYTE = "ERROR_CSV_PATTERNID_BYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_PATTERNNM = "ERROR_CSV_PATTERNNM";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_PATTERNNM_BYTE = "ERROR_CSV_PATTERNNM_BYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CDEFAULTFLG = "ERROR_CSV_CDEFAULTFLG";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CDEFAULTFLG_DOUBLEBYTE = "ERROR_CSV_CDEFAULTFLG_DOUBLEBYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CDEFAULTFLG_BYTE = "ERROR_CSV_CDEFAULTFLG_BYTE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CDEFAULTFLG_MASTER = "ERROR_CSV_CDEFAULTFLG_MASTER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_DATE_CHANGE_TIME = "ERROR_CSV_DATE_CHANGE_TIME";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_DATE_CHANGE_TIME_FORMAT = "ERROR_CSV_DATE_CHANGE_TIME_FORMAT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_OPEN = "ERROR_CSV_OPEN";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_OPEN_FORMAT = "ERROR_CSV_OPEN_FORMAT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CLOSE = "ERROR_CSV_CLOSE";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CLOSE_FORMAT = "ERROR_CSV_CLOSE_FORMAT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CLOSE_COMP = "ERROR_CSV_CLOSE_COMP";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_BREAK_OPEN_FORMAT = "ERROR_CSV_BREAK_OPEN_FORMAT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_BREAK_CLOSE_FORMAT = "ERROR_CSV_BREAK_CLOSE_FORMAT";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_BREAK_CLOSE_COMP = "ERROR_CSV_BREAK_CLOSE_COMP";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_BREAK = "ERROR_CSV_BREAK";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_BREAK_TIME_MAX = "ERROR_CSV_BREAK_TIME_MAX";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_REQUIRED_ITEM = "ERROR_CSV_REQUIRED_ITEM";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_BREAK_TIME_ORVER = "ERROR_BREAK_TIME_ORVER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_OPEN_RANGE_ORVER = "ERROR_OPEN_RANGE_ORVER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CLOSE_RANGE_ORVER = "ERROR_CLOSE_RANGE_ORVER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_BREAK_OPEN_RANGE_ORVER = "ERROR_BREAK_OPEN_RANGE_ORVER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_BREAK_CLOSE_RANGE_ORVER = "ERROR_BREAK_CLOSE_RANGE_ORVER";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_CDEFAULTFLG_ON = "ERROR_CSV_CDEFAULTFLG_ON";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_BREAK_OVERLAP = "ERROR_BREAK_OVERLAP";

    /**
     * リソースバンドルキー
     */
    public static final String WRD_WAVE_DASH = "WRD_WAVE_DASH";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_DUPLICATION_ID = "ERROR_CSV_DUPLICATION_ID";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_REPETITION = "ERROR_REPETITION";

    /**
     * リソースバンドルキー
     */
    public static final String ERROR_CSV_IS_NEXTPTN = "ERROR_CSV_IS_NEXTPTN";

    /**
     * 顧客コード
     */
    public static final int MAX_BYTE_CUSTOMERID = 10;

    /**
     * 法人コード
     */
    public static final int MAX_BYTE_COMPANYID = 10;

    /**
     * 勤務パターンID
     */
    public static final int MAX_BYTE_PATTERNID = 41;

    /**
     * 勤務パターン名称
     */
    public static final int MAX_BYTE_PATTERNNM = 50;

    /**
     * デフォルトフラグ
     */
    public static final int MAX_BYTE_CDEFAULTFLG = 41;

    /**
     * キー:開始
     */
    public static final String OPEN = "OPEN";

    /**
     * キー:終了
     */
    public static final String CLOSE = "CLOSE";

    /**
     * キー:リミット:勤務時：間下限
     */
    public static final String LIMIT_WORK_LOWER = "LIMIT_WORK_LOWER";

    /**
     * キー:リミット:勤務時：間上限
     */
    public static final String LIMIT_WORK_UPPER = "LIMIT_WORK_UPPER";

    /**
     * キー:リミット:休憩時間下限
     */
    public static final String LIMIT_REST_LOWER = "LIMIT_REST_LOWER";

    /**
     * キー:リミット:休憩時間
     */
    public static final String LIMIT_MSG = "LIMIT_MSG";

    /**
     * CSVファイルカラム番号_顧客ID
     */
    public static final int CSV_COLUMN_INDEX_CUSTOMERID = 0;

    /**
     * CSVファイルカラム番号_法人ID
     */
    public static final int CSV_COLUMN_INDEX_COMPANYID = 1;

    /**
     * CSVファイルカラム番号_勤務パターンID
     */
    public static final int CSV_COLUMN_INDEX_PATTERNID = 2;

    /**
     * CSVファイルカラム番号_勤務パターン名称
     */
    public static final int CSV_COLUMN_INDEX_PATTERNNM = 3;

    /**
     * CSVファイルカラム番号_デフォルトフラグ
     */
    public static final int CSV_COLUMN_INDEX_CDEFAULTFLG = 4;

    /**
     * CSVファイルカラム番号_日付切替時刻
     */
    public static final int CSV_COLUMN_INDEX_CHANGE_TIME = 5;

    /**
     * CSVファイルカラム番号_翌日の勤務パターン
     */
    public static final int CSV_COLUMN_INDEX_NEXTPTN = 6;

    /**
     * CSVファイルカラム番号_始業時刻
     */
    public static final int CSV_COLUMN_INDEX_OPEN = 7;

    /**
     * CSVファイルカラム番号_終業時刻
     */
    public static final int CSV_COLUMN_INDEX_CLOSE = 8;

    /**
     * CSVファイルカラム番号_休憩
     */
    public static final int CSV_COLUMN_INDEX_REST = 9;

    /**
     * JSON:CTYPE名:始業・就業時刻
     */
    public static final String JSON_CTYPE_PLAN = "PLAN";

    /**
     * JSON:CTYPE名:休憩時刻
     */
    public static final String JSON_CTYPE_REJECT = "REJECT";

    /**
     * 画面：一覧画面
     */
    public static final String SCREEN_DISP = "Disp";

    /**
     * 画面：編集画面
     */
    public static final String SCREEN_EDIT = "Edit";

    /**
     * 画面：適用画面
     */
    public static final String SCREEN_APPLIES = "Applies";

    /**
     * SYSDATE
     */
    public static final String SQL_SYSDATE = "SYSDATE";

    /**
     * TRUNC_SYSDATE
     */
    public static final String SQL_TRUNC_SYSDATE = "TRUNC(SYSDATE)";

    /**
     * MIN DATE
     */
    public static final String SQL_MIN_DATE = "TO_DATE('1900/01/01', 'yyyy/MM/dd')";

    /**
     * MAX DATE
     */
    public static final String SQL_MAX_DATE = "TO_DATE('2222/12/31', 'yyyy/MM/dd')";

    /**
     * LANGUAGE
     */
    public static final String SQL_LANGUAGE = "ja";

    /**
     * ONE
     */
    public static final String SQL_ONE = "1";

    /**
     * FW Version3
     */
    public static final String TXT_FW_VERSION3 = "3";

    /**
     * FW Version4
     */
    public static final String TXT_FW_VERSION4 = "4";

    /**
     * FW Version3
     */
    public static final String TXT_MASTGENERIC_SPLIT = "|";

    /**
     * 時間帯表示様式
     */
    public static final int TIMEBER_HOUR_MIN = 60;

    /**
     * 15分単位
     */
    public static final int TIMEBER_MIN_UNIT = 15;

    /**
     * ピクセル設定
     */
    public static final String TIMEBER_MIN_STYLE = "width: 1px";

}
