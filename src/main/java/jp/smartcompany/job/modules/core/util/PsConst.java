package jp.smartcompany.job.modules.core.util;

/**
 * @author Xiao Wenpeng
 */
public class PsConst {

    /** フレームバージョン */
    public static final String FRAME_VERSION = "4.10.0";
    /** システムプロパティKEY：FrameVersion バージョンチェック用 */
    public static final String KEY_VERSION = "FrameVersion";

    /** 顧客コード **/
    public static final String CUSTOMER_ID = "01";

    /** デフォルトシステム **/
    public static final String DEFAULT_SYSTEM = "01";

    /** ドメイン定数:従業員ドメイン */
    public static final String DOMAIN_EMPLOYEE = "01";
    /** ドメイン定数:組織ドメイン */
    public static final String DOMAIN_ORGANIZATION = "02";
    /** ドメイン定数:法人ドメイン */
    public static final String DOMAIN_COMPANY = "03";

    /** 日付定数:最小日付 */
    public static final String MINDATE = "1900/01/01";
    /** 日付定数:最大日付 */
    public static final String MAXDATE = "2222/12/31";

    /** リレーションID:自分自身 */
    public static final int RELID_EMP_USER_HIMSELF = 0;
    /** リレーションID:その他 */
    public static final int RELID_EMP_NO_SPECIAL = 97;
    /** リレーションID:未入社 */
    public static final int RELID_EMP_BEFORE_EMPLOY = 98;
    /** リレーションID:退職者 */
    public static final int RELID_EMP_RETIRE = 99;

    /** リレーションID:その他 */
    public static final int RELID_ORG_NO_SPECIAL = 7;

    /** レポートラインタイプ：デフォルト */
    public static final String REPORTLINE_TYPE_DEF = "000";

    /** 全社区分 */
    public static final String CODE_ALL_COMPANIES = "*";

    /** 日付の形式 */
    public static final String DATE_YYYYMMDD = "yyyy/MM/dd";

    /** RequestParameter KEY - サイトID */
    public static final String PARAM_KEY_SITEID = "psSite";
    /** RequestParameter KEY - アプリケーションID */
    public static final String PARAM_KEY_APPID = "psApp";
    /** RequestParameter KEY - サブアプリケーションID */
    public static final String PARAM_KEY_SUBAPPID = "psSubApp";
    /** RequestParameter KEY - サブアプリケーションID */
    public static final String PARAM_KEY_SCREEN = "psScreen";
    /** RequestParameter KEY - システムコード */
    public static final String PARAM_KEY_SYSTEM = "psSystem";
    /** RequestParameter KEY - 被検索者ユーザID */
    public static final String PARAM_KEY_USERID = "psTargetUser";
    /** RequestParameter KEY - 被検索法人コード */
    public static final String PARAM_KEY_COMP = "psSelectedComp";
    /** RequestParameter KEY - 被検索組織コード */
    public static final String PARAM_KEY_DEPT = "psSelectedDept";
    /** RequestParameter KEY - 被検索評価レベル */
    public static final String PARAM_KEY_EVALLEVEL = "psSelectedEvalLevel";
    /** RequestParameter KEY - 被検索レポートラインタイプ */
    public static final String PARAM_KEY_REPORTLINE = "psSelectedReportLine";
    /** RequestParameter KEY - セキュリティ基準日 */
    public static final String PARAM_KEY_SECURITYDATE = "psSecurityDate";
    /** RequestParameter KEY - データ検索基準日 */
    public static final String PARAM_KEY_SEARCHDATE = "psSearchDate";

    /** RequestParameter KEY - 顔写真名(ユーザID＋MD5) */
    public static final String PARAM_KEY_PHOTO_NAME = "psPhotoName";

    /** RequestParameter KEY - CheckDigit(MD5暗号化済) */
    public static final String PARAM_KEY_CHECK_DIGIT = "checkDigit";

    /** RequestParameter KEY - 言語区分 */
    public static final String PARAM_KEY_LANGUAGE = "psLanguage";

    public static final String PARAM_KEY_NOTFOUNDTITLE = "NotFoundTitle";

