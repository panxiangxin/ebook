package com.example.ebook.response;

import com.example.ebook.exception.ResultCode;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author pxx
 * Date 2020/2/27 21:58
 * @Description
 */
@Data
public class ResponseResult<T> {
	
	private boolean success;//是否操作成功
	private Integer code;//操作状态码，rest风格
	private String message;//操作结果详细信息
	private T data;//实体类数据
	
	public ResponseResult(ResultCode rc) {
		this.success = rc.isSuccess();
		this.code = rc.getCode();
		this.message = rc.getMessage();
	}
	
	public ResponseResult(ResultCode rc, T data) {
		this.success = rc.isSuccess();
		this.code = rc.getCode();
		this.message = rc.getMessage();
		this.data = data;
	}
	
}
