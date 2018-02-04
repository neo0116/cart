package com.oracle.frontweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.oracle.commons.pojo.HttpClientReturn;
import com.oracle.commons.utils.CookieUtils;
import com.oracle.frontweb.httpclient.service.HttpClientUtils;
import com.oracle.frontweb.threadlocal.UserThreadLocal;
import com.oracle.manager.pojo.User;

public class UserCartInterceptor implements HandlerInterceptor {
	
	@Autowired
	private  HttpClientUtils httpClientUtils;
	
	@Value("${CHECK_USER_LOGIN}")
	public String CHECK_USER_LOGIN;
	
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1,
			Object arg2) throws Exception {
		String ticket = CookieUtils.getCookieValue(request, "SSO_COOKIE", true);
		String url = CHECK_USER_LOGIN + ticket;
		HttpClientReturn httpClientReturn = httpClientUtils.doGet(url);
		Integer statusCode = httpClientReturn.getStatusCode();
		if (statusCode==200) {
			String jsonData = httpClientReturn.getResponseBody();
			User user = MAPPER.readValue(jsonData, User.class);
			UserThreadLocal.set(user);
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

}
