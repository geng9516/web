package jp.smartcompany.framework.appcontrol.business;

import jp.smartcompany.framework.appcontrol.AppInfo;
import jp.smartcompany.framework.appcontrol.SiteInfo;

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
     * 指定サイトに属するサイト情報を取得します。
     *
     * @param sSiteId サイトID
     * @return SiteInfo サイト情報
     */
    SiteInfo getSiteInfo(String sSiteId);

}
