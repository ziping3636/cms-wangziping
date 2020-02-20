package com.wangziping.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.mapper.ArticleMapper;

@Service
public class RedisArticleService {

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private ArticleMapper articleMapper;

	public void save(ArticleWithBLOBs articleWithBLOBs) {
		HashOperations opsForHash = redisTemplate.opsForHash();
		opsForHash.put("newArticle", articleWithBLOBs.getTitle(), articleWithBLOBs);
	}

	@SuppressWarnings("unchecked")
	public void saveMySQL(String value) {
		HashOperations opsForHash = redisTemplate.opsForHash();
		Set keys = opsForHash.keys(value);
		for (Object object : keys) {
			String title = object.toString();
			ArticleWithBLOBs articleWithBLOBs = (ArticleWithBLOBs) opsForHash.get(value, title);
			int insert = articleMapper.insert(articleWithBLOBs);
			if (insert > 0) {
				opsForHash.delete(value, articleWithBLOBs.getTitle());
				System.out.println(articleWithBLOBs.getTitle() + "已导入完毕！");
			}
		}
	}

}
