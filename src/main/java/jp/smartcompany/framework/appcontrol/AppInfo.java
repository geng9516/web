package jp.smartcompany.framework.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * アプリケーション情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class AppInfo extends AbstractInfo{

    /** サブアプリケーション情報クラス */
    private Map ghSubAppInfo;

    /** 画面情報クラス */
    private Map ghScreenInfo;


    /**
     * @return subAppInfo
     */
    public Map getSubAppInfo() {
        return ghSubAppInfo;
    }

    /**
     * @param subAppInfo
     */
    public void setSubAppInfo(Map subAppInfo) {
        this.ghSubAppInfo = subAppInfo;
    }

    /**
     * @return screenInfo
     */
    public Map getScreenInfo() {
        return ghScreenInfo;
    }

    /**
     * @param screenInfo
     */
    public void setScreenInfo(Map screenInfo) {
        this.ghScreenInfo = screenInfo;
    }

}
