package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/21 11:14
 * @Description
 */
@Data
public class InvestOrderDTO {
	
	private String id;
	private Long userId;
	private Integer transType;
	private Integer type;
	private Double money;
	private String subject;
	
}
