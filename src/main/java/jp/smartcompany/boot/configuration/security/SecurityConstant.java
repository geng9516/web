package jp.smartcompany.boot.configuration.security;

/**
 * Spring Security需要的常量定义文件
 * @author Xiao Wenpeng
 */
public interface SecurityConstant {

    /**
     * 未登录者角色
     */
    String ANONYMOUS_USER = "anonymous_user";

    /**
     * 登录提示
     */
    String LOGIN_SUCCESS = "ログインしました";
    /**
     * 登出提示
     */
    String LOGOUT_SUCCESS = "ログアウトしました";
    /**
     * 登录过程中出现错误的异常提示信息
     */
    String USERNAME_OR_PASSWORD_NOT_EXISTS = "ユーザ名またはパスワードを入力してください";
    String ACCOUNT_LOCKED = "このアカウントはロックアウトされました、システム管理者へ連絡してください";
    String ACCOUNT_EXPIRED = "ログインできませんでした。アカウント、パスワードを確認してください";
    String ACCOUNT_DISABLED = "ログインできませんでした。アカウント、パスワードを確認してください ";
    String PASSWORD_ERROR = "ログインできませんでした。アカウント、パスワードを確認してください";
    String UNKNOWN_LOGIN_ERROR = "登録処理中に想定外のエラーが発生しました、システム管理者までお問い合わせください";
    String PASSWORD_EXPIRED = "パスワードの有効期限が切れています、新しいパスワードを設定してください";
    String INCORRECT_CONTENT_TYPE ="ログインContent-Typeはまちがいます";
    String LOGIN_TIMEOUT = "タイムアウトしました";

    String LOGIN_TRY_COUNT_ERROR = "パスワード入力最大許容回数が設定されていない";
    String PASSWORD_INVALID_DAYS_ERROR = "パスワードﾞ有効日数が設定されていない";

}
