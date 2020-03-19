package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/3/16 16:44
 * @Description
 */
public enum UpFileTypeEnum {
	/**
	 * 1.用户头像
	 * 2.电子书
	 * 3.电子书封面
	 */
	USER_AVATAR(1),
	EBOOK(2),
	EBOOK_COVER(3);
	private Integer type;
	
	UpFileTypeEnum(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return type;
	}
}
