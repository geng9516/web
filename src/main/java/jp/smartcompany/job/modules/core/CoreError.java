package jp.smartcompany.job.modules.core;

import jp.smartcompany.job.enums.ResponseMessage;

/**
 * @author Xiao Wenpeng
 */
public enum CoreError implements ResponseMessage {

    USER_LOCK(100,"ユーザーはロックされており、ログインできません");

    CoreError(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;

    @Override
    public int code() {
        return code;
    }

    @Override
    public String msg() {
        return msg;
    }
}
