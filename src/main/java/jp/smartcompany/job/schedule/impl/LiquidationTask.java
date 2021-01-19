package jp.smartcompany.job.schedule.impl;

import jp.smartcompany.job.schedule.ITask;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 清算劳动制
 */
@Component
@RequiredArgsConstructor
public class LiquidationTask implements ITask {

    /**
     * 每隔1分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    @Override
    public void execute() {

    }
}
