package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2019/10/9 16:56
 * @Description
 */
public enum NotificationEnum {
	/**
	 * 通知类型
	 */
	REPLY_ARTICLE(1,"回复了书籍"),
	REPLY_COMMENT(2,"回复了你的评论"),
	REPLY_TOPIC(3,"回复了你的主题"),
	REPLY_TOPIC_COMMENT(4, "回复了你的主题评论"),
	
	;
	private int type;
	private String name;
	
	NotificationEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
	
	public static String getNameOf(int type){
		for (NotificationEnum notificationEnum:NotificationEnum.values()) {
			if (notificationEnum.getType() == type) {
				return notificationEnum.getName();
			}
		}
		return "";
	}
}
