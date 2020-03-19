package com.example.ebook.advice;

import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.response.ResponseResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pxx
 * Date 2020/2/27 22:02
 * @Description
 */
@ControllerAdvice
public class CommonExceptionHandler {
	@ExceptionHandler(MyException.class)
	@ResponseBody
	public ResponseResult<Void> exceptionHandle(MyException e){
		ResultCode rc = e.getExceptionEnum();
		return new ResponseResult<>(rc);
	}
}
