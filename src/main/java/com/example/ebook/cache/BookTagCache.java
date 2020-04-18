package com.example.ebook.cache;

import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.dto.ReturnBookDTO;
import com.example.ebook.dto.TagDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/10 14:44
 * @Description
 */
@Data
@Component
public class BookTagCache {
	
	private List<HotTagDTO> hotBookTags = new ArrayList<>();
	
	public void updateTags(Map<String, HotTagDTO> priorities) {
		
		int max = 5;
		PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
		priorities.forEach((name, priority) -> {
			if(priorityQueue.size() < max){
				priorityQueue.add(priority);
			}else {
				HotTagDTO minBookTag = priorityQueue.peek();
				if (priority.compareTo(minBookTag) > 0) {
					priorityQueue.poll();
					priorityQueue.add(priority);
				}
			}
		});
		List<HotTagDTO> list = new ArrayList<>();
		while (priorityQueue.size() != 0) {
			HotTagDTO hotTagDTO = priorityQueue.poll();
			list.add(0,hotTagDTO);
		}
		hotBookTags = list;
	}
	
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
	
	public static ArrayList<String> getBookCategory() {
		ArrayList<String> tags = new ArrayList<>();
		tags.add("都市");
		tags.add("玄幻");
		tags.add("科技");
		tags.add("社会");
		tags.add("生活");
		tags.add("经管");
		tags.add("文化");
		tags.add("流行");
		return tags;
	}
	
	public static String filterInvalid(String tags) {
		String[] split = StringUtils.split(tags, ",");
		ArrayList<TagDTO> tagDTOS = get();
		List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
		return Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
	}
}
