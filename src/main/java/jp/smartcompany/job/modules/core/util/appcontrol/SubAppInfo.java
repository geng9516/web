package jp.smartcompany.job.modules.core.util.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * @author Xiao Wenpeng
 * サブアプリケーション情報格納クラス
 */
@ToString
public class SubAppInfo extends AbstractInfo{

    /** 画面情報クラス */
    Map ghScreenInfo;


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
