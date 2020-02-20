package com.wangziping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.service.ArticleService;

@RequestMapping("admin")
@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@GetMapping("article/selects")
	public String selects(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		
		if(article.getStatus() == null) {
			article.setStatus(0);
		}

		PageInfo<Article> selects = articleService.selects(article, pageNum, pageSize);
		m.addAttribute("info", selects);
		return "admin/article/articles";
	}
}
