package com.example.ebook.service;

import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.util.FileHandleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author pxx
 * Date 2020/3/3 12:27
 * @Description
 */
@Slf4j
@Service
public class FileService {
	
	
	@Value("${web.cc}")
	private String myWebCc;
	
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
			urls = myWebCc + File.separator + uri(typeEnum) + fileName;
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
	
	
	public ResponseEntity<byte[]> downloadFile(String filePath) throws IOException {
		
		File file = loadFileAsResource(filePath);
		
		// 设置下载文件名称，以下两种方式均可
		 String fileName = new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		//String fileName = URLEncoder.encode(file.getName(), "utf-8");
		HttpHeaders httpHeaders = new HttpHeaders();
		// 通知浏览器以下载文件方式打开
		ContentDisposition contentDisposition =
				ContentDisposition.builder("attachment").filename(fileName).build();
		httpHeaders.setContentDisposition(contentDisposition);
		// application/octet_stream设置MIME为任意二进制数据
		httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		 //使用apache commons-io 里边的 FileUtils工具类
		byte[] bytes = FileUtils.readFileToByteArray(file);
		return new ResponseEntity<>(bytes,
				httpHeaders, HttpStatus.OK);
		// 使用spring自带的工具类也可以 FileCopyUtils
//		return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),
//				httpHeaders, HttpStatus.OK);
	}
	public File loadFileAsResource(String filePath) {
		
		String path = filePath.replaceAll("\\\\", "/");
		try {
			UrlResource resource = new UrlResource(path);
			File file = new File(uploadFolder + eBookPath + Objects.requireNonNull(resource.getFilename()));
			if (file.exists()) {
				return file;
			}
			throw new MyException(ResultCode.FILE_NOT_FOUND);
		} catch (MalformedURLException e) {
			throw new MyException(ResultCode.FILE_NOT_FOUND);
		}
	}
}
