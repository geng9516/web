package jp.smartcompany.boot.configuration;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import jp.smartcompany.boot.util.MailUtil;
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

    private final MailUtil mailUtil;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("web-work-async-task-pool");
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(8);
        executor.setKeepAliveSeconds(5);
        executor.setQueueCapacity(100);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
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
                MailType mailType = (MailType) params[0];
                String empId = (String)params[1];
                Date standardDate = (Date)params[2];
                String toAddress = (String)params[3];
                String title = (String)params[4];
                String content = (String)params[5];
                for (Object param : params) {
                    if (param instanceof MailType){
                        mailType = (MailType)param;
                    }
                }
                mailUtil.saveSendMailHistory(mailType.getDesc(),empId,standardDate,toAddress,title,content,2);
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
