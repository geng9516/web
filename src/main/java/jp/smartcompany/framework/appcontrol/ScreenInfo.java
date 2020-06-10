package jp.smartcompany.framework.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * 画面情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class ScreenInfo extends AbstractInfo {

    /** ボタン情報クラス */
    private Map<String,ButtonInfo> ghButtonInfo;

    /**
     * @return buttonInfo
     */
    public Map<String,ButtonInfo> getButtonInfo() {
        return ghButtonInfo;
    }

    /**
     * @param buttonInfo
     */
    public void setButtonInfo(Map<String,ButtonInfo> buttonInfo) {
        this.ghButtonInfo = buttonInfo;
    }
}
