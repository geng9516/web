package jp.smartcompany.framework.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.common.GlobalException;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.boot.util.ScCacheUtil;
import jp.smartcompany.boot.util.SysUtil;
import jp.smartcompany.framework.appcontrol.AbstractInfo;
import jp.smartcompany.framework.appcontrol.business.AppAuthInfoBusiness;
import jp.smartcompany.framework.appcontrol.business.AppSearchRangeInfoBusiness;
import jp.smartcompany.framework.sysboot.dto.TableCombinationTypeDTO;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Xiao Wenpeng
 * 検索対象範囲設定情報取得クラス
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PsBuildTargetSql {

    /** クエリ組み立て：「=」 */
    private static final String STR_EQ = "=";
    /** 区切り文字:半角スペース */
    private static final String SPACE = " ";
    /** 区切り文字:カンマ */
    private static final String CAMMA = ",";
    /** 区切り文字:AND */
    private static final String AND = " AND ";
    /** 区切り文字:OR */
    private static final String OR = " OR ";
    /** クエリ組み立て:EXISTS( */
    private static final String EXISTS_START = " EXISTS ( ";
    /** クエリ組み立て:SELECT 1 */
    private static final String SELECT = " SELECT 1 ";
    /** クエリ組み立て：FROM*/
    private static final String FROM = " FROM";
    /** クエリ組み立て：SELECT */
    private static final String CSELECT = " SELECT ";
    /** クエリ組み立て:EXISTS( */
    private static final String START = " ( ";
    /** クエリ組み立て：WHERE */
    private static final String WHERE = " WHERE ";
    /** クエリ組み立て：AS */
    private static final String AS = " AS ";
    /** クエリ組み立て(テーブル別名)：EXSISTB */
    private static final String EXSISTB = " EXSISTB";

    /** クエリ組み立て:) */
    private static final String EXISTS_END = " ) ";
    /** PermaLink組み立て:トップページのサイトID */
    private static final String TOP_PAGE = "TopPage";
    /** PermaLink組み立て文字:「?」 */
    private static final String LINK_QUEST = "?";
    /** PermaLink組み立て文字:「=」 */
    private static final String LINK_EQ = STR_EQ;
    /** パターン文字列:テーブル別名用ID */
    private static final String PT_ID = "SR_";
    /** パターン文字列:プレースホルダー汎用 */
    private static final String PT_SIGN = "##";
    /** 例外用:not null */
    private static final String ERR_NOT_NULL = "not null";
    /** 例外用:established data */
    private static final String ERR_ESTABLISHED_DATA = "Accurate Setting";

    /** 異動歴Index */
    private static final String IDX_HIST_DESIGNATION = "PS_HIST_DESIGNATION_IDX5";
    /** 基本情報Index */
    private static final String IDX_MAST_EMPLOYEES = "MAST_EMPLOYEES_IDX4";

    /** 検索対象範囲情報：From句 */
    private final Map<String, List<String>> gFromListMap = MapUtil.newHashMap();
    /** 検索対象範囲情報：Where句(結合式用) */
    private final Map <String, List<String>> gWhereListMap = MapUtil.newHashMap();
    /** 検索対象範囲情報：Where句(条件式用) */
    private final Map <String, String> gWheresMap = MapUtil.newHashMap();
    /** 検索対象範囲情報：設定済みフラグ */
    private boolean gbSetFlg = false;
    /**
     * 呼び元のドメイン情報。
     * 通常はアプリケーションのドメイン属性となる。
     * コンポーネント等の場合は、サイトのドメイン属性となる。
     */
    private String domain = null;
    /** ドメイン判定モード true:判定する(デフォルト)、false:判定しない */
    private Boolean domainCheckMode = true;
    /** 検索対象範囲のクエリ部品：From句 */
    private final Map<String, String> gFromSqlMap = MapUtil.newHashMap();
    /** 検索対象範囲のクエリ部品：Where句 */
    private final Map<String, String> gWhereSqlMap = MapUtil.newHashMap();
    /** セキュリティ基準日 YYYY/MM/DD */
    private String gsPsSecurityDate;

    /** Request:URL情報取得用にインジェクションする */
    private HttpServletRequest request;
    // ログイン情報クラス取得
    private PsSession gPsSession;
    private final ScCacheUtil scCacheUtil;
    private final AppSearchRangeInfoBusiness appSearchRangeInfoLogic;
    private final AppAuthInfoBusiness appAuthInfoLogic;

    /**
     * request 設定する。
     *
     * @param request を設定
     */
    public void setRequest(HttpServletRequest request) {
        this.request = request;
        gPsSession = (PsSession) request.getSession().getAttribute(Constant.PS_SESSION);
        // セキュリティ基準日
        setPsSecurityDate(request.getParameter("psSecurityDate"));
    }

    /**
     * 基準日のセット
     * @param psSecurityDate 基準日
     */
    public void setPsSecurityDate(String psSecurityDate) {
        this.gsPsSecurityDate = psSecurityDate;
    }

    /**
     * 基準日を取得
     * @return セキュリティ基準日(SQL)
     */
    private String getPsSecurityDate() {
        // PsSecurityDateが取得できないときはシステム日付を返す
        if(this.gsPsSecurityDate == null) {
            return "TRUNC(SYSDATE) ";
        }
        // PsSecurityDateが設定されているときはそれを返す
        else {
            return "TO_DATE('" + SysUtil.escapeQuote(gsPsSecurityDate) + "','YYYY/MM/DD') ";
        }
    }


    /**
     * 呼び元のドメイン情報を設定します。
     * 通常はアプリケーションのドメイン属性となります。
     * コンポーネント等の場合は、サイトのドメイン属性となります。
     * @param domain を設定
     */
    protected void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * domain 取得する。
     * 従業員ドメイン or 組織ドメイン でない場合は例外が発生する。
     * @return domain
     */
    protected String getDomain() {
        if (this.domain == null) {
            throw new GlobalException("従業員ドメイン or 組織ドメイン でない場合は例外が発生する");
        }
        return this.domain;
    }

    /**
     * 検索対象範囲情報を構築する。
     * @param psSiteId サイトId
     * @param psMode			モード（0:通常モード 1:異動歴ID結合モード）
     */
    private void create(String psSiteId, String psMode) {
        setRequest(ContextUtil.getHttpRequest());
        String sSiteId = psSiteId;
        if (psSiteId == null) {
            // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
            // 「psSite=TopPage」の扱いで処理する
            sSiteId = TOP_PAGE;
        }
        appSearchRangeInfoLogic.setPsSite(sSiteId);
        System.out.println("getApplicationUrl" +getApplicationUrl()+","+psMode);
        appSearchRangeInfoLogic.create(getApplicationUrl(), psMode);
    }
    /**
     * 検索対象範囲情報を構築する。
     * @param psSiteId サイトId
     * @param psDomainCode ドメインコード
     */
    private void createDomain(String psSiteId, String psDomainCode, String psMode) {
        String sSiteId = psSiteId;
        if (psSiteId == null) {
            // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
            // 「psSite=TopPage」の扱いで処理する
            sSiteId = TOP_PAGE;
        }
        appSearchRangeInfoLogic.setPsSite(sSiteId);
        appSearchRangeInfoLogic.create(getApplicationUrl(), psDomainCode, psMode);
    }
    /**
     * 検索対象範囲情報を構築する。
     * @param psSiteId サイトId
     * @param psAppId アプリケーションId
     * @param psMode			モード（0:通常モード 1:異動歴ID結合モード）
     */
    private void create(String psSiteId, String psAppId, String psMode) {
        String sSiteId = psSiteId;
        if (psSiteId == null) {
            // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
            // 「psSite=TopPage」の扱いで処理する
            sSiteId = TOP_PAGE;
        }
        appSearchRangeInfoLogic.setPsSite(sSiteId);
        appSearchRangeInfoLogic.setPsApp(psAppId);
        appSearchRangeInfoLogic.create(this.getApplicationUrl(), psMode);
    }
    /**
     * 検索対象範囲情報を構築する。
     * @param psSiteId サイトId
     * @param psAppId アプリケーションId
     * @param psDomainCode ドメインコード
     */
    private void createDomain(String psSiteId, String psAppId, String psDomainCode, String psMode) {
        String sSiteId = psSiteId;
        if (psSiteId == null) {
            // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
            // 「psSite=TopPage」の扱いで処理する
            sSiteId = TOP_PAGE;
        }
        appSearchRangeInfoLogic.setPsSite(sSiteId);
        appSearchRangeInfoLogic.setPsApp(psAppId);
        appSearchRangeInfoLogic.create(getApplicationUrl(), psDomainCode, psMode);
    }

    /**
     * 検索対象範囲設定SQL組み立て処理
     * (From句)
     * @return 検索対象範囲設定SQL
     */
    private List <String> getTargetSqlFroms() {
        return appSearchRangeInfoLogic.getFrom();
    }
    /**
     * 検索対象範囲設定SQL組み立て処理
     * (Where<条件式用>句)
     * @return 検索対象範囲設定SQL
     */
    private String getTargetSqlWhere() {
        return appSearchRangeInfoLogic.getCondWhere();
    }
    /**
     * 検索対象範囲設定SQL組み立て処理
     * (Where<結合式用>句)
     * @return 検索対象範囲設定SQL
     */
    private List < String > getTargetSqlWheres() {
        return appSearchRangeInfoLogic.getCombWhere();
    }
    /**
     * 検索対象範囲設定SQL組み立て処理
     * (設定済みフラグ)
     * @return 設定済みフラグ(true:設定あり, false：未設定)
     */
    private boolean isSetupFlg() {
        return appSearchRangeInfoLogic.isCondSetupFlg();
    }
    /**
     * 検索対象範囲適用用のFrom句を返します。
     * 返却される文字列は、「From句の最後に付与する形」となります。
     * @return 検索対象範囲適用用From句
     */
    protected String getFromSql() {
        return convertFromListToSql(gFromListMap.get(getDomain()), gWhereListMap.get(getDomain()));
    }
    /**
     * 検索対象範囲適用用のWhere句を返します。
     * 返却される文字列は、「Where句の最後に付与する形」となります。
     * @return 検索対象範囲適用用Where句
     */
    protected String getWhereSql() {
        return replaceTableNameString(
                convertWhereInfoToSql(
                gWhereListMap.get(getDomain()),
                gWheresMap.get(getDomain())),
                gFromListMap.get(getDomain())
        );
    }
    /**
     * 検索対象範囲 退職者フラグ
     * @return 退職者検索対象範囲フラグ
     */
    protected int getCondRetired() {
        return appSearchRangeInfoLogic.getCondRetired();
    }

    /**
     * Request情報を基に、検索対象範囲情報生成ロジックを実行します。
     * 検索対象範囲情報生成ロジックが実行されていない場合にのみ処理が行われます。
     */
    protected void createSearchRange(String psMode) {
        setRequest(ContextUtil.getHttpRequest());
// サイトID、アプリケーションID(V3互換)を取得
        String sSiteId = request.getParameter(PsConst.PARAM_KEY_SITEID);
        String sAppId = request.getParameter(PsConst.PARAM_KEY_APPID);
        if (sSiteId == null) {
            // POSTパラメータとしてサイトIDが渡ってきている場合に対応
            // Action.execメソッド内などを想定
            sSiteId = (String) request.getAttribute(PsConst.PARAM_KEY_SITEID);
            if (sSiteId == null) {
                // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
                // 「psSite=TopPage」の扱いで処理する
                sSiteId = TOP_PAGE;
            }
        }
        // アプリケーションIDの有無にあわせてcreate呼び出し＆ドメイン情報取得準備
        AbstractInfo inf;
        if (sAppId == null) {
            // V4
            create(sSiteId, psMode);
            String sV4AppId = appAuthInfoLogic.getAppId(getApplicationUrl());
            if (sV4AppId != null) {
                // サイトID&アプリケーションIDを利用してアプリ情報オブジェクト取得
                inf = appAuthInfoLogic.getAppInfo(sSiteId, sV4AppId);
            } else {
                // アプリケーションIDが取得できない場合はサイト情報オブジェクト取得
                inf = appAuthInfoLogic.getSiteInfo(sSiteId);
            }
        } else {
            // V3互換
            create(sSiteId, sAppId, psMode);
            inf = appAuthInfoLogic.getAppInfo(sSiteId, sAppId);
        }
        // ドメイン情報を設定
        if (inf != null) {
            String sDomain = inf.getDomainId();
            this.setDomain(sDomain);
            // createしてなかったら処理続行
            if (this.gFromListMap.get(this.getDomain()) != null && this.gWhereListMap.get(this.getDomain()) != null && this.gWheresMap.get(this.getDomain()) != null ) {
                return;
            }
            if (this.getDomain().equals(PsConst.DOMAIN_EMPLOYEE)) {
                appSearchRangeInfoLogic.setPsTargetUser(
                       request.getParameter(PsConst.PARAM_KEY_USERID));
            } else if (this.getDomain().equals(PsConst.DOMAIN_ORGANIZATION)) {
                appSearchRangeInfoLogic.setPsSelectedComp(
                        request.getParameter(PsConst.PARAM_KEY_COMP));
                appSearchRangeInfoLogic.setPsSelectedDept(
                        request.getParameter(PsConst.PARAM_KEY_DEPT));
            }
        }
        // createしてなかったら処理続行
        if (this.gFromListMap.get(this.getDomain()) != null &&
                this.gWhereListMap.get(this.getDomain()) != null &&
                this.gWheresMap.get(this.getDomain()) != null) {
            return;
        }
        // 各種情報取得
        this.gFromListMap.put(this.getDomain(), this.getTargetSqlFroms());
        this.gWhereListMap.put(this.getDomain(), this.getTargetSqlWheres());
        this.gWheresMap.put(this.getDomain(), this.getTargetSqlWhere());
        this.gbSetFlg = this.isSetupFlg();
        // 検索対象範囲クエリで利用する部品を取得
        this.gFromSqlMap.put(this.getDomain(), this.getFromSql());
        this.gWhereSqlMap.put(this.getDomain(), this.getWhereSql());
        // 文字列結合用に、各部品の頭文字部分を置換
        Pattern pattern;
        Matcher matcher;
        // FROM句スタート
        pattern = Pattern.compile(CAMMA);
        matcher = pattern.matcher(this.gFromSqlMap.get(this.getDomain()));
        this.gFromSqlMap.put(this.getDomain(), matcher.replaceFirst(" FROM "));
        if (this.gbSetFlg) {
            // WHERE句スタート
            pattern = Pattern.compile(AND);
            matcher = pattern.matcher(this.gWhereSqlMap.get(this.getDomain()));
            this.gWhereSqlMap.put(this.getDomain(), matcher.replaceFirst(" WHERE "));
        }
    }

    /**
     * Request情報を基に、検索対象範囲情報生成ロジックを実行します。
     * 検索対象範囲情報生成ロジックが実行されていない場合にのみ処理が行われます。
     */
    protected void createSearchRange(String psDomainCode, String psMode) {
        setRequest(ContextUtil.getHttpRequest());
        setDomain(psDomainCode);
        // createしてなかったら処理続行
        if (this.gFromListMap.get(this.getDomain()) != null &&
                this.gWhereListMap.get(this.getDomain()) != null &&
                this.gWheresMap.get(this.getDomain()) != null) {
            return;
        }
        // サイトID、アプリケーションID(V3互換)を取得
        String sSiteId = request.getParameter(PsConst.PARAM_KEY_SITEID);
        String sAppId = request.getParameter(PsConst.PARAM_KEY_APPID);
        if (sSiteId == null) {
            // POSTパラメータとしてサイトIDが渡ってきている場合に対応
            // Action.execメソッド内などを想定
            sSiteId = (String)request.getAttribute(PsConst.PARAM_KEY_SITEID);

            if (sSiteId == null) {
                // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
                // 「psSite=TopPage」の扱いで処理する
                sSiteId = TOP_PAGE;
            }
        }
        // アプリケーションIDの有無にあわせてcreate呼び出し＆ドメイン情報取得準備
        if (sAppId == null) {
            // V4
            createDomain(sSiteId, psDomainCode, psMode);
        } else {
            // V3互換
            createDomain(sSiteId, sAppId, psDomainCode, psMode);
        }
        // ドメイン情報を設定(未設定時のデフォルト状態(自分自身・自組織・自法人)のクエリを作成する為に使用)
        if (StrUtil.equals(getDomain(),PsConst.DOMAIN_EMPLOYEE)) {
            // 従業員ドメイン
            appSearchRangeInfoLogic.setPsTargetUser(request.getParameter(PsConst.PARAM_KEY_USERID));
        } else if (StrUtil.equals(getDomain(),PsConst.DOMAIN_ORGANIZATION)) {
            // 組織ドメイン
            appSearchRangeInfoLogic.setPsSelectedComp(request.getParameter(PsConst.PARAM_KEY_COMP));
            appSearchRangeInfoLogic.setPsSelectedDept(request.getParameter(PsConst.PARAM_KEY_DEPT));
        } else {
            // 法人ドメイン
            appSearchRangeInfoLogic.setPsSelectedComp(request.getParameter(PsConst.PARAM_KEY_COMP));
        }
        // 各種情報取得
        gFromListMap.put(getDomain(), getTargetSqlFroms());
        gWhereListMap.put(getDomain(), getTargetSqlWheres());
        gWheresMap.put(getDomain(), getTargetSqlWhere());
        gbSetFlg = isSetupFlg();

        // 検索対象範囲クエリで利用する部品を取得
        gFromSqlMap.put(this.getDomain(), this.getFromSql());
        gWhereSqlMap.put(this.getDomain(), this.getWhereSql());

        // 文字列結合用に、各部品の頭文字部分を置換
        Pattern pattern;
        Matcher matcher;
        // FROM句スタート
        pattern = Pattern.compile(CAMMA);
        matcher = pattern.matcher(this.gFromSqlMap.get(this.getDomain()));
        this.gFromSqlMap.put(domain, matcher.replaceFirst(" FROM "));

        if (gbSetFlg) {
            // WHERE句スタート
            pattern = Pattern.compile(AND);
            matcher = pattern.matcher(this.gWhereSqlMap.get(this.getDomain()));
            gWhereSqlMap.put(domain, matcher.replaceFirst(" WHERE "));
        }
    }

    /**
     * From句のListを、クエリに埋め込み可能な形にして返します。
     * 「From句の最後に付与する形」＝カンマスタートとなります。
     * @param plFrom 検索対象範囲情報:From句
     * @param plWhere 検索対象範囲情報：Where句(結合式用)
     * @return カンマスタートの文字列
     */
    protected String convertFromListToSql(List < String > plFrom, List < String > plWhere) {
        int nCount = 0;
        StringBuilder sb = new StringBuilder();
        for (String sTempTable : plFrom) {
            sb.append(CAMMA);
            sb.append(SPACE);
            sb.append(sTempTable);
            sb.append(SPACE);
            // テーブル別名をつける：[SR_0],[SR_1],...
            sb.append(PT_ID).append(nCount);
            nCount++;
        }
        // sb.append(this.getAdditionalTable(plWhere));
        return sb.toString();
    }

    /**
     * Where句の情報を、クエリに埋め込み可能な形にして返します。
     * 「Where句の最後に付与する形」＝ANDスタートとなります。
     * @param plWhere 検索対象範囲情報：Where句(結合式用)
     * @param psWheres 検索対象範囲情報：Where句(条件式用)
     * @return ANDスタートの文字列
     */
    protected String convertWhereInfoToSql(List <String> plWhere, String psWheres) {
        // 検索対象範囲情報：Where句(結合式用)
        StringBuilder sb = new StringBuilder();
        if (plWhere != null && !plWhere.isEmpty()) {
            Iterator<String> i = plWhere.iterator();
            while (i.hasNext()) {
                String sQueryWhere = i.next();
                if (StrUtil.isNotBlank(sQueryWhere)) {
                    sb.append(AND).append(sQueryWhere);
                }
            }
        }
        // 検索対象範囲情報：Where句(条件式用)
        if (StrUtil.isNotBlank(psWheres)) {
            sb.append(AND);
            sb.append(psWheres);
        }
        return sb.toString();
    }

    /**
     * あらかじめ指定されたテーブルIDに対応するプレースホルダー部分を置換します。
     * @param psSource パターン文字列を含む検索対象範囲情報の文字列
     * @param plFrom 検索対象範囲情報:From句
     * @return 置換後の文字列
     */
    protected String replaceTableNameString(String psSource, List < String > plFrom) {
        if (StrUtil.isNotBlank(psSource)) {
            // 置換対象文字がある場合にのみ処理実行
            String sReturn = psSource;
            Pattern pattern;
            Matcher matcher;
            // テーブル用のカウンタ
            int n = 0;

            // 条件指定用テーブル部分を置換(置換の都合でiteratorは未使用)
            for (String s : plFrom) {
                // 「##[0-9]*##」 → 「SR_[0-9]*」
                pattern = Pattern.compile(
                        PT_SIGN
                                + String.valueOf(n)
                                + PT_SIGN);
                matcher = pattern.matcher(sReturn);
                sReturn = matcher.replaceAll(PT_ID + String.valueOf(n));

                pattern = Pattern.compile(
                        PT_SIGN
                                + s
                                + PT_SIGN);
                matcher = pattern.matcher(sReturn);

                sReturn = matcher.replaceAll(PT_ID + String.valueOf(n));
                n++;
            }
            // ここまで来て置換漏れがある場合は、設定に何らかの問題があると判断して例外をthrow
            if (sReturn.contains(PT_SIGN)) {
                throw new GlobalException(
                        "SearchRange "+ERR_ESTABLISHED_DATA);
            }
            return sReturn;
        }
        return psSource;
    }

    // ▼ #3161 未設定時は、取得した条件式のみ返却する
    /**
     * あらかじめ指定されたカラムIDに対応するプレースホルダー部分を置換します。
     * @param psSource パターン文字列を含む検索対象範囲情報の文字列
     * @param psRep1 置換対象文字列1
     * @param psRep2 置換対象文字列2
     * @param psRep2 置換対象文字列3
     * @return 置換後の文字列
     */
    protected String replaceColumeNameString(String psSource, String psRep1, String psRep2, String psRep3) {
        String sReturn = psSource;

        // ドメイン毎の固定部分を置換
        if (StrUtil.equals(domain,PsConst.DOMAIN_EMPLOYEE)) {
            // 従業員ドメイン
            if (StrUtil.isNotBlank(psRep1)) {
                sReturn = SysUtil.replaceString(sReturn, "#HD_USER#", psRep1);
            } else {
                sReturn = SysUtil.replaceString(sReturn, "#HD_COMP#", psRep2);
                sReturn = SysUtil.replaceString(sReturn, "#HD_EMPLOYEE#", psRep3);
            }

        } else if (StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION) && StrUtil.isNotBlank(psRep2)) {
            // 組織ドメイン
            sReturn = SysUtil.replaceString(sReturn, "#MO_COMP#", psRep1);
            sReturn = SysUtil.replaceString(sReturn, "#MO_DEPT#", psRep2);
        } else {

            // 法人ドメイン
            sReturn = SysUtil.replaceString(sReturn, "#MC_COMP#", psRep1);

        }
        return sReturn;
    }

    /**
     * URL(PermaLink)情報を取得します
     * @return URL(PermaLink)
     */
    protected String getApplicationUrl() {
        String sSiteId = request.getParameter(PsConst.PARAM_KEY_SITEID);
        StringBuffer sUrl = request.getRequestURL();
        if (StrUtil.isNotBlank(sSiteId)) {
            sUrl.append(LINK_QUEST);
            sUrl.append(request.getQueryString());
        } else {
            // POSTパラメータとしてサイトIDが渡ってきている場合に対応
            // Action.execメソッド内などを想定
            sSiteId = (String) this.request.getAttribute(PsConst.PARAM_KEY_SITEID);
            if (sSiteId == null) {
                // サイトが取得できない場合(ログイン直後＆ダイアログアプリケーションを想定)は
                // 「psSite=TopPage」の扱いで処理する
                sSiteId = TOP_PAGE;
            }
            sUrl.append(LINK_QUEST);
            sUrl.append(PsConst.PARAM_KEY_SITEID);
            sUrl.append(LINK_EQ);
            sUrl.append(sSiteId);
        }
        return sUrl.toString();
    }



    /**
     * ========================业务相关方法================
     * (社員検索用)検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元が組織ドメインに属している場合は空文字が返ります。
     * @param psUserColName ユーザIDとして結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQueryHdId(String psUserColName, String psIdColName) {
        if (StrUtil.isBlank(psUserColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psUserColName "+ ERR_NOT_NULL);
        }
        createSearchRange("1");
        int nCondRetired = getCondRetired();
        StringBuilder sb = new StringBuilder();

        // ▼ #3161 未設定時は、取得した条件式のみ返却する
        if (!gbSetFlg) {
            if(StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION)){
                return "";
            }
            return replaceColumeNameString(gWhereSqlMap.get(domain), psUserColName, "", "");
        }
        if ((StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION) && domainCheckMode)
                || CollUtil.isEmpty(gFromListMap.get(domain))
                || CollUtil.isEmpty(gWhereListMap.get(domain))
                || StrUtil.isBlank(gWheresMap.get(domain))) {
            // 組織ドメインである場合(判定モードの場合のみ)
            // or 検索対象範囲情報が存在しない場合は空文字を返す

            // 退職者検索対象範囲
            // 参照しない(=在職)
            if (nCondRetired == 0) {
                sb.append(AND);			// AND
                sb.append(START);			// (
                sb.append(this.createRetiredQuery(psUserColName, 0));
                sb.append(EXISTS_END);	// )
                return sb.toString();
                // 自社のみ(=在職 + 自社退職)
            } else if (nCondRetired == 1) {
                sb.append(AND);			// AND
                sb.append(START);			// (
                sb.append(START);			// (
                sb.append(this.createRetiredQuery(psUserColName, 0));
                sb.append(EXISTS_END);	// )
                sb.append(OR);			// OR
                sb.append(START);			// (
                sb.append(this.createRetiredQuery(psUserColName, 1));
                sb.append(EXISTS_END);	// )
                sb.append(EXISTS_END);	// )
                return sb.toString();
                // すべて(制限なし)
            } else {
                return "";
            }
        }

        sb.append(AND);
        sb.append(START);	//(
        sb.append(EXISTS_START);	// EXISTS (
        sb.append(SELECT);		//SELECT 1
        sb.append(FROM);	//FROM
        sb.append(START);	//(
        sb.append(CSELECT);		//SELECT

        // カラムに別名を付与する
        boolean bDesig = false;	// 異動歴があるかどうか
        int nIndex = 0;
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {

            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableFromType
                    = scCacheUtil.getTableCombinationType(sFrom);
            if (!StrUtil.equalsIgnoreCase(tableFromType.getTableName(),"HIST_DESIGNATION")) {
                nIndex++;
                continue;
            }
            bDesig = true;    // 異動歴がある
            String sIdColumnName = tableFromType.getIdColumnName();
            if (StrUtil.isNotBlank(sIdColumnName)) {
                //sb.append(PsBuildTargetSqlLogicImpl.AND);
                sb.append(PT_ID).append(nIndex).append(".");
                sb.append(sIdColumnName);
                sb.append(AS);//AS
                sb.append(" EX_");
                sb.append(sIdColumnName);
                break;
            }

            nIndex++;
        }

        // 異動歴がない場合は、getExistsQuery()を返す
        if (!bDesig) {
            return this.getExistsQuery(psUserColName);
        }

        sb.append(this.gFromSqlMap.get(domain));
        sb.append(this.gWhereSqlMap.get(domain));
        sb.append(EXISTS_END);//)
        sb.append(EXSISTB);// EXSISTB
        sb.append(WHERE);// WHERE
        // 指定カラムの結合条件を記述
        nIndex = 0;
        for (String sConditionFrom : this.gFromListMap.get(this.getDomain())) {

            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableCombinationTypeDTO
                    = scCacheUtil.getTableCombinationType(sConditionFrom);
            if (!StrUtil.equalsIgnoreCase(tableCombinationTypeDTO.getTableName(),"HIST_DESIGNATION")) {
                continue;
            }
            // ユーザIDを保有するテーブルに結合条件を記述
            String sIdColumnName = tableCombinationTypeDTO.getIdColumnName();
            if (StrUtil.isNotBlank(sIdColumnName)) {
                // ユーザID
                if (nIndex != 0) {
                    sb.append(AND);
                }
                //sb.append(PsBuildTargetSqlLogicImpl.PT_ID + nIndex + ".");
                sb.append(EXSISTB + ".");
                sb.append("EX_");
                sb.append(sIdColumnName);
                sb.append(" = ");
                sb.append(psIdColName);

                break;
            }

            nIndex++;
        }

        sb.append(EXISTS_END);

        // 退職者検索対象範囲
        // 参照しない(=在職)
        if (nCondRetired == 0) {
            sb.append(AND);			// AND
            sb.append(START);			// (
            sb.append(createRetiredQuery(psUserColName, nCondRetired));
            sb.append(EXISTS_END);	// )
            // 退職者
        } else {
            sb.append(OR);			// OR
            sb.append(START);			// (
            sb.append(this.createRetiredQuery(psUserColName, nCondRetired));
            sb.append(EXISTS_END);	// )
        }

        sb.append(EXISTS_END);	// )

        return sb.toString();
    }

    /**
     * (社員検索用)検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元が組織ドメインに属している場合は空文字が返ります。
     * @param psUserColName ユーザIDとして結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQuery(String psUserColName) {
        if (StrUtil.isBlank(psUserColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psUserColName "+ ERR_NOT_NULL);
        }
        this.createSearchRange("0");
        int nCondRetired = getCondRetired();
        StringBuilder sb = new StringBuilder();
        // ▼ #3161 未設定時は、取得した条件式のみ返却する
        if (!this.gbSetFlg) {
            if(StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION)){
                return "";
            }
            return replaceColumeNameString(gWhereSqlMap.get(this.getDomain()), psUserColName, "", "");
        }
        if ((StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION)
                && domainCheckMode)
                || CollUtil.isEmpty(gFromListMap.get(domain))
                || CollUtil.isEmpty(gWhereListMap.get(domain))
                || StrUtil.isBlank(gWheresMap.get(domain))
        ) {
            // 組織ドメインである場合(判定モードの場合のみ)
            // or 検索対象範囲情報が存在しない場合は空文字を返す

            // 退職者検索対象範囲
            // 参照しない(=在職)
            if (nCondRetired == 0) {
                sb.append(AND);			// AND
                sb.append(START);			// (
                sb.append(createRetiredQuery(psUserColName, 0));
                sb.append(EXISTS_END);	// )
                return sb.toString();
                // 自社のみ(=在職 + 自社退職)
            } else if (nCondRetired == 1) {
                sb.append(AND);			// AND
                sb.append(START);			// (
                sb.append(START);			// (
                sb.append(this.createRetiredQuery(psUserColName, 0));
                sb.append(EXISTS_END);	// )
                sb.append(OR);			// OR
                sb.append(START);			// (
                sb.append(this.createRetiredQuery(psUserColName, 1));
                sb.append(EXISTS_END);	// )
                sb.append(EXISTS_END);	// )
                return sb.toString();
                // すべて(制限なし)
            } else {
                return "";
            }
        }

        sb.append(AND);
        sb.append(START);	//(
        sb.append(EXISTS_START);	// EXISTS (
        sb.append(SELECT);		//SELECT 1
        sb.append(FROM);	//FROM
        sb.append(START);	//(
        sb.append(CSELECT);		//SELECT

        // ヒントを付ける
        int nIndex = 0;
        StringBuilder sbHint = new StringBuilder();
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 異動歴ならHintを追加
            if (StrUtil.equalsIgnoreCase(sFrom,"HIST_DESIGNATION")) {
                sbHint.append(" INDEX (" + PT_ID).append(nIndex).append(" ").append(IDX_HIST_DESIGNATION).append(" ) ");
            }
            nIndex++;

        }
        if (sbHint.length() > 0) {
            sb.append(" /*+ ");
            sb.append(sbHint);
            sb.append(" */ ");
        }

        // カラムに別名を付与する
        nIndex = 0;
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableFromType
                    = scCacheUtil.getTableCombinationType(sFrom);
            String sUserIdColumnName = tableFromType.getUserIdColumnName();
            if (StrUtil.isNotBlank(sUserIdColumnName)) {
                // ユーザID
                if (nIndex != 0) {
                    sb.append(",");
                }
                //sb.append(PsBuildTargetSqlLogicImpl.AND);
                sb.append(PT_ID).append(nIndex).append(".");
                sb.append(sUserIdColumnName);
                sb.append(AS);//AS
                sb.append(" EX_");
                sb.append(sUserIdColumnName);
                break;
            }

            nIndex++;
        }
        sb.append(gFromSqlMap.get(domain));
        sb.append(gWhereSqlMap.get(domain));
        //)
        sb.append(EXISTS_END);
        // EXSISTB
        sb.append(EXSISTB);
        // WHERE
        sb.append(WHERE);
        // 指定カラムの結合条件を記述
        nIndex = 0;
        for (String sConditionFrom : gFromListMap.get(domain)) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableCombinationTypeDTO
                    = scCacheUtil.getTableCombinationType(sConditionFrom);
            // ユーザIDを保有するテーブルに結合条件を記述
            String sUserIdColumnName = tableCombinationTypeDTO.getUserIdColumnName();
            if (StrUtil.isNotBlank(sUserIdColumnName)) {
                // ユーザID
                if (nIndex != 0) {
                    sb.append(AND);
                }
                //sb.append(PsBuildTargetSqlLogicImpl.PT_ID + nIndex + ".");
                sb.append(EXSISTB + ".");
                sb.append("EX_");
                sb.append(sUserIdColumnName);
                sb.append(" = ");
                sb.append(psUserColName);

                break;
            }

            nIndex++;
        }

        sb.append(EXISTS_END);

        // 退職者検索対象範囲
        // 参照しない(=在職)
        if (nCondRetired == 0) {
            sb.append(AND);			// AND
            sb.append(START);			// (
            sb.append(this.createRetiredQuery(psUserColName, nCondRetired));
            sb.append(EXISTS_END);	// )
            // 退職者
        } else {
            sb.append(OR);			// OR
            sb.append(START);			// (
            sb.append(createRetiredQuery(psUserColName, nCondRetired));
            sb.append(EXISTS_END);	// )
        }

        sb.append(EXISTS_END);	// )

        return sb.toString();
    }

    /**
     * (組織検索用)検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元が従業員ドメインに属している場合は空文字が返ります。
     * @param psCompColName 法人コードとして結合されるカラムID
     * @param psOrgColname 組織コードとして結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQuery(String psCompColName, String psOrgColname) {
        if (StrUtil.isBlank(psCompColName)
                || StrUtil.isBlank(psOrgColname)
        ) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psCompColName/psOrgColname "+ ERR_NOT_NULL);
        }
        this.createSearchRange("0");
        // ▼ #3161 未設定時は、取得した条件式のみ返却する
        if (!this.gbSetFlg && StrUtil.equals(domain,PsConst.DOMAIN_ORGANIZATION)) {
            return replaceColumeNameString(this.gWhereSqlMap.get(this.getDomain()), psCompColName, psOrgColname, "");
        }
        if ((StrUtil.equals(domain,PsConst.DOMAIN_EMPLOYEE) && domainCheckMode)
                || CollUtil.isEmpty(gFromListMap.get(domain))
                || CollUtil.isEmpty(gWhereListMap.get(domain))
                || StrUtil.isBlank(gWheresMap.get(domain))
        ) {
            // 従業員ドメインである場合(判定モードの場合のみ)
            // or 検索対象範囲情報が存在しない場合は空文字を返す
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(AND);//AND
        sb.append(EXISTS_START);//EXISTS(
        sb.append(SELECT);//SELECT 1
        sb.append(FROM);//FROM
        sb.append(START);//(
        sb.append(CSELECT);//SELECT

        // 指定カラムの別名を付与
        int nIndex = 0;
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableFromType
                    = scCacheUtil.getTableCombinationType(sFrom);
            // 法人コードと組織コードの両方を保有するテーブルに結合条件を記述
            String sCompanyIdColumnName = tableFromType.getCompanyIdColumnName();
            String sSectionIdColumnName = tableFromType.getSectionIdColumnName();
            if (StrUtil.isNotBlank(sCompanyIdColumnName) && StrUtil.isNotBlank(sSectionIdColumnName)) {
                if (nIndex != 0) {
                    sb.append(",");
                }
                // 法人コード
                //sb.append(PsBuildTargetSqlLogicImpl.AND);
                sb.append(PT_ID).append(nIndex).append(".");
                sb.append(sCompanyIdColumnName);
                sb.append(AS);
                sb.append("EX_");
                sb.append(sCompanyIdColumnName);

                // 組織コード
                sb.append(",");
                sb.append(PT_ID).append(nIndex).append(".");
                sb.append(sSectionIdColumnName);
                sb.append(AS);
                sb.append("EX_");
                sb.append(sSectionIdColumnName);

                break;
            }

            nIndex++;
        }

        sb.append(this.gFromSqlMap.get(this.getDomain()));
        sb.append(this.gWhereSqlMap.get(this.getDomain()));
        // )
        sb.append(EXISTS_END);
        // EXSISTB
        sb.append(EXSISTB);
        // WHERE
        sb.append(WHERE);

        // 指定カラムの結合条件を記述
        nIndex = 0;
        for (String sConditionFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableCombinationTypeDTO
                    = scCacheUtil.getTableCombinationType(sConditionFrom);
            // 法人コードと組織コードの両方を保有するテーブルに結合条件を記述
            String sCompanyIdColumnName = tableCombinationTypeDTO.getCompanyIdColumnName();
            String sSectionIdColumnName = tableCombinationTypeDTO.getSectionIdColumnName();
            if (StrUtil.isNotBlank(sCompanyIdColumnName) && StrUtil.isNotBlank(sSectionIdColumnName)) {
                // 法人コード
                if (nIndex != 0) {
                    sb.append(AND);
                }
                sb.append(EXSISTB + ".");
                sb.append("EX_");
                sb.append(sCompanyIdColumnName);
                sb.append(" = ");
                sb.append(psCompColName);

                // 組織コード
                sb.append(AND);
                sb.append(EXSISTB + ".");
                sb.append("EX_");
                sb.append(sSectionIdColumnName);
                sb.append(" = ");
                sb.append(psOrgColname);

                break;
            }
            nIndex++;
        }
        sb.append(EXISTS_END);
        return sb.toString();
    }

    /**
     * 従業員検索用の検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元のドメインに依存なくEXISTS句を返却します。
     * @param psCompanyColName 法人コードとして結合されるカラムID
     * @param psEmployeeColName 社員番号として結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQueryEmployee(String psCompanyColName, String psEmployeeColName) {
        // パラメータエラーチェック
        if (StrUtil.isBlank(psCompanyColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psCompanyColName "+ERR_NOT_NULL);
        } else if (StrUtil.isBlank(psEmployeeColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psEmployeeColName "+ERR_NOT_NULL);
        }
        // 検索対象範囲情報取得(従業員ドメイン)
        createSearchRange(PsConst.DOMAIN_EMPLOYEE, "0");
        String sExistsQuery = null;
        if (gbSetFlg) {
            // 指定カラムの結合条件を記述
            StringBuilder sbExistsQuery = new StringBuilder();
            int nIndex = 0;
            for (String sConditionFrom : this.gFromListMap.get(this.getDomain())) {
                // 対象テーブル取得
                // 使用するテーブルの情報を取得
                TableCombinationTypeDTO tableCombinationTypeDTO
                        = scCacheUtil.getTableCombinationType(sConditionFrom);
                // 法人コードと組織コードの両方を保有するテーブルに結合条件を記述
                if (StrUtil.isNotBlank(tableCombinationTypeDTO.getCompanyIdColumnName()) &&
                        StrUtil.isNotBlank(tableCombinationTypeDTO.getEmployeeIdColumnName())) {
                    // 法人コード
                    if (nIndex != 0) {
                        sbExistsQuery.append(AND);
                    }
                    sbExistsQuery.append(EXSISTB + ".");
                    sbExistsQuery.append("EX_");
                    sbExistsQuery.append(tableCombinationTypeDTO.getCompanyIdColumnName());
                    sbExistsQuery.append(" = ");
                    sbExistsQuery.append(psCompanyColName);

                    // 社員番号
                    sbExistsQuery.append(AND);
                    sbExistsQuery.append(EXSISTB + ".");
                    sbExistsQuery.append("EX_");
                    sbExistsQuery.append(tableCombinationTypeDTO.getEmployeeIdColumnName());
                    sbExistsQuery.append(" = ");
                    sbExistsQuery.append(psEmployeeColName);

                    break;
                }

                nIndex++;
            }

            sExistsQuery = getExistsQueryCommon(sbExistsQuery.toString());

            // 退職者検索対象範囲
            StringBuilder sb = new StringBuilder();
            int nCondRetired = getCondRetired();
            // 条件がある場合
            if (StrUtil.isNotBlank(sExistsQuery)) {
                sb.append(" AND ( ( ");
                sb.append(sExistsQuery);
                // 参照しない (=在職)
                if (nCondRetired == 0) {
                    sb.append(" ) AND ( ");
                    sb.append(createRetiredQuery(psCompanyColName, psEmployeeColName, nCondRetired));
                    // 自社のみ / すべて
                } else {
                    sb.append(" ) OR ( ");
                    sb.append(createRetiredQuery(psCompanyColName, psEmployeeColName, nCondRetired));
                }
                sb.append(" ) ) ");
                // 条件がない場合
            } else {
                // 参照しない (=在職)
                if (nCondRetired == 0) {
                    sb.append(" AND ");
                    sb.append(createRetiredQuery(psCompanyColName, psEmployeeColName, 0));
                    // 自社のみ (=在職 + 自社退職)
                } else if (nCondRetired == 1) {
                    sb.append(" AND ( ( ");
                    sb.append(createRetiredQuery(psCompanyColName, psEmployeeColName, 0));
                    sb.append(" ) OR ( ");
                    sb.append(createRetiredQuery(psCompanyColName, psEmployeeColName, 1));
                    sb.append(" ) ) ");
                    // 全て (条件無し)
                }
            }
            sExistsQuery = sb.toString();
        } else {

            String sWheres = " AND '" + gPsSession.getLoginUser() + "' = '" + gPsSession.getLoginUser() + "'" +
                    " AND " + "#HD_COMP#" + " = '" + gPsSession.getLoginCompany() + "'" +
                    " AND " + "#HD_EMPLOYEE#" + " = '" + gPsSession.getLoginEmployee() + "'";
            sExistsQuery = replaceColumeNameString(sWheres, "", psCompanyColName, psEmployeeColName);
            //sExistsQuery = this.replaceColumeNameString(this.gWhereSqlMap.get(this.getDomain()), "", psCompanyColName, psEmployeeColName);
        }

        return sExistsQuery;
    }

    /**
     * 組織検索用の検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元のドメインに依存なくEXISTS句を返却します。
     * @param psCompanyColName 法人コードとして結合されるカラムID
     * @param psOrganisationColName 組織コードとして結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQueryOrganisation(String psCompanyColName, String psOrganisationColName) {
        // パラメータエラーチェック
        if (StrUtil.isBlank(psCompanyColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psCompanyColName "+ERR_NOT_NULL);
        } else if (StrUtil.isBlank(psOrganisationColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psOrganisationColName "+ ERR_NOT_NULL);
        }

        // 検索対象範囲情報取得(組織ドメイン)
        createSearchRange(PsConst.DOMAIN_ORGANIZATION, "0");
        // 変数初期化
        String sExistsQuery = null;
        boolean bExistsUsedFlag = false;
        String sCompanyIdColumnName = null;
        String sSectionIdColumnName = null;
        if (gbSetFlg) {
            // 指定カラムの結合条件を記述
            StringBuilder sbExistsQuery = new StringBuilder();
            for (String sConditionFrom : gFromListMap.get(this.getDomain())) {
                // 対象テーブル取得
                // 使用するテーブルの情報を取得
                TableCombinationTypeDTO tableCombinationTypeDTO
                        = scCacheUtil.getTableCombinationType(sConditionFrom);

                // 法人コードと組織コードの両方を保有するテーブルに結合条件を記述
                sCompanyIdColumnName = tableCombinationTypeDTO.getCompanyIdColumnName();
                sSectionIdColumnName = tableCombinationTypeDTO.getSectionIdColumnName();
                // カラムを持っていないテーブル用判定
                if (StrUtil.isNotBlank(sCompanyIdColumnName) && StrUtil.isNotBlank(sSectionIdColumnName)) {
                    // 一件目はMAST_ORGANISATIONなので結合式準備
                    if (!bExistsUsedFlag) {
                        // 法人コード
                        sbExistsQuery.append(EXSISTB + ".");
                        sbExistsQuery.append("EX_");
                        sbExistsQuery.append(sCompanyIdColumnName);
                        sbExistsQuery.append(" = ");
                        sbExistsQuery.append(psCompanyColName);
                        // 組織コード
                        sbExistsQuery.append(AND);
                        sbExistsQuery.append(EXSISTB + ".");
                        sbExistsQuery.append("EX_");
                        sbExistsQuery.append(sSectionIdColumnName);
                        sbExistsQuery.append(" = ");
                        sbExistsQuery.append(psOrganisationColName);
                    }
                    bExistsUsedFlag = true;

                } else {
                    bExistsUsedFlag = false;
                    break;
                }
            }
            // 渡された値と結合できない場合は絞込を行わない
            if (bExistsUsedFlag){
                // 返却
                sExistsQuery = getExistsQueryCommon(sbExistsQuery.toString());
                if (StrUtil.isNotBlank(sExistsQuery)) {
                    sExistsQuery = AND + sExistsQuery;
                }
            }else{
                sExistsQuery = "";
            }
        } else {
            sExistsQuery = replaceColumeNameString(gWhereSqlMap.get(domain), psCompanyColName, psOrganisationColName, "");
        }

        return sExistsQuery;
    }

    /**
     * 法人検索用の検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元のドメインに依存なくEXISTS句を返却します。
     * @param psCompanyColName 法人コードとして結合されるカラムID
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    public String getExistsQueryCompany(String psCompanyColName) {

        // パラメータエラーチェック
        if (StrUtil.isBlank(psCompanyColName)) {
            // カラム指定されていないので例外
            throw new GlobalException(
                    "psCompanyColName"+ERR_NOT_NULL);
        }
        // 検索対象範囲情報取得(法人ドメイン)
        createSearchRange("03", "1");
        String sExistsQuery;
        if (gbSetFlg) {
            // 指定カラムの結合条件を記述
            StringBuilder sbExistsQuery = new StringBuilder();
            int nIndex = 0;
            for (String sConditionFrom : this.gFromListMap.get(this.getDomain())) {
                // 対象テーブル取得
                // 使用するテーブルの情報を取得
                TableCombinationTypeDTO tableCombinationTypeDTO
                        = scCacheUtil.getTableCombinationType(sConditionFrom);
                // 法人コードを保有するテーブルに結合条件を記述
                // 法人コードと組織コードの両方を保有するテーブルに結合条件を記述
                String sCompanyIdColumnName = tableCombinationTypeDTO.getCompanyIdColumnName();
                if (StrUtil.isNotBlank(sCompanyIdColumnName)) {
                    // 法人コード
                    if (nIndex != 0) {
                        sbExistsQuery.append(AND);
                    }
                    sbExistsQuery.append(EXSISTB + ".");
                    sbExistsQuery.append("EX_");
                    sbExistsQuery.append(sCompanyIdColumnName);
                    sbExistsQuery.append(" = ");
                    sbExistsQuery.append(psCompanyColName);

                    break;
                }

                nIndex++;
            }

            sExistsQuery = getExistsQueryCommon(sbExistsQuery.toString());
            if (StrUtil.isNotBlank(sExistsQuery)) {
                sExistsQuery = AND + sExistsQuery;
            }
        } else {
            sExistsQuery = replaceColumeNameString(gWhereSqlMap.get(this.getDomain()), psCompanyColName, "", "");
        }
        return sExistsQuery;
    }

    /**
     * 退職者検索対象範囲のクエリを生成します
     * @param psCompanyColName 法人カラム
     * @param psEmployeeColName 社員番号カラム
     * @param nCondRetired 退職者検索対象範囲(0:参照しない/1:自社のみ/2:すべて)
     * @return クエリ "ユーザIDカラム in (退職者検索対象範囲)"
     * @exception
     */
    private String createRetiredQuery(String psCompanyColName, String psEmployeeColName, int nCondRetired) {
        StringBuilder sb = new StringBuilder();
        sb.append(" EXISTS ( SELECT 1 FROM ( SELECT /*+ INDEX(MAST_EMPLOYEES " + IDX_MAST_EMPLOYEES + ") */ ME_CCOMPANYID AS EX_ME_CCOMPANYID, ME_CEMPLOYEEID_CK AS EX_ME_CEMPLOYEEID_CK ");
        sb.append(" FROM MAST_EMPLOYEES WHERE ");
        sb.append(" ME_CCUSTOMERID_CK = '").append(gPsSession.getLoginCustomer()).append("' ");
        sb.append(" AND ME_DSTARTDATE <= ").append(getPsSecurityDate()).append(" ");
        sb.append(" AND ME_DENDDATE >= ").append(getPsSecurityDate()).append(" ");
        switch (nCondRetired) {
            case 0:	// 参照しない (=在職)
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '0' ");
                break;
            case 1:	// 自社のみ
                sb.append(" AND ME_CCOMPANYID = '").append(gPsSession.getLoginCompany()).append("' ");
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '1' ");
                break;
            case 2:	// すべて
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '1' ");
                break;
            default:
                break;
        }
        sb.append(" ) EXSIST_RETIRED ");
        sb.append("WHERE EXSIST_RETIRED.EX_ME_CCOMPANYID = ").append(psCompanyColName).append(" ");
        sb.append("AND EXSIST_RETIRED.EX_ME_CEMPLOYEEID_CK = ").append(psEmployeeColName).append(" ");
        sb.append(" ) ");
        return sb.toString();
    }

    /**
     * 検索対象範囲を適用するEXISTS句条件を返します。
     * 返却される文字列は「Where句の最後に付与する形(ANDスタート)」となります。
     * 呼び出し元のドメインに依存なくEXISTS句を返却します。
     * @param psIndividualCondition 検索対象依存条件
     * @return 検索対象範囲を適用するEXISTS句条件
     */
    private String getExistsQueryCommon(String psIndividualCondition) {

        boolean isValid = CollUtil.isEmpty(gFromListMap.get(domain)) ||
                CollUtil.isEmpty(gWhereListMap.get(domain))||
                StrUtil.isBlank(gWheresMap.get(domain));
        // 検索対象範囲情報が存在しない場合は空文字を返す
        if (isValid) {
            return "";
        }
        // EXISTS QUERY組立て
        StringBuilder sb = new StringBuilder();
        sb.append(EXISTS_START);
        // SELECT 1
        sb.append(SELECT);
        // FROM
        sb.append(FROM);
        // (
        sb.append(START);
        // SELECT
        sb.append(CSELECT);

        int nIndex = 0;
        for (String sConditionFrom : gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableCombinationTypeDTO
                    = scCacheUtil.getTableCombinationType(sConditionFrom);
            String sCompIdColumnName = tableCombinationTypeDTO.getCompanyIdColumnName();
            String sSectionIdColumnName = tableCombinationTypeDTO.getSectionIdColumnName();
            String sEmployeeColumnName = tableCombinationTypeDTO.getEmployeeIdColumnName();

            if (StrUtil.equals(domain,"01")) {
                // 別名付与(従業員）
                boolean isColValid = StrUtil.isNotBlank(tableCombinationTypeDTO.getCompanyIdColumnName()) &&
                        StrUtil.isNotBlank(tableCombinationTypeDTO.getEmployeeIdColumnName());
                if (isColValid) {
                    // 法人コード
                    //sb.append(PsBuildTargetSqlLogicImpl.AND);
                    if (nIndex != 0) {
                        sb.append(",");
                    }
                    sb.append(PT_ID).append(nIndex).append(".");
                    sb.append(tableCombinationTypeDTO.getCompanyIdColumnName());
                    sb.append(AS);
                    sb.append(" EX_");
                    sb.append(sCompIdColumnName);
                    sb.append(",");

                    // 社員番号
                    //sb.append(PsBuildTargetSqlLogicImpl.AND);
                    sb.append(PT_ID).append(nIndex).append(".");
                    sb.append(tableCombinationTypeDTO.getEmployeeIdColumnName());
                    sb.append(AS);
                    sb.append(" EX_");
                    sb.append(sEmployeeColumnName);

                    break;
                }
            } else if (StrUtil.equals(domain,"02")) {
                //別名付与（組織）

                if (StrUtil.isNotBlank(sCompIdColumnName) && StrUtil.isNotBlank(sSectionIdColumnName)) {
                    if (nIndex != 0) {
                        sb.append(",");
                    }
                    // 法人コード
                    //sb.append(PsBuildTargetSqlLogicImpl.AND);
                    sb.append(PT_ID).append(nIndex).append(".");
                    sb.append(sCompIdColumnName);
                    sb.append(AS);
                    sb.append("EX_");
                    sb.append(sCompIdColumnName);

                    // 組織コード
                    sb.append(",");
                    sb.append(PT_ID).append(nIndex).append(".");
                    sb.append(sSectionIdColumnName);
                    sb.append(AS);
                    sb.append("EX_");
                    sb.append(sSectionIdColumnName);

                    break;
                }

            } else {
                //別名付与（法人)
                if (StrUtil.isNotBlank(sCompIdColumnName)) {
                    // 法人コード
                    //sb.append(PsBuildTargetSqlLogicImpl.AND);
                    sb.append(PT_ID).append(nIndex).append(".");
                    sb.append(sCompIdColumnName);
                    sb.append(AS);
                    sb.append("EX_");
                    sb.append(sCompIdColumnName);

                    break;
                }
            }

            nIndex++;
        }

        sb.append(gFromSqlMap.get(domain));
        sb.append(gWhereSqlMap.get(domain));
        sb.append(EXISTS_END);
        sb.append(EXSISTB);
        sb.append(WHERE);
        sb.append(psIndividualCondition);
        sb.append(EXISTS_END);
        return sb.toString();
    }

    /**
     * ドメイン判定モードの設定を行います。
     * @param pbDomainMode ドメインの判定有無 true:判定する(デフォルト)、false:判定しない
     */
    public void setDomainCheckMode(Boolean pbDomainMode) {
        this.domainCheckMode = pbDomainMode;
    }

    /**
     * (社員検索用)検索対象範囲で参照出来る異動歴を返します。
     * 返却される文字列は「( SELECT * FROM HIST_DESIGNATION WHERE ...)」となります。
     * 呼び出し元が組織ドメインに属している場合は「HIST_DESIGNATION」が返ります。
     * @return 検索対象範囲で参照出来る異動歴
     */
    public String getDesignationQuery() {
        createSearchRange("0");
        StringBuilder sb = new StringBuilder();
        // 未設定、組織ドメイン、条件がない
        if (!gbSetFlg
                || StrUtil.equals(domain, PsConst.DOMAIN_ORGANIZATION)
                || CollUtil.isEmpty(gFromListMap.get(domain))
                || CollUtil.isEmpty(gWhereListMap.get(domain))
                || StrUtil.isBlank(this.gWheresMap.get(domain))
        ) {
            return "HIST_DESIGNATION";
        }
        sb.append(" ( SELECT ");
        // ヒント
        int nIndex = 0;
        StringBuilder sbHint = new StringBuilder();
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 異動歴ならHintを追加
            if (StrUtil.equalsIgnoreCase(sFrom,"HIST_DESIGNATION")) {
                sbHint.append(" INDEX (" + PT_ID).append(nIndex).append(" ").append(IDX_HIST_DESIGNATION).append(" ) ");
            }
            nIndex++;
        }
        if (sbHint.length() > 0) {
            sb.append(" /*+ ");
            sb.append(sbHint);
            sb.append(" */ ");
            // 異動歴がない
        } else {
            return "HIST_DESIGNATION";
        }
        // 異動歴のIDカラム
        nIndex = 0;
        for (String sFrom : this.gFromListMap.get(this.getDomain())) {
            // 対象テーブル取得
            // 使用するテーブルの情報を取得
            TableCombinationTypeDTO tableFromType = scCacheUtil.getTableCombinationType(sFrom);
            // 異動歴の場合
            if (StrUtil.equalsIgnoreCase(tableFromType.getTableName(),"HIST_DESIGNATION")) {
                // 異動歴のレコード
                sb.append(PT_ID).append(nIndex).append(".* ");
                break;
            }
            nIndex++;
        }
        sb.append(this.gFromSqlMap.get(this.getDomain()));
        sb.append(this.gWhereSqlMap.get(this.getDomain()));
        sb.append(" ) ");
        return sb.toString();
    }

    /**
     * 退職者検索対象範囲のクエリを生成します
     * @param sColName ユーザIDカラム
     * @param nCondRetired 退職者検索対象範囲(0:参照しない/1:自社のみ/2:すべて)
     * @return クエリ "ユーザIDカラム in (退職者検索対象範囲)"
     */
    private String createRetiredQuery(String sColName, int nCondRetired) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(sColName);
        sb.append(" IN ( SELECT /*+ INDEX(MAST_EMPLOYEES " + IDX_MAST_EMPLOYEES + ") */ ME_CUSERID FROM MAST_EMPLOYEES WHERE ");
        sb.append(" ME_CCUSTOMERID_CK = '").append(gPsSession.getLoginCustomer()).append("' ");
        sb.append(" AND ME_DSTARTDATE <= ").append(getPsSecurityDate()).append(" ");
        sb.append(" AND ME_DENDDATE >= ").append(getPsSecurityDate()).append(" ");
        switch (nCondRetired) {
            case 0:	// 参照しない (=在職)
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '0' ");
                break;
            case 1:	// 自社のみ
                sb.append(" AND ME_CCOMPANYID = '").append(this.gPsSession.getLoginCompany()).append("' ");
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '1' ");
                break;
            case 2:	// すべて
                sb.append(" AND ME_CIFSTILLEMPLOYEDID = '1' ");
                break;
        }
        sb.append(" ) ");
        return sb.toString();
    }

}
