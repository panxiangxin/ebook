package com.example.ebook.exception;

/**
 * @author pxx
 * Date 2020/2/27 21:59
 * @Description
 */
public class MyException extends RuntimeException {
	private ResultCode resultCode;
	
	public MyException(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
	
	public MyException() {
	}
	
	public ResultCode getExceptionEnum() {
		return resultCode;
	}
	
	public void setExceptionEnum(ResultCode resultCode) {
		this.resultCode = resultCode;
	}
}
