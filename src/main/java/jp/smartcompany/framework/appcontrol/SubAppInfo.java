package jp.smartcompany.framework.appcontrol;

import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * サブアプリケーション情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class SubAppInfo extends AbstractInfo {

    /** 画面情報クラス */
    Map<String,ScreenInfo> ghScreenInfo;


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
