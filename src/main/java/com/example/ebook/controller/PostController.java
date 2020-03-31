package com.example.ebook.controller;

import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.PostUpDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.PostMapper;
import com.example.ebook.model.Post;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.UserService;
import com.example.ebook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pxx
 * Date 2020/3/31 18:43
 * @Description
 */
@RestController
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private UserService userService;
	
	
	@UserLoginToken
	@PostMapping("/publish")
	public Object publish(HttpServletRequest request, @RequestBody PostUpDTO postUpDTO) {
		Long userIdByToken = JwtUtil.getUserIdByToken(request);
		User userById = userService.findUserById(userIdByToken);
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		//对于post的相关判断
		
		Post post = new Post();
		post.setTitle(postUpDTO.getTitle());
		post.setGmtCreate(System.currentTimeMillis());
		post.setGmtModified(System.currentTimeMillis());
		post.setCreator(userIdByToken);
		post.setCommentCount(0);
		post.setLikeCount(0);
		post.setViewCount(0);
		post.setTag(postUpDTO.getTag());
		post.setDescription(postUpDTO.getDescription());
		postMapper.insertSelective(post);
		
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
}
