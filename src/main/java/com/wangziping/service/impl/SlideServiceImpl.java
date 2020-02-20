package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangziping.domain.Slide;
import com.wangziping.mapper.SlideMapper;
import com.wangziping.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

	@Autowired
	private SlideMapper slideMapper;
	
	@Override
	public List<Slide> selects() {

		return slideMapper.selects();
	}

}
