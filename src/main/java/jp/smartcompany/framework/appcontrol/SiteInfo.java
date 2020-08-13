package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * サイト情報格納クラス
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public class SiteInfo extends AbstractInfo {

    /** アプリケーション情報クラス */
    private Map<String,AppInfo> appInfo;


}
