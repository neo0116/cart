package com.oracle.frontweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.commons.utils.CookieUtils;
import com.oracle.frontweb.service.UserService;

@RequestMapping("/user")
@Controller
public class UserLoginController {
	
	@Autowired
	private UserService userService;
	
	private static final String cookieName = "SSO_COOKIE";
	
	private static final String SSO_ORACLE_COM="http://sso.oracle.com/";

	@RequestMapping(value="/login.html",method=RequestMethod.GET)
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String dologin(
			@RequestParam("loginname")String loginname,
			@RequestParam("nloginpwd")String nloginpwd,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			String ticket = userService.dologin(request,SSO_ORACLE_COM,loginname,nloginpwd);
			System.out.println(ticket);
			if (!StringUtils.isEmpty(ticket)) {
				CookieUtils.setCookie(request, response, cookieName, ticket, true);
				return "redirect:/index.html";
			}
			return "redirect:/user/login.html";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/login.html";
	}
	
}
