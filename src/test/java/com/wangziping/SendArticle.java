package com.wangziping;

import java.io.File;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.utils.DateUtil;
import com.wangziping.utils.StreamUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class SendArticle {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		File file = new File("F:\\Article");
		File[] listFiles = file.listFiles();
		int i = 1;
		for (File file2 : listFiles) {
			if (i >= 50) {
				break;
			}
			i++;
			String content = StreamUtil.readFile(file2, "utf-8");
			ArticleWithBLOBs articleWithBLOBs = new ArticleWithBLOBs();
			String title = file2.toString().substring(12, file2.toString().lastIndexOf(".txt"));
			articleWithBLOBs.setTitle(title);
			articleWithBLOBs.setContent(content);
			articleWithBLOBs.setSummary(content.substring(0, 139));
			articleWithBLOBs.setHits(i % 2 == 1 ? 1 : 2);
			articleWithBLOBs.setHot(i % 2 == 1 ? 0 : 1);
			articleWithBLOBs.setChannelId(9);
			articleWithBLOBs.setUserId(174);
			articleWithBLOBs.setDeleted(0);
			articleWithBLOBs.setStatus(0);
			articleWithBLOBs.setContentType(0);
			Date date = new Date();
			date.setDate(1);
			date.setYear(0);
			date.setYear(2019);
			Date date2 = new Date();
			Date randomDate = DateUtil.randomDate(date, date2);
			articleWithBLOBs.setCreated(randomDate);
			kafkaTemplate.send("a2", "redisAdd" + JSON.toJSONString(articleWithBLOBs));
		}

	}
	
	@Test
	public void saveMySQL() {
		kafkaTemplate.send("a2", "newArticle");
	}
}
