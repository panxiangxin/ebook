package com.example.ebook.cache;

import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.dto.ReturnBookDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author pxx
 * Date 2020/4/15 13:15
 * @Description
 */
@Data
@Component
public class HotBookCache {
	
	private List<ReturnBookDTO> hotBooks = new ArrayList<>();
	
	public void updateBooks(List<ReturnBookDTO> priorities) {
		int max = 3;
		
		PriorityQueue<ReturnBookDTO> priorityQueue = new PriorityQueue<>();
		
		priorities.forEach(priority -> {
			if(priorityQueue.size() < max){
				priorityQueue.add(priority);
			}else {
				ReturnBookDTO minBook = priorityQueue.peek();
				if (priority.compareTo(minBook) > 0) {
					priorityQueue.poll();
					priorityQueue.add(priority);
				}
			}
		});
		List<ReturnBookDTO> list = new ArrayList<>();
		while (priorityQueue.size() != 0) {
			ReturnBookDTO returnBookDTO = priorityQueue.poll();
			list.add(0,returnBookDTO);
		}
		hotBooks = list;
	}
}
