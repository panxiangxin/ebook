package com.example.ebook.schedule;

import com.example.ebook.cache.HotBookCache;
import com.example.ebook.dto.ReturnBookDTO;
import com.example.ebook.mapper.BookMapper;
import com.example.ebook.model.Book;
import com.example.ebook.model.BookExample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
