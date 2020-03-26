package com.example.ebook.service;

import com.auth0.jwt.JWT;
import com.example.ebook.dto.ReturnBookDTO;
import com.example.ebook.dto.UpBookDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookExtMapper;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.BookExample;
import com.example.ebook.model.BookOrder;
import com.example.ebook.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/8 13:34
 * @Description
 */
@Service
public class BookService {
	
	@Autowired
	BookMapper bookMapper;
	@Autowired
	BookExtMapper bookExtMapper;
	@Autowired
	BookOrderService bookOrderService;
	@Autowired
	UserService userService;
	@Autowired
	FileService fileService;
	
	
	public List<ReturnBookDTO> list(String tags) {
		
		List<Book> books;
		if (StringUtils.isBlank(tags) || StringUtils.equals(tags,"全部")) {
			BookExample bookExample = new BookExample();
			bookExample.createCriteria()
					.andBookNameIsNotNull();
			bookExample.setOrderByClause("id desc");
			
			 books = bookMapper.selectByExampleWithBLOBs(bookExample);
		} else {
			String[] tag = StringUtils.split(tags, ',');
			String regexpTag = Arrays
									   .stream(tag)
									   .filter(StringUtils::isNotBlank)
									   .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
									   .filter(StringUtils::isNotBlank)
									   .collect(Collectors.joining("|"));
			
			books = bookExtMapper.selectRelated(regexpTag);
		}
		List<ReturnBookDTO> returnBookDTOS = books.stream().map(book -> {
			ReturnBookDTO returnBookDTO = new ReturnBookDTO();
			BeanUtils.copyProperties(book, returnBookDTO);
			return returnBookDTO;
		}).collect(Collectors.toList());
		return returnBookDTOS;
	}
	
	public Book getBookById(String bookId) {
		Long id = Long.parseLong(bookId);
		return bookMapper.selectByPrimaryKey(id);
	}
	
	public Long insert(UpBookDTO upBookDTO, String bookUrls, String imgUrl, Long size) {
		
		Book book = new Book();
		book.setBookName(upBookDTO.getBookName());
		book.setImgUrl(imgUrl);
		book.setAuthor(upBookDTO.getAuthor());
		book.setTags(upBookDTO.getTags());
		book.setBookUrl(bookUrls);
		book.setBookStamps(upBookDTO.getStamps());
		book.setBookSize(size);
		book.setDate(System.currentTimeMillis());
		book.setBio(upBookDTO.getBio());
		
		bookMapper.insertSelective(book);
		return book.getId();
	}
	
	public ReturnBookDTO findBookHasBought(String bookId, HttpServletRequest request) {
		
		Book book = getBookById(bookId);
		ReturnBookDTO returnBookDTO = new ReturnBookDTO();
		BeanUtils.copyProperties(book, returnBookDTO);
		
		String oldToken = request.getHeader("token");
		if (StringUtils.isNotBlank(oldToken)) {
			String id = JWT.decode(oldToken).getAudience().get(0);
			Long userId = Long.parseLong(id);
			List<BookOrder> bookOrder = bookOrderService.findOrderByUserIdAndBookId(userId, bookId);
			if (bookOrder.size() == 0) {
				returnBookDTO.setHasBought(false);
			}
			returnBookDTO.setHasBought(true);
		} else {
			returnBookDTO.setHasBought(false);
		}
		return returnBookDTO;
	}
	
	public ResponseEntity<byte[]> downloadBook(String userId, String bookId ) throws IOException {
		
		User userById = userService.findUserById(Long.parseLong(userId));
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		Book book = bookMapper.selectByPrimaryKey(Long.parseLong(bookId));
		if (book == null) {
			throw new MyException(ResultCode.BOOK_NOT_FOUND);
		}
		List<BookOrder> orderByUserIdAndBookId = bookOrderService.findOrderByUserIdAndBookId(userById.getId(), bookId);
		if (orderByUserIdAndBookId.size() == 0) {
			throw new MyException(ResultCode.BOOK_ORDER_NOT_FOUND);
		}
		return fileService.downloadFile(book.getBookUrl());
	}
}
