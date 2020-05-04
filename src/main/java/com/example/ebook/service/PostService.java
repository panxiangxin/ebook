package com.example.ebook.service;

import com.example.ebook.dto.PostDTO;
import com.example.ebook.dto.PostUpDTO;
import com.example.ebook.dto.QueryPostDTO;
import com.example.ebook.enums.SortEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.PostExtMapper;
import com.example.ebook.mapper.PostMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.Post;
import com.example.ebook.model.PostExample;
import com.example.ebook.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	
	public List<PostDTO> list(String search, String tag,String sort) {
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
		for (SortEnum sortEnum : SortEnum.values()) {
			if (sortEnum.name().toLowerCase().equals(sort)) {
				queryPostDTO.setSort(sort);
				if (sortEnum == SortEnum.HOT7) {
					queryPostDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
				}
				if (sortEnum == SortEnum.HOT30) {
					queryPostDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
				}
				break;
			}
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
	
	public void createOrUpdate(PostUpDTO postUpDTO, Long userIdByToken) {
		
		if (postUpDTO.getId() == null) {
			//新增post
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
		} else {
			
			Post oldPost = postMapper.selectByPrimaryKey(postUpDTO.getId());
			if (oldPost == null) {
				throw new MyException(ResultCode.POST_NOT_FOUND);
			}
			//修改post
			Post post = new Post();
			post.setId(postUpDTO.getId());
			post.setTitle(postUpDTO.getTitle());
			post.setDescription(postUpDTO.getDescription());
			post.setTag(postUpDTO.getTag());
			postMapper.updateByPrimaryKeySelective(post);
		}
	}
	
	@Transactional
	public void delete(Long id) {
		
		Post post = postMapper.selectByPrimaryKey(id);
		if (post == null) {
			throw new MyException(ResultCode.POST_NOT_FOUND);
		}
		//删除帖子
		postMapper.deleteByPrimaryKey(id);
		//删除相关评论
		
	}
	
	public void deletePostBatchByIds(List<Long> ids) {
		
		ids.forEach(this::delete);
	}
	
	public List<PostDTO> selectRelated(PostDTO postDTO) {
		if (StringUtils.isBlank(postDTO.getTag())) {
			return new ArrayList<>();
		}
		String[] tags = StringUtils.split(postDTO.getTag(), ',');
		String regexpTag = Arrays
								   .stream(tags)
								   .filter(StringUtils::isNotBlank)
								   .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
								   .filter(StringUtils::isNotBlank)
								   .collect(Collectors.joining("|"));
		
		Post post = new Post();
		post.setId(postDTO.getId());
		post.setTag(regexpTag);
		List<Post> questions = postExtMapper.selectRelated(post);
		List<PostDTO> questionDTOS = questions.stream().map(q -> {
			PostDTO questionDTO = new PostDTO();
			BeanUtils.copyProperties(q,questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());
		return questionDTOS;
	}
	
	public Long count() {
		return postMapper.countByExample(new PostExample());
	}
	
	public PageInfo<PostDTO> list(Integer page, Integer size, String sort, String search, String tag) {
		
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
		for (SortEnum sortEnum : SortEnum.values()) {
			if (sortEnum.name().toLowerCase().equals(sort)) {
				queryPostDTO.setSort(sort);
				if (sortEnum == SortEnum.HOT7) {
					queryPostDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 7);
				}
				if (sortEnum == SortEnum.HOT30) {
					queryPostDTO.setTime(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * 30);
				}
				break;
			}
		}
		PageHelper.startPage(page, size);
		List<Post> posts = postExtMapper.selectBySearchAndTag(queryPostDTO);
		PageInfo<Post> info = new PageInfo<>(posts);
		
		List<PostDTO> postDTOS = posts.stream().map(post -> {
			User user = userMapper.selectByPrimaryKey(post.getCreator());
			PostDTO postDTO = new PostDTO();
			BeanUtils.copyProperties(post, postDTO);
			postDTO.setUser(user);
			return postDTO;
		}).collect(Collectors.toList());
		
		PageInfo<PostDTO> infos = new PageInfo<>(postDTOS);
		BeanUtils.copyProperties(info, infos);
		infos.setList(postDTOS);
		
		return infos;
	}
}
