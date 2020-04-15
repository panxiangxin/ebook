package com.example.ebook.dto;

import com.example.ebook.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/12 14:35
 * @Description
 */
@Data
public class ReturnStampsOrderDTO {
	private String id;
	private User user;
	private String secondId;
	private Integer type;
	private Integer channel;
	private Double money;
	private Long gmtCreate;
}
