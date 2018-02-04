package com.oracle.frontweb.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.frontweb.service.CartService;
import com.oracle.frontweb.service.CookieCartService;
import com.oracle.frontweb.threadlocal.UserThreadLocal;
import com.oracle.manager.pojo.Cart;
import com.oracle.manager.pojo.User;

@RequestMapping("/cart")
@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private CookieCartService  cookieCartService;

	@RequestMapping(value = "/showcart.html", method = RequestMethod.GET)
	public ModelAndView showCart(HttpServletRequest request) {
		User user = UserThreadLocal.get();

		List<Cart> list = new ArrayList<>();
		try {
			if (user == null) {
				list = cookieCartService.showCart(request);
			} else {
				list = cartService.showCart(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView("cart");
		modelAndView.addObject("cartList", list);
		return modelAndView;
	}

	// 添加商品：www.oracle.com/cart/addcart/{productId}
	@RequestMapping(value = "/addcart/{productId}", method = RequestMethod.GET)
	public String addCart(
			@PathVariable("productId") Long productId,
			HttpServletRequest request,
			HttpServletResponse response) {
		User user = UserThreadLocal.get();
		if (user == null) {
			try {
				cookieCartService.addCart(request,response,productId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				cartService.addCart(user, productId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/cart/showcart.html";
	}

	// 删除购物车的商品
	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
	public String deleteCart(
			@PathVariable("productId") Long productId,
			HttpServletRequest request) {
		User user = UserThreadLocal.get();
		if (user == null) {
			try {
				cookieCartService.deleteCart(request,productId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				cartService.deleteCart(user, productId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  

		return "redirect:/cart/showcart.html";
	}
	
	
	//更新数量：http://www.oracle.com/service/cart/update/num/1473155199/3
	@RequestMapping(value="/update/num/{productId}/{num}",method=RequestMethod.POST)
	public String updateNum(
			@PathVariable("productId") Long productId,
			@PathVariable("num")Integer num,
			HttpServletRequest request){
		User user = UserThreadLocal.get();
		if (user == null) {
			try {
				cookieCartService.updateNum(request,productId,num);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				cartService.updateNum(user, productId,num);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/cart/showcart.html";
	}
}
