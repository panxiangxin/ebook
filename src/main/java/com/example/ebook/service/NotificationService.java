package com.example.ebook.service;

import com.example.ebook.dto.NotificationDTO;
import com.example.ebook.enums.NotificationEnum;
import com.example.ebook.enums.NotificationStatusEnum;
import com.example.ebook.exception.MyException;
import com.example.ebook.exception.ResultCode;
import com.example.ebook.mapper.NotificationMapper;
import com.example.ebook.model.Notification;
import com.example.ebook.model.NotificationExample;
import com.example.ebook.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/3/30 10:51
 * @Description
 */
@Service
public class NotificationService {

	@Autowired
	private NotificationMapper notificationMapper;
	
	public List<NotificationDTO> list(Long userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria()
				.andReceiverEqualTo(userId);
		notificationExample.setOrderByClause("gmt_create desc");
		
		List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
		
		if (notifications.size() == 0) {
			return new ArrayList<>();
		}
		return notifications.stream().map(notification -> {
			NotificationDTO notificationDTO = new NotificationDTO();
			BeanUtils.copyProperties(notification, notificationDTO);
			notificationDTO.setTypeName(NotificationEnum.getNameOf(notification.getType()));
			return notificationDTO;
		}).collect(Collectors.toList());
	}
	public Long unReadCount(Long userId) {
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria()
				.andReceiverEqualTo(userId)
				.andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		return notificationMapper.countByExample(notificationExample);
	}
	
	public void read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		
		if (notification == null) {
			throw new MyException(ResultCode.NOTIFICATION_NOT_FOUND);
		}
		if (!notification.getReceiver().equals(user.getId())) {
			throw new MyException(ResultCode.READ_NOTIFICATION_FAILED);
		}
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		int i = notificationMapper.updateByPrimaryKey(notification);
	}
}
