package com.example.ebook.service;

import com.example.ebook.dto.ReturnBookOrderDTO;
import com.example.ebook.enums.InvestChannelEnum;
import com.example.ebook.enums.OrderTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookExtMapper;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.mapper.BookOrderMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.*;
import com.example.ebook.util.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/25 18:00
 * @Description
 */
@Service
public class BookOrderService {
	
	@Autowired
	private BookOrderMapper bookOrderMapper;
	@Autowired
	private BookExtMapper bookExtMapper;
	@Autowired
	private BookService bookService;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	
	public  List<BookOrder> findOrderByUserId(Long userId) {
		BookOrderExample bookOrderExample = new BookOrderExample();
		bookOrderExample.createCriteria()
				.andUserIdEqualTo(userId);
		return bookOrderMapper.selectByExample(bookOrderExample);
	}
	
	
	@Transactional
	public void insertBookOrder(String userId, String bookId) {
		
		User user = userService.findUserById(Long.parseLong(userId));
		if (user == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		Book book = bookService.getBookById(bookId);
		if (book == null) {
			throw new MyException(ResultCode.BOOK_NOT_FOUND);
		}
		if (book.getBookStamps() > user.getStamps()) {
			throw new MyException(ResultCode.USER_STAMPS_FAIL);
		}
		
		String bookOrderId = OrderCodeFactory.genSerialNum(
				OrderTypeEnum.BUY_BOOK.getType(),
				InvestChannelEnum.BOUGHT.getType(),
				user.getId()
		);
		
		BookOrder bookOrder = new BookOrder();
		bookOrder.setId(bookOrderId);
		bookOrder.setUserId(user.getId());
		bookOrder.setStamps(book.getBookStamps());
		bookOrder.setBookId(book.getId());
		bookOrder.setGmtCreate(System.currentTimeMillis());
		
		//写入订单
		bookOrderMapper.insert(bookOrder);
		//书籍销量加一
		book.setSaleCount(1);
		bookExtMapper.incSaleCountCount(book);
		//更新用户积分
		user.setStamps(user.getStamps() - book.getBookStamps());
		userService.update(user);
		
	}
	
	public List<BookOrder> findOrderByUserIdAndBookId(Long userId, String bookId) {
		
		Long bookIds = Long.parseLong(bookId);
		BookOrderExample example = new BookOrderExample();
		example.createCriteria()
				.andUserIdEqualTo(userId)
				.andBookIdEqualTo(bookIds);
		return bookOrderMapper.selectByExample(example);
	}
	
	public List<ReturnBookOrderDTO> list() {
		
		BookOrderExample example = new BookOrderExample();
		example.createCriteria()
				.andIdIsNotNull();
		example.setOrderByClause("id desc");
		List<BookOrder> bookOrders = bookOrderMapper.selectByExample(example);
		
		if (bookOrders.size() == 0) {
			return new ArrayList<>();
		}
		
		//获取用户id
		List<Long> userIds = bookOrders.stream().map(BookOrder::getUserId).collect(Collectors.toList());
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
		
		//获取book
		List<Long> bookIds = bookOrders.stream().map(BookOrder::getBookId).collect(Collectors.toList());
		BookExample bookExample = new BookExample();
		bookExample.createCriteria()
				.andIdIn(bookIds);
		List<Book> bookList = bookMapper.selectByExampleWithBLOBs(bookExample);
		Map<Long, Book> bookMap = bookList.stream().collect(Collectors.toMap(Book::getId, book -> book));
		
		List<ReturnBookOrderDTO> returnBookOrderDTOS = bookOrders.stream().map(bookOrder -> {
			ReturnBookOrderDTO returnBookOrderDTO = new ReturnBookOrderDTO();
			BeanUtils.copyProperties(bookOrder, returnBookOrderDTO);
			returnBookOrderDTO.setBook(bookMap.get(bookOrder.getBookId()));
			returnBookOrderDTO.setUser(userMap.get(bookOrder.getUserId()));
			
			return returnBookOrderDTO;
		}).collect(Collectors.toList());
		
		return  returnBookOrderDTOS;
	}
	
	public void delete(String id) {
		//删除订单书籍销量减一
		BookOrder bookOrder = bookOrderMapper.selectByPrimaryKey(id);
		Book book = bookMapper.selectByPrimaryKey(bookOrder.getBookId());
		if (book != null) {
			book.setSaleCount(-1);
			bookExtMapper.incSaleCountCount(book);
		}
		bookOrderMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteBookOrderBatch(String[] bookOrderList) {
		
		for (String s : bookOrderList) {
			delete(s);
		}
	}
}
