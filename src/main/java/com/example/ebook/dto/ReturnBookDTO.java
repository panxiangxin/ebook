package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/26 16:20
 * @Description
 */
@Data
public class ReturnBookDTO implements Comparable<ReturnBookDTO>{
	private Long id;
	
	private String bookName;
	
	private String imgUrl;
	
	private String author;
	
	private String tags;
	
	private Double bookStamps;
	
	private Long bookSize;
	
	private Integer saleCount;
	
	private Long date;
	
	private String bio;
	
	private Boolean hasBought;
	
	@Override
	public int compareTo(ReturnBookDTO o) {
		return saleCount - o.saleCount;
	}
}
