package com.wangziping.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wangziping.domain.User;
import com.wangziping.service.UserService;
import com.wangziping.util.CMSException;
import com.wangziping.util.CookieUtil;
import com.wangziping.utils.StreamUtil;
import com.wangziping.utils.StringUtil;

@RequestMapping("passport")
@Controller
public class PassportController {

	@Autowired
	private UserService userService;

	@GetMapping("login")
	public String login() {
		return "passport/login";
	}

	@GetMapping("reg")
	public String reg() {
		return "passport/reg";
	}

	@PostMapping("reg")
	public String reg(Model m, RedirectAttributes redeAttributes, User user) {
		try {
			int insertSelective = userService.insertSelective(user);
			if (insertSelective > 0) {
				redeAttributes.addFlashAttribute("username", user.getUsername());
				return "redirect:/passport/login";
			}
		} catch (CMSException e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", "系统错误, 请联系管理员!");
		}
		m.addAttribute("user", user);
		
		return "passport/reg";
	}

	@PostMapping("login")
	public String login(Model m, User user, HttpSession session, HttpServletResponse response) {
		try {
			User u = userService.login(user);

			if (StringUtil.hasText(user.getIsRemenber())) {
				CookieUtil.addCookie(response, "username", user.getUsername(), 60 * 60 * 24 * 10);
				CookieUtil.addCookie(response, "password", user.getPassword(), 60 * 60 * 24 * 10);
			}
			if ("0".equals(u.getRole())) {
				session.setAttribute("user", u);
				return "redirect:/my";
			} else {
				session.setAttribute("admin", u);
				return "redirect:/admin";
			}
		} catch (CMSException e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			m.addAttribute("error", "请与管理员联系");
		}
		return "passport/login";
	}

	@GetMapping("logout")
	public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}

		session.invalidate();

		return "redirect:/passport/login";
	}

}
