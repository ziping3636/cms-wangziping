package com.wangziping.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		try {
			String valueEncode = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, valueEncode);
			cookie.setPath("/");
			if (maxAge > 0) {
				cookie.setMaxAge(maxAge);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
	}

	/**
	 * @Title: getCookieByName
	 * @Description: TODO从cookie中获取值
	 * @param request
	 * @param name
	 * @return
	 * @return: Cookie
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
