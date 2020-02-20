package com.wangziping.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wangziping.domain.User;
import com.wangziping.mapper.UserMapper;

/**
 * @ClassName: UserInterceptor
 * @Description: TODO用户拦截器, 只拦截进入个人中心的用户
 * @author: hasee
 * @date: 2019年12月18日 下午3:12:56
 */
public class UserInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);

		if (null != session) {
			User attribute = (User) session.getAttribute("user");
			if (null != attribute) {
				return true;
			}
		}

		request.setAttribute("error", "权限不符合, 请重新登录");
		request.getRequestDispatcher("/WEB-INF/view/passport/login.jsp").forward(request, response);

		return super.preHandle(request, response, handler);
	}

	@SuppressWarnings("unused")
	private boolean rememberAutoLogin(HttpServletRequest request, HttpSession session) throws Exception {

		Cookie cookieByName = CookieUtil.getCookieByName(request, "username");
		Cookie cookieByPassword = CookieUtil.getCookieByName(request, "password");

		if (null != cookieByName && null != cookieByPassword && null != cookieByName.getValue()
				&& null != cookieByPassword.getValue()) {

			String username = URLDecoder.decode(cookieByName.getValue(), "UTF-8");
			String password = URLDecoder.decode(cookieByPassword.getValue(), "UTF-8");

			User user = userMapper.selectByName(username);

			if (null != user && username.equals(user.getUsername()) && password.equals(user.getPassword())) {
				session.setAttribute("user", user);
				return true;
			}
		}

		return false;
	}
}
