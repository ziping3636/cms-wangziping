package com.wangziping.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.domain.Category;
import com.wangziping.domain.Channel;
import com.wangziping.domain.Collect;
import com.wangziping.domain.User;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.service.ArticleService;
import com.wangziping.service.CategoryService;
import com.wangziping.service.ChannelService;
import com.wangziping.service.CollectService;
import com.wangziping.util.CMSException;
import com.wangziping.utils.StringUtil;

@RequestMapping("my")
@Controller
public class MyController {

	@Autowired
	private ChannelService channelService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ArticleService articleService;

	@SuppressWarnings("rawtypes")
	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CollectService collectService;

	@RequestMapping(value = { "", "/", "index" })
	public String index() {
		return "my/index";
	}

	@GetMapping("article/publish")
	public String publish() {

		return "my/article/publish";
	}

	/**
	 * @Title: deleteCollect
	 * @Description: TODO 删除收藏的地址
	 * @param id
	 * @return
	 * @return: String
	 */
	@RequestMapping("deleteCollect")
	public String deleteCollect(Integer id) {
		collectService.deleteCollect(id);
		return "my/index";
	}

	/**
	 * @Title: collect
	 * @Description: TODO查询该登录用户的收藏
	 * @param session
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@RequestMapping("article/collects")
	public String collect(HttpSession session, Model model, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {
		User user = (User) session.getAttribute("user");
		PageInfo<Collect> selects = collectService.selects(user.getId(), pageNum, pageSize);
		model.addAttribute("info", selects);
		return "my/article/collects";
	}

	/**
	 * @Title: toAddCollect
	 * @Description: TODO跳转收藏页面
	 * @param model
	 * @param session
	 * @param id
	 * @return
	 * @return: String
	 */
	@RequestMapping("toAddCollect")
	public String toAddCollect(Model model, HttpSession session, Integer id) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "index/addCollect";
	}

	/**
	 * @Title: doAddCollect
	 * @Description: TODO执行添加收藏文章
	 * @param collect
	 * @param m
	 * @return
	 * @return: String
	 */
	@RequestMapping("doAddCollect")
	public String doAddCollect(Collect collect, Model m) {
		if (StringUtil.isHttpUrl(collect.getUrl())) { // 判断url是否合法
			int doAddCollect = collectService.doAddCollect(collect);
			if (doAddCollect > 0) {

				return "redirect:";
			} else {
				m.addAttribute("error", "添加失败");
				return "index/addCollect";
			}

		} else {
			m.addAttribute("collect", collect);
			m.addAttribute("error", "请输入合法的链接！！");
			return "index/addCollect";
		}
	}

	@GetMapping("article/updateArticleInfo")
	public String updateArticleInfo(Model model, Integer id) {
		if (id != null && !"".equals(id)) {
			ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(id);
			model.addAttribute("article", selectByPrimaryKey);
		}
		return "my/article/update";
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("article/update")
	public boolean update(ArticleWithBLOBs article) {
		if (article.getId() != null && article.getDeleted() != null) {

			if (article.getDeleted() == 1) {
				// System.out.println("删除" + article);
				kafkaTemplate.send("1708D", "del" + article);
				ArticleWithBLOBs selectByPrimaryKey2 = articleService.selectByPrimaryKey(article.getId());
				articleRepository.delete(selectByPrimaryKey2);
			} else if (article.getDeleted() == 0) {
				ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(article.getId());
				// System.out.println("恢复" + article);
				kafkaTemplate.send("1708D", "add" + selectByPrimaryKey);
				ArticleWithBLOBs selectByPrimaryKey2 = articleService.selectByPrimaryKey(article.getId());
				if (selectByPrimaryKey2 != null) {
					if (selectByPrimaryKey2.getStatus() == 1) {

						articleRepository.save(selectByPrimaryKey2);
					}
				}
			}
		}
		return articleService.updateByPrimaryKeySelective(article) > 0;
	}

	@ResponseBody
	@GetMapping("channel/selects")
	public List<Channel> selectChannles() {

		return channelService.selects();
	}

	@ResponseBody
	@GetMapping("category/selectsByChannelId")
	public List<Category> selectCategory(Integer channelId) {

		return categoryService.selectsByChannelId(channelId);
	}

	@GetMapping("article/articles")
	public String articles(Model m, HttpServletRequest request, Article article,
			@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "3") int pageSize) {
		User u = (User) request.getSession().getAttribute("user");
		article.setUserId(u.getId());
		PageInfo<Article> selects = articleService.selects(article, pageNum, pageSize);
		m.addAttribute("info", selects);
		return "my/article/articles";
	}

	@ResponseBody
	@PostMapping("article/publish")
	public boolean publish(MultipartFile file, ArticleWithBLOBs article, HttpServletRequest request)
			throws IllegalStateException, IOException {
		String path = "d:/pic/";
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString().replace("-", "")
					+ fileName.substring(fileName.lastIndexOf("."));
			file.transferTo(new File(path, newFileName));
			article.setPicture(newFileName);
		}
		article.setCreated(new Date());
		article.setStatus(0);
		article.setHits(0);
		article.setDeleted(0);
		article.setUpdated(new Date());
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());
		article.setHot(0);
		article.setComplainNum(0);
		return articleService.insertSelective(article) > 0;
	}

	@ResponseBody
	@PostMapping("article/updateArticleInfo")
	public boolean updateArticleInfo(MultipartFile file, ArticleWithBLOBs article, HttpServletRequest request)
			throws IllegalStateException, IOException {
		String path = "d:/pic/";
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString().replace("-", "")
					+ fileName.substring(fileName.lastIndexOf("."));
			file.transferTo(new File(path, newFileName));
			article.setPicture(newFileName);
		}
		article.setCreated(new Date());
		article.setStatus(0);
		article.setHits(0);
		article.setDeleted(0);
		article.setUpdated(new Date());
		HttpSession session = request.getSession();
		User u = (User) session.getAttribute("user");
		article.setUserId(u.getId());
		article.setHot(0);
		article.setComplainNum(0);
		return articleService.updateByPrimaryKeySelective(article) > 0;
	}

	/**
	 * @Title: article
	 * @Description: TODO文章详情
	 * @param id
	 * @return
	 * @return: String
	 */
	@GetMapping("article/article")
	public String article(Model m, Integer id) {
		ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(id);
		m.addAttribute("article", selectByPrimaryKey);
		return "my/article/article";
	}
}
