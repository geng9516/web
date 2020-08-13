package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ボタン情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class ButtonInfo extends AbstractInfo {

    /** 実行権限(権限あり：true  権限なし：false) */
    private boolean permission;

}
