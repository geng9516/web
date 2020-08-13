package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * トップページ情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class TopPageInfo extends AbstractInfo {

    /**
     * サイト情報
     */
    private Map<String,SiteInfo> siteInfo;

    /**
     * アプリケーション情報
     */
    private Map<String,AppInfo> appInfo;

    /**
     * ダイアログアプリケーション情報
     */
    private Map<String,AppInfo> dlgAppInfo;


}
