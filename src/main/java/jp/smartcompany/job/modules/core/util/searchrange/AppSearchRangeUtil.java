package jp.smartcompany.job.modules.core.util.searchrange;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import jp.smartcompany.job.common.GlobalException;
import jp.smartcompany.job.modules.core.business.BaseSectionBusiness;
import jp.smartcompany.job.modules.core.business.SysInfoBusiness;
import jp.smartcompany.job.modules.core.pojo.bo.BaseSectionBO;
import jp.smartcompany.job.modules.core.pojo.bo.LoginGroupBO;
import jp.smartcompany.job.modules.core.pojo.entity.HistDesignationDO;
import jp.smartcompany.job.modules.core.service.IHistDesignationService;
import jp.smartcompany.job.modules.core.util.Designation;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.util.SysUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 *   @system    Progress@Site
 *   @version   4.0
 *   @since     JDK1.5
 *   @id        AppSearchRangeInfoLogicImpl
 *   @title     アプリケーション別検索範囲取得処理のLogic実装クラス
 *   @author    C.Yamada
 *   @create    2007/06/05
 *   @update    更新日		 更新者		 更新内容
 *              2007/06/22 C.Yamada  [仕様変更対応]検索範囲情報をDBではなく、
 *                                   常駐変数より取得させる用に変更
 *              2007/07/28 C.Yamada  [仕様変更対応]ドメインごとに処理分けを行うように変更
 *                                   [仕様変更対応]基点組織設定データが存在した場合は、
 *                                   設定した式と基点組織設定との結合条件を作成する
 *              2007/09/21 C.Yamada  [障害対応]検索対象範囲判定で組織を条件とした場合、
 *                                   組織コードに不要な文字列が付加される
 *                                   [障害対応]検索対象範囲で「自分の」属性と比較の場合に
 *                                   ログインユーザの条件が付加されていない
 *				2010/12/01	S.Saito		プリセット条件式に基準日条件を追加できるように修正（サポートサイト2761）
 *				2011/09/27	S.Saito		getSetDefaultを選択中法人、組織ではなく自法人、自組織で比較するように修正
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppSearchRangeUtil {

    /** ログイン情報クラス */
    private PsSession gPsSession;
    /** リクエストパラメータ‐サイトID */
    private String gPsSite;
    /** リクエストパラメータ‐アプリケーションID */
    private String gsPsApp;
    /** リクエストパラメータ‐検索対象者ユーザID **/
    private String gsPsTargetUser;
    /** リクエストパラメータ‐選択法人コード **/
    private String gsPsSelectedComp;
    /** リクエストパラメータ‐選択組織コード **/
    private String gsPsSelectedDept;
    /** リクエストパラメータ‐基準日 **/
    private String gsPsSecurityDate;
    /** リクエスト **/
    private final HttpServletRequest gRequest;

    /** テーブル結合式情報ロジック */
    private final PsTableCombinationType psTableCombinationType;

    /** アプリケーション起動権限情報取得Logicクラスオブジェクト */
    private final AppAuthUtil gAppAuthInfoLogic;

    /** システム情報取得クラスオブジェクト */
    private final SysInfoBusiness sysInfoBusiness;

    /** システム情報取得(基点組織) */
    private final BaseSectionBusiness baseSectionBusiness;

    private final IHistDesignationService iHistDesignationService;

    private final TableCombinationTypeCache tableCombinationTypeCache;

    private final AppSearchRangeInfoCache appSearchRangeInfoCache;

    private final SearchRangeInfoCache searchRangeInfoCache;

    /** 異動歴 所属長フラグ*/
    private static final String COL_HD_BOSSORNOT    = "HD_CBOSSORNOT";

    /** 組織ツリーマスタ 組織階層コード */
    private static final String COL_MO_LAYERED_ID   = "MO_CLAYEREDSECTIONID";

    /** クエリ組み立てパーツ イコール */
    private static final String PT_EQUAL        = " = ";
    private static final String PT_UNEQUAL        = " != ";
    /** クエリ組み立てパーツ アンド */
    private static final String PT_AND          = " AND ";
    /** クエリ組み立てパーツ オア */
    private static final String PT_OR           = " OR ";
    /** クエリ組み立てパーツ カンマ(空白あり) */
    private static final String PT_COMMA        = " , ";
    /** クエリ組み立てパーツ カンマ(空白なし) */
    private static final String PT_TRIM_COMMA   = ",";
    /** クエリ組み立てパーツ 空白 */
    private static final String PT_SPACE        = " ";
    /** クエリ組み立てパーツ 右カッコ */
    private static final String PT_OPEN_PAR     = " ( ";
    /** クエリ組み立てパーツ 左カッコ */
    private static final String PT_CLOSE_PAR    = " ) ";
    /** クエリ組み立てパーツ シャープ */
    private static final String PT_SIGN         = "##";
    /** クエリ組み立てパーツ ドッド */
    private static final String PT_DOD          = ".";
    /** クエリ組み立てパーツ 記号(小なり) */
    private static final String PT_LESSER       = " <= ";
    /** クエリ組み立てパーツ 記号(大なり) */
    private static final String PT_GREATER      = " >= ";
    /** クエリ組み立てパーツ システム日付 */
    private static final String PT_SYSDATE      = " TRUNC(SYSDATE) ";
    /** クエリ組み立てパーツ LIKE検索用 */
    private static final String PT_LIKE         = " LIKE ";
    /** クエリ組み立てパーツ アポストロフィ */
    private static final String PT_APO          = "'";
    /** クエリ組み立てパーツ パーセント */
    private static final String PT_PERCENT      = "%";
    /** クエリ組み立てパーツ イン */
    private static final String PT_IN           = " IN ";
    /** クエリ組み立てパーツ 縦棒 */
    private static final String PT_BAR          = "\\|";
    /** クエリ組み立てパーツ アンダーバー */
    private static final String PT_UBAR         = "_";
    /** クエリ組み立てパーツ IS NULL */
    private static final String PT_ISNULL       = "IS NULL";
    /** クエリ組み立てパーツ IS NOT NULL */
    private static final String PT_ISNOTNULL    = "IS NOT NULL";

    /** 所属長フラグ */
    private static final String BOSS_FLG = "1";
    /** 基点組織使用フラグ (有) */
    private static final String BASE_SECTION_TRUE = BOSS_FLG;
    /** 自分のフラグ 通常の比較 */
    private static final String MY_FLG0  = "0";
    /** 自分のフラグ 自分自身との比較 */
    private static final String MY_FLG1  = BOSS_FLG;
    /** 自分のフラグ プリセット用(単一クエリ) */
    private static final String MY_FLG2  = "2";
    /** 自分のフラグ プリセット用(複数クエリ) */
    private static final String MY_FLG3  = "3";

    /** 条件(IN句)に指定できる値の制限数 */
    private static final long LIMITVALUE = 1000;
    /** ORの文字数 */
    private static final int CNT_OR     = 4;
    /** ANDの文字数 */
    private static final int CNT_AND    = 5;

    /** マスタテーブルID 法人マスタ */
    private static final String QCOMPANY	= "QCOMPANY";
    /** マスタテーブルID 組織マスタ */
    private static final String QSECTION	= "QSECTION";
    /** マスタテーブルID 役職マスタ */
    private static final String QPOST		= "QPOST";
    /** マスタテーブルID 役職順位マスタ */
    private static final String QPOSTNUM	= "QPOSTNUM";

    /** 基本情報テーブル */
    private static final String TBL_MAST_EMPLOYEES		= "MAST_EMPLOYEES";
    /** 基本情報テーブル */
    private static final String TBL_HIST_DESIGNATION	= "HIST_DESIGNATION";
    /** 組織ツリーマスタ テーブル */
    private static final String TBL_MAST_ORGANISATION	= "MAST_ORGANISATION";
    /** 役職ツリーマスタ テーブル */
    private static final String TBL_MAST_POST			= "MAST_POST";
    /** 法人ツリーマスタ テーブル */
    private static final String TBL_MAST_COMPANY		= "MAST_COMPANY";

    /** 検索対象範囲条件のFrom句リスト */
    private List<String> glistFrom = CollUtil.newLinkedList();
    /** 検索対象範囲条件の結合Where句リスト */
    private List<String> glistCombWhere = CollUtil.newLinkedList();
    /** 検索対象範囲条件の条件Where句 */
    private String gsCondWhere = null;
    /** 検索対象範囲条件 設定済フラグ(true：設定あり, false:未設定) */
    private boolean gbCondSetupFlg = false;
    /** 検索対象範囲条件 退職者(0:検索しない 1:自社のみ 2:すべて) */
    private int gnCondRetired = 0;

    /** 検索対象範囲条件のFrom句リスト(内部でのみ使用) */
    private Map<String, String> gConditionFromMap;

    /** 基点組織判定で"()"を含めない*/
    private static final String SELECT_MODE_1 = "yes";
    /** 基点組織判定で"()"を含める*/
    private static final String SELECT_MODE_2 = "no";

    /** 「全て」の場合使用 */
    private static final String CODE_0001 = "0001";
    /** 全件抽出クエリ用 */
    private static final String ALLSQL = "'A' = 'A'";
    /**　未抽出クエリ用 */
    private static final String NOMATCHSQL = "'A' != 'A'";

    /**
     * ログイン情報コンポーネント取得（自動インジェクション）
     *
     * @param pPsSession PsSession
     */
    public void setPsSession(PsSession pPsSession) {
        this.gPsSession = pPsSession;
    }

    /**
     * リクエストパラメータよりサイトID取得（自動インジェクション）
     *
     * @param psSite サイトID
     */
    public void setPsSite(String psSite) {
        this.gPsSite = psSite;
    }

    /**
     * リクエストパラメータよりアプリケーションID取得（自動インジェクション）
     *
     * @param psApp アプリケーションID
     */
    public void setPsApp(String psApp) {
        this.gsPsApp = psApp;
    }

    /**
     * リクエストパラメータより検索対象者のユーザID取得（自動インジェクション）
     *
     * @param psTargetUser 検索対象者のユーザID
     */
    public void setPsTargetUser(String psTargetUser) {
        this.gsPsTargetUser = psTargetUser;
    }

    /**
     * リクエストパラメータより選択中の法人コード取得（自動インジェクション）
     *
     * @param psSelectedComp 選択中の法人コード
     */
    public void setPsSelectedComp(String psSelectedComp) {
        this.gsPsSelectedComp = psSelectedComp;
    }

    /**
     * リクエストパラメータより選択中の組織コード取得（自動インジェクション）
     *
     * @param psSelectedDept 選択中の組織コード
     */
    public void setPsSelectedDept(String psSelectedDept) {
        this.gsPsSelectedDept = psSelectedDept;
    }

    /**
     * リクエストパラメータより基準日取得（自動インジェクション）
     *
     * @param psSecurityDate 基準日
     */
    public void setPsSecurityDate(String psSecurityDate) {
        this.gsPsSecurityDate = psSecurityDate;
    }

    /**
     * リクエストの取得（自動インジェクション）
     *
     */
    public void setRequest() {
        // setPsSecurityDateがDIされないので、リクエストから設定
        setPsSecurityDate(gRequest.getParameter("psSecurityDate"));
    }

    /**
     * 基準日を取得
     *
     * @return
     */
    private String getPsSecurityDate() {
        // PsSecurityDateが取得できないときはシステム日付を返す
        if(this.gsPsSecurityDate == null) {
            return PT_SYSDATE;
        }
        // PsSecurityDateが設定されているときはそれを返す
        else {
            return "TO_DATE('" + SysUtil.escapeQuote(this.gsPsSecurityDate) + "','YYYY/MM/DD') ";
        }
    }

    /**
     * 検索対象範囲条件作成
     *
     * @param   psApplicationURL    アプリケーションURL
     * @param psMode			モード（0:通常モード 1:異動歴ID結合モード）
     */
    public void create(String psApplicationURL, String psMode) {

        /* サイトID取得 */
        String sSiteId = this.gPsSite;

        /* アプリケーションID取得 */
        String sApplicationId;

        if (this.gsPsApp == null || this.gsPsApp.equals("")) {

            /* アプリケーションIDが設定されていない場合 */
            /* アプリケーションID取得（サイトの検索対象範囲の場合は無し） */
            sApplicationId = this.gAppAuthInfoLogic.getAppID(psApplicationURL);
        } else {

            /* アプリケーションIDが設定されている場合 */
            sApplicationId = this.gsPsApp;
        }

        /* ドメインコード取得 */
        String sDomainCode = "";
        /* システムコード取得 */
        String sSystemCode = "";

        if (sApplicationId == null) {
            // アプリケーションIDが取得できなかった場合は、サイト情報より取得
            sDomainCode = this.gAppAuthInfoLogic.getSiteInfo(sSiteId).getDomainId();
            /* システムコード取得 */
            sSystemCode = this.gAppAuthInfoLogic.getSystemCode(sSiteId);
        } else {
            // アプリケーションIDが取得できた場合は、アプリケーション情報より取得
            sDomainCode = this.gAppAuthInfoLogic.getAppInfo(sSiteId, sApplicationId).getDomainId();
            /* システムコード取得 */
            sSystemCode = this.gAppAuthInfoLogic.getSystemCode(sSiteId, sApplicationId);
        }

        // PsSessionパラメータチェック
        if (this.checkSession()) {

            /* グループ判定結果情報(HashMap)を取得 */
            Map<String, List<LoginGroupBO>> maploginGroup
                    = gPsSession.getLoginGroups();

            /* システム毎のグループ判定結果(List)を取得 */
            List<LoginGroupBO> listloginGroup = maploginGroup.get(sSystemCode);

            /* グループ別アプリケーション別検索対象範囲設定の定義Queryを作成 */
            groupAppSearchRangeQuery(
                    listloginGroup, sSiteId, sApplicationId, sDomainCode, sSystemCode, psMode);

        }
    }

    /**
     * 検索対象範囲条件作成(ドメイン指定あり)
     *
     * @param psApplicationURL アプリケーションURL
     * @param psDomainCode ドメインコード
     * @param psMode			モード（0:通常モード 1:異動歴ID結合モード）
     */
    public void create(String psApplicationURL, String psDomainCode, String psMode) {

        /* サイトID取得 */
        String sSiteId = this.gPsSite;

        /* アプリケーションID取得 */
        String sApplicationId;

        if (this.gsPsApp == null || this.gsPsApp.equals("")) {

            /* アプリケーションIDが設定されていない場合 */
            /* アプリケーションID取得（サイトの検索対象範囲の場合は無し） */
            sApplicationId = this.gAppAuthInfoLogic.getAppID(psApplicationURL);
        } else {

            /* アプリケーションIDが設定されている場合 */
            sApplicationId = this.gsPsApp;
        }

        /* システムコード取得 */
        String sSystemCode = "";

        if (sApplicationId == null) {
            /* システムコード取得 */
            sSystemCode = this.gAppAuthInfoLogic.getSystemCode(sSiteId);
        } else {
            /* システムコード取得 */
            sSystemCode = this.gAppAuthInfoLogic.getSystemCode(sSiteId, sApplicationId);
        }

        // PsSessionパラメータチェック
        if (this.checkSession()) {

            /* グループ判定結果情報(HashMap)を取得 */
            Map<String, List<LoginGroupBO>>  maploginGroup
                    =  this.gPsSession.getLoginGroups();

            /* システム毎のグループ判定結果(List)を取得 */
            List<LoginGroupBO> listloginGroup = maploginGroup.get(sSystemCode);

            /* グループ別アプリケーション別検索対象範囲設定の定義Queryを作成 */
            this.groupAppSearchRangeQuery(
                    listloginGroup, sSiteId, sApplicationId, psDomainCode, sSystemCode, psMode);
        }
    }

    private void groupAppSearchRangeQuery(List<LoginGroupBO> pListloginGroup, String psSiteID, String psApplicationId, String psDomainId, String psSystemId, String psMode) {

        this.gConditionFromMap	= MapUtil.newHashMap(true);
        String sCustomerID      = this.gPsSession.getLoginCustomer();   // 顧客コード

        // ログインユーザのグループ分処理を繰り返す。
        List<String> condWhereNecessity	= CollUtil.newLinkedList();
        List<String> condWhereMust		= CollUtil.newLinkedList();
        List<String> condWhereBase		= CollUtil.newLinkedList();
        List<String> condWhereBaseMust  = CollUtil.newLinkedList();

        boolean bSetDefault = false;	// 設定フラグ(false：未設定)
        boolean bAllUsed = false;       // 「すべて」フラグ
        boolean bAnd = false;			// 「AND」フラグ
        int nMaxRitired = 0;			// 退職者検索対象範囲

        StringBuilder sb = new StringBuilder();

        for (Iterator<LoginGroupBO> iteloginGroup = pListloginGroup.iterator(); iteloginGroup.hasNext();) {
            LoginGroupBO loginGroup   = iteloginGroup.next();    // ログイングループ
            String sGroupID         = loginGroup.getGroupCode();       // グループコード
            // 初期化
            sb.delete(0, sb.length());
            // key作成
            if (psApplicationId != null){
                sb.append(sCustomerID).append("_").append(psSystemId).append("_").append(sGroupID).append("_").append(psSiteID).append("_").append(psApplicationId);
            } else {
                sb.append(sCustomerID).append("_").append(psSystemId).append("_").append(sGroupID).append("_").append(psSiteID);
            }
            // 該当グループ毎の条件ID(必要・必須)を取得する
            List<AppSearchRangeInfoEntity> appSearchRangeInfoEntityList = appSearchRangeInfoCache.getAppSearchRangeInfoCache(sb.toString());
            // null判定
            if (appSearchRangeInfoEntityList == null){
                continue;
            }
            // 登録済みの検索対象範囲情報(条件ID)分処理を繰り返す。
            for (Iterator<AppSearchRangeInfoEntity> appSearchRangeInfoEntityIte
                 = appSearchRangeInfoEntityList.iterator(); appSearchRangeInfoEntityIte.hasNext();) {
                AppSearchRangeInfoEntity appSearchRangeInfoEntity = appSearchRangeInfoEntityIte.next();
                String sCondWhere = null;

                // 必要条件定義IDが定義されている場合
                if (appSearchRangeInfoEntity.getHgpCpermnecessity() != null) {
                    // 検索範囲情報(組織、役職)常駐変数より、条件式結合クエリを取得する
                    String sPermissionID = appSearchRangeInfoEntity.getHgpCpermnecessity();
                    String sPermissionQuery = this.getPermissionQuery(sPermissionID, sCustomerID, psDomainId);
                    if (!this.isEmpty(sPermissionQuery)) {
                        condWhereNecessity.add(sPermissionQuery);
                    }
                    bSetDefault = true;
                    // 「すべて」フラグ
                    if (sPermissionID.equals(CODE_0001)){
                        bAllUsed = true;
                    }
                }

                // 必須条件定義IDが定義されている場合
                if (appSearchRangeInfoEntity.getHgpCpermmust() != null) {
                    // 検索範囲情報(組織、役職)常駐変数より、条件式結合クエリを取得する
                    String sPermissionQuery = this.getPermissionQuery(appSearchRangeInfoEntity.getHgpCpermmust(), sCustomerID, psDomainId);
                    if (!this.isEmpty(sPermissionQuery)) {
                        condWhereMust.add(sPermissionQuery);
                    }
                    bSetDefault = true;
                    // 「AND」フラグ
                    bAnd = true;
                }

                // 必要条件定義用基点組織フラグが"1"(有)の場合
                if ((!this.isEmpty(appSearchRangeInfoEntity.getHgpCbasesectionFlagNeed()) &&
                        appSearchRangeInfoEntity.getHgpCbasesectionFlagNeed().equals(BASE_SECTION_TRUE))) {

                    // 条件式作成(基点組織) を条件Where句へ格納
                    sCondWhere = this.createBaseSectionSQL(psSystemId, psDomainId, sGroupID);
                    if (!this.isEmpty(sCondWhere)) {
                        condWhereBase.add(sCondWhere);
                        // 基点組織の場合には結合テーブルに異動歴を含める(組織ドメイン・法人ドメインの場合を除く)
                        this.gConditionFromMap.put(
                                TBL_MAST_ORGANISATION,
                                TBL_MAST_ORGANISATION);
                        if ((!psDomainId.equals(PsConst.DOMAIN_ORGANIZATION) && !psDomainId.equals(PsConst.DOMAIN_COMPANY)) || psMode.equals("1")) {
                            this.gConditionFromMap.put(
                                   TBL_HIST_DESIGNATION,
                                   TBL_HIST_DESIGNATION);
                        }
                        bSetDefault = true;
                    }
                }
                // 必須条件定義用基点組織フラグが"1"(有)の場合
                if (!this.isEmpty(appSearchRangeInfoEntity.getHgpCbasesectionFlagMust()) &&
                        appSearchRangeInfoEntity.getHgpCbasesectionFlagMust().equals(BASE_SECTION_TRUE)) {

                    // 条件式作成(基点組織) を条件Where句へ格納
                    sCondWhere = this.createBaseSectionSQL(psSystemId, psDomainId, sGroupID);
                    if (!this.isEmpty(sCondWhere)) {
                        condWhereBaseMust.add(sCondWhere);
                        // 基点組織の場合には結合テーブルに異動歴を含める(組織ドメイン・法人ドメインの場合を除く)
                        this.gConditionFromMap.put(
                                TBL_MAST_ORGANISATION,
                                TBL_MAST_ORGANISATION);
                        if ((!psDomainId.equals(PsConst.DOMAIN_ORGANIZATION) && !psDomainId.equals(PsConst.DOMAIN_COMPANY)) || psMode.equals("1")) {
                            this.gConditionFromMap.put(
                                    TBL_HIST_DESIGNATION,
                                   TBL_HIST_DESIGNATION);
                        }
                        bSetDefault = true;
                    }
                }
                // 退職者検索対象範囲
                int nPermRetired = 0;
                if (appSearchRangeInfoEntity.getHgpCpermRetired() != null) {
                    nPermRetired = Integer.parseInt(appSearchRangeInfoEntity.getHgpCpermRetired());
                }
                // 大きい方を優先(0:参照しない < 1:自社のみ < 2:すべて)
                if (nPermRetired > nMaxRitired) {
                    nMaxRitired = nPermRetired;
                }
            }
        }

        // 「AND」がある場合は「すべて」を外す
        if (bAnd) { bAllUsed = false; }

        // 退職者検索対象範囲
        this.setCondRetired(nMaxRitired);

        // ▼ #3161 未設定時は、検索クエリではなくフラグを返却するように修正
        this.setCondSetupFlg(bSetDefault);

        // ▼ #2736 未設定時の検索クエリを全権ユーザから制限ユーザへ修正
        // 定義が未設定の場合
        if (!bSetDefault && this.checkRequest(psDomainId)) {
            String sQuery = this.getSetDefault(this.gsPsTargetUser, this.gsPsSelectedComp,  this.gsPsSelectedDept, psDomainId);
            condWhereNecessity.add(sQuery);
        }

        // 定義が未設定の場合には、FROM句とWHERE句(結合条件式)は返却しない
        if (bSetDefault) {

            // ドメインに対応するテーブルIDを取得
            String sDomainTable = this.getDomainToTableId(psDomainId);
            this.gConditionFromMap.remove(sDomainTable);

            // 検索対象範囲条件のFrom句リスト
            List<String> conditionFromList = CollUtil.newLinkedList();
            conditionFromList.add(sDomainTable);
            Set<String> conditionFromSet = gConditionFromMap.keySet();
            for (Iterator<String> conditionFromIte = conditionFromSet.iterator(); conditionFromIte.hasNext();) {
                String sConditionFrom = conditionFromIte.next();
                conditionFromList.add(sConditionFrom);
            }
            this.setFrom(conditionFromList);

            // 結合条件作成用前処理
            // テーブル使用有無
            boolean bDesignation = false;
            boolean bOrganisation = false;
            boolean bPost = false;
            // 置換文字列用通し番号
            int nDesignation = 0;
            int nOrganisation = 0;
            int nPost = 0;
            for (int i = 0; i < conditionFromList.size(); i++) {
                String sConditionFrom = conditionFromList.get(i);
                // 異動歴あり
                if (sConditionFrom.equalsIgnoreCase(TBL_HIST_DESIGNATION)) {
                    bDesignation = true;
                    nDesignation = i;
                }
                // 組織マスタあり
                if (sConditionFrom.equalsIgnoreCase(TBL_MAST_ORGANISATION)) {
                    bOrganisation = true;
                    nOrganisation = i;
                }
                // 役職マスタあり
                if (sConditionFrom.equalsIgnoreCase(TBL_MAST_POST)) {
                    bPost = true;
                    nPost = i;
                }
            }

            /* 検索対象範囲-テーブル結合条件式生成 */
            List<String> queryList = CollUtil.newLinkedList();
            // 戻り値用
            String sQuery = null;
            for (Iterator<String> iterator = conditionFromList.iterator() ; iterator.hasNext();) {

                String sConditionFrom = iterator.next();
                // 組織・法人ドメインの場合はユーザIDを含めずに結合式を作成
                if (sConditionFrom.equals(sDomainTable)) {
                    sQuery = psTableCombinationType.assemble(
                            sConditionFrom, this.atBraceFolder(queryList.size()),
                            null, null,
                            psDomainId.equals(PsConst.DOMAIN_EMPLOYEE));
                } else {
                    sQuery = psTableCombinationType.assemble(
                            sDomainTable, this.atBraceFolder(0),
                            sConditionFrom, this.atBraceFolder(queryList.size()),
                            psDomainId.equals(PsConst.DOMAIN_EMPLOYEE));
                }
                queryList.add(sQuery);
            }
            // 異動歴と組織マスタの組み合わせの場合
            if (bDesignation && bOrganisation) {
                sQuery = psTableCombinationType.assemble(
                        TBL_HIST_DESIGNATION, this.atBraceFolder(nDesignation),
                        TBL_MAST_ORGANISATION, this.atBraceFolder(nOrganisation),
                        psDomainId.equals(PsConst.DOMAIN_EMPLOYEE));
                queryList.add(sQuery);
            }
            // 異動歴と役職マスタの組み合わせの場合
            if (bDesignation && bPost) {
                sQuery = psTableCombinationType.assemble(
                        TBL_HIST_DESIGNATION, this.atBraceFolder(nDesignation),
                        TBL_MAST_POST, this.atBraceFolder(nPost),
                        psDomainId.equals(PsConst.DOMAIN_EMPLOYEE));
                queryList.add(sQuery);
            }
            this.setCombWhere(queryList);
        }

        // 検索対象範囲条件の条件Where句を設定する
        StringBuilder sbQuery = new StringBuilder();
        // 戻り値
        String sQuery = null;
        // 条件の組立
        if(!condWhereNecessity.isEmpty() || !condWhereBase.isEmpty() || !condWhereMust.isEmpty() || !condWhereBaseMust.isEmpty()){
            // まず"("をつける
            sbQuery.append(PT_OPEN_PAR);
            // O R条件の組立
            if(!condWhereNecessity.isEmpty() && !condWhereBase.isEmpty()){
                // "("をつける
                sbQuery.append(PT_OPEN_PAR);
                sbQuery.append(createCondWhere(condWhereNecessity, PT_OR));
                sbQuery.append(PT_OR);
                // O R条件の組立(基点組織)
                sbQuery.append(createCondWhere(condWhereBase, PT_OR));
                // 最後に")"をつける
                sbQuery.append(PT_CLOSE_PAR);
                // AND条件の組立
                sQuery = checkWhereMust(SELECT_MODE_1, sbQuery, condWhereMust, condWhereBaseMust);
            }else if(!condWhereNecessity.isEmpty()){
                // O R条件が
                sbQuery.append(createCondWhere(condWhereNecessity, PT_OR));
                // AND条件の組立
                sQuery = checkWhereMust(SELECT_MODE_1, sbQuery, condWhereMust, condWhereBaseMust);
            }else if(!condWhereBase.isEmpty()){
                // O R条件が基点組織のみ
                sbQuery.append(createCondWhere(condWhereBase, PT_OR));
                // AND条件の組立
                sQuery = checkWhereMust(SELECT_MODE_1, sbQuery, condWhereMust, condWhereBaseMust);
            }else{
                // AND条件の組立
                sQuery = checkWhereMust(SELECT_MODE_2, sbQuery, condWhereMust, condWhereBaseMust);
            }
        } else {
            // 条件の組立がない場合
            sQuery = sbQuery.toString();
        }

        this.setCondWhere(sQuery);
    }

    /**
     * ANDクエリを組み立てて返却します
     *
     * @param psMode               モード選択 yes:(なし、no:()あり
     * @param psbQuery             クエリ
     * @param pcondWhereMust       基点組織以外"and"の場合
     * @param pcondWhereBaseMust   基点組織"and"の場合
     * @return psbQuery.toString() クエリ
     */
    private String checkWhereMust(
            String psMode,
            StringBuilder psbQuery,
            List<String> pcondWhereMust,
            List<String> pcondWhereBaseMust
    ){
        // "orを含める"
        if(psMode.equals(SELECT_MODE_1)){
            // AND条件の組立
            if(!pcondWhereMust.isEmpty() && !pcondWhereBaseMust.isEmpty()){
                psbQuery.append(PT_AND);
                // "("をつける
                psbQuery.append(PT_OPEN_PAR);
                psbQuery.append(createCondWhere(pcondWhereMust, PT_AND));
                psbQuery.append(PT_AND);
                // AND条件の組立(基点組織)
                psbQuery.append(createCondWhere(pcondWhereBaseMust, PT_AND));
                // ")"をつける
                psbQuery.append(PT_CLOSE_PAR);
            }else if(!pcondWhereMust.isEmpty()){
                psbQuery.append(PT_AND);
                psbQuery.append(createCondWhere(pcondWhereMust, PT_AND));
            }else if(!pcondWhereBaseMust.isEmpty()){
                psbQuery.append(PT_AND);
                psbQuery.append(createCondWhere(pcondWhereBaseMust, PT_AND));
            }
        }
        // "andのみ"
        else {
            // AND条件の組立
            if(!pcondWhereMust.isEmpty() && !pcondWhereBaseMust.isEmpty()){
                psbQuery.append(createCondWhere(pcondWhereMust, PT_AND));
                psbQuery.append(PT_AND);
                // AND条件の組立(基点組織)
                psbQuery.append(createCondWhere(pcondWhereBaseMust,PT_AND));
            }else if(!pcondWhereMust.isEmpty()){
                psbQuery.append(createCondWhere(pcondWhereMust, PT_AND));
            }else if(!pcondWhereBaseMust.isEmpty()){
                psbQuery.append(createCondWhere(pcondWhereBaseMust, PT_AND));
            }
        }
        // ")"をつける
        psbQuery.append(PT_CLOSE_PAR);

        return psbQuery.toString();

    }

    private static final String NOTHING_SQL = "";

    /**
     * 指定定義IDのクエリを組み立てて返却します
     * ※ NoDataFoundException が発生した場合、絶対に一致しないSQLを返却
     *
     * @param sPermissionID 定義ID
     * @param sCustomerID 顧客コード
     * @param sDomainId ドメインコード
     * @return String クエリ
     */
    private String getPermissionQuery(String sPermissionID, String sCustomerID, String sDomainId){

        try {
            String sPermissionQuery = this.createPermissionQuery(sPermissionID, sCustomerID, sDomainId);
            if (!this.isEmpty(sPermissionQuery)) {
                return sPermissionQuery;
            }
        } catch (GlobalException e){
            return this.getNoDataPermissionDefs();
        }

        return NOTHING_SQL;
    }

    /**
     * 指定定義IDのクエリを組み立てて返却します
     *
     * @param sPermissionID 定義ID
     * @param sCustomerID 顧客コード
     * @param sDomainId ドメインコード
     * @return String クエリ
     */
    private String createPermissionQuery(String sPermissionID, String sCustomerID, String sDomainId){

        List <SearchRangeInfo> searchRangeInfoList = CollUtil.newLinkedList();

        // 組織・役職指定の場合
        searchRangeInfoList = searchRangeInfoCache.getDataSectionPost(sPermissionID);
        String sQuery = this.setDataSectionPost(
                searchRangeInfoList,
                TBL_HIST_DESIGNATION);

        if (!this.isEmpty(sQuery)) {

            this.gConditionFromMap.put(
                    TBL_HIST_DESIGNATION,
                    TBL_HIST_DESIGNATION
            );

            return sQuery;
        }
        // 全ての場合は別にSQLを組立て
        if (sPermissionID.equals(CODE_0001)){
            sQuery = this.setDataPermissionDefs();
        }else{
            // 条件式指定の場合
            searchRangeInfoList = searchRangeInfoCache.getDataPermissionDefs(sPermissionID);
            sQuery = this.setDataPermissionDefs(searchRangeInfoList, sCustomerID, sDomainId);
        }
        if (!this.isEmpty(sQuery)) {
            return sQuery;
        }

        return new String();
    }

    /**
     * ドメインに対応するテーブルのIDを返却します
     *
     * @param psDomainId ドメインID
     * @return String テーブルID
     */
    private String getDomainToTableId(String psDomainId) {

        String sDomainTable = new String();

        if (psDomainId.equals(PsConst.DOMAIN_EMPLOYEE)) {
            sDomainTable = TBL_MAST_EMPLOYEES;
        } else if (psDomainId.equals(PsConst.DOMAIN_ORGANIZATION)) {
            sDomainTable = TBL_MAST_ORGANISATION;
        } else {
            sDomainTable = TBL_MAST_COMPANY;
        }

        return sDomainTable;
    }

    /**
     * 定義 未設定時のデフォルトクエリ組立て処理
     * 検索対象範囲設定にて、何も定義されていない場合のクエリを組み立てます。
     *
     * @param   psTargetUser	選択ユーザ
     * @param   psTargetCompany 選択法人
     * @param   psTargetSection 選択組織
     * @param   psDomain		ドメインコード
     */
    private String getSetDefault(String psTargetUser, String psTargetCompany, String psTargetSection, String psDomain) {

        StringBuilder sWheres = new StringBuilder();
        // 検索対象の条件を追加
        if (psDomain.equalsIgnoreCase(PsConst.DOMAIN_EMPLOYEE)) {

            // 条件式：従業員ドメイン"01"の場合
            sWheres.append(this.escDBString(this.gPsSession.getLoginUser()) + PT_EQUAL + this.escDBString(psTargetUser));
            sWheres.append(PT_AND + "#HD_USER#" + PT_EQUAL + this.escDBString(psTargetUser));
        } else if (psDomain.equalsIgnoreCase(PsConst.DOMAIN_ORGANIZATION)) {

            // 条件式：組織ドメイン"02"の場合
            List <Designation> designation = this.gPsSession.getLoginDesignation();
            sWheres.append(PT_OPEN_PAR);
            for (int i = 0; i < designation.size(); i++) {
                // 法人コード比較
                sWheres.append(PT_OPEN_PAR + "#MO_COMP#" + PT_EQUAL + this.escDBString(designation.get(i).getCompanyCode()));
                // 組織コード比較
                sWheres.append(PT_AND + "#MO_DEPT#" + PT_EQUAL + this.escDBString(designation.get(i).getSection()) + PT_CLOSE_PAR);

                sWheres.append(PT_OR);
            }
            // 最後のORを取り除く
            String st = sWheres.toString();
            st = st.substring(0, st.length() - PT_OR.length());
            sWheres = new StringBuilder(st + PT_CLOSE_PAR);

        } else {

            // 条件式：法人ドメイン"03"の場合
            List < Designation > designation = this.gPsSession.getLoginDesignation();
            sWheres.append(PT_OPEN_PAR);

            for (Designation value : designation) {
                // 法人コード比較
                sWheres.append(PT_OPEN_PAR + "#MC_COMP#" + PT_EQUAL + this.escDBString(value.getCompanyCode()) + PT_CLOSE_PAR);

                sWheres.append(PT_OR);
            }
            // 最後のカンマを取り除く
            String st = sWheres.toString();
            st = st.substring(0, st.length() - PT_OR.length());
            sWheres = new StringBuilder(st + PT_CLOSE_PAR);
        }
        return sWheres.toString();
    }

    /**
     * 条件Where句を組み立てて返却
     *
     * 編集用の内部変数(各条件Where句リスト)より、条件Where句を取得し
     * 条件Where句用のクエリを組み立てて設定します。
     *
     */
    private String createCondWhere(List<String> conditionList, String sConnective) {

        String sQuery = new String();

        // 条件が存在する場合にのみ組み立てを行う
        if (!conditionList.isEmpty()) {

            StringBuilder sbQuery = new StringBuilder();

            for (Iterator<String> conditionIterator = conditionList.iterator(); conditionIterator.hasNext();) {

                String sCondition = conditionIterator.next();

                if (!sCondition.equals("")) {

                    sbQuery.append(PT_OPEN_PAR + sCondition + PT_CLOSE_PAR);

                    if (conditionIterator.hasNext()) {

                        sbQuery.append(sConnective);

                    }
                }
            }
            // 条件が2件以上存在する場合にはカッコをつける
            sQuery = sbQuery.toString();
            if (conditionList.size() > 1) {

                sQuery = PT_OPEN_PAR + sQuery + PT_CLOSE_PAR;

            }
        }
        return sQuery;
    }

    /**
     * 検索対象範囲条件作成(組織、役職)
     *
     * 検索対象範囲条件定義マスタ(組織、役職)に設定されている定義内容を元に、
     * 検索対象範囲条件を作成します。(作成した各データは内部変数に格納されます)
     *
     * @param   psTableID テーブルID
     */
    private String setDataSectionPost(List<SearchRangeInfo> searchRangeInfoList, String psTableID) {

        // 値が取得できなかった場合は、処理を抜ける
        if (searchRangeInfoList == null) {
            return "";
        }

        // ▼#2882:検索範囲情報(組織、役職)も、常駐変数分クエリを組立てる
        // 対象テーブル情報(組織、役職)分処理を繰り返す。
        StringBuilder sbWhere = new StringBuilder();
        for (int i = 0; i < searchRangeInfoList.size(); i++) {

            SearchRangeInfo searchRangeInfo = searchRangeInfoList.get(i);

            String sWhere = this.createSectionPostMappingSQL(searchRangeInfo, psTableID);
            sbWhere.append(sWhere);

            // データが複数存在する場合は、ORで結合する
            if (sbWhere.toString().length() > 0) {
                sbWhere.append(PT_OR);
            }
        }

        return this.checkWhereList(sbWhere.toString());

    }
    /**
     * 検索対象範囲条件作成(全て)
     *
     * 検索対象範囲条件定義マスタ(全てを抽出するクエリを組み立てます)
     *
     * @return sbTempCond 全件抽出クエリ
     */
    private String setDataPermissionDefs() {

        // 対象テーブル情報(条件式)分処理を繰り返す。
        StringBuilder sbTempCond = new StringBuilder();
        // 全件抽出クエリ作成
        sbTempCond.append(PT_OPEN_PAR);
        sbTempCond.append(ALLSQL);
        sbTempCond.append(PT_CLOSE_PAR);

        return sbTempCond.toString();
    }

    /**
     * 検索対象範囲条件作成(0件)
     *
     * 検索対象範囲条件定義マスタ(一件も合致しないクエリを組み立てます)
     *
     * @return sbSQL 0件抽出クエリ
     */
    private String getNoDataPermissionDefs() {

        // 対象テーブル情報(条件式)分処理を繰り返す。
        StringBuilder sbSQL = new StringBuilder();
        // 0件抽出クエリ作成
        sbSQL.append(PT_OPEN_PAR);
        sbSQL.append(NOMATCHSQL);
        sbSQL.append(PT_CLOSE_PAR);

        return sbSQL.toString();
    }

    /**
     * 検索対象範囲条件作成(条件式)
     *
     * 検索対象範囲条件定義マスタ(条件式)に設定されている定義内容を元に、
     * 検索対象範囲条件を作成します。(作成した各データは内部変数に格納されます)
     *
     * @param   searchRangeInfoList        定義ID
     * @param   psCustomerId    顧客コード
     * @param   psDomain        ドメインコード
     */
    private String setDataPermissionDefs(List<SearchRangeInfo> searchRangeInfoList, String psCustomerId, String psDomain) {

        // 値が取得できなかった場合は、処理を抜ける
        if (searchRangeInfoList == null || searchRangeInfoList.isEmpty()) {
            return "";
        }

        // 対象テーブル情報(条件式)分処理を繰り返す。
        StringBuilder sbTempCond = new StringBuilder();
        for (int i = 0; i < searchRangeInfoList.size(); i++) {

            // 検索対象範囲条件定義マスタ(条件式)より、条件式クエリ情報を取得する
            SearchRangeInfo searchRangeInfo = searchRangeInfoList.get(i);
            String sMdpdCtableid = searchRangeInfo.getMdpdCtableid();
            String sMdCmastertblname = searchRangeInfo.getMdCmastertblname();

            // FROM句に対象のテーブルを追加
            this.gConditionFromMap.put(sMdpdCtableid, sMdpdCtableid);

            // データディクショナリ情報(各テーブルのカラムID)を取得する
            TableCombinationType tableCombinationType
                    = tableCombinationTypeCache.getTableCombinationType(sMdpdCtableid);
            if (tableCombinationType == null) {
                break;
            }

            String sMasterRecodeQuery = new String();
            // マスタ参照カラムの場合
            if (searchRangeInfo.getMdpdCmyflag().equals(MY_FLG2) || searchRangeInfo.getMdpdCmyflag().equals(MY_FLG3)) {
                // プリセット：MY_FLG = 2 / MY_FLG = 3
                sMasterRecodeQuery = this.createPreSetSQL(searchRangeInfo);
            } else if (!this.isEmpty(sMdCmastertblname) &&
                    (	sMdCmastertblname.equals(QCOMPANY) ||
                            sMdCmastertblname.equals(QSECTION) ||
                            sMdCmastertblname.equals(QPOST) ||
                            sMdCmastertblname.equals(QPOSTNUM)		) ) {
                sMasterRecodeQuery = this.createMasterSpecialMeaningSQL(searchRangeInfo, psDomain);
            } else {
                sMasterRecodeQuery = this.createNormalSQL(tableCombinationType, searchRangeInfo, psDomain);
            }
            sbTempCond.append(sMasterRecodeQuery);

        }

        return sbTempCond.toString();
    }
    /**
     * 名称マスタ区分が特別な意味を持つ場合のSQLを組み立てます。
     *
     * @param searchRangeInfo 検索範囲取得DTOクラス
     * @param psDomain ドメイン
     * @return String SQL
     */
    private String createMasterSpecialMeaningSQL(SearchRangeInfo searchRangeInfo, String psDomain) {

        // 関連テーブルの情報を取得
        String sMdpdCtableid = searchRangeInfo.getMdpdCtableid();
        String sMdCmastertblname = searchRangeInfo.getMdCmastertblname();
        TableCombinationType tableCombinationType = tableCombinationTypeCache.getTableCombinationType(sMdpdCtableid);

        // 返却値用
        StringBuilder sbQuery = new StringBuilder();

        // [AND/OR] [NOT] [(]
        sbQuery.append(PT_SPACE);
        sbQuery.append(this.toBlank(searchRangeInfo.getMdpdCandor()));

        if (searchRangeInfo.getMdpdCoperator().equals("!=") || searchRangeInfo.getMdpdCoperator().equals("<>")) {
            sbQuery.append(" NOT ");
        }

        sbQuery.append(PT_SPACE);
        sbQuery.append(this.toBlank(searchRangeInfo.getMdpdCopenedparenthsis()));

        // 処理対象のデータを準備
        List<Designation> designationList = this.createMasterSpecialMeaningData(searchRangeInfo);

        sbQuery.append(PT_OPEN_PAR);
        for (Iterator<Designation> designationIte = designationList.iterator(); designationIte.hasNext();) {

            Designation designation = designationIte.next();

            sbQuery.append(PT_OPEN_PAR);
            if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QCOMPANY)) {
                // 法人：MAC_CCOMPANYID_CK [=] ''
                sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                sbQuery.append(PT_SPACE);
                sbQuery.append(searchRangeInfo.getMdpdCoperator());
                sbQuery.append(PT_SPACE);
                sbQuery.append(this.escDBString(this.toBlank(designation.getCompanyCode())));
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QSECTION)) {
                // [IS NULL][IS NOT NULL]時
                if(searchRangeInfo.getMdpdCoperator().equals(PT_ISNULL) ||
                        searchRangeInfo.getMdpdCoperator().equals(PT_ISNOTNULL)){
                    sbQuery.append(tableCombinationType.getSectionIdColumnName());
                    sbQuery.append(PT_SPACE);
                    sbQuery.append(searchRangeInfo.getMdpdCoperator());
                }else{
                    // 法人：MO_CCOMPANYID_CK_FK = '' AND
                    sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                    sbQuery.append(PT_EQUAL);
                    sbQuery.append(this.escDBString(this.toBlank(designation.getCompanyCode())));
                    sbQuery.append(PT_AND);
                    // 組織：MO_CSECTIONID_CK = ''
                    sbQuery.append(tableCombinationType.getSectionIdColumnName());
                    sbQuery.append(PT_EQUAL);
                    sbQuery.append(this.escDBString(this.toBlank(designation.getSection())));
                }
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOST)) {
                // [IS NULL][IS NOT NULL]時
                if(searchRangeInfo.getMdpdCoperator().equals(PT_ISNULL) ||
                        searchRangeInfo.getMdpdCoperator().equals(PT_ISNOTNULL)){
                    sbQuery.append(tableCombinationType.getPostIdColumnName());
                    sbQuery.append(PT_SPACE);
                    sbQuery.append(searchRangeInfo.getMdpdCoperator());
                }else{
                    // 法人：MO_CCOMPANYID_CK_FK = '' AND
                    sbQuery.append(tableCombinationType.getCompanyIdColumnName());
                    sbQuery.append(PT_EQUAL);
                    sbQuery.append(this.escDBString(this.toBlank(designation.getCompanyCode())));
                    sbQuery.append(PT_AND);

                    // 役職：MAP_NWEIGHTAGE [=] ''
                    if (searchRangeInfo.getMdpdCoperator().equals("=")){
                        // 役職に対して[=][!=]の比較に対しては役職コードにて行う
                        sbQuery.append(tableCombinationType.getPostIdColumnName() + PT_SPACE);
                        sbQuery.append(searchRangeInfo.getMdpdCoperator() + PT_SPACE);
                        sbQuery.append(this.escDBString(this.toBlank(designation.getPostCode())) + PT_SPACE);
                    }else if (searchRangeInfo.getMdpdCoperator().equals("!=") ||
                            searchRangeInfo.getMdpdCoperator().equals("<>")) {
                        // "!="か"<>"の場合は条件が反転するので"="に直す
                        sbQuery.append(tableCombinationType.getPostIdColumnName() + PT_SPACE);
                        sbQuery.append("=" + PT_SPACE);
                        sbQuery.append(this.escDBString(this.toBlank(designation.getPostCode())) + PT_SPACE);
                    } else if (searchRangeInfo.getMdpdCoperator().equals(">")  ||
                            searchRangeInfo.getMdpdCoperator().equals(">=") ||
                            searchRangeInfo.getMdpdCoperator().equals("<")  ||
                            searchRangeInfo.getMdpdCoperator().equals("<=") ) {
                        // 役職に対して[>][>=][<=][<]の比較に対しては役職順位にて行う
                        sbQuery.append(tableCombinationType.getPostIdColumnName());
                        sbQuery.append(PT_IN);
                        sbQuery.append(" ( SELECT MP1.MAP_CPOSTID_CK ");
                        sbQuery.append("FROM MAST_POST MP1, MAST_POST MP2 ");
                        sbQuery.append("WHERE MP1.MAP_CCUSTOMERID_CK_FK = " + this.escDBString(this.toBlank(designation.getCustomerCode())) + PT_SPACE);
                        sbQuery.append("AND MP1.MAP_CCOMPANYID_CK_FK = " + this.escDBString(this.toBlank(designation.getCompanyCode())) + PT_SPACE);
                        sbQuery.append("AND MP1.MAP_CLANGUAGE = 'ja' ");
                        sbQuery.append("AND MP1.MAP_DSTART <= ").append(this.getPsSecurityDate());
                        sbQuery.append("AND MP1.MAP_DEND >= ").append(this.getPsSecurityDate());
                        // 注意：「○○職以上の人」との表現にて、数値的な「50以上」と意味が反転するので右辺と左辺を反対に記述
                        sbQuery.append("AND MP2.MAP_NWEIGHTAGE " + searchRangeInfo.getMdpdCoperator() + " MP1.MAP_NWEIGHTAGE ");
                        sbQuery.append("AND MP2.MAP_CCUSTOMERID_CK_FK = MP1.MAP_CCUSTOMERID_CK_FK ");
                        sbQuery.append("AND MP2.MAP_CCOMPANYID_CK_FK = MP1.MAP_CCOMPANYID_CK_FK ");
                        sbQuery.append("AND MP2.MAP_CPOSTID_CK = " + this.escDBString(this.toBlank(designation.getPostCode())) + PT_SPACE);
                        sbQuery.append("AND MP2.MAP_CLANGUAGE = MP1.MAP_CLANGUAGE ");
                        sbQuery.append("AND MP2.MAP_DSTART <= ").append(this.getPsSecurityDate());
                        sbQuery.append("AND MP2.MAP_DEND >= ").append(this.getPsSecurityDate());
                        sbQuery.append(") ");
                    }
                }
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOSTNUM)) {
                // [IS NULL][IS NOT NULL]時
                if(searchRangeInfo.getMdpdCoperator().equals(PT_ISNULL) ||
                        searchRangeInfo.getMdpdCoperator().equals(PT_ISNOTNULL)){
                    sbQuery.append(tableCombinationType.getPostIdColumnName());
                    sbQuery.append(PT_SPACE);
                    sbQuery.append(searchRangeInfo.getMdpdCoperator());
                }else{
                    // 役職順位：MAP_NWEIGHTAGE [=] [1]
                    sbQuery.append(tableCombinationType.getPostIdColumnName());
                    sbQuery.append(PT_IN);
                    sbQuery.append(" ( SELECT MAP_CPOSTID_CK ");
                    sbQuery.append("FROM MAST_POST ");
                    sbQuery.append("WHERE MAP_CCUSTOMERID_CK_FK = " + this.escDBString(this.toBlank(designation.getCustomerCode())) + PT_SPACE);
                    sbQuery.append("AND MAP_CCOMPANYID_CK_FK = " + this.escDBString(this.toBlank(designation.getCompanyCode())) + PT_SPACE);
                    sbQuery.append("AND MAP_CLANGUAGE = 'ja' ");
                    sbQuery.append("AND MAP_DSTART <= ").append(this.getPsSecurityDate());
                    sbQuery.append("AND MAP_DEND >= ").append(this.getPsSecurityDate());
                    // チェック用退避
                    String sCoperator = searchRangeInfo.getMdpdCoperator();
                    // "!="か"<>"の場合は条件が反転するので"="に直す
                    if (sCoperator.equals("!=") || sCoperator.equals("<>")){
                        // 注意：「○○職以上の人」との表現にて、数値的な「50以上」と意味が反転するので右辺と左辺を反対に記述
                        sbQuery.append("AND " + designation.getPostRank() + PT_SPACE + "=" + " MAP_NWEIGHTAGE ");
                    }else{
                        // 注意：「○○職以上の人」との表現にて、数値的な「50以上」と意味が反転するので右辺と左辺を反対に記述
                        sbQuery.append("AND " + designation.getPostRank() + PT_SPACE + sCoperator + " MAP_NWEIGHTAGE ");
                    }
                    sbQuery.append(") ");
                }
            }
            sbQuery.append(PT_CLOSE_PAR);
            if (designationIte.hasNext()) {
                sbQuery.append(PT_OR);
            }
        }
        // )
        sbQuery.append(PT_CLOSE_PAR);

        // )
        sbQuery.append(this.toBlank(searchRangeInfo.getMdpdCclosedparenthsis()));

        return sbQuery.toString();
    }
    /**
     * 名称マスタ区分が特別な意味を持つ場合の検索対象データの取得
     *
     * @param searchRangeInfo 検索範囲取得DTOクラス
     * @return String SQL
     */
    private List<Designation> createMasterSpecialMeaningData(SearchRangeInfo searchRangeInfo) {

        List<Designation> designationList = new LinkedList<Designation>();

        String sMdCmastertblname = searchRangeInfo.getMdCmastertblname();
        if (searchRangeInfo.getMdpdCmyflag().equals(MY_FLG0)) {
            /** 指定値 */
            if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QCOMPANY)) {
                Designation designation = new Designation();
                designation.setCustomerCode(this.gPsSession.getLoginCustomer());	// 顧客コード
                designation.setCompanyCode(searchRangeInfo.getMdpdCcompanyid());	// 法人コード
                designationList.add(designation);
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QSECTION)) {
                Designation designation = new Designation();
                designation.setCustomerCode(this.gPsSession.getLoginCustomer());	// 顧客コード
                designation.setCompanyCode(searchRangeInfo.getMdpdCcompanyid());	// 法人コード
                designation.setSection(this.getDetailId(searchRangeInfo.getMdpdCvalue()));			// 組織コード
                designationList.add(designation);
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOST)) {
                // 指定法人に対する役職コードが指定されるので、ログインユーザの自所属内の役職として展開
                for (Iterator<Designation> designationIte = this.gPsSession.getLoginDesignation().iterator(); designationIte.hasNext();) {
                    Designation designation = designationIte.next();
                    // 初期化
                    Designation newDesignation = new Designation();
                    // nullチェック
                    if (searchRangeInfo.getMdpdCcompanyid() != null){
                        if (this.gPsSession.getLoginCustomer().equals(designation.getCustomerCode()) &&
                                searchRangeInfo.getMdpdCcompanyid().equals(designation.getCompanyCode())) {
                            newDesignation.setCustomerCode(this.gPsSession.getLoginCustomer());	// 顧客コード
                            newDesignation.setCompanyCode(searchRangeInfo.getMdpdCcompanyid());	// 法人コード
                            newDesignation.setSection(designation.getSection());				// 組織コード
                            newDesignation.setPostCode(this.getDetailId(searchRangeInfo.getMdpdCvalue()));		// 役職コード
                        }
                    }
                    // [IS NULL][IS NOT NULL]の時は空のまま追加(SQL組込時に吸収する)
                    designationList.add(newDesignation);
                }
            } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOSTNUM)) {
                // 初期化
                Designation designation = new Designation();
                // nullチェック
                if (searchRangeInfo.getMdpdCcompanyid() != null && searchRangeInfo.getMdpdCvalue() != null){
                    designation.setCustomerCode(this.gPsSession.getLoginCustomer());	// 顧客コード
                    designation.setCompanyCode(searchRangeInfo.getMdpdCcompanyid());	// 法人コード
                    designation.setPostRank(Integer.parseInt(this.getDetailId(searchRangeInfo.getMdpdCvalue())));	// 役職順位
                }
                // [IS NULL][IS NOT NULL]の時は空のまま追加(SQL組込時に吸収する)
                designationList.add(designation);
            }
        } else if (searchRangeInfo.getMdpdCmyflag().equals(MY_FLG1)) {
            /** 検索者の値：異動歴 */
            designationList = this.gPsSession.getLoginDesignation();
        }
        // 条件式での指定値の情報および異動歴に対して「上位」「下位」の表現に対する対象を求める
        // （クエリで回避出来ない法人および組織の上位取得部分はここで吸収しておく）
        if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QCOMPANY)) {
            /** 法人 */
        } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QSECTION)) {
            /** 組織 */
            List<Designation> newDesignationList = new LinkedList<Designation>();

            // 対象リスト(指定値および異動歴)毎のLOOP
            for (Iterator<Designation> designationIte = designationList.iterator(); designationIte.hasNext();) {

                Designation designation = designationIte.next();

                List<String> tergetSectionList = new LinkedList<String>();
                if (searchRangeInfo.getMdpdCoperator().equals(">") || searchRangeInfo.getMdpdCoperator().equals(">=")) {
                    // 上位情報
                    tergetSectionList = sysInfoBusiness.getUpperSectionListDeptForSQL(designation.getCustomerCode(),
                            designation.getCompanyCode(), designation.getSection(), this.getPsSecurityDate4DateType());
                } else if (searchRangeInfo.getMdpdCoperator().equals("<") || searchRangeInfo.getMdpdCoperator().equals("<=")) {
                    // 下位情報
                    tergetSectionList = sysInfoBusiness.getLowerSectionListDeptForSQL(designation.getCustomerCode(),
                            designation.getCompanyCode(), designation.getSection(), this.getPsSecurityDate4DateType());
                }

                // 取得した上位組織を処理対象のリストに反映
                if (tergetSectionList != null && !tergetSectionList.isEmpty()) {
                    for (Iterator<String> tergetSectionIte = tergetSectionList.iterator(); tergetSectionIte.hasNext();) {
                        // セクションＩＤ待避
                        String sSection = tergetSectionIte.next();
                        // TODO:上位検索で複数件取得している場合、一件目が""で取得される為チェックではじく
                        if (!sSection.equals("")){
                            // [>][<]の時、自組織は含めない
                            if (!((searchRangeInfo.getMdpdCoperator().equals(">") || searchRangeInfo.getMdpdCoperator().equals("<") ) &&
                                    sSection.equals(designation.getSection()))){
                                // 元になった異動歴情報に対象組織を反映
                                Designation tergetDesignation = new Designation();
                                tergetDesignation.setCustomerCode(designation.getCustomerCode());
                                tergetDesignation.setCompanyCode(designation.getCompanyCode());
                                tergetDesignation.setSection(sSection);
                                // 新たな対象組織リストを作成
                                newDesignationList.add(tergetDesignation);
                            }
                        }
                    }
                }
                // [>=][<=]の場合は基点部分も対象に含める
                if (searchRangeInfo.getMdpdCoperator().equals(">=") || searchRangeInfo.getMdpdCoperator().equals("<=")) {
                    newDesignationList.add(designation);
                }
                // [=][<>][IN]の時は自組織のみ追加
                else if (searchRangeInfo.getMdpdCoperator().equals("=") || searchRangeInfo.getMdpdCoperator().equals("<>") ||
                        searchRangeInfo.getMdpdCoperator().equals("IN")) {
                    newDesignationList.add(designation);
                }
                // [IS NULL][IS NOT NULL]の時は自組織のみ追加(SQL組込時に吸収する)
                else if (searchRangeInfo.getMdpdCoperator().equals(PT_ISNULL) ||
                        searchRangeInfo.getMdpdCoperator().equals(PT_ISNOTNULL)) {
                    newDesignationList.add(designation);
                }
                // [>][<]の場合でからの場合は追加
                if ((searchRangeInfo.getMdpdCoperator().equals(">") || searchRangeInfo.getMdpdCoperator().equals("<"))
                        && newDesignationList.size() == 0) {
                    // 一件も取得できない場合にからで値を作成しておく、まったく値を設定しないとSQL作成時エラー発生
                    Designation tergetNullDesignation = new Designation();
                    tergetNullDesignation.setCustomerCode("");
                    tergetNullDesignation.setCompanyCode("");
                    tergetNullDesignation.setSection("");
                    // 新たな対象組織リストを作成
                    newDesignationList.add(tergetNullDesignation);
                }
            }
            designationList = newDesignationList;
        } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOST)) {
            /** 役職 */
        } else if (!this.isEmpty(sMdCmastertblname) && sMdCmastertblname.equals(QPOSTNUM)) {
            /** 役職順位 */
        }

        return designationList;
    }

    /**
     * テーブル重複(妥当性)チェック
     *
     * 検索対象範囲条件のFrom句リストに、既に
     * 同じテーブルが登録されているかどうかチェックします。
     * また、各ドメインごとの固定テーブルかどうかチェックします。
     *
     * @param   plFrom  返却テーブルリスト
     * @param   psTable 対象テーブル
     * @return  boolean チェック結果
     */
    private boolean checkDuplicate(List<String> plFrom, String psTable) {

        for (int j = 0; j < plFrom.size(); j++) {

            // 同じテーブルが存在する場合は追加しない
            if (plFrom.get(j).equals(psTable)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 条件式作成(基点組織)
     *
     * グループ別基点組織マスタを検索し、
     * 異動歴または組織ツリーマスタとの結合式を作成します。
     *
     * @param   psSystemId  システムコード
     * @param   psDomainId  ドメインコード
     * @param   psGroupId   グループID
     *
     * @return  String      (基点組織設定時の)条件式
     *
     */
    String createBaseSectionSQL(String psSystemId, String psDomainId, String psGroupId) {

        // セッション情報(基点組織)より、システム毎の基点組織情報(グループ毎)を取得
        String sSecurityDate = this.gsPsSecurityDate;
        if (sSecurityDate == null) {
            sSecurityDate = SysUtil.transDateToString(new Date());
        }
        BaseSectionBO bsi = baseSectionBusiness.getBaseSection(sSecurityDate);
        Map<String, Map<String, String>> baseSectionListMapMap = bsi.getHmGroup();
        Map<String, String> baseSectionListMap = baseSectionListMapMap.get(psSystemId);

        if (!isBaseSection(psGroupId, baseSectionListMapMap, baseSectionListMap)){
            return "";
        }

        StringBuilder sbWork = new StringBuilder();
        // 共通条件を追加する
        sbWork.append(PT_OPEN_PAR);
        sbWork.append(subCreateBaseSectionSQL(psDomainId));
        sbWork.append(PT_OPEN_PAR);

        // テーブル：組織ツリーマスタ，異動歴
        // カラムID：組織階層コード
        StringTokenizer stringTokenizer = new StringTokenizer(this.getLoginLayeredBaseSection(psGroupId, baseSectionListMap), "!");
        int nCount = 0;
        // １つの法人に複数の基点組織が設定されている場合
        while (stringTokenizer.hasMoreTokens()) {
            if(nCount > 0){
                sbWork.append(PT_OR);
            }
            sbWork.append(this.atBraceFolderDot(TBL_MAST_ORGANISATION));
            sbWork.append(COL_MO_LAYERED_ID);
            sbWork.append(PT_EQUAL);
            sbWork.append(PT_APO);
            sbWork.append(stringTokenizer.nextToken().trim());
            sbWork.append(PT_APO);
            nCount++;
        }

        // 閉じカッコ
        sbWork.append(PT_CLOSE_PAR);
        sbWork.append(PT_CLOSE_PAR);

        // 結合式の妥当性チェック
        String sResult = new String();
        sResult = sbWork.toString();
        sResult = this.checkWhereList(sResult);

        return sResult;
    }

    /**
     * 基点組織が設定されているかチェックを行う
     *
     * グループ別基点組織マスタを検索し、基点組織が設定されているか判定を行います。
     *
     * @param   psGroupId   グループID
     * @param   baseSectionListMapMap   システム毎の基点組織情報(グループ単位)
     * @param   baseSectionListMap      基点組織(グループ単位)
     *
     * @return  Boolean     設定されている:true/設定されていない:false
     *
     */
    private Boolean isBaseSection(String psGroupId,
                                  Map<String,Map<String, String>> baseSectionListMapMap,
                                  Map<String, String> baseSectionListMap){

        // 基点組織(システム単位)が設定されていない場合は、処理を抜ける
        if (baseSectionListMapMap.size() < 1) {
            return false;
        }

        // 基点組織(グループ単位)が設定されていない場合は、処理を抜ける
        if (baseSectionListMap.size() < 1) {
            return false;
        }

        // 基点組織が取得できない場合は処理を抜ける
        if (baseSectionListMap.get(psGroupId) == null || "".equals(baseSectionListMap.get(psGroupId))){
            return false;
        }

        // 基点組織情報が空の場合は、処理を抜ける(通常はあり得ないデータなので)
        if ("".equals(this.getLoginLayeredBaseSection(psGroupId, baseSectionListMap))) {
            return false;
        }

        return true;
    }

    /**
     * 基点組織情報(組織階層コード)を返却する
     *
     * セッション情報から該当の基点組織情報(組織階層コード)を抽出し返却する
     * 存在しない場合は「""」を返却する
     *
     * @param   psGroupId   グループID
     * @param   baseSectionListMap      基点組織(グループ単位)
     *
     * @return  String
     *
     */
    private String getLoginLayeredBaseSection(String psGroupId, Map<String, String> baseSectionListMap){
        try{
            return baseSectionListMap.get(psGroupId);
        } catch(NullPointerException e){
            return "";
        }
    }

    /**
     * 従業員ドメイン時の条件式作成
     *
     * 従業員ドメインの条件式作成に必要なSQLを作成し返却します。
     *
     * @param   psDomainId  ドメインコード
     *
     * @return  String      (基点組織設定時の)条件式
     *
     */
    private String subCreateBaseSectionSQL(String psDomainId){

        StringBuilder sbWork = new StringBuilder("");

        // 組織ドメイン・法人ドメインの場合は、共通の結合条件は不要
        if (!PsConst.DOMAIN_ORGANIZATION.equals(psDomainId) && !PsConst.DOMAIN_COMPANY.equals(psDomainId)) {
            sbWork.append(PT_SIGN);
            sbWork.append(TBL_MAST_ORGANISATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("MO_DSTART");
            sbWork.append(PT_LESSER);
            sbWork.append(getPsSecurityDate());
            sbWork.append(PT_AND);

            sbWork.append(PT_SIGN);
            sbWork.append(TBL_MAST_ORGANISATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("MO_DEND");
            sbWork.append(PT_GREATER);
            sbWork.append(getPsSecurityDate());
            sbWork.append(PT_AND);

            sbWork.append(PT_SIGN);
            sbWork.append(TBL_MAST_ORGANISATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("MO_CLANGUAGE");
            sbWork.append(PT_EQUAL);
            sbWork.append("'ja'");
            sbWork.append(PT_AND);

            sbWork.append(PT_SIGN);
            sbWork.append(TBL_HIST_DESIGNATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("HD_CCOMPANYID_CK");
            sbWork.append(PT_EQUAL);
            sbWork.append(PT_SIGN);
            sbWork.append(TBL_MAST_ORGANISATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("MO_CCOMPANYID_CK_FK");
            sbWork.append(PT_AND);

            sbWork.append(PT_SIGN);
            sbWork.append(TBL_HIST_DESIGNATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("HD_CSECTIONID_FK");
            sbWork.append(PT_EQUAL);
            sbWork.append(PT_SIGN);
            sbWork.append(TBL_MAST_ORGANISATION);
            sbWork.append(PT_SIGN);
            sbWork.append(PT_DOD);
            sbWork.append("MO_CSECTIONID_CK");
            sbWork.append(PT_AND);
        }

        return sbWork.toString();

    }

    /**
     *
     * 検索対象範囲条件定義マスタ(組織、役職)で定義
     *
     * 定義区分に応じて異動歴との条件式を作成します。
     *
     * @param searchRangeInfo 検索範囲取得DTOクラス
     * @param psTableID テーブルID
     * @return String 異動歴との条件式
     */
    private String createSectionPostMappingSQL(SearchRangeInfo searchRangeInfo, String psTableID) {

        String sTypeid		= searchRangeInfo.getMdspCtypeid();
        String sCompanyId	= searchRangeInfo.getMdspCcompanyid();
        String sSectionId	= searchRangeInfo.getMdspCsectionid();
        String sPostId		= searchRangeInfo.getMdspCpostid();
        String sEmployeeId	= searchRangeInfo.getMdspCemployeeid();

        // テーブル情報を取得
        TableCombinationType tableCombinationType
                = tableCombinationTypeCache.getTableCombinationType(psTableID);
        String sCompanyIdColumnName	= tableCombinationType.getCompanyIdColumnName();
        String sSectionIdColumnName	= tableCombinationType.getSectionIdColumnName();
        String sPostIdColumnName	= tableCombinationType.getPostIdColumnName();
        String sEmployeeIdColumnName= tableCombinationType.getEmployeeIdColumnName();

        StringBuilder sbCombWhereCol = new StringBuilder();

        // カラムID：法人コード
        if (!this.isEmpty(sCompanyIdColumnName) && !this.isEmpty(sCompanyId)) {
            sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
            sbCombWhereCol.append(sCompanyIdColumnName);
            sbCombWhereCol.append(PT_EQUAL);
            sbCombWhereCol.append(this.escDBString(sCompanyId));
            // 05：カラムID：所属長
            if (sTypeid.equals("05") && !this.isEmpty(sSectionIdColumnName) && !this.isEmpty(sSectionId)) {
                sbCombWhereCol.append(PT_AND);
                sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                sbCombWhereCol.append(sSectionIdColumnName);
                sbCombWhereCol.append(PT_EQUAL);
                sbCombWhereCol.append(this.escDBString(sSectionId));
                sbCombWhereCol.append(PT_AND);
                sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                sbCombWhereCol.append(COL_HD_BOSSORNOT);
                sbCombWhereCol.append(PT_EQUAL);
                sbCombWhereCol.append(BOSS_FLG);
                // 06：カラムID：役職コード
            } else if (sTypeid.equals("06") && !this.isEmpty(sPostIdColumnName) && !this.isEmpty(sPostId)) {
                sbCombWhereCol.append(PT_AND);
                sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                sbCombWhereCol.append(sPostIdColumnName);
                sbCombWhereCol.append(PT_EQUAL);
                sbCombWhereCol.append(this.escDBString(sPostId));
                // 07：カラムID：社員番号
            } else if (sTypeid.equals("07") && !this.isEmpty(sEmployeeIdColumnName) && !this.isEmpty(sEmployeeId)) {
                sbCombWhereCol.append(PT_AND);
                sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                sbCombWhereCol.append(sEmployeeIdColumnName);
                sbCombWhereCol.append(PT_EQUAL);
                sbCombWhereCol.append(this.escDBString(sEmployeeId));
                // 02 03 04：カラムID：組織コード
            } else if (!this.isEmpty(sSectionIdColumnName) && !this.isEmpty(sSectionId)) {
                sbCombWhereCol.append(PT_AND);
                sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                sbCombWhereCol.append(sSectionIdColumnName);
                sbCombWhereCol.append(PT_EQUAL);
                sbCombWhereCol.append(this.escDBString(sSectionId));
                // 03：カラムID：役職コード
                if (sTypeid.equals("03") && !this.isEmpty(sPostIdColumnName) && !this.isEmpty(sPostId)) {
                    sbCombWhereCol.append(PT_AND);
                    sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                    sbCombWhereCol.append(sPostIdColumnName);
                    sbCombWhereCol.append(PT_EQUAL);
                    sbCombWhereCol.append(this.escDBString(sPostId));
                    // 04：カラムID：社員番号
                } else if (sTypeid.equals("04") && !this.isEmpty(sEmployeeIdColumnName) && !this.isEmpty(sEmployeeId)) {
                    sbCombWhereCol.append(PT_AND);
                    sbCombWhereCol.append(this.atBraceFolderDot(psTableID));
                    sbCombWhereCol.append(sEmployeeIdColumnName);
                    sbCombWhereCol.append(PT_EQUAL);
                    sbCombWhereCol.append(this.escDBString(sEmployeeId));
                }
            }
        }
        return sbCombWhereCol.toString();
    }
    /**
     * 一般カラムの比較条件SQLを生成します
     *
     * @param   tableCombinationType        データディクショナリ(カラム)情報
     * @param   paSearchRangeInfo   検索データ取得結果
     * @param   psDomainId          ドメインコード
     * @return  String              一般のカラムを検索するSQL
     */
    private String createNormalSQL(TableCombinationType tableCombinationType, SearchRangeInfo paSearchRangeInfo, String psDomainId) {

        // 検索データ取得
        String sAndOr = this.toBlank(paSearchRangeInfo.getMdpdCandor());
        String sOpenedParenthsis = this.toBlank(paSearchRangeInfo.getMdpdCopenedparenthsis());
        String sTableId = paSearchRangeInfo.getMdpdCtableid();
        String sColumnId = paSearchRangeInfo.getMdpdCcolumnid();
        String sOperator = paSearchRangeInfo.getMdpdCoperator();
        String sValue = paSearchRangeInfo.getMdpdCvalue();
        String sClosedParenthsis = this.toBlank(paSearchRangeInfo.getMdpdCclosedparenthsis());
        String sMyFlag = this.toBlank(paSearchRangeInfo.getMdpdCmyflag());
        String sTypeOfColumn = paSearchRangeInfo.getMdpdCtypeofcolumn();
        String sMaster = this.getMaseterId(sValue);

        StringBuilder sWhere = new StringBuilder();
        String sAndA = " AND a.";

        // 条件式クエリ組み立て
        sWhere.append(sAndOr).append(PT_SPACE);
        sWhere.append(sOpenedParenthsis);
        sWhere.append(this.atBraceFolderDot(sTableId));
        sWhere.append(sColumnId).append(PT_SPACE);
        sWhere.append(sOperator).append(PT_SPACE);

        // 通常の比較の場合
        if (sMyFlag.equals(MY_FLG0)) {
            if (!sOperator.equalsIgnoreCase("IS NULL")
                    && !sOperator.equalsIgnoreCase("IS NOT NULL")) {

                // マスタ項目以外の時
                if (sMaster.equals("")) {

                    if (sTypeOfColumn.equalsIgnoreCase("VARCHAR2")) {
                        sWhere.append(this.escDBString(sValue));
                    } else if (sTypeOfColumn.equalsIgnoreCase("NUMBER")) {
                        sWhere.append(this.toDBNumber(sValue));
                    } else if (sTypeOfColumn.equalsIgnoreCase("DATE")) {
                        sWhere.append(this.toDBDate(sValue));
                    } else {
                        sWhere.append(this.escDBString(sValue));
                    }
                } else {
                    // マスタ項目の時
                    //sWhere.append(this.escDBString(this.getDetailId(sValue)));
                    sWhere.append(this.escDBString(sValue));
                }
                sWhere.append(PT_SPACE);

            }
        } else if (sMyFlag.equals(MY_FLG1)) {

            // 検索者自身の属性と比較の場合
            sWhere.append("(SELECT DISTINCT ");
            sWhere.append(sColumnId).append(PT_SPACE);
            sWhere.append(" FROM ");
            sWhere.append(sTableId).append(" a ");
            sWhere.append(" WHERE ");

            // カラムID：顧客コード
            String sCustomerIdColumnName = tableCombinationType.getCustomerIdColumnName();
            if (sCustomerIdColumnName != null) {
                sWhere.append("a.").append(sCustomerIdColumnName);
                sWhere.append(PT_EQUAL);
                sWhere.append(this.escDBString(this.gPsSession.getLoginCustomer()));
                sWhere.append(PT_SPACE);
            }
            // ドメインコード＝"01"(従業員ドメイン) or "03"(法人ドメイン)
            // カラムID：ユーザID
            if (psDomainId.equals(PsConst.DOMAIN_EMPLOYEE) || psDomainId.equals(PsConst.DOMAIN_COMPANY)) {
                String sUserIdColumnName = tableCombinationType.getUserIdColumnName();
                if (sUserIdColumnName != null) {
                    sWhere.append(sAndA).append(sUserIdColumnName);
                    sWhere.append(PT_EQUAL);
                    sWhere.append(this.escDBString(this.gPsSession.getLoginUser()));
                    sWhere.append(PT_SPACE);
                }

                // ドメインコード＝"02"(組織ドメイン)
            } else if (psDomainId.equals(PsConst.DOMAIN_ORGANIZATION)) {
                // カラムID：法人コード
                String sCompanyIdColumnName = tableCombinationType.getCompanyIdColumnName();
                if (sCompanyIdColumnName != null) {
                    sWhere.append(sAndA).append(sCompanyIdColumnName);
                    sWhere.append(PT_EQUAL);
                    sWhere.append(this.escDBString(this.gPsSession.getLoginCompany()));
                    sWhere.append(PT_SPACE);
                }

                // カラムID：組織コード
                // 異動歴分ループ
                String sSectionIdColumnName = tableCombinationType.getSectionIdColumnName();
                if (sSectionIdColumnName != null) {
                    sWhere.append(sAndA).append(sSectionIdColumnName);

                    int nCnt = this.gPsSession.getLoginDesignation().size();
                    // 異動歴が1件のときはイコール
                    if (nCnt == 1) {
                        sWhere.append(PT_EQUAL);
                        sWhere.append(this.escDBString(this.gPsSession.
                                getLoginDesignation().get(0).getSection()));
                    } else {
                        sWhere.append(PT_IN);
                        sWhere.append(PT_OPEN_PAR);
                        sWhere.append(this.escDBString(this.gPsSession.getLoginDesignation().get(0).getSection()));
                        for (int i = 1; i < nCnt - 1; i++) {
                            sWhere.append(PT_COMMA);
                            sWhere.append(this.escDBString(this.gPsSession.
                                    getLoginDesignation().get(i).getSection()));
                        }
                        sWhere.append(PT_CLOSE_PAR);
                    }
                    sWhere.append(PT_SPACE);
                }
            }

            // カラムID：開始日
            String sStartDateColumnName = tableCombinationType.getStartDateColumnName();
            if (sStartDateColumnName != null) {
                sWhere.append(sAndA).append(sStartDateColumnName);
                sWhere.append(PT_LESSER);
                sWhere.append(this.getPsSecurityDate());
                sWhere.append(PT_SPACE);
            }

            // カラムID：終了日
            String sEndDateColumnName = tableCombinationType.getEndDateColumnName();
            if (sEndDateColumnName != null) {
                sWhere.append(sAndA).append(sEndDateColumnName);
                sWhere.append(PT_GREATER);
                sWhere.append(this.getPsSecurityDate());
                sWhere.append(PT_SPACE);
            }

            sWhere.append(PT_CLOSE_PAR);

        }
        sWhere.append(sClosedParenthsis);

        return sWhere.toString();
    }

    private static final String MAPKEY_COM = "lCom";
    private static final String MAPKEY_SEC = "lSec";
    private static final String MAPKEY_EMP = "lEmp";
    private static final String MAPKEY_POS = "lpos";

    /**
     * プリセットデータの条件SQLを生成します
     *
     * @param   searchRangeInfo 検索範囲取得DTOクラス
     * @return  String		プリセット用条件式
     */
    private String createPreSetSQL(SearchRangeInfo searchRangeInfo)   {

        String sMyFlag = searchRangeInfo.getMdpdCmyflag();
        String sPreSetSQL = this.toBlank(searchRangeInfo.getMdpdCvalue());

        StringBuilder sWhere = new StringBuilder();

        if (sMyFlag.equals(MY_FLG2)) {

            // プリセットの条件式を使用する場合(異動暦データをリストで返却、クエリは１つで返却するパターン)
            // 例）異動暦のデータをまとめて条件に指定する場合
            String sCus = this.gPsSession.getLoginCustomer();
            String sUsr = this.gPsSession.getLoginUser();

            HashMap<String,List <String>> hashMap = this.getLoginUserInfoMap();

            // 文字の置き換え処理
            // 顧客コード
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##CUSTOMER##", sCus);
            // 法人コード
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##COMPANY##",  this.replaceList(hashMap.get(MAPKEY_COM)));
            // 組織コード
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##SECTION##",  this.replaceList(hashMap.get(MAPKEY_SEC)));
            // 社員番号
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##EMPLOYEE##", this.replaceList(hashMap.get(MAPKEY_EMP)));
            // ユーザID
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##USER##",     sUsr);
            // 役職コード
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##POST##",     this.replaceList(hashMap.get(MAPKEY_POS)));
            // 基準日
            sPreSetSQL = SysUtil.replaceStringIgnoreCase(sPreSetSQL, "##DATE##",     getPsSecurityDate());

            // [AND/OR] [(] ##[TABLE_ID]##.[COLUMN_ID] [=] [SQL] [)]
            sWhere.append(this.toBlank(searchRangeInfo.getMdpdCandor()));
            sWhere.append(PT_SPACE);
            sWhere.append(this.toBlank(searchRangeInfo.getMdpdCopenedparenthsis()));
            sWhere.append(this.atBraceFolderDot(this.toBlank(searchRangeInfo.getMdpdCtableid())));
            sWhere.append(this.toBlank(searchRangeInfo.getMdpdCcolumnid()));
            sWhere.append(PT_SPACE);
            sWhere.append(this.toBlank(searchRangeInfo.getMdpdCoperator()));
            sWhere.append(PT_SPACE);
            sWhere.append(sPreSetSQL);
            sWhere.append(this.toBlank(searchRangeInfo.getMdpdCclosedparenthsis()));

        } else if (sMyFlag.equals(MY_FLG3)) {

            // プリセットの条件式を使用する場合(異動暦分クエリをORで結合し、返却するパターン)
            // 例）異動暦のデータを１つずつ確認し、条件に指定する場合
            StringBuilder sb = new StringBuilder();

            String sCus = this.gPsSession.getLoginCustomer();
            String sUsr = this.gPsSession.getLoginUser();

            List < HistDesignationDO> lHistDesignationList= this.getLoginUserInfoList();

            // 異動暦情報取得(法人・組織・社員番号)
            for (Iterator < HistDesignationDO> ite = lHistDesignationList.iterator(); ite.hasNext();) {

                HistDesignationDO histDesignationEntity= ite.next();

                // 文字の置き換え処理
                // 顧客コード
                String sTemp  = sPreSetSQL;
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##CUSTOMER##", sCus);
                // 法人コード
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##COMPANY##",  histDesignationEntity.getHdCcompanyidCk());
                // 組織コード
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##SECTION##",  histDesignationEntity.getHdCsectionidFk());
                // 社員番号
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##EMPLOYEE##", histDesignationEntity.getHdCemployeeidCk());
                // ユーザID
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##USER##", sUsr);
                // 役職コード
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##POST##", histDesignationEntity.getHdCpostidFk());
                // 基準日
                sTemp = SysUtil.replaceStringIgnoreCase(sTemp, "##DATE##", getPsSecurityDate());

                // プリセット条件式を作成
                sb.append(this.toBlank(searchRangeInfo.getMdpdCandor()));
                sb.append(PT_SPACE);
                sb.append(this.toBlank(searchRangeInfo.getMdpdCopenedparenthsis()));
                sb.append(this.atBraceFolderDot(this.toBlank(searchRangeInfo.getMdpdCtableid())));
                sb.append(this.toBlank(searchRangeInfo.getMdpdCcolumnid()));
                sb.append(PT_SPACE);
                sb.append(this.toBlank(searchRangeInfo.getMdpdCoperator()));
                sb.append(PT_SPACE);
                sb.append(sTemp);
                sb.append(this.toBlank(searchRangeInfo.getMdpdCclosedparenthsis()));
                sb.append(PT_OR);

            }
            sWhere = new StringBuilder(this.checkWhereList(sb.toString()));
            sWhere.append(PT_SPACE);
        }

        return sWhere.toString();
    }

    /**
     * プリセットデータに使用する異動歴情報を MAP で返却します。
     *
     * @return  HashMap<String,List <String>>		異動歴情報
     */
    private HashMap<String,List <String>> getLoginUserInfoMap() {

        String sCus = this.gPsSession.getLoginCustomer();
        String sUsr = this.gPsSession.getLoginUser();
        // 基準日とSYSDATEが一致しない場合DBから異動歴情報を参照する
        return isTheSameDay4Sysdate() ? getLoginUserInfoDBMap(sCus, sUsr) : getLoginUserInfoSessionMap(sCus, sUsr);

    }

    /**
     * プリセットデータに使用する異動歴情報をDBから取得し返却します。
     *
     *@param sCus 顧客コード
     *@param sUsr ユーザーID
     *
     * @return  HashMap<String,List <String>>		異動歴情報
     */
    private HashMap<String,List <String>> getLoginUserInfoDBMap(String sCus, String sUsr) {

        return getLoginUserInfoMain(getLoginUserInfoDB(sCus, sUsr));

    }

    /**
     * プリセットデータに使用する異動歴情報をセッションから取得し返却します。
     *
     * @param sCus 顧客コード
     * @param sUsr ユーザーID
     *
     * @return  HashMap<String,List <String>>		異動歴情報
     */
    private HashMap<String,List <String>> getLoginUserInfoSessionMap(String sCus, String sUsr){

        return getLoginUserInfoMain(getLoginUserInfoSession(sCus, sUsr));

    }

    /**
     * セッションに保持している異動歴情報を HashMap 形式で返却します。
     *
     * @param  lHistDesignationEntity 異動歴(Entity)
     *
     * @return  HashMap<String,List <String>>		異動歴情報
     */
    private HashMap<String,List <String>> getLoginUserInfoMain(List < HistDesignationDO> lHistDesignationEntity){

        HashMap<String,List <String>> hashMap = MapUtil.newHashMap();
        List <String> lCom = CollUtil.newArrayList();
        List <String> lSec = CollUtil.newArrayList();
        List <String> lEmp = CollUtil.newArrayList();
        List <String> lpos = CollUtil.newArrayList();

        for (Iterator < HistDesignationDO> ite = lHistDesignationEntity.iterator(); ite.hasNext();) {

            HistDesignationDO histDesignationEntity= ite.next();

            if (this.checkDuplicate(lCom, histDesignationEntity.getHdCcompanyidCk())) {
                lCom.add(histDesignationEntity.getHdCcompanyidCk());
            }
            if (this.checkDuplicate(lSec, histDesignationEntity.getHdCsectionidFk())) {
                lSec.add(histDesignationEntity.getHdCsectionidFk());
            }
            if (this.checkDuplicate(lEmp, histDesignationEntity.getHdCemployeeidCk())) {
                lEmp.add(histDesignationEntity.getHdCemployeeidCk());
            }
            if (this.checkDuplicate(lpos, histDesignationEntity.getHdCpostidFk())) {
                lpos.add(histDesignationEntity.getHdCpostidFk());
            }

        }

        hashMap.put(MAPKEY_COM, lCom);
        hashMap.put(MAPKEY_SEC, lSec);
        hashMap.put(MAPKEY_EMP, lEmp);
        hashMap.put(MAPKEY_POS, lpos);

        return hashMap;

    }

    /**
     * プリセットデータに使用する異動歴情報を List で返却します。
     *
     * @return  List < HistDesignationDO>		異動歴情報
     */
    private List < HistDesignationDO> getLoginUserInfoList(){

        String sCus = this.gPsSession.getLoginCustomer();
        String sUsr = this.gPsSession.getLoginUser();

        return isTheSameDay4Sysdate() ? getLoginUserInfoDB(sCus, sUsr) : getLoginUserInfoSession(sCus, sUsr);

    }

    private static final String TABLE_ID_HISTDESIGNATION = "HistDesignation";
    /**
     * DBに保持している異動歴情報を List 形式で返却します。
     *
     * @param sCus 顧客コード
     * @param sUsr ユーザーID
     *
     * @return  List < HistDesignationDO>		異動歴情報
     */
    private List < HistDesignationDO> getLoginUserInfoDB(String sCus, String sUsr){
        // DBからログインユーザー情報を取得
        List < HistDesignationDO> lHistDesignationList=
                iHistDesignationService.selectByEmpId(sCus, this.gPsSession.getLoginCompany(), sUsr, this.getPsSecurityDateNoSQL());
        if (lHistDesignationList.size() == 0){
            throw new GlobalException(TABLE_ID_HISTDESIGNATION);
        }
        return lHistDesignationList;

    }

    /**
     * セッションに保持している異動歴情報を List 形式で返却します。
     *
     * @param sCus 顧客コード
     * @param sUsr ユーザーID
     *
     * @return  List < HistDesignationDO>		異動歴情報
     */
    private List < HistDesignationDO> getLoginUserInfoSession(String sCus, String sUsr){

        List <HistDesignationDO> lHistDesignationList= new ArrayList < HistDesignationDO>();

        int nCnt = this.gPsSession.getLoginDesignation().size();

        // 異動暦情報取得(法人・組織・社員番号・役職)
        for (int i = 0; i < nCnt; i++) {
            HistDesignationDO histDesignationEntity= new HistDesignationDO();
            histDesignationEntity.setHdCcompanyidCk(this.gPsSession.getLoginDesignation().get(i).getCompanyCode());
            histDesignationEntity.setHdCsectionidFk(this.gPsSession.getLoginDesignation().get(i).getSection());
            histDesignationEntity.setHdCemployeeidCk(this.gPsSession.getLoginDesignation().get(i).getEmployee());
            histDesignationEntity.setHdCpostidFk(this.gPsSession.getLoginDesignation().get(i).getPostCode());
            lHistDesignationList.add(i, histDesignationEntity);

        }

        return lHistDesignationList;

    }

    /**
     * 基準日が今日(SYSDATE)と一致するか判定します。
     *
     * @return Boolean True:一致しない/false:一致する
     */
    private Boolean isTheSameDay4Sysdate(){
        return !SysUtil.getSysdate(PsConst.DATE_YYYYMMDD).equals(this.getPsSecurityDateNoSQL());
    }

    /**
     * 基準日を文字列で返却します。
     *
     * @return String 基準日
     */
    private String getPsSecurityDateNoSQL(){
        if (this.gsPsSecurityDate == null){
            return SysUtil.getSysdate(PsConst.DATE_YYYYMMDD);
        } else {
            return this.gsPsSecurityDate;
        }
    }

    /**
     * 基準日をDate型で返却します。
     *
     * @return Date 基準日
     */
    private Date getPsSecurityDate4DateType(){
        try {
            return SysUtil.transStringToDate(this.getPsSecurityDateNoSQL());
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 検索対象範囲条件の結合Where句の妥当性チェック
     *
     * @param   psWhere 検索対象範囲条件の結合Where句
     * @return  String  検索対象範囲条件の結合Where句
     */
    protected String checkWhereList(String psWhere) {

        // 結合Where句がなかったら空白で返却
        if (this.toBlank(psWhere).equals("")) {
            return psWhere;
        }

        // 最後がORで終わっていたら削除する
        if (psWhere.lastIndexOf(PT_OR)
                == (psWhere.length() - CNT_OR)
                && (psWhere.length() - CNT_OR) > 0) {

            psWhere = psWhere.substring(0, psWhere.length() - CNT_OR);
        }

        // 最後がANDで終わっていたら削除する
        if (psWhere.lastIndexOf(PT_AND)
                == (psWhere.length() - CNT_AND)
                && (psWhere.length() - CNT_AND) > 0) {

            psWhere = psWhere.substring(0, psWhere.length() - CNT_AND);
        }

        return psWhere;
    }

    /**
     * 検索対象範囲条件のFrom句リストを取得します
     *
     * @return List 検索対象範囲条件のFrom句リスト
     */
    public List<String> getFrom() {
        return this.glistFrom;
    }

    /**
     * 検索対象範囲条件のFrom句リストを設定します
     *
     * @param plistFrom 検索対象範囲条件のFrom句リスト
     */
    protected void setFrom(List<String> plistFrom) {
        this.glistFrom = plistFrom;
    }

    /**
     * 検索対象範囲条件の結合Where句リストを取得します
     *
     * @return List 検索対象範囲条件の結合Where句リスト
     */
    public List<String> getCombWhere() {
        return this.glistCombWhere;
    }

    /**
     * 検索対象範囲条件の結合Where句リストを設定します
     *
     * @param plistCombWhere    検索対象範囲条件の結合Where句リスト
     */
    protected void setCombWhere(List<String> plistCombWhere) {
        this.glistCombWhere = plistCombWhere;
    }

    /**
     * 検索対象範囲条件の条件Where句を取得します
     *
     * @return String 検索対象範囲条件の条件Where句
     */
    public String getCondWhere() {
        return this.gsCondWhere;
    }

    /**
     * 検索対象範囲条件の条件Where句を設定します
     *
     * @param psCondWhere   検索対象範囲条件の条件Where句
     */
    protected void setCondWhere(String psCondWhere) {
        this.gsCondWhere = psCondWhere;
    }
    /**
     * 検索対象範囲条件 設定済みフラグを取得します
     *
     * @return condSetupFlg	設定済みフラグ
     */
    public boolean isCondSetupFlg() {
        return this.gbCondSetupFlg;
    }

    /**
     * 検索対象範囲条件 設定済みフラグを設定します
     *
     * @param condSetupFlg 設定済みフラグ
     */
    protected void setCondSetupFlg(boolean condSetupFlg) {
        this.gbCondSetupFlg = condSetupFlg;
    }

    /**
     * 検索対象範囲 退職者検索対象範囲を取得します
     * @return 退職者検索対象範囲フラグ
     */
    public int getCondRetired() {
        return this.gnCondRetired;
    }

    /**
     * 検索対象範囲 退職者検索対象範囲を設定します
     * @param nRetired 退職者検索対象範囲フラグ
     * @exception
     */
    protected void setCondRetired(int nRetired) {
        this.gnCondRetired = nRetired;
    }

    /**
     * DB更新用に文字列を「'」で囲んで返します。 nullであれば「NULL」の文字列を返します。
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String escDBString(String psString) {
        return SysUtil.transStringNullToDB(SysUtil.escapeQuote(psString));
    }

    /**
     * DB更新用に数値であればそのままを、nullであれば0をします。
     *
     * @param   psNumber    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String toDBNumber(String psNumber) {
        return SysUtil.transNumberNullToDB(psNumber);
    }
    /**
     * DB更新用に日付「to_date形式に変換して返します。
     * nullであれば「NULL」の文字列を返します。
     * @param   psDate  変換元文字列
     * @return  String  変換結果文字列
     */
    protected String toDBDate(String psDate) {
        return SysUtil.transDateNullToDB(psDate);
    }

    /**
     * 取得した値がNULLの場合は、空白で返却します
     *
     * @param   psString    変換元文字列
     * @return  String      変換結果文字列
     */
    protected String toBlank(String psString) {
        return SysUtil.transNull(psString);
    }

    /**
     * 指定カラムに対する条件値をINの形式で表すSQLの一部を返します<BR>
     * Oracleの制約によりINに1001以上の値を指定するとエラーが発生する現象の対応<BR>
     *
     * @param psSQLColumn psSQLColumn
     * @param psSections psSections
     * @param pbEqual pbEqual
     * @return String SQLのWHERE句IN
     */
    protected String sectionsSeparateWhere(String psSQLColumn, String psSections, boolean pbEqual) {

        /* Error Check */
        if (this.isEmpty(psSQLColumn) || this.isEmpty(psSections)) {
            return "";
        }

        // 指定文字列を単要素に展開し、重複を取り除く
        CSVTokenizer separatePart = new CSVTokenizer(psSections);
        HashMap<String, String> mapSeparatePart = new HashMap<String, String>();
        while (separatePart.hasMoreTokens()) {
            String sSeparatePart = separatePart.nextToken();
            mapSeparatePart.put(sSeparatePart, sSeparatePart);
        }

        // 要素が1つの場合には[=]の式を返却する
        if (mapSeparatePart.size() == 1) {
            if (pbEqual) {
                return psSQLColumn + PT_EQUAL + psSections;
            } else {
                return psSQLColumn + PT_UNEQUAL + psSections;
            }
        }

        /* SQL Separate */
        StringBuilder sbWhere = new StringBuilder();
        Set<String> setSeparatePart = mapSeparatePart.keySet();
        for (Iterator<String> iteSeparatePart = setSeparatePart.iterator(); iteSeparatePart.hasNext();) {

            StringBuilder sbSeparatePart = new StringBuilder();
            long j = 0;

            while (iteSeparatePart.hasNext() && j < LIMITVALUE) {

                String sSeparatePart = iteSeparatePart.next();

                if (j == 0) {
                    sbSeparatePart.append(sSeparatePart);
                } else {
                    sbSeparatePart.append(PT_COMMA);
                    sbSeparatePart.append(sSeparatePart);
                }
                j++;
            }

            /* SQL Molding */
            if (sbWhere.length() != 0) {
                sbWhere.append(PT_OR);
            }
            if (pbEqual) {
                sbWhere.append(psSQLColumn + " IN ( ");
            } else {
                sbWhere.append(psSQLColumn).append(" NOT IN ( ");
            }
            sbWhere.append(sbSeparatePart.toString());
            sbWhere.append(PT_CLOSE_PAR);
        }

        String sWhere = sbWhere.toString();
        if (sWhere.indexOf(" IN (") != sWhere.lastIndexOf(" IN (")) {
            // 複数のIN句に分解されているので括弧で囲む
            sWhere = PT_OPEN_PAR
                    + sWhere
                    + PT_CLOSE_PAR;
        }

        return sWhere;
    }

    /**
     * リストに格納された値を、カンマ区切りの文字列に変換します。
     *
     * @param   psList  変換対象リスト
     * @return  String  カンマで区切られた文字列
     */
    protected String replaceList(List<String> psList) {

        // 値が未格納の場合
        if (psList == null || psList.size() < 1) {
            return "";
        }

        // 値が格納済みの場合
        String sResultList  = "";
        if (psList.size() > 0) {
            // カンマ区切りの文字列にする
            StringBuilder sbResult  = new StringBuilder();
            for (int i = 0; i < psList.size(); i++) {

                // 前後にシングルクォーテーションを付加し、末尾にカンマをつける
                if (!this.toBlank(psList.get(i)).equals("")) {
                    sbResult.append(PT_APO);
                    sbResult.append(psList.get(i).trim());
                    sbResult.append(PT_APO);
                    sbResult.append(PT_TRIM_COMMA);
                }
            }
            // 最末尾のカンマを取り除く
            sResultList = sbResult.toString();
            sResultList = sResultList.substring(0, sResultList.length() - 1);
        }

        return sResultList;
    }
    /**
     * 対象文字列内の空白を除去します
     *
     * @param   psString    対象文字列
     * @return  String      空白除去後の文字列
     */
    protected String trimSpace(String psString) {
        String sString = "";
        for (int i = 0; i < psString.length(); i++) {
            if (psString.charAt(i) != ' ') {
                sString = psString.substring(i);
                break;
            }
        }
        return sString;
    }

    /**
     * 使用するPsSessionのデータの有無判定
     *
     * @return  boolean   判定結果
     */
    protected boolean checkSession() {

        // 顧客コードが取得できなかった場合は、例外とする
        if (this.gPsSession.getLoginCustomer() == null) {
            throw new GlobalException("LoginCustomer not added");

        }
        // グループ判定結果が取得できなかった場合は、例外とする
        if (this.gPsSession.getLoginGroups() == null) {
            throw new GlobalException("LoginGroup not found");

        }
        // ユーザIDが取得できなかった場合は、例外とする
        if (this.gPsSession.getLoginUser() == null) {
            throw new GlobalException("LoginUser not added");

        }
        // 異動歴情報が取得できなかった場合は、例外とする
        if (this.gPsSession.getLoginDesignation() == null) {
            throw new GlobalException("LoginDesignation not added");

        }
        return true;
    }

    // ▼ #2736 未設定時の検索クエリを全権ユーザから制限ユーザへ修正
    /**
     * 使用するリクエストパラメータのデータの有無判定
     *
     * @param	psDomainId	ドメインID
     * @return  boolean   	判定結果
     */
    protected boolean checkRequest(String psDomainId) {
        // TODO:リクエストパラメータが異常値の場合は、例外を発生させるようにしたい
        // ドメインコード＝"01"(従業員ドメイン)
        if (psDomainId.equals(PsConst.DOMAIN_EMPLOYEE)) {
            // 検索対象者ユーザID が取得できなかった場合は、ログインユーザの値を設定
            if (this.gsPsTargetUser == null) {
                this.gsPsTargetUser = this.gPsSession.getLoginUser();
            }
        } else {
            // ドメインコード＝"02"(組織ドメイン)
            // 選択法人コードが取得できなかった場合は、ログインユーザの法人(本務)を設定
            if (this.gsPsSelectedComp == null) {
                this.gsPsSelectedComp = this.gPsSession.getLoginDesignation().get(0).getCompanyCode();
            }
            // 選択組織コードが取得できなかった場合は、ログインユーザの役職(本務)を設定
            if (this.gsPsSelectedDept == null || this.gsPsSelectedDept.equals("")) {
                this.gsPsSelectedDept = this.gPsSession.getLoginDesignation().get(0).getSection();
            }
        }
        return true;
    }

    /**
     * 名称マスタコード取得処理<br>
     * 指定された値がマスタコードであった場合、名称マスタコードを返却します。
     *
     * @param psValue   値(マスタコード)
     * @return  String  名称マスタコード
     */
    protected String getMaseterId(String psValue) {
        String sMasterId = "";
        if (psValue != null) {
            Pattern pattern = Pattern.compile(PT_BAR);
            String[] stValue = pattern.split(psValue);
            // 分割された場合は、名称マスタコードを返却
            if (!psValue.equals(stValue[0])) {
                sMasterId = stValue[0];
            }
        }
        return sMasterId;
    }

    /**
     * 明細データコード取得処理<br>
     * 指定された値がマスタコードであった場合、明細データコードを返却します。
     *
     * @param psValue   値(マスタコード)
     * @return  String  明細データコード
     */
    public String getDetailId(String psValue) {
        String sDetailId = "";
        if (!this.isEmpty(psValue)) {
            Pattern pattern = Pattern.compile(PT_BAR);
            String[] stValue = pattern.split(psValue);
            // 分割された場合は、明細データコードを返却
            if (psValue.equals(stValue[0])) {
                sDetailId = psValue;
            } else {
                sDetailId = stValue[1];
            }
        }
        return sDetailId;
    }

    /**
     * 文字列の空チェック
     *
     * @param sString 文字列
     * @return boolean
     */
    private boolean isEmpty(String sString) {
        if (sString == null){
            return true;
        }
        if (sString.equals("")){
            return true;
        }
        return false;
    }
    /**
     * ブレースフォルダ形式の変換
     *
     * @return boolean
     */
    private String atBraceFolder(int nIndex) {
        return atBraceFolder(Integer.toString(nIndex));
    }
    private String atBraceFolder(String sString) {
        return PT_SIGN + sString + PT_SIGN;
    }
    private String atBraceFolderDot(String sString) {
        return atBraceFolder(sString) + PT_DOD;
    }

}
