package com.oracle.frontweb.service;

import java.util.List;

import com.oracle.manager.pojo.Cart;
import com.oracle.manager.pojo.User;

public interface CartService {

	public List<Cart> showCart(User user) throws Exception;

	public void addCart(User user, Long productId) throws Exception;

	public void deleteCart(User user, Long productId) throws Exception;

	public void updateNum(User user, Long productId, Integer num) throws Exception;

}
