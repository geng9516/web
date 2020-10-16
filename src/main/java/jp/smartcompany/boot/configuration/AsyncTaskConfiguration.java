package jp.smartcompany.boot.configuration;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import jp.smartcompany.boot.util.SpringUtil;
import jp.smartcompany.job.modules.core.pojo.entity.ErrorAuditDO;
import jp.smartcompany.job.modules.core.service.ErrorAuditService;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncTaskConfiguration implements AsyncConfigurer {

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
            Date now = DateUtil.date();
            ErrorAuditService errorAuditService = SpringUtil.getBean(ErrorAuditService.class);
            ErrorAuditDO error = new ErrorAuditDO();
            error.setCalledMethod(method.getName());
            error.setMessage(ex.getMessage());
            error.setMethod("ASYNC TASK");
            error.setUsername("ASYNC TASK");
            error.setCreateTime(now);
            error.setUpdateTime(now);
            error.setParams(ArrayUtil.join(params,","));
            errorAuditService.save(error);
        };
    }

}
