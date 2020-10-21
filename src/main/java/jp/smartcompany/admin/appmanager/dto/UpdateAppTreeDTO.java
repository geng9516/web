package jp.smartcompany.admin.appmanager.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateAppTreeDTO {

    //ドメイン
    private String mtrCdomainid;
    // アプリケーション自動起動
    private String mtrCappautoload;
    // インラインフレーム表示設定
    private String mtrCiframeflag;
    // オンラインヘルプURL
    private String mtrConlinehelpurl;
    // オンラインヘルプWindow属性
    private String mtrConlinehelpattr;
    // タイプ
    private String mtrCtype;
    // 適用基準日区分
    private String mtrCcriterialdatetype;
    // システムID
    private String mtrCsystemid;
    // 検索対象範囲適用種別
    private String mtrCdatapermissiontype;
    // デフォルト検索対象者
    private String mtrCdefaulttargetuser;
    // テンプレート
    private String mtrCtemplateid;
    // サイト説明（日本語）
    private String mtrCsitecaptionja;
    // 画像URL
    private String mtrCimageurl;
    // URL
    private String mtrCurl;
    // 項目名（日本語）
    private String mtrCobjnameja;
    // ボタンID
    private String mtrCbuttonid;
    // 页面ID
    private String mtrCscreenid;
    // サブアプリケーションID
    private String mtrCsubappid;
    // アプリケーションID
    private String mtrCappid;
    // サイトID
    private String mtrCsiteid;
    // キー
    private String mtrCobjectid;
    // 対象バージョン
    private String mtrCversion;

}
