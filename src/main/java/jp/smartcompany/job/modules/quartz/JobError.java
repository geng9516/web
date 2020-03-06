package jp.smartcompany.job.modules.quartz;

import jp.smartcompany.job.enums.ResponseMessage;

/**
 * @author Xiao Wenpeng
 */
public enum JobError implements ResponseMessage {

    /**
     * CronTriggerエラーを取得する
     */
    FETCH_CRON_ERROR(60107,"CronTriggerエラーを取得する"),
    /**
     * CronTriggerの作成が失敗しました
     */
    CREATE_CRON_ERROR(60108,"CronTriggerの作成が失敗しました"),
    /**
     * CronTriggerの更新が失敗しました
     */
    UPDATE_CRON_ERROR(60109,"CronTriggerの更新が失敗しました"),
    /**
     * スケジュールの即時実行が失敗しました
     */
    RUN_CRON_ERROR(60110,"スケジュールの即時実行が失敗しました"),
    /**
     * スケジュールの一時停止が失敗しました
     */
    PAUSE_CRON_ERROR(60111,"スケジュールの一時停止が失敗しました"),
    /**
     *スケジュールの再開が失敗しました
     */
    RESUME_CRON_ERROR(60112,"スケジュールの再開が失敗しました"),
    /**
     * スケジュールの削除が失敗しました
     */
    REMOVE_CRON_ERROR(60113,"スケジュールの削除が失敗しました"),;

    JobError(final int code,final String msg) {
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
