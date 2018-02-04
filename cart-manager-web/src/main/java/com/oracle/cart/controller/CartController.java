package com.oracle.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.cart.service.CartService;
import com.oracle.manager.pojo.Cart;

@RequestMapping("/cart")
@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	//添加商品到购物车
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Void> addProductToCart(Cart cart){
		try {
			Boolean b = cartService.addProductToCart(cart);
			if (b) {
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	//通过用户Id去查询购物车商品
	@RequestMapping(value="/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Cart>> queryAllCartByUserId(@PathVariable("userId") Long userId){
		try {
			List<Cart> list = cartService.queryAllCartByUserId(userId);
			if (list!=null) {
				return ResponseEntity.status(HttpStatus.OK).body(list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//通过用户Id，商品的Id去更新购物车中的数量
	@RequestMapping(value="/{userId}/{productId}/{num}",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Void> updateNumOnCart(
			@PathVariable("userId")Long userId,
			@PathVariable("productId")Long productId,
			@PathVariable("num")Integer num){
		try {
			Boolean b = cartService.updateNumOnCart(userId,productId,num);
			if (b) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@RequestMapping(value="/{userId}/{productId}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProductByUserIdAndProductId(
			@PathVariable("userId")Long userId,
			@PathVariable("productId")Long productId){
		try {
			Boolean b = cartService.deleteProductByUserIdAndProductId(userId,productId);
			if (b) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
