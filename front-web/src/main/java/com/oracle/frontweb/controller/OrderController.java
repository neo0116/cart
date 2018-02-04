package com.oracle.frontweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/order")
@Controller
public class OrderController {
	@RequestMapping(value="/order.html",method=RequestMethod.GET)
	public ModelAndView orderPage() {
		ModelAndView modelAndView = new ModelAndView("order");
		return modelAndView;
	}
}
