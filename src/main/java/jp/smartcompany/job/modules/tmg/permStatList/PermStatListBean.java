package jp.smartcompany.job.modules.tmg.permStatList;

import cn.hutool.core.util.StrUtil;
import jp.smartcompany.job.modules.core.pojo.entity.TmgTriggerDO;
import jp.smartcompany.job.modules.core.service.*;
import jp.smartcompany.job.modules.core.util.PsDBBean;
import jp.smartcompany.job.modules.tmg.tmgresults.TmgResultsBean;
import jp.smartcompany.job.modules.tmg.tmgresults.vo.DispMonthlyVO;
import jp.smartcompany.job.modules.tmg.util.TmgReferList;
import jp.smartcompany.job.modules.tmg.util.TmgUtil;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 承認状況一覧クラス
 * ps.c01.tmg.PermStatList.PermStatListBean
 *
 * @author Nie Wanqun
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermStatListBean {

    /**
     * PsDBBean
     */
    private final PsDBBean psDBBean;

    /**
     * IMastGenericDetailService
     */
    private final IMastGenericDetailService iMastGenericDetailService;

    /**
     * ITmgEmployeeAttributeService
     */
    private final ITmgEmployeeAttributeService iTmgEmployeeAttributeService;

    /**
     * ITmgMonthlyService
     */
    private final ITmgMonthlyService iTmgMonthlyService;

    /**
     * ITmgEmployeesService
     */
    private final ITmgEmployeesService iTmgEmployeesService;

    /**
     * ITmgDailyService
     */
    private final ITmgDailyService iTmgDailyService;

    /**
     * ITmgDailyService
     */
    private final ITmgCompanyService iTmgCompanyService;

    /**
     * ITmgDailyDetailService
     */
    private final ITmgDailyDetailService iTmgDailyDetailService;

    /**
     * IMastOrganisationService
     */
    private final IMastOrganisationService iMastOrganisationService;

    /**
     * ITmgDailyCheckService
     */
    private final ITmgDailyCheckService iTmgDailyCheckService;

    /**
     * ITmgDailyDetailCheckService
     */
    private final ITmgDailyDetailCheckService iTmgDailyDetailCheckService;

    /**
     * ITmgErrmsgService
     */
    private final ITmgErrmsgService iTmgErrmsgService;

    /**
     * ITmgTriggerService
     */
    private final ITmgTriggerService iTmgTriggerService;
    /**
     * TmgReferList
     */
    private TmgReferList referList = null;


    public static final String BEANDESC  = "PermStatListBean";
    public static final String APPLICATION_ID = "PermStatList";
    public static final String TMG_PERMSTATLIST_PAGE_NAME = "ps.c01.tmg.PermStatList.PermStatList";

    /** プロパティファイル　キー */
    public static final String PROP_NO_CLOSETP_WITH_OVERTIME = "ERROR_NO_CLOSETP_WITH_OVERTIME";
    public static final String PROP_NON_APPROVAL_OVERTIME    = "ERROR_NON_APPROVAL_OVERTIME";

    /** クエリインデックス - [勤怠]月単位日別情報 */
    public static final int IDX_TMG_MONTHLY_INFO      = 0;
    /** クエリインデックス - [勤怠]全社カレンダー */
    public static final int IDX_TMG_CALENDAR          = 1;
    public static final int IDX_TMG_V_DAYCOUNT        = 2;
    public static final int IDX_TMG_MONTHLY_INFO_NEXT = 3;
    public static final int IDX_TMG_MONTHLY_INFO_PREV = 4;
    public static final int IDX_TMG_EMPLOYEE_DETAIL   = 5;
    public static final int IDX_TMG_DISP_MONTHLY_LIST = 6;

    /** カラムインデックス - [勤怠]月単位日別情報.職員番号 */
    public static final int COL_MONTHLY_INFO_EMPLOYEEID   = 0;
    /** カラムインデックス - [勤怠]月単位日別情報.職員氏名 */
    public static final int COL_MONTHLY_INFO_EMPLOYEENAME = 1;
    public static final int COL_MONTHLY_STATUSFLG         = 2;   // 月次情報ステータス
    public static final int COL_MONTHLY_TATUS_NAME        = 3;   // 月次情報ステータス(名称)
    public static final int COL_DAILY_COUNT               = 4;   // デイリーカウント情報
    public static final int COL_MONTHLY_LASTDAY           = 5;   // 月末日

    /** クエリインデックス - 日別一括承認画面表示 */
    public static final int IDX_TMG_DAILY            = 0;

    /** カラムインデックス - 休日フラグ */
    public static final int COL_TMG_CALENDAR_HOGFLG  = 0;

    public static final int COL_TMG_V_DAYCOUNT_DATE  = 0;
    public static final int COL_TMG_V_DAYCOUNT_DAY   = 1;
    public static final int COL_TMG_V_DAYCOUNT_PDATE = 2;

    public static final int COL_CEMPLOYEEID                     = 0; // 0 職員番号
    public static final int COL_CSTATUSFLG                      = 1; // 1 ステータス
    public static final int COL_CNOTCLOSETPWITHOVERTIME_EMPNAME = 2; // 2 『承認状態が「承認待」状態で、超勤命令を有する未終業打刻』状態の職員氏名
    public static final int COL_CEMPNAME                        = 3; // 3 職員氏名
    public static final int COL_CNONAPPROVALOVERHOUR_FLG        = 4; // 4 『申請中の超過勤務申請』を持つかどうか判定するフラグ

    /** カラムインデックス - 一括承認画面・承認状況表示項目マスタ */
    /** (日次情報)ヘッダ文言 */
    public static final int COL_DISPITEM_NAME    = 0;
    /** (日次情報)ヘッダキーワード */
    public static final int COL_DISPITEM_KEYWORD = 1;
    /** (日次情報)select句用SQL */
    public static final int COL_DISPITEM_SQL     = 2;
    /** (日次情報)表示幅 */
    public static final int COL_DISPITEM_WIDTH   = 3;
    /** (日次情報)データ表示スタイル */
    public static final int COL_DISPITEM_STYLE   = 4;

    /** カラムインデックス　表示年月遷移リスト　コード（年月日） */
    public static final int COL_DISPMONTHLYLIST_CODE = 0;

    /** カラムインデックス　表示年月遷移リスト　値（表示値） */
    public static final int COL_DISPMONTHLYLIST_VAL = 1;

    // #294 2007/09/18	J.okamoto	承認状況一覧における打刻反映処理対応
    // ステータス
    public static final String STATUS_UNCHECKED		= "TMG_DATASTATUS|1";	// 未チェック

    /** 勤怠承認権限を持っていない場合の一文字ステータス */
    public static final String STATUS_UNAPPLYAUTHORITY = "不";

    /** ステータス関連クラス - 未入力 */
    public static final String STYLE_CLASS_ST_UNINPUT     = "st_uninput";
    /** ステータス関連クラス - 未反映 */
    public static final String STYLE_CLASS_ST_UNCHECKEDI  = "st_unchecked";
    /** ステータス関連クラス - エラー有り */
    public static final String STYLE_CLASS_ST_ERROR       = "st_error";
    /** ステータス関連クラス - 承認待 */
    public static final String STYLE_CLASS_ST_PENDING     = "st_pending";
    /** ステータス関連クラス - 承認済 */
    public static final String STYLE_CLASS_ST_APPROVED    = "st_approved";
    /** ステータス関連クラス - 確定済み */
    public static final String STYLE_CLASS_ST_FIXED       = "st_fixed";
    /** ステータス関連クラス - 権限無し(承認不可) */
    public static final String STYLE_CLASS_SHONIN_DISABLE = "shonin_disable";
    /** ステータス関連クラス - 超過勤務命令 */
    public static final String STYLE_CLASS_CHOKIN         = "chokin";

    /** リクエストキー - アクション */
    public static final String REQ_ACTION          = "txtAction";
    /** リクエストキー - 対象組織 */
    public static final String REQ_SECTION_ID      = "txtSectionId";
    /** リクエストキー - 対象勤務年月日 */
    public static final String REQ_DYYYYMMDD       = "txtDYYYYMMDD";
    /** リクエストキー - 対象年月 */
    public static final String REQ_DYYYYMM         = "txtDYYYYMM";
    /** リクエストキー - 対象職員番号 */
    public static final String REQ_CEMPLOYEEID     = "txtCEMPLOYEEID";
    /** リクエストキー - リダイレクトBean名 */
    public static final String REQ_REDIRECT_BEAN   = "txtRedirectBean";
    /** リクエストキー - リダイレクト先アクション値 */
    public static final String REQ_REDIRECT_ACTION = "txtRedirectACTION";
    /** リクエストキー - コールバック先アクション値 */
    public static final String REQ_CALL_ACTION     = "txtCallBeanAction";
    /** リクエストキー - 処理対象職員番号 */
    public static final String REQ_EXECUTEEMPID    = "txtExecuteEmpId";
    /** リクエストキー - 再表示ボタン使用判定用 */
    private static final String TREEVIEW_KEY_REFRESH_FLG  = "txtTmgReferListTreeViewRefreshFlg";

    /** リダイレクト先Bean名 - 勤怠登録・承認申請*/
    public static final String REDIRECT_RESULTS_BEAN = "TmgResults";
    /** リダイレクト先アクション識別子 - 月別一覧画面表示 */
    public static final String REDIRECT_RESULTS_ACT_DISP_RMONTHLY   = TmgResultsBean.ACT_DISP_RMONTHLY;
    /** リダイレクト先アクション識別子 - 日別承認画面表示 */
    // TODO
    //public static final String REDIRECT_RESULTS_ACT_EDITPERM_RDAILY = TmgResultsBean.ACT_EDITPERM_RDAILY;

    /** スタイル - 休日 */
    public static final String STYLE_DAY_SUN   = "day_sun";
    /** スタイル - 平日 */
    public static final String STYLE_DAY_BASIC = "day_basic";
    /** スタイル - 当日 */
    public static final String STYLE_DAY_TODAY = "day_today";

    /** アクション - 月別一覧画面表示) */
    public static final String ACT_DISP_MONTHLY   = "ACT_DISP_MONTHLY";
    /** アクション - 月別一括承認処理 */
    public static final String ACT_MONTHLY_PERMIT = "ACT_MONTHLY_PERMIT";
    /** アクション - 日別一括承認画面表示 */
    public static final String ACT_EDIT_DAIRY     = "ACT_EDIT_DAIRY";
    /** アクション - 日別一括承認処理 */
    public static final String ACT_PERMIT         = "ACT_PERMIT";
    /** アクション - 別コンテンツへのリダイレクト */
    public static final String ACT_REDIRECT       = "ACT_REDIRECT";


    /** 汎用参照リスト */
    private TmgReferList _referList = null;

    /** JSP識別子 - Disp */
    private static final String JSP_DISP     = "Disp";
    /** JSP識別子 - Edit */
    private static final String JSP_EDIT     = "Edit";
    /** JSP識別子 - Redirect */
    private static final String JSP_REDIRECT = "Redirect";
    /** 日付形式1 */
    private static final String FORMAT_DATE_TYPE1 = "yyyy/MM/dd";
    /** 日付形式2 */
    private static final String FORMAT_DATE_TYPE2 = "yyyy'年'M'月'";
    /** 日付形式3 */
    private static final String FORMAT_DATE_TYPE3 = "yyyy'年'M'月'd'日'";
    private static final String FORMAT_SLASH      = "/";
    private static final String FORMAT_ZERO       = "00";

    /** 1ヵ月後 */
    private static final int PARAM_NEXT_MONTH =  0;
    /** 今月 */
    private static final int PARAM_THIS_MONTH = -1;
    /** 1ヶ月前 */
    private static final int PARAM_PREV_MONTH = -2;

    /** アクション */
    private String _sAction           = null;
    /** サイトID */
    private String _reqSiteId         = null;
    /** システム年月日 */
    private String _sysdate           = null;
    /** 対象組織 */
    private String _reqSectionId      = null;
    /** 対象勤務年月日 */
    private String _reqDYYYYMMDD      = null;
    /** 対象年月(検索対象年月) */
    private String _reqDYYYYMM        = null;
    /** 対象職員番号 */
    private String _reqEmployeeId     = null;
    /** 登録対象職員番号 */
    private String _reqExecuteEmpId   = null;
    /** ログイン顧客コード */
    private String _loginCustId   = null;
    /** ログイン法人コード */
    private String _loginCompCode = null;
    /** ログインユーザーコード */
    private String _loginUserCode = null;
    /** 対象年月の前月 */
    private String _prevMonth = null;
    /** 対象年月の翌月 */
    private String _nextMonth = null;
    /** 今月 */
    private String _thisMonth = null;

    /** 裁量労働者以外の判定 */
    public static final String NO_DISCRETION = "0";
    /** 裁量労働者の判定 */
    public static final String DISCRETION = "1";

    /** 日次情報表示項目Map */
    public HashMap<String, ArrayList<String>> permstatlistItemMap = new HashMap<String, ArrayList<String>>();

    /** 表示項目Mapキー:ヘッダー名称 */
    public static final String DISPITEM_KEY_HEADER = "header";

    /** 表示項目Mapキー:ヘッダーKeyWord */
    public static final String DISPITEM_KEY_HKEYWORD = "keyword";

    /** 表示項目Mapキー:職員毎用SQL */
    public static final String DISPITEM_KEY_SQL = "sql";

    /** 表示項目Mapキー:WIDTH */
    public static final String DISPITEM_KEY_WIDTH = "width";

    /** 表示項目Mapキー:データスタイル */
    public static final String DISPITEM_KEY_DATASTYLE = "style";

    /** 承認欄判別マスタ設定キーワード */
    private static final String DISPITEM_STATUS_KEYWORD = "APPROVALSTATUS";

    /** 氏名欄判別マスタ設定キーワード */
    private static final String DISPITEM_NAME_KEYWORD = "EMPNAME";

    /** 超勤欄判別マスタ設定キーワード */
    private static final String DISPITEM_OVERTIME_KEYWORD = "OVERTIME";

    /**
     * 対象組織が選択されているかどうかを表します。
     * <p>
     * 	true(選択あり) / false(選択なし) <br>
     * 	初回遷移時などでは汎用参照コンポーネントで組織が選択されていない状態
     * 	のためfalseとなります。
     * </p>
     */
    private boolean _isSelectSection = false;

    /* 2007/08/03 	H.Kawabata		参照権限チェック仕様変更対応 */
    /** 勤怠シートの参照権限(基準日の翌月) */
    boolean _authorityNextMonth       = false;
    /** 勤怠シートの参照権限(基準月) */
    boolean _authorityMonth           = false;
    /** 参照権限：参照可能 */
    private static final boolean CB_CAN_REFER = true;
    /** 参照権限：参照不可能  */
    private static final boolean CB_CANT_REFER = false;

    // 申請ステータス：申請中
    private static final String STATUS_OVERHOURS_STATUS_0    = "TMG_OVERHOUR_STATUS|0";
    // 申請ステータス：確認済
    private static final String STATUS_OVERHOURS_STATUS_5    = "TMG_OVERHOUR_STATUS|5";


    /**
     * 検索対象年月を返す
     * @return String 検索対象年月
     */
    public String getReqDYYYYMM() {
        return this._reqDYYYYMM;
    }

    /**
     * 承認サイト・管理サイト
     * 月別一覧画面表示
     * <p>
     * ACT_DISP_MONTHLY
     *
     * @param modelMap
     */
    public void actDispMonthly(ModelMap modelMap) {

        // 月別一覧表示の為のプロセスを実行します。
        executeReadMonthlyList(modelMap);
    }


    /**
     * 承認サイト・管理サイト
     * 日別一括承認画面表示
     * <p>
     * ACT_EDIT_DAIRY
     *
     * @param modelMap
     */
    public void actEditDairy(ModelMap modelMap) {

    }

    /**
     * 承認サイト・管理サイト
     * 月別一括承認処理
     * <p>
     * ACT_MONTHLY_PERMIT
     *
     * @param modelMap
     */
    public void actMonthlyPermit(ModelMap modelMap) {

    }

    /**
     * 承認サイト・管理サイト
     * 日別一括承認処理
     * <p>
     * ACT_PERMIT
     *
     * @param modelMap
     */
    public void actPermit(ModelMap modelMap) {

    }

    /**
     * 承認サイト・管理サイト
     * 別コンテンツへのリダイレクト
     * <p>
     * ACT_REDIRECT
     *
     * @param modelMap
     */
    public void actRedirect(ModelMap modelMap) {

    }



    /**
     * 月別一覧表示の為のプロセスを実行します。
     * <p>
     * 実行するSQL生成プロセスは以下になります。
     * 	<ol>
     * 		<li>職員氏名と、承認ステータス状態を取得する。</li>
     * 		<li>カレンダーテーブルより休日フラグを取得する。</li>
     * 		<li>対象勤務年月の1ヶ月間の日付・曜日を取得する。</li>
     * 		<li>翌月リンクを作成する為の勤怠データ件数を取得する。</li>
     * 		<li>前月リンクを作成する為の勤怠データ件数を取得する。</li>
     * 		<li>職員情報を取得する。</li>
     * 		<li>表示月遷移リスト情報を取得する。</li>
     * 	</ol>
     * </p>
     * <p>
     * 	結果セット編集プロセスは以下になります。
     * 	<ol>
     * 		<li>カレンダーリストを設定する。</li>
     * 		<li>承認状況一覧を設定する。</li>
     * 	</ol>
     * </p>
     *    @exception Exception
     */
    private void executeReadMonthlyList(ModelMap modelMap){

        String empSql = getReferList().buildSQLForSelectEmployees();

        // 打刻反映処理を行う。
        execReflectionTimePunch(empSql);

        // 月次一覧表示データを取得する。
//        // 0 表示月情報の取得
//        buildSQLForSelectTMG_MONTHLY_INFO(empSql);
//
//        // 1 カレンダー情報の取得
//        buildSQLForSelectTMG_CALENDER();
//
//        // 2 対象勤務年月の1ヶ月間の日付・曜日を取得
//        buildSQLForSelectTMG_V_DAYCOUNT();
//
//        // 3 表示対象月の前月データを持つ職員数
//        buildSQLForSelectTMG_MONTHLY_INFO_NEXT(TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_NEXT_MONTH));
//
//        // 4 表示対象月の翌月データを持つ職員数
//        buildSQLForSelectTMG_MONTHLY_INFO_PREV(TmgUtil.getFirstDayOfMonth(getReqDYYYYMM(), PARAM_PREV_MONTH));

        // 5 選択組織名称の取得
        iMastOrganisationService.buildSQLForSelectEmployeeDetail(_reqSectionId,getToDay(),_loginCustId,_loginCompCode);

        // 6 表示月遷移リスト情報取得
        List<DispMonthlyVO> cispMonthlyVOList = iTmgMonthlyService.buildSQLForSelectDispTmgMonthlyList(getThisMonth(), empSql);


    }

    /**
     * 月次一覧、また日次承認画面表示時の打刻反映処理
     */
    private void execReflectionTimePunch(String empSql) {

        // アクション
        String action = _sAction;

        // メニューから初期の月次一覧画面表示時はアクションが未設定なので、月次一覧画面表示アクションを設定する。
        if (StrUtil.hasEmpty(_sAction)) {

            action = ACT_DISP_MONTHLY;
        }

        // 打刻反映処理対象の期間を取得
        String stDate;  // 開始日
        String endDate; // 終了日

        // 月次一覧画面表示時
        if (action.equals(ACT_DISP_MONTHLY)) {

            // 表示月のカレンダークラス
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(getReqDYYYYMM().split("/")[0]),
                    Integer.parseInt(getReqDYYYYMM().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(getReqDYYYYMM().split("/")[2]), 0, 0);

            // 日付を月末日にする。
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

            // 運用月のカレンダークラス
            Calendar sysCal = Calendar.getInstance();
            sysCal.set(Integer.parseInt(TmgUtil.getSysdate().split("/")[0]),
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[1]) - 1, // カレンダークラスでは月が０から始まるので１引いてあげる
                    Integer.parseInt(TmgUtil.getSysdate().split("/")[2]), 0, 0);

            // 運用日 <= 表示月末日の場合、表示月が運用月なので運用日を打刻反映処理の期間終了日とする。（未来については打刻反映処理対象外なので）
            if (sysCal.compareTo(cal) <= 0) {

                // 運用月の月初～運用日までを対象期間とする。
                stDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.getMinimum(Calendar.DATE);
                endDate = sysCal.get(Calendar.YEAR) + "/" + (sysCal.get(Calendar.MONTH) + 1) + "/" + sysCal.get(Calendar.DATE);
            } else {

                // 表示月の月初～月末までを対象期間とする。
                stDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getMinimum(Calendar.DATE);
                endDate = cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.getActualMaximum(Calendar.DATE);
            }

            // 日次承認画面表示時
        } else {

            // 日次画面を表示する場合は打刻反映処理の対象が表示日のみなので開始、終了日共に表示日とする。
            stDate = _reqDYYYYMMDD;
            endDate = _reqDYYYYMMDD;
        }

        // 打刻反映処理
        iTmgTriggerService.buildSQLForInsertTmgTriggerByTimePunch(psDBBean.getUserCode(), action, stDate, endDate, empSql);
        iTmgTriggerService.getBaseMapper().delete(SysUtil.<TmgTriggerDO>query().eq("TTR_CCUSTOMERID", psDBBean.getCustID())
                .eq("TTR_CCOMPANYID", psDBBean.getCompCode())
                .eq("TTR_CMODIFIERUSERID", _loginUserCode)
                .eq("TTR_CMODIFIERPROGRAMID", APPLICATION_ID + "_" + action));

        // TODO
        //TmgUtil.checkInsertErrors(setInsertValues(vQuery, BEANDESC), session, BEANDESC);
    }


    /**
     * メインメソッド。
     */
    public void execute(ModelMap modelMap) throws Exception {

        setExecuteParameter(modelMap);

        // 2007/08/03 	H.Kawabata		参照権限チェック仕様変更対応
        // ■初期表示時：
        //   　選択した組織、(もしくはグループ)の対象年月(デフォルトでは現在日付時点の年月)時点での
        //   評価状況一覧コンテンツの参照権限をチェックする。
        //   参照権限がある場合は、問題なく評価状況一覧を表示する。
        //   　しかし、参照権限が無い場合は1ヶ月遡った月の参照権限をチェックする。
        //   1ヶ月遡った月の参照権限があればその月の評価状況一覧を表示し、
        //   1ヶ月遡った月の参照権限も無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
        //   メッセージを画面へ表示する。
        // ■初期表示以外：
        //   選択した組織、(もしくはグループ)の対象年月時点での評価状況一覧コンテンツの参照権限をチェックする。
        //   権限があれば問題なく評価状況一覧を表示する。
        //   権限が無い場合は画面に「参照できる職員が存在しません」(文言変更有り)
        //   メッセージを画面へ表示する。
        //   ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
        //     権限が無いのと同じ扱いとする。
        // 勤怠承認サイト、もしくは勤怠管理サイトの場合に以下の処理を実行する
        if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId()) || TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId())) {
            // 勤怠承認サイトは初期表示時、勤怠管理サイトは初期表示+(組織選択時or組織選択済)の場合
            // ※勤怠管理サイトの場合、初期表示時でも組織が選択されていない状態なら権限チェックを行わない
            if (TmgUtil.Cs_SITE_ID_TMG_PERM.equals(psDBBean.getSiteId())  && StrUtil.isEmpty(_sAction) ||
                    TmgUtil.Cs_SITE_ID_TMG_ADMIN.equals(psDBBean.getSiteId()) && StrUtil.isEmpty(_sAction) && isSelectSection()
            ) {
                // 参照権限チェック(現在時点での年月)
                if (getReferList().existsAnyone(getFirstDayOfSysdate()) && getReferList().isThereSomeEmployees(getFirstDayOfSysdate())) {
                    setAuthorityMonth(CB_CAN_REFER);

                    // 参照権限が無い場合は、1ヶ月過去のシートの権限をチェックする。
                } else {
                    String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);

                    // 汎用参照コンポーネントの基準日を基準日の前月(過去)に設定しなおす
                    setReferList(sPrevMonth,modelMap);

                    // 参照権限の設定:
                    // 初期表示時の対象年月の時点の参照権限が無い場合に、
                    // 1ヶ月過去の参照権限を判定し参照権限がある場合は1ヶ月過去のシートを参照する。
                    // 権限が無い場合は、参照できない。
                    if (getReferList().existsAnyone(sPrevMonth) && getReferList().isThereSomeEmployees(sPrevMonth)) {

                        // 対象年月が現在の年月の場合、1ヶ月過去の年月を対象年月に設定します
                        // このif文は、現在「部署A」を選択していて対象年月が変更された状態で「組織B」を選択しなおすと
                        // 「組織B」の現在日付時点の年月と、その年月-1ヶ月時点での参照権限をチェックします。
                        // その際に、変更後対象年月が現在年月でない場合にも現在年月-1ヶ月を設定されるのを防ぐ為
                        // 「対象年月が現在の年月の場合」という条件を実装しています。
                        if (getFirstDayOfSysdate().equals(getReqDYYYYMM())) {
                            // 対象年月を1ヶ月過去に設定します
                            setReqDYYYYMM(sPrevMonth);
                            // 検索対象年月の前月
                            _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
                            // 検索対象年月の翌月
                            _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);
                        }
                        setAuthorityMonth(CB_CAN_REFER);
                    } else {
                        // 対象年月を元に戻します
                        setReferList(getReqDYYYYMM(),modelMap);

                        setAuthorityMonth(CB_CANT_REFER);
                    }
                }

                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                if (getReferList().existsAnyone(getNextMonth()) && getReferList().isThereSomeEmployees(getNextMonth())) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }

                // 初期表示時以外
                // ※組織を選択していないときは権限チェックを行わない。
                // 　勤怠管理サイトで組織未選択時に権限チェックを行うとえらーで落ちてしまうので
                // 　それを防ぐ為に「組織を選択しているとき」という条件を実装しています。
            } else if (isSelectSection()) {
                // 参照権限の判定：設定(当月分)
                // 当月もしくは、先月どちらかの権限が有効な場合は過去に関しては常に表示する(シートがある限り)
                String sPrevMonth = TmgUtil.getFirstDayOfMonth(getFirstDayOfSysdate(), PARAM_PREV_MONTH);
                if (getReferList().existsAnyone(getFirstDayOfSysdate()) && getReferList().isThereSomeEmployees(getFirstDayOfSysdate()) ||
                        getReferList().existsAnyone(sPrevMonth)             && getReferList().isThereSomeEmployees(sPrevMonth)
                ) {
                    setAuthorityMonth(CB_CAN_REFER);
                } else {
                    setAuthorityMonth(CB_CANT_REFER);
                }

                // 2007/08/07 	H.Kawabata		参照権限チェック仕様変更対応
                // 選択した組織、(もしくはグループ)の対象年月の翌月(未来の月)の権限をチェックする。
                // 翌月の権限があればリンク「>」を画面に表示する。
                // 権限が無い場合は「>」を表示しない。
                // ※また、権限はあるが選択している組織(もしくはグループ)に所属している職員が存在しない場合も
                //   権限が無いのと同じ扱いとする。
                if (getReferList().existsAnyone(getNextMonth()) && getReferList().isThereSomeEmployees(getNextMonth())) {
                    setAuthorityNextMonth(CB_CAN_REFER);
                } else {
                    setAuthorityNextMonth(CB_CANT_REFER);
                }
            }
            // その他のサイトの場合
        } else {
            setAuthorityMonth(CB_CAN_REFER);
        }

    }


    /**
     * 処理実行用パラメータの設定を行います。
     *
     * @throws Exception
     */
    public void setExecuteParameter(ModelMap modelMap) throws Exception {

        _sysdate = psDBBean.getSysDate();
        _reqSiteId = psDBBean.getSiteId();
        _loginCustId = psDBBean.getCustID();
        _loginCompCode = psDBBean.getCompCode();
        _loginUserCode = psDBBean.getUserCode();
        _sAction = psDBBean.getReqParam(REQ_ACTION);
        _reqDYYYYMM = psDBBean.getReqParam(REQ_DYYYYMM);
        _reqDYYYYMMDD = psDBBean.getReqParam(REQ_DYYYYMMDD);
        _reqEmployeeId = psDBBean.getReqParam(REQ_CEMPLOYEEID);
        _reqExecuteEmpId = psDBBean.getReqParam(REQ_EXECUTEEMPID);

        // 検索対象年月の入力がなければ、現在日付月初を検索対象年月する。
        if (_reqDYYYYMM == null || _reqDYYYYMM.length() == 0) {
            _reqDYYYYMM = getFirstDayOfSysdate();
        }

        try {
            // TmgReferListの生成
            setReferList(_reqDYYYYMM, modelMap);
            // 組織コードの取得
            _reqSectionId = _referList.getTargetSec();

            // 組織が選択されているか確認
            if (_reqSectionId == null || _reqSectionId.length() == 0) {
                _isSelectSection = false;
            } else {
                _isSelectSection = true;
            }

        } catch (Exception e) {
            // TODO

        }

        // 検索対象年月の前月
        _prevMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_PREV_MONTH);
        // 検索対象年月の翌月
        _nextMonth = TmgUtil.getFirstDayOfMonth(_reqDYYYYMM, PARAM_NEXT_MONTH);

        /*
         * 初期表示時、または組織ツリー再表示時の表示対象日付設定を行う。
         * 初期表示時は「getReqParm(REQ_ACTION)」がNULLとなる。
         * 再表示ボタン押下時は「getReqParm(TREEVIEW_KEY_REFRESH_FLG)」に値が設定される。
         */
        // 組織ツリー基準日情報チェック
        if (_referList.getRecordDate() == null) {
            // 今月の月初
            _thisMonth = TmgUtil.getFirstDayOfMonth(psDBBean.getSysDate(), PARAM_THIS_MONTH);
        } else {
            // 組織ツリー基準日
            _thisMonth = TmgUtil.getFirstDayOfMonth(_referList.getRecordDate(), PARAM_THIS_MONTH);

            // 初期表示、再表示ボタン使用時処理
            if (StrUtil.isEmpty(psDBBean.getReqParam(REQ_ACTION)) || StrUtil.isNotEmpty(psDBBean.getReqParam(TREEVIEW_KEY_REFRESH_FLG))) {

                // 表示日付変更
                _reqDYYYYMM = TmgUtil.getFirstDayOfMonth(_referList.getRecordDate(), PARAM_THIS_MONTH);
                // 検索対象年月の前月
                _prevMonth = TmgUtil.getFirstDayOfMonth(_referList.getRecordDate(), PARAM_PREV_MONTH);
                // 検索対象年月の翌月
                _nextMonth = TmgUtil.getFirstDayOfMonth(_referList.getRecordDate(), PARAM_NEXT_MONTH);
            }
        }
    }


    /**
     * システム日付の月初日(1日)を返却します。
     * <p>
     * [例] "2007/03/23" --> "2007/03/01"
     * </p>
     *
     * @return 基準日
     */
    private String getFirstDayOfSysdate() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TYPE1);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, 1);
        Date date = calendar.getTime();

        return sdf.format(date);
    }

    /**
     * 汎用リンクオブジェクトを生成します。
     *
     * @param pDate 対象年月日
     */
    private void setReferList(String pDate, ModelMap modelMap) {


        try {
            _referList = new TmgReferList(psDBBean, "PermStatList", pDate, TmgReferList.TREEVIEW_TYPE_LIST, true);
            _referList.putReferList(modelMap);
        } catch (Exception e) {
            // TODO
        }

    }


    /** 対象組織有無フラグを返却します。
     * @return 対象組織有無フラグ
     */
    public boolean isSelectSection() {
        return _isSelectSection;
    }

    /**
     * 生成したReferListを返す
     * @return _referList
     */
    public TmgReferList getReferList() {
        return _referList;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
     */
    public void setAuthorityMonth(boolean bValue) {
        _authorityMonth = bValue;
    }

    /**
     * 対象勤務年月日を返却します。
     */
    public String getReqDYYYYMMDD() {
        return _reqDYYYYMMDD;
    }

    public void setReqDYYYYMM(String sValue) {
        this._reqDYYYYMM = sValue;
    }

    /**
     * 対象年月の前月を返却します。
     */
    public String getPrevMonth() {
        return _prevMonth;
    }

    /**
     * 対象年月の翌月を返却します。
     */
    public String getNextMonth() {
        return _nextMonth;
    }

    /**
     * 今月の月初を返却します。
     */
    public String getThisMonth() {
        return _thisMonth;
    }

    /**
     * 今日の日付を返す(基準日があればその日付)
     * @return String 今日の日付
     */
    private String getToDay() {

        if (_referList != null) {
            return _referList.getRecordDate();
        } else {
            return getSysdate();
        }

    }

    /**
     * システム日付を返す
     * @return String システム日付
     */
    private String getSysdate() {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TYPE1);
        return sdf.format(new Date());
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)設定メソッド
     */
    public void setAuthorityNextMonth(boolean bValue) {
        _authorityNextMonth = bValue;
    }

    /**
     * 勤怠シートの参照権限(基準日の翌月)取得メソッド
     */
    public boolean getAuthorityNextMonth() {
        return _authorityNextMonth;
    }


}
