package com.wangziping.mapper;

import java.util.List;

import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;

public interface ArticleMapper {
	
	List<Article> selects(Article article);
	
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
    
    int updateComplainNum(Integer articleId);

	List<ArticleWithBLOBs> findAll(ArticleWithBLOBs articleWithBLOBs);
}