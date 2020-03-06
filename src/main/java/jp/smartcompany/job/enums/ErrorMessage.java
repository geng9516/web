package jp.smartcompany.job.enums;

import org.springframework.http.HttpStatus;

/**
 * @author xiao wenpeng
 */
public enum ErrorMessage implements ResponseMessage {

    /**
     * TOO_MANY_REQUESTS
     */
    TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS.value(), "現在のシステムリクエストが多すぎます。しばらくしてからもう一度お試しください"),
    /**
     * SERVER_INTERNAL_ERROR
     */
    SERVER_INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(),"サーバーエラー"),
    /**
     * ACCESS_LIMIT_REQUEST
     */
    ACCESS_LIMIT_REQUEST(HttpStatus.TOO_MANY_REQUESTS.value(),"リクエストが多すぎるため、{}秒後にもう一度お試しください"),
    /**
     * BAD_REQUEST_ERROR
     */
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST.value(), "必要のあるパラメーターが存在しないか、タイプが正しくありません"),
    /**
     * NOT_FOUND_ERROR
     */
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND.value(), "リクエストインターフェースが存在しません"),
    /**
     * METHOD_NOT_ALLOWED_ERROR
     */
    METHOD_NOT_ALLOWED_ERROR(HttpStatus.METHOD_NOT_ALLOWED.value(),"このインターフェースは「{}」リクエストをサポートしていません"),
    /**
     * CONTENT_TYPE_SUPPORTED_ERROR
     */
    CONTENT_TYPE_SUPPORTED_ERROR(HttpStatus.NOT_ACCEPTABLE.value(),"不正なContent-Type"),
    /**
     * AUTH_NOT_ENOUGH
     */
    AUTH_NOT_ENOUGH(HttpStatus.UNAUTHORIZED.value(),"認処に失敗し、アクセスできません"),
    /**
     * FIELD_NULL_ERROR
     */
    FIELD_NULL_ERROR(HttpStatus.EXPECTATION_FAILED.value(),"フィールド:{}の値はnullにできません"),
    /**
     * SITE_MAINTENANCE
     */
    SITE_MAINTENANCE(HttpStatus.OK.value(),"ウェブサイトはメンテナンス中です"),
    /**
     * LOAD_CONFIG_FAILED
     */
    LOAD_CONFIG_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(),"グローバル設定の読み込みに失敗しました"),
    /**
     * PASSWORD_INVALID
     */
    PASSWORD_INVALID(60004,"パスワードが間違っています"),
    /**
     * USER_NOT_EXIST
     */
    USER_NOT_EXIST(60013,"ユーザーが存在しません"),

    SESSION_EXPIRE(60014,"セッションの期限が切れました,もう一度ログインしてください。"),

    NPC_EXCEPTION(60016,"NPC異常,もう一度ログイン試してください。"),

    MAIL_CONFIG_INVALID(60015,"メール配置はまだないです"),

    OUT_OF_BOUNDARY(60016,"範囲外の配列異常,もう一度ログイン試してください。");

    private final int code;
    private final String msg;

    ErrorMessage(final int code,final String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
