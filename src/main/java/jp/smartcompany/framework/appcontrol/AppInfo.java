package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * アプリケーション情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class AppInfo extends AbstractInfo{

    /** サブアプリケーション情報クラス */
    private Map<String,SubAppInfo> subAppInfo;

    /** 画面情報クラス */
    private Map<String,ScreenInfo> screenInfo;


}
