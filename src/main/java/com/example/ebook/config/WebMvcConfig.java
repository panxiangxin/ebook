package com.example.ebook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pxx
 * Date 2020/3/3 12:24
 * @Description
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Value("${file.staticAccessPath}")
	private String staticAccessPath;
	
	@Value("${file.staticEbookAccessPath}")
	private String staticEbookAccessPath;
	
	@Value("${file.staticEbookCoverAccessPath}")
	private String staticEbookCoverAccessPath;
	
	@Value("${smas.captrue.image.path}")
	private String captureImagePath;
	
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	
	@Value("${smas.ebook.path}")
	private String eBookPath;
	
	@Value("${smas.ebook.cover}")
	private String eBookCover;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowCredentials(true).allowedMethods("*").maxAge(3600);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(staticAccessPath).addResourceLocations("file:" + uploadFolder + captureImagePath);
		registry.addResourceHandler(staticEbookAccessPath).addResourceLocations("file:" + uploadFolder + eBookPath);
		registry.addResourceHandler(staticEbookCoverAccessPath).addResourceLocations("file:" + uploadFolder + eBookCover);
	}
	
}
