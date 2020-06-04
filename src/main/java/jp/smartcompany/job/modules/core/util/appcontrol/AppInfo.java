package jp.smartcompany.job.modules.core.util.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 * アプリケーション情報格納クラス
 */
@ToString
public class AppInfo extends AbstractInfo{

    /** サブアプリケーション情報クラス */
    Map ghSubAppInfo;

    /** 画面情報クラス */
    Map ghScreenInfo;


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
