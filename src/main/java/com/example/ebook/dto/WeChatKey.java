package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/21 20:49
 * @Description
 */
@Data
public class WeChatKey {
	private String openid;
	private String session_key;
}
