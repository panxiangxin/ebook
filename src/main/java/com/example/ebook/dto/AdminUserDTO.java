package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/8 12:47
 * @Description
 */
@Data
public class AdminUserDTO {
	private Long id;
	private String username;
	private String password;
	private String sex;
	private String bio;
	private Integer qq;
	private int age;
	private String mail;
	private String imageUrl;
	private Double stamps;
}
