package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangziping.domain.Category;
import com.wangziping.mapper.CategoryMapper;
import com.wangziping.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	@Override
	public List<Category> selectsByChannelId(Integer channelId) {

		return categoryMapper.selectsByChannelId(channelId);
	}

}
