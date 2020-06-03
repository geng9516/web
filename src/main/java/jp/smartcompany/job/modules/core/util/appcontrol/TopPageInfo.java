package jp.smartcompany.job.modules.core.util.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * トップページ情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class TopPageInfo extends AbstractInfo {
    /**
     * サイト情報
     */
    Map ghSiteInfo;

    /**
     * アプリケーション情報
     */
    Map ghAppInfo;

    /**
     * ダイアログアプリケーション情報
     */
    Map ghDlgAppInfo;

    /**
     * @return siteInfo
     */
    public Map getSiteInfo() {
        return ghSiteInfo;
    }

    /**
     * @param siteInfo
     */
    public void setSiteInfo(Map siteInfo) {
        ghSiteInfo = siteInfo;
    }

    /**
     * @return appInfo
     */
    public Map getAppInfo() {
        return ghAppInfo;
    }

    /**
     * @param appInfo
     */
    public void setAppInfo(Map appInfo) {
        this.ghAppInfo = appInfo;
    }

    /**
     * @return dlgAppInfo
     */
    public Map getDlgAppInfo() {
        return ghDlgAppInfo;
    }

    /**
     * @param dlgAppInfo
     */
    public void setDlgAppInfo(Map dlgAppInfo) {
        ghDlgAppInfo = dlgAppInfo;
    }
}
