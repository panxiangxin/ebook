package com.example.ebook.service;

import com.example.ebook.dto.LoginUserDTO;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.User;
import com.example.ebook.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pxx
 * Date 2020/2/27 17:09
 * @Description
 */
@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
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
	
	public void createOrUpdate(User user) {
	
	UserExample example = new UserExample();
		example.createCriteria()
				.andUserNameEqualTo(user.getUserName());
		List<User> users = userMapper.selectByExample(example);
		
		if (users.size() != 0) {
			User dbUser = users.get(0);
			userMapper.updateByPrimaryKeySelective(dbUser);
		}else {
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
}
