package jp.smartcompany.framework.appcontrol;

import lombok.ToString;

/**
 * ボタン情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
public class ButtonInfo extends AbstractInfo {

    /** 実行権限(権限あり：true  権限なし：false) */
    private boolean gsPermission;

    public boolean isPermission() {
        return gsPermission;
    }

    public void setPermission(boolean gsPermission) {
        this.gsPermission = gsPermission;
    }

}
