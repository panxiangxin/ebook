package com.example.ebook.service;

import com.example.ebook.dto.UpBookDTO;
import com.example.ebook.mapper.BookExtMapper;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.BookExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Book> list(String tags) {
		
		List<Book> books;
		if (StringUtils.isBlank(tags) || StringUtils.equals(tags,"全部")) {
			BookExample bookExample = new BookExample();
			bookExample.createCriteria()
					.andBookNameIsNotNull();
			bookExample.setOrderByClause("id desc");
			
			 books = bookMapper.selectByExampleWithBLOBs(bookExample);
			return books;
		}
		String[] tag = StringUtils.split(tags, ',');
		String regexpTag = Arrays
								   .stream(tag)
								   .filter(StringUtils::isNotBlank)
								   .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
								   .filter(StringUtils::isNotBlank)
								   .collect(Collectors.joining("|"));
		
		 books = bookExtMapper.selectRelated(regexpTag);
		return books;
		
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
}
