package com.example.ebook.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.cache.BookTagCache;
import com.example.ebook.cache.HotTagCache;
import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.dto.PostDTO;
import com.example.ebook.dto.PostUpDTO;
import com.example.ebook.dto.TagDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.User;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.PostService;
import com.example.ebook.service.UserService;
import com.example.ebook.util.JwtUtil;
import com.github.pagehelper.PageInfo;
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
		System.out.println(postUpDTO);
		
		postService.createOrUpdate(postUpDTO, userIdByToken);
		
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@GetMapping("/postCount")
	public Object count() {
		Long count = postService.count();
		return new ResponseResult<>(ResultCode.CLICK_OK, count);
	}
	
	@PostMapping("/postPage")
	public Object postPage(@RequestParam(value = "search", required = false) String search,
						   @RequestParam(value = "tag", required = false) String tag,
						   @RequestParam(value = "page", defaultValue = "1") Integer page,
						   @RequestParam(value = "size", defaultValue = "5") Integer size,
						   @RequestParam(value = "sort", defaultValue = "new") String sort) {
		
		PageInfo<PostDTO> list = postService.list(page, size, sort, search, tag);
		return new ResponseResult<>(ResultCode.CLICK_OK, list);
	}
	
	@GetMapping("/postList")
	public Object postList(@RequestParam(value = "search", required = false) String search,
						   @RequestParam(value = "tag", required = false) String tag,
						   @RequestParam(value = "sort", required = false) String sort) {
		
		List<PostDTO> postDTOS = postService.list(search, tag, sort);
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
		List<PostDTO> postDTOS = postService.selectRelated(postDTO);
		JSONObject object = new JSONObject();
		object.put("post", postDTO);
		object.put("related", postDTOS);
		return new ResponseResult<>(ResultCode.CLICK_OK, object);
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
	
	@UserLoginToken
	@DeleteMapping("/{id}")
	public Object deletePost(@PathVariable("id") Long id) {
		postService.delete(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@GetMapping("/hotPost")
	public Object hotPost() {
		List<PostDTO> hotPosts = postService.list(null, null, "hot");
		List<PostDTO> postDTOS;
		if (hotPosts.size() >= 5) {
			postDTOS = hotPosts.subList(0, 5);
		} else {
			postDTOS = hotPosts;
		}
		return new ResponseResult<>(ResultCode.CLICK_OK, postDTOS);
	}
}
