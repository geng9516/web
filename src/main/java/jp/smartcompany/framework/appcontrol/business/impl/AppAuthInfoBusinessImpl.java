package jp.smartcompany.framework.appcontrol.business.impl;

import cn.hutool.cache.impl.LRUCache;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jp.smartcompany.boot.common.Constant;
import jp.smartcompany.boot.util.ContextUtil;
import jp.smartcompany.framework.appcontrol.AppInfo;
import jp.smartcompany.framework.appcontrol.SiteInfo;
import jp.smartcompany.framework.appcontrol.TopPageInfo;
import jp.smartcompany.framework.appcontrol.business.AppAuthInfoBusiness;
import jp.smartcompany.framework.auth.business.AppAuthJudgmentBusiness;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * アプリケーション起動権限情報取得のLogicインターフェース
 * @author Xiao Wenpeng
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppAuthInfoBusinessImpl implements AppAuthInfoBusiness {

    private static final String SITE_HTML = "/Site.html";
    private static final String PHOTO_SERVLET = "/PhotoServlet";
    //2007/08/24 V3アプリケーション起動かを判断する(URLに含まれていればV3App起動）
    private static final String V3APP_HTML = "iFrame.html";
    private final AppAuthJudgmentBusiness appAuthJudgmentBusiness;
    /** Session保持情報格納クラス **/
    private PsSession gPsSession;
    /** HttpServletRequestクラス **/
    private HttpServletRequest gRequest;
    private final LRUCache<Object,Object> scCache;


    /**
     * ログイン情報コンポーネントの取得（自動インジェクション）
     *
     * @param pPsSession ログイン情報
     */
    public void setPsSession(PsSession pPsSession) {
        this.gPsSession = pPsSession;
    }

    /**
     * HttpServletRequestの取得（自動インジェクション）
     *
     * @param pRequest Request情報
     */
    public void setRequest(HttpServletRequest pRequest) {
        this.gRequest = pRequest;
    }

    /**
     * アプリケーションIDを取得します。
     * @param sUrl URL
     * @return sAppId アプリケーションID
     */
    @Override
    public String getAppId(String sUrl) {
        setRequest(ContextUtil.getHttpRequest());
        // ▼ 2007/07/31 Saito
        // サイト検索時のアプリケーションIDはnull
        if (sUrl.contains(SITE_HTML)) {
            return null;
        }
        // PhotoServlet
        if (sUrl.contains(PHOTO_SERVLET)) {
            return null;
        }
        // ▲ 2007/07/31 Saito

        //▼2007/08/24 Konno 複数箇所で使うのでメソッド化
        List<String> lUrl = CollUtil.newArrayList();

        // サイトID取得
        String sSiteId = getSiteId(sUrl);

        //▼ 2007/08/24 V3互換アプリケーション起動時はパラメータより値を取得
        // サイトID返却値
        String sAppId = gRequest.getParameter(PsConst.PARAM_KEY_APPID);
        // リクエストパラメータの取得
        //▲ 2007/08/24
        AppInfo appInfo;
        // アプリケーションIDの取得(サブアプリケーションが無い場合の位置)
//        System.out.println("-----+======");
//        System.out.println(lUrl);
//        sAppId = lUrl.get(lUrl.size() - 3);

        // アプリケーションIDのチェック
        appInfo = this.getAppInfo(sSiteId, sAppId);
        if (appInfo != null) {
            if (appInfo.getAppID().equals(sAppId)) {
                return sAppId;
            }
        }
        // アプリケーションIDの取得(サブアプリケーションが有る場合の位置)
//        sAppId = lUrl.get(lUrl.size() - 4);
        // アプリケーションIDのチェック
        appInfo = this.getAppInfo(sSiteId, sAppId);
        if (appInfo != null) {
            if (appInfo.getAppID().equals(sAppId)) {
                return sAppId;
            }
        }
        return null;
    }

    /**
     * サイトIDを取得します。
     * @param sUrl URL
     * @return sSiteId サイトID
     */
    @Override
    public String getSiteId(String sUrl) {

        // サイトID返却値
        String sSiteId = null;

        // リクエストパラメータの取得
        if(sUrl.contains("?psSite=")) {

            // URL分割リスト
            List < String > lUrl = new ArrayList < String > ();

            // URLの要素処理
            StringTokenizer strTokensUrl = new StringTokenizer(sUrl, "=");

            while (strTokensUrl.hasMoreTokens()) {

                lUrl.add(strTokensUrl.nextToken());
            }

            // サイトID取得
            sSiteId = (String) lUrl.get(1);

            if (sSiteId.contains("&")) {

                sSiteId = sSiteId.substring(0, sSiteId.indexOf("&"));
            }

        } else if(sUrl.contains("&psSite=")) {

            // URL分割リスト
            List < String > lUrl = new ArrayList < String > ();

            // URLの要素処理
            StringTokenizer strTokensUrl = new StringTokenizer(sUrl, "&");

            while (strTokensUrl.hasMoreTokens()) {

                lUrl.add(strTokensUrl.nextToken());
            }

            for (String s : lUrl) {

                if (s.contains("psSite=")) {

                    sSiteId = s.substring(7);
                }
            }
        }
        if(sSiteId == null) {
            String sPsSite = gRequest.getParameter("psSite");
            if(sPsSite != null) {
                sSiteId = sPsSite;
            }
            else {
                sSiteId = "TopPage";
            }
        }
        return sSiteId;
    }

    /**
     * ダイアログアプリケーション情報をListで取得します。
     *
     * @return lApp ダイアログアプリケーション情報リスト
     */
    public List <AppInfo> getDlgAppList() {
        // 返却用
        List <AppInfo> appInfoList = new ArrayList < AppInfo >();
        TopPageInfo topPageInfo = gPsSession.getLoginAppPermission();
        if (topPageInfo == null) {
            return appInfoList;
        }
        Map<String, AppInfo> dlgAppInfoMap = topPageInfo.getDlgAppInfo();
        if (dlgAppInfoMap == null) {
            return appInfoList;
        }
        Set set = dlgAppInfoMap.entrySet();
        for (AppInfo appInfo : (Iterable<AppInfo>) set) {
            Map.Entry entry = (Map.Entry) appInfo;
            appInfoList.add((AppInfo) entry.getValue());
        }
        return appInfoList;
    }

    /**
     * 指定アプリケーションに属するダイアログアプリケーション情報を取得します。
     *
     * @param sAppId アプリケーションID
     * @return DlgAppInfo ダイアログアプリケーション情報
     */
    @Override
    public AppInfo getDlgAppInfo(String sAppId) {
        // トップページ情報のダイアログアプリケーション情報Mapを取得
        Map<String, AppInfo> hDlgAppInfo = gPsSession.getLoginAppPermission().getDlgAppInfo();
        // 指定ダイアログアプリケーション情報を取得
        if (hDlgAppInfo != null) {
            String sDlgAppKey = "Site:TopPage_Dlg:" + sAppId;
            if (hDlgAppInfo.containsKey(sDlgAppKey)) {
                return hDlgAppInfo.get(sDlgAppKey);
            }
        }
        // #1794,1878
//        // 例外処理
//        throw new ParameterInvalidException("アプリケーションID", "");
        return null;

    }

    @Override
    public AppInfo getAppInfo(String sSiteId, String sAppId) {
        // サイト情報のアプリケーション情報Mapを取得
        // サイトがトップページだった場合に対応
        if (sSiteId == null || "".equals(sSiteId) || "TopPage".equals(sSiteId)) {
            sSiteId = "TopPage";
            // ダイアログアプリケーションのチェックから
            List<AppInfo> dlgAppList = getDlgAppList();
            if (dlgAppList != null && !dlgAppList.isEmpty()) {
                AppInfo dlgAppInfo = getDlgAppInfo(sAppId);
                if (dlgAppInfo != null) {
                    return dlgAppInfo;
                }
            }
            // ダイアログアプリケーションではない場合にアプリケーションのチェック
            TopPageInfo topPageInfo = getTopPageInfo();
            if (topPageInfo != null) {
                Map<String, AppInfo> hAppInfo = topPageInfo.getAppInfo();
                if (hAppInfo != null && !hAppInfo.isEmpty()) {
                    // アプリケーションをトップページの通常のアプリケーションとして取得
                    String sAppInfoKey = "Site:" + sSiteId + "_App:" + sAppId;
                    if (hAppInfo.containsKey(sAppInfoKey)) {
                        return hAppInfo.get(sAppInfoKey);
                    }
                }
            }
        } else {
            // トップページ以外はサイトからアプリケーション情報を取得
            SiteInfo siteInfo = this.getSiteInfo(sSiteId);
            if (siteInfo != null) {
                Map<String, AppInfo> hAppInfo = siteInfo.getAppInfo();
                if (hAppInfo != null && !hAppInfo.isEmpty()) {
                    // 指定アプリケーション情報を取得
                    String sAppInfoKey = "Site:" + sSiteId + "_App:" + sAppId;
                    if (hAppInfo.containsKey(sAppInfoKey)) {
                        return hAppInfo.get(sAppInfoKey);
                    }
                }
            }
        }
        return null;
    }

    // ▼ 2007/07/12 Saito
    // トップページ情報取得を追加
    /**
     * トップページ情報を取得します。
     *
     * @author shun
     * @return
     * @exception
     */
    @Override
    public TopPageInfo getTopPageInfo() {
        return gPsSession.getLoginAppPermission();
    }

    @Override
    public SiteInfo getSiteInfo(String sSiteId) {
        SiteInfo gSiteInfo;
        // サイトIDが渡されてこなかった場合
        if(sSiteId == null) {
            return null;
        }
        // すべてのサイト情報をListで取得
        List < SiteInfo > lSiteInfoAll = this.getSiteList();

        // サイトが１つも登録されていないケースに対応
        if(lSiteInfoAll == null) {
            // #1794,1878
//            throw new ParameterInvalidException("サイトID","");
            return null;
        }

        for (int i = 0; i < lSiteInfoAll.size(); i++) {

            gSiteInfo = lSiteInfoAll.get(i);

            // TopPageだったら例外を出さずに終了
            if (sSiteId.equals("TopPage")) {
                return null;
            }

            // 指定サイトIDに属するサイト情報の場合
            if (sSiteId.equals(gSiteInfo.getSiteID())) {
                return gSiteInfo;
            }
        }

        // #1794,1878
//        // 例外処理
//        throw new ParameterInvalidException("サイトID","");
        return null;
    }

    /**
     * URLを「/」をデリミタとして分割して返す。
     * 2007/08/24 Konno
     * @return sAppId URLを/区切りで分割したリスト
     */
    private List<String> separateURL(String psUrl) {
        // URLパラメーターを切り捨て
        String sUrlTmp = "";
        if (psUrl.contains("?")) {
            StringTokenizer strTokensUrlTmp = new StringTokenizer(psUrl, "?");
            while (strTokensUrlTmp.hasMoreTokens()) {
                sUrlTmp = strTokensUrlTmp.nextToken();
            }
        } else {
            sUrlTmp = psUrl;
        }
        // URL分割リスト
        List < String > lUrl = new ArrayList< String >();
        // URLの要素処理
        StringTokenizer strTokensUrl = new StringTokenizer(sUrlTmp, "/");
        while (strTokensUrl.hasMoreTokens()) {
            lUrl.add(strTokensUrl.nextToken());
        }
        return lUrl;
    }

    /**
     * システムコードを取得します。
     *
     * @param sSiteId サイトID
     * @return sSystemCode システムコード
     */
    @Override
    public String getSystemCode(String sSiteId) {

        String sSystemCode = ""; // 返却値用システムコード
        // サイト情報取得
        SiteInfo siteInfo = this.getSiteInfo(sSiteId);
        // nullチェック
        if (siteInfo == null){
            return null;
        }

        // システムコード取得
        sSystemCode = siteInfo.getSystem();

        return sSystemCode;
    }

    /**
     * システムコードを取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @return sSystemCode システムコード
     */
    @Override
    public String getSystemCode(String sSiteId, String sAppId) {

        String sSystemCode = ""; // 返却値用システムコード

        // アプリケーション情報取得
        AppInfo gAppInfo = this.getAppInfo(sSiteId, sAppId);
        // nullチェック
        if (gAppInfo == null){
            return null;
        }

        // システムコード取得
        sSystemCode = gAppInfo.getSystem();

        return sSystemCode;
    }

    /**
     * すべてのサイト情報をListで取得します。
     *
     * @return lSite サイト情報リスト
     */
    public List<SiteInfo> getSiteList() {
        setRequest(ContextUtil.getHttpRequest());
        gPsSession = (PsSession) gRequest.getSession().getAttribute(Constant.PS_SESSION);
        List<SiteInfo> lSite = CollUtil.newArrayList();
        TopPageInfo topPageInfo;
        Object o = scCache.get(gPsSession.getLoginEmployee()+":loginAppPermissions");
        if (o==null){
            topPageInfo = appAuthJudgmentBusiness.getAppAuthJudgmentInfo();
            scCache.put(gPsSession.getLoginEmployee()+":loginAppPermissions",topPageInfo);
        }else {
            topPageInfo = (TopPageInfo)o;
        }
        if(topPageInfo == null) {
            // #1794,1878
//            throw new SessionVariableInvalidException("","");
            return null;
        }

        Map hSite = topPageInfo.getSiteInfo();
        // ▼ 2007/07/12 Saito
        // サイトが一つも登録されていなかったらnullを返すように修正
        if(hSite == null) {
            // #1794,1878
//            throw new ParameterInvalidException("サイトID","");
            return null;
        }
        // ▲ 2007/07/12 Saito

        Set set = hSite.entrySet();
        Iterator ite = set.iterator();

        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            lSite.add((SiteInfo) entry.getValue());
        }

        return lSite;

    }

}
