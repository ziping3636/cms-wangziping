package com.wangziping.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.Article;
import com.wangziping.domain.ArticleWithBLOBs;
import com.wangziping.domain.Category;
import com.wangziping.domain.Channel;
import com.wangziping.domain.Collect;
import com.wangziping.domain.Complain;
import com.wangziping.domain.Slide;
import com.wangziping.domain.User;
import com.wangziping.service.ArticleService;
import com.wangziping.service.ChannelService;
import com.wangziping.service.CollectService;
import com.wangziping.service.ComplainService;
import com.wangziping.service.SlideService;
import com.wangziping.util.HLUtils;

@Controller
public class IndexController {

	@Autowired
	private ChannelService channelService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private SlideService slideService;

	@Autowired
	private ComplainService complainService;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@SuppressWarnings("unused")
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ThreadPoolTaskExecutor executor;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "", "/", "index" })
	public String index(Model m, Article article, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "5") Integer pageSize) {

		article.setStatus(1);
		// 封装查询条件
		m.addAttribute("article", article);

		// 查询所有栏目
		List<Channel> selects = channelService.selects();
		m.addAttribute("channels", selects);

		// 如果栏目为空, 则默认显示推荐的文章
		if (null == article.getChannelId()) {

			List range = redisTemplate.opsForList().range("hotArticle", (pageNum - 1) * pageSize,
					(pageNum - 1) * pageSize + pageSize - 1);
			if (range == null || range.size() == 0) {
				PageInfo<Article> selects2 = articleService.selects(article, pageNum, pageSize);
				m.addAttribute("info", selects2);
				redisTemplate.opsForList().leftPushAll("hotArticle", selects2.getList().toArray());
			} else {
				PageInfo<Article> pageInfo = new PageInfo<Article>(range);
				m.addAttribute("info", pageInfo);
			}

			List<Slide> slides = slideService.selects();
			m.addAttribute("slides", slides);

		}

		// 如果栏目不为空, 则查询栏目下所有分类
		if (null != article.getChannelId()) {
			List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());

			// 查询栏目下所有的文章
			PageInfo<Article> selects2 = articleService.selects(article, pageNum, pageSize);
			m.addAttribute("info", selects2);
			m.addAttribute("categorys", categorys);
		}
		// 如果分类不为空, 则查询分类下 文章
		if (null != article.getCategoryId()) {
			PageInfo<Article> selects2 = articleService.selects(article, pageNum, pageSize);
			m.addAttribute("info", selects2);
		}

		// 显示最近发布的5篇文章
		Article last = new Article();
		last.setStatus(1);

		// 1:从redis中查询最新文章
		List<Article> range = redisTemplate.opsForList().range("new_article", 0, -1);
		// 2:判断redis中的最新文章有没有
		if (range == null || range.size() == 0) {
			// 3:如果为空就从MySQL中查询，存入redis，返回给前台
			PageInfo<Article> selects2 = articleService.selects(last, 1, 5);
			redisTemplate.opsForList().leftPushAll("new_article", selects2.getList().toArray());
			m.addAttribute("lastInfo", selects2);
		} else {
			// 4:如果不为空，就直接把redis中的数据返回给前台
			PageInfo<Article> selects2 = new PageInfo<Article>(range);
			m.addAttribute("lastInfo", selects2);
		}
		PageInfo<Article> lastInfo = articleService.selects(last, 1, 5);
		m.addAttribute("lastInfo", lastInfo);

		return "index/index";
	}

	@SuppressWarnings("unchecked")
	@GetMapping("article")
	public String article(Model m, Integer id, HttpServletRequest request) throws Exception {
		ArticleWithBLOBs selectByPrimaryKey = articleService.selectByPrimaryKey(id);
		
		// 获取用户IP方式
		String user_ip = request.getRemoteAddr();
		// 准备redis的key
		String key = "Hits" + id + user_ip;
		// 查询redis中有没有该key
		String redisKey = (String) redisTemplate.opsForValue().get(key);
		if (redisKey == null) {
			executor.execute(new Thread() {
				@Override
				public void run() {
					selectByPrimaryKey.setHits(selectByPrimaryKey.getHits() + 1);
					articleService.updateByPrimaryKeySelective(selectByPrimaryKey);
					redisTemplate.opsForValue().set(key, "", 5, TimeUnit.MINUTES);
				}
			});
		}

//		kafkaTemplate.send("a1", "upd" + JSON.toJSONString(articleWithBLOBs));
		m.addAttribute("article", selectByPrimaryKey);
		return "index/article";
	}

	@GetMapping("complain")
	public String complain(Model m, Article article, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {
			article.setUser(user);
			m.addAttribute("article", article);
			return "index/complain";
		}
		return "redirect:/passport/login";
	}

	@ResponseBody
	@PostMapping("complain")
	public boolean complain(MultipartFile file, Complain complain) throws Exception {
		if (!file.isEmpty()) {
			String path = "/pic/";
			String fileName = file.getOriginalFilename();
			String newFileName = UUID.randomUUID().toString().replace("-", "")
					+ fileName.substring(fileName.lastIndexOf("."));
			File file2 = new File(path, newFileName);
			file.transferTo(file2);
			complain.setPicurl(fileName);
		}
		return complainService.insert(complain);
	}

	/**
	 * @Title: search
	 * @Description: elasticSearch查询
	 * @param key
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @return: String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("search")
	public String search(String key, Model model, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {

		PageInfo<Article> findByHighLight = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate,
				Article.class, pageNum, pageSize, new String[] { "title" }, "id", key); // 高亮搜索

		model.addAttribute("info", findByHighLight);
		model.addAttribute("key", key);
		return "index/esSearch";
	}

}
