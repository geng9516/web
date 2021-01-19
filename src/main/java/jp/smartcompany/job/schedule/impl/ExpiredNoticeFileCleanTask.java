package jp.smartcompany.job.schedule.impl;

import jp.smartcompany.job.schedule.ITask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 清理揭示板上传的已经过期的公告附件和富文本图片
 */
@Component
@RequiredArgsConstructor
public class ExpiredNoticeFileCleanTask implements ITask {

    /**
     * 每天凌晨四点执行
     */
    @Scheduled(cron = "0 0 4 * * ?")
    @Override
    public void execute() {

    }

}
