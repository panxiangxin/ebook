package com.example.ebook.service;

import com.example.ebook.model.Book;
import com.example.ebook.util.BuNovelGenerate;
import com.example.ebook.util.Content;
import com.example.ebook.util.MultipartFileToFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @author pxx
 * Date 2020/3/16 17:59
 * @Description
 */
@Service
public class ChapterService {

	@Async
	public void splitBook(MultipartFile mFile) throws Exception {
		File file = MultipartFileToFile.multipartFileToFile(mFile);
		List<Content> contentList = BuNovelGenerate.read("11", file);
		//存入数据库
		contentList.forEach(content -> {
			System.out.println(content.getChapter()+" : "+content.getNumber());
		});
		MultipartFileToFile.deleteTempFile(file);
	}
	
}
