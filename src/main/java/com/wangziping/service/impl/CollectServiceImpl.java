package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Collect;
import com.wangziping.mapper.CollectMapper;
import com.wangziping.service.CollectService;

@Service
public class CollectServiceImpl implements CollectService {

	@Autowired
	private CollectMapper collectMapper;

	@Override
	public int doAddCollect(Collect collect) {
		return collectMapper.insert(collect);
	}

	@Override
	public PageInfo<Collect> selects(Integer userId, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Collect> selectsByUserId = collectMapper.selectsByUserId(userId);

		return new PageInfo<Collect>(selectsByUserId);
	}

	@Override
	public void deleteCollect(Integer id) {
		collectMapper.deleteByPrimaryKey(id);
	}

}
