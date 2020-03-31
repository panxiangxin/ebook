package com.example.ebook.enums;

/**
 * @author pxx
 * Date 2019/10/9 16:56
 * @Description
 */
public enum NotificationStatusEnum {
	/**
	 * 通知状态
	 */
	UNREAD(0),
	READ(1)
	;
	private int status;
	
	NotificationStatusEnum(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
