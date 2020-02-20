package com.wangziping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.wangziping.domain.User;
import com.wangziping.mapper.ArticleRepository;
import com.wangziping.service.UserService;

@RequestMapping("admin")
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("user/selects")
	public String selects(Model m, String username, @RequestParam(defaultValue = "1") Integer pageNum,
			@RequestParam(defaultValue = "3") Integer pageSize) {
		PageInfo<User> selects = userService.selects(username, pageNum, pageSize);
		m.addAttribute("info", selects);
		m.addAttribute("username", username);
		return "admin/user/users";
	}

	@ResponseBody
	@PostMapping("user/update")
	public boolean update(User user) {
		return userService.updateByPrimaryKeySelective(user) > 0;
	}
}
