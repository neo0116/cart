package com.oracle.frontweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.commons.utils.CookieUtils;
import com.oracle.frontweb.service.UserService;
import com.oracle.manager.pojo.User;

public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService UserService;
	
	private static final String cookieName = "SSO_COOKIE";
	
	private static final String SSO_ORACLE_COM="http://sso.oracle.com/";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String ticket = CookieUtils.getCookieValue(request, cookieName, true);
		if (StringUtils.isEmpty(ticket)) {
			return false;
		}
		User user =  UserService.check(SSO_ORACLE_COM,ticket);
		if (user==null) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
