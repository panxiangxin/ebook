package com.example.ebook.service;

import com.example.ebook.dto.PostDTO;
import com.example.ebook.dto.QueryPostDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.PostExtMapper;
import com.example.ebook.mapper.PostMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.Post;
import com.example.ebook.model.PostExample;
import com.example.ebook.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/4/2 10:34
 * @Description
 */
@Service
public class PostService {
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private PostExtMapper postExtMapper;
	
	public List<PostDTO> list(Long userId) {
		
		PostExample postExample = new PostExample();
		postExample.createCriteria()
				.andCreatorEqualTo(userId);
		postExample.setOrderByClause("gmt_create desc");
		List<Post> posts = postMapper.selectByExampleWithBLOBs(postExample);
		
		List<PostDTO> postDTOS = posts.stream().map(post -> {
			User user = userMapper.selectByPrimaryKey(post.getCreator());
			PostDTO postDTO = new PostDTO();
			BeanUtils.copyProperties(post, postDTO);
			postDTO.setUser(user);
			return postDTO;
		}).collect(Collectors.toList());
		
		return postDTOS;
	}
	
	public List<PostDTO> list(String search, String tag) {
		if (StringUtils.isNotBlank(search)) {
			String[] tags = StringUtils.split(search, " ");
			search = Arrays
							 .stream(tags)
							 .filter(StringUtils::isNotBlank)
							 .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
							 .filter(StringUtils::isNotBlank)
							 .collect(Collectors.joining("|"));
		}
		
		QueryPostDTO queryPostDTO = new QueryPostDTO();
		queryPostDTO.setSearch(search);
		
		if (StringUtils.isNotBlank(tag)) {
			tag = tag.replace("+", "").replace("*", "").replace("?", "");
			queryPostDTO.setTag(tag);
		}
		
		List<Post> posts = postExtMapper.selectBySearchAndTag(queryPostDTO);
		
		List<PostDTO> postDTOS = posts.stream().map(post -> {
			User user = userMapper.selectByPrimaryKey(post.getCreator());
			PostDTO postDTO = new PostDTO();
			BeanUtils.copyProperties(post, postDTO);
			postDTO.setUser(user);
			return postDTO;
		}).collect(Collectors.toList());
		
		return postDTOS;
	}
	
	public PostDTO getById(Long id) {
		
		Post post = postMapper.selectByPrimaryKey(id);
		if (post == null) {
			throw new MyException(ResultCode.POST_NOT_FOUND);
		}
		PostDTO postDTO = new PostDTO();
		BeanUtils.copyProperties(post, postDTO);
		User user = userMapper.selectByPrimaryKey(post.getCreator());
		postDTO.setUser(user);
		//+1 浏览
		inView(id);
		return postDTO;
		
		
	}
	public void inView(Long id) {
		Post post = new Post();
		post.setId(id);
		post.setViewCount(1);
		postExtMapper.updateByPrimaryKeyInc(post);
	}
}