package com.example.ebook.cache;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/10 14:44
 * @Description
 */
public class BookTagCache {
	public static List<String> get() {
		List<String> tags = new ArrayList<String>();
		Collections.addAll(tags, "文学", "流行","文化","生活","经管");
		return tags;
	}
	
	public static String filterInvalid(String tags) {
		String[] split = StringUtils.split(tags, ",");
		List<String> tag = get();
		return Arrays.stream(split).filter(t -> !tag.contains(t)).collect(Collectors.joining(","));
	}
}
