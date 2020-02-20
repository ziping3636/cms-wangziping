package com.wangziping.service;

import java.util.List;

import com.wangziping.domain.Category;

public interface CategoryService {

	List<Category> selectsByChannelId(Integer channelId);
}
