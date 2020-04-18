package com.example.ebook.service;

import com.example.ebook.dto.AnnouncementDTO;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.AnnouncementMapper;
import com.example.ebook.model.Announcement;
import com.example.ebook.model.AnnouncementExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pxx
 * Date 2020/4/18 16:25
 * @Description
 */
@Service
public class AnnounceService {
	
	@Autowired
	private AnnouncementMapper announcementMapper;
	
	
	public void saveOrUpdate(AnnouncementDTO announcementDTO) {
		
		if (announcementDTO.getId() == null) {
			
			Announcement announcement = new Announcement();
			announcement.setTitle(announcementDTO.getTitle());
			announcement.setGmtCreate(System.currentTimeMillis());
			announcement.setContent(announcementDTO.getContent());
			announcementMapper.insertSelective(announcement);
		} else {
			Announcement announcement = announcementMapper.selectByPrimaryKey(announcementDTO.getId());
			if (announcement == null) {
				throw new MyException(ResultCode.ANNOUNCE_NOT_FOUND);
			}
			announcement.setTitle(announcementDTO.getTitle());
			announcement.setContent(announcementDTO.getContent());
			
			announcementMapper.updateByPrimaryKeyWithBLOBs(announcement);
		}
	}
	
	public List<Announcement> list() {
		
		return announcementMapper.selectByExampleWithBLOBs(new AnnouncementExample());
	}
	
	public void delete(Long id) {
		
		Announcement announcement = announcementMapper.selectByPrimaryKey(id);
		if (announcement == null) {
			return;
		}
		announcementMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteBatch(Long[] announceList) {
		
		for (int i = 0; i < announceList.length; i++) {
			delete(announceList[i]);
		}
	}
}
