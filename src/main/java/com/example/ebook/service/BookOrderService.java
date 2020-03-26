package com.example.ebook.service;

import com.example.ebook.enums.InvestChannelEnum;
import com.example.ebook.enums.OrderTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookOrderMapper;
import com.example.ebook.model.*;
import com.example.ebook.util.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	private BookService bookService;
	@Autowired
	private UserService userService;
	
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
}
