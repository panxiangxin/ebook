package com.example.ebook.service;

import com.example.ebook.dto.LoginUserDTO;
import com.example.ebook.dto.WeChatKey;
import com.example.ebook.dto.WeChatUserInfo;
import com.example.ebook.enums.Gender;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.mapper.BookOrderMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/2/27 17:09
 * @Description
 */
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private BookMapper bookMapper;
	@Autowired
	private BookOrderMapper bookOrderMapper;
	
	public User findUserById(Long id) {
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andIdEqualTo(id);
		List<User> users = userMapper.selectByExample(userExample);
		if (users.size() != 0) {
			return users.get(0);
		}
		return null;
	}
	
	public User findByUsername(LoginUserDTO user) {
		UserExample userExample = new UserExample();
		userExample.createCriteria()
				.andUserNameEqualTo(user.getUserName());
		List<User> users = userMapper.selectByExample(userExample);
		if (users.size() != 0) {
			return users.get(0);
		}
		return null;
	}
	
	public void create(User user) {
	
	UserExample example = new UserExample();
		example.createCriteria()
				.andUserNameEqualTo(user.getUserName());
		List<User> users = userMapper.selectByExample(example);
		
		if (users.size() == 0) {
			userMapper.insertSelective(user);
		}
	}
	
	public boolean isExistsUserByUserName(String userName) {
		UserExample example = new UserExample();
		example.createCriteria()
				.andUserNameEqualTo(userName);
		List<User> users = userMapper.selectByExample(example);
		
		return users.size() != 0;
	}
	
	public void update(User existsUser) {
		userMapper.updateByPrimaryKeySelective(existsUser);
	}
	
	public List<Book> getUserBook(Long id) {
		
		List<Book> books = new ArrayList<>();
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		
		BookOrderExample bookOrderExample = new BookOrderExample();
		bookOrderExample.createCriteria()
				.andUserIdEqualTo(user.getId());
		List<BookOrder> bookOrders = bookOrderMapper.selectByExample(bookOrderExample);
		
		List<Long> bookIds = bookOrders.stream().map(BookOrder::getBookId).collect(Collectors.toList());
		
		if (bookIds.size() == 0) {
			return books;
		}
		BookExample bookExample = new BookExample();
		bookExample.createCriteria()
				.andIdIn(bookIds);
		books = bookMapper.selectByExample(bookExample);
		return books;
	}
	
	public List<User> list(String search) {
		UserExample userExample = new UserExample();
		
		UserExample.Criteria userExampleCriteria = userExample.createCriteria();
		if (StringUtils.isNotBlank(search)) {
			search = "%" + search + "%";
			userExampleCriteria.andUserNameLike(search);
		} else {
			userExampleCriteria.andIdIsNotNull();
		}
		return userMapper.selectByExample(userExample);
	}
	
	public void deleteUserById(Long id) {
		
		userMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteUserBatchByIds(List<Long> ids) {
		
		if (ids.size() != 0) {
			ids.forEach(id -> {
				userMapper.deleteByPrimaryKey(id);
			});
		}
	}
	
	public void resetPassword(Long id) {
		
		User user = userMapper.selectByPrimaryKey(id);
		user.setPassword("1234");
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	public User loginOrRegister(WeChatUserInfo userInfo, WeChatKey wxOpenId) {
		
		UserExample example = new UserExample();
		example.createCriteria()
				.andAccountIdEqualTo(wxOpenId.getOpenid());
		List<User> users = userMapper.selectByExample(example);
		if (users.size() == 0) {
			User user = new User();
			user.setAccountId(wxOpenId.getOpenid());
			user.setUserName(userInfo.getNickName());
			if (userInfo.getGender().equals(Gender.MAN.getValue())) {
				user.setSex(Gender.MAN.getName());
			} else {
				user.setSex(Gender.WOMAN.getName());
			}
			user.setStatus(0);
			user.setStamps((double) 20);
			user.setGmtModified(System.currentTimeMillis());
			user.setGmtCreate(System.currentTimeMillis());
			user.setAvatarUrl(userInfo.getAvatarUrl());
			userMapper.insertSelective(user);
		} else {
			User user = users.get(0);
			user.setAvatarUrl(userInfo.getAvatarUrl());
			user.setUserName(userInfo.getNickName());
			if (userInfo.getGender().equals(Gender.MAN.getValue())) {
				user.setSex(Gender.MAN.getName());
			} else {
				user.setSex(Gender.WOMAN.getName());
			}
			UserExample userExample = new UserExample();
			userExample.createCriteria()
					.andAccountIdEqualTo(wxOpenId.getOpenid());
			userMapper.updateByExampleSelective(user, userExample);
		}
		example.clear();
		example.createCriteria().andAccountIdEqualTo(wxOpenId.getOpenid());
		List<User> usersList = userMapper.selectByExample(example);
		return usersList.get(0);
	}
}
