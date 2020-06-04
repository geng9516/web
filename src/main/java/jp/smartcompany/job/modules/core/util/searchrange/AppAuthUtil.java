package jp.smartcompany.job.modules.core.util.searchrange;

import cn.hutool.core.collection.CollUtil;
import jp.smartcompany.job.modules.core.util.PsConst;
import jp.smartcompany.job.modules.core.util.PsSession;
import jp.smartcompany.job.modules.core.util.appcontrol.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * アプリケーション起動権限情報取得のLogicImplクラス
 * @author Xiao Wenpeng
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppAuthUtil {

    private final String STYLESHEER_CACHE_SEPARATOR = "_";

    private final String SITE_HTML = "/Site.html";

    private final String PHOTO_SERVLET = "/PhotoServlet";

    //2007/08/24 V3アプリケーション起動かを判断する(URLに含まれていればV3App起動）
    private final String V3APP_HTML = "iFrame.html";

    // CSSフォルダ
    private final String CSS_FOLDER = "css/";
    // CSSファイル頭文字
    private final String CSS_FILE_HEADER = "PS";
    // 画面用CSSファイル
    private final String CSS_SCRREN = "_Screen.css";

    /** Session保持情報格納クラス **/
    private PsSession gPsSession;

    /** HttpServletRequestクラス **/
    private final HttpServletRequest gRequest;

    /**
     * ログイン情報コンポーネントの取得（自動インジェクション）
     *
     * @param pPsSession ログイン情報
     */
    public void setPsSession(PsSession pPsSession) {
        this.gPsSession = pPsSession;
    }

    /**
     * すべてのサイト情報をListで取得します。
     *
     * @return lSite サイト情報リスト
     */
    public List<SiteInfo> getSiteList() {
        List<SiteInfo> lSite = CollUtil.newArrayList();
        TopPageInfo topPageInfo = gPsSession.getLoginAppPermission();
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

    // ▼ 2007/07/12 Saito
    // トップページ情報取得を追加
    /**
     * トップページ情報を取得します。
     *
     * @author shun
     * @return
     * @exception
     */
    public TopPageInfo getTopPageInfo() {
        return this.gPsSession.getLoginAppPermission();
    }

    /**
     * 指定サイトに属するサイト情報を取得します。
     *
     * @param sSiteId サイトID
     * @return gSiteInfo サイト情報
     */
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
     * 指定サイトに属するアプリケーション情報をListで取得します。
     *
     * @param psSiteID サイトID
     * @return lApp アプリケーション情報リスト
     */
    public List < AppInfo > getAppList(String psSiteID) {

        List < AppInfo > lApp = CollUtil.newArrayList();

        SiteInfo si = (SiteInfo) this.gPsSession.getLoginAppPermission().getSiteInfo().get(
                "Site:" + psSiteID);
        if (si == null) { // 見つからない・設定されていない
            // #1794,1878
//            // 例外処理
//            throw new ParameterInvalidException("サイトID","");
            return null;
        }

        Map hApp = si.getAppInfo();
        Set set = hApp.entrySet();
        Iterator ite = set.iterator();

        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
            lApp.add((AppInfo) entry.getValue());
        }

        return lApp;

    }

    /**
     * 指定サイト、指定アプリケーションに属するアプリケーション情報を取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @return AppInfo アプリケーション情報
     */
    public AppInfo getAppInfo(String sSiteId, String sAppId) {

        // サイト情報のアプリケーション情報Mapを取得

        // サイトがトップページだった場合に対応
        if (sSiteId == null || "".equals(sSiteId) || "TopPage".equals(sSiteId)) {

            sSiteId = "TopPage";

            // ダイアログアプリケーションのチェックから
            List<AppInfo> dlgAppList = this.getDlgAppList();

            if (dlgAppList != null && !dlgAppList.isEmpty()) {

                AppInfo dlgAppInfo = this.getDlgAppInfo(sAppId);

                if (dlgAppInfo != null) {

                    return dlgAppInfo;

                }
            }

            // ダイアログアプリケーションではない場合にアプリケーションのチェック
            TopPageInfo topPageInfo = this.getTopPageInfo();

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

            SiteInfo siteInfo = getSiteInfo(sSiteId);

            if (siteInfo != null) {

                Map<String, AppInfo> hAppInfo = siteInfo.getAppInfo();

                if (hAppInfo != null && !hAppInfo.isEmpty()) {

                    // 指定アプリケーション情報を取得
                    String sAppInfoKey = "Site:" + sSiteId + "_App:" + sAppId;

                    if (hAppInfo.containsKey(sAppInfoKey)) {

                        return (AppInfo) hAppInfo.get(sAppInfoKey);

                    }
                }
            }
        }

        return null;
    }

    /**
     * 指定サイト、指定アプリケーションに属するサブアプリケーション情報をListで取得します。
     *
     * @param sSiteID サイトID
     * @param sAppID アプリケーションID
     * @return lSubApp サブアプリケーション情報リスト
     */
    public List < SubAppInfo > getSubAppList(String sSiteID, String sAppID) {

        // アプリケーション情報
        AppInfo gAppInfo = new AppInfo();

        // 返却値用サブアプリケーション情報リスト
        List < SubAppInfo > lSubApp = new ArrayList < SubAppInfo >();

        // アプリケーション情報のサブアプリケーション情報格納用LinkedHashMap
        Map<String, SubAppInfo> hSubAppInfo;

        try {

            // アプリケーション情報取得
            gAppInfo = this.getAppInfo(sSiteID, sAppID);
            if (gAppInfo == null) {
                return null;
            }

        } catch (Exception e) {

            // #1794,1878
//            // 例外処理
//            throw new ParameterInvalidException("サイトID,アプリケーションID","");
            return null;
        }

        // サブアプリケーション情報格納用LinkedHashMapを取得
        hSubAppInfo = gAppInfo.getSubAppInfo();
        // サブアプリケーションがない場合はここでnullを返す
        if (hSubAppInfo == null) {
            return lSubApp;
        }
        Set set = hSubAppInfo.entrySet();

        // サブアプリケーション情報を取得しリストに追加
        for (SubAppInfo subAppInfo : (Iterable<SubAppInfo>) set) {
            Map.Entry entry = (Map.Entry) subAppInfo;
            lSubApp.add((SubAppInfo) entry.getValue());
        }

        return lSubApp;
    }

    /**
     * 指定サイト、指定アプリケーション、指定サブアプリケーションに属する サブアプリケーション情報を取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @param sSubAppId サブアプリケーションID
     * @return SubAppInfo サブアプリケーション情報
     */
    public SubAppInfo getSubAppInfo(String sSiteId, String sAppId, String sSubAppId) {

        try {
            // アプリケーション情報のサブアプリケーション情報Mapを取得
            Map<String, SubAppInfo> hSubAppInfo
                    = this.getAppInfo(sSiteId, sAppId).getSubAppInfo();

            // 指定サブアプリケーション情報を取得
            String sSubAppKey = "Site:" + sSiteId + "_App:" + sAppId + "_Sub:" + sSubAppId;
            if (hSubAppInfo.containsKey(sSubAppKey)) {
                return hSubAppInfo.get(sSubAppKey);
            }
        } catch (Exception e) {
            // #1794,1878
//            throw new ParameterInvalidException("サイトID,アプリケーションID","");
            return null;
        }
        // #1794,1878
//        throw new ParameterInvalidException("サイトID,アプリケーションID,サブアプリケーションID","");
        return null;
    }

    /**
     * ダイアログアプリケーション情報をListで取得します。
     *
     * @return lApp ダイアログアプリケーション情報リスト
     */
    public List < AppInfo > getDlgAppList() {

        // 返却用
        List < AppInfo > appInfoList = CollUtil.newArrayList();

        TopPageInfo topPageInfo = this.gPsSession.getLoginAppPermission();
        if (topPageInfo == null) {
            return appInfoList;
        }

        Map<String, AppInfo> dlgAppInfoMap = topPageInfo.getDlgAppInfo();
        if (dlgAppInfoMap == null) {
            return appInfoList;
        }

        Set set = dlgAppInfoMap.entrySet();
        Iterator<AppInfo> ite = set.iterator();
        while (ite.hasNext()) {
            Map.Entry entry = (Map.Entry) ite.next();
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
    public AppInfo getDlgAppInfo(String sAppId) {

        // トップページ情報のダイアログアプリケーション情報Mapを取得
        Map<String, AppInfo> hDlgAppInfo
                = this.gPsSession.getLoginAppPermission().getDlgAppInfo();

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

    /**
     * アプリケーションIDを取得します。
     *
     * @param sUrl URL
     * @return sAppId アプリケーションID
     */
    public String getAppID(String sUrl) {

        // ▼ 2007/07/31 Saito
        // サイト検索時のアプリケーションIDはnull
        if (sUrl.indexOf(this.SITE_HTML) > -1) {
            return null;
        }

        // PhotoServlet
        if (sUrl.indexOf(this.PHOTO_SERVLET) > -1) {
            return null;
        }
        // ▲ 2007/07/31 Saito

        //▼2007/08/24 Konno 複数箇所で使うのでメソッド化
        List < String > lUrl = separateURL(sUrl);

        // サイトID取得
        String sSiteId = this.getSiteID(sUrl);

        //▼ 2007/08/24 V3互換アプリケーション起動時はパラメータより値を取得
        try {
            String sApp = (String) lUrl.get(lUrl.size()-1);
            if (sApp.equalsIgnoreCase(V3APP_HTML)){
                //パラメータよりアプリケーションId、サブアプリケーションIdを取得
                //URLの後続パラメータをURLから分離
                StringTokenizer st1 = new StringTokenizer(sUrl, "?");
                String sURLFirstHalf = st1.nextToken();
                String sURLSecondHalf = st1.nextToken();
                //後続パラメータを"&"を区切りとして分割
                StringTokenizer st2 = new StringTokenizer(sURLSecondHalf, "&");
                String sAppId = null;
                String sSubAppId = null;

                while (st2.hasMoreTokens()) {
                    //KeywordとValueを分解
                    String sParam = st2.nextToken();
                    if (!sParam.startsWith(PsConst.PARAM_KEY_APPID)) {
                        // アプリケーションIDのパラメータ以外は、以降の処理を行わない
                        continue;
                    }
                    String sKey = null;
                    String sValue = null;
                    StringTokenizer st3 = new StringTokenizer(sParam, "=");
                    while (st3.hasMoreTokens()) {
                        sKey = st3.nextToken();
                        // 対応する値が存在する場合のみ、値を取得する
                        if (st3.hasMoreTokens()) {
                            sValue = st3.nextToken();
                        }
                        break;
                    }
                    //アプリケーションID取得
                    if ( sKey.equalsIgnoreCase(PsConst.PARAM_KEY_APPID)){
                        sAppId = sValue;
                        // ここでは、アプリケーションIDが取得できればOKなので、ループを抜ける
                        break;
                    }
                }
                // アプリケーションIDのチェック
                if (this.getAppInfo(sSiteId, sAppId).getAppID().equals(sAppId)) {
                    return sAppId;
                }
            }
        } catch (Exception e) {
            System.err.println("アプリケーションIDの取得エラー");
            e.printStackTrace();
        }
        //▲ 2007/08/24

        AppInfo appInfo = new AppInfo();
        String sAppId = null;

        // アプリケーションIDの取得(サブアプリケーションが無い場合の位置)
        sAppId = (String) lUrl.get(lUrl.size() - 3);

        // アプリケーションIDのチェック
        appInfo = this.getAppInfo(sSiteId, sAppId);
        if (appInfo != null) {
            if (appInfo.getAppID().equals(sAppId)) {
                return sAppId;
            }
        }

        // アプリケーションIDの取得(サブアプリケーションが有る場合の位置)
        sAppId = (String) lUrl.get(lUrl.size() - 4);

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
     * サブアプリケーションIDを取得します。
     *
     * @param sUrl URL
     * @return サブアプリケーションID
     */
    public String getSubAppID(String sUrl) {

        // URLにサイトHTMLが指定されている場合は、アプリケーションが未選択状態なのでnullで返却
        if (sUrl.indexOf(this.SITE_HTML) > -1) {
            return null;
        }
        //▼2007/08/24 Konno 複数箇所で使うのでメソッド化
        List < String > lUrl = separateURL(sUrl);

        // サブアプリケーションIDのチェック
        try {
            // URLから判断したサイトID＆アプリケーションID＆サブアプリケーションIDの取得
            String sSiteId = this.getSiteID(sUrl);

            //▼ 2007/08/24 V3互換アプリケーション起動時はパラメータより値を取得
            try {
                String sApp = (String) lUrl.get(lUrl.size()-1);
                if (sApp.equalsIgnoreCase(V3APP_HTML)){
                    //パラメータよりアプリケーションId、サブアプリケーションIdを取得
                    //URLの後続パラメータをURLから分離
                    StringTokenizer st1 = new StringTokenizer(sUrl, "?");
                    String sURLFirstHalf = st1.nextToken();
                    String sURLSecondHalf = st1.nextToken();
                    //後続パラメータを"&"を区切りとして分割
                    StringTokenizer st2 = new StringTokenizer(sURLSecondHalf, "&");
                    String sAppId = null;
                    String sSubAppId = null;

                    while (st2.hasMoreTokens()) {
                        //KeywordとValueを分解
                        String sParam = st2.nextToken();
                        String sKey = null;
                        String sValue = null;
                        StringTokenizer st3 = new StringTokenizer(sParam, "=");
                        while (st3.hasMoreTokens()) {
                            sKey = st3.nextToken();
                            // 対応する値が存在する場合のみ、値を取得する
                            if (st3.hasMoreTokens()) {
                                sValue = st3.nextToken();
                            }
                            break;
                        }
                        //アプリケーションID取得
                        if ( sKey.equalsIgnoreCase(PsConst.PARAM_KEY_APPID)){
                            sAppId = sValue;
                            //サブアプリケーションID取得
                        } else if ( sKey.equalsIgnoreCase(PsConst.PARAM_KEY_SUBAPPID)){
                            sSubAppId = sValue;
                        }
                    }
                    if ( sSubAppId == null ){
                        return null;
                    }
                    // サブアプリケーションIDのチェック
                    if (this.getSubAppInfo(sSiteId, sAppId, sSubAppId).getSubAppID().equals(sSubAppId)) {
                        return sSubAppId;
                    }
                }
            } catch (Exception e) {
                System.err.println("アプリケーションIDの取得エラー");
                e.printStackTrace();
            }
            //▲ 2007/08/24
            String sAppId = (String) lUrl.get(lUrl.size() - 4);
            String sSubAppId = (String) lUrl.get(lUrl.size() - 3);

            SubAppInfo subAppInfo = this.getSubAppInfo(sSiteId, sAppId, sSubAppId);
            if (subAppInfo.getSubAppID().equals(sSubAppId)) {
                return sSubAppId;
            }
        } catch (Exception e) {
        }
        return null;
        //throw new ParameterInvalidException("URL","");
    }
    /**
     * URLを「/」をデリミタとして分割して返す。
     * 2007/08/24 Konno
     * @return sAppId URLを/区切りで分割したリスト
     */
    private List<String> separateURL(String psUrl) {

        // URLパラメーターを切り捨て
        String sUrlTmp = "";

        if (psUrl.indexOf("?") > -1) {

            StringTokenizer strTokensUrlTmp = new StringTokenizer(psUrl, "?");

            while (strTokensUrlTmp.hasMoreTokens()) {

                sUrlTmp = strTokensUrlTmp.nextToken();
                break;
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
     * 画面IDを取得します。
     *
     * @param sUrl URL
     * @return sScreenId 画面ID
     */
    public String getScreenID(String sUrl) {

        // サイトIDおよびアプリケーションID取得
        String sSiteID = this.getSiteID(sUrl);
        String sAppID = this.getAppID(sUrl);
        String sSubAppID = this.getSubAppID(sUrl);

        if (sSiteID == null || sSiteID.equals("")) {
            return null;
        }

        if (sAppID == null || sAppID.equals("")) {
            return null;
        }

        // 画面情報クラス
        Map<String, ScreenInfo> screenInfoMap
                = new LinkedHashMap <String, ScreenInfo>();
        if (sSubAppID == null) {
            // アプリケーション情報クラス取得
            AppInfo appInfo = this.getAppInfo(sSiteID, sAppID);
            if (appInfo == null) {
                return null;
            }
            // アプリケーション情報クラスより画面情報(を保持したLinkedHashMap)を取得
            screenInfoMap = appInfo.getScreenInfo();
        } else {
            // サブアプリケーション情報取得
            SubAppInfo subAppInfo = this.getSubAppInfo(sSiteID, sAppID, sSubAppID);
            if (subAppInfo == null) {
                return null;
            }
            // サブアプリケーション情報クラスより画面情報(を保持したLinkedHashMap)を取得
            screenInfoMap = subAppInfo.getScreenInfo();
        }
        //▼2007/08/24 Konno V3アプリ起動かを判定
        List < String > lUrl = separateURL(sUrl);
        boolean bV3App = false;
        String sApp = (String) lUrl.get(lUrl.size()-1);
        if (sApp.equalsIgnoreCase("iFrame.html")){
            bV3App = true;
        }
        //▲2007/08/24

        // URLが一致する画面を検索する
        Set screenInfoSet = screenInfoMap.entrySet();
        Iterator<ScreenInfo> iScreenInfo = screenInfoSet.iterator();
        while (iScreenInfo.hasNext()) {
            Map.Entry entry = (Map.Entry) iScreenInfo.next();
            ScreenInfo screenInfo = (ScreenInfo) entry.getValue();
            //▼2007/08/24 Konno V3アプリの場合、一致する画面がURLに含まれないので１件目の画面を返す
            if ( bV3App ){
                return screenInfo.getScreenID();
            }
            //▲2007/08/24
            if (sUrl.indexOf(screenInfo.getURL()) > -1) {
                return screenInfo.getScreenID();
            }
        }
        return null;
    }





    /**
     * システムコードを取得します。
     *
     * @param sSiteId サイトID
     * @return sSystemCode システムコード
     */
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




//    /**
//     * 指定の画面に属するスタイルシートのURLを取得します。
//     *
//     * @param psServletPath サーブレットパス
//     * @param psScreenId 画面ID
//     * @return sStyleSheetURL スタイルシートのURL
//     */
//    public String getStyleSheetURL(String psServletPath, String psScreenId){
//
//        // PsSessionよりスタイルシートカテゴリを取得
//        String sStyleSheetCategory  = this.gPsSession.getLoginCssCategory();
//
//        String sStyleSheetURL = new String();
//        // 画面IDのスタイルシートのパス生成
//        if (psScreenId != null && !psScreenId.equals("")) {
//            // viewフォルダと同階層にCSSフォルダがあるとみなす
//            String sStyle[] = psServletPath.split("view");
//            // パスを組立
//            sStyleSheetURL = sStyle[0] + this.CSS_FOLDER + this.CSS_FILE_HEADER + this.STYLESHEER_CACHE_SEPARATOR +
//                            sStyleSheetCategory + this.STYLESHEER_CACHE_SEPARATOR + psScreenId + this.CSS_SCRREN;
//        }
//
//        return sStyleSheetURL;
//    }

    /**
     * 指定アプリケーション情報配下の画面情報を取得します。
     *
     * @param pAppInfo アプリケーション情報
     * @param sSiteId サイトID
     * @return ScreenInfo 画面情報
     */
    public ScreenInfo getScreenInfo(AppInfo pAppInfo, String sSiteId) {
        return new ScreenInfo();
    }

    /**
     * 指定サイト、指定アプリケーション、指定サブアプリケーション、 指定画面、に属する画面情報を取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @param sScreenId 画面ID
     * @return gButtonInfo 画面情報
     */
    public ScreenInfo getScreenInfo(String sSiteId, String sAppId, String sScreenId) {

        // アプリケーション情報取得
        AppInfo appInfo = this.getAppInfo(sSiteId, sAppId);
        // nullチェック
        if (appInfo == null){
            return null;
        }
        // アプリケーション情報クラス配下の画面情報クラスを取得
        Map<String, ScreenInfo> screenInfoMap = appInfo.getScreenInfo();

        if (sSiteId == null || sSiteId.equals("")) {
            sSiteId = "TopPage";
        }

        String sAppScreenKey = "Site:" + sSiteId + "_App:" + sAppId + "_Scr:" + sScreenId;
        if (screenInfoMap != null && screenInfoMap.containsKey(sAppScreenKey)) {
            // アプリケーション情報クラス配下に画面情報クラスを持つ場合
            return screenInfoMap.get(sAppScreenKey);
        }
        // #1794,1878
//        throw new ParameterInvalidException(
//            "サイトID,アプリケーションID,サブアプリケーションID,画面ID", "");
        return null;

    }

    /**
     * 指定サイト、指定アプリケーション、指定サブアプリケーション、 指定画面、に属する画面情報を取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @param sSubAppId サブアプリケーションID
     * @Param sScreenId 画面ID
     * @return gButtonInfo 画面情報
     */
    public ScreenInfo getScreenInfo(
            String sSiteId, String sAppId, String sSubAppId, String sScreenId) {

        // アプリケーション情報取得
        if (sSiteId == null || "".equals(sSiteId)) {
            sSiteId = "TopPage";
        }
        AppInfo appInfo = this.getAppInfo(sSiteId, sAppId);
        // nullチェック
        if (appInfo == null){
            return null;
        }
        // アプリケーション情報クラス配下の画面情報クラスを取得
        Map<String, SubAppInfo> subAppInfoMap = appInfo.getSubAppInfo();

        String sSubAppKey = "Site:" + sSiteId + "_App:" + sAppId + "_Sub:" + sSubAppId;
        String sSubAppScreenKey = sSubAppKey + "_Scr:" + sScreenId;

        //アプリケーション情報クラス配下に画面情報クラスを持たない場合
        if (subAppInfoMap != null && subAppInfoMap.containsKey(sSubAppKey)) {

            // サブアプリケーション情報クラス配下に画面情報クラスを持つ場合
            SubAppInfo subAppInfo = subAppInfoMap.get(sSubAppKey);

            Map<String, ScreenInfo> screenInfoMap = subAppInfo.getScreenInfo();
            if (screenInfoMap.containsKey(sSubAppScreenKey)) {
                return screenInfoMap.get(sSubAppScreenKey);
            }
        }

        // #1794,1878
//        throw new ParameterInvalidException(
//            "サイトID,アプリケーションID,サブアプリケーションID,画面ID", "");
        return null;

    }
    /**
     * サイトIDを取得します。
     * @param sUrl URL
     * @return sSiteId サイトID
     */
    public String getSiteID(String sUrl) {

        // サイトID返却値
        String sSiteId = null;

        // リクエストパラメータの取得
        if(sUrl.contains("?psSite=")) {

            // URL分割リスト
            List <String> lUrl = CollUtil.newArrayList();
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

    public AppInfo getAppInfo(String sUrl) {
        //サイトHTMLを渡されたとき用の対応
        //サイトHTMLではnullを返却
        String sAppID = this.getAppID(sUrl);
        if( sAppID == null ){
            return null;
        }
        return getAppInfo(this.getSiteID(sUrl), this.getAppID(sUrl));
    }

}
