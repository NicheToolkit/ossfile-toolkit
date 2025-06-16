package io.github.nichetoolkit.ossfile.configure;

import io.github.nichetoolkit.rest.util.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <code>OssfileAsyncExecutorConfigure</code>
 * <p>The ossfile async executor configure class.</p>
 * @author Cyan (snow22314@outlook.com)
 * @see org.springframework.scheduling.annotation.AsyncConfigurer
 * @see lombok.extern.slf4j.Slf4j
 * @see org.springframework.scheduling.annotation.EnableAsync
 * @see org.springframework.stereotype.Component
 * @since Jdk1.8
 */
@Slf4j
@EnableAsync
@Component
public class OssfileAsyncExecutorConfigure implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setDaemon(true);
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("ossfile-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("the async method has error, method: {}, error: {}", method.getName(), throwable.getMessage());
            GeneralUtils.printStackTrace(throwable);
        };
    }
}
