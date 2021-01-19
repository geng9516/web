package jp.smartcompany.job.schedule.impl;

import jp.smartcompany.job.schedule.ITask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 月次集计
 */
@Component
@RequiredArgsConstructor
public class MonthlyOutputTask implements ITask {

    /**
     * 每隔五分钟执行一次
     */
    @Scheduled(cron = "0 */5 * * * ?")
    @Override
    public void execute() {

    }

}
