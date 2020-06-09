package jp.smartcompany.framework.appcontrol.business;

import jp.smartcompany.framework.appcontrol.AppInfo;
import jp.smartcompany.framework.appcontrol.SiteInfo;
import jp.smartcompany.framework.appcontrol.TopPageInfo;

/**
 * アプリケーション起動権限情報取得のLogicインターフェース
 * @author Xiao Wenpeng
 */
public interface AppAuthInfoBusiness {

    /**
     * アプリケーションIDを取得します。
     *
     * @param sUrl url
     * @return String アプリケーションID
     */
    String getAppId(String sUrl);


    /**
     * 指定サイト、指定アプリケーションに属するアプリケーション情報を取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @return AppInfo アプリケーション情報
     */
    AppInfo getAppInfo(String sSiteId, String sAppId);

    /**
     * トップページ情報を取得します。
     *   by Konno 2007/07/27
     * @return SiteInfo トップページ情報
     */
    TopPageInfo getTopPageInfo();

    /**
     * 指定サイトに属するサイト情報を取得します。
     *
     * @param sSiteId サイトID
     * @return SiteInfo サイト情報
     */
    SiteInfo getSiteInfo(String sSiteId);

    /**
     * サイトIDを取得します。
     *  by Koizumi 2007/09/01
     * @param sUrl URL
     * @return sSiteId サイトID
     */
    String getSiteId(String sUrl);

    /**
     * 指定アプリケーションに属するダイアログアプリケーション情報を取得します。
     *
     * @param sAppId アプリケーションID
     * @return AppInfo ダイアログアプリケーション情報
     */
    AppInfo getDlgAppInfo(String sAppId);

    /**
     * システムコードを取得します。
     *
     * @param sSiteId サイトID
     * @return sSystemCode システムコード
     */
    String getSystemCode(String sSiteId);

    /**
     * システムコードを取得します。
     *
     * @param sSiteId サイトID
     * @param sAppId アプリケーションID
     * @return sSystemCode システムコード
     */
    String getSystemCode(String sSiteId, String sAppId);

}
