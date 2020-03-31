package com.example.ebook.controller;

import com.example.ebook.exception.ResultCode;
import com.example.ebook.provider.UCloudProvider;
import com.example.ebook.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author pxx
 * Date 2020/3/31 18:23
 * @Description
 */
@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private UCloudProvider uCloudProvider;
	
	@RequestMapping("/upload/image")
	public Object upload(@RequestParam(value = "file") MultipartFile file){
		
		try {
			String fileName = uCloudProvider.upload(file.getInputStream(), file.getContentType(), Objects.requireNonNull(file.getOriginalFilename()));
			return new ResponseResult<>(ResultCode.CLICK_OK, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseResult<>(ResultCode.FILE_UPLOAD_ERROR);
	}
}
