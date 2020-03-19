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
	BOOK_NOT_FOUND(false,40010,"评论的书籍不存在"),
	RECEIVER_NOT_FOUND(false,40012,"被评论人不存在");
	
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
