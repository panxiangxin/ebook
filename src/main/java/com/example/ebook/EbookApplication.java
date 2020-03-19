package com.example.ebook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan(basePackages = "com.example.ebook.mapper")
public class EbookApplication {
	
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(uploadFolder);
		return factory.createMultipartConfig();
	}
	public static void main(String[] args) {
		SpringApplication.run(EbookApplication.class, args);
	}

}
