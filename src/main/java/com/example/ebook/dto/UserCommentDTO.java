package com.example.ebook.dto;

import com.example.ebook.model.Book;
import com.example.ebook.model.Comment;
import com.example.ebook.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/24 15:45
 * @Description
 */
@Data
public class UserCommentDTO {
	//评论id
	private Long id;
	//评论父级
	private Long parentId;
	//评论类型
	private Integer type;
	//被评论数
	private Integer commentCount;
	//创建时间
	private Long gmtCreate;
	//修改时间
	private Long gmtModified;
	//点赞数
	private Integer likeCount;
	//帖子内容
	private String content;
	//接收人
	private User receiver;
	//一级评论
	private Comment comment;
	//评论主题
	private Book commentBook;
}
