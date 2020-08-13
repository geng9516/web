package jp.smartcompany.framework.appcontrol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Xiao Wenpeng
 */
@ToString
@Getter
@Setter
public abstract class AbstractInfo {
    /** サイトID */
    protected String siteId;

    /** アプリケーションID */
    protected String appId;

    /** サブアプリケーションID */
    protected String subAppId;

    /** 画面ID */
    protected String screenId;

    /** ボタンID */
    protected String buttonId;

    /** 項目名称 */
    protected String name;

    /** 種別(1:サイト) */
    protected String type = "1";

    /** 対応バージョン */
    protected String version;

    /** 対応システム */
    protected String system;

    /** 使用アプリケーションテンプレートID */
    protected String appTemplateId;

    /** URL */
    protected String url;

    /** 画像URL */
    protected String imageUrl;

    /** アプリケーション自動起動 */
    protected String appAutoLoad;

    /** オンラインヘルプURL */
    protected String onlineHelpUrl;

    /** ヘルプwindow.open属性 */
    protected String helpWindowOpen;

    /** サイト説明文 */
    protected String siteDirections;

    /** デフォルト検索対象者(3:デフォルトなし) */
    protected String defSearchObj = "3";

    /** 適用基準日種別(2:システム日付) */
    protected String baseDateType = "2";

    /** 検索対象範囲適用種別(1:起動時にチェック) */
    protected String searchRangeType = "1";

    /** ドメインコード */
    protected String domainId;

    /** インラインフレーム表示設定フラグ */
    protected String iframeFlag;

}
