package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/2 15:43
 * @Description
 */
@Data
public class RegisterUserDTO {
	private String username;
	private String password;
	private String sex;
	private int age;
	private String email;
	private String imageUrl;
}
