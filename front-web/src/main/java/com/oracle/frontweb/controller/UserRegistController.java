package com.oracle.frontweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.frontweb.service.UserService;
import com.oracle.manager.pojo.User;

@RequestMapping("/user")
@Controller
public class UserRegistController {
	
	@Autowired
	private UserService userService;
	
	private static final String SSO_ORACLE_COM="http://sso.oracle.com/";
	
	@RequestMapping(value="/reg.html",method=RequestMethod.GET)
	public  ModelAndView registPage() {
		ModelAndView modelAndView = new ModelAndView("reg");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/doreg.html",method=RequestMethod.POST)
	public String doRegist(User user) {
		try {
			Boolean bl = userService.doRegist(SSO_ORACLE_COM,user);
			if (bl) {
				return "redirect:/";
			}
			return "redirect:/user/reg.html";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/reg.html";
	}
	
	
}
