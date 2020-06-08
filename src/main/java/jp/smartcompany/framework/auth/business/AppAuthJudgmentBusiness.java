package jp.smartcompany.framework.auth.business;

import jp.smartcompany.framework.appcontrol.TopPageInfo;

/**
 * アプリケーション起動権限判定処理用Logicインターフェース<br>.
 * @author Xiao Wenpeng
 */
public interface AppAuthJudgmentBusiness {
    /**
     * セッションにアプリケーション起動権限判定結果情報をセットします.
     * @return TopPageInfo トップページ情報
     */
    TopPageInfo getAppAuthJudgmentInfo();
}
