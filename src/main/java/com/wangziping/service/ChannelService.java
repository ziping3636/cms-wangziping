package com.wangziping.service;

import java.util.List;

import com.wangziping.domain.Category;
import com.wangziping.domain.Channel;

public interface ChannelService {

	List<Channel> selects();

	List<Category> selectsByChannelId(Integer channelId);
} 
