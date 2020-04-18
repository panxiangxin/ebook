package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2020/4/15 10:55
 * @Description
 */
public enum RoleTypeEnum {
	/*
	0 普通用户
	1 管理员
	 */
	ROLE_ADMIN(1, "admin"),ROLE_USER(0, "user");
	private final Integer type;
	private final String message;
	RoleTypeEnum(Integer type, String message){
		this.type = type;
		this.message = message;
	}
	public static boolean isExist(Integer type) {
		for (RoleTypeEnum value : RoleTypeEnum.values()) {
			if(value.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
	public static String getMessageByType(Integer type) {
		for (RoleTypeEnum value: RoleTypeEnum.values()){
			if (value.getType().equals(type)) {
				return value.getMessage();
			}
		}
		return null;
	}
	public Integer getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
}
