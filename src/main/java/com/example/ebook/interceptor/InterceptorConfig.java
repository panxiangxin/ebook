package com.example.ebook.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pxx
 * Date 2020/2/27 17:03
 * @Description
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
				.addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
	}
}
