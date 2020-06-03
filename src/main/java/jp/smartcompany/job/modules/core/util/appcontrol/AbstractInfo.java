package jp.smartcompany.job.modules.core.util.appcontrol;

import lombok.ToString;

/**
 * モジュールの説明を記述
 * @author Xiao Wenpeng
 */
@ToString
public abstract class AbstractInfo {
    /** サイトID */
    String gsSiteID;

    /** アプリケーションID */
    String gsAppID;

    /** サブアプリケーションID */
    String gsSubAppID;

    /** 画面ID */
    String gsScreenID;

    /** ボタンID */
    String gsButtonID;

    /** 項目名称 */
    String gsName;

    /** 種別(1:サイト) */
    String gsType = "1";

    /** 対応バージョン */
    String gsVersion;

    /** 対応システム */
    String gsSystem;

    /** 使用アプリケーションテンプレートID */
    String gsAppTemplateID;

    /** URL */
    String gsURL;

    /** 画像URL */
    String gsImageURL;

    /** アプリケーション自動起動 */
    String gsAppAutoLoad;

    /** オンラインヘルプURL */
    String gsOnlineHelpURL;

    /** ヘルプwindow.open属性 */
    String gsHelpWindowOpen;

    /** サイト説明文 */
    String gsSiteDirections;

    /** デフォルト検索対象者(3:デフォルトなし) */
    String gsDefSearchObj = "3";

    /** 適用基準日種別(2:システム日付) */
    String gsBaseDateType = "2";

    /** 検索対象範囲適用種別(1:起動時にチェック) */
    String gsSearchRangeType = "1";

    /** ドメインコード */
    String gsDomainId;

    /** インラインフレーム表示設定フラグ */
    String gsIframeFlag;

    /**
     * @return siteID
     */
    public String getSiteID() {
        return gsSiteID;
    }

    /**
     * @param siteID
     */
    public void setSiteID(String siteID) {
        this.gsSiteID = siteID;
    }

    /**
     * @return appID
     */
    public String getAppID() {
        return gsAppID;
    }

    /**
     * @param appID
     */
    public void setAppID(String appID) {
        this.gsAppID = appID;
    }

    /**
     * @return subAppID
     */
    public String getSubAppID() {
        return gsSubAppID;
    }

    /**
     * @param subAppID
     */
    public void setSubAppID(String subAppID) {
        this.gsSubAppID = subAppID;
    }

    /**
     * @return screenID
     */
    public String getScreenID() {
        return gsScreenID;
    }

    /**
     * @param screenID
     */
    public void setScreenID(String screenID) {
        this.gsScreenID = screenID;
    }

    /**
     * @return buttonID
     */
    public String getButtonID() {
        return gsButtonID;
    }

    /**
     * @param buttonID
     */
    public void setButtonID(String buttonID) {
        this.gsButtonID = buttonID;
    }

    /**
     * @return appName
     */
    public String getName() {
        return gsName;
    }

    /**
     * @param appName
     */
    public void setName(String pName) {
        this.gsName = pName;
    }

    /**
     * @return type
     */
    public String getType() {
        return gsType;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.gsType = type;
    }

    /**
     * @return version
     */
    public String getVersion() {
        return gsVersion;
    }

    /**
     * @param version
     */
    public void setVersion(String version) {
        this.gsVersion = version;
    }

    /**
     * @return system
     */
    public String getSystem() {
        return gsSystem;
    }

    /**
     * @param system
     */
    public void setSystem(String system) {
        this.gsSystem = system;
    }

    /**
     * @return appTemplateID
     */
    public String getAppTemplateID() {
        return gsAppTemplateID;
    }

    /**
     * @param appTemplateID
     */
    public void setAppTemplateID(String appTemplateID) {
        this.gsAppTemplateID = appTemplateID;
    }

    /**
     * @return url
     */
    public String getURL() {
        return gsURL;
    }

    /**
     * @param url
     */
    public void setURL(String url) {
        this.gsURL = url;
    }

    /**
     * @return imageURL
     */
    public String getImageURL() {
        return gsImageURL;
    }

    /**
     * @param imageURL
     */
    public void setImageURL(String imageURL) {
        this.gsImageURL = imageURL;
    }

    /**
     * @return appAutoLoad
     */
    public String getAppAutoLoad() {
        return this.gsAppAutoLoad;
    }

    /**
     * @param appAutoLoad
     */
    public void setAppAutoLoad(String gsAppAutoLoad) {
        this.gsAppAutoLoad = gsAppAutoLoad;
    }

    /**
     * @return onlineHelpURL
     */
    public String getOnlineHelpURL() {
        return gsOnlineHelpURL;
    }

    /**
     * @param onlineHelpURL
     */
    public void setOnlineHelpURL(String onlineHelpURL) {
        this.gsOnlineHelpURL = onlineHelpURL;
    }

    /**
     * helpWindowOpen 取得する。
     *
     * @return helpWindowOpen
     */
    public String getHelpWindowOpen() {
        return this.gsHelpWindowOpen;
    }

    /**
     * helpWindowOpen 設定する。
     *
     * @param helpWindowOpen を設定
     */
    public void setHelpWindowOpen(String helpWindowOpen) {
        this.gsHelpWindowOpen = helpWindowOpen;
    }

    /**
     * @return siteDirections
     */
    public String getSiteDirections() {
        return gsSiteDirections;
    }

    /**
     * @param siteDirections
     */
    public void setSiteDirections(String siteDirections) {
        this.gsSiteDirections = siteDirections;
    }

    /**
     * @return defSearchObj
     */
    public String getDefSearchObj() {
        return gsDefSearchObj;
    }

    /**
     * @param defSearchObj
     */
    public void setDefSearchObj(String defSearchObj) {
        this.gsDefSearchObj = defSearchObj;
    }

    /**
     * @return baseDateType
     */
    public String getBaseDateType() {
        return gsBaseDateType;
    }

    /**
     * @param baseDateType
     */
    public void setBaseDateType(String baseDateType) {
        this.gsBaseDateType = baseDateType;
    }

    /**
     * @return searchRangeType
     */
    public String getSearchRangeType() {
        return gsSearchRangeType;
    }

    /**
     * @param searchRangeType
     */
    public void setSearchRangeType(String searchRangeType) {
        this.gsSearchRangeType = searchRangeType;
    }

    /**
     * @return domainId
     */
    public String getDomainId() {
        return gsDomainId;
    }

    /**
     * @param domainId
     */
    public void setDomainId(String domainId) {
        this.gsDomainId = domainId;
    }

    /**
     * @return iframeFlag
     */
    public String getIframeFlag() {
        return this.gsIframeFlag;
    }
    /**
     * @param iframeFlag
     */
    public void setIframeFlag(String iframeFlag) {
        gsIframeFlag = iframeFlag;
    }

}
