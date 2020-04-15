package com.example.ebook.service;

import com.example.ebook.dto.ReturnStampsOrderDTO;
import com.example.ebook.mapper.StampOrderMapper;
import com.example.ebook.mapper.UserMapper;
import com.example.ebook.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author pxx
 * Date 2020/4/12 14:30
 * @Description
 */
@Service
public class StampOrderService {
	
	@Autowired
	private StampOrderMapper stampOrderMapper;
	@Autowired
	private UserMapper userMapper;
	
	public List<ReturnStampsOrderDTO> list() {
		
		StampOrderExample stampOrderExample = new StampOrderExample();
		stampOrderExample.createCriteria()
				.andIdIsNotNull();
		stampOrderExample.setOrderByClause("gmt_create desc");
		List<StampOrder> stampOrders = stampOrderMapper.selectByExample(stampOrderExample);
		
		if (stampOrders.size() == 0) {
			return new ArrayList<>();
		}
		
		List<Long> userIds = stampOrders.stream().map(StampOrderKey::getUserId).collect(Collectors.toList());
		UserExample example = new UserExample();
		example.createCriteria()
				.andIdIn(userIds);
		List<User> users = userMapper.selectByExample(example);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
		
		List<ReturnStampsOrderDTO> returnStampsOrderDTOS = stampOrders.stream().map(stampOrder -> {
			ReturnStampsOrderDTO returnStampsOrderDTO = new ReturnStampsOrderDTO();
			BeanUtils.copyProperties(stampOrder, returnStampsOrderDTO);
			returnStampsOrderDTO.setUser(userMap.get(stampOrder.getUserId()));
			return returnStampsOrderDTO;
		}).collect(Collectors.toList());
		return returnStampsOrderDTOS;
	}
	
	public void delete(String id) {
		StampOrderKey stampOrderKey = new StampOrderKey();
		stampOrderKey.setId(id);
		stampOrderMapper.deleteByPrimaryKey(stampOrderKey);
	}
	
	public void deleteBatch(String[] stampsOrders) {
		
		for (String stampsOrder : stampsOrders) {
			System.out.println(stampsOrder);
		}
	}
}
