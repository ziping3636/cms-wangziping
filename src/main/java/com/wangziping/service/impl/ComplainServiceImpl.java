package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Complain;
import com.wangziping.mapper.ArticleMapper;
import com.wangziping.mapper.ComplainMapper;
import com.wangziping.service.ComplainService;
import com.wangziping.vo.ComplainVO;

@Service
public class ComplainServiceImpl implements ComplainService {

	@Autowired
	private ComplainMapper complainMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public Boolean insert(Complain complain) {
		try {
			complainMapper.insert(complain);
			articleMapper.updateComplainNum(complain.getArticleId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("举报失败");
		}
	}

	@Override
	public PageInfo<Complain> selects(ComplainVO complainVO, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Complain> selects = complainMapper.selects(complainVO);
		return new PageInfo<Complain>(selects);
	}
}
