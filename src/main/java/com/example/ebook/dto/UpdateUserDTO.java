package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/23 17:21
 * @Description
 */
@Data
public class UpdateUserDTO {
	
	private Long id;
	private String username;
	private String password;
	private String sex;
	private String bio;
	private Integer qq;
	private int age;
	private String mail;
	private String imageUrl;
}
