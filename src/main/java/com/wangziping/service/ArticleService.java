package com.wangziping.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;

public interface ArticleService {

	PageInfo<Article> selects(Article article, Integer pageNum, Integer pageSize);

	int insertSelective(ArticleWithBLOBs record);

	ArticleWithBLOBs selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(ArticleWithBLOBs record);

	List<ArticleWithBLOBs> findAll(ArticleWithBLOBs articleWithBLOBs);
}
