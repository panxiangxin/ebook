package com.example.ebook.mapper;

import com.example.ebook.model.Comment;

/**
 * @author pxx
 * Date 2020/3/30 9:58
 * @Description
 */
public interface CommentExtMapper {
	/**
	 * 评论加一操作
	 *
	 * @param comment
	 * @return
	 */
	int incCommentCount(Comment comment);
	
	/**
	 * 点赞加一操作
	 * @param comment
	 * @return
	 */
	int incLikeCount(Comment comment);
}
