package com.example.ebook.controller;

import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.BookService;
import com.example.ebook.service.ChapterService;
import com.example.ebook.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.List;


/**
 * @author pxx
 * Date 2020/3/8 13:27
 * @Description
 */
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
	
	@PostMapping("/upBook")
	public Object upBook(HttpServletRequest request,
						 @RequestParam(value = "bookFile") MultipartFile file) throws Exception {
		String bookUrls = fileService.upload(file, request, UpFileTypeEnum.EBOOK);
		//相关操作
		//分隔章节 后台进行
		chapterService.splitBook(file);
		System.out.println("ok");
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
}
