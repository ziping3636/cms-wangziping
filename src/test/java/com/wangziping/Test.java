package com.wangziping;

import java.io.File;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.utils.StreamUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class Test {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private ArticleRepository articleRepository;

	@org.junit.Test
	public void testRead() throws Exception {
		File file = new File("F:\\Article");
		File[] listFiles = file.listFiles();
		for (File file2 : listFiles) {
			int i = 0;
			if (i >= 50) {
				break;
			}
			String s = file2.toString();
			String title = s.substring(12, s.lastIndexOf(".txt"));
			ArticleWithBLOBs articleWithBLOBs = new ArticleWithBLOBs();
			articleWithBLOBs.setTitle(title);
			articleWithBLOBs.setChannelId(9);
			articleWithBLOBs.setCategoryId(30);
			String content = StreamUtil.readFile(file2, "utf-8");
			articleWithBLOBs.setSummary(content.substring(0, 139));
			articleWithBLOBs.setContent(content);
			articleWithBLOBs.setHot(i % 2 == 0 ? 1 : 0);
			articleWithBLOBs.setHits(i % 2 == 0 ? 1 : 2);
			articleWithBLOBs.setDeleted(0);
			articleWithBLOBs.setUserId(175);
			articleWithBLOBs.setStatus(0);
			articleWithBLOBs.setContentType(0);
			articleWithBLOBs.setComplainNum(0);
			kafkaTemplate.send("a1", "add" + JSON.toJSONString(articleWithBLOBs));
		}
	}
}
