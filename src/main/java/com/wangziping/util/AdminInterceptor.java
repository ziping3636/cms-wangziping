package com.wangziping.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wangziping.domain.User;

/**
 * @ClassName: UserInterceptor
 * @Description: TODO管理员拦截器, 只拦截进入管理员后台的用户
 * @author: hasee
 * @date: 2019年12月18日 下午3:12:56
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);

		if (null != session) {
			User attribute = (User) session.getAttribute("admin");
			if (null != attribute) {
				return true;
			}
		}

		request.setAttribute("error", "权限不符合, 请重新登录");
		request.getRequestDispatcher("/WEB-INF/view/passport/login.jsp").forward(request, response);

		return super.preHandle(request, response, handler);
	}
}
