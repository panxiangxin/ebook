package com.example.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.ReturnBookDTO;
import com.example.ebook.dto.UpBookDTO;
import com.example.ebook.enums.InvestChannelEnum;
import com.example.ebook.enums.OrderTypeEnum;
import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.StampOrderMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.BookOrder;
import com.example.ebook.model.StampOrder;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.*;
import com.example.ebook.util.OrderCodeFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
	@Autowired
	private BookOrderService bookOrderService;
	
	@GetMapping("/books")
	public Object books(String tags) {
		List<ReturnBookDTO> bookList = bookService.list(tags);
		return new ResponseResult<>(ResultCode.CLICK_OK, bookList);
	}
	
	@GetMapping("/getBook")
	public Object book(String bookId, HttpServletRequest request) {
		
		
		ReturnBookDTO returnBook = bookService.findBookHasBought(bookId, request);
		
		return new ResponseResult<>(ResultCode.CLICK_OK, returnBook);
	}
	
	@Transactional
	@UserLoginToken
	@PostMapping("/upBook")
	public Object upBook(HttpServletRequest request,
						 @RequestParam(value = "bookFile") MultipartFile file,
						 @RequestParam(value = "book") String book,
						 @RequestParam(value = "bookImg") MultipartFile img) throws Exception {
		
		//书籍大小
		long size = file.getSize();
		
		String bookUrls = fileService.upload(file, request, UpFileTypeEnum.EBOOK);
		//上传书籍封面
		String imgUrl = fileService.upload(img, request, UpFileTypeEnum.EBOOK_COVER);
		//相关操作
		UpBookDTO upBookDTO = JSON.parseObject(book, UpBookDTO.class);
		//存入book
		Long booId = bookService.insert(upBookDTO, bookUrls, imgUrl, size);
		
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
	
	@UserLoginToken
	@PostMapping("/buy")
	public Object buyBook(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
		
		bookOrderService.insertBookOrder(userId, bookId);
		
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/download")
	public Object download(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId ) throws IOException {
		return bookService.downloadBook(userId, bookId);
		
	}
}
