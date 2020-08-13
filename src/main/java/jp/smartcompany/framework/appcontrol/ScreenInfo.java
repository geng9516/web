package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 画面情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class ScreenInfo extends AbstractInfo {

    /** ボタン情報クラス */
    private Map<String,ButtonInfo> buttonInfo;

}