    public static final String MGD_NOTIFICATION_TYPE_DISTRIBUTION   = "NOTIFICATIONTYPE|000"; // 配布通知
    public static final String MGD_NOTIFICATION_TYPE_DEADLINE       = "NOTIFICATIONTYPE|010"; // 締切通知
    public static final String MGD_NOTIFICATION_TYPE_SUBMIT         = "NOTIFICATIONTYPE|020"; // 目標提出
    public static final String MGD_NOTIFICATION_TYPE_APP_TARGET     = "NOTIFICATIONTYPE|030"; // 承認
    public static final String MGD_NOTIFICATION_TYPE_FIX_SALF       = "NOTIFICATIONTYPE|040"; // 本人確定
    public static final String MGD_NOTIFICATION_TYPE_FIX_1ST        = "NOTIFICATIONTYPE|050"; // １次確定
    public static final String MGD_NOTIFICATION_TYPE_FIX_2ND        = "NOTIFICATIONTYPE|060"; // ２次確定
    public static final String MGD_NOTIFICATION_TYPE_FIX_3RD        = "NOTIFICATIONTYPE|070"; // ３次確定
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_TARGET = "NOTIFICATIONTYPE|080"; // 差戻
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_SELF   = "NOTIFICATIONTYPE|090"; // 本人確定解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_1ST    = "NOTIFICATIONTYPE|100"; // １次解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_2ND    = "NOTIFICATIONTYPE|110"; // ２次解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_3RD    = "NOTIFICATIONTYPE|120"; // ３次解除
    public static final String MGD_NOTIFICATION_TYPE_FEEDBACK       = "NOTIFICATIONTYPE|130"; // フィードバック
    public static final String MGD_NOTIFICATION_TYPE_CREATE_SHEET   = "NOTIFICATIONTYPE|140"; // 新規作成
    public static final String MGD_NOTIFICATION_TYPE_UNANSWERED     = "NOTIFICATIONTYPE|150"; // 未回答
    public static final String MGD_NOTIFICATION_TYPE_ANSWER_TARGET  = "NOTIFICATIONTYPE|160"; // 本人回答済
    public static final String MGD_NOTIFICATION_TYPE_ANSWER_1ST     = "NOTIFICATIONTYPE|170"; // １次追記済
    public static final String MGD_NOTIFICATION_TYPE_ANSWER_2ND     = "NOTIFICATIONTYPE|180"; // ２次追記済
    public static final String MGD_NOTIFICATION_TYPE_ANSWER_3RD     = "NOTIFICATIONTYPE|190"; // ３次追記済
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_ANSWER_TARGET  = "NOTIFICATIONTYPE|200"; // 本人回答済解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_ANSWER_1ST     = "NOTIFICATIONTYPE|210"; // １次追記済解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_ANSWER_2ND     = "NOTIFICATIONTYPE|220"; // ２次追記済解除
    public static final String MGD_NOTIFICATION_TYPE_RELEASE_ANSWER_3RD     = "NOTIFICATIONTYPE|230"; // ３次追記済解除

    // 人事台帳
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPLY       = "NOTIFICATIONTYPE|300";   // 申請
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPLY_SUB   = "NOTIFICATIONTYPE|305";   // 代理申請
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE1ST  = "NOTIFICATIONTYPE|310";   // 一次承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE2ND  = "NOTIFICATIONTYPE|320";   // 二次承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE3RD  = "NOTIFICATIONTYPE|330";   // 三次承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE4TH  = "NOTIFICATIONTYPE|340";   // 四次承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE5TH  = "NOTIFICATIONTYPE|350";   // 五次承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_APPROVE_PI  = "NOTIFICATIONTYPE|360";   // 人事部承認
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_REMAND      = "NOTIFICATIONTYPE|370";   // 差戻
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_REJECT      = "NOTIFICATIONTYPE|380";   // 却下
    public static final String MGD_NOTIFICATION_TYPE_HRWORKFLOW_RETRIEVE    = "NOTIFICATIONTYPE|390";   // 取戻

}
