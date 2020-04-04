package com.example.ebook.cache;

import com.example.ebook.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/10 14:44
 * @Description
 */
public class BookTagCache {
	
	public static ArrayList<TagDTO> get() {
		
		ArrayList<TagDTO> tagDTOS = new ArrayList<>();
		TagDTO duShi = new TagDTO();
		duShi.setCategoryName("都市");
		duShi.setTags(Arrays.asList("爱情","总裁","搞笑","感动","女频","男频","日常","高冷","轻松","二次元","幽默","青春","校园","成长"));
		tagDTOS.add(duShi);
		
		TagDTO xuanHuan = new TagDTO();
		xuanHuan.setCategoryName("玄幻");
		xuanHuan.setTags(Arrays.asList("热血","升级","扮猪吃虎","打怪","爽文"));
		tagDTOS.add(xuanHuan);
		
		TagDTO xiuZhen = new TagDTO();
		xiuZhen.setCategoryName("修真");
		xiuZhen.setTags(Arrays.asList("修真","东方","西方","仙侠","宏大"));
		tagDTOS.add(xiuZhen);
		
		TagDTO keJi = new TagDTO();
		keJi.setCategoryName("科技");
		keJi.setTags(Arrays.asList("西方科技","未来科技","末日科技","幻想","想象","现代科技"));
		tagDTOS.add(keJi);
		
		TagDTO other = new TagDTO();
		other.setCategoryName("其他");
		other.setTags(Arrays.asList("图书讨论","推书","书荒"));
		tagDTOS.add(other);
		return tagDTOS;
	}
	
	public static String filterInvalid(String tags) {
		String[] split = StringUtils.split(tags, ",");
		ArrayList<TagDTO> tagDTOS = get();
		List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
		return Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
	}
}
