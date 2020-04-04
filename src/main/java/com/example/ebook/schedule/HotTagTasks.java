package com.example.ebook.schedule;

import com.example.ebook.cache.HotTagCache;
import com.example.ebook.dto.HotTagDTO;
import com.example.ebook.mapper.PostMapper;
import com.example.ebook.model.Post;
import com.example.ebook.model.PostExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author pxx
 * Date 2019/10/11 15:27
 * @Description
 */
@Component
@Slf4j
public class HotTagTasks {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private HotTagCache hotTagCache;
	
	@Scheduled(fixedRate = 1000L * 60 *30)
	//@Scheduled(cron = " 0 0 1 * * *")
	public void hotTagSchedule() {
		int offset = 0;
		int limit = 5;
		log.info("hot schedule start {}", new Date());
		List<Post> list = new ArrayList<>();
		Map<String, HotTagDTO> priorities = new HashMap<>();
		while (offset == 0 || list.size() == limit) {
			list = postMapper.selectByExampleWithBLOBsWithRowbounds(new PostExample(),new RowBounds(offset,limit));
			list.forEach(post -> {
				String[] tags = StringUtils.split(post.getTag(), ",");
				for (String tag : tags) {
					HotTagDTO priority = priorities.get(tag);
					if (priority != null) {
						priority.setPostCount(priority.getPostCount() + 1);
						priority.setCommentCount(priority.getCommentCount()+ post.getCommentCount());
					}else {
						priority = new HotTagDTO();
						priority.setName(tag);
						priority.setPostCount(1);
						priority.setCommentCount(post.getCommentCount());
					}
					priorities.put(tag,priority);
				}
			});
			offset += limit;
		}
		log.info("hot schedule end {}", new Date());
		hotTagCache.updateTags(priorities);
	}
}
