package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/21 20:03
 * @Description
 */
@Data
public class WeChatUserInfo {
	
	private String nickName;
	private Integer gender;
	private String language;
	private String city;
	private String province;
	private String country;
	private String avatarUrl;
	private String signature;
	private String encryptedData;
	private String iv;
}
