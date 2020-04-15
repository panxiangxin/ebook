package com.example.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.*;
import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author pxx
 * Date 2020/4/7 10:59
 * @Description
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BookService bookService;
	@Autowired
	private ChapterService chapterService;
	@Autowired
	private FileService fileService;
	@Autowired
	private PostService postService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private BookOrderService bookOrderService;
	@Autowired
	private StampOrderService stampOrderService;
	
	@UserLoginToken
	@GetMapping("/user")
	public Object userList(@RequestParam(value = "search", required = false) String search) {
		
		List<User> userList = userService.list(search);
		return new ResponseResult<>(ResultCode.CLICK_OK, userList);
	}
	
	@UserLoginToken
	@PutMapping("/user")
	public Object updateUser(@RequestBody AdminUserDTO adminUserDTO) {
		
		User userById = userService.findUserById(adminUserDTO.getId());
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		User user = new User();
		user.setId(adminUserDTO.getId());
		user.setUserName(adminUserDTO.getUsername());
		user.setPassword(adminUserDTO.getPassword());
		user.setSex(adminUserDTO.getSex());
		user.setAge(adminUserDTO.getAge());
		user.setBio(adminUserDTO.getBio());
		user.setQq(adminUserDTO.getQq());
		user.setStamps(adminUserDTO.getStamps());
		user.setMail(adminUserDTO.getMail());
		user.setGmtModified(System.currentTimeMillis());
		user.setAvatarUrl(adminUserDTO.getImageUrl());
		
		userService.update(user);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@DeleteMapping("/user/{id}")
	public Object deleteUser(@PathVariable("id") Long id) {
		
		System.out.println(id);
		User userById = userService.findUserById(id);
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@PostMapping("/user/deleteUserBatch")
	public Object deleteUserBatch(@RequestParam(value = "userList[]") Long[] userIds) {
		
		List<Long> ids = Arrays.asList(userIds);
		userService.deleteUserBatchByIds(ids);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@PutMapping("/user/password")
	public Object resetPassword(@RequestParam(value = "id") Long id) {
		
		User userById = userService.findUserById(id);
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		userService.resetPassword(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/book")
	public Object bookList(@RequestParam(value = "search", required = false) String search) {
		List<Book> books = bookService.findBySearch(search);
		return new ResponseResult<>(ResultCode.CLICK_OK, books);
	}
	
	@Transactional
	@UserLoginToken
	@PostMapping("/book")
	public Object updateBook(HttpServletRequest request,
							 @RequestParam("book") String book,
							 @RequestParam(value = "img", required = false) MultipartFile file,
							 @RequestParam(value = "bookFile", required = false) MultipartFile bookFile) throws Exception {
		
		Book books = JSON.parseObject(book, Book.class);
		Long id;
		if (file != null) {
			String bookImgUrl = fileService.upload(file, request, UpFileTypeEnum.EBOOK_COVER);
			books.setImgUrl(bookImgUrl);
		}
		if (books.getId() != null) {
			Book bookSearch = bookService.findBookById(books.getId());
			
			if (bookSearch == null) {
				throw new MyException(ResultCode.BOOK_NOT_FOUND);
			}
			id = books.getId();
			bookService.update(books);
		} else {
			//添加新的图书
			books.setSaleCount(0);
			books.setCommentCount(0);
			id = bookService.insert(books);
		}
		if (bookFile != null) {
			chapterService.splitBook(bookFile, books.getBookName(), id);
			log.info("{} is ok", books.getBookName());
		}
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@DeleteMapping("/book/{id}")
	public Object deleteBook(@PathVariable("id") Long id) {
		System.out.println(id);
		Book bookById = bookService.findBookById(id);
		
		if (bookById == null) {
			throw new MyException(ResultCode.BOOK_NOT_FOUND);
		}
		
		bookService.deleteById(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@PostMapping("/book/deleteBookBatch")
	public Object deleteBookBatch(@RequestParam(value = "bookList[]") Long[] bookIds) {
		
		List<Long> ids = Arrays.asList(bookIds);
		bookService.deleteBookBatchByIds(ids);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/book/download")
	public Object downloadBook(@RequestParam("bookId") String bookId) throws IOException {
		System.out.println(bookId);
		return bookService.downloadBookAdmin(bookId);
	}
	
	@UserLoginToken
	@PostMapping("/book/up")
	public Object upBook(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		
		long size = file.getSize();
		String bookUrls = fileService.upload(file, request, UpFileTypeEnum.EBOOK);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("bookSize", size);
		jsonObject.put("bookUrl", bookUrls);
		return new ResponseResult<>(ResultCode.CLICK_OK, jsonObject);
	}
	
	@UserLoginToken
	@GetMapping("/post")
	public Object getPost(@RequestParam(value = "search", required = false) String search,
						  @RequestParam(value = "tag", required = false) String tag,
						  @RequestParam(value = "sort",required = false) String sort) {
		
		List<PostDTO> postDTOS = postService.list(search, tag, sort);
		return new ResponseResult<>(ResultCode.CLICK_OK, postDTOS);
	}
	
	@UserLoginToken
	@PostMapping("/post/deletePostBatch")
	public Object deletePostBatch(@RequestParam("postList[]") Long[] postIds) {
		
		
		List<Long> ids = Arrays.asList(postIds);
		postService.deletePostBatchByIds(ids);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	
	@UserLoginToken
	@GetMapping("/comment")
	public Object comment() {
		
		List<UserCommentDTO> userCommentById = commentService.getUserCommentById(null, true);
		return new ResponseResult<>(ResultCode.CLICK_OK, userCommentById);
	}
	
	@UserLoginToken
	@PostMapping("/comment/deleteCommentPost")
	public Object deleteCommentPost(@RequestParam("commentList[]") Long[] commentIds) {
		List<Long> ids = Arrays.asList(commentIds);
		commentService.deleteCommentBatchByIds(ids);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/bookOrder")
	public Object bookOrders() {
		
		List<ReturnBookOrderDTO> returnBookOrderDTOS = bookOrderService.list();
		return new ResponseResult<>(ResultCode.CLICK_OK, returnBookOrderDTOS);
	}
	
	@UserLoginToken
	@DeleteMapping("/bookOrder/{id}")
	public Object deleteBookOrder(@PathVariable("id") String id) {
		bookOrderService.delete(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@PostMapping("/bookOrder/deleteBookOrderBatch")
	public Object deleteBookOrderBatch(@RequestParam("bookOrderList[]") String[] bookOrderList) {
		
		bookOrderService.deleteBookOrderBatch(bookOrderList);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/investOrder")
	public Object getInvestOrder() {
		
		List<ReturnStampsOrderDTO> list = stampOrderService.list();
		return new ResponseResult<>(ResultCode.CLICK_OK, list);
	}
	
	@UserLoginToken
	@DeleteMapping("/investOrder/{id}")
	public Object deleteInvestOrder(@PathVariable("id") String id) {
		stampOrderService.delete(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@PostMapping("/investOrder/deleteInvestOrderPatch")
	public Object deleteStampsOrderBatch(@RequestParam("stampsOrders[]") String[] stampsOrders) {
		
		stampOrderService.deleteBatch(stampsOrders);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
}
