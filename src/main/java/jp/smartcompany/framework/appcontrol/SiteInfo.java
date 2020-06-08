package jp.smartcompany.framework.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * サイト情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class SiteInfo extends AbstractInfo {

    /** アプリケーション情報クラス */
    private Map ghAppInfo;


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

}
