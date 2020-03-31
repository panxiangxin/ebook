package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/30 11:26
 * @Description
 */
@Data
public class UserReturnDTO {
	
	private Long id;
	
	private String accountId;
	
	private String userName;
	
	private String token;
	
	private String password;
	
	private String sex;
	
	private Integer age;
	
	private Integer status;
	
	private String bio;
	
	private Integer qq;
	
	private Double stamps;
	
	private String mail;
	
	private Long gmtModified;
	
	private Long gmtCreate;
	
	private String avatarUrl;
	
	private Long unReadCount;
	
}
