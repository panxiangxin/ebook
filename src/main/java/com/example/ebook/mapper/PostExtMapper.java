package com.example.ebook.mapper;

import com.example.ebook.dto.QueryPostDTO;
import com.example.ebook.model.Post;

import java.util.List;

/**
 * @author pxx
 * Date 2020/4/2 10:48
 * @Description
 */
public interface PostExtMapper {
	
	List<Post> selectBySearchAndTag(QueryPostDTO queryPostDTO);
	
	List<Integer> countBySearchAndTag(QueryPostDTO queryPostDTO);
	
	List<Post> selectRelated(Post post);
	
	int updateByPrimaryKeyInc(Post post);
	
	int incCommentCount(Post post);
	
	int decCommentCount(Post post);
}
