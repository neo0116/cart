package com.oracle.frontweb.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.manager.pojo.Cart;

public interface CookieCartService {

	 public  List<Cart> showCart(HttpServletRequest request) throws Exception;

	public Boolean addCart(HttpServletRequest request,
			HttpServletResponse response, Long productId) throws Exception;

	public void deleteCart(HttpServletRequest request, Long productId) throws Exception;

	public void updateNum(HttpServletRequest request, Long productId,
			Integer num) throws Exception;

}
