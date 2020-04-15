package com.example.ebook.dto;

import com.example.ebook.model.Book;
import com.example.ebook.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/12 13:24
 * @Description
 */
@Data
public class ReturnBookOrderDTO {
	
	private String id;
	private User user;
	private Double stamps;
	private Book book;
	private Long gmtCreate;
}
