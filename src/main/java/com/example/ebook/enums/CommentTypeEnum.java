package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/3/14 12:21
 * @Description
 */
public enum CommentTypeEnum {
	/*
	1.评论了书籍
	2.评论了评论
	 */
	BOOK(1),COMMENT(2);
	private Integer type;
	CommentTypeEnum(Integer type){
		this.type = type;}
	public static boolean isExist(Integer type) {
		for (CommentTypeEnum value : CommentTypeEnum.values()) {
			if(value.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
	
	public Integer getType() {
		return type;
	}
}
