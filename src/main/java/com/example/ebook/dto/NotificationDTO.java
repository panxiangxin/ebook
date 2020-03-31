package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/10/9 18:49
 * @Description
 */
@Data
public class NotificationDTO {
	
	private Long id;
	private Long gmtCreate;
	private Integer status;
	private Long notifier;
	private String notifierName;
	private String outerTitle;
	private Long outerid;
	private String typeName;
	private Integer type;
}
