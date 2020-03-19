package com.example.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.example.ebook.dto.UpBookDTO;
import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.Book;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.BookService;
import com.example.ebook.service.ChapterService;
import com.example.ebook.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author pxx
 * Date 2020/3/8 13:27
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/book")
public class BookAPI {
	
	@Autowired
	private BookService bookService;
	@Autowired
	private FileService fileService;
	@Autowired
	private ChapterService chapterService;
	
	@GetMapping("/books")
	public Object books(String tags) {
		List<Book> bookList = bookService.list(tags);
		return new ResponseResult<>(ResultCode.CLICK_OK, bookList);
	}
	
	@GetMapping("/getBook")
	public Object book(String bookId) {
		System.out.println(bookId);
		Book book = bookService.getBookById(bookId);
		return new ResponseResult<>(ResultCode.CLICK_OK, book);
	}
	
	@Transactional
	@PostMapping("/upBook")
	public Object upBook(HttpServletRequest request,
						 @RequestParam(value = "bookFile") MultipartFile file,
						 @RequestParam(value = "book") String book,
						 @RequestParam(value = "bookImg") MultipartFile img) throws Exception {
		String bookUrls = fileService.upload(file, request, UpFileTypeEnum.EBOOK);
		//上传书籍封面
		String imgUrl = fileService.upload(img, request, UpFileTypeEnum.EBOOK_COVER);
		//相关操作
		UpBookDTO upBookDTO = JSON.parseObject(book, UpBookDTO.class);
		//存入book
		Long booId = bookService.insert(upBookDTO, bookUrls, imgUrl);
		//分隔章节 后台进行
		chapterService.splitBook(file, upBookDTO.getBookName(), booId);
		log.info("{} is ok", upBookDTO.getBookName());
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@GetMapping("/getChapters")
	public Object getChapters(@RequestParam(value = "bookId") String bookId) {
		
		if (bookService.getBookById(bookId) == null) {
			throw new MyException(ResultCode.BOOK_NOT_FOUND);
		}
		List<String> chapterNames = chapterService.getChapterName(bookId);
		return new ResponseResult<>(ResultCode.CLICK_OK, chapterNames);
	}
}
