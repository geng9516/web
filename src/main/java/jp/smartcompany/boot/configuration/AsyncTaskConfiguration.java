package jp.smartcompany.boot.configuration;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import jp.smartcompany.job.asynctask.SendMailTask;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.enums.MailType;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;
import jp.smartcompany.job.modules.core.service.ErrorAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AsyncTaskConfiguration implements AsyncConfigurer {

    private final SendMailTask sendMailTask;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("web-work-async-task-pool");
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(80);
        executor.setKeepAliveSeconds(3);
        executor.setQueueCapacity(1000);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池关闭的时候是否会等待所有的任务都完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            if (ex instanceof MailException){
                String from = (String)params[0];
                MailType mailType = (MailType) params[1];
                String empId = (String)params[2];
                Date standardDate = (Date)params[3];
                String toAddress = (String)params[4];
                String title = (String)params[5];
                String content = (String)params[6];
                for (Object param : params) {
                    if (param instanceof MailType){
                        mailType = (MailType)param;
                    }
                }
                sendMailTask.saveSendMailHistory(from,mailType.getDesc(),empId,standardDate,toAddress,title,content,2);
            } else {
                Date now = DateUtil.date();
                ErrorAuditService errorAuditService = SpringUtil.getBean(ErrorAuditService.class);
                ErrorAuditDO error = new ErrorAuditDO();
                error.setCalledMethod(method.getName());
                error.setMessage(ex.getMessage());
                error.setMethod("ASYNC TASK");
                error.setUsername("ASYNC TASK");
                error.setCreateTime(now);
                error.setUpdateTime(now);
                error.setParams(ArrayUtil.join(params, ","));
                errorAuditService.save(error);
            }
        };
    }

}
