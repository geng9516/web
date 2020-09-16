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
     * 登录过程中出现错误的异常提示信息
     */
    String PROVIDER_NOT_FOUND = "内部エラーが発生しました。システム管理者までご連絡下さい。";
    String ACCOUNT_LOCKED = "このアカウントはロックアウトされました。システム管理者へ連絡してください。";
    String ACCOUNT_EXPIRED = "ログインできませんでした。アカウント、パスワードを確認してください。";
    String ACCOUNT_DISABLED = "ログインできませんでした。アカウント、パスワードを確認してください。 ";
    String PASSWORD_ERROR = "ログインできませんでした。アカウント、パスワードを確認してください。";
    String UNKNOWN_LOGIN_ERROR = "登録処理中に想定外のエラーが発生しました。システム管理者までお問い合わせください。";
    String PASSWORD_EXPIRED = "パスワードの有効期限が切れています。新しいパスワードを設定してください。";
    String INCORRECT_CONTENT_TYPE ="ログインContent-Typeはまちがいます";

}
