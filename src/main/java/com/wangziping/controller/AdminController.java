package com.wangziping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.domain.Complain;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.service.ArticleService;
import com.wangziping.service.ComplainService;
import com.wangziping.vo.ComplainVO;

@RequestMapping("admin")
@Controller
public class AdminController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ComplainService complainService;

	@Autowired
	private ArticleRepository articleRepository;

	@SuppressWarnings("rawtypes")
	@Autowired
	private KafkaTemplate kafkaTemplate;

	@RequestMapping(value = { "/", "index", "" })
	public String index() {
		return "admin/index";
	}

	/**
	 * @Title: select
	 * @Description: TODO查看文章详情
	 * @param id
	 * @return
	 * @return: String
	 */
	@GetMapping("article/select")
	public String select(Model m, Integer id) {
		ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(id);
		m.addAttribute("article", selectByPrimaryKey);
		return "admin/article/article";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@PostMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(article.getId());
		if (article.getStatus() == 1) {
			if (selectByPrimaryKey.getDeleted() == 0) {

				kafkaTemplate.send("a2", "add" + JSON.toJSONString(selectByPrimaryKey));
			}
		} else if (selectByPrimaryKey.getStatus() == -1) {
			kafkaTemplate.send("a2", "del" + JSON.toJSONString(selectByPrimaryKey));
		}
		return articleService.updateByPrimaryKeySelective(article) > 0;
	}

	@GetMapping("article/complains")
	public String selects(Model m, ComplainVO complainVO, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		PageInfo<Complain> selects = complainService.selects(complainVO, pageNum, pageSize);
		m.addAttribute("info", selects);
		return "admin/article/complains";
	}
}
