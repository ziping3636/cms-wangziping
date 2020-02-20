package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangziping.domain.Category;
import com.wangziping.domain.Channel;
import com.wangziping.mapper.CategoryMapper;
import com.wangziping.mapper.ChannelMapper;
import com.wangziping.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelMapper channelMapper;
	
	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public List<Channel> selects() {
		
		return channelMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channelId) {
		
		return categoryMapper.selectsByChannelId(channelId);
	}
}
