package com.example.ebook.controller;

import com.auth0.jwt.JWT;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.NotificationDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.NotificationService;
import com.example.ebook.service.UserService;
import com.example.ebook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author pxx
 * Date 2019/10/9 20:29
 * @Description
 */
@RestController
@RequestMapping("/notify")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private UserService userService;
	
	@UserLoginToken
	@GetMapping("/get")
	public Object profile(HttpServletRequest request) {
		Long userId = JwtUtil.getUserIdByToken(request);
		User user = userService.findUserById(userId);
		if (user == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		List<NotificationDTO> notificationDTOS = notificationService.list(userId);
		
		return new ResponseResult<>(ResultCode.CLICK_OK, notificationDTOS);
	}
	
	@UserLoginToken
	@GetMapping("/{id}")
	public Object read(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		Long userId = JwtUtil.getUserIdByToken(request);
		User user = userService.findUserById(userId);
		
		if (user == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		notificationService.read(id, user);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
}
