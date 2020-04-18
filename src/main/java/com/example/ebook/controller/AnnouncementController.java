package com.example.ebook.controller;

import com.example.ebook.exception.ResultCode;
import com.example.ebook.model.Announcement;
import com.example.ebook.response.ResponseResult;
import com.example.ebook.service.AnnounceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pxx
 * Date 2020/4/18 16:25
 * @Description
 */
@RestController
@RequestMapping("/announce")
public class AnnouncementController {
	
	@Autowired
	private AnnounceService announceService;
	
	@GetMapping("/list")
	public Object getAnnounce() {
		List<Announcement> announcementList = announceService.list();
		return new ResponseResult<>(ResultCode.CLICK_OK, announcementList);
	}
}
