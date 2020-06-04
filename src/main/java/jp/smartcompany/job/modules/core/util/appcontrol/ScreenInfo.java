package jp.smartcompany.job.modules.core.util.appcontrol;

import lombok.ToString;

import java.util.Map;

/**
 * 画面情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class ScreenInfo extends AbstractInfo {
    /** ボタン情報クラス */
    Map ghButtonInfo;

    /**
     * @return buttonInfo
     */
    public Map getButtonInfo() {
        return ghButtonInfo;
    }

    /**
     * @param buttonInfo
     */
    public void setButtonInfo(Map buttonInfo) {
        this.ghButtonInfo = buttonInfo;
    }
}
