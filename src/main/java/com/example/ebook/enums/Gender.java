package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/4/21 21:03
 * @Description
 */
public enum Gender {
	UNKNOW(0, "未知"),
	MAN(1, "男"),
	WOMAN(2, "女");
	
	private final Integer value;
	private final String name;
	
	Gender(int value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
}
