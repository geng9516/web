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
    private Map<String,SubAppInfo> ghSubAppInfo;

    /** 画面情報クラス */
    private Map<String,ScreenInfo> ghScreenInfo;


    /**
     * @return subAppInfo
     */
    public Map<String,SubAppInfo> getSubAppInfo() {
        return ghSubAppInfo;
    }

    /**
     * @param subAppInfo
     */
    public void setSubAppInfo(Map<String,SubAppInfo> subAppInfo) {
        this.ghSubAppInfo = subAppInfo;
    }

    /**
     * @return screenInfo
     */
    public Map<String,ScreenInfo> getScreenInfo() {
        return ghScreenInfo;
    }

    /**
     * @param screenInfo
     */
    public void setScreenInfo(Map<String,ScreenInfo> screenInfo) {
        this.ghScreenInfo = screenInfo;
    }

}
