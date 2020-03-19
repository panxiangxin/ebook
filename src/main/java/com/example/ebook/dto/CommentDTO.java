package com.example.ebook.dto;

import com.example.ebook.model.User;
import lombok.Data;

import java.util.List;

/**
 * @author pxx
 * Date 2020/3/15 11:28
 * @Description
 */
@Data
public class CommentDTO {
	
	//帖子相关信息
	private Long id;
	private Long parentId;
	private Integer type;
	private Long commentator;
	private Integer commentCount;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer likeCount;
	private String content;
	private User commentUser;
	private User recevier;
	//回复信息
	private List<CommentDTO> reply;
}
