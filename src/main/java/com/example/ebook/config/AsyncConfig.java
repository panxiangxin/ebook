package com.example.ebook.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author pxx
 * Date 2020/3/16 17:57
 * @Description
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {
	@Override
	public Executor getAsyncExecutor() {
		// 自定义线程池
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 核心线程数
		taskExecutor.setCorePoolSize(10);
		// 最大线程数
		taskExecutor.setMaxPoolSize(30);
		// 线程队列最大线程数
		taskExecutor.setQueueCapacity(2000);
		// 初始化线程池
		taskExecutor.initialize();
		return taskExecutor;
	}
	
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			log.error("Error Occurs in async method:{}", ex.getMessage());
		};
	}
}
