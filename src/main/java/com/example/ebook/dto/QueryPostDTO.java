package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/2 10:45
 * @Description
 */
@Data
public class QueryPostDTO {
	
	private String search;
	private String sort;
	private String tag;
	private Long time;
	
}
