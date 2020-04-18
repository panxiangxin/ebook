package com.example.ebook.schedule;

import com.example.ebook.cache.BookTagCache;
import com.example.ebook.cache.HotBookCache;
import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.dto.ReturnBookDTO;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.BookExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author pxx
 * Date 2020/4/15 13:23
 * @Description
 */
@Component
@Slf4j
public class HotBookTasks {
	
	@Autowired
	private HotBookCache hotBookCache;
	@Autowired
	private BookTagCache bookTagCache;
	@Autowired
	private BookMapper bookMapper;
	
	@Scheduled(fixedRate = 1000L * 60 * 30)
	public void getHotBook() {
		log.info("hotBook schedule start {}", new Date());
		List<ReturnBookDTO> priorities = new ArrayList<>();
		List<Book> books = bookMapper.selectByExampleWithBLOBs(new BookExample());
		books.forEach(book -> {
			ReturnBookDTO returnBookDTO = new ReturnBookDTO();
			BeanUtils.copyProperties(book, returnBookDTO);
			priorities.add(returnBookDTO);
		});
		log.info("hotBook schedule end {}", new Date());
		hotBookCache.updateBooks(priorities);
	}
	@Scheduled(fixedRate = 1000L * 60 * 30)
	public void getHotBookTags() {
		log.info("hotBookTags schedule start {}", new Date());
		Map<String,HotTagDTO> priorities = new HashMap<>();
		List<Book> books = bookMapper.selectByExampleWithBLOBs(new BookExample());
		books.forEach(book -> {
			String[] tags = StringUtils.split(book.getTags(), ",");
			for (String tag : tags) {
				HotTagDTO priority = priorities.get(tag);
				if (priority != null) {
					priority.setPostCount(priority.getPostCount() + 1);
				}else {
					priority = new HotTagDTO();
					priority.setName(tag);
					priority.setPostCount(1);
				}
				priorities.put(tag,priority);
			}
		});
		log.info("hotBook schedule end {}", new Date());
		bookTagCache.updateTags(priorities);
	}
}
