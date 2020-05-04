package com.example.ebook.controller;

import com.example.ebook.annotation.UserLoginToken;
import com.example.ebook.dto.CommentCreateDTO;
import com.example.ebook.dto.CommentDTO;
import com.example.ebook.dto.UserCommentDTO;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.Comment;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pxx
 * Date 2020/3/14 12:31
 * @Description
 */
@RestController
@RequestMapping("/comment")
public class CommentAPI {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post")
	@UserLoginToken
	public Object post(@RequestBody CommentCreateDTO commentCreateDTO) {
		
		if (commentCreateDTO.getContent() == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
			return new ResponseResult<>(ResultCode.CONTENT_EMPTY);
		}
		System.out.println(commentCreateDTO);
		Comment comment = new Comment();
		comment.setParentId(commentCreateDTO.getParentId());
		comment.setType(commentCreateDTO.getType());
		comment.setCommentTopic(commentCreateDTO.getCommentTopic());
		comment.setCommentator(commentCreateDTO.getCommentator());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setLikeCount(0);
		comment.setCommentCount(0);
		comment.setRecevierId(commentCreateDTO.getRecevierId());
		comment.setContent(commentCreateDTO.getContent());
		
		commentService.insert(comment);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
	
	@GetMapping("/comment/{type}-{id}")
	public Object comment(@PathVariable("type") Integer type, @PathVariable("id") Long id) {
		List<CommentDTO> list = commentService.list(type, id);
		return new ResponseResult<>(ResultCode.CLICK_OK, list);
	}
	@PostMapping("/commentPage")
	public Object commentPage(@RequestParam("type") Integer type,
							  @RequestParam("id") Long id,
							  @RequestParam(value = "page",defaultValue = "1") Integer page,
							  @RequestParam(value = "size",defaultValue = "5") Integer size) {
		PageInfo<CommentDTO> list = commentService.list(type, id, page, size);
		return new ResponseResult<>(ResultCode.CLICK_OK, list);
	}
	
	@UserLoginToken
	@GetMapping("/user/{id}")
	public Object getUserComment(@PathVariable("id") Long id) {
		List<UserCommentDTO> userCommentById = commentService.getUserCommentById(id, false);
		return new ResponseResult<>(ResultCode.CLICK_OK, userCommentById);
	}
	
	@UserLoginToken
	@DeleteMapping("/{id}")
	public Object deleteComment(@PathVariable("id") Long id) {
		
		commentService.delete(id);
		return new ResponseResult<>(ResultCode.CLICK_OK);
	}
}
