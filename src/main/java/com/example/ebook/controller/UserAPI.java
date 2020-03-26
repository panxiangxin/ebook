package com.example.ebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.example.ebook.dto.LoginUserDTO;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.RegisterUserDTO;
import com.example.ebook.dto.UpdateUserDTO;
import com.example.ebook.enums.UpFileTypeEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.Book;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.FileService;
import com.example.ebook.service.UserService;
import com.example.ebook.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author pxx
 * Date 2020/2/27 17:06
 * @Description
 */
@Slf4j
@Api(tags = "测试demo")
@RestController
@RequestMapping("/user")
public class UserAPI {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	//登录
	@CrossOrigin
	@ApiOperation(value = "登录", notes = "hello测试api")
	@PostMapping("/login")
	public Object login(@RequestBody LoginUserDTO user) {
		JSONObject jsonObject = new JSONObject();
		User userForBase = userService.findByUsername(user);
		if (userForBase == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		} else {
			if (!userForBase.getPassword().equals(user.getPassword())) {
				throw new MyException(ResultCode.PASSWORD_ERROR);
			} else {
				String token = JwtUtil.getToken(userForBase);
				System.out.println("token:" + token);
				jsonObject.put("token", token);
				jsonObject.put("user", userForBase);
			}
			return new ResponseResult<>(ResultCode.CLICK_OK, jsonObject);
		}
	}
	
	@PostMapping("/register")
	public Object register(HttpServletRequest request,
						   @RequestParam(value = "img") MultipartFile file,
						   @RequestParam(value = "user") String user) {
		
		
		String avatarUrl = fileService.upload(file, request, UpFileTypeEnum.USER_AVATAR);
		
		RegisterUserDTO registerUserDTO = JSON.parseObject(user, RegisterUserDTO.class);
		
		if (userService.isExistsUserByUserName(registerUserDTO.getUsername())) {
			return new ResponseResult<>(ResultCode.USERNAME_EXISTS);
		}
		User existsUser = new User();
		existsUser.setUserName(registerUserDTO.getUsername());
		existsUser.setPassword(registerUserDTO.getPassword());
		existsUser.setSex(registerUserDTO.getSex());
		existsUser.setAge(registerUserDTO.getAge());
		existsUser.setStamps((double) 20);
		existsUser.setMail(registerUserDTO.getEmail());
		existsUser.setGmtModified(System.currentTimeMillis());
		existsUser.setGmtCreate(System.currentTimeMillis());
		existsUser.setAvatarUrl(avatarUrl);
		
		userService.create(existsUser);
		
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@UserLoginToken
	@GetMapping("/refresh")
	public Object refreshUser(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		String oldToken = request.getHeader("token");
		String id = JWT.decode(oldToken).getAudience().get(0);
		Long userId = Long.parseLong(id);
		User user = userService.findUserById(userId);
		String newToken = JwtUtil.getToken(user);
		jsonObject.put("token", newToken);
		jsonObject.put("user", user);
		return new ResponseResult<>(ResultCode.CLICK_OK, jsonObject);
	}
	
	@UserLoginToken
	@PostMapping("/update")
	public Object updateUser(HttpServletRequest request,
							 @RequestParam(value = "img",required = false) MultipartFile file,
							 @RequestParam(value = "user") String user) {
		
		JSONObject jsonObject = new JSONObject();
		UpdateUserDTO updateUserDTO = JSON.parseObject(user, UpdateUserDTO.class);
		
		User userById = userService.findUserById(updateUserDTO.getId());
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		
		System.out.println(updateUserDTO);
		User existsUser = new User();
		existsUser.setId(updateUserDTO.getId());
		existsUser.setUserName(updateUserDTO.getUsername());
		existsUser.setPassword(updateUserDTO.getPassword());
		existsUser.setSex(updateUserDTO.getSex());
		existsUser.setAge(updateUserDTO.getAge());
		existsUser.setBio(updateUserDTO.getBio());
		existsUser.setQq(updateUserDTO.getQq());
		existsUser.setMail(updateUserDTO.getMail());
		existsUser.setGmtCreate(System.currentTimeMillis());
		if (file != null) {
			String avatarUrl = fileService.upload(file, request, UpFileTypeEnum.USER_AVATAR);
			existsUser.setAvatarUrl(avatarUrl);
		}
		userService.update(existsUser);
		userById = userService.findUserById(updateUserDTO.getId());
		jsonObject.put("user", userById);
		return new ResponseResult<>(ResultCode.CLICK_OK, jsonObject);
	}
	
	
	@GetMapping("/getUserBook/{id}")
	public Object userBook(@PathVariable("id") Long id) {
		
		List<Book> userBook = userService.getUserBook(id);
		return new ResponseResult<>(ResultCode.CLICK_OK, userBook);
	}
}
