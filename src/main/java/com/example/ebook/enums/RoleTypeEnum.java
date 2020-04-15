package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/4/15 10:55
 * @Description
 */
public enum RoleTypeEnum {
	/*
	1.评论了书籍
	2.评论了书籍评论
	3.评论了主题
	4.评论了主题评论
	 */
	ROLE_ADMIN(1),ROLE_USER(2);
	private final Integer type;
	RoleTypeEnum(Integer type){
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
