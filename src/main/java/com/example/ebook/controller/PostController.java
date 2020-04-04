package com.example.ebook.controller;

import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.cache.BookTagCache;
import com.example.ebook.cache.HotTagCache;
import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.dto.PostDTO;
import com.example.ebook.dto.PostUpDTO;
import com.example.ebook.dto.TagDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.PostMapper;
import com.example.ebook.model.Post;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.PostService;
import com.example.ebook.service.UserService;
import com.example.ebook.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	private PostService postService;
	@Autowired
	private HotTagCache hotTagCache;
	
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
	
	@GetMapping("/postList")
	public Object postList(@RequestParam(value = "search", required = false) String search,
						   @RequestParam(value = "tag", required = false) String tag) {
		
		List<PostDTO> postDTOS = postService.list(search, tag);
		return new ResponseResult<>(ResultCode.CLICK_OK, postDTOS);
	}
	
	@UserLoginToken
	@GetMapping("/getUserPost")
	public Object userPosts(HttpServletRequest request) {
		Long userIdByToken = JwtUtil.getUserIdByToken(request);
		User userById = userService.findUserById(userIdByToken);
		if (userById == null) {
			throw new MyException(ResultCode.USER_NOT_FOUND);
		}
		
		List<PostDTO> postDTOS = postService.list(userIdByToken);
		return new ResponseResult<>(ResultCode.CLICK_OK, postDTOS);
	}
	
	@GetMapping("/{id}")
	public Object post(@PathVariable("id") Long id) {
		
		PostDTO postDTO = postService.getById(id);
		
		return new ResponseResult<>(ResultCode.CLICK_OK, postDTO);
	}
	
	@GetMapping("/tag")
	public Object tagGet() {
		ArrayList<TagDTO> tagDTOS = BookTagCache.get();
		
		return new ResponseResult<>(ResultCode.CLICK_OK, tagDTOS);
	}
	
	@GetMapping("/hotTag")
	public Object hotTagGet() {
		List<HotTagDTO> hotTags = hotTagCache.getHotTags();
		return new ResponseResult<>(ResultCode.CLICK_OK, hotTags);
	}
}
