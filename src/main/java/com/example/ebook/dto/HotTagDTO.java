package com.example.ebook.dto;

import lombok.Data;

/**
 * @author pxx
 * Date 2019/10/11 16:24
 * @Description
 */
@Data
public class HotTagDTO implements Comparable<HotTagDTO> {
		private String name;
		private Integer commentCount;
		private Integer postCount;
		
	@Override
	public int compareTo(HotTagDTO o) {
		if (o.commentCount != null && commentCount != null) {
			return (postCount *5 +commentCount) - (o.postCount *5 + o.commentCount);
		}
		else if (o.commentCount == null && commentCount == null) {
			return postCount - o.postCount;
		}
		else {
			return commentCount - o.commentCount;
		}
	}
}
