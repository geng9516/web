package jp.smartcompany.job.modules.core;

import jp.smartcompany.boot.enums.ResponseMessage;

/**
 * @author Xiao Wenpeng
 */
public enum CoreError implements ResponseMessage {

    USER_LOCK(100,"ユーザーはロックされており、ログインできません"),
    LOGIN_GROUP_NOT_FOUND(101,"ログイングループは空にできません");

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
