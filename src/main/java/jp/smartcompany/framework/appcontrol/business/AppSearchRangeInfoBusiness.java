package jp.smartcompany.framework.appcontrol.business;

import java.util.List;

/**
 * アプリケーション別検索範囲取得処理のLogicインタフェース
 * @author Xiao Wenpeng
 */
public interface AppSearchRangeInfoBusiness {

    /**
     * 検索対象範囲条件作成
     * @param   psApplicationURL    アプリケーションURL
     */
    void create(String psApplicationURL, String psMode);
    /**
     * 検索対象範囲条件作成(ドメイン指定あり)
     *
     * @param psApplicationURL アプリケーションURL
     * @param psDomeinCode ドメインコード
     */
    void create(String psApplicationURL, String psDomeinCode, String psMode);
    /**
     * 検索対象範囲条件(From句)取得
     * @return List 検索対象範囲条件のFrom句リスト
     */
    List<String> getFrom();

    /**
     * 検索対象範囲条件(結合Where句)取得
     * @return List 検索対象範囲条件の結合Where句
     */
    List < String > getCombWhere();

    /**
     * 検索対象範囲条件(条件Where句)取得
     * @return String   検索対象範囲条件の条件Where句
     */
    String getCondWhere();

    /**
     * 検索対象範囲条件 設定済みフラグ取得
     *
     * @return condSetupFlg
     */
    boolean isCondSetupFlg();

    /**
     * 検索対象範囲 退職者検索対象範囲を取得します
     * @return 退職者検索対象範囲フラグ
     */
    int getCondRetired();

    /**
     * リクエストパラメータよりサイトID取得（自動インジェクション）
     *
     * @param psSite サイトID
     */
    void setPsSite(String psSite);

    /**
     * リクエストパラメータよりアプリケーションID取得（自動インジェクション）
     *
     * @param psApp アプリケーションID
     */
    void setPsApp(String psApp);

    /**
     * リクエストパラメータより検索対象者のユーザID取得（自動インジェクション）
     *
     * @param psTargetUser 検索対象者のユーザID
     */
    void setPsTargetUser(String psTargetUser);

    /**
     * リクエストパラメータより選択中の法人コード取得（自動インジェクション）
     *
     * @param psSelectedComp 選択中の法人コード
     */
    void setPsSelectedComp(String psSelectedComp);

    /**
     * リクエストパラメータより選択中の組織コード取得（自動インジェクション）
     *
     * @param psSelectedDept 選択中の組織コード
     */
    void setPsSelectedDept(String psSelectedDept);

}
