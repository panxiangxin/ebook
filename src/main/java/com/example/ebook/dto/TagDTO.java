package com.example.ebook.dto;

import lombok.Data;

import java.util.List;

/**
 * @author pxx
 * Date 2019/10/9 11:07
 * @Description
 */
@Data
public class TagDTO {
	private String categoryName;
	private List<String> tags;
}
