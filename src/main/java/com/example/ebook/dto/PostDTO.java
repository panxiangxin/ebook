package com.example.ebook.dto;

import com.example.ebook.model.User;
import lombok.Data;

/**
 * @author pxx
 * Date 2020/4/2 10:38
 * @Description
 */
@Data
public class PostDTO {
	
	private Long id;
	private String title;
	private String description;
	private String tag;
	private Long gmtCreate;
	private Long gmtModified;
	private Long creator;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	private User user;
}
