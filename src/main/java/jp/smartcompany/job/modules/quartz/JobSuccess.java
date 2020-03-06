package jp.smartcompany.job.modules.quartz;

import jp.smartcompany.job.enums.ResponseMessage;

/**
 * @author Xiao Wenpeng
 */
public enum JobSuccess implements ResponseMessage {

    /**
     * ウェイクアップタイミングタスク成功
     */
    RESUME_SCHEDULE(0,"ウェイクアップタイミングタスク成功"),
    /**
     * 中断タイミングタスク成功
     */
    PAUSE_SCHEDULE(0,"中断タイミングタスク成功"),
    /**
     * タスク実行成功
     */
    RUN_SCHEDULE(0,"タスク実行成功"),
    /**
     * タスク削除成功
     */
    DELETE_SCHEDULE(0,"タスク削除成功"),
    /**
     * タスク更新成功
     */
    UPDATE_SCHEDULE(0,"タスク更新成功"),
    /**
     * タスク追加成功
     */
    SAVE_SCHEDULE(0,"タスク追加成功"),
    /**
     * リーダー追加成功
     */
    SAVE_LEADER(0,"リーダー追加成功"),;

    JobSuccess(final int code, final String msg) {
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
