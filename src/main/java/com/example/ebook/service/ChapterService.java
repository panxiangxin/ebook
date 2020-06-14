package com.example.ebook.service;

import com.example.ebook.mapper.ChapterMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.Chapter;
import com.example.ebook.model.ChapterExample;
import com.example.ebook.util.BuNovelGenerate;
import com.example.ebook.util.Content;
import com.example.ebook.util.MultipartFileToFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/16 17:59
 * @Description
 */
@Slf4j
@Service
public class ChapterService {
	
	@Autowired
	private ChapterMapper chapterMapper;

	@Async
	public void splitBook(MultipartFile mFile,String bookName,Long bookId) throws Exception {
		File file = MultipartFileToFile.multipartFileToFile(mFile);
		System.out.println(file.getName());
		List<Content> contentList = BuNovelGenerate.read(bookName, file);
		//存入数据库
		contentList.forEach(content -> {
			Chapter chapter = new Chapter();
			chapter.setBookId(bookId);
			chapter.setChapterName(content.getChapter());
			chapter.setNumber(content.getNumber());
			chapter.setChapterContent(content.getContent());
			chapterMapper.insertSelective(chapter);
		});
		MultipartFileToFile.deleteTempFile(file);
	}
	
	
	public List<String> getChapterName(String bookId) {
		Long id = Long.parseLong(bookId);
		List<String> chapterName;
		ChapterExample chapterExample = new ChapterExample();
		chapterExample.createCriteria()
				.andBookIdEqualTo(id);
		List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
		 chapterName = chapters.stream().map(Chapter::getChapterName).collect(Collectors.toList());
		return chapterName;
	}
}
