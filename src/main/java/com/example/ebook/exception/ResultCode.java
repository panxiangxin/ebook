package com.example.ebook.exception;


/**
 * @author pxx
 * Date 2020/2/27 22:01
 * @Description
 */
public enum ResultCode {
	/**
	 *
	 */
	CLICK_OK(true, 10001,"操作成功！"),
	/**
	 *
	 */
	USER_NOT_FOUND(false, 40001, "用户不存在"),
	USERNAME_EXISTS(false,40002,"用户名已存在"),
	PASSWORD_ERROR(false,40003,"密码错误"),
	TOKEN_NOT_FOUND(false, 40004,"token 不存在，请登录"),
	FILE_UPLOAD_ERROR(false, 40005, "文件上传失败"),
	CONTENT_EMPTY(false,40006,"评论内容为空"),
	TOKEN_INVALID(false, 40007,"token 过期，请重新登录"),
	TARGET_PARAM_NOT_FOUND(false,40008,"回复的主题不存在"),
	TYPE_PARAM_WRONG(false, 40009, "评论类型错误"),
	COMMENT_NOT_FOUND(false,400011,"回复的评论不存在"),
	BOOK_NOT_FOUND(false,40010,"书籍不存在"),
	RECEIVER_NOT_FOUND(false,40012,"被评论人不存在"),
	USER_STAMPS_FAIL(false,40013,"用户积分不够 无法购买"),
	BOOK_ORDER_NOT_FOUND(false, 40014, "用户未购买此书"),
	FILE_NOT_FOUND(false, 40015, "文件不存在"),
	READ_NOTIFICATION_FAILED(false, 40016, "消息通知阅读失败"),
	NOTIFICATION_NOT_FOUND(false, 40017, "消息通知不存在"),
	POST_NOT_FOUND(false, 40018, "查看的讨论不存在"),
	USER_NO_AUTH(false, 40019, "用户权限不足"),
	ANNOUNCE_NOT_FOUND(false, 40020, "该公告不存在"),
	ROLE_NOT_FOUND(false, 40021, "权限角色不存在");
	//操作是否成功
	boolean success;
	//操作代码
	Integer code;
	//提示信息
	String message;
	private ResultCode(boolean success, Integer code, String message){
		this.success = success;
		this.code = code;
		this.message = message;
	}
	
	public boolean isSuccess() {
		return success;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
