package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * サブアプリケーション情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class SubAppInfo extends AbstractInfo {

    /** 画面情報クラス */
    Map<String,ScreenInfo> screenInfo;

}
