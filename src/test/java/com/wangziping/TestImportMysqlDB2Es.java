package com.wangziping;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class TestImportMysqlDB2Es {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;
	
	@Test
	public void testImportMysql2ES() {
		ArticleWithBLOBs articleWithBLOBs = new ArticleWithBLOBs();
		articleWithBLOBs.setStatus(1);
		List<ArticleWithBLOBs> findAll = articleService.findAll(articleWithBLOBs);
		articleRepository.saveAll(findAll);
	}
}
