package com.example.ebook.mapper;

import com.example.ebook.model.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pxx
 * Date 2020/3/10 15:13
 * @Description
 */
public interface BookExtMapper {
	List<Book> selectRelated(String regexpTag);
	
	/**
	 * 书籍评论 评论加一
	 * @param book
	 * @return
	 */
	int incCommentCount(Book book);
	
	/**
	 * 书籍销量加一
	 * @param book
	 * @return
	 */
	int saleCountCount(Book book);
	
	List<Book> selectByAuthorOrName(@Param("search") String search);
}
