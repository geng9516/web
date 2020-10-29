package jp.smartcompany.boot.enums;

/**
 * @author xiao wenpeng
 */
public enum SuccessMessage implements ResponseMessage {

    /**
     * Success
     */
    SUCCESS(0, "リクエストしました。"),
    LOGIN(0,"ログインしました。");

    private final int code;
    private final String msg;

    SuccessMessage(int code, String msg) {
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
