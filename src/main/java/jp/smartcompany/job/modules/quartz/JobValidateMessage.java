package jp.smartcompany.job.modules.quartz;


/**
 * @author Xiao Wenpeng
 */
public interface JobValidateMessage {

    String JOB_ID_EMPTY = "タスクIDは空にできません";
    String BEAN_NAME_EMPTY = "Bean名は空にできません";
    String BEAN_NAME_LENGTH_1_100 = "beanの名前の長さは1~100文字のみで構成できます";
    String PARAM_LENGTH_1_100 = "パラメータの長さは1~100文字のみで構成できます";
    String JOB_EXPRESS_EMPTY="計算式は空にできません";
    String EXPRESS_LENGTH_1_100 = "計算式の長さは1〜100文字のみで構成できます";
    String TASK_STATUS_EMPTY = "タスクのステータスは空にできません";
    String TASK_STATUS_INVALID = "無効なタスクステータス";

}
