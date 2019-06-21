package com.samsolution.demo.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
// see https://spring.io/guides/gs/async-method/
// see https://www.baeldung.com/spring-async
// see https://dzone.com/articles/spring-boot-creating-asynchronous-methods-using-as
public class AsyncConfig implements AsyncConfigurer {

    @Bean
    // Spring call "org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor.shutdown()"
    // as a "destroyMethod" on closing the application context
    public Executor rabbitExecutor(@Autowired TaskExecutorBuilder builder) {
        return builder
                .corePoolSize(4)
                .maxPoolSize(4)
                .queueCapacity(500)
                .threadNamePrefix("rabbitExec-")
                //.build()
                //.build(ThreadPoolTaskExecutor.class)
                .configure(new ThreadPoolTaskExecutor());

//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);
//        executor.setMaxPoolSize(4);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("rabbitExec-");
//        executor.initialize();
//        return executor;
    }

//    // Change default AsyncExecutor instance to be used when processing async method invocations.
//    @Override
//    public Executor getAsyncExecutor() {
//        return rabbitExecutor();
//    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
