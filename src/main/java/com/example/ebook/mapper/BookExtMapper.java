package com.example.ebook.mapper;

import com.example.ebook.model.Book;

import java.util.List;

/**
 * @author pxx
 * Date 2020/3/10 15:13
 * @Description
 */
public interface BookExtMapper {
	List<Book> selectRelated(String regexpTag);
}
