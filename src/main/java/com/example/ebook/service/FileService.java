package com.example.ebook.service;

import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.util.FileHandleUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author pxx
 * Date 2020/3/3 12:27
 * @Description
 */
@Service
public class FileService {
	
	@Value("${smas.captrue.image.path}")
	private String captureImagePath;
	
	@Value("${file.uploadFolder}")
	private String uploadFolder;
	
	@Value("${file.uri}")
	private String uri;
	
	@Value("${file.ebook.uri}")
	private String eBookUri;
	
	@Value("${file.ebook.cover.uri}")
	private String eBookCoverUri;
	
	@Value("${smas.ebook.path}")
	private String eBookPath;
	
	
	@Value("${smas.ebook.cover}")
	private String eBookCover;
	
	
	public String upload(MultipartFile file, HttpServletRequest request, UpFileTypeEnum typeEnum) {
		String fileName = file.getOriginalFilename();
		String pathDeposit = "";
		if (typeEnum.getType().equals(UpFileTypeEnum.USER_AVATAR.getType())) {
			pathDeposit = uploadFolder + captureImagePath;
		} else if (typeEnum.getType().equals(UpFileTypeEnum.EBOOK.getType())) {
			pathDeposit = uploadFolder + eBookPath;
		}else {
			pathDeposit = uploadFolder + eBookCover;
		}
		String urls = "";
		try {
			FileHandleUtils.upload(file.getInputStream(), pathDeposit, fileName);
			urls = FileHandleUtils.getServerIPPort(request) + File.separator + uri(typeEnum) + fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return urls;
	}
	
	public String uri(UpFileTypeEnum upFileTypeEnum) {
		
		if (upFileTypeEnum.getType().equals(UpFileTypeEnum.USER_AVATAR.getType())) {
			return uri;
		} else if (upFileTypeEnum.getType().equals(UpFileTypeEnum.EBOOK.getType())) {
			return eBookUri;
		} else {
			return eBookCoverUri;
		}
	}
}
