package com.wangziping.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.service.ArticleService;
import com.wangziping.service.impl.RedisArticleService;

public class MessageAccept implements MessageListener<String, String> {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private RedisArticleService redisArticleService;

	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		String value = data.value();
		if (value.startsWith("add")) {
			String substring = value.substring(3);
			ArticleWithBLOBs parseObject = JSON.parseObject(substring, ArticleWithBLOBs.class);
			articleRepository.save(parseObject);
//			articleService.insertSelective(parseObject);
		} else if (value.startsWith("del")) {
			String substring = value.substring(3);
			ArticleWithBLOBs parseObject = JSON.parseObject(substring, ArticleWithBLOBs.class);
			articleRepository.delete(parseObject);
		} else if (value.startsWith("upd")) {
			String substring = value.substring(3);
			ArticleWithBLOBs parseObject = JSON.parseObject(substring, ArticleWithBLOBs.class);
			articleService.updateByPrimaryKeySelective(parseObject);
		} else if (value.startsWith("redisAdd")) {
			String substring = value.substring(8);
			ArticleWithBLOBs parseObject = JSON.parseObject(substring, ArticleWithBLOBs.class);
			redisArticleService.save(parseObject);
		} else if (value.equals("newArticle")) {
			redisArticleService.saveMySQL(value);
		}
	}

}
