package com.oracle.cart.service;

import java.util.List;

import com.oracle.manager.pojo.Cart;

public interface CartService {

	public  Boolean addProductToCart(Cart cart);

	public List<Cart> queryAllCartByUserId(Long userId);

	public Boolean updateNumOnCart(Long userId, Long productId, Integer num);

	public Boolean deleteProductByUserIdAndProductId(Long userId, Long productId);

}
