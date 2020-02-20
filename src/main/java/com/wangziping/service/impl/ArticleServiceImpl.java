package com.wangziping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.mapper.ArticleMapper;
import com.wangziping.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Override
	public PageInfo<Article> selects(Article article, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> selects = articleMapper.selects(article);
		return new PageInfo<Article>(selects);
	}

	@Override
	public int insertSelective(ArticleWithBLOBs record) {
		
		return articleMapper.insertSelective(record);
	}

	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ArticleWithBLOBs record) {
		
		return articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<ArticleWithBLOBs> findAll(ArticleWithBLOBs articleWithBLOBs) {
		return articleMapper.findAll(articleWithBLOBs);
	}

}
