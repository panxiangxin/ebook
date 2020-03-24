package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2020/3/15 10:42
 * @Description
 */
@Data
public class CommentCreateDTO {

		private Long parentId;
		private Long commentTopic;
		private Long commentator;
		private Long recevierId;
		private Integer type;
		private String content;
}
