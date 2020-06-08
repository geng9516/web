package jp.smartcompany.framework.auth;

import jp.smartcompany.framework.appcontrol.TopPageInfo;

/**
 * アプリケーション起動権限判定処理用Logicインターフェース<br>.
 * @author Xiao Wenpeng
 */
public interface AppAuthJudgmentLogic {
    /**
     * セッションにアプリケーション起動権限判定結果情報をセットします.
     * @return TopPageInfo トップページ情報
     */
    TopPageInfo getAppAuthJudgmentInfo();
}
